package com.jfshare.score.dao.mysql;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.score.CachAmountCallParam;
import com.jfshare.finagle.thrift.score.RelaAccountRequestParam;
import com.jfshare.finagle.thrift.score.ResponseScore;
import com.jfshare.finagle.thrift.score.ScoreRequestParam;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreTradeQueryParam;
import com.jfshare.finagle.thrift.score.ScoreUserQueryParam;
import com.jfshare.score.model.TbScoreAccountRela;
import com.jfshare.score.model.TbScoreTrade;
import com.jfshare.score.model.TbScoreUser;
import com.jfshare.score.model.TbUser;
import com.jfshare.score.model.TbUserInfo;


public interface IScoreDao {
	int insertUserScore(int userId, int count);
	int queryUserScore(int userId);
	TbScoreUser selectUserScoreByUserId(int userId);
	int updateUserScore(int userId, int count,int version);
	int insertScoreTradeRecord(TbScoreTrade scoreTrade);
	int queryScoreTrade(ScoreTrade scoreTrade);
	List<TbScoreTrade> queryScoreTrade(ScoreTradeQueryParam param);
	List<TbUserInfo> queryScoreUser(ScoreUserQueryParam param);
	int updateByPrimaryKey(TbScoreUser tbScoreUser);
	int queryUserBymobile(String mobile);//根据手机号查询用户是否存在
	int insertUser(TbUser user);
	int selectUser(int userId);
	//根据用户ID查看手机号
	String selectUserMobile(int userId);
	
	//积分兑出记录请求日志
	int insertCashRequestJson(JSONObject json);
	int updateCashInterfaceLogById(int id,String reponse);
	
	//积分兑入记录请求日志
	int insertEnterRequest(ScoreRequestParam param);
	int updateEnterInterfaceLogById(int id,ResponseScore reponse);



	//调用账号绑定记录请求日志
	int insertCashRelaAccountRequestJson(JSONObject json);
	int updateCashRelaAccountLogById(int id,String reponse);
	
	//调用账号绑定记录请求日志
	int insertEnterRelaAccountlogRequest(RelaAccountRequestParam param);
	int updateEnterRelaAccountLogById(int id,ResponseScore reponse);
	
	//查看积分兑出用户账号绑定关系by用户ID
	TbScoreAccountRela queryScoreAccountRela(int userId);
	
	//查看积分兑出用户账号绑定关系by账号
	TbScoreAccountRela queryScoreAccountRela(String account);
	
	//查看积分兑出用户账号绑定关系
	int queryScoreAccountRela(TbScoreAccountRela tbsar);
	
	//添加用户绑定关系
	int insertScoreAccountRela(TbScoreAccountRela tbsar);
	
	//更新用户绑定状态
	int updateScoreAccountRela(CachAmountCallParam param);
	
}
