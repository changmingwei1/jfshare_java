package com.jfshare.score2cash.utils;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.trade.ExchangeDetail;
import com.jfshare.finagle.thrift.trade.ExchangeResult;
import com.jfshare.finagle.thrift.trade.ExchangeRule;
import com.jfshare.finagle.thrift.trade.ExchangeRuleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author CUIJIANBIN
 * 
 */
public class ResultGenerator {

	public static int SUCCESS_CODE = 0;

	public static int FAIL_CODE = 1;

	public static String PARAM_ERROR = "4444";
	
	public static String SCORE_SERVER_ERROR = "1001";
	
	public static String SCORE_QUANTITY_ERROR = "1002";
	
	public static String SCORE_QUANTITY_PARAM_ERROR = "1003";
	
	public static String SCORE_QUANTITY_IS_ZERO = "1004";
	
	public static String SCORE_RULE_CHANGE = "1005";

	public static String NOT_TELCHARGE_EXCHANGE_PERIOD = "1006";

	public static String EXCEED_EXCHANGE_CASH_LIMIT = "1007";

	public static FailDesc getFailDesc(String failCode) {
		FailDesc failDesc = new FailDesc();
		failDesc.setName("score2cash");
		failDesc.setFailCode(failCode);

		if (PARAM_ERROR.equals(failCode))
			failDesc.setDesc("参数错误");
		else if (SCORE_SERVER_ERROR.equals(failCode))
			failDesc.setDesc("积分服务异常");
		else if (SCORE_QUANTITY_ERROR.equals(failCode))
			failDesc.setDesc("积分数量不足");
		else if (SCORE_QUANTITY_PARAM_ERROR.equals(failCode))
			failDesc.setDesc("积分数量大于可用积分");
		else if (SCORE_QUANTITY_IS_ZERO.equals(failCode))
			failDesc.setDesc("积分数量为0");
		else if (NOT_TELCHARGE_EXCHANGE_PERIOD.equals(failCode))
			failDesc.setDesc("不在充值卡积分兑换活动期间");
		else if (EXCEED_EXCHANGE_CASH_LIMIT.equals(failCode))
			failDesc.setDesc("超出积分可兑换现金限额");
		else {
			failDesc.setDesc("未知错误");
		}
		
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

	public static List<FailDesc> getFailList(String failCode) {
		if (StringUtil.empty(failCode))
			return null;
		List<FailDesc> failDescList = new ArrayList<FailDesc>();
		failDescList.add(getFailDesc(failCode));
		return failDescList;
	}
	
	public static ExchangeResult createExchangeResult(String totalScore, String exchangeScore, String amount, List<ExchangeDetail> list) {
		ExchangeResult e = new ExchangeResult();

		Result result = new Result();
		result.setCode(SUCCESS_CODE);
		e.setAmount(amount);
		e.setExchangeScore(exchangeScore);
		e.setTotalScore(totalScore);
		if (list != null && list.size() > 0)
			e.setExchangeDetailList(list);
		e.setResult(result);
		return e;
	}
	
	public static ExchangeResult createErrorExchangeResult(String failCode) {
		ExchangeResult e = new ExchangeResult();
		Result result = new Result();
		result.setCode(FAIL_CODE);
		result.setFailDescList(getFailList(failCode));
		e.setResult(result);
		return e;
	}
	
	public static ExchangeRuleResult createExchangeRuleResult(int code, String failCode, ExchangeRule rule){
		ExchangeRuleResult e = new ExchangeRuleResult();
		Result result = new Result();
		result.setCode(code);
		if (code != SUCCESS_CODE) {
			result.setFailDescList(getFailList(failCode));
		}
		if(rule != null)
			e.setExchangeRule(rule);
		e.setResult(result);
		return e;
	}

}
