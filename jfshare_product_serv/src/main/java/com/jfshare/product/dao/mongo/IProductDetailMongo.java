package com.jfshare.product.dao.mongo;

import com.jfshare.product.dao.BaseDAO;
import com.jfshare.product.model.manual.ProductDetail;

/**
 * Created by lenovo on 2015/10/20.
 */
public interface IProductDetailMongo extends BaseDAO<ProductDetail, String>{
    ProductDetail getByProductId(String productId);
}
