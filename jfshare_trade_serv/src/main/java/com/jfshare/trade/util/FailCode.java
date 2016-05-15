package com.jfshare.trade.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
    public static FailDesc SYSTEM_EXCEPTION;
    public static FailDesc PARAM_ERROR;

	public static FailDesc USER_ID_ERROR;
	public static FailDesc RECEIVER_ADDRESS_NULL_ERROR;
	public static FailDesc PRODUCT_UNSELECT_ERROR;
	public static FailDesc PRODUCT_FIELD_ERROR;
	public static FailDesc PRODUCT_GET_ERROR;
	public static FailDesc PRODUCT_STATE_ERROR;
	public static FailDesc PRODUCT_BUYLIMIT_ERROR;
	public static FailDesc PRODUCT_BUYLIMIT_HISTORY_ERROR;

	public static FailDesc STOCK_GET_ERROR;
	public static FailDesc STOCK_LACK_ERROR;

	public static FailDesc PAY_PRICE_ERROR;
	public static FailDesc PAY_PRICE_CHANGE_ERROR;

	public static FailDesc ORDER_PERSIS_CALL_ERROR;
	public static FailDesc ORDER_PERSIS_RET_ERROR;


	static {
        SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
        PARAM_ERROR = new FailDesc("param", "2001", "参数有误");

		USER_ID_ERROR = new FailDesc("userId", "3000", "用户ID错误");
		RECEIVER_ADDRESS_NULL_ERROR = new FailDesc("receiverAddress", "3001", "收货地址不能为空");
		PRODUCT_UNSELECT_ERROR = new FailDesc("productUnselect", "3002", "没有选择商品");
		PRODUCT_FIELD_ERROR = new FailDesc("productField", "3003", "商品信息有误");
		PRODUCT_GET_ERROR = new FailDesc("product", "3004", "获取商品信息异常");
		PRODUCT_STATE_ERROR = new FailDesc("activeState", "3005", "商品状态错误");
		PRODUCT_BUYLIMIT_ERROR = new FailDesc("maxBuyLimit", "3006", "商品超过限购数量");
		PRODUCT_BUYLIMIT_HISTORY_ERROR = new FailDesc("maxBuyLimit", "3007", "获取商品历史购买数量错误");

		STOCK_GET_ERROR = new FailDesc("stock", "3008", "锁定库存错误");
		STOCK_LACK_ERROR = new FailDesc("stock", "3009", "库存不足错误");
		PAY_PRICE_ERROR = new FailDesc("amount", "3010", "支付价格错误");
		PAY_PRICE_CHANGE_ERROR = new FailDesc("amount", "3011", "支付价格变化错误");

		ORDER_PERSIS_CALL_ERROR = new FailDesc("order", "3012", "订单入库调用错误");
		ORDER_PERSIS_RET_ERROR = new FailDesc("order", "3013", "订单入库结果错误");
    }
    
    /**
     * 结果集中添加错误码
     * @param result
     * @param failDesc
     */
    public static void addFails(Result result, FailDesc... failDesc) {
		result.setCode(1);
		List<FailDesc> fails = result.getFailDescList();
		if (fails == null) {
		    fails = new ArrayList<FailDesc>();
			result.setFailDescList(fails);
		}
		for (FailDesc item : failDesc) {
			fails.add(item);
		}
	}

	public static void addFails(Result result, List<FailDesc> failDescs) {
		result.setCode(1);
		for (FailDesc item : failDescs) {
			result.addToFailDescList(item);
		}
	}
    
}
