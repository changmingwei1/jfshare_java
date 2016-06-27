package com.jfshare.baseTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.baseTemplate.dao.mysql.IPostageTemplateDao;
import com.jfshare.baseTemplate.dao.redis.BaseRedis;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.manual.ProductPostageModel;
import com.jfshare.baseTemplate.mybatis.model.manual.PostageModel;
import com.jfshare.baseTemplate.mybatis.model.manual.SellerPostageModel;
import com.jfshare.baseTemplate.mybatis.model.manual.SellerPostageReturnModel;
import com.jfshare.baseTemplate.service.IPostageTemplateSvc;
import com.jfshare.baseTemplate.util.Constant;
import com.jfshare.baseTemplate.util.ConvertUtil;
import com.jfshare.baseTemplate.util.FailCode;
import com.jfshare.finagle.thrift.baseTemplate.CalculatePostageResult;
import com.jfshare.finagle.thrift.baseTemplate.SellerPostageReturn;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.utils.PriceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<TbPostageTemplate> getPostageTemplateBySellerId(int sellerId, int group, int isUsed) {
        // TODO: 2016/5/11 先简单实现，后续需要在缓存里获取
        Map queryMap = new HashMap();
        queryMap.put("sellerId", sellerId);
        queryMap.put("templateGroup", group);
        queryMap.put("isUsed", isUsed);
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
    public String calculatePostage(ProductPostageModel model) {

        /*double postageTotal = 0;
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
            *//* 例子： 2件内，10元，每增加1件，增加运费3元 *//*

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
            *//*例子： 2kg内，10元，每增加1kg，增加运费3元*//*
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
            if(model.getNumber() > number || Integer.parseInt(model.getAmount()) > amount) {
                postageTotal = postage;
            }

        }
        // 按订单重量+订单金额计算运费
        if(tbPostageTemplate.getType() == 22) {
            int number = Integer.parseInt(map.get("number"));
            int amount = Integer.parseInt(map.get("amount"));
            int limit = Integer.parseInt(map.get("limit"));
            int postage = Integer.parseInt(map.get("postage"));
            *//* 类推示例：5kg以内，100元以上，运费3元；10kg以内，200元以上，运费6元…以此类推 *//*
            if (limit == 1) {
                for (int i = 1; i < Integer.MAX_VALUE; i++) {
                    if(model.getWeight() <= i * number && Integer.parseInt(model.getAmount()) >= i * amount) {
                        postageTotal = i * postage;
                    }
                    // 如果重量超过n倍重量基础值，但金额没有到达n倍金额基础值，则没有匹配上规则，返回null
                    if(model.getWeight() > i * number && Integer.parseInt(model.getAmount()) < i * amount) {
                       return null;
                    }
                }

            }
            // 不类推，匹配不上直接返回null
            if(limit == 0) {
                if (model.getWeight() <= number && Integer.parseInt(model.getAmount()) >= amount) {
                    postageTotal = postage;
                } else {
                    return null;
                }
            }
        }

        return postageTotal + "";*/

        return null;
    }

    @Override
    public CalculatePostageResult calculatePostage(List<SellerPostageModel> sellerPostageModels, String sendToProvince) {

        CalculatePostageResult calculatePostageResult = new CalculatePostageResult();
        Result result = new Result();
        calculatePostageResult.setResult(result);
        int totalPostage = 0;

        // 计算单个卖家商品总邮费
        for (SellerPostageModel sellerPostageModel : sellerPostageModels) {
            int sellerProductTotal = 0;
            int sellerTotal = 0;
            List<FailDesc> sellerFail = new ArrayList<>();
            for (ProductPostageModel productPostageModel : sellerPostageModel.getProductPostageModels()) {
                // 计算卖家单个商品的邮费
                int productPostage = getProductPostage(productPostageModel, sendToProvince);
                // 模板不存在
                if (productPostage == -1) {
                    FailDesc failDesc = FailCode.POSTAGE_CALCULATE_PRODUCT_POSTAGE_TEMPLATE_NOT_EXIST;
                    sellerFail.add(new FailDesc(productPostageModel.getProductId(), failDesc.getFailCode(), failDesc.getDesc()));
                    sellerProductTotal = Integer.MAX_VALUE;
                }
                // 没有找到对应的省份
                if (productPostage == -2) {
                    FailDesc failDesc = FailCode.POSTAGE_CALCULATE_CAN_NOT_SEND;
                    sellerFail.add(new FailDesc(productPostageModel.getProductId(), failDesc.getFailCode(), failDesc.getDesc()));
                    sellerProductTotal = Integer.MAX_VALUE;
                }
                // 正确返回
                if (productPostage >= 0) {
                    sellerProductTotal += productPostage;
                }

            }
            // 计算商家维度邮费
            // 获取商家邮费优惠模板
            List<TbPostageTemplate> templates = this.getPostageTemplateBySellerId(sellerPostageModel.getSellerId(), 2, 1);
            // 商家邮费优惠模板不存在
            if (CollectionUtils.isEmpty(templates)) {
                sellerTotal = Integer.MAX_VALUE;
            } else {
                sellerTotal = getShopPostage(templates, sellerPostageModel.getProductPostageModels(), sendToProvince);
                // 无法计算卖家维度运费， 模板不存在或者无收货地址匹配的模板
                if (sellerTotal < 0) {
                    sellerTotal = Integer.MAX_VALUE;
                }
            }

            // 如果两个结果都没计算成功
            if (sellerTotal == Integer.MAX_VALUE && sellerProductTotal == Integer.MAX_VALUE) {
                for (FailDesc failDesc : sellerFail) {
                    result.addToFailDescList(failDesc);
                }
            } else {
                int minPostage = getMin(sellerProductTotal, sellerTotal);
                totalPostage += minPostage;
                SellerPostageReturn sellerPostageReturn = new SellerPostageReturn(sellerPostageModel.getSellerId(), PriceUtils.intToStr(minPostage));
                sellerPostageReturn.setPostageTemplate(CollectionUtils.isEmpty(templates) ? "" : templates.get(0).getTemplateDesc());
                calculatePostageResult.addToSellerPostageReturnList(sellerPostageReturn);
            }

        }
        if (CollectionUtils.isEmpty(result.getFailDescList())) {
            result.setCode(0);
            calculatePostageResult.setTotalPostage(PriceUtils.intToStr(totalPostage));
        } else {
            result.setCode(1);
        }
        return calculatePostageResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean setDefaultPostageTemplate(TbPostageTemplate tbPostageTemplate) {

        List<TbPostageTemplate> tbPostageTemplateList = this.getPostageTemplateBySellerId(tbPostageTemplate.getSellerId(), tbPostageTemplate.getTemplateGroup(), 1);
        if (CollectionUtils.isEmpty(tbPostageTemplateList)) {
            // 将原来使用的模板修改成不使用
            TbPostageTemplate oldUsed = new TbPostageTemplate();
            oldUsed.setId(tbPostageTemplateList.get(0).getId());
            oldUsed.setIsUsed(2);
            this.updatePostageTemplate(oldUsed);
        }
        TbPostageTemplate newUsed = new TbPostageTemplate();
        newUsed.setId(tbPostageTemplate.getId());
        newUsed.setIsUsed(1);
        this.updatePostageTemplate(newUsed);
        return true;
    }

    /**
     * 获取商品维度邮费
     * @param model
     * @param sendToProvince
     * @return
     */
    private int getProductPostage(ProductPostageModel model, String sendToProvince) {
        int totalPostage = 0;
        // 全国包邮，统一模板id
        if (model.getTemplateId() == 1) {
            return 0;
        }
        // 获取模板信息
        TbPostageTemplate tbPostageTemplate = this.getById(model.getTemplateId());
        // 模板不存在，返回错误数据
        if(tbPostageTemplate == null) {
            return -1;
        }

        String postageInfo = tbPostageTemplate.getPostageInfo();
        // 获取所有运费信息
        List<PostageModel> postageModels = JSON.parseArray(postageInfo, PostageModel.class);
        String rule = "";
        // 找到发送到省份的运费规则
        for (PostageModel postageModel : postageModels) {
            if(("," + postageModel.getSupportProvince() + ",").contains("," + sendToProvince + ",")) {
                rule = postageModel.getRule();
                break;
            }
        }
        // 没有找到对应的省份，返回错误数据
        if ("".equals(rule)) {
            return -2;
        }
        JSONObject jsonObject = JSON.parseObject(rule);
        int number = jsonObject.getInteger("number");
        int postage = PriceUtils.strToInt(jsonObject.getString("postage"));
        int addNumber = jsonObject.getInteger("addNumber");
        int addPostage = PriceUtils.strToInt(jsonObject.getString("addPostage"));

        // 按件数计算运费
        if(tbPostageTemplate.getType() == 11) {
            /* 例子： 2件内，10元，每增加1件，增加运费3元 */
            if (model.getNumber() <= number) {
                totalPostage = postage;
            } else {
                totalPostage = postage + (model.getNumber() - number) / addNumber * addPostage;
            }
        }
        // 按重量计算运费
        double weight = jsonObject.getDouble("number");
        double addWeight = jsonObject.getDouble("addNumber");
        if(tbPostageTemplate.getType() == 12) {
            /*例子： 2kg内，10元，每增加1kg，增加运费3元*/
            if(model.getWeight() <= weight) {
                totalPostage = postage;
            } else {
                totalPostage = (int) (postage + (model.getWeight() - weight) / addWeight * addPostage);
            }
        }
        return totalPostage;
    }

    private int getShopPostage(List<TbPostageTemplate> templates, List<ProductPostageModel> models, String sendToProvince) {

        int totalPostage = Integer.MAX_VALUE;

        // 计算总金额,总重量和总件数
        int totalAmount = 0;
        double totalWeight = 0;
        int totalNumber = 0;
        for (ProductPostageModel model : models) {
            totalAmount += PriceUtils.strToInt(model.getAmount());
            totalWeight += model.getWeight();
            totalNumber += model.getNumber();
        }

        for (TbPostageTemplate template : templates) {
            String postageInfo = template.getPostageInfo();
            // 获取所有运费信息
            List<PostageModel> postageModels = JSON.parseArray(postageInfo, PostageModel.class);
            String rule = "";
            // 找到发送到省份的运费规则
            for (PostageModel postageModel : postageModels) {
                if (("," + postageModel.getSupportProvince() + ",").contains("," + sendToProvince + ",")) {
                    rule = postageModel.getRule();
                    break;
                }
            }
            // 没有找到对应的省份，返回错误数据
            if ("".equals(rule)) {
                return -2;
            }
            JSONObject jsonObject = JSON.parseObject(rule);
            int number = jsonObject.getInteger("number");
            int amount = PriceUtils.strToInt(jsonObject.getString("amount"));
            int limit = jsonObject.getInteger("limit");
            int postage = PriceUtils.strToInt(jsonObject.getString("postage"));

            // 按订单件数+订单金额计算运费
            if (template.getType() == 21) {
                // 例： 2  件以上，或 100 元以上，运费  5  元
                if (totalNumber > number || totalAmount > amount) {
                    // 取较小邮费金额
                    totalPostage = getMin(totalPostage, postage);
                }

            }

            // 按订单重量+订单金额计算运费
            if (template.getType() == 22) {
                /* 类推示例：5kg以内，100元以上，运费3元；10kg以内，200元以上，运费6元…以此类推 */
                if (limit == 1) {
                    for (int i = 1; i < Integer.MAX_VALUE; i++) {
                        if (totalWeight <= i * number && totalAmount >= i * amount) {
                            // 取较小邮费金额
                            totalPostage = getMin(totalPostage, i * postage);
                        }
                        // 如果重量超过n倍重量基础值，但金额没有到达n倍金额基础值，则没有匹配上规则，运费总额不变
                        if (totalWeight > i * number && totalAmount < i * amount) {
                            break;
                        }
                    }
                }
                // 不类推，匹配不上邮费总额不变
                if (limit == 0) {
                    if (totalWeight <= number && totalAmount >= amount) {
                        // 取较小邮费金额
                        totalPostage = getMin(totalPostage, postage);
                    }
                }
            }
        }
        return totalPostage;
    }

    private int getMin(int a, int b) {
        return a > b ? b : a;
    }
}
