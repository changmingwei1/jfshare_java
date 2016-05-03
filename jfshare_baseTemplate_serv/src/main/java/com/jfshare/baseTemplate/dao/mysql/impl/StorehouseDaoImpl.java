package com.jfshare.baseTemplate.dao.mysql.impl;

import com.jfshare.baseTemplate.dao.mysql.IStorehouseDao;
import com.jfshare.baseTemplate.mybatis.mapper.automatic.TbStorehouseMapper;
import com.jfshare.baseTemplate.mybatis.mapper.manual.TbStorehouseManualMapper;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouseExample;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 仓库DAO实现类
 */
@Repository
public class StorehouseDaoImpl extends BaseDaoImpl<TbStorehouse, Integer> implements IStorehouseDao {

    @Resource
    private TbStorehouseMapper storehouseMapper;

    @Resource
    private TbStorehouseManualMapper storehouseManualMapper;

    @Override
    public int deleteStorehouse(int sellerId, int id) {

        TbStorehouseExample example = new TbStorehouseExample();
        TbStorehouseExample.Criteria criteria = example.createCriteria();
        criteria.andSellerIdEqualTo(sellerId);
        criteria.andIdEqualTo(id);
        return this.storehouseMapper.deleteByExample(example);
    }

    @Override
    public List<TbStorehouse> queryStorehouse(Map queryMap) {
        return this.storehouseManualMapper.queryStorehouse(queryMap);
    }
}
