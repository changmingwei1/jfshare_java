package com.jfshare.baseTemplate.service;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IStorehouseSvc {

    int addStorehouse(TbStorehouse tbStorehouse);

    int updateStorehouse(TbStorehouse tbStorehouse);

    int deleteStorehouse(int sellerId, int id);

    int deleteStorehouseBatch(List<TbStorehouse> storehouseList);

    /**
     * 根据条件查询仓库，管理页面用
     * @param queryMap
     * @return
     */
    List<TbStorehouse> queryStorehouse(Map queryMap);

    /**
     * 根据id获取仓库信息
     * @param id
     * @return
     */
    TbStorehouse getById(int id);



    /**
     * 清除仓库缓存
     * @param id
     */
    void removeCache(int id);

    /**
     * 将仓库信息添加到缓存
     * @param id
     */
    TbStorehouse addCache(int id);

}
