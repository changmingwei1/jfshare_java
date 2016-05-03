package com.jfshare.baseTemplate.service.impl;

import com.jfshare.baseTemplate.dao.mysql.IPostageTemplateDao;
import com.jfshare.baseTemplate.dao.redis.BaseRedis;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.service.IPostageTemplateSvc;
import com.jfshare.baseTemplate.util.Constant;
import com.jfshare.baseTemplate.util.ConvertUtil;
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
}
