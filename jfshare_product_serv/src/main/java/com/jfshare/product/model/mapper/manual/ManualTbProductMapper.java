package com.jfshare.product.model.mapper.manual;

import java.util.List;

import com.jfshare.finagle.thrift.product.ProductSurvey;
import com.jfshare.finagle.thrift.product.ProductSurveyQueryParam;

public interface ManualTbProductMapper {

	List<ProductSurvey> productSurveyQueryPage(ProductSurveyQueryParam param);
	
	List<ProductSurvey> queryFloorProduct(int floorId);
	
    List<ProductSurvey> productSurveyQuery(ProductSurveyQueryParam param);
    
    ProductSurvey productSurveyQueryById(ProductSurveyQueryParam param);
}
