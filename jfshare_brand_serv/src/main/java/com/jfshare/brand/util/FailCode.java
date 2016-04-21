package com.jfshare.brand.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
    public static FailDesc SYSTEM_EXCEPTION;
    public static FailDesc PARAM_ERROR;

    static {
        
        SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
        PARAM_ERROR = new FailDesc("param", "2001", "参数有误");
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
    
}
