package com.jfshare.product.server;

import com.jfshare.finagle.thrift.product.*;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.product.exceptions.BaseException;
import com.jfshare.product.service.IProductSvc;
import com.jfshare.product.util.FailCode;
import com.jfshare.product.util.ResultUtil;
import com.jfshare.product.util.ValidateUtil;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value="handler")
public class ServHandle implements ProductServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
    private IProductSvc productSvcImpl;

	@Override
	public ProductResult queryProduct(String productId, ProductRetParam param) throws TException {
		ProductResult productResult = new ProductResult();
		Result result = new Result();
		result.setCode(0);
		productResult.setResult(result);
		if (param == null || StringUtil.isNullOrEmpty(productId)) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productResult;
		}
		try {
			Product product = productSvcImpl.queryProduct(productId, param);
			if (product != null) {
				productResult.setProduct(product);
			} else {
				FailCode.addFails(result, FailCode.PRODUCT_SKU_NULL_ERROR);
			}
		} catch (Exception e) {
			logger.error("查询商品出错！productId=" + productId + ",param=" + param, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return productResult;
	}

	@Override
	public ProductSkuResult queryProductSku(String productId) throws TException {
		ProductSkuResult productSkuResult = new ProductSkuResult();
		Result result = new Result();
		result.setCode(0);
		productSkuResult.setResult(result);
		if (StringUtil.isNullOrEmpty(productId)) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productSkuResult;
		}
		try {
			ProductSku productSku = productSvcImpl.getProductSku(productId);
			if (productSku != null) {
				productSkuResult.setProductSku(productSku);
			} else {
				FailCode.addFails(result, FailCode.PRODUCT_SKU_NULL_ERROR);
			}
		} catch (Exception e) {
			logger.error("查询商品Sku出错！productId=" + productId, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return productSkuResult;
	}

	@Override
	public ProductResult queryHotSKU(String productId, String skuNum, ProductRetParam param) throws TException {
		ProductResult productResult = new ProductResult();
		Result result = new Result();
		result.setCode(0);
		productResult.setResult(result);
		if (param == null || StringUtil.isNullOrEmpty(productId)) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productResult;
		}
		try {
			param.setSkuTag(0);
			Product product = productSvcImpl.queryProduct(productId, param);
			if (product != null) {
				ProductSku productHotSku = productSvcImpl.getProductHotSku(productId, skuNum);
				if (productHotSku != null) {
					product.setProductSku(productHotSku);
					productResult.setProduct(product);
				} else {
					FailCode.addFails(result, FailCode.PRODUCT_SKU_NULL_ERROR);
				}
			} else {
				FailCode.addFails(result, FailCode.PRODUCT_NULL_ERROR);
			}
		} catch (Exception e) {
			logger.error("查询商品指定sku出错！productId=" + productId + ",skuNum=" + skuNum + ",param=" + param, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return productResult;
	}

	@Override
	public StringResult queryProductDetail(ProductDetailParam param) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		if (param == null || (StringUtil.isNullOrEmpty(param.getDetailKey()) && StringUtil.isNullOrEmpty(param.getProductId()))) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return stringResult;
		}
		try {
             String mongoContent = productSvcImpl.queryProductDetail(param);
			stringResult.setValue(mongoContent);
		} catch (Exception e) {
			logger.error("查询商品详情出错！ProductDetailParam=" + param, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return stringResult;
	}

	@Override
	public StringResult addProduct(Product product) throws TException {
        StringResult stringResult = null;
        String productId = "";
        List<FailDesc> failDescs = ValidateUtil.productBasicValidate(product);
        if(failDescs.size() > 0) {
            logger.error("创建商品失败， 校验未通过");
            return ResultUtil.createNormalFailStringResult(failDescs);
        }

        try {
            productId = productSvcImpl.addProduct(product);
            stringResult = ResultUtil.createNormalStringResult(productId);
        } catch (Exception e) {
            stringResult = ResultUtil.createNormalFailStringResult(FailCode.SYSTEM_EXCEPTION);
            logger.error(FailCode.SYSTEM_EXCEPTION.getDesc(), e);
        }

        return stringResult;
    }

	@Override
	public StringResult updateProduct(Product product) throws TException {
        StringResult stringResult = null;
        String productId = "";
        List<FailDesc> failDescs = ValidateUtil.productBasicValidate(product);
        if(failDescs.size() > 0) {
            logger.error("变更商品失败， 校验未通过");
            return ResultUtil.createNormalFailStringResult(failDescs);
        }
        ProductRetParam param = new ProductRetParam(1,0,0,0);
        Product curTbProduct = productSvcImpl.queryProduct(product.getProductId(), param);
        if(curTbProduct == null) {
            logger.error("变更商品失败，商品不存在");
            return ResultUtil.createNormalFailStringResult(FailCode.PRODUCT_NOT_EXISTS_ERROR);
        } else if(curTbProduct.getActiveState() >= 200) {
            logger.error("变更商品失败， 当前商品状态不允许编辑");
            return ResultUtil.createNormalFailStringResult(FailCode.PRODUCT_NOT_EDITBALE);
        }

        try {
            productId = productSvcImpl.updateProduct(product);
            stringResult = ResultUtil.createNormalStringResult(productId);
        } catch (Exception e) {
            stringResult = ResultUtil.createNormalFailStringResult(FailCode.SYSTEM_EXCEPTION);
            logger.error(FailCode.SYSTEM_EXCEPTION.getDesc(), e);
        }

        return stringResult;
	}

	@Override
	public ProductSurveyResult productSurveyQuery(ProductSurveyQueryParam param)
			throws TException {
		ProductSurveyResult productSurveyResult = new ProductSurveyResult();
		Result result = new Result();
		result.setCode(0);
		productSurveyResult.setResult(result);
		if (param == null || StringUtil.isNullOrEmpty(param.getSellerId())) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productSurveyResult;
		}
		try {
			productSurveyResult = productSvcImpl.productSurveyQuery(param);
			productSurveyResult.setResult(result);
		} catch (Exception e) {
			logger.error("查询商品概要信息列表出错！params=" + param, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		logger.info(productSurveyResult.toString());
		
		return productSurveyResult;
	}

    @Override
    public ProductSurveyResult productSurveyBackendQuery(ProductSurveyQueryParam param) throws TException {
        ProductSurveyResult productSurveyResult = new ProductSurveyResult();
        Result result = new Result();
        result.setCode(0);
        productSurveyResult.setResult(result);
        try {
            productSurveyResult = productSvcImpl.productSurveyQueryFromDB(param);
            productSurveyResult.setResult(result);
        } catch (Exception e) {
            logger.error("查询商品概要信息列表出错！params=" + param, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        logger.info(productSurveyResult.toString());

        return productSurveyResult;
    }

    @Override
    public Result setProductState(ProductOpt productOpt) throws TException {
        if(productOpt == null || StringUtils.isBlank(productOpt.getProductId()) || productOpt.getCurState() <=0 || productOpt.getActiveState() <=0) {
            return ResultUtil.createNormalFailResult(FailCode.PARAM_ERROR);
        }

        Result result = null;
        try {
            FailDesc failDesc = productSvcImpl.updateProductState(productOpt);
            if(failDesc != null) {
                result = ResultUtil.createNormalFailResult(failDesc);
            } else {
                result = ResultUtil.createNormalResult();
            }
        } catch (BaseException e) {
            result = ResultUtil.createNormalFailResult(e.getFailDesc());
            logger.warn(e.getFailDesc().getDesc());
        } catch (Exception e) {
            result = ResultUtil.createNormalFailResult(FailCode.SYSTEM_EXCEPTION);
            logger.error(FailCode.SYSTEM_EXCEPTION.getDesc(), e);
        }

        return result;
    }

    /**
     * 	方法描述:  首页楼层商品查询
     *
     *	@see com.jfshare.finagle.thrift.product.ProductServ.Iface#queryFloorProduct(int)
     *  @author : ljx 创建时间 2015年11月14日 下午12:53:48
     */
	@Override
	public ProductSurveyResult queryFloorProduct(int floorId) throws TException {
		logger.debug("floorId = " + floorId);
		ProductSurveyResult productSurveyResult = new ProductSurveyResult();
		Result result = new Result();
		result.setCode(0);
		productSurveyResult.setResult(result);
		try {
			productSurveyResult = productSvcImpl.queryFloorProduct(floorId);
			productSurveyResult.setResult(result);
		} catch (Exception e) {
			logger.error("首页楼层商品查询！floorId=" + floorId, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		logger.info(productSurveyResult.toString());
		
		return productSurveyResult;
	}

	@Override
	public ProductResult queryProductSnap(String snapId, ProductRetParam param) throws TException {
		ProductResult productResult = new ProductResult();
		Result result = new Result();
		result.setCode(0);
		productResult.setResult(result);
		if (param == null || StringUtil.isNullOrEmpty(snapId)) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productResult;
		}
		try {
			Product product = productSvcImpl.queryProductSnap(snapId, param);
			if (product != null) {
				productResult.setProduct(product);
			} else {
				FailCode.addFails(result, FailCode.PRODUCT_SNAP_NULL_ERROR);
			}
		} catch (Exception e) {
			logger.error("查询商品快照出错！snapId=" + snapId + ",param=" + param, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return productResult;
	}

    @Override
    public ProductSurveyResult productSurveyQueryBatch(ProductSurveyQueryBatchParam param) throws TException {
        ProductSurveyResult productSurveyResult = new ProductSurveyResult();
        Result result = new Result();
        result.setCode(0);
        productSurveyResult.setResult(result);

        List<String> productIds = param.getProductIds();
        if(param.getFromType() <=0 || CollectionUtils.isEmpty(productIds) || productIds.size() > 200) {
            productSurveyResult.result.setCode(1);
            productSurveyResult.result.addToFailDescList(FailCode.PARAM_ERROR);
            return productSurveyResult;
        }

        productSurveyResult.setProductSurveyList(this.productSvcImpl.queryProductBatch(param.getFromType(), productIds));
        return productSurveyResult;
    }
}
