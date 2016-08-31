package com.jfshare.message.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
//    public static FailDesc loginNameInvalid;
//    public static FailDesc pwdInvalid;
//    public static FailDesc loginNameNotExist;
//    public static FailDesc loginError;
//    public static FailDesc tryTimeLimitError;
//	public static FailDesc loginNameHasExist;
//	public static FailDesc noOnlineUser;
	
	public static FailDesc requestArgsIsNull;
	public static FailDesc titleInvalid;
	public static FailDesc dateInvalid;
	
	public static FailDesc PUSHARGS_ERROR;

    public static FailDesc SYSTEM_EXCEPTION;
//    public static FailDesc PARAM_ERROR;
//    public static FailDesc THIRDPARTY_BIND_ERROR;

    static {
    	requestArgsIsNull = new FailDesc("requestArgs", "1000", "请求参数错误");
    	PUSHARGS_ERROR=new FailDesc("pushArgsError", "1001", "cannot find user by this audience!");

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
