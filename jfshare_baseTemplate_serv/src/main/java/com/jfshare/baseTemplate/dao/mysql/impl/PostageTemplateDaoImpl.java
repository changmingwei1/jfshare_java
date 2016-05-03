package com.jfshare.baseTemplate.dao.mysql.impl;

import com.jfshare.baseTemplate.dao.mysql.IPostageTemplateDao;
import com.jfshare.baseTemplate.mybatis.mapper.automatic.TbPostageTemplateMapper;
import com.jfshare.baseTemplate.mybatis.mapper.manual.TbPostageTemplateManualMapper;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplateExample;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 邮费模板DAO实现类
 */
@Repository
public class PostageTemplateDaoImpl extends BaseDaoImpl<TbPostageTemplate, Integer> implements IPostageTemplateDao {

    @Resource
    private TbPostageTemplateMapper templateMapper;

    @Resource
    private TbPostageTemplateManualMapper templateManualMapper;

    @Override
    public int deletePostageTemplate(int sellerId, int id) {
        TbPostageTemplateExample example = new TbPostageTemplateExample();
        TbPostageTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andSellerIdEqualTo(sellerId);
        criteria.andIdEqualTo(id);
        return this.templateMapper.deleteByExample(example);
    }

    @Override
    public List<TbPostageTemplate> queryPostageTemplate(Map queryMap) {
        return this.templateManualMapper.queryPostageTemplate(queryMap);
    }
}
