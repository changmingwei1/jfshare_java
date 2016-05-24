package com.jfshare.order.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
	
	//公共错误代码
	public static final FailDesc SYS_ERROR = 			    new FailDesc("SYS_ERROR", "1000", "系统异常");

	public static final FailDesc EntryIsNull = 		        new FailDesc("DATA_ERROR", "2000", "带转换实体为null");
	public static final FailDesc PARAM_ERROR = 		    new FailDesc("DATA_ERROR", "2001", "接口参数非法");
	public static final FailDesc USER_ID_ERROR =            new FailDesc("DATA_ERROR", "1025", "用户id错误");
	public static final FailDesc OUT_MAX_QUERY_COUNT =      new FailDesc("DATA_ERROR", "1026", "分页查询数最多不能大于50");
	public static final FailDesc ORDERID_NULL = 			new FailDesc("DATA_ERROR", "2033", "订单ID不能为空");

	public static final FailDesc ORDER_INSERT_ERROR = 			new FailDesc("ORDER_INSERT_ERROR", "2034", "订单入库失败");
	public static final FailDesc ORDER_UPDATE_ERROR = 			new FailDesc("ORDER_UPDATE_ERROR", "2035", "订单表更新失败");
	public static final FailDesc ORDER_NO_EXIST = 			new FailDesc("ORDER_NO_ERROR", "2036", "订单不存在");
	public static final FailDesc RECEIPT_FAIL = 			new FailDesc("ORDER_RECEIPT_ERROR", "2037", "订单确认收货失败");
	public static final FailDesc CANCEL_FAIL = 			new FailDesc("ORDER_CANCEL_ERROR", "2038", "订单关闭失败");
	public static FailDesc ORDER_INFO_ERROR  = new FailDesc("order", "3014", "获取订单信息错误");
	public static FailDesc PAY_ORDER_CLOSE  = new FailDesc("pay", "3015", "订单已经关闭，无法支付");
	public static FailDesc PAY_ORDER_PAY_FINISH  = new FailDesc("pay", "3016", "订单已经完成支付，无法支付");
	public static FailDesc PAY_CHANNEL_NULL  = new FailDesc("payChannel", "3017", "支付渠道不能为空");
	public static FailDesc PAY_SYS_FAIL  = new FailDesc("payUrl", "3018", "支付平台返回错误");
	public static final FailDesc TOKEN_ID_AUTH_FAIL =  new FailDesc("tokenId", "3019", "支付令牌认证失败");
	public static final FailDesc SIGN_AUTH_FAIL =  new FailDesc("sign", "3020", "支付签名认证失败");
	public static final FailDesc EXTRA_PARAM＿FAIL =  new FailDesc("extraParam", "3021", "支付订单参数验证失败");
	public static final FailDesc THIRD_PRICE＿FAIL =  new FailDesc("thirdPrice", "3022", "支付通知金额不符");
	public static final FailDesc THIRD_SCORE＿FAIL =  new FailDesc("thirdScore", "3023", "支付通知积分不符");
	public static final FailDesc PAY_STATE_GET_FAIL = new FailDesc("paystate", "3024", "支付状态获取失败");
	public static final FailDesc PAY_SCORE_CHECK_FAIL = new FailDesc("payscore", "3025", "积分校验失败");
	public static final FailDesc PAY_SCORE_REDUCE_FAIL = new FailDesc("payscore", "3026", "扣减积分失败");

	public static final FailDesc PAY_EXCHANGE_AMOUNT_UNEQUAL = new FailDesc("payscore", "3027", "抵现积分与金额不等");
	public static final FailDesc DELIVER_GET_PRODUCTCARD_FAIL = new FailDesc("DELIVER", "4001", "获取虚拟商品卡密/卡券失败");

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
		List<FailDesc> fails = result.getFailDescList();
		if (fails == null) {
			fails = new ArrayList<FailDesc>();
			result.setFailDescList(fails);
		}
		for (FailDesc item : failDescs) {
			fails.add(item);
		}
	}
}
