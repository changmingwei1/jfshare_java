package com.jfshare.pay.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
	
	//公共错误代码
	public static final FailDesc SYS_ERROR = 			    new FailDesc("SYS_ERROR", "1000", "系统异常");
	public static final FailDesc PARAM_ERROR = 		    new FailDesc("DATA_ERROR", "2001", "接口参数非法");
	public static final FailDesc PARAM_NOT_VALID = 		    new FailDesc("DATA_ERROR", "2002", "接口参数有误");
	public static final FailDesc REQ_DB_ERROR = 		    new FailDesc("DB_ERROR", "2003", "申请支付入库失败");
	public static final FailDesc RES_DB_ERROR = 		    new FailDesc("DB_ERROR", "2004", "支付结果更新库失败");
	public static final FailDesc RES_PARSE_ERROR = 		    new FailDesc("PAY_URL_PARSE_ERROR", "2005", "解析支付结果失败");
	public static final FailDesc RES_NO_ERROR = 		    new FailDesc("PAY_ID_NO_ERROR", "2006", "查询不到支付交易号");
	public static final FailDesc TOKEN_ID_AUTH_FAIL = 		    new FailDesc("TOKEN_ID", "2007", "支付令牌认证失败");
	public static final FailDesc REQ_CHANGE_ERROR = 		    new FailDesc("THIRD_REQ", "2008", "申请支付链接已改变");


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
