package com.jfshare.score.service;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.CachAmountCallParam;
import com.jfshare.finagle.thrift.score.CachAmountQueryParam;
import com.jfshare.finagle.thrift.score.CachAmountResult;
import com.jfshare.finagle.thrift.score.RelaAccountRequestParam;
import com.jfshare.finagle.thrift.score.ResponseCachResult;
import com.jfshare.finagle.thrift.score.ResponseRelaAcoountResult;
import com.jfshare.finagle.thrift.score.ResponseScoreResult;
import com.jfshare.finagle.thrift.score.ScoreAccountResult;
import com.jfshare.finagle.thrift.score.ScoreRequestParam;
import com.jfshare.finagle.thrift.score.ScoreResult;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreTradeQueryParam;
import com.jfshare.finagle.thrift.score.ScoreTradeResult;
import com.jfshare.finagle.thrift.score.ScoreUserQueryParam;
import com.jfshare.finagle.thrift.score.ScoreUserResult;

public interface IScoreServ {
	//积分增加
	void income(StringResult stringResult, ScoreTrade scoreTrade);
	
	//积分扣减
	void expenditure(StringResult stringResult, ScoreTrade scoreTrade);
	
	//查询积分
	void getScore(ScoreResult scoreResult, int userId);
	
	//查询积分交易明细
	void queryScoreTrade(ScoreTradeResult scoreTradeResult, ScoreTradeQueryParam param, Pagination pagination);
	
	//查询积分列表
	void queryScoreUser(ScoreUserResult scoreInfoResult, ScoreUserQueryParam param, Pagination pagination);	
	
	//兑换积分查询
	void queryCachAmount(CachAmountResult scoreInfoResult, CachAmountQueryParam param);
	
	// 兑出积分调用接口
	void cachAmountCall(ResponseCachResult responseCachResult, CachAmountCallParam param);
	
	//兑入积分调用接口
	void enterAmountCall(ResponseScoreResult responseScoreResult,ScoreRequestParam param);
	
	//判断用户是否绑定账号
	void isUserIdRela(ScoreAccountResult scoreAccountResult,int userId);
	
	//判断用户是否绑定账号
	void isAccountRela(ScoreAccountResult scoreAccountResult,String account);
	
	void relaAccountCall(ResponseRelaAcoountResult responseScoreResult,RelaAccountRequestParam param);
}
