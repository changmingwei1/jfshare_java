package com.jfshare.score.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.CachAmount;
import com.jfshare.finagle.thrift.score.CachAmountCallParam;
import com.jfshare.finagle.thrift.score.CachAmountQueryParam;
import com.jfshare.finagle.thrift.score.CachAmountResult;
import com.jfshare.finagle.thrift.score.RelaAccountRequestParam;
import com.jfshare.finagle.thrift.score.ReponseCach;
import com.jfshare.finagle.thrift.score.ResponseCachResult;
import com.jfshare.finagle.thrift.score.ResponseRelaAcoountResult;
import com.jfshare.finagle.thrift.score.ResponseScore;
import com.jfshare.finagle.thrift.score.ResponseScoreResult;
import com.jfshare.finagle.thrift.score.Score;
import com.jfshare.finagle.thrift.score.ScoreAccount;
import com.jfshare.finagle.thrift.score.ScoreAccountResult;
import com.jfshare.finagle.thrift.score.ScoreRequestParam;
import com.jfshare.finagle.thrift.score.ScoreResult;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreTradeQueryParam;
import com.jfshare.finagle.thrift.score.ScoreTradeResult;
import com.jfshare.finagle.thrift.score.ScoreUser;
import com.jfshare.finagle.thrift.score.ScoreUserQueryParam;
import com.jfshare.finagle.thrift.score.ScoreUserResult;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.score.dao.mysql.IScoreDao;
import com.jfshare.score.dao.redis.IScoreRedis;
import com.jfshare.score.model.TbScoreAccountRela;
import com.jfshare.score.model.TbScoreTrade;
import com.jfshare.score.model.TbScoreUser;
import com.jfshare.score.model.TbUser;
import com.jfshare.score.model.TbUserInfo;
import com.jfshare.score.service.IScoreServ;
import com.jfshare.score.util.Constants;
import com.jfshare.score.util.FailCode;
import com.jfshare.score.util.HttpClient;
import com.jfshare.score.util.ScoreUtil;
import com.jfshare.utils.StringUtil;

@Service
public class ScoreServImpl implements IScoreServ {
	private static Logger logger = LoggerFactory.getLogger(ScoreServImpl.class);

	@Autowired
	private IScoreDao scoreDaoImpl;
	@Autowired
	private IScoreRedis scoreRediImpl;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	@Override
	public void income(StringResult stringResult, ScoreTrade scoreTrade) {
		if (StringUtil.isNullOrEmpty(scoreTrade) || StringUtil.isNullOrEmpty(scoreTrade.getTradeId()) || scoreTrade.getUserId() <= 0 || scoreTrade.getInOrOut()<=0 || scoreTrade.getType() <= 0 ||scoreTrade.getTrader() <= 0 ) {
			FailCode.addFails(stringResult.getResult(), FailCode.requestArgsIsNull);
			return;
		}
		int rowsRepeact = scoreDaoImpl.queryScoreTrade(scoreTrade);
		if(rowsRepeact > 0){
			FailCode.addFails(stringResult.getResult(), FailCode.requestRepeact);
			return;
		}
		// 更新用户积分表
		//int userCurAmount = scoreDaoImpl.queryUserScore(scoreTrade.getUserId());
		TbScoreUser scoreUser = scoreDaoImpl.selectUserScoreByUserId(scoreTrade.getUserId());
		
		if (scoreUser == null) { 
			// 用户记录不存在
			scoreDaoImpl.insertUserScore(scoreTrade.getUserId(), scoreTrade.getAmount());
		} else {
			int rows = scoreDaoImpl.updateUserScore(scoreTrade.getUserId(), scoreUser.getAmount() + scoreTrade.getAmount(),scoreUser.getVersion());
			if (rows <= 0) {
				logger.info("用户增加积分失败，用户id为："+scoreTrade.getUserId()+"积分值为："+scoreTrade.getAmount());
				FailCode.addFails(stringResult.getResult(), FailCode.dbUpdateFailed);
				return;
			}
		}
		// 插入用户交易表
		TbScoreTrade tbScoreTrade = ScoreUtil.scoreTrade2TbScoreTrade(scoreTrade);
		int threadRows = scoreDaoImpl.insertScoreTradeRecord(tbScoreTrade);
		if ( threadRows <= 0) {
			logger.info("记录增加积分交易明细失败，用户id为："+scoreTrade.getUserId()+"积分值为："+scoreTrade.getAmount());
			FailCode.addFails(stringResult.getResult(), FailCode.dbUpdateFailed);
			return;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	@Override
	public void expenditure(StringResult stringResult, ScoreTrade scoreTrade) {
		if (StringUtil.isNullOrEmpty(scoreTrade) || StringUtil.isNullOrEmpty(scoreTrade.getTradeId()) || scoreTrade.getUserId() <= 0 || scoreTrade.getInOrOut()<=0 || scoreTrade.getType() <= 0 ||scoreTrade.getTrader() <= 0 ) {
			FailCode.addFails(stringResult.getResult(), FailCode.requestArgsIsNull);
			return;
		}
		int rowsRepeact = scoreDaoImpl.queryScoreTrade(scoreTrade);
		if(rowsRepeact > 0){
			FailCode.addFails(stringResult.getResult(), FailCode.requestRepeact);
			return;
		}
		// 更新用户积分表
		//int userCurAmount = scoreDaoImpl.queryUserScore(scoreTrade.getUserId());
		TbScoreUser scoreUser = scoreDaoImpl.selectUserScoreByUserId(scoreTrade.getUserId());

		if (scoreUser == null) {
			FailCode.addFails(stringResult.getResult(), FailCode.scoreNotEnough);
			return;
		}
		if(scoreUser.getAmount() < scoreTrade.getAmount()){
			FailCode.addFails(stringResult.getResult(), FailCode.scoreNotEnough);
			return;
		}
		int count = scoreDaoImpl.updateUserScore(scoreTrade.getUserId(), scoreUser.getAmount() - scoreTrade.getAmount(),scoreUser.getVersion());
		if (count <= 0) {
			logger.info("用户扣除积分失败，用户id为："+scoreTrade.getUserId()+"积分值为："+scoreTrade.getAmount());
			FailCode.addFails(stringResult.getResult(), FailCode.dbUpdateFailed);
			return;
		}
		// 插入用户交易表
		TbScoreTrade tbScoreTrade = ScoreUtil.scoreTrade2TbScoreTrade(scoreTrade);
		int threadRows = scoreDaoImpl.insertScoreTradeRecord(tbScoreTrade);
		if ( threadRows <= 0) {
			logger.info("记录增加积分交易明细失败，用户id为："+scoreTrade.getUserId()+"积分值为："+scoreTrade.getAmount());
			FailCode.addFails(stringResult.getResult(), FailCode.dbUpdateFailed);
			return;
		}
	}

	@Override
	public void getScore(ScoreResult scoreResult, int userId) {
		int userCurAmount = scoreDaoImpl.queryUserScore(userId);
		if(userCurAmount > 0){
			Score score = new Score();
			score.setUserId(userId);
			score.setAmount(userCurAmount);
			scoreResult.setSroce(score);
			return;
		}	
	}

	@Override
	public void queryScoreTrade(ScoreTradeResult scoreTradeResult, ScoreTradeQueryParam param, Pagination pagination) {
		List<TbScoreTrade> tbScoreTrades= scoreDaoImpl.queryScoreTrade(param);
		if(tbScoreTrades == null){
			FailCode.addFails(scoreTradeResult.getResult(), FailCode.dbOptionFailed);
			return;
		}
		
		List<ScoreTrade> scoreTrades = ScoreUtil.process(tbScoreTrades);
		
		//按照分页的参数自己处理list的获取范围
		if(scoreTrades != null && scoreTrades.size() > 0){
			if(pagination != null){
				int fromIndex = pagination.getNumPerPage() * (pagination.getCurrentPage() - 1);
				int toIndex = pagination.getNumPerPage() * pagination.getCurrentPage();
				if(fromIndex < 0) fromIndex = 0;
				if(toIndex > scoreTrades.size()) toIndex = scoreTrades.size();
				
				pagination.setTotalCount(scoreTrades.size());
				pagination.setPageNumCount((int) Math.ceil(pagination.totalCount / (double) pagination.numPerPage));
				
				List<ScoreTrade> trades = scoreTrades.subList(fromIndex, toIndex);
				scoreTradeResult.setScoreTrades(trades);
				scoreTradeResult.setPagination(pagination);
				
			} else {
				scoreTradeResult.setScoreTrades(scoreTrades);
			}
		}
	}

	//查询积分列表20160511
	@Override
	public void queryScoreUser(ScoreUserResult scoreUserResult, ScoreUserQueryParam param, Pagination pagination) {
		List<TbUserInfo> tbUserInfos= scoreDaoImpl.queryScoreUser(param);
		if(tbUserInfos == null){
			FailCode.addFails(scoreUserResult.getResult(), FailCode.dbOptionFailed);
			return;
		}
		//DB的数据转换到thrift返回对象
		List<ScoreUser> scoreUsers = ScoreUtil.processUserInfo(tbUserInfos);
		
		//按照分页的参数自己处理list的获取范围
		if(scoreUsers != null && scoreUsers.size() > 0){
			if(pagination != null){
				int fromIndex = pagination.getNumPerPage() * (pagination.getCurrentPage() - 1);
				int toIndex = pagination.getNumPerPage() * pagination.getCurrentPage();
				if(fromIndex < 0) fromIndex = 0;
				if(toIndex > scoreUsers.size()) toIndex = scoreUsers.size();
				
				pagination.setTotalCount(scoreUsers.size());
				pagination.setPageNumCount((int) Math.ceil(pagination.totalCount / (double) pagination.numPerPage));
				
				List<ScoreUser> sus = scoreUsers.subList(fromIndex, toIndex);
				scoreUserResult.setScoreUsers(sus);
				scoreUserResult.setPagination(pagination);
				
			} else {
				scoreUserResult.setScoreUsers(scoreUsers);
			}
		}
		
	}
	
	//兑换积分查询
	@Override
	public void queryCachAmount(CachAmountResult cachAmountResult, CachAmountQueryParam param) {
		int totalAmount = scoreDaoImpl.queryUserScore(param.getUserId());
		String amount = scoreRediImpl.getCashAmountByUserid(String.valueOf(param.getUserId()));
		
		CachAmount cachAmount = new CachAmount();
		if(totalAmount > 0){
			cachAmount.setTotalAmount(totalAmount);
		}
		if(!StringUtil.isNullOrEmpty(amount)){
			cachAmount.setCachAmount(Integer.valueOf(amount));
		}
		cachAmountResult.setCachAmount(cachAmount);
		return;
	}
	
	/* 兑出积分调用接口 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	@Override
	public void cachAmountCall(ResponseCachResult responseCachResult, CachAmountCallParam param) {
		int redisAmount = 0;//累计用户ID积分
		int redisMoblieAmount = 0;//累计手机号积分
		String redisValue = scoreRediImpl.getCashAmountByUserid(String.valueOf(param.getUserId()));//累计积分
		String redisMoblieValue = scoreRediImpl.getCashAmountByMobile(String.valueOf(param.getMobile()));//累计手机号积分
		if(!StringUtil.isNullOrEmpty(redisValue)){
			redisAmount = Integer.valueOf(redisValue);
		}
		if(!StringUtil.isNullOrEmpty(redisMoblieValue)){
			redisMoblieAmount = Integer.valueOf(redisMoblieValue);
		}
		//累计兑换积分额
		int totalAmount = scoreDaoImpl.queryUserScore(param.getUserId());//积分总额
		int cashAmount = param.getCachAmount();//本次兑换积分数量
		int sumAmountTemp = cashAmount+redisAmount;//本次兑换+累计用户ID积分
		int sumMobileAmountTemp = cashAmount+redisMoblieAmount;//本次兑换+累计手机号积分
		if(sumAmountTemp > Constants.MAXAMOUNT){
			FailCode.addFails(responseCachResult.getResult(), FailCode.scoreToolarge);
			return;
		}if(sumMobileAmountTemp > Constants.MAXAMOUNT){
			FailCode.addFails(responseCachResult.getResult(), FailCode.scoreToolargeMobile);
			return;
		}
		if(cashAmount > totalAmount){
			FailCode.addFails(responseCachResult.getResult(), FailCode.scoreNotEnough);
			return;
		}if(ScoreUtil.isMobile(param.mobile) == false){
			FailCode.addFails(responseCachResult.getResult(), FailCode.isNotMobile);
			return;
		}	  
		//调用积分发行接口接口
		JSONObject json = createCashJson(responseCachResult,param);//封装需要发送的数据		
		int id = scoreDaoImpl.insertCashRequestJson(json);//记录积分兑出请求信息
		if(id <= 0){
			FailCode.addFails(responseCachResult.getResult(), FailCode.dbUpdateFailed);
			return;
		}
		String postxml =json.toString();
		String postUrl = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_cach_posturl", "0");
		if("0".equals(postUrl)){
			FailCode.addFails(responseCachResult.getResult(), FailCode.repquestPostUrl);		
			return;
		}
		String reponse = HttpClient.getInstance().sendPostReq(postUrl, postxml, Constants.CONTENTTYPE);
		scoreDaoImpl.updateCashInterfaceLogById(id,reponse);//更新返回结果到tb_request_interfacce_log
		
		if(StringUtil.isNullOrEmpty(reponse)){
			FailCode.addFails(responseCachResult.getResult(), FailCode.responseError);
            return ;
		}
        JSONObject responseJson = (JSONObject) JSONObject.parse(reponse);
        if(Constants.CASH_STATE_SUCCESS.equals(responseJson.getString("ErrCode"))){//兑出成功操作
        	TbScoreAccountRela tbsar = new TbScoreAccountRela();
        	tbsar.setAccount(param.getMobile());
        	tbsar.setUserId(param.getUserId());
        	//校验是否已经绑定账号
        	Boolean flag = validIsRela(tbsar);
        	if(!flag){	
        		tbsar.setType("1");//兑出绑定
        		//添加用户账号绑定关系
        		int insertRows = scoreDaoImpl.insertScoreAccountRela(tbsar);
        		if(insertRows < 1){
        			FailCode.addFails(responseCachResult.getResult(), FailCode.dbUpdateFailed);
                    return ;
        		}
        		String mobile = scoreDaoImpl.selectUserMobile(param.getUserId());//聚分享手机号
        		String cashMobile = param.getMobile();//兑出手机号
            	JSONObject relaJson = createAccountRelaJson(responseCachResult,mobile,cashMobile);
        		String relaXml =relaJson.toString();
        		
        		int relaId = scoreDaoImpl.insertCashRelaAccountRequestJson(relaJson);//记录绑定账号请求信息
        		if(relaId <= 0){
        			FailCode.addFails(responseCachResult.getResult(), FailCode.dbUpdateFailed);
        			return;
        		}
        		
        		String relaUrl = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_rela_posturl", "0");
        		
        		if("0".equals(relaUrl)){
        			FailCode.addFails(responseCachResult.getResult(), FailCode.repquestPostUrl);		
        			return;
        		}
        		String relaReponse = HttpClient.getInstance().sendPostReq(relaUrl, relaXml, Constants.CONTENTTYPE);
        		scoreDaoImpl.updateCashRelaAccountLogById(relaId, relaReponse);//更新返回结果到tb_score_account_rela_log        		
        	}
        	
    		
    		TbScoreTrade scoreTrade = new TbScoreTrade();
//    		scoreTrade.setId(id);
//    		scoreTrade.setTradeTime(tradeTime);
    		scoreTrade.setTrader(Constants.CASHTRADER);
    		scoreTrade.setType(Constants.CASHTYPE);
    		scoreTrade.setAmount(param.getCachAmount());
    		scoreTrade.setInOrOut(Constants.CASHINOROUT);
    		scoreTrade.setUserId(param.getUserId());
    		scoreTrade.setCashMobile(param.getMobile());
    		scoreTrade.setTradeId(Constants.TRADEID_CASH_USERID+param.getUserId());
    		int insertRows = scoreDaoImpl.insertScoreTradeRecord(scoreTrade);
    		if(insertRows < 1){
    			FailCode.addFails(responseCachResult.getResult(), FailCode.dbUpdateFailed);
                return ;
    		}
        	//扣除积分
//    		TbScoreUser tbScoreUser = new TbScoreUser();
//    		tbScoreUser.setUserId(param.getUserId());
//    		tbScoreUser.setAmount(totalAmount-cashAmount);
//    		int updateRows = scoreDaoImpl.updateByPrimaryKey(tbScoreUser);
    		TbScoreUser scoreUser = scoreDaoImpl.selectUserScoreByUserId(param.getUserId());//根据用户Id查询积分是否存在
			int cashAmountTemp = totalAmount-cashAmount;
			int updaterows = scoreDaoImpl.updateUserScore(param.getUserId(), cashAmountTemp,scoreUser.getVersion());	
    		if(updaterows < 1){
    			FailCode.addFails(responseCachResult.getResult(), FailCode.dbUpdateFailed);
                return ;
    		}
    		
    		String redisAmountUpdate=String.valueOf(redisAmount+cashAmount);
    		scoreRediImpl.setCashAmountByUserid(String.valueOf(param.getUserId()),redisAmountUpdate);//redis累计积分额
    		String redisMobileUpdate = String.valueOf(redisMoblieAmount+cashAmount);
    		scoreRediImpl.setCashAmountByMobile(String.valueOf(param.getMobile()), redisMobileUpdate);//redis累计手机积分额
    		logger.info("----------------------积分兑出完成-------------");
        }else{//兑出失败操作
        	logger.info("----------------------调用天翼积分兑出接口失败-------------");
        	FailCode.addFails(responseCachResult.getResult(), FailCode.responseError);
        	ReponseCach rc = new ReponseCach();
        	rc.setAppCode(responseJson.getString("AppCode"));
        	rc.setResponseDate(responseJson.getString("ResponseDate"));
        	rc.setSign(responseJson.getString("Sign"));
        	rc.setErrCode(responseJson.getString("ErrCode"));     	
        	rc.setErrMsg(responseJson.getString("ErrMsg"));
        	responseCachResult.setReponseCach(rc);
			return;
        }
        //封装返回结果
        ReponseCach rc = new ReponseCach();
    	rc.setAppCode(responseJson.getString("AppCode"));
    	rc.setResponseDate(responseJson.getString("ResponseDate"));
    	rc.setSign(responseJson.getString("Sign"));
    	rc.setErrCode(responseJson.getString("ErrCode"));     	
    	rc.setErrMsg(responseJson.getString("ErrMsg"));
    	responseCachResult.setReponseCach(rc);

	}
	//积分兑入接口
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,  rollbackFor = Exception.class)
	@Override
	public void enterAmountCall(ResponseScoreResult responseScoreResult, ScoreRequestParam param) {
		ResponseScore responseScore = new ResponseScore();
		//插入积分兑入请求信息记录
		int enterId = scoreDaoImpl.insertEnterRequest(param);
		if(enterId < 1){
			FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
			responseScore.setErrCode(FailCode.repquestError.failCode);
			responseScore.setErrMsg(FailCode.repquestError.desc);
			responseScore.setResponseDate(ScoreUtil.getTime());
			responseScoreResult.setResponseScore(responseScore);
            return ;
		}
		
		String singKey = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_enter_singkey", "0");
		if("0".equals(singKey)){//无权限访问此接口
			FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestErrorNotSingkey);
			responseScore.setErrCode(FailCode.repquestErrorNotSingkey.failCode);
			responseScore.setErrMsg(FailCode.repquestErrorNotSingkey.desc);
			responseScoreResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			return;
        }
		String appcode = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_enter_appcode", "0");
		if("0".equals(appcode)){//无权限访问此接口
			FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestErrorNotSingkey);
			responseScore.setErrCode(FailCode.repquestErrorNotSingkey.failCode);
			responseScore.setErrMsg(FailCode.repquestErrorNotSingkey.desc);
			responseScoreResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			return;
        }
		String waitSign = "AppCode=" + appcode + "&SingKey="+singKey + "&Date=" + param.getRequestDate();
		//String waitSign = "AppCode=" + Constants.ENTER_APPCODE + "&SingKey="+singKey + "&Date=" + param.getRequestDate();
		String sign = null;//加密后字符串
        try {
			 sign = ScoreUtil.md5Encode(waitSign);	
		} catch (Exception e) {
			logger.info("数据签名加密异常"+e.getMessage());
			e.printStackTrace();
		}
        
        if(!sign.equals(param.getSign())){//数字签名验证是否正确
			FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestErrorSign);
			responseScore.setErrCode(FailCode.repquestErrorSign.failCode);
			responseScore.setErrMsg(FailCode.repquestErrorSign.desc);
			responseScore.setAppCode(param.getAppCode());
			responseScore.setSign(param.getSign());
			responseScoreResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			return;
        }
		if(ScoreUtil.isMobile(param.getDeviceNo()) == false){//手机号是否为正确手机号
				FailCode.addFails(responseScoreResult.getResult(), FailCode.isNotMobile);
				responseScore.setErrCode(FailCode.isNotMobile.failCode);
				responseScore.setErrMsg(FailCode.isNotMobile.desc);
				responseScoreResult.setResponseScore(responseScore);
				responseScore.setResponseDate(ScoreUtil.getTime());
				scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
				return;
		}if(ScoreUtil.getExpTimeMatches(param.getExpTime()) == false){//验证过期日期格式
			FailCode.addFails(responseScoreResult.getResult(), FailCode.requestCompareNull);
			responseScore.setErrCode(FailCode.requestCompareNull.failCode);
			responseScore.setErrMsg(FailCode.requestCompareNull.desc);
			responseScoreResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			return;
		}
		if(ScoreUtil.getCompare(param.getRequestDate(),param.getExpTime()) == false){//验证过期日期是否大于请求时间
			FailCode.addFails(responseScoreResult.getResult(), FailCode.requestCompareNull);
			responseScore.setErrCode(FailCode.requestCompareNull.failCode);
			responseScore.setErrMsg(FailCode.requestCompareNull.desc);
			responseScoreResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			return;
		}
		int userId = scoreDaoImpl.queryUserBymobile(param.getDeviceNo());//根据手机号查看用户是否存在
		if(userId > 0){
			TbScoreUser scoreUser = scoreDaoImpl.selectUserScoreByUserId(userId);//根据用户Id查询积分是否存在
			if(scoreUser != null){
				int enterAmountTemp = scoreUser.getAmount()+Integer.valueOf(param.getNum());
				int updaterows = scoreDaoImpl.updateUserScore(userId, enterAmountTemp,scoreUser.getVersion());
				if(updaterows < 1){
					FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
					responseScore.setErrCode(FailCode.repquestError.failCode);
					responseScore.setErrMsg(FailCode.repquestError.desc);
					responseScore.setResponseDate(ScoreUtil.getTime());
					scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			        return ;
				}
			}else{
				int insertRows = scoreDaoImpl.insertUserScore(userId, Integer.valueOf(param.getNum()));
				if(insertRows < 1){
					FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
					responseScore.setErrCode(FailCode.repquestError.failCode);
					responseScore.setErrMsg(FailCode.repquestError.desc);
					responseScore.setResponseDate(ScoreUtil.getTime());
					scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
			        return ;
				}
			}
			//兑入插入scoretrade表记录
			TbScoreTrade scoreTrade = new TbScoreTrade();
			scoreTrade.setTrader(1);
			scoreTrade.setType(1);
			scoreTrade.setAmount(Integer.valueOf(param.getNum()));
			scoreTrade.setInOrOut(1);
			scoreTrade.setTradeId(Constants.TRADEID_ENTER_USERID+userId);
			scoreTrade.setUserId(userId);
			scoreTrade.setCashMobile(param.getDeviceNo());
			int insertTraRows = scoreDaoImpl.insertScoreTradeRecord(scoreTrade);//兑入时插入交易记录
			if(insertTraRows < 1){
				
				FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
				responseScore.setErrCode(FailCode.repquestError.failCode);
				responseScore.setErrMsg(FailCode.repquestError.desc);
				responseScore.setResponseDate(ScoreUtil.getTime());
				scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
		        return ;
			}
			
		}else{
			TbUser user = new TbUser();
			user.setLoginName(param.getDeviceNo());
			user.setMobile(param.getDeviceNo());
			int rows = scoreDaoImpl.insertUser(user);//创建用户信息
			if(rows < 1){
				FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
				responseScore.setErrCode(FailCode.repquestError.failCode);
				responseScore.setErrMsg(FailCode.repquestError.desc);
				responseScore.setResponseDate(ScoreUtil.getTime());
				responseScoreResult.setResponseScore(responseScore);
				scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
				return ;
			}
			int insertUserScoreRows = scoreDaoImpl.insertUserScore(user.getUserId(), Integer.valueOf(param.getNum()));//创建用户积分记录
			if(insertUserScoreRows < 1){
				FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
				responseScore.setErrCode(FailCode.repquestError.failCode);
				responseScore.setErrMsg(FailCode.repquestError.desc);
				responseScore.setResponseDate(ScoreUtil.getTime());
				responseScoreResult.setResponseScore(responseScore);
				scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
				return ;
			}
			//兑入插入scoretrade表记录
			TbScoreTrade scoreTrade = new TbScoreTrade();
			scoreTrade.setTrader(Constants.CASHTRADER);
			scoreTrade.setType(Constants.ENTERTYPE);
			scoreTrade.setAmount(Integer.valueOf(param.getNum()));
			scoreTrade.setInOrOut(Constants.ENTERINOROUT);
			scoreTrade.setUserId(user.getUserId());
			scoreTrade.setCashMobile(param.getDeviceNo());
			scoreTrade.setTradeId(Constants.TRADEID_ENTER_USERID+userId);
			int insertTraRows = scoreDaoImpl.insertScoreTradeRecord(scoreTrade);//兑入积分插入scoretrade表记录
			if(insertTraRows < 1){
				FailCode.addFails(responseScoreResult.getResult(), FailCode.repquestError);
				responseScore.setErrCode(FailCode.repquestError.failCode);
				responseScore.setErrMsg(FailCode.repquestError.desc);
				responseScore.setResponseDate(ScoreUtil.getTime());
				responseScoreResult.setResponseScore(responseScore);
				scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);
				return ;
			}
			
		}
			//封装返回结果
			responseScore = new ResponseScore();
			responseScore.setAppCode(param.getAppCode());
			responseScore.setResponseDate(ScoreUtil.getTime());
			responseScore.setSign(param.getSign());
			responseScore.setErrCode(Constants.ENTER_STATE_SUCCESS);
			responseScore.setErrMsg(Constants.ENTER_SUCCESS);	
			responseScoreResult.setResponseScore(responseScore);
			
			//更新请求日志
			scoreDaoImpl.updateEnterInterfaceLogById(enterId, responseScore);

		
	}
		//封装积分兑出数据
	  public JSONObject createCashJson(ResponseCachResult responseCachResult,CachAmountCallParam param){
      	JSONObject json = new JSONObject();		
  		String appCode = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_cach_appcode", "0");
  		String singKey = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_cach_singkey", "0");
  		String spid = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_cach_spid", "0");
  		json.put("AppCode", appCode);//应用编号
  		json.put("RequestDate",ScoreUtil.getTime());  //请求时间
  		try {
  			String inStr = "AppCode="+appCode+"&"+"SingKey="+singKey+"&Date="+json.getString("RequestDate");
  			//String inStr = "AppCode="+Constants.APPCODE+"&"+"SingKey="+Constants.SINGKEY+"&Date="+json.getString("RequestDate");
  			json.put("Sign", ScoreUtil.md5Encode(inStr));//签名
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		json.put("SpID", spid);//业务编号
  		//json.put("SpID", Constants.SPID);//业务编号
  		String orderId = "DC"+json.getString("RequestDate");
  		json.put("OutOrderID", orderId);//外部订单号
  		json.put("DeviceNo", param.getMobile());//接收手机号
  		if(ScoreUtil.matchesPhoneNumber(param.getMobile()) == 1){
  			json.put("DeviceType", Constants.TYDEVICETYPE);//天翼设备类型
  		}else{
  			json.put("DeviceType", Constants.NOTTYDEVICETYPE);//移动or联通设备类型
  		}
  		json.put("ProvinceID", Constants.PROVINCEID);//省编号
  		json.put("CustID", "");//客户编号
  		json.put("Num", param.getCachAmount());//兑出积分额
  		json.put("Remark", Constants.REMARK);//备注
  		json.put("ActiveID", "");//翼积分发行编号
  		json.put("ExpTime", ScoreUtil.getLastTime());//过期时间月最后一天
		return json;
      }
	  
	//封装绑定账号数据
	  public JSONObject createAccountRelaJson(ResponseCachResult responseCachResult,String mobile,String cashMobile){
		  
      	JSONObject json = new JSONObject();	
      	String inStr = null;
  		json.put("AppCode", Constants.APPCODE);//应用编号
  		json.put("RequestDate",ScoreUtil.getTime());  //请求时间
  		String appCode = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_rela_appcode", "0");
  		String singKey = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_rela_singkey", "0");
  		String spid = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_rela_spid", "0");
  		try {
  			inStr = "AppCode="+appCode+"&"+"SingKey="+singKey+"&Date="+json.getString("RequestDate");
  			//String inStr = "AppCode="+Constants.APPCODE+"&"+"SingKey="+Constants.SINGKEY+"&Date="+json.getString("RequestDate");
  			json.put("Sign", ScoreUtil.md5Encode(inStr));//签名
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		json.put("SpID", spid);//业务编号
  		json.put("DeviceNo", cashMobile);//注册手机号
  		if(ScoreUtil.matchesPhoneNumber(cashMobile) == 1){
  			json.put("DeviceType", Constants.TYDEVICETYPE);//天翼设备类型
  		}else{
  			json.put("DeviceType", Constants.NOTTYDEVICETYPE);//移动or联通设备类型
  		}
  		json.put("OutCustID", mobile);//外部订单号
  		try {
  	  		String token = mobile+inStr;
			json.put("ToKen", ScoreUtil.md5Encode(token));//token
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  			
  		json.put("ExceedTime", ScoreUtil.getLastTime());//过期时间月最后一天
		return json;
      }

	@Override
	public void isUserIdRela(ScoreAccountResult scoreAccountResult,int userId) {
		ScoreAccount scoreAccount = null;
		boolean isState = false;
		//获取用户绑定账号关系
		TbScoreAccountRela tbsar = scoreDaoImpl.queryScoreAccountRela(userId);
		if (tbsar != null && tbsar.getUserId() == userId) {
			isState = true;
			 scoreAccount = ScoreUtil.process(tbsar);
		}	
		scoreAccountResult.setScoreAccount(scoreAccount);
		scoreAccountResult.setValue(isState);
	}

	@Override
	public void isAccountRela(ScoreAccountResult scoreAccountResult, String account) {
		ScoreAccount scoreAccount = null;
		boolean isState = false;
		//获取用户绑定账号关系
		TbScoreAccountRela tbsar = scoreDaoImpl.queryScoreAccountRela(account);
		if (tbsar != null && tbsar.getAccount().equals(account)) {
			isState = true;
			 scoreAccount = ScoreUtil.process(tbsar);
		}	
		scoreAccountResult.setScoreAccount(scoreAccount);
		scoreAccountResult.setValue(isState);
	}
	
	public boolean validIsRela(TbScoreAccountRela param){
		boolean isFlag = false;
		int count = scoreDaoImpl.queryScoreAccountRela(param);
		if(count > 0){
			isFlag = true;
		}
		return isFlag;	
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ,  rollbackFor = Exception.class)
	@Override
	public void relaAccountCall(ResponseRelaAcoountResult responseRelaAcoountResult, RelaAccountRequestParam param) {
		ResponseScore responseScore = new ResponseScore();
		//插入账号绑定请求信息记录
		int enterId = scoreDaoImpl.insertEnterRelaAccountlogRequest(param);
		if(enterId < 1){
			FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.repquestError);
			responseScore.setErrCode(FailCode.repquestError.failCode);
			responseScore.setErrMsg(FailCode.repquestError.desc);
			responseScore.setResponseDate(ScoreUtil.getTime());
			responseRelaAcoountResult.setResponseScore(responseScore);
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
            return ;
		}
				
		String singKey = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_enter_singkey", "0");
		if("0".equals(singKey)){//无权限访问此接口
			FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.repquestErrorNotSingkey);
			responseScore.setErrCode(FailCode.repquestErrorNotSingkey.failCode);
			responseScore.setErrMsg(FailCode.repquestErrorNotSingkey.desc);
			responseRelaAcoountResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
			return;
        }	
		String waitSign = "AppCode=" + Constants.ENTER_APPCODE + "&SingKey="+singKey + "&Date=" + param.getRequestDate();
		String sign = null;//加密后字符串
        try {
			 sign = ScoreUtil.md5Encode(waitSign);	
		} catch (Exception e) {
			logger.info("数据签名加密异常"+e.getMessage());
			e.printStackTrace();
		}
        
        if(!sign.equals(param.getSign())){//数字签名验证是否正确
			FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.repquestErrorSign);
			responseScore.setErrCode(FailCode.repquestErrorSign.failCode);
			responseScore.setErrMsg(FailCode.repquestErrorSign.desc);
			responseScore.setAppCode(param.getAppCode());
			responseScore.setSign(param.getSign());
			responseRelaAcoountResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
			return;
        }
		if(ScoreUtil.isMobile(param.getDeviceNo()) == false){//手机号是否为正确手机号
				FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.isNotMobile);
				responseScore.setErrCode(FailCode.isNotMobile.failCode);
				responseScore.setErrMsg(FailCode.isNotMobile.desc);
				responseScore.setAppCode(param.getAppCode());
				responseScore.setSign(param.getSign());
				responseRelaAcoountResult.setResponseScore(responseScore);
				responseScore.setResponseDate(ScoreUtil.getTime());
				scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
				return;
		}if(ScoreUtil.getExpTimeMatches(param.getExceedTime()) == false){//验证过期日期格式
			FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.requestCompareNull);
			responseScore.setErrCode(FailCode.requestCompareNull.failCode);
			responseScore.setErrMsg(FailCode.requestCompareNull.desc);
			responseScore.setAppCode(param.getAppCode());
			responseScore.setSign(param.getSign());
			responseRelaAcoountResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
			return;
		}
		if(ScoreUtil.getCompare(param.getRequestDate(),param.getExceedTime()) == false){//验证过期日期是否大于请求时间
			FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.requestCompareNull);
			responseScore.setErrCode(FailCode.requestCompareNull.failCode);
			responseScore.setErrMsg(FailCode.requestCompareNull.desc);
			responseScore.setAppCode(param.getAppCode());
			responseScore.setSign(param.getSign());
			responseRelaAcoountResult.setResponseScore(responseScore);
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
			return;
		}
		int userId = scoreDaoImpl.queryUserBymobile(param.getDeviceNo());//根据手机号查看用户是否存在
		if(userId > 0){
			TbScoreAccountRela tbsar = new TbScoreAccountRela();
        	tbsar.setAccount(param.getOutCustID());
        	tbsar.setUserId(userId);;
			Boolean flag = validIsRela(tbsar);
			if(flag){
				FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.repquestRelaAccountRepeatError);
				responseScore.setErrCode(FailCode.repquestRelaAccountRepeatError.failCode);
				responseScore.setErrMsg(FailCode.repquestRelaAccountRepeatError.desc);
				responseRelaAcoountResult.setResponseScore(responseScore);
				responseScore.setAppCode(param.getAppCode());
				responseScore.setSign(param.getSign());
				responseScore.setResponseDate(ScoreUtil.getTime());
				scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
				return;	
			}
			if(!flag){
	    		tbsar.setType("0");//兑入绑定
				scoreDaoImpl.insertScoreAccountRela(tbsar);
			}	
		}else{
			FailCode.addFails(responseRelaAcoountResult.getResult(), FailCode.repquestRelaAccountError);
			responseScore.setErrCode(FailCode.repquestRelaAccountError.failCode);
			responseScore.setErrMsg(FailCode.repquestRelaAccountError.desc);
			responseRelaAcoountResult.setResponseScore(responseScore);
			responseScore.setAppCode(param.getAppCode());
			responseScore.setSign(param.getSign());
			responseScore.setResponseDate(ScoreUtil.getTime());
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
			return;	
		}
			//封装返回结果
			responseScore = new ResponseScore();
			responseScore.setAppCode(param.getAppCode());
			responseScore.setResponseDate(ScoreUtil.getTime());
			responseScore.setSign(param.getSign());
			responseScore.setErrCode(Constants.RELA_STATE_SUCCESS);
			responseScore.setErrMsg(Constants.RELA_ACCOUNT_SUCCESS);
			responseRelaAcoountResult.setResponseScore(responseScore);
			scoreDaoImpl.updateEnterRelaAccountLogById(enterId, responseScore);
		
	}
}


