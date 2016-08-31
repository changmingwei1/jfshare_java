package com.jfshare.buyer.service.impl;

import com.alibaba.fastjson.JSON;
import com.jfshare.buyer.dao.mysql.IUserDao;
import com.jfshare.buyer.dao.redis.IUserJedis;
import com.jfshare.buyer.model.TbThirdUserinfo;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.TbUserThirdparty;
import com.jfshare.buyer.model.ThirdPartyType;
import com.jfshare.buyer.server.ServHandle;
import com.jfshare.buyer.service.IBuyerSvc;
import com.jfshare.buyer.util.*;
import com.jfshare.finagle.thrift.buyer.AuthInfo;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.buyer.BuyerListResult;
import com.jfshare.finagle.thrift.buyer.H5ThirdLoginParam;
import com.jfshare.finagle.thrift.buyer.H5ThirdLoginResult;
import com.jfshare.finagle.thrift.buyer.LoginLog;
import com.jfshare.finagle.thrift.buyer.ThirdpartyUser;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.*;

import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class BuyerSvcImpl implements IBuyerSvc {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);

	@Autowired
	private IUserDao userDaoImpl;
	@Autowired
	private IUserJedis userJedisImpl;

	@Override
	public boolean userIsExist(String loginName) {
		int count = userDaoImpl.userIsExist(loginName);

		return count > 0 ? true : false;
	}

	@Override
	public void validUser(Result result, Buyer buyer) {
		if (StringUtil.isNullOrEmpty(buyer.getLoginName()) || buyer.getLoginName().length() < 6
				|| buyer.getLoginName().length() > 20 || buyer.getLoginName().matches("!^[a-zA-Z0-9_]+$")) {
			FailCode.addFails(result, FailCode.loginNameInvalid);
		}
		if (StringUtil.isNullOrEmpty(buyer.getPwdEnc()) || buyer.getPwdEnc().length() < 6
				|| buyer.getPwdEnc().length() > 20) {
			FailCode.addFails(result, FailCode.pwdInvalid);
		}

		if (result.getFailDescList() != null && !result.getFailDescList().isEmpty()) {
			return;
		}

		if (userIsExist(buyer.getLoginName().trim())) {
			FailCode.addFails(result, FailCode.loginNameHasExist);
		}
	}

	@Override
	public void validPwd(Result result, Buyer buyer) {
		List<TbUser> users = userDaoImpl.checkLogin(buyer);
		if (users.size() != 1) {
			FailCode.addFails(result, FailCode.pwdInvalid);
		}
	}

	@Override
	public int insert(Buyer buyer) {
		TbUser user = new TbUser();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(buyer);
		BeanUtil.fillBeanData(user, stringObjectMap);
		user.setPwdEnc(CryptoUtil.encryptDBPwd(buyer.getPwdEnc().trim()));
		user.setBirthday(DateUtils.strToDateTime(buyer.getBirthday(), DateUtils.Simple_DateTime_Format));
		user.setCreateTime(new DateTime());
		return userDaoImpl.insert(user);
	}

	@Override
	public void validLogin(Result result, Buyer buyer) {
		if (StringUtil.isNullOrEmpty(buyer.getLoginName()) || buyer.getLoginName().length() < 6) {
			FailCode.addFails(result, FailCode.loginNameInvalid);
		}
		if (StringUtil.isNullOrEmpty(buyer.getPwdEnc())) {
			FailCode.addFails(result, FailCode.pwdInvalid);
		}

		int tryFailCount = userJedisImpl.getTryFailCount(buyer.getLoginName().trim());
		if (tryFailCount >= ConvertUtil.getInt(PropertiesUtil.getProperty("jfx_buyer_serv", "try_login_limit", "5"))) {
			FailCode.addFails(result, FailCode.tryTimeLimitError);
			return;
		}
		List<TbUser> users = userDaoImpl.checkLogin(buyer);
		if (users.size() != 1) {
			FailCode.addFails(result, FailCode.loginError);
			return;
		}

		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(users.get(0));
		BeanUtil.fillBeanData(buyer, stringObjectMap);
		//DateTime类型的字符串截取
		if(!StringUtil.isNullOrEmpty(buyer.getBirthday()))
		buyer.setBirthday(buyer.getBirthday().substring(0, 10)+" "+buyer.getBirthday().substring(11, 19));
	}

	@Override
	public int addTryFail(Buyer buyer) {
		TbUser user = new TbUser();
		user.setLoginName(buyer.getLoginName().trim());
		return userJedisImpl.addTryFail(user);
	}

	@Override
	public void addOnline(TbUser user, LoginLog loginLog) {
		logger.error("添加在线token开始-------------------！user=" + user.getUserId());
		userJedisImpl.addOnline(user, loginLog.getTokenId());
	}
	

	@Override
	public int clearTryFail(TbUser user) {
		if (user == null || StringUtil.isNullOrEmpty(user.getLoginName())) {
			return 0;
		}
		return userJedisImpl.clearTryFail(user.getLoginName());
	}

	@Override
	public boolean isOnline(int userId, String tokenId) {
		boolean isOnline = false;
		int signinModel = NumberUtils
				.toInt(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, ConstantUtil.SIGNIN_MODEL), 0);

		if (signinModel == ConstantUtil.SIGNIN_MODEL_SINGLE) {
			isOnline = userJedisImpl.isOnline(ConvertUtil.getString(userId), tokenId);
		} else {
			//TbUser tbUser = userJedisImpl.getOnlineBySID(tokenId);
			//获取用户信息
			TbUser tbUser = userJedisImpl.getOnlineByUID(ConvertUtil.getString(userId));
			if (tbUser != null && tbUser.getUserId().intValue() == userId) {
				isOnline = true;
			}
		}
		return isOnline;
	}

	@Override
	public Buyer getOnline(int userId, String tokenId, LoginLog loginLog) {
//		TbUser tbUser = userJedisImpl.getOnlineBySID(tokenId);
//		if (tbUser == null) {
//			tbUser = userJedisImpl.getOnlineByUID(String.valueOf(userId),loginLog );
//		}
		//获取用户个人信息
		TbUser tbUser = userJedisImpl.getOnlineByUID(String.valueOf(userId));
		if (!StringUtil.isNullOrEmpty(tbUser) && !StringUtil.isNullOrEmpty(tbUser.getUserId())) {
			if (tbUser.getUserId() == userId) {
				Buyer buyer = new Buyer();
				Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
				BeanUtil.fillBeanData(buyer, stringObjectMap);
				if (tbUser.getBirthday() != null) {
					buyer.setBirthday(DateUtils.dateToStrLong(tbUser.getBirthday().toDate()));
				}
				return buyer;
			}
		}

		return null;
	}

	@Override
	public Buyer getBuyer(int userId) {
		TbUser tbUser = userDaoImpl.getBuyer(userId);
		if (!StringUtil.isNullOrEmpty(tbUser) && !StringUtil.isNullOrEmpty(tbUser.getUserId())) {
			Buyer buyer = new Buyer();
			tbUser.setPwdEnc("******");
			Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
			BeanUtil.fillBeanData(buyer, stringObjectMap);
			if (tbUser.getBirthday() != null) {
				buyer.setBirthday(DateUtils.dateToStrLong(tbUser.getBirthday().toDate()));
			}
			return buyer;
		}

		return null;
	}

	@Override
	public boolean updatePwd(int userId, String newPwd) {
		Buyer buyer = new Buyer();
		buyer.setUserId(userId);
		buyer.setPwdEnc(CryptoUtil.encryptDBPwd(newPwd.trim()));
		return this.updateBuyer(buyer);
	}

	@Override
	public boolean updateBuyer(Buyer buyer) {
		TbUser tbUser = new TbUser();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(buyer);
		BeanUtil.fillBeanData(tbUser, stringObjectMap);
		tbUser.setUserId(buyer.getUserId());
		if (!StringUtil.isNullOrEmpty(buyer.getBirthday())) {
			tbUser.setBirthday(DateUtils.strToDateTime(buyer.getBirthday(), DateUtils.PATTERN_YYYYMMDD));
		}
		return userDaoImpl.update(tbUser) > 0;
	}

	@Override
	public void removeOnline(Result result, int userId, String tokenId) {
		//int ret = userJedisImpl.removeOnline(ConvertUtil.getString(userId), tokenId);
		//删除redis用户个人信息和token根据userId
		int ret = userJedisImpl.removeOnline(ConvertUtil.getString(userId));
		if (ret == 0) {
			FailCode.addFails(result, FailCode.noOnlineUser);
		} else if (ret == -1) {
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
	}

	@Override
	public Buyer checkThirdCustomId(ThirdpartyUser thirdUser) {
		Buyer buyer = null;
		TbUserThirdparty tbUserThirdparty = new TbUserThirdparty();
		tbUserThirdparty.setThirdType(ThirdPartyType.valueOf(thirdUser.getThirdType()).getCode());
		tbUserThirdparty.setAccountNo(thirdUser.getAccountNo());
		TbUser tbUser = userDaoImpl.getBuyerByThirdUser(tbUserThirdparty);
		if (tbUser != null) {
			buyer = BuyerUtil.tbUser2Buyer(tbUser);
		}
		return buyer;
	}

	@Override
	public void insertThirdPartyRel(ThirdpartyUser thirdUser, int userId) {
		TbUserThirdparty tbUserThirdparty = new TbUserThirdparty();
		tbUserThirdparty.setUserName(thirdUser.getUserName());
		tbUserThirdparty.setCustId(thirdUser.getCustId());
		tbUserThirdparty.setAccountNo(thirdUser.getAccountNo());
		tbUserThirdparty.setUserId(userId);
		tbUserThirdparty.setThirdType(ThirdPartyType.valueOf(thirdUser.getThirdType()).getCode());
		tbUserThirdparty.setExtInfo(thirdUser.getExtInfo());
		userDaoImpl.insertThirdParty(tbUserThirdparty);
	}

	@Override
	public ThirdpartyUser queryThirdPartyInfo(int userId, int thirdPartyType) {
		ThirdpartyUser thirdpartyUser = null;
		TbUserThirdparty tbUserThirdparty = userDaoImpl.getThirdParty(userId, thirdPartyType);
		if (tbUserThirdparty != null) {
			thirdpartyUser = new ThirdpartyUser();
			thirdpartyUser.setAccountNo(tbUserThirdparty.getAccountNo());
			thirdpartyUser.setUserName(tbUserThirdparty.getUserName());
			thirdpartyUser.setCustId(tbUserThirdparty.getCustId());
			thirdpartyUser.setThirdType(String.valueOf(tbUserThirdparty.getThirdType()));
			thirdpartyUser.setExtInfo(tbUserThirdparty.getExtInfo());
		}
		return thirdpartyUser;
	}

	@Override
	public Buyer getBuyerByLoginName(String loginName) {
		TbUser tbUser = userDaoImpl.getBuyerByLoginName(loginName);
		if (!StringUtil.isNullOrEmpty(tbUser) && !StringUtil.isNullOrEmpty(tbUser.getUserId())) {
			Buyer buyer = new Buyer();
			tbUser.setPwdEnc("******");
			Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
			BeanUtil.fillBeanData(buyer, stringObjectMap);
			if (tbUser.getBirthday() != null) {
				buyer.setBirthday(DateUtils.dateToStrLong(tbUser.getBirthday().toDate()));
			}
			return buyer;
		}

		return null;
	}

	@Override
	@Transactional
	public Buyer createUserThird(ThirdpartyUser thirdUser) throws Exception {
		Buyer buyer = new Buyer();
		int thirdType = ThirdPartyType.valueOf(thirdUser.getThirdType()).getCode();
		String loginName = RLoginName.getRLoginName(thirdType);
		buyer.setUserName(thirdUser.getUserName());
		buyer.setLoginName(loginName);
		buyer.setState(1);	//天翼用户
		buyer.setBirthday("1990-01-01 00:00:00");
		buyer.setPwdEnc(JSON.toJSONString(thirdUser));
		String deviceNo = JSON.parseObject(thirdUser.getExtInfo()).getString("deviceNo");
		if(deviceNo.contains("@") || deviceNo.length() > 20) {
			buyer.setEmail(deviceNo);
		} else {
			buyer.setMobile(deviceNo);
		}
		int userId = this.insert(buyer);
		buyer.setUserId(userId);
		Buyer newBuyer = this.getBuyerByLoginName(buyer.getLoginName());
		this.insertThirdPartyRel(thirdUser, newBuyer.getUserId());

		return newBuyer;
	}

	@Override
	public AuthInfo createAuth(String userId, String mobile, String email, String appId,int clientType) throws Exception {
		AuthInfo authInfo = new AuthInfo();
		String time = DateUtils.fullTime();
		logger.info("登陆开始准备！userId为:"+ userId);
		logger.info("登陆开始准备！mobile为:"+ mobile);
		logger.info("登陆开始准备！email为:"+ email);
		logger.info("登陆开始准备！browser为:"+ appId);
		logger.info("登陆开始准备！time为:"+ time);
		logger.info("登陆开始准备！clientType为:"+ clientType);
		String token = AuthenticationUtil.getToken(userId, mobile, email, appId, time);
		String ppInfo = AuthenticationUtil.getPPInfo(userId, mobile, email);
		

		authInfo.setToken(token);
		authInfo.setPpInfo(ppInfo);
		String clientFlag = null;
		//保证android和ios只能登陆一个
		if(clientType ==2 || clientType ==1 ){
			 clientFlag = "Mobile";
		}if(clientType ==3){
			clientFlag = "H5";
		}if(clientType ==4){
			clientFlag = "Web";
		}
		userJedisImpl.setTokenTimestamp(userId, time,clientFlag);
		logger.info("登陆加密后！token为:"+ token);
		logger.info("登陆加密后！ppInfo为:"+ ppInfo);
		return authInfo;
		
	}
	@Override
	public boolean verificationToken(String uid, AuthInfo authInfo, String appId,int clientType) throws Exception {
		String clientFlag = null;
		//保证android和ios只能登陆一个
		if(clientType ==2 || clientType ==1 ){
			 clientFlag = "Mobile";
		}if(clientType ==3){
			clientFlag = "H5";
		}if(clientType ==4){
			clientFlag = "Web";
		}
		String time = userJedisImpl.getTokenTimestamp(uid,clientFlag);
		boolean flg = AuthenticationUtil.tokenVerification(authInfo.getToken(), authInfo.ppInfo, appId, time);
		
		return flg;
	}
	@Override
	public boolean verificationTokenByonline(LoginLog loginLog, AuthInfo authInfo) throws Exception {
		boolean falg = userJedisImpl.isOnlineToken(String.valueOf(loginLog.getUserId()),authInfo.getToken(),loginLog.getClientType());		
		return falg;
	}
	@Override
	public boolean userIsExistForThird(String cust_id, int thirdType) {
		int count = userDaoImpl.userIsExistForThird(cust_id,thirdType);

		return count > 0 ? true : false;
	}
	@Override
	public List<TbUser> selectByUserPK(TbThirdUserinfo ttu){
		
		return userDaoImpl.selectByUserPK(ttu);
		
	}
	@Override
	@Transactional
	public TbUser addUserAndThirdUser(TbThirdUserinfo ttu,TbUser tu){
		
		String mobile=ttu.getMobile();
		int thirdType=ttu.getThirdType();
		
		//判断手机号是否已经绑定
		if(userDaoImpl.userIsExistForMobile(mobile, thirdType)>0){
			TbUser tbu=new TbUser();
			tbu.setUserId(-1);
			return tbu;
		}
		
		List<TbUser> tbUsers=userDaoImpl.userExistForMoblie(mobile);
		if(tbUsers.size()==0){//user表中无数据
			
			int userId=userDaoImpl.insertUser(tu);//返回刚才插入的key
			
			ttu.setUserId(tu.getUserId());
			
			userDaoImpl.insertThirdUserInfo(ttu);
			
			tu.setUserId(tu.getUserId());
			return tu;
			
		}else{//只插数据到第三方表
			
			ttu.setUserId(tbUsers.get(0).getUserId());
			
			userDaoImpl.insertThirdUserInfo(ttu);
			
			return tbUsers.get(0);
		}
		
	}

	@Override
	public void getListBuyer(BuyerListResult buyerListResult,List<Integer> userIdList) {
		List<TbUser> tbUsers = userDaoImpl.getListBuyer(userIdList);
		List<Buyer> buyerList = new ArrayList<Buyer>();
		if(!tbUsers.isEmpty()){
			for(TbUser tbUser : tbUsers){
				Buyer Buyer = BuyerUtil.tbUser2Buyer(tbUser);
				buyerList.add(Buyer);
			}
			buyerListResult.setBuyerList(buyerList);
		}
	}

	@Override
	public void addOnlineToken(TbUser user, LoginLog loginLog) {
		logger.error("添加在线token-------------------！userId=" + user.getUserId() +"tokenId=" +loginLog.getTokenId()+"type="+loginLog.getClientType());
		userJedisImpl.addOnlineToken(user.getUserId(), loginLog.getTokenId(),loginLog.getClientType());	
	}

//	@Override
//	public Buyer getBuyerRedis(int userId, int clientType) { 
//		LoginLog loginLog = new LoginLog();
//		loginLog.setClientType(clientType);
//		//TbUser tbUser = userJedisImpl.getOnlineByUID(String.valueOf(userId),loginLog );
//		TbUser tbUser = userJedisImpl.getOnlineByUID(String.valueOf(userId));
//		if (!StringUtil.isNullOrEmpty(tbUser) && !StringUtil.isNullOrEmpty(tbUser.getUserId())) {
//			if (tbUser.getUserId() == userId) {
//				Buyer buyer = new Buyer();
//				Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
//				BeanUtil.fillBeanData(buyer, stringObjectMap);
//				if (tbUser.getBirthday() != null) {
//					buyer.setBirthday(DateUtils.dateToStrLong(tbUser.getBirthday().toDate()));
//				}
//				return buyer;
//			}
//		}
//
//		return null;
//	}
	@Override
	public Buyer getBuyerRedis(int userId) {
		//获取用户信息
		TbUser tbUser = userJedisImpl.getOnlineByUID(String.valueOf(userId));
		if (!StringUtil.isNullOrEmpty(tbUser) && !StringUtil.isNullOrEmpty(tbUser.getUserId())) {
			if (tbUser.getUserId() == userId) {
				Buyer buyer = new Buyer();
				Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
				BeanUtil.fillBeanData(buyer, stringObjectMap);
				if (tbUser.getBirthday() != null) {
					buyer.setBirthday(DateUtils.dateToStrLong(tbUser.getBirthday().toDate()));
				}
				return buyer;
			}
		}

		return null;
	}
	@Override
	public void updateOnline(TbUser tbuser) {
		logger.error("更新在线token-------------------！user=" + tbuser.getUserId());
		userJedisImpl.updateOnline(tbuser);
	}

	@Override
	public TbUser getTbuser(int userId) {
		TbUser tbUser = userDaoImpl.getBuyer(userId);
		if (!StringUtil.isNullOrEmpty(tbUser) && !StringUtil.isNullOrEmpty(tbUser.getUserId())) {
			Buyer buyer = new Buyer();
			tbUser.setPwdEnc("******");
			Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(tbUser);
			BeanUtil.fillBeanData(buyer, stringObjectMap);
			if (tbUser.getBirthday() != null) {
				buyer.setBirthday(DateUtils.dateToStrLong(tbUser.getBirthday().toDate()));
			}
			return tbUser;
		}

		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	@Override
	public void h5ThirdLogin(H5ThirdLoginResult h5ThirdLoginResult, H5ThirdLoginParam param) {
		String singKey = PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_h5ThirdLogin_singkey", "0");
		if("0".equals(singKey)){//无权限访问此接口
			FailCode.addFails(h5ThirdLoginResult.getResult(), FailCode.repquestErrorNotSingkey);
			return;
        }
		String appcode = PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_h5ThirdLogin_appcode", "0");
		if("0".equals(appcode)){//无权限访问此接口
			FailCode.addFails(h5ThirdLoginResult.getResult(), FailCode.repquestErrorNotSingkey);
			return;
        }
		String waitSign = "AppCode=" + appcode + "&SingKey="+singKey + "&Date=" + param.getRequestDate()+"&Mobile=" + param.getMobile()+"&WayType=" + param.getWayType();
		

		String sign = null;//加密后字符串
        try {
			 sign = BuyerUtil.md5Encode(waitSign);	
		} catch (Exception e) {
			logger.info("数据签名加密异常"+e.getMessage());
			e.printStackTrace();
		}
        
        if(!sign.equals(param.getSign())){//数字签名验证是否正确
			FailCode.addFails(h5ThirdLoginResult.getResult(), FailCode.repquestErrorSign);
			return;
        }
		if(BuyerUtil.isMobile(param.getMobile()) == false){//手机号是否为正确手机号
			FailCode.addFails(h5ThirdLoginResult.getResult(), FailCode.isNotMobile);
			return;
		}
		TbUser tbUser = null;
		//获取第三方绑定账户信息
		TbUserThirdparty tbutp  = userDaoImpl.getThirdUser(param.getMobile());
		if(tbutp != null){
			//获取聚分享用户信息
			tbUser = userDaoImpl.getBuyer(tbutp.getUserId());
		}
		
		if(tbUser == null){
				Buyer buyer = new Buyer();
	     		buyer.setLoginName(param.getMobile());
	     		buyer.setState(1);	//天翼用户
	     		buyer.setBirthday("1990-01-01 00:00:00");
	     		buyer.setPwdEnc("");
	     		buyer.setMobile(param.getMobile());
	     		//新增聚分享用户信息
	     		int userId =userDaoImpl.insertBuyerH5(buyer);
	     		if(userId > 0){
	     			TbUserThirdparty tbUserThirdparty = new TbUserThirdparty(); 
	     			tbUserThirdparty.setAccountNo(param.getMobile());
	     			tbUserThirdparty.setUserId(userId);
	     			tbUserThirdparty.setCustId(param.getMobile());
	     			tbUserThirdparty.setThirdType(0);
	     			tbUserThirdparty.setUserName(param.getMobile());
	     			//新增第三方账号绑定关系
	     			int rows = userDaoImpl.insertThirdParty(tbUserThirdparty);
	     			if(rows > 0){
	     				//获取聚分享用户信息
			     		tbUser = userDaoImpl.getBuyer(userId);
	     			}	
	     		}else{
	     			FailCode.addFails(h5ThirdLoginResult.getResult(), FailCode.SYSTEM_EXCEPTION);
	    			return;
	     		}	
		}
		
		LoginLog loginLog = new LoginLog();
        loginLog.setUserId(tbUser.getUserId());
        loginLog.setClientType(3);//H5
        tbUser.setPwdEnc(null);
		AuthInfo authInfo = null;
		try {
			authInfo = createAuth(String.valueOf(tbUser.getUserId()), tbUser.getMobile(), tbUser.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		loginLog.setTokenId(authInfo.getToken());
		//添加在线用户信息
		addOnline(tbUser, loginLog);
		//添加在线用户token
		addOnlineToken(tbUser, loginLog);
		clearTryFail(tbUser);
		String url = PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_h5ThirdLogin_url", "0");
		h5ThirdLoginResult.setUrl(url+"?userId="+tbUser.getUserId()+"&token="+authInfo.getToken()+"&ppinfo="+authInfo.getPpInfo());	
	}
}
