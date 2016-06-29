package com.jfshare.product.server;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.product.*;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.product.exceptions.BaseException;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;
import com.jfshare.product.model.vo.Page;
import com.jfshare.product.service.IProductCartSvc;
import com.jfshare.product.service.IProductSvc;
import com.jfshare.product.util.ConvertUtil;
import com.jfshare.product.util.FailCode;
import com.jfshare.product.util.ResultUtil;
import com.jfshare.product.util.ValidateUtil;
import com.jfshare.utils.DateUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.project.artifact.ProjectArtifactMetadata;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.parallel.ParMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value="handler")
public class ServHandle implements ProductServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);

	@Resource
    private IProductSvc productSvcImpl;

	@Resource
	private IProductCartSvc productCartSvc;

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
		logger.info(">>>> addProduct ---- product : {}", product.toString());
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
		logger.info("<<<< addProduct success ---- productId: {},  product : {}", stringResult.getValue(), product.toString());
        return stringResult;
    }

	@Override
	public StringResult updateProduct(Product product) throws TException {
		logger.info(">>>> updateProduct ---- product : {}", product.toString());
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
		logger.info("<<<< updateProduct success ---- product : {}", product.toString());
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

//		logger.info(productSurveyResult.toString());
		
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

//        logger.info(productSurveyResult.toString());

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

//		logger.info(productSurveyResult.toString());
		
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

	@Override
	public ProductSurveyResult productSurveyQueryByClick(ProductSurveyQueryParam param) throws TException {
		return null;
	}

	@Override
	public ProductResult queryHotSKUV1(ProductSkuParam skuParam, ProductRetParam param) throws TException {
		ProductResult productResult = new ProductResult();
		Result result = new Result();
		result.setCode(0);
		productResult.setResult(result);
		if (param == null || StringUtil.isNullOrEmpty(skuParam.getProductId())) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productResult;
		}
		try {
			param.setSkuTag(0);
			Product product = productSvcImpl.queryProduct(skuParam.getProductId(), param);
			if (product != null) {
				ProductSku productHotSku = productSvcImpl.getProductHotSku(skuParam.getProductId(), skuParam.getStorehouseId(), skuParam.getSkuNum());
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
			logger.error("<<<<<<<< queryHotSKUV1 error !! ---- skuParam : " + skuParam.toString() + ",param=" + param.toString(), e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return productResult;
	}

	@Override
	public ProductBatchResult queryHotSKUBatch(ProductSkuBatchParam skuParam, ProductRetParam param) throws TException {
		ProductBatchResult productBatchResult = new ProductBatchResult();
		Result result = new Result(0);
		productBatchResult.setResult(result);
		if (param == null || CollectionUtils.isEmpty(skuParam.getProductSkuParams())) {
			FailCode.addFails(result, FailCode.PARAM_ERROR);
			return productBatchResult;
		}
		try {
			for (ProductSkuParam productSkuParam : skuParam.getProductSkuParams()) {
				param.setSkuTag(0);
				Product product = productSvcImpl.queryProduct(productSkuParam.getProductId(), param);
				if (product == null) {
					FailCode.addFails(result, FailCode.PRODUCT_NULL_ERROR);
					return productBatchResult;
				}
				ProductSku productHotSku = productSvcImpl.getProductHotSku(productSkuParam.getProductId(), productSkuParam.getStorehouseId(), productSkuParam.getSkuNum());
				if (productHotSku != null) {
					product.setProductSku(productHotSku);
					productBatchResult.addToProductList(product);
				} else {
					FailCode.addFails(result, FailCode.PRODUCT_SKU_NULL_ERROR);
				}
			}
		} catch (Exception e) {
			logger.error("<<<<<<<< queryHotSKUBatch error !! ---- skuParam : " + skuParam.toString() + ",param=" + param.toString(), e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return productBatchResult;
	}

	@Override
	public ProductCardResult getProductCard(ProductCardParam param) throws TException {
		logger.info(">>>> getProductCard --- param : " + param.toString());
		ProductCardResult productCardResult = new ProductCardResult();
		Result result = new Result();
		productCardResult.setResult(result);
		Map queryMap = new HashMap();
		queryMap.put("productId", param.getProductId());
		queryMap.put("transactionId", param.getTransactionId());
		queryMap.put("num", param.getNum());
		queryMap.put("buyerId", param.getBuyerId());

		StringBuffer cards = null;
		List<ProductCard> productCards = new ArrayList<ProductCard>();
		try {
			List<TbProductCard> tbProductCards = this.productCartSvc.getProductCard(queryMap);
			if (CollectionUtils.isEmpty(tbProductCards)) {
                result.setCode(1);
                result.addToFailDescList(FailCode.PRODUCT_CARD_GET_FAIL);
                logger.error("<<<<<<<< getProductCard error !! --- param : " + param.toString());
                return productCardResult;
            }
			cards = new StringBuffer();
			for (TbProductCard tbProductCard : tbProductCards) {
                productCards.add(ConvertUtil.tbProductCard2Thrift(tbProductCard));
                cards.append(" ").append(tbProductCard.getCardNumber());
            }
		} catch (Exception e) {
			logger.error("<<<<<<<< getProductCard error !! --- param : " + param.toString(), e);
		}
		logger.info("<<<< getProductCard ---- success !! param : {} , cards :{}", param.toString(), cards.toString());
		productCardResult.setCardList(productCards);
		return productCardResult;
	}

	@Override
	public ProductCardResult queryProductCard(ProductCardParam param) throws TException {
		ProductCardResult productCardResult = new ProductCardResult();
		Result result = new Result();
		productCardResult.setResult(result);

		if (param.getTransactionId() == null) {
			result.setCode(1);
			result.addToFailDescList(FailCode.PARAM_ERROR);
			return productCardResult;
		}

		Map queryMap = new HashMap();
		queryMap.put("productId", param.getProductId());
		queryMap.put("transactionId", param.getTransactionId());
		try {
			List<TbProductCard> tbProductCards = this.productCartSvc.queryProductCard(queryMap);
			List<ProductCard> productCards = new ArrayList<ProductCard>();
			if (CollectionUtils.isEmpty(tbProductCards)) {
                productCardResult.setCardList(productCards);
                return productCardResult;
            }
			for (TbProductCard tbProductCard : tbProductCards) {
                productCardResult.addToCardList(ConvertUtil.tbProductCard2Thrift(tbProductCard));
            }
		} catch (Exception e) {
			logger.error("<<<<<<<< queryProductCard error !! --- param : " + param.toString(), e);
		}
		return productCardResult;
	}

	@Override
	public ProductCardStatisticsResult statisticsProductCard(ProductCardStatisticsParam param, Pagination pagination) throws TException {

		ProductCardStatisticsResult productCardStatisticsResult = new ProductCardStatisticsResult();
		Result result = new Result();
		productCardStatisticsResult.setResult(result);
		List<ProductCardStatistics> productCardStatisticsList = new ArrayList<ProductCardStatistics>();

		if (param.getSellerId() == 0) {
			result.setCode(1);
			result.addToFailDescList(FailCode.PARAM_ERROR);
			return productCardStatisticsResult;
		}

		Page page = new Page(pagination.getCurrentPage(), pagination.getNumPerPage());

		Map queryMap = new HashMap();
		queryMap.put("sellerId", param.getSellerId());
		queryMap.put("productName", param.getProductName());
		queryMap.put("start", page.getStart());
		queryMap.put("count", page.getCount());

		// 查询总数
		int total = this.productCartSvc.statisticsProductCardCount(queryMap);
		page.setTotal(total);

		// 获取数据
		List<ProductCardStatisticsModel> statisticsModels = this.productCartSvc.statisticsProductCard(queryMap);
		for (ProductCardStatisticsModel statisticsModel : statisticsModels) {
			productCardStatisticsList.add(ConvertUtil.productCardStatisticsModel2Thrift(statisticsModel));
		}

		productCardStatisticsResult.setCardtatisticsList(productCardStatisticsList);
		productCardStatisticsResult.setPagination(ConvertUtil.page2Pagination(page));
		return productCardStatisticsResult;
	}

	@Override
	public ProductCardViewListResult queryProductCardViewList(ProductCardViewParam param, Pagination pagination) throws TException {
		ProductCardViewListResult productCardViewListResult = new ProductCardViewListResult();
		Result result = new Result();
		productCardViewListResult.setResult(result);

		if(param.getSellerId() == 0) {
			result.setCode(1);
			result.addToFailDescList(FailCode.PARAM_ERROR);
			return  productCardViewListResult;
		}

		List<ProductCardView> productCardViews = new ArrayList<ProductCardView>();

		Page page = new Page(pagination.getCurrentPage(), pagination.getNumPerPage());

		Map queryMap = new HashMap();
		queryMap.put("sellerId", param.getSellerId());
		queryMap.put("productId", param.getProductId());
		queryMap.put("cardNumber", param.getCardNumber());
		queryMap.put("state", param.getState());
		queryMap.put("start", page.getStart());
		queryMap.put("count", page.getCount());

		// 查询总数
		int total = this.productCartSvc.queryProductCardViewListCount(queryMap);
		page.setTotal(total);

		List<TbProductCard> tbProductCards = this.productCartSvc.queryProductCardViewList(queryMap);
		for (TbProductCard tbProductCard : tbProductCards) {
			productCardViews.add(ConvertUtil.tbProductCard2ViewThrift(tbProductCard));
		}
		productCardViewListResult.setCardViewList(productCardViews);
		productCardViewListResult.setPagination(ConvertUtil.page2Pagination(page));
		return productCardViewListResult;
	}

	@Override
	public Result importProductCard(ProductCardImportParam param) throws TException {
		Result result = new Result(0);
		try {
			boolean flag = this.productCartSvc.importProductCard(param.getSellerId(), param.getPath());
			if (!flag) {
				result.setCode(1);
				result.addToFailDescList(FailCode.PRODUCT_CARD_IMPORT_FAIL);
				return result;
			}
		} catch (Exception e) {
			logger.error("<<<<<<<< importProductCard error !! ---- param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.PRODUCT_CARD_IMPORT_FAIL);
		}
		return result;
	}

	@Override
	public ProductCardResult useProductCard(ProductCard productCard) throws TException {

		logger.info(">>>> useProductCard --- productCard : {}", productCard.toString());
		ProductCardResult productCardResult = new ProductCardResult();
		Result result = new Result();
		productCardResult.setResult(result);
		try {
			TbProductCard tbProductCard = ConvertUtil.thrift2TbProductCard(productCard);
			TbProductCard dbProductCard = this.productCartSvc.useProductCard(tbProductCard);
			if (dbProductCard == null) {
                result.setCode(1);
                result.addToFailDescList(FailCode.PRODUCT_CARD_USE_FAIL);
                logger.error("<<<<<<<< useProductCard ---- error !! productCard : " + productCard.toString());
            }
			productCardResult.addToCardList(ConvertUtil.tbProductCard2Thrift(dbProductCard));
		} catch (Exception e) {
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
			logger.error("<<<<<<<< useProductCard ---- error !! productCard : " + productCard.toString(), e);
		}
		return productCardResult;
	}

	@Override
	public CaptchaListResult queryCaptchaList(CaptchaQueryParam param) throws TException {

		CaptchaListResult captchaListResult = new CaptchaListResult();
		Result result = new Result();
		captchaListResult.setResult(result);

		/*captchaListResult.setYedNum(88);
		captchaListResult.setMonNum(999);
		captchaListResult.addToItemList(new AldCaptchaItem("123132", "植物大战僵尸", 809, 120));
		captchaListResult.addToItemList(new AldCaptchaItem("3534534", "黑客帝国", 546, 324));
		captchaListResult.addToItemList(new AldCaptchaItem("123132", "星球大战", 555, 123));
		captchaListResult.setPagination(param.getPagination());*/




		Page page = new Page(param.getPagination().getCurrentPage(), param.getPagination().getNumPerPage());

		Map queryMap = new HashMap();
		queryMap.put("sellerId", param.getSellerId());
//		queryMap.put("month", param.getMonthQuery());
//		queryMap.put("productId", param.getProductId());
		queryMap.put("start", page.getStart());
		queryMap.put("count", page.getCount());

		try {
			// 查询总数
			int total = this.productCartSvc.sellerProductCardCount(queryMap);
			page.setTotal(total);

			List<AldCaptchaItem> itemList = this.productCartSvc.sellerProductCardList(queryMap);
			captchaListResult.setItemList(itemList);

			// 昨天验证
			queryMap.put("day", DateUtils.dateToStr(DateUtils.addDay(new Date(), -1)));
			queryMap.put("state", 3);
			captchaListResult.setYedNum(this.productCartSvc.getProductCardCount(queryMap));

			queryMap.remove("day");
			// 本月验证
			queryMap.put("month", DateUtils.date2Str(DateUtils.getNow()).substring(0, 7));
			captchaListResult.setMonNum(this.productCartSvc.getProductCardCount(queryMap));

			captchaListResult.setPagination(ConvertUtil.page2Pagination(page));
		} catch (Exception e) {
			logger.error("<<<<<<<< queryCaptchaList error !! param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		return captchaListResult;
	}

	@Override
	public DayCaptchaListResult queryCaptchaTotalList(CaptchaQueryParam param) throws TException {

		// TODO: 2016/5/30 造数据，后期实现

		DayCaptchaListResult dayCaptchaListResult = new DayCaptchaListResult();
		Result result = new Result();
		dayCaptchaListResult.setResult(result);

		dayCaptchaListResult.setPagination(param.getPagination());

		Page page = new Page(param.getPagination().getCurrentPage(), param.getPagination().getNumPerPage());

		Map queryMap = new HashMap();
		queryMap.put("sellerId", param.getSellerId());
		queryMap.put("month", param.getMonthQuery());
//		queryMap.put("productId", param.getProductId());
		queryMap.put("start", page.getStart());
		queryMap.put("count", page.getCount());
		try {
			// 查询总数
			int total = this.productCartSvc.sellerProductCardDayAllCount(queryMap);
			page.setTotal(total);

			List<DayAldCaptchaCount> dayAldCaptchaCounts = this.productCartSvc.sellerProductCardDayAllList(queryMap);
			dayCaptchaListResult.setDayAldCaptchaCountList(dayAldCaptchaCounts);

			// 当月销售
			queryMap.put("state", 2);
			dayCaptchaListResult.setSoldNum(this.productCartSvc.getProductCardCount(queryMap));

			// 当月验证
			queryMap.put("state", 3);
			dayCaptchaListResult.setCheckedNum(this.productCartSvc.getProductCardCount(queryMap));

			dayCaptchaListResult.setPagination(ConvertUtil.page2Pagination(page));
		} catch (Exception e) {
			logger.error("<<<<<<<< queryCaptchaTotalList error !! param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		return dayCaptchaListResult;
	}

	@Override
	public DayCaptchaProductResult queryCaptchaDayTotalList(CaptchaDayQueryParam param) throws TException {

		DayCaptchaProductResult dayCaptchaProductResult = new DayCaptchaProductResult();
		Result result = new Result();
		dayCaptchaProductResult.setResult(result);

		dayCaptchaProductResult.setPagination(param.getPagination());
		Page page = new Page(param.getPagination().getCurrentPage(), param.getPagination().getNumPerPage());

		Map queryMap = new HashMap();
		queryMap.put("sellerId", param.getSellerId());
		queryMap.put("day", param.getDate());
		queryMap.put("start", page.getStart());
		queryMap.put("count", page.getCount());
		try {
			// 查询总数
			int total = this.productCartSvc.sellerProductCardDayCount(queryMap);
			page.setTotal(total);

			List<DayAldCaptchaItem> dayAldCaptchaItems = this.productCartSvc.sellerProductCardDayList(queryMap);
			dayCaptchaProductResult.setItemList(dayAldCaptchaItems);
			dayCaptchaProductResult.setPagination(ConvertUtil.page2Pagination(page));

		} catch (Exception e) {
			logger.error("<<<<<<<< queryCaptchaDayTotalList error !! param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		return dayCaptchaProductResult;
	}

	@Override
	public CaptchaDetailResult queryCaptchaDetails(CaptchaQueryParam param) throws TException {

		CaptchaDetailResult captchaDetailResult = new CaptchaDetailResult();
		Result result = new Result();
		captchaDetailResult.setResult(result);

		Page page = new Page(param.getPagination().getCurrentPage(), param.getPagination().getNumPerPage());

		Map queryMap = new HashMap();
		queryMap.put("sellerId", param.getSellerId());
		queryMap.put("productId", param.getProductId());
		queryMap.put("month", param.getMonthQuery());
		queryMap.put("start", page.getStart());
		queryMap.put("count", page.getCount());

		try {
			// 查询总数
			int total = this.productCartSvc.queryProductCardCheckCount(queryMap);
			page.setTotal(total);

			List<TbProductCard> productCardList = this.productCartSvc.queryProductCardCheckList(queryMap);
			for (TbProductCard tbProductCard : productCardList) {
                captchaDetailResult.addToProductCards(ConvertUtil.tbProductCard2Thrift(tbProductCard));
            }

			captchaDetailResult.setPagination(ConvertUtil.page2Pagination(page));
		} catch (Exception e) {
			logger.error("<<<<<<<< queryCaptchaDetails error !! param : " + param.toString(), e);
			result.setCode(1);
			result.addToFailDescList(FailCode.SYSTEM_EXCEPTION);
		}
		return captchaDetailResult;
	}
}
