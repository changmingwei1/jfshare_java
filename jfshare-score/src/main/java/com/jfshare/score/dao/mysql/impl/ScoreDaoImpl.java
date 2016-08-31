package com.jfshare.score.dao.mysql.impl;


import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.score.CachAmountCallParam;
import com.jfshare.finagle.thrift.score.RelaAccountRequestParam;
import com.jfshare.finagle.thrift.score.ResponseScore;
import com.jfshare.finagle.thrift.score.ScoreRequestParam;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreTradeQueryParam;
import com.jfshare.finagle.thrift.score.ScoreUserQueryParam;
import com.jfshare.score.dao.mysql.IScoreDao;
import com.jfshare.score.model.TbRequestInterfaceLog;
import com.jfshare.score.model.TbScoreAccountRela;
import com.jfshare.score.model.TbScoreAccountRelaExample;
import com.jfshare.score.model.TbScoreAccountRelaLog;
import com.jfshare.score.model.TbScoreTrade;
import com.jfshare.score.model.TbScoreTradeExample;
import com.jfshare.score.model.TbScoreUser;
import com.jfshare.score.model.TbScoreUserExample;
import com.jfshare.score.model.TbUser;
import com.jfshare.score.model.TbUserInfo;
import com.jfshare.score.model.TbUserInfoExample;
import com.jfshare.score.model.mapper.TbRequestInterfaceLogMapper;
import com.jfshare.score.model.mapper.TbScoreAccountRelaLogMapper;
import com.jfshare.score.model.mapper.TbScoreAccountRelaMapper;
import com.jfshare.score.model.mapper.TbScoreTradeMapper;
import com.jfshare.score.model.mapper.TbScoreUserMapper;
import com.jfshare.score.model.mapper.TbUserInfoMapper;
import com.jfshare.score.model.mapper.TbUserMapper;
import com.jfshare.utils.StringUtil;

@Repository
public class ScoreDaoImpl implements IScoreDao {
	@Autowired
	private TbScoreTradeMapper scoreTradeMapper;
	@Autowired
	private TbScoreUserMapper scoreUserMapper;
	@Autowired
	private TbUserInfoMapper userInfoMapper;
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private TbRequestInterfaceLogMapper requersInterfaceLogMapper;
	
	@Autowired
	private TbScoreAccountRelaLogMapper tbScoreAccountRelaLogMapper;
	
	@Autowired
	private TbScoreAccountRelaMapper scoreAccountRelaMapper;
	
	DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");//日期格式化
	DateTimeFormatter format2 = DateTimeFormat.forPattern("yyyy-MM-dd");//日期格式化
	DateTimeFormatter format3 = DateTimeFormat.forPattern("yyyyMMddHHmmss");//日期格式化
	@Override
	public int insertUserScore(int userId, int amount) {
		TbScoreUser score = new TbScoreUser();
		score.setAmount(amount);
		score.setUserId(userId);
				
		return scoreUserMapper.insertSelective(score);
	}

	@Override
	public int queryUserScore(int userId) {
		TbScoreUserExample example = new TbScoreUserExample();
		TbScoreUserExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		TbScoreUser score = scoreUserMapper.selectByPrimaryKey(userId);
		if(!StringUtil.isNullOrEmpty(score)){
			return score.getAmount();
		}
		return -100;
	}

	@Override
	public int updateUserScore(int userId, int count,int version) {
		TbScoreUserExample example = new TbScoreUserExample();
		TbScoreUserExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andVersionEqualTo(version);
		TbScoreUser score = new TbScoreUser();
		score.setAmount(count);
		score.setVersion(version+1);
		return scoreUserMapper.updateByExampleSelective(score, example);
	}

	@Override
	public int insertScoreTradeRecord(TbScoreTrade scoreTrade) {
		
		return scoreTradeMapper.insertSelective(scoreTrade);
	}

	@Override
	public List<TbScoreTrade> queryScoreTrade(ScoreTradeQueryParam param) {
		TbScoreTradeExample example = new TbScoreTradeExample();
		TbScoreTradeExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(param.getUserId());
		if(param.getInOrOut() > 0) {
			criteria.andInOrOutEqualTo(param.getInOrOut());
		}
//		if(!StringUtil.isNullOrEmpty(param.getStartTime())){
//			criteria.andTradeTimeLessThanOrEqualTo(DateTime.now());
//		}
//		if(!StringUtil.isNullOrEmpty(param.getStartTime())  && !StringUtil.isNullOrEmpty(param.getEndTime())){
//			criteria.andTradeTimeBetween(new DateTime(param.getStartTime()),new DateTime(param.getEndTime()));
//		}
		if(param.getType() > 0){
			if(param.getType() == 99){
				List<Integer> values = new ArrayList<Integer>();
				values.add(3);
				values.add(4);
				criteria.andTypeIn(values);
			}else{
				criteria.andTypeEqualTo(param.getType());
			}
		}
		if(!StringUtil.isNullOrEmpty(param.getStartTime())){
			criteria.andTradeTimeGreaterThanOrEqualTo(DateTime.parse(param.getStartTime(),format));
		}
		if(!StringUtil.isNullOrEmpty(param.getEndTime())){
			criteria.andTradeTimeLessThanOrEqualTo(DateTime.parse(param.getEndTime(),format));
		}
		example.setOrderByClause("trade_time desc");
		List<TbScoreTrade> scoreTradeRecores =  scoreTradeMapper.selectByExample(example);
		return scoreTradeRecores;
	}

	@Override
	public List<TbUserInfo> queryScoreUser(ScoreUserQueryParam param) {
		TbUserInfoExample example = new TbUserInfoExample();
		TbUserInfoExample.Criteria criteria = example.createCriteria();
		if(param.getUserId() > 0) {
			criteria.andUserIdEqualTo(param.getUserId());
		}
		if(!StringUtil.isNullOrEmpty(param.getMobile())){
			criteria.andMobileEqualTo(param.getMobile());
		}
		if(!StringUtil.isNullOrEmpty(param.getStartTime())){
			criteria.andCreateTimeGreaterThanOrEqualTo(DateTime.parse(param.getStartTime(), format));	  
		}
		if(!StringUtil.isNullOrEmpty(param.getEndTime())){
			criteria.andCreateTimeLessThanOrEqualTo(DateTime.parse(param.getEndTime(), format));
		}
		if(param.getAmount() == 1){
			criteria.andAmountEqualToZreo(0);
		}if(param.getAmount() == 2){
			criteria.andAmountGreaterThanZero(0);
		}
		example.setOrderByClause("create_time desc");
		List<TbUserInfo> scoreUserInfoRecores =  userInfoMapper.selectUserInfo(example);
		return scoreUserInfoRecores;
	}
	
	/* tb_user表兑出积分扣除20160512_2 */
	@Override
	public int updateByPrimaryKey(TbScoreUser parm) {
//		TbScoreUserExample example = new TbScoreUserExample();
//		TbScoreUserExample.Criteria criteria = example.createCriteria();
//		criteria.andUserIdEqualTo(parm.getUserId());
//		criteria.andAmountEqualTo(parm.getAmount());
		int rows = scoreUserMapper.updateByPrimaryKeyCashAmount(parm);
		return rows;
	}

	@Override
	public int queryUserBymobile(String mobile) {
		TbUser user = userMapper.selectByMobile(mobile);
		if(user != null){
			return user.getUserId();
		}
		return -100;
		
	}

	@Override
	public int insertUser(TbUser user) {
		int rows =userMapper.insert(user);
		if(rows > 0 ){
			return rows;
		}
		return -100;
	}
	@Override
	public int selectUser(int userId) {
		TbUser user =userMapper.selectByPrimaryKey(userId);
		if(user != null ){
			return user.getUserId();
		}
		return -100;
	}
	
	@Override
	public String selectUserMobile(int userId) {
		TbUser user =userMapper.selectByPrimaryKey(userId);
		if(user != null ){
			return user.getMobile();
		}
		return null;
	}

	@Override
	public TbScoreUser selectUserScoreByUserId(int userId) {
		TbScoreUserExample example = new TbScoreUserExample();
		TbScoreUserExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		TbScoreUser scoreUser = scoreUserMapper.selectByPrimaryKey(userId);
		return scoreUser;
	}
	
	//兑入积分操作tb_request_interface_
	@Override
	public int insertEnterRequest(ScoreRequestParam param) {
		TbRequestInterfaceLog req = new TbRequestInterfaceLog();
		req.setAppcode(param.getAppCode());
		req.setRequestdate(DateTime.parse(param.getRequestDate(), format3));
		req.setSign(param.getSign());
		req.setSpid(param.getSpID());
		req.setOutorderid(param.getOutOrderID());
		req.setDeviceno(param.getDeviceNo());
		req.setDevicetype(param.getDeviceType());
		req.setProvinceid(param.getProvinceID());
		req.setCustid(param.getCustID());
		req.setNum(param.getNum());
		req.setRemark(param.getRemark());
		req.setActiveid(param.getActiveID());
		req.setExptime(DateTime.parse(param.getExpTime(), format2));
		req.setType("0");//兑入类型
		req.setSysname("天翼");
		req.setSyscode("TY000001");
		int rows = requersInterfaceLogMapper.insert(req);
		if(rows > 0){
			return req.getId();
		}
		return -100;
	}

	@Override
	public int insertCashRequestJson(JSONObject json) {
		TbRequestInterfaceLog req = new TbRequestInterfaceLog();
		req.setAppcode(json.getString("AppCode"));//应用编号
		req.setRequestdate(DateTime.parse(json.getString("RequestDate"), format3));
		req.setSign(json.getString("Sign"));//签名
		req.setSpid(json.getString("SpID"));//业务编号
		req.setOutorderid(json.getString("OutOrderID"));//外部订单号
		req.setDeviceno(json.getString("DeviceNo"));//接收手机号
		req.setDevicetype(json.getString("DeviceType"));//设备类型
		req.setProvinceid(json.getString("ProvinceID"));//省编号
		req.setCustid(json.getString("CustID"));//客户编号
		req.setNum(json.getString("Num"));//兑出积分额
		req.setRemark(json.getString("Remark"));//备注
		req.setActiveid(json.getString("ActiveID"));//翼积分发行编号
		req.setExptime(new DateTime(DateTime.parse(json.getString("ExpTime"),format2)));//过期时间
		req.setType("1");//兑出类型
		req.setSysname("天翼");
		req.setSyscode("TY000001");
		int rows = requersInterfaceLogMapper.insert(req);
		if(rows > 0){
			return req.getId();
		}
		return -100;
	}

	@Override
	public int updateCashInterfaceLogById(int id,String reponse) {
		TbRequestInterfaceLog req = new TbRequestInterfaceLog();
		JSONObject json = (JSONObject) JSONObject.parse(reponse);
		req.setReperrcode(json.getString("ErrCode"));//响应编码
		req.setReperrmsg(json.getString("ErrMsg"));//响应描述
		req.setId(id);
		int rows = requersInterfaceLogMapper.updateByPrimaryKeySelective(req);
		if(rows > 0 ){
			return rows;
		}
		return -100;
	}
	
	@Override
	public int updateEnterInterfaceLogById(int id,ResponseScore reponse) {
		TbRequestInterfaceLog req = new TbRequestInterfaceLog();
		req.setReperrcode(reponse.getErrCode());//响应编码
		req.setReperrmsg(reponse.getErrMsg());//响应描述
		req.setId(id);
		int rows = requersInterfaceLogMapper.updateById(req);
		if(rows > 0 ){
			return rows;
		}
		return -100;
	}

	@Override
	public int queryScoreTrade(ScoreTrade scoreTrade) {
		TbScoreTradeExample example = new TbScoreTradeExample();
		TbScoreTradeExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(scoreTrade.getUserId());
		criteria.andInOrOutEqualTo(scoreTrade.getInOrOut());
		criteria.andTypeEqualTo(scoreTrade.getType());
		criteria.andTraderEqualTo(scoreTrade.getTrader());
		criteria.andTraderIdEqualTo(scoreTrade.getTradeId());
		List<TbScoreTrade> scoreTradeRecores =  scoreTradeMapper.selectByExample(example);
		if(scoreTradeRecores.size() > 0){
			return scoreTradeRecores.size();
		}
		return -100;
	}

	@Override
	public TbScoreAccountRela queryScoreAccountRela(int userId) {
		
		TbScoreAccountRelaExample example = new TbScoreAccountRelaExample();
		TbScoreAccountRelaExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<TbScoreAccountRela> listSAR = scoreAccountRelaMapper.selectByExample(example);
		if(listSAR.size() > 0){
			TbScoreAccountRela scoreAccountRela = listSAR.get(0);
			return scoreAccountRela;
		}
		return null;
	}

	@Override
	public TbScoreAccountRela queryScoreAccountRela(String account) {
		TbScoreAccountRelaExample example = new TbScoreAccountRelaExample();
		TbScoreAccountRelaExample.Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(account);
		List<TbScoreAccountRela> listSAR = scoreAccountRelaMapper.selectByExample(example);
		if(listSAR.size() > 0){
			TbScoreAccountRela scoreAccountRela = listSAR.get(0);
			return scoreAccountRela;
		}
		return null;
	}

	@Override
	public int queryScoreAccountRela(TbScoreAccountRela tbsar) {
		TbScoreAccountRelaExample example = new TbScoreAccountRelaExample();
		TbScoreAccountRelaExample.Criteria criteria = example.createCriteria();
		criteria.andAccountEqualTo(tbsar.getAccount());
		criteria.andUserIdEqualTo(tbsar.getUserId());
		List<TbScoreAccountRela> listSAR = scoreAccountRelaMapper.selectByExample(example);
		return listSAR.size();
	}

	@Override
	public int insertScoreAccountRela(TbScoreAccountRela tbsar) {
		
		int rows = scoreAccountRelaMapper.insert(tbsar);
		return rows;
	}

	@Override
	public int updateScoreAccountRela(CachAmountCallParam param) {
		
		TbScoreAccountRelaExample example = new TbScoreAccountRelaExample();
		TbScoreAccountRelaExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(param.getUserId());
		criteria.andAccountEqualTo(param.getMobile());
		TbScoreAccountRela tbsar = new TbScoreAccountRela();
		tbsar.setState("1");//账号绑定状态 1代表已绑定
		int rows = scoreAccountRelaMapper.updateByExample(tbsar, example);
		return rows;
	}

	@Override
	public int insertEnterRelaAccountlogRequest(RelaAccountRequestParam param) {
		TbScoreAccountRelaLog req = new TbScoreAccountRelaLog();
		req.setAppcode(param.getAppCode());//应用编号
		req.setRequestdate(DateTime.parse(param.getRequestDate(), format3));
		req.setSign(param.getSign());//签名
		req.setSpid(param.getSpID());//业务编号
		req.setDeviceno(param.getDeviceNo());//接收手机号
		req.setDevicetype(param.getDeviceType());//设备类型
		req.setOutcustid(param.getOutCustID());//客户编号
		req.setToken(param.getToKen());//Token
		req.setExceedtime(new DateTime(DateTime.parse(param.getExceedTime(),format2)));//过期时间
		req.setType("0");//兑出类型
		req.setSysname("天翼");
		req.setSyscode("TY000001");
		int rows = tbScoreAccountRelaLogMapper.insert(req);
		if(rows > 0){
			return req.getId();
		}
		return -100;
	}
	@Override
	public int updateCashRelaAccountLogById(int reaId, String reponse) {
		TbScoreAccountRelaLog req = new TbScoreAccountRelaLog();
		JSONObject json = (JSONObject) JSONObject.parse(reponse);
		req.setReperrcode(json.getString("ErrCode"));//响应编码
		req.setReperrmsg(json.getString("ErrMsg"));//响应描述
		req.setId(reaId);
		int rows = tbScoreAccountRelaLogMapper.updateByPrimaryKeySelective(req);
		if(rows > 0 ){
			return rows;
		}
		return -100;
	}
	@Override
	public int insertCashRelaAccountRequestJson(JSONObject json) {
		TbScoreAccountRelaLog req = new TbScoreAccountRelaLog();
		req.setAppcode(json.getString("AppCode"));//应用编号
		req.setRequestdate(DateTime.parse(json.getString("RequestDate"), format3));
		req.setSign(json.getString("Sign"));//签名
		req.setSpid(json.getString("SpID"));//业务编号
		req.setDeviceno(json.getString("DeviceNo"));//接收手机号
		req.setDevicetype(json.getString("DeviceType"));//设备类型
		req.setOutcustid(json.getString("OutCustID"));//客户编号
		req.setToken(json.getString("ToKen"));//Token
		req.setExceedtime(new DateTime(DateTime.parse(json.getString("ExceedTime"),format2)));//过期时间
		req.setType("1");//兑出类型
		req.setSysname("天翼");
		req.setSyscode("TY000001");
		int rows = tbScoreAccountRelaLogMapper.insert(req);
		if(rows > 0){
			return req.getId();
		}
		return -100;
	}

	@Override
	public int updateEnterRelaAccountLogById(int reaId, ResponseScore reponse) {
		TbScoreAccountRelaLog req = new TbScoreAccountRelaLog();
		req.setReperrcode(reponse.getErrCode());//响应编码
		req.setReperrmsg(reponse.getErrMsg());//响应描述
		req.setId(reaId);
		int rows = tbScoreAccountRelaLogMapper.updateByPrimaryKeySelective(req);
		if(rows > 0 ){
			return rows;
		}
		return -100;
	}
}
