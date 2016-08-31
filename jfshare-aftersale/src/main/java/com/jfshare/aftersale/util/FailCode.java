package com.jfshare.aftersale.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {

	public static FailDesc requestArgsIsNull;
	public static FailDesc dbOptionFailed;
	
    public static FailDesc SYSTEM_EXCEPTION;

    static {
    	requestArgsIsNull = new FailDesc("requestArgs", "1000", "请求参数错误");
    	dbOptionFailed = new FailDesc("dbOptionFailed", "1001", "数据库操作失败");

        SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
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
