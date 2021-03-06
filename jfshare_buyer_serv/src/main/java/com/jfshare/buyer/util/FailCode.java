package com.jfshare.buyer.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

public class FailCode {
    public static FailDesc loginNameInvalid;
    public static FailDesc pwdInvalid;
    public static FailDesc loginNameNotExist;
    public static FailDesc loginError;
    public static FailDesc tryTimeLimitError;
	public static FailDesc loginNameHasExist;
	public static FailDesc noOnlineUser;

    public static FailDesc SYSTEM_EXCEPTION;
    public static FailDesc PARAM_ERROR;
    public static FailDesc THIRDPARTY_BIND_ERROR;
    
    public static FailDesc VALIDATE_NUM_ERROR;
    public static FailDesc TOKEN_NUM_ERROR;
    public static FailDesc ADDSYSTEM_ERROR;
    public static FailDesc EREADLYBIND_ERROR;
    public static FailDesc NOTNULL_ERROR;
    public static FailDesc repquestErrorNotSingkey;
    public static FailDesc repquestErrorSign;
    public static FailDesc isNotMobile;
    static {
    	loginNameInvalid = new FailDesc("longName", "1000", "登录名不合法");
        pwdInvalid = new FailDesc("password", "1001", "密码不合法");
    	loginNameNotExist = new FailDesc("longName", "1003", "用户名不存在");
    	loginError = new FailDesc("login", "1004", "用户名或密码错误");
    	tryTimeLimitError = new FailDesc("login", "1005", "超过最大尝试次数");
		loginNameHasExist = new FailDesc("longName", "1006", "用户名已存在");
		noOnlineUser = new FailDesc("tokenId", "1007", "在线用户不存在");
        THIRDPARTY_BIND_ERROR = new FailDesc("thirdParty", "1008", "绑定账号与登录账号不一致");
        SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
        PARAM_ERROR = new FailDesc("param", "2001", "参数有误");
        
        VALIDATE_NUM_ERROR = new FailDesc("validateNum", "3001", "短信验证码有误");
        TOKEN_NUM_ERROR = new FailDesc("tokenNum", "3002", "验证参数错误");
        ADDSYSTEM_ERROR = new FailDesc("addSystem", "3003", "数据写入错误");
        EREADLYBIND_ERROR = new FailDesc("eReadlyBind", "3004", "该手机号已绑定其他微信号");
        
        
        NOTNULL_ERROR = new FailDesc("param", "2001", "参数不能为空");
        repquestErrorNotSingkey = new FailDesc("repquestErrorNotSingkey", "1001", "无权限访问此接口");
        repquestErrorSign = new FailDesc("repquestErrorSign", "1001", "数字签名验证失败");
        isNotMobile   = new FailDesc("isNotMobile", "1005", "手机号验证失败");

        
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
