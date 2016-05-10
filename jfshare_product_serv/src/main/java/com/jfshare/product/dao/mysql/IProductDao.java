package com.jfshare.product.dao.mysql;

import com.jfshare.finagle.thrift.product.ProductSurvey;
import com.jfshare.finagle.thrift.product.ProductSurveyQueryParam;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductSku;
import com.jfshare.product.model.TbProductWithBLOBs;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IProductDao {
    TbProduct queryProduct(String productId);
    TbProductWithBLOBs queryProductWithBLOBs(String productId);
    List<ProductSurvey> productSurveyQueryPage(ProductSurveyQueryParam param);

    List<TbProductSku> getProductSku(String productId);

    @Deprecated
    TbProductSku getProductHotSku(String productId, String skuNum);

    TbProductSku getProductHotSku(String productId, int storehouseId, String skuNum);

    void addProduct(TbProductWithBLOBs tbProduct);

    void updateProduct(TbProductWithBLOBs tbProduct);

    void updateProductState(TbProductWithBLOBs tbProduct, int oldState);
    
    List<ProductSurvey> queryFloorProduct(int floorId);
    

    List<ProductSurvey> productSurveyQuery(ProductSurveyQueryParam param);
    
    ProductSurvey productSurveyQueryById(ProductSurveyQueryParam param);
}
