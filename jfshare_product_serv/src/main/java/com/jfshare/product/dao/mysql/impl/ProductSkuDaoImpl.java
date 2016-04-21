package com.jfshare.product.dao.mysql.impl;

import com.jfshare.product.dao.mysql.IProductSkuDao;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductSkuExample;
import com.jfshare.product.model.mapper.TbProductSkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lenovo on 2015/10/20.
 */
@Repository
public class ProductSkuDaoImpl implements IProductSkuDao{

    @Autowired
    private TbProductSkuMapper tbProductSkuMapper;

    @Override
    public boolean addProductSku(List<TbProductSku> tbProductSkus) {
        for (TbProductSku tbProductSku : tbProductSkus) {
            tbProductSkuMapper.insertSelective(tbProductSku);
        }
        return true;
    }

    @Override
    public boolean delSkuByProductId(String productId) {
        TbProductSkuExample tbProductSkuExample = new TbProductSkuExample();
        tbProductSkuExample.createCriteria().andProductIdEqualTo(productId);
        tbProductSkuMapper.deleteByExample(tbProductSkuExample);
        return true;
    }

    @Override
    public Object add(Object obj) {
        return null;
    }

    @Override
    public void update(Object obj) {

    }

    @Override
    public Object getById(Object id) {
        return null;
    }

    @Override
    public void delete(Object id) {

    }
}
