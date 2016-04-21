package com.jfshare.product.dao.mysql.impl;

import com.jfshare.product.dao.mysql.IProductHistoryDao;
import com.jfshare.product.model.TbProductHistoryWithBLOBs;
import com.jfshare.product.model.mapper.TbProductHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Lenovo on 2015/10/20.
 */
@Repository
public class ProductHistoryDaoImpl implements IProductHistoryDao {
    @Autowired
    private TbProductHistoryMapper tbProductHistoryMapper;


    @Override
    public String add(TbProductHistoryWithBLOBs obj) {
        tbProductHistoryMapper.insertSelective(obj);
        return null;
    }

    @Override
    public void update(TbProductHistoryWithBLOBs obj) {

    }

    @Override
    public TbProductHistoryWithBLOBs getById(String id) {
        return tbProductHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(String id) {

    }
}
