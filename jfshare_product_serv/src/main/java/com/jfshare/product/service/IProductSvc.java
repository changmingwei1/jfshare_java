package com.jfshare.product.service;

import com.jfshare.finagle.thrift.product.*;
import com.jfshare.finagle.thrift.result.FailDesc;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IProductSvc {
    Product queryProduct(String productId, ProductRetParam param);
    
    //商品概要信息列表查询
    ProductSurveyResult productSurveyQuery(ProductSurveyQueryParam param);

    //后台管理服务商品概要信息列表查询
    ProductSurveyResult productSurveyQueryFromDB(ProductSurveyQueryParam param);

    ProductSku getProductSku(String productId);

    String queryProductDetail(ProductDetailParam param);

    String addProduct(Product product);

    String updateProduct(Product product);

    @Deprecated
    ProductSku getProductHotSku(String productId, String skuNum);

    ProductSku getProductHotSku(String productId, int storehouseId, String skuNum);

    FailDesc updateProductState(ProductOpt productOpt) throws InterruptedException;
    
    //商品概要信息列表查询
    ProductSurveyResult queryFloorProduct(int floorId);

    Product queryProductSnap(String snapId, ProductRetParam param);

    List<ProductSurvey> queryProductBatch(int fromType, List<String> productIds);
}
