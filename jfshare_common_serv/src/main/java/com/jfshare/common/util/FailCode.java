package com.jfshare.common.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;

import java.util.ArrayList;
import java.util.List;

/*
 * 错误编码的处理
 */
public class FailCode {

    public static FailDesc DATA_NO_EXIST;
    public static FailDesc SYSTEM_EXCEPTION;
    public static FailDesc PARAM_EXCEPTION;
    public static FailDesc CAPTCHA_VALIDATE_FAILURE;
    public static FailDesc CAPTCHA_EXPIRED;
    public static FailDesc CAPTCHA_CREATE_TIME_LIMIT;
    public static FailDesc SEND_MSG_FAILURE;

    static {
        DATA_NO_EXIST = new FailDesc("data", "20007", "没有查到数据记录");
        SYSTEM_EXCEPTION = new FailDesc("system", "20009", "系统异常");
        PARAM_EXCEPTION = new FailDesc("system", "20008", "参数错误");
        CAPTCHA_VALIDATE_FAILURE = new FailDesc("captcha", "20010", "验证码错误");
        CAPTCHA_EXPIRED = new FailDesc("captcha", "20011", "验证码已过期");
        CAPTCHA_CREATE_TIME_LIMIT = new FailDesc("captcha", "20012", "验证码申请间隔太短");
        SEND_MSG_FAILURE = new FailDesc("message", "30000", "调用短信平台发送验证码未成功");
    }
    public static List<FailDesc> getFailDescList(FailDesc failDesc, List<FailDesc> failDescList){
        //当错误描述集合为null时，创建一个错误描述集合
        if(failDescList == null)  {
            failDescList = new  ArrayList<FailDesc>();
        }
        failDescList.add(failDesc);
        return failDescList;
    }
    
    public static Result createErrorResult(FailDesc failDesc){
        Result result = new Result();
        result.setCode(1);
        result.setFailDescList(getFailDescList(failDesc, null));
        return result;
    }
}
