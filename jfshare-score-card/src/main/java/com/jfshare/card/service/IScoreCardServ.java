package com.jfshare.card.service;

import com.jfshare.finagle.thrift.card.ActivityBatchResult;
import com.jfshare.finagle.thrift.card.ActivityBean;
import com.jfshare.finagle.thrift.card.ActivityQueryParam;
import com.jfshare.finagle.thrift.card.ActivityResult;
import com.jfshare.finagle.thrift.card.CardBatchResult;
import com.jfshare.finagle.thrift.card.CardQueryParam;
import com.jfshare.finagle.thrift.card.CardRecordBatchResult;
import com.jfshare.finagle.thrift.card.CardRecordResult;
import com.jfshare.finagle.thrift.card.CardResult;
import com.jfshare.finagle.thrift.card.DirectRechargeResult;
import com.jfshare.finagle.thrift.card.ExcelExportResult;
import com.jfshare.finagle.thrift.card.InvalidOneActivityResult;
import com.jfshare.finagle.thrift.card.RechargeParam;
import com.jfshare.finagle.thrift.card.ToRechargeParams;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;

/**
 * service 实现类
 */
public interface IScoreCardServ {

	public void createOneActivity(ActivityResult retResult ,ActivityBean entity);

	public void queryActivities(ActivityBatchResult retResult, ActivityQueryParam param, Pagination pagination) ;

	public void queryActivityById(ActivityResult retResult,int activityId) ;

	public void queryCards(CardBatchResult retResult,int activityId, CardQueryParam param, Pagination pagination) ;

	public void queryCardById(CardResult retResult,int cardId) ;

	public void queryRechargeCards(CardRecordBatchResult retResult,int userId,Pagination pagination);

	public void queryRechargeCardById(CardRecordResult retResult,int rechargeCardId) ;

	public void exportExcelByqueryCards(ExcelExportResult retResult,int activityId, CardQueryParam param, String psd) ;

	/**
	 * 主站手动充值
	 * @param stringResult
	 * @param param
	 * @param mobile
	 * @return
	 */
	public StringResult recharge(StringResult stringResult, RechargeParam param, String mobile) throws Exception;
	
	public boolean validataPassword(String validataStr);
	public void directRecharge(DirectRechargeResult directRechargeResult,ToRechargeParams params)throws Exception;

	public void invalidOneActivity(InvalidOneActivityResult retResult,int activityId, String psd) ;

}

