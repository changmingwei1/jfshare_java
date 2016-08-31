package com.jfshare.card.server;

import com.jfshare.card.server.depend.BuyerClient;
import com.jfshare.card.service.IScoreCardServ;
import com.jfshare.card.util.FailCode;
import com.jfshare.card.util.ScoreCardUtil;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.buyer.BuyerResult;
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
import com.jfshare.finagle.thrift.card.ScoreCardServ;
import com.jfshare.finagle.thrift.card.ToRechargeParams;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.utils.StringUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "handler")
public class ServHandle  implements ScoreCardServ.Iface {
	private static  final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);


	@Autowired
	private IScoreCardServ scoreCardServImpl;
	@Autowired
	private BuyerClient buyerClient;

	@Override
	public ActivityResult createOneActivity(ActivityBean entity) throws TException {


		ActivityResult retResult = new ActivityResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		try {
			logger.info("$$$$收入积分---请求参数：" + retResult != null ? retResult.toString() : null);
			logger.info("创建批次活动---请求参数：" + entity != null ? entity.toString() : null);
			if(null==entity){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}
			if(StringUtil.isNullOrEmpty(entity.getName()) || entity.getName().length() > 40){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}
			if(StringUtil.isNullOrEmpty(entity.getPieceValue()) ) {
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}else {
//                面值：单张积分卡包含的积分数
//                1-100000的整数，仅支持输入阿拉伯数字；
//                输入值错误时提示：面值需为1-100000的整数
				int pieceValue=Integer.parseInt(entity.getPieceValue());
				if(pieceValue<1 || pieceValue> 100000){
					FailCode.addFails(result, FailCode.repquestParamError);
					return  retResult;
				}
			}

			if(entity.getTotalCount()<1 || entity.getTotalCount()>5000){

				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}

			if(ScoreCardUtil.compareDate(entity.getStartTime(),entity.getEndTime())!=-1){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}

			if(StringUtil.isNullOrEmpty(entity.getStartTime()) || StringUtil.isNullOrEmpty(entity.getEndTime())){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}

			if(StringUtil.isNullOrEmpty(entity.getRechargeType()) || StringUtil.isNullOrEmpty(entity.getMultiRechargeEnable())){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}

			if(!(entity.getRechargeType().equals("0") || entity.getRechargeType().equals("1"))){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}
			if(!(entity.getMultiRechargeEnable().equals("0") || entity.getMultiRechargeEnable().equals("1"))){
				FailCode.addFails(result, FailCode.repquestParamError);
				return  retResult;
			}

			scoreCardServImpl.createOneActivity(retResult,entity);


		} catch (Exception ex) {
			logger.error("$$$$收入积分---收入积分请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			return retResult;
		}


		return retResult;

	}

	@Override
	public ActivityBatchResult queryActivities(ActivityQueryParam param, Pagination pagination) throws TException {

		ActivityBatchResult retResult = new ActivityBatchResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		if(pagination==null){
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			return retResult;
		}
		if (StringUtil.isNullOrEmpty(pagination.getCurrentPage()) || pagination.getCurrentPage()<=0) {
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		
		if (StringUtil.isNullOrEmpty(pagination.getNumPerPage()) || pagination.getNumPerPage()<=0) {
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		scoreCardServImpl.queryActivities(retResult,param,pagination);



		return retResult;
	}

	@Override
	public ActivityResult queryActivityById(int activityId) throws TException {
		ActivityResult retResult = new ActivityResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		if(StringUtil.isNullOrEmpty(activityId)){
			FailCode.addFails(result, FailCode.repquestParamError);
			return  retResult;
		}

		scoreCardServImpl.queryActivityById(retResult,activityId);

		return retResult;


	}

	@Override
	public CardBatchResult queryCards(int activityId, CardQueryParam param, Pagination pagination) throws TException {

		CardBatchResult retResult = new CardBatchResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);

		if(pagination==null){
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		
		if (StringUtil.isNullOrEmpty(pagination.getCurrentPage()) || pagination.getCurrentPage()<=0) {
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		
		if (StringUtil.isNullOrEmpty(pagination.getNumPerPage()) || pagination.getNumPerPage()<=0) {
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}

		scoreCardServImpl.queryCards(retResult,activityId,param,pagination);

		return retResult;

	}

	@Override
	public CardResult queryCardById(int cardId) throws TException {

		CardResult retResult = new CardResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);
		if(StringUtil.isNullOrEmpty(cardId)){
			FailCode.addFails(result, FailCode.repquestParamError);
			return  retResult;
		}

		scoreCardServImpl.queryCardById(retResult,cardId);

		return  retResult;
	}


	@Override
	public CardRecordBatchResult queryRechargeCards(int userId,  Pagination pagination) throws TException {

		CardRecordBatchResult retResult = new CardRecordBatchResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);
		if(StringUtil.isNullOrEmpty(userId) || null ==pagination){
			FailCode.addFails(result, FailCode.repquestParamError);
			return  retResult;
		}
		
		if(pagination==null){
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		
		if (StringUtil.isNullOrEmpty(pagination.getCurrentPage()) || pagination.getCurrentPage()<=0) {
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		
		if (StringUtil.isNullOrEmpty(pagination.getNumPerPage()) || pagination.getNumPerPage()<=0) {
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}
		
		scoreCardServImpl.queryRechargeCards(retResult,userId,pagination);

		return  retResult;
	}

	@Override
	public CardRecordResult queryRechargeCardById(int rechargeCardId) throws TException {

		CardRecordResult retResult = new CardRecordResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);
		if(StringUtil.isNullOrEmpty(rechargeCardId)){
			FailCode.addFails(result, FailCode.repquestParamError);
			return  retResult;
		}

		scoreCardServImpl.queryRechargeCardById(retResult,rechargeCardId);

		return  retResult;

	}

	@Override
	public ExcelExportResult exportExcelByqueryCards(int activityId, CardQueryParam param,String psd) throws TException {
		ExcelExportResult retResult = new ExcelExportResult();
		Result result = new Result();
		result.setCode(0);
		retResult.setResult(result);
		if(StringUtil.isNullOrEmpty(activityId) || StringUtil.isNullOrEmpty(psd)){
			FailCode.addFails(result, FailCode.repquestParamError);
			return  retResult;
		}

		scoreCardServImpl.exportExcelByqueryCards(retResult,activityId,param,psd);

		return  retResult;
	}

	@Override
	public StringResult recharge(RechargeParam param) throws TException {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		try {
			//参数校验
			if (StringUtil.isNullOrEmpty(param.getCardName()) || StringUtil.isNullOrEmpty(param.getCardPsd())
					|| StringUtil.isNullOrEmpty(param.getUserId())) {
				FailCode.addFails(result, FailCode.repquestParamError);
				return stringResult;
			}
			//先获取用户信息(手机号)
			Buyer buyer = new Buyer();
			buyer.setUserId(param.getUserId());
			BuyerResult buyerResult = buyerClient.getBuyer(buyer);
			logger.info("获取到的用户信息，buyerResult=" + buyerResult);
			if (buyerResult.getResult().getCode() != 0) {
				FailCode.addFails(result, FailCode.GET_BUYER_FAILURE);
				return stringResult;
			}
			String mobile = buyerResult.getBuyer().getMobile();
			stringResult = scoreCardServImpl.recharge(stringResult,param,mobile);
		} catch (Exception e) {
			logger.error("积分充值失败",e);
			FailCode.addFails(stringResult.getResult(), FailCode.SYSTEM_EXCEPTION);
			return stringResult;
		}
		return stringResult;
	}

	@Override
	public Result validataPassword(String validataStr) throws TException {
		Result result=new Result(0);
		result.setCode(0);
		if(StringUtil.isNullOrEmpty(validataStr)){
			FailCode.addFails(result, FailCode.repquestParamError);
			return result;
		}
		
		if(!scoreCardServImpl.validataPassword(validataStr)){
			FailCode.addFails(result, FailCode.validataParamError);
			return result;
		}
		
		return result;
	}

	@Override
	public DirectRechargeResult directRecharge(ToRechargeParams params) throws TException {
		DirectRechargeResult directRechargeResult=new DirectRechargeResult();
		Result result=new Result(0);
		directRechargeResult.setResult(result);
		result.setCode(0);
		if(params==null||StringUtil.isNullOrEmpty(params.getFilePath())||StringUtil.isNullOrEmpty(params.getValidataStr())
				||StringUtil.isNullOrEmpty(params.getActivityId())){
			FailCode.addFails(result, FailCode.repquestParamError);
			return directRechargeResult;
		}
		
		try {
			scoreCardServImpl.directRecharge(directRechargeResult, params);
		} catch (Exception e) {
			logger.error("--积分定向充值失败---，系统异常！", e);
			FailCode.addFails(directRechargeResult.getResult(), FailCode.SYSTEM_EXCEPTION);
			return directRechargeResult;
		}
		
		return directRechargeResult;
	}

	@Override
	public InvalidOneActivityResult invalidOneActivity(int activityId, String psd) throws TException {

		InvalidOneActivityResult retResult=new InvalidOneActivityResult();
		Result result=new Result();
		result.setCode(0);
		retResult.setResult(result);

		if(StringUtil.isNullOrEmpty(activityId)||StringUtil.isNullOrEmpty(psd)){
			FailCode.addFails(result, FailCode.repquestParamError);
			return retResult;
		}

		try {
			scoreCardServImpl.invalidOneActivity(retResult,activityId,psd);
		} catch (Exception e) {
			logger.error("--批次活动作废失败---，系统异常！", e);
			FailCode.addFails(retResult.getResult(), FailCode.SYSTEM_EXCEPTION);
			return retResult;
		}


		return retResult;
	}
}
