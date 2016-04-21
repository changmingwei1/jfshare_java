package com.jfshare.cart.util;

import com.jfshare.finagle.thrift.cart.*;
import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;

import java.util.ArrayList;
import java.util.List;

public class ResultGenerator {

	public static int SUCCESS_CODE = 0;

	public static int FAIL_CODE = 1;
	
	public static String SYSTEM_ERROR = "1001";

	public static String PARAM_ERROR = "4444";

	public static String CART_STOCK_LACK = "2001";

	public static String CART_MAX_COUNT_ERROR = "2002";

	public static String CART_QUANTITY_ERROR = "2003";

	public static String CART_PRODUCT_ERROR = "2004";

	public static String CART_DATA_ERROR = "2005";

	public static String CART_PRODUCT_QUANTITY_ERROR = "2006";

	public static String PRODUCT_CONNECT_ERROR = "3001";

	public static String PRODUCT_DATA_ERROR = "3002";

	public static String DATA_TIME_OUT = "3003";
	
	public static String CART_PRODUCT_INFO_ERROR = "3004";
	
	

	public static FailDesc getFailDesc(String failCode) {
		FailDesc failDesc = new FailDesc();
		failDesc.setName("cart");
		failDesc.setFailCode(failCode);

		if (PARAM_ERROR.equals(failCode))
			failDesc.setDesc("参数错误");
		else if (CART_STOCK_LACK.equals(failCode))
			failDesc.setDesc("库存不足");
		else if (CART_MAX_COUNT_ERROR.equals(failCode))
			failDesc.setDesc("超出购物车商品的最大值");
		else if (PRODUCT_CONNECT_ERROR.equals(failCode))
			failDesc.setDesc("连接产品服务失败");
		else if (PRODUCT_DATA_ERROR.equals(failCode))
			failDesc.setDesc("商品服务获取库存、及购买信息为空");
		else if (CART_QUANTITY_ERROR.equals(failCode))
			failDesc.setDesc("超出商品的限购数量");
		else if (DATA_TIME_OUT.equals(failCode))
			failDesc.setDesc("读取数据超时");
		else if (CART_DATA_ERROR.equals(failCode))
			failDesc.setDesc("购物车数据异常");
		else if (CART_PRODUCT_QUANTITY_ERROR.equals(failCode))
			failDesc.setDesc("购物车商品数据有误");
		else if (CART_PRODUCT_INFO_ERROR.equals(failCode))
			failDesc.setDesc("商品信息有误");
		else if (SYSTEM_ERROR.equals(failCode))
			failDesc.setDesc("系统异常");
		else 
			failDesc.setDesc("未知错误");

		return failDesc;
	}

	public static Result createCorrectResult() {
		Result result = new Result();
		result.setCode(SUCCESS_CODE);
		return result;
	}

	public static Result createErrorResult(String failCode) {
		Result result = new Result();
		result.setCode(FAIL_CODE);
		result.setFailDescList(getFailList(failCode));
		return result;
	}

	public static StringResult createStringResult(int code, String failCode, String value) {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(code);
		if (code == FAIL_CODE) {
			result.setFailDescList(getFailList(failCode));
		}

		stringResult.setValue(value);
		stringResult.setResult(result);
		return stringResult;
	}

	public static CartResult createCartResult(int code, String failCode, List<sellerItemDetail> itemList, List<ItemDetail> invalidItemList) {
		CartResult cartResult = new CartResult();
		Result result = new Result();
		result.setCode(code);
		if (code == SUCCESS_CODE) {
			cartResult.setItemList(itemList);
			cartResult.setInvalidItemList(invalidItemList);
		} else {
			result.setFailDescList(getFailList(failCode));
		}

		cartResult.setResult(result);
		return cartResult;
	}
	
	public static MiniCartResult createMiniCartResult(int code, String failCode, List<ItemPlus> pList, int validCount, int invalidCount) {
		MiniCartResult m = new MiniCartResult();
		Result result = new Result();
		result.setCode(code);
		if (code == SUCCESS_CODE) {
			m.setPList(pList);
			m.setValidCount(validCount);
			m.setInvalidCount(invalidCount);
		} else {
			result.setFailDescList(getFailList(failCode));
		}
		m.setResult(result);
		return m;
	}

	public static List<FailDesc> getFailList(String failCode) {
		if (StringUtil.empty(failCode))
			return null;
		List<FailDesc> failDescList = new ArrayList<FailDesc>();
		failDescList.add(getFailDesc(failCode));
		return failDescList;
	}

	public static CheckItemInfo createCheckItemInfo(String productId, String skuNum, int skuCount, int currQuantity, int maxBuyLimit,
			int currBuy, boolean isOutOfGauge, boolean isOutOfStock) {
		if (StringUtil.empty(productId))
			return null;

		CheckItemInfo r = new CheckItemInfo();
		r.setProductId(productId);
		r.setSkuNum(skuNum);
		r.setSkuCount(skuCount);
		r.setCurrBuy(currBuy);
		r.setCurrQuantity(currQuantity);
		r.setMaxBuyLimit(maxBuyLimit);
		r.setIsOutOfGauge(isOutOfGauge);
		r.setIsOutOfStock(isOutOfStock);
		return r;
	}

	public static CheckCartResult creatCheckCartResult(int code, String failCode, List<CheckItemInfo> list) {
		CheckCartResult c = new CheckCartResult();
		Result result = new Result();
		result.setCode(code);
		if (code != SUCCESS_CODE) {
			result.setFailDescList(getFailList(failCode));
		}
		if(list != null && list.size() > 0)
			c.setCheckList(list);	//针对更新方法，超过限购，依然可以保持
		
		c.setResult(result);
		return c;
	}

	public static CheckCartResult creatCheckCartResult(int code, String failCode, List<CheckItemInfo> list, String value) {
		CheckCartResult c = creatCheckCartResult(code, failCode, list);
		if ((c != null) && (c.getResult() != null))
			c.setValue(value);
		return c;
	}

}
