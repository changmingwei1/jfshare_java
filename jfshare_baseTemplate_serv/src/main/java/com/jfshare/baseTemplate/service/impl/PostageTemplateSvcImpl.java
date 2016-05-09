package com.jfshare.baseTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.jfshare.baseTemplate.dao.mysql.IPostageTemplateDao;
import com.jfshare.baseTemplate.dao.redis.BaseRedis;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.manual.CalculatePostageModel;
import com.jfshare.baseTemplate.mybatis.model.manual.PostageModel;
import com.jfshare.baseTemplate.service.IPostageTemplateSvc;
import com.jfshare.baseTemplate.util.Constant;
import com.jfshare.baseTemplate.util.ConvertUtil;
import com.jfshare.utils.JsonMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
@Service
public class PostageTemplateSvcImpl implements IPostageTemplateSvc {

    @Resource
    private IPostageTemplateDao postageTemplateDao;

    @Resource
    private BaseRedis baseRedis;

    @Override
    public int addPostageTemplate(TbPostageTemplate tbPostageTemplate) {
        DateTime now = new DateTime();
        tbPostageTemplate.setCreateTime(now);
        tbPostageTemplate.setLastUpdateTime(now);
        return this.postageTemplateDao.add(tbPostageTemplate);
    }

    @Override
    public int updatePostageTemplate(TbPostageTemplate tbPostageTemplate) {
        tbPostageTemplate.setLastUpdateTime(new DateTime());
        return this.postageTemplateDao.updateById(tbPostageTemplate);
    }

    @Override
    public int deletePostageTemplate(int sellerId, int id) {
        return this.postageTemplateDao.deletePostageTemplate(sellerId, id);
    }

    @Override
    public int deletePostageTemplateBatch(List<TbPostageTemplate> tbPostageTemplateList) {
        int num = 0;
        for (TbPostageTemplate template : tbPostageTemplateList) {
            num = num + this.deletePostageTemplate(template.getSellerId(), template.getId());
        }
        return num;
    }

    @Override
    public List<TbPostageTemplate> queryPostageTemplate(Map queryMap) {
        return this.postageTemplateDao.queryPostageTemplate(queryMap);
    }

    @Override
    public TbPostageTemplate getById(int id) {
        TbPostageTemplate tbPostageTemplate = null;
        // 从缓存获取
        Map<String, String> postageTemplateMap = this.baseRedis.getMapAll(Constant.POSTAGE_TEMPLATE_REDIS_KEY + id);
        if (postageTemplateMap != null) {
            tbPostageTemplate = ConvertUtil.redis2TbPostageTemplate(postageTemplateMap);
        } else {
            // 缓存中不存在，从数据库中获取，并放入缓存
            tbPostageTemplate = this.addCache(id);
        }
        return tbPostageTemplate;
    }


    @Override
    public void removeCache(int id) {
        this.baseRedis.removeKV(Constant.POSTAGE_TEMPLATE_REDIS_KEY + id);
    }

    @Override
    public TbPostageTemplate addCache(int id) {
        TbPostageTemplate tbPostageTemplate = this.postageTemplateDao.getById(id);
        if (tbPostageTemplate != null) {
            this.baseRedis.putMap(Constant.POSTAGE_TEMPLATE_REDIS_KEY + id, ConvertUtil.tbPostageTemplate2Redis(tbPostageTemplate));
        }
        return tbPostageTemplate;
    }

    @Override
    public String calculatePostage(CalculatePostageModel model) {

        int postageTotal = 0;
        // 获取模板信息
        TbPostageTemplate tbPostageTemplate = this.getById(model.getTemplateId());
        // 模板不存在
        if(tbPostageTemplate == null) {
           return null;
        }
        String postageInfo = tbPostageTemplate.getPostageInfo();
        // 获取所有运费信息
        List<PostageModel> postageModels = JSON.parseArray(postageInfo, PostageModel.class);
        String rule = "";
        // 找到发送到省份的运费规则
        for (PostageModel postageModel : postageModels) {
            if(("," + postageModel.getSupportProvince() + ",").contains("," + model.getSendToProvince() + ",")) {
                rule = postageModel.getRule();
                break;
            }
        }
        // 按件数计算运费
        Map<String, String> map = JsonMapper.toMap(rule);
        if(tbPostageTemplate.getType() == 11) {
            /* 例子： 2件内，10元，每增加1件，增加运费3元 */

            int number = Integer.parseInt(map.get("number"));
            int postage = Integer.parseInt(map.get("postage"));
            int addNumber = Integer.parseInt(map.get("addNumber"));
            int addPostage = Integer.parseInt(map.get("addPostage"));

            if(model.getNumber() <= number) {
                postageTotal = postage;
            } else {
                postageTotal = postage + (model.getNumber() - number) / addNumber * addPostage;
            }
        }
        // 按重量计算运费
        if(tbPostageTemplate.getType() == 12) {
            /*例子： 2kg内，10元，每增加1kg，增加运费3元*/
            int number = Integer.parseInt(map.get("number"));
            int postage = Integer.parseInt(map.get("postage"));
            int addNumber = Integer.parseInt(map.get("addNumber"));
            int addPostage = Integer.parseInt(map.get("addPostage"));

            if(model.getWeight() <= number) {
                postageTotal = postage;
            } else {
                postageTotal = postage + (model.getWeight() - number) / addNumber * addPostage;
            }
        }
        // 按订单件数+订单金额计算运费
        if(tbPostageTemplate.getType() == 21) {
            int number = Integer.parseInt(map.get("number"));
            int amount = Integer.parseInt(map.get("amount"));
            int limit = Integer.parseInt(map.get("limit"));
            int postage = Integer.parseInt(map.get("postage"));

            // 例： 2  件以上，100 元以上，运费  5  元
            if(model.getNumber() > number || Integer.parseInt(model.getOrderAmount()) > amount) {
                postageTotal = postage;
            }

        }
        // 按订单重量+订单金额计算运费
        if(tbPostageTemplate.getType() == 22) {
            int number = Integer.parseInt(map.get("number"));
            int amount = Integer.parseInt(map.get("amount"));
            int limit = Integer.parseInt(map.get("limit"));
            int postage = Integer.parseInt(map.get("postage"));
            /* 类推示例：5kg以内，100元以上，运费3元；10kg以内，200元以上，运费6元…以此类推 */
            if (limit == 1) {
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    if(model.getWeight() <= i * number && Integer.parseInt(model.getOrderAmount()) >= i * amount) {
                        postageTotal = i * postage;
                    }
                    // 如果重量超过n倍重量基础值，但金额没有到达n倍金额基础值，则没有匹配上规则，返回null
                    if(model.getWeight() > i * number && Integer.parseInt(model.getOrderAmount()) < i * amount) {
                       return null;
                    }
                }

            }
            // 不类推，匹配不上直接返回null
            if(limit == 0) {
                if (model.getWeight() <= number && Integer.parseInt(model.getOrderAmount()) >= amount) {
                    postageTotal = postage;
                } else {
                    return null;
                }
            }
        }

        return postageTotal + "";
    }
}
