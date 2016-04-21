package com.jfshare.product.dao.mysql;

import com.jfshare.product.dao.BaseDAO;
import com.jfshare.product.model.TbProductSku;

import java.util.List;

/**
 * Created by Lenovo on 2015/10/20.
 */
public interface IProductSkuDao extends BaseDAO{
    boolean addProductSku(List<TbProductSku> tbProductSkus);
    boolean delSkuByProductId(String productId);
}
