package com.jfshare.baseTemplate.service;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.manual.CalculatePostageModel;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IPostageTemplateSvc {

    int addPostageTemplate(TbPostageTemplate tbPostageTemplate);

    int updatePostageTemplate(TbPostageTemplate tbPostageTemplate);

    int deletePostageTemplate(int sellerId, int id);

    int deletePostageTemplateBatch(List<TbPostageTemplate> tbPostageTemplateList);

    /**
     * 根据条件查询邮费模板，管理页面用
     * @param queryMap
     * @return
     */
    List<TbPostageTemplate> queryPostageTemplate(Map queryMap);

    /**
     * 根据id获取邮费模板信息
     * @param id
     * @return
     */
    TbPostageTemplate getById(int id);


    /**
     * 清除邮费模板缓存
     * @param id
     */
    void removeCache(int id);

    /**
     * 将邮费模板信息添加到缓存
     * @param id
     */
    TbPostageTemplate addCache(int id);

    String calculatePostage(CalculatePostageModel model);

}
