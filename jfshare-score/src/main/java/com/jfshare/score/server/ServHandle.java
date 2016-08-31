package com.jfshare.score.server;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.CachAmountCallParam;
import com.jfshare.finagle.thrift.score.CachAmountQueryParam;
import com.jfshare.finagle.thrift.score.CachAmountResult;
import com.jfshare.finagle.thrift.score.RelaAccountRequestParam;
import com.jfshare.finagle.thrift.score.ResponseCachResult;
import com.jfshare.finagle.thrift.score.ResponseRelaAcoountResult;
import com.jfshare.finagle.thrift.score.ResponseScore;
import com.jfshare.finagle.thrift.score.ResponseScoreResult;
import com.jfshare.finagle.thrift.score.ScoreAccountResult;
import com.jfshare.finagle.thrift.score.ScoreRequestParam;
import com.jfshare.finagle.thrift.score.ScoreResult;
import com.jfshare.finagle.thrift.score.ScoreServ.Iface;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreTradeQueryParam;
import com.jfshare.finagle.thrift.score.ScoreTradeResult;
import com.jfshare.finagle.thrift.score.ScoreUserQueryParam;
import com.jfshare.finagle.thrift.score.ScoreUserResult;
import com.jfshare.score.dao.redis.IScoreRedis;
import com.jfshare.score.service.IScoreServ;
import com.jfshare.score.util.Constants;
import com.jfshare.score.util.FailCode;
import com.jfshare.utils.StringUtil;

@Service(value = "handler")
public class ServHandle implements Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);

	@Autowired
	private IScoreServ scoreServImpl;

	@Autowired
	private IScoreRedis ir;
	@Override
	public StringResult income(ScoreTrade scoreTrade) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);

		try {
			logger.info("$$$$收入积分---请求参数：" + scoreTrade != null ? scoreTrade.toString() : null);
			if (StringUtil.isNullOrEmpty(scoreTrade) || scoreTrade.getUserId() <= 0 || scoreTrade.getInOrOut() != 1
					|| scoreTrade.getAmount() <= 0) {
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return stringResult;
			}

			scoreServImpl.income(stringResult, scoreTrade);
		} catch (Exception ex) {
			logger.error("$$$$收入积分---收入积分请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			return stringResult;
		}

		return stringResult;
	}

	@Override
	public StringResult expenditure(ScoreTrade scoreTrade) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);

		try {
			logger.info("$$$$支出积分---请求参数：" + scoreTrade != null ? scoreTrade.toString() : null);
			if (StringUtil.isNullOrEmpty(scoreTrade) || scoreTrade.getUserId() <= 0 || scoreTrade.getInOrOut() != 2
					|| scoreTrade.getAmount() <= 0) {
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return stringResult;
			}

			scoreServImpl.expenditure(stringResult, scoreTrade);
		} catch (Exception ex) {
			logger.error("$$$$支出积分---支出积分请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			return stringResult;
		}

		return stringResult;
	}

	@Override
	public ScoreResult getScore(int userId) throws TException {
		ScoreResult scoreResult = new ScoreResult();
		Result result = new Result();
		result.setCode(0);
		scoreResult.setResult(result);
		try{
			logger.info("$$$$获取用户积分---请求参数：userId:" + userId);
			if(userId <= 0){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return scoreResult;
			}
			
			scoreServImpl.getScore(scoreResult, userId);
		} catch(Exception ex){
			logger.error("$$$$获取用户积分---获取用户积分请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		
		return scoreResult;
	}

	@Override
	public ScoreTradeResult queryScoreTrade(ScoreTradeQueryParam param, Pagination pagination) throws TException {
		ScoreTradeResult scoreTradeResult = new ScoreTradeResult();
		Result result = new Result();
		result.setCode(0);
		scoreTradeResult.setResult(result);
		try{
			logger.info(String.format("$$$$获取用户交易积分明细---请求参数：param:%s;; page:%s", 
					param != null ? param.toString() : null, pagination != null ? pagination.toString(): null));
			if(StringUtil.isNullOrEmpty(param) || param.getUserId() <= 0){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
				return scoreTradeResult;
			}
			
			scoreServImpl.queryScoreTrade(scoreTradeResult, param, pagination);
		} catch (Exception ex) {
			logger.error("$$$$获取用户交易积分列表---获取用户交易积分明细请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return scoreTradeResult;
	}

	//查询积分列表
	@Override
	public ScoreUserResult queryScoreUser(ScoreUserQueryParam param, Pagination pagination) throws TException {
		ScoreUserResult scoreUserResult = new ScoreUserResult();
		Result result = new Result();
		result.setCode(0);
		scoreUserResult.setResult(result);
		try{
			logger.info(String.format("$$$$获取用户积分列表---请求参数：param:%s;; page:%s", 
					param != null ? param.toString() : null, pagination != null ? pagination.toString(): null));
//			if(StringUtil.isNullOrEmpty(param)){
//				FailCode.addFails(result, FailCode.requestArgsIsNull);
//				return scoreUserResult;
//			}
			
			scoreServImpl.queryScoreUser(scoreUserResult, param, pagination);
		} catch (Exception ex) {
			logger.error("$$$$获取用户交易积分列表---获取用户积分列表请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return scoreUserResult;
	}
	
		//兑出积分查询返回结果
		@Override
		public CachAmountResult queryCachAmount(CachAmountQueryParam param) throws TException {
			//String postUrl = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_cach_posturl", "0");
			CachAmountResult cachAmountResult = new CachAmountResult();
			Result result = new Result();
			result.setCode(0);
			cachAmountResult.setResult(result);
			try{
				logger.info(String.format("$$$$兑换积分查询---请求参数：param:%s", param != null ? param.toString() : null));
				if(StringUtil.isNullOrEmpty(param)){
					FailCode.addFails(result, FailCode.requestArgsIsNull);
					return cachAmountResult;
				}
				
				scoreServImpl.queryCachAmount(cachAmountResult, param);
			} catch (Exception ex) {
				logger.error("$$$$兑换积分查询---兑换积分查询请求失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return cachAmountResult;
		}
		
		/* 兑出积分调用接口*/
		@Override
		public ResponseCachResult cachAmountCall(CachAmountCallParam param) throws TException {
			ResponseCachResult responseCachResult = new ResponseCachResult();
			Result result = new Result();
			result.setCode(0);
			responseCachResult.setResult(result);
			try{
				logger.info(String.format("$$$$兑换积分查询---请求参数：param:%s", param != null ? param.toString() : null));
				if(StringUtil.isNullOrEmpty(param) || StringUtil.isNullOrEmpty(param.getMobile()) ){
					FailCode.addFails(result, FailCode.requestArgsIsNull);
					return responseCachResult;
				}
				scoreServImpl.cachAmountCall(responseCachResult, param);
				
			} catch (Exception ex) {
				logger.error("$$$$兑换积分查询---兑换积分查询请求失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return responseCachResult;
		}
		/* 兑入积分调用接口*/
		@Override
		public ResponseScoreResult enterAmountCall(ScoreRequestParam param) throws TException {
			ResponseScoreResult responseScoreResult = new ResponseScoreResult();
			ResponseScore responseScore = new ResponseScore();
			Result result = new Result();
			result.setCode(0);
			responseScoreResult.setResult(result);
			try{
				logger.info(String.format("$$$$电信兑入积分---请求参数：param:%s", param != null ? param.toString() : null));
				if(StringUtil.isNullOrEmpty(param.getAppCode())){//应用编码
					FailCode.addFails(result, FailCode.requestAppCodeNull);
					responseScore.setErrCode(FailCode.requestAppCodeNull.failCode);
					responseScore.setErrMsg(FailCode.requestAppCodeNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getRequestDate())){//请求时间
					FailCode.addFails(result, FailCode.requestRequestDateNull);
					responseScore.setErrCode(FailCode.requestRequestDateNull.failCode);
					responseScore.setErrMsg(FailCode.requestRequestDateNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getSign())){//数字签名
					FailCode.addFails(result, FailCode.requestSignNull);
					responseScore.setErrCode(FailCode.requestSignNull.failCode);
					responseScore.setErrMsg(FailCode.requestSignNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getSpID())){//SPID业务编号
					FailCode.addFails(result, FailCode.requestSpIDNull);
					responseScore.setErrCode(FailCode.requestSpIDNull.failCode);
					responseScore.setErrMsg(FailCode.requestSpIDNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getOutOrderID())){//外部订单号
					FailCode.addFails(result, FailCode.requestOutOrderIDNull);
					responseScore.setErrCode(FailCode.requestOutOrderIDNull.failCode);
					responseScore.setErrMsg(FailCode.requestOutOrderIDNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getDeviceNo())){//设备号
					FailCode.addFails(result, FailCode.requestDeviceNoNull);
					responseScore.setErrCode(FailCode.requestDeviceNoNull.failCode);
					responseScore.setErrMsg(FailCode.requestDeviceNoNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getDeviceType())){//设备类型
					FailCode.addFails(result, FailCode.requestDeviceTypeNull);
					responseScore.setErrCode(FailCode.requestDeviceTypeNull.failCode);
					responseScore.setErrMsg(FailCode.requestDeviceTypeNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getProvinceID())){//省编号
					FailCode.addFails(result, FailCode.requestProvinceIDNull);
					responseScore.setErrCode(FailCode.requestProvinceIDNull.failCode);
					responseScore.setErrMsg(FailCode.requestProvinceIDNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getNum())){//兑入积分
					FailCode.addFails(result, FailCode.requestNumNull);
					responseScore.setErrCode(FailCode.requestNumNull.failCode);
					responseScore.setErrMsg(FailCode.requestNumNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getExpTime())){//过期时间
					FailCode.addFails(result, FailCode.requestExpTimeNull);
					responseScore.setErrCode(FailCode.requestExpTimeNull.failCode);
					responseScore.setErrMsg(FailCode.requestExpTimeNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}if(StringUtil.isNullOrEmpty(param.getRemark())){//备注
					FailCode.addFails(result, FailCode.requestRemarkNull);
					responseScore.setErrCode(FailCode.requestRemarkNull.failCode);
					responseScore.setErrMsg(FailCode.requestRemarkNull.desc);
					responseScoreResult.setResponseScore(responseScore);
					return responseScoreResult;
				}

				scoreServImpl.enterAmountCall(responseScoreResult, param);
			} catch (Exception ex) {
				logger.error("$$$$电信兑入积分---兑入积分请求失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return responseScoreResult;
		}

		@Override
		public StringResult getRedisbyKey(String key,int count) throws TException {
			StringResult stringResult = new StringResult();
			Result result = new Result();
			result.setCode(2);
			stringResult.setResult(result);

			try {
				logger.info("$$$$收入积分---请求参数：" +key);
				if (StringUtil.isNullOrEmpty(key)) {
					FailCode.addFails(result, FailCode.requestArgsIsNull);
					return stringResult;
				}

				int  size=ir.getRedisbyKey(key,count);
				result.setCode(size);
				stringResult.setResult(result);
			} catch (Exception ex) {
				logger.error("$$$$收入积分---收入积分请求失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
				return stringResult;
			}

			return stringResult;
		}

		@Override
		public ScoreAccountResult isUserIdRela(int userId) throws TException {
			ScoreAccountResult scoreAccountResult = new ScoreAccountResult();
			Result result = new Result();
			result.setCode(0);
			scoreAccountResult.setResult(result);
			try{
				logger.info(String.format("$$$$判断用户是否绑定账号---请求参数：param:"+userId));
				if(userId == 0){
					FailCode.addFails(result, FailCode.requestArgsIsNull);
					return scoreAccountResult;
				}
				
				scoreServImpl.isUserIdRela(scoreAccountResult,userId);
			} catch (Exception ex) {
				logger.error("$$$$判断用户是否绑定账号失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return scoreAccountResult;
		}

		@Override
		public ScoreAccountResult isAccountRela(String account) throws TException {
			ScoreAccountResult scoreAccountResult = new ScoreAccountResult();
			Result result = new Result();
			result.setCode(0);
			scoreAccountResult.setResult(result);
			try{
				logger.info(String.format("$$$$判断用户是否绑定账号---请求参数：param:"+account));
				if(StringUtil.isNullOrEmpty(account)){
					FailCode.addFails(result, FailCode.requestArgsIsNull);
					return scoreAccountResult;
				}
				
				scoreServImpl.isAccountRela(scoreAccountResult,account);
			} catch (Exception ex) {
				logger.error("$$$$判断用户是否绑定账号失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return scoreAccountResult;
		}

		@Override
		public ResponseRelaAcoountResult relaAccountCall(RelaAccountRequestParam param) throws TException {
			ResponseRelaAcoountResult responseRelaAcoountResult = new ResponseRelaAcoountResult();
			ResponseScore responseScore = new ResponseScore();
			Result result = new Result();
			result.setCode(0);
			responseRelaAcoountResult.setResult(result);
			try{
				logger.info(String.format("$$$$电信绑定账号---请求参数：param:%s", param != null ? param.toString() : null));
				if(StringUtil.isNullOrEmpty(param.getAppCode())){//应用编码
					FailCode.addFails(result, FailCode.requestAppCodeNull);
					responseScore.setErrCode(FailCode.requestAppCodeNull.failCode);
					responseScore.setErrMsg(FailCode.requestAppCodeNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}if(StringUtil.isNullOrEmpty(param.getRequestDate())){//请求时间
					FailCode.addFails(result, FailCode.requestRequestDateNull);
					responseScore.setErrCode(FailCode.requestRequestDateNull.failCode);
					responseScore.setErrMsg(FailCode.requestRequestDateNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}if(StringUtil.isNullOrEmpty(param.getSign())){//数字签名
					FailCode.addFails(result, FailCode.requestSignNull);
					responseScore.setErrCode(FailCode.requestSignNull.failCode);
					responseScore.setErrMsg(FailCode.requestSignNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}if(StringUtil.isNullOrEmpty(param.getSpID())){//SPID业务编号
					FailCode.addFails(result, FailCode.requestSpIDNull);
					responseScore.setErrCode(FailCode.requestSpIDNull.failCode);
					responseScore.setErrMsg(FailCode.requestSpIDNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}if(StringUtil.isNullOrEmpty(param.getDeviceNo())){//设备号
					FailCode.addFails(result, FailCode.requestDeviceNoNull);
					responseScore.setErrCode(FailCode.requestDeviceNoNull.failCode);
					responseScore.setErrMsg(FailCode.requestDeviceNoNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}if(StringUtil.isNullOrEmpty(param.getDeviceType())){//设备类型
					FailCode.addFails(result, FailCode.requestDeviceTypeNull);
					responseScore.setErrCode(FailCode.requestDeviceTypeNull.failCode);
					responseScore.setErrMsg(FailCode.requestDeviceTypeNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}if(StringUtil.isNullOrEmpty(param.getExceedTime())){//过期时间
					FailCode.addFails(result, FailCode.requestExpTimeNull);
					responseScore.setErrCode(FailCode.requestExpTimeNull.failCode);
					responseScore.setErrMsg(FailCode.requestExpTimeNull.desc);
					responseRelaAcoountResult.setResponseScore(responseScore);
					return responseRelaAcoountResult;
				}

				scoreServImpl.relaAccountCall(responseRelaAcoountResult, param);			
			} catch (Exception ex) {
				logger.error("$$$$电信绑定账号---电信绑定账号请求失败，系统异常！", ex);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return responseRelaAcoountResult;
		}
}
