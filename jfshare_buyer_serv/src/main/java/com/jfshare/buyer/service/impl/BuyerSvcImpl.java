package com.jfshare.buyer.service.impl;

import com.jfshare.buyer.dao.mysql.IUserDao;
import com.jfshare.buyer.dao.redis.IUserJedis;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.TbUserThirdparty;
import com.jfshare.buyer.model.ThirdPartyType;
import com.jfshare.buyer.server.ServHandle;
import com.jfshare.buyer.service.IBuyerSvc;
import com.jfshare.buyer.util.AuthenticationUtil;
import com.jfshare.buyer.util.BuyerUtil;
import com.jfshare.buyer.util.ConstantUtil;
import com.jfshare.buyer.util.FailCode;
import com.jfshare.buyer.util.RLoginName;
import com.jfshare.finagle.thrift.buyer.AuthInfo;
import com.jfshare.finagle.thrift.buyer.Buyer;
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
	}

	@Override
	public int addTryFail(Buyer buyer) {
		TbUser user = new TbUser();
		user.setLoginName(buyer.getLoginName().trim());
		return userJedisImpl.addTryFail(user);
	}

	@Override
	public void addOnline(TbUser user, LoginLog loginLog) {
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
			TbUser tbUser = userJedisImpl.getOnlineBySID(tokenId);
			if (tbUser != null && tbUser.getUserId().intValue() == userId) {
				isOnline = true;
			}
		}
		return isOnline;
	}

	@Override
	public Buyer getOnline(int userId, String tokenId) {
		TbUser tbUser = userJedisImpl.getOnlineBySID(tokenId);
		if (tbUser == null) {
			tbUser = userJedisImpl.getOnlineByUID(String.valueOf(userId));
		}
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
		int ret = userJedisImpl.removeOnline(ConvertUtil.getString(userId), tokenId);
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
	public Buyer createUserThird(ThirdpartyUser thirdUser) {
		Buyer buyer = new Buyer();
		int thirdType = ThirdPartyType.valueOf(thirdUser.getThirdType()).getCode();
		String loginName = RLoginName.getRLoginName(thirdType);
		buyer.setUserName(thirdUser.getUserName());
		buyer.setLoginName(loginName);
		buyer.setPwdEnc("24rcLSmUWau+Vu7X8g+OMg==");
		this.insert(buyer);

		return this.getBuyerByLoginName(loginName);
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
		//保证android和ios只能登陆一个
		if(clientType ==2 || clientType ==1 ){
			clientType = 999;
		}
		userJedisImpl.setTokenTimestamp(userId, time,clientType);
		logger.info("登陆加密后！token为:"+ token);
		logger.info("登陆加密后！ppInfo为:"+ ppInfo);
		return authInfo;
		
	}
	@Override
	public boolean verificationToken(String uid, AuthInfo authInfo, String appId,int clientType) throws Exception {
		//保证android和ios只能登陆一个
		if(clientType ==2 || clientType ==1 ){
			clientType = 999;
		}
		logger.info("校验开始准备，time的key为："+clientType);
		String time = userJedisImpl.getTokenTimestamp(uid,clientType);
		boolean flg = AuthenticationUtil.tokenVerification(authInfo.getToken(), authInfo.ppInfo, appId, time);
		
		return flg;
	}
	
}
