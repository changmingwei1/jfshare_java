package com.jfshare.baseTemplate.dao.mysql;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IPostageTemplateDao extends IBaseDao<TbPostageTemplate, Integer> {

    /**
     * 删除邮费模板，sellerId用于验证
     * @param sellerId
     * @param id
     */

    int deletePostageTemplate(int sellerId, int id);
    /**
     * 根据条件查询邮费模板，管理页面用
     * @param queryMap
     * @return
     */
    List<TbPostageTemplate> queryPostageTemplate(Map queryMap);
}
