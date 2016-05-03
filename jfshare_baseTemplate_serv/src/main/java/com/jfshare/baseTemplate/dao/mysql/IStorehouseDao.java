package com.jfshare.baseTemplate.dao.mysql;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IStorehouseDao extends IBaseDao<TbStorehouse, Integer> {

    /**
     * 删除仓库，sellerId用于校验
     * @param sellerId
     * @param id
     * @return
     */
    int deleteStorehouse(int sellerId, int id);

    /**
     * 根据条件查询仓库信息，管理页面用
     * @param queryMap
     * @return
     */
    List<TbStorehouse> queryStorehouse(Map queryMap);
}
