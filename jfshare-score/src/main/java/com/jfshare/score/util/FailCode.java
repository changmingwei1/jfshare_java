package com.jfshare.score.util;

import java.util.ArrayList;
import java.util.List;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;


public class FailCode {
	public static FailDesc requestArgsIsNull;
	public static FailDesc dbOptionFailed;
	public static FailDesc scoreNotEnough;
	public static FailDesc scoreToolarge;
	public static FailDesc scoreToolargeMobile;
	public static FailDesc isNotMobile;
	
	public static FailDesc dbUpdateFailed;
    public static FailDesc SYSTEM_EXCEPTION;
    public static FailDesc repquestParamError;
    public static FailDesc repquestSuccess;
    public static FailDesc repquestError;
    
    public static FailDesc responseError;
    public static FailDesc repquestErrorSign;
    
    //兑入积分请求参数验证
    public static FailDesc requestAppCodeNull ;
    public static FailDesc requestRequestDateNull;
    public static FailDesc requestSignNull;
    public static FailDesc requestSpIDNull;
    public static FailDesc requestOutOrderIDNull;
    public static FailDesc requestDeviceNoNull;
    public static FailDesc requestDeviceTypeNull;
    public static FailDesc requestProvinceIDNull;
    public static FailDesc requestNumNull;
    public static FailDesc requestRemarkNull;
    public static FailDesc requestExpTimeNull;
    public static FailDesc requestCompareNull;
    public static FailDesc requestExpTimeMatches;
    public static FailDesc repquestPostUrl;
    public static FailDesc requestRepeact;
    public static FailDesc  repquestErrorNotSingkey;
    public static FailDesc repquestRelaAccountError;
    public static FailDesc repquestRelaAccountRepeatError;
    static {
    	requestRepeact = new  FailDesc("requestRepeact", "1000", "交易请求重复");
    	requestArgsIsNull = new FailDesc("requestArgsIsNull", "1000", "请求参数错误");
    	dbOptionFailed = new FailDesc("dbOptionFailed", "1001", "数据库查询失败");
    	scoreNotEnough = new FailDesc("scoreNotEnough", "1002", "用户积分不足");
    	scoreToolarge   = new FailDesc("scoreToolarge", "1003", "当日兑换积分已超出限额50000");
    	scoreToolargeMobile   = new FailDesc("scoreToolargeMobile", "1004", "当日对入的手机号积分已超出限额50000");
    	isNotMobile   = new FailDesc("isNotMobile", "1005", "手机号验证失败");
    	dbUpdateFailed = new FailDesc("dbUpdateFailed", "1006", "数据库更新异常");
        SYSTEM_EXCEPTION = new FailDesc("system", "2000", "系统异常");
        repquestSuccess = new FailDesc("repquestSuccess", "0000", "积分发行成功");
        repquestError = new FailDesc("repquestError", "1001", "兑入积分失败");
        responseError = new FailDesc("responseError", "1008", "兑出积分接口调用异常");
        repquestErrorSign = new FailDesc("repquestErrorSign", "1001", "数字签名验证失败");
        repquestParamError = new FailDesc("repquestParamError", "0001", "请求参数异常");
        repquestPostUrl = new FailDesc("repquestPostUrl", "0001", "兑出地址为空");
        //兑入积分请求参数验证非空
        requestAppCodeNull = new FailDesc("requestAppCodeNull", "0001", "应用编码不能为空");
        requestRequestDateNull = new FailDesc("requestRequestDateNull", "0001", "请求时间不能为空");
        requestSignNull = new FailDesc("requestSignNull", "0001", "签名不能为空");
        requestSpIDNull = new FailDesc("requestSpIDNull", "0001", "业务编号不能为空");
        requestOutOrderIDNull = new FailDesc("requestOutOrderIDNull", "0001", "外部订单号不能为空");
        requestDeviceNoNull = new FailDesc("requestAppCodeNull", "0001", "设备号不能为空");
        requestDeviceTypeNull = new FailDesc("requestDeviceTypeNull", "0001", "省编号不能为空");
        requestProvinceIDNull = new FailDesc("requestProvinceIDNull", "0001", "应用编码不能为空");
        requestNumNull = new FailDesc("requestNumNull", "0001", "发行积分数不能为空");
        requestRemarkNull = new FailDesc("requestRemarkNull", "0001", "备注不能为空");
        requestExpTimeNull = new FailDesc("requestExpTimeNull", "0001", "过期时间不能为空");
        requestCompareNull = new FailDesc("requestCompareNull", "0001", "过期不能小于请求时间");
        requestExpTimeMatches = new FailDesc("requestExpTimeMatches", "0001", "请输入正确的过期日期格式  YYYY-MM-DD");
        repquestErrorNotSingkey = new FailDesc("repquestErrorNotSingkey", "1001", "无权限访问此接口");
        //绑定账号接口异常
        repquestRelaAccountError = new FailDesc("repquestRelaAccountError", "1001", "绑定账号失败，请确定设备号是否成功兑入过积分");
        repquestRelaAccountRepeatError = new FailDesc("repquestRelaAccountRepeatError", "1001", "绑定账号失败，重复绑定");
        


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
