package com.jfshare.buyer.server;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.buyer.model.TbThirdUserinfo;
import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.ThirdPartyType;
import com.jfshare.buyer.server.depend.CommonClient;
import com.jfshare.buyer.service.IBuyerSvc;
import com.jfshare.buyer.util.AuthenticationUtil;
import com.jfshare.buyer.util.BuyerUtil;
import com.jfshare.buyer.util.ConstantUtil;
import com.jfshare.buyer.util.FailCode;
import com.jfshare.buyer.util.ThirdUserLoginUtil;
import com.jfshare.finagle.thrift.buyer.AuthInfo;
import com.jfshare.finagle.thrift.buyer.AuthInfoResult;
import com.jfshare.finagle.thrift.buyer.Buyer;
import com.jfshare.finagle.thrift.buyer.BuyerListResult;
import com.jfshare.finagle.thrift.buyer.BuyerResult;
import com.jfshare.finagle.thrift.buyer.BuyerServ;
import com.jfshare.finagle.thrift.buyer.H5ThirdLoginParam;
import com.jfshare.finagle.thrift.buyer.H5ThirdLoginResult;
import com.jfshare.finagle.thrift.buyer.LoginLog;
import com.jfshare.finagle.thrift.buyer.ThirdpartyUser;
import com.jfshare.finagle.thrift.buyer.UserInfoThird;
import com.jfshare.finagle.thrift.buyer.ValidateInfo;
import com.jfshare.finagle.thrift.common.MsgCaptcha;
import com.jfshare.finagle.thrift.result.BoolResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.StringUtil;

@Service(value="handler")
public class ServHandle implements BuyerServ.Iface  {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
    private IBuyerSvc buyerSvcImpl;
	
//	@Autowired
//    private MsgHandler msgHandler;
	
	@Autowired
	private CommonClient commonClient;

	@Override
	public BoolResult buyerIsExist(String loginName) throws TException {
		BoolResult boolResult = new BoolResult();
		Result result = new Result();
		result.setCode(0);
		boolResult.setResult(result);
		try {
			if (StringUtil.isNullOrEmpty(loginName)) {
				logger.warn(MessageFormat.format("buyerIsExist参数验证失败！loginName[{0}]", loginName));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				boolResult.setResult(result);
				return boolResult;
			}
			loginName = loginName.trim();
			boolean ret = buyerSvcImpl.userIsExist(loginName);
			boolResult.setValue(ret);
			logger.info(MessageFormat.format("检测用户名成功，loginName[{0}]", loginName));
		} catch (Exception e) {
			logger.error(MessageFormat.format("检测用户名失败！loginName[{0}]", loginName), e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return boolResult;
	}

	@Override
	public Result signin(Buyer buyer) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			if (StringUtil.isNullOrEmpty(buyer)) {
				logger.warn("signin参数验证失败！buyer=" + buyer);
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}

			buyerSvcImpl.validUser(result, buyer);
			if (result.getCode() == 0) {
				buyerSvcImpl.insert(buyer);
			    logger.info(MessageFormat.format("注册成功，buyer[{0}]", buyer.getLoginName()));
			}
		} catch (Exception e) {
			logger.error("注册失败！buyer=" + buyer, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
	}

	@Override
	public BuyerResult login(Buyer buyer, LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		try {
			if (StringUtil.isNullOrEmpty(buyer)) {
				logger.warn(MessageFormat.format("login参数验证失败！buyer[{0}],loginLog[{1}]", buyer, loginLog));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}

			buyerSvcImpl.validLogin(result, buyer);
			if (result.getCode() == 1) {
				logger.warn("登陆校验失败，" + "[user:" + buyer.getLoginName() + "]" + result.getFailDescList());
				//user fail times cache，fail log queue
				int ret = buyerSvcImpl.addTryFail(buyer);
				if (ret > 0) {
					//add to fail mq
				}
				return buyerResult;
			} else {
				loginLog.setUserId(buyer.getUserId());
				loginLog.setTokenId(UUID.randomUUID().toString()); //TODO 生成token策略
				buyer.setPwdEnc(null);
				TbUser user = BuyerUtil.buyer2TbUser(buyer);
				//write online cache
				buyerSvcImpl.addOnline(user, loginLog);
				//添加在线用户token
				buyerSvcImpl.addOnlineToken(user, loginLog);
				buyerSvcImpl.clearTryFail(user);
				//add to success mq

				buyerResult.setBuyer(buyer);
				buyerResult.setLoginLog(loginLog);
			}
		} catch (Exception e) {
			logger.error("登陆失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	@Override
	public Result logout(LoginLog loginLog) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			if (StringUtil.isNullOrEmpty(loginLog) || StringUtil.isNullOrEmpty(loginLog.getUserId()) ||
					StringUtil.isNullOrEmpty(loginLog.getTokenId())) {
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}

			buyerSvcImpl.removeOnline(result, loginLog.getUserId(), loginLog.getTokenId());
			if (result.getCode() == 0) {
				logger.info(MessageFormat.format("注销成功，loginLog[{0}]", loginLog));
			}
		} catch (Exception e) {
			logger.error("注销失败！loginLog=" + loginLog, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
	}

	@Override
	public BuyerResult isOnline(LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		try {
			if (StringUtil.isNullOrEmpty(loginLog) || StringUtil.isNullOrEmpty(loginLog.getUserId()) ||
					StringUtil.isNullOrEmpty(loginLog.getTokenId())) {
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			boolean onlineState = buyerSvcImpl.isOnline(loginLog.getUserId(), loginLog.getTokenId());
			buyerResult.setValue(onlineState);
		} catch (Exception e) {
			logger.error("验证在线状态失败！param=" + loginLog, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	@Override
	public BuyerResult getOnlineInfo(LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		try {
			if (StringUtil.isNullOrEmpty(loginLog) || (StringUtil.isNullOrEmpty(loginLog.getUserId()) &&
					StringUtil.isNullOrEmpty(loginLog.getTokenId()))) {
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			Buyer buyer = buyerSvcImpl.getOnline(loginLog.getUserId(), loginLog.getTokenId(), loginLog);
			buyerResult.setBuyer(buyer);
		} catch (Exception e) {
			logger.error("获取在线用户信息失败！loginLog=" + loginLog, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	@Override
	public StringResult pwdFind(int findWay, String account) throws TException {
		return null;
	}

    @Override
    public BuyerResult getBuyer(Buyer buyer) throws TException {
        BuyerResult buyerResult = new BuyerResult();
        Result result = new Result();
        result.setCode(0);
        buyerResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getUserId())) {
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return buyerResult;
            }
            //Buyer rBuyer = buyerSvcImpl.getBuyerRedis(buyer.getUserId(),buyer.getClientType());
            //获取用户信息从redis
            Buyer rBuyer = buyerSvcImpl.getBuyerRedis(buyer.getUserId());
            if(rBuyer == null){
                 rBuyer = buyerSvcImpl.getBuyer(buyer.getUserId());
            }
            if(StringUtil.isNullOrEmpty(rBuyer)){
            	FailCode.addFails(result, FailCode.loginNameNotExist);
            	buyerResult.setBuyer(rBuyer);
				return buyerResult;
            }
            buyerResult.setBuyer(rBuyer);
            
        } catch (Exception e) {
            logger.error("获取用户信息失败！loginLog=" + buyer, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return buyerResult;
    }

    @Override
    public Result updateBuyer(Buyer buyer) throws TException {
        Result result = new Result(0);
        try {
            if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getUserId())) {
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }
           //mysql中更新用户个人信息
            boolean res = buyerSvcImpl.updateBuyer(buyer);
            if(!res) {
            	 FailCode.addFails(result, FailCode.ADDSYSTEM_ERROR);
                 return result;
            }
            //mysql中获取用户信息
            TbUser tbuser = buyerSvcImpl.getTbuser(buyer.getUserId());
            if(tbuser != null){
            	//redis中更新用户个人信息
            	buyerSvcImpl.updateOnline(tbuser);
            }
            
        } catch (Exception e) {
            logger.error("修改用户信息失败！loginLog=" + buyer, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return result;
    }

    @Override
    public Result resetBuyerPwd(String newPwd, Buyer buyer) throws TException {
        Result result = new Result();
        result.setCode(0);
        try {
            if (StringUtil.isNullOrEmpty(newPwd) || !newPwd.matches("^[a-zA-Z0-9_]+$")) {
                FailCode.addFails(result, FailCode.pwdInvalid);
                return result;
            }
            if (StringUtil.isNullOrEmpty(buyer)) {
                logger.warn("signin参数验证失败！buyer=" + buyer);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }

            buyerSvcImpl.validPwd(result, buyer);
            if (result.getCode() != 0) {
                logger.warn("原密码验证失败！buyer=" + buyer);
                return result;
            }

            buyerSvcImpl.updatePwd(buyer.getUserId(), newPwd);
            logger.info("修改密码成功，buyer[{}]", buyer.getLoginName());
        } catch (Exception e) {
            logger.error("修改密码失败！buyer=" + buyer, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return result;
    }

    /**
     * 1.已第三方账号已绑定聚分享账号，直接使用聚分享账号登录
     * 2.未绑定聚分享账号，但当前处于聚分享账号登录状态，对第三方账号进行绑定，进行登录
     * 3.未绑定聚分享账号，且当前处于未登录状态，创建默认聚分享账号，并与第三方账号绑定，进行登录
     * @param loginLog
     * @param thirdUser
     * @return
     * @throws TException
     */
    @Override
    public BuyerResult signinThirdParty(LoginLog loginLog, ThirdpartyUser thirdUser) throws TException {
        BuyerResult buyerResult = new BuyerResult();
        Result result = new Result();
        result.setCode(0);
        buyerResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(thirdUser)
                    ||StringUtil.isNullOrEmpty(thirdUser.getThirdType())
                    ||StringUtil.isNullOrEmpty(thirdUser.getAccountNo())) {
                logger.warn(MessageFormat.format("第三方登录参数验证失败！thirdUser[{0}],loginLog[{1}]", thirdUser, loginLog));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return buyerResult;
            }

            Buyer buyer = buyerSvcImpl.checkThirdCustomId(thirdUser);

            if(buyer == null) {
				buyer = buyerSvcImpl.createUserThird(thirdUser);
            }

            loginLog.setUserId(buyer.getUserId());
//            loginLog.setTokenId(UUID.randomUUID().toString());
            buyer.setPwdEnc(null);
            TbUser user = BuyerUtil.buyer2TbUser(buyer);
            //add to success mq
            
//          String token = AuthenticationUtil.getToken(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser());
//			String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail());
			
			
			AuthInfo authInfo = buyerSvcImpl.createAuth(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
//			authInfo.setToken(token);
//			authInfo.setPpInfo(ppInfo);
			loginLog.setTokenId(authInfo.getToken());

			//write online cache
			buyerSvcImpl.addOnline(user, loginLog);
			//添加在线用户token
			buyerSvcImpl.addOnlineToken(user, loginLog);
			buyerSvcImpl.clearTryFail(user);
			
			buyerResult.setAuthInfo(authInfo);
            buyerResult.setBuyer(buyer);
            buyerResult.setLoginLog(loginLog);
            buyerResult.setThirdUser(thirdUser);
        } catch (Exception e) {
            logger.error("第三方登陆失败！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return buyerResult;
    }

    @Override
    public BuyerResult isBindThirdParty(String thirdType, LoginLog loginLog) throws TException {
        BuyerResult buyerResult = new BuyerResult();
        Result result = new Result();
        result.setCode(0);
        buyerResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(thirdType)
                    ||StringUtil.isNullOrEmpty(loginLog)
                    ||loginLog.getUserId() <=0 ) {
                logger.warn(MessageFormat.format("是否绑定指定第三方账号校验失败！thirdType[{0}],loginLog[{1}]", thirdType, loginLog));
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return buyerResult;
            }

            ThirdpartyUser thirdpartyUser = buyerSvcImpl.queryThirdPartyInfo(loginLog.getUserId(), ThirdPartyType.valueOf(thirdType).getCode());

            if(thirdpartyUser == null) {
                buyerResult.setThirdUser(null);
            } else {
                buyerResult.setThirdUser(thirdpartyUser);
            }
        } catch (Exception e) {
            logger.error("是否绑定指定第三方账号校验失败！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return buyerResult;
    }

	@Override
	public Result newSignin(Buyer buyer) throws TException {
		
		Result result = new Result();
		result.setCode(0);
		try {
			logger.warn("newSignin参数验证！buyer=" + buyer);
			if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getMobile())) {
				logger.warn("signin参数验证失败！buyer=" + buyer);
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}
			buyerSvcImpl.validUser(result, buyer);	
			if (result.getCode() == 0) {
				String pwd = AuthenticationUtil.spa512Encode(buyer.getPwdEnc());
				buyer.setPwdEnc(pwd);
				if(StringUtil.isNullOrEmpty(buyer.getLoginName())) 
					buyer.setLoginName(buyer.getMobile());
				buyerSvcImpl.insert(buyer);
			    logger.info(MessageFormat.format("注册成功，buyer[{0}]", buyer.getLoginName()));
			}
		} catch (Exception e) {
			logger.error("注册失败！buyer=" + buyer, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
	}

	@Override
	public BuyerResult newLogin(Buyer buyer, LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
		try {
			if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getMobile()) || StringUtil.isNullOrEmpty(buyer.getPwdEnc()) || loginLog.getClientType()<=0) {
				logger.warn(MessageFormat.format("login参数验证失败！buyer[{0}],loginLog[{1}]", buyer, loginLog));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			//加密密码
			String pwd = AuthenticationUtil.spa512Encode(buyer.getPwdEnc());
			buyer.setPwdEnc(pwd);
			if(StringUtil.isNullOrEmpty(buyer.getLoginName())) 
				buyer.setLoginName(buyer.getMobile());
			
			//校验登陆密码是否正确
			buyerSvcImpl.validLogin(result, buyer);
			if (result.getCode() == 1) {
				logger.warn("登陆校验失败，" + "[user:" + buyer.getLoginName() + "]" + result.getFailDescList());
				//记录登陆失败次数
				int ret = buyerSvcImpl.addTryFail(buyer);
				if (ret > 0) {
					//add to fail mq
				}
				return buyerResult;
			} else {
				loginLog.setUserId(buyer.getUserId());
				buyer.setPwdEnc(null);
				TbUser user = BuyerUtil.buyer2TbUser(buyer);
				logger.info("----------------------------登陆开始啦--------------------------");
				//生成token 记录登陆时间
				AuthInfo authInfo = buyerSvcImpl.createAuth(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
				loginLog.setTokenId(authInfo.getToken());
				loginLog.setLogoutTime(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_login_timeout", "0"));
				//添加在线用户
				buyerSvcImpl.addOnline(user, loginLog);	
				//添加在线用户token
				buyerSvcImpl.addOnlineToken(user, loginLog);
				//清除失败登陆次数
				buyerSvcImpl.clearTryFail(user);
				buyerResult.setAuthInfo(authInfo);
				buyerResult.setBuyer(buyer);
				buyerResult.setLoginLog(loginLog);
				logger.info("----------------------------登陆结束啦--------------------------");
			}
		} catch (Exception e) {
			logger.error("登陆失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	@Override
	public BuyerResult smsLogin(Buyer buyer, LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
//		AuthInfo authInfo = new AuthInfo();
//		buyerResult.setAuthInfo(authInfo);
		try {
			if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getMobile()) || loginLog.getClientType() <= 0) {
				logger.warn(MessageFormat.format("login参数验证失败！buyer[{0}],loginLog[{1}]", buyer, loginLog));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			
			if(StringUtil.isNullOrEmpty(buyer.getLoginName())) 
				buyer.setLoginName(buyer.getMobile());
			
//			buyerSvcImpl.validLogin(result, buyer);
			
			Buyer dbBuyer = buyerSvcImpl.getBuyerByLoginName(buyer.getMobile());
			if (StringUtil.isNullOrEmpty(dbBuyer)) {
				logger.warn("用户还没有注册过，" + "[user:" + buyer.getLoginName() + "]" + result.getFailDescList());
				buyer.setPwdEnc("jfshare@123.com");
				newSignin(buyer);
				dbBuyer = buyerSvcImpl.getBuyerByLoginName(buyer.getMobile());
			} 
			
			loginLog.setUserId(dbBuyer.getUserId());
			buyer.setPwdEnc(null);
			TbUser user = BuyerUtil.buyer2TbUser(dbBuyer);
			
			//add to success mq
//			String token = AuthenticationUtil.getToken(user.getUserId().toString(), dbBuyer.getMobile(), dbBuyer.getEmail(), loginLog.getBrowser());
//			String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), dbBuyer.getMobile(), dbBuyer.getEmail());
			AuthInfo authInfo = buyerSvcImpl.createAuth(user.getUserId().toString(), dbBuyer.getMobile(), dbBuyer.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
			loginLog.setTokenId(authInfo.getToken());
			loginLog.setLogoutTime(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_login_timeout", "0"));
			//write online cache
			buyerSvcImpl.addOnline(user, loginLog);
			//添加在线用户token
			buyerSvcImpl.addOnlineToken(user, loginLog);
			buyerSvcImpl.clearTryFail(user);
			
//			authInfo.setToken(token);
//			authInfo.setPpInfo(ppInfo);
			buyerResult.setAuthInfo(authInfo);
			buyerResult.setBuyer(dbBuyer);
			buyerResult.setLoginLog(loginLog);
			
		} catch (Exception e) {
			logger.error("登陆失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	@Override
	public Result newResetBuyerPwd(String newPwd, Buyer buyer) throws TException {
		Result result = new Result();
        result.setCode(0);
        try {
          
            if (StringUtil.isNullOrEmpty(buyer)) {
                logger.warn("signin参数验证失败！buyer=" + buyer);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }

            String pwd = AuthenticationUtil.spa512Encode(newPwd);
            if(buyer.getUserId() <= 0)
            	buyer = buyerSvcImpl.getBuyerByLoginName(buyer.getMobile());
            
            buyerSvcImpl.updatePwd(buyer.getUserId(), pwd);
            logger.info("修改密码成功，buyer[{}]", buyer.getLoginName());
        } catch (Exception e) {
            logger.error("修改密码失败！buyer=" + buyer, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return result;
	}
    
	//获取鉴权信息
	@Override
	public AuthInfoResult getAuthInfo(AuthInfo authInfo1, Buyer buyer, LoginLog loginLog) throws TException {
		AuthInfoResult authInfoResult = new AuthInfoResult();
		Result result = new Result();
		result.setCode(0);
		authInfoResult.setResult(result);
		try {
			if(StringUtil.isNullOrEmpty(buyer) || buyer.getUserId() <= 0 || StringUtil.isNullOrEmpty(buyer.getMobile()) || loginLog.getClientType() <= 0){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return authInfoResult;
			}
			//生成token 记录登陆时间
			AuthInfo authInfo = buyerSvcImpl.createAuth(String.valueOf(buyer.getUserId()), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
			authInfoResult.setAuthInfo(authInfo);
			
			//根据token和userId查看在线用户信息
			Buyer buyerInRedis = buyerSvcImpl.getOnline(buyer.getUserId(), authInfo1.getToken(), loginLog);
			TbUser user = BuyerUtil.buyer2TbUser(buyerInRedis);
			buyerSvcImpl.removeOnline(new Result(), buyer.getUserId(), authInfo1.getToken());
			loginLog.setTokenId(authInfo.getToken());
			buyerSvcImpl.addOnline(user, loginLog);
			buyerSvcImpl.addOnlineToken(user, loginLog);
			
		} catch(Exception ex) {
			logger.error("获取鉴权失败！buyer=" + buyer, ex);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return authInfoResult;
	}

	@Override
	public Result validAuth(LoginLog loginLog, AuthInfo authInfo) throws TException {
		Result result = new Result();
		result.setCode(0);
		
		try{
			logger.warn("validAuth验证鉴权开始！param1={"+authInfo+"}"+"param2={"+loginLog+"}");
			if(StringUtil.isNullOrEmpty(loginLog) || loginLog.getUserId() <= 0 || StringUtil.isNullOrEmpty(authInfo) || loginLog.getClientType() <= 0){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}
			
//			boolean flg = AuthenticationUtil.tokenVerification(authInfo.getToken(), authInfo.ppInfo, loginLog.getBrowser());
//			boolean flg = buyerSvcImpl.verificationToken(String.valueOf(loginLog.getUserId()), authInfo, loginLog.getBrowser(),loginLog.getClientType());
			boolean flg = buyerSvcImpl.verificationTokenByonline(loginLog,authInfo);
			if(!flg){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				
			}
		}catch (Exception ex) {
			logger.error("获取鉴权失败！buyer=" + loginLog.getUserId(), ex);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		
		return result;
	}
	
	//=====================第三方登陆====================
	//判断用户是否存在
	@Override
	public BuyerResult isExitsThirdUser(LoginLog loginLog, ValidateInfo validateInfo) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
//		AuthInfo authInfo = new AuthInfo();
//		buyerResult.setAuthInfo(authInfo);
		try {
			if ( StringUtil.isNullOrEmpty(validateInfo) ||StringUtil.isNullOrEmpty(validateInfo.getThirdType()) ||
					StringUtil.isNullOrEmpty(validateInfo.getCustId()) || StringUtil.isNullOrEmpty(validateInfo.getAccessToken())||
					StringUtil.isNullOrEmpty(validateInfo.getOpenId())) {
				logger.warn(MessageFormat.format("第三方login参数验证失败！thirdType[{0}],custId[{1}],accessToken[{2}],opendId[{3}]",
						validateInfo.getThirdType(),validateInfo.getCustId(), validateInfo.getAccessToken(),validateInfo.getOpenId()));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			
			logger.info("----------------------校验openId+access_token是否有效 --开始--------------------------");
			//0.校验openId+access_token是否有效
			if(!ThirdUserLoginUtil.isValidateWX(validateInfo.getOpenId(),validateInfo.getAccessToken())){
				
				FailCode.addFails(result, FailCode.TOKEN_NUM_ERROR);
				return buyerResult;
				
			}
			logger.info("----------------------校验openId+access_token是否有效--结束--------------------------");
			
			//1.判断第三方表中是否存在数据,如果不存在，返回不存在标志
//			if(!buyerSvcImpl.userIsExistForThird(custId, thirdType)){
//				FailCode.addFails(result, FailCode.loginNameNotExist);
//				return buyerResult;
//			}
			
			
			logger.info("---------------------1.判断第三方表中是否存在数据,如果不存在，返回不存在标志 --开始--------------------------");
			TbThirdUserinfo ttu=new TbThirdUserinfo();
			ttu.setCustId(validateInfo.getCustId());
			ttu.setThirdType(validateInfo.getThirdType());
			
			List<TbUser> tbus= buyerSvcImpl.selectByUserPK(ttu);
			
			if(tbus.size()==0){
				FailCode.addFails(result, FailCode.loginNameNotExist);
				return buyerResult;
			}
			logger.info("---------------------1.判断第三方表中是否存在数据,如果不存在，返回不存在标志 --结束--------------------------");
			//2.如果存在，返回登录信息
			
			TbUser user = tbus.get(0);
					
			Buyer buyer=BuyerUtil.tbUser2Buyer(user);
			
			loginLog.setUserId(buyer.getUserId());
//				loginLog.setTokenId(UUID.randomUUID().toString()); //TODO 生成token策略
			buyer.setPwdEnc(null);
			
			//add to success mq
//				String token = AuthenticationUtil.getToken(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser());
//				String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail());
			logger.info("----------------------------登陆开始--------------------------");
			AuthInfo authInfo = buyerSvcImpl.createAuth(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
			loginLog.setTokenId(authInfo.getToken());
			loginLog.setLogoutTime(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_login_timeout", "0"));
			//write online cache
			buyerSvcImpl.addOnline(user, loginLog);
			//添加在线用户token
			buyerSvcImpl.addOnlineToken(user, loginLog);
			buyerSvcImpl.clearTryFail(user);
			
//				authInfo.setToken(token);
//				authInfo.setPpInfo(ppInfo);
			buyerResult.setAuthInfo(authInfo);
			buyerResult.setBuyer(buyer);
			buyerResult.setLoginLog(loginLog);
			logger.info("----------------------------登陆结束--------------------------");
		} catch (Exception e) {
			logger.error("isExitsThirdUser 失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	//注册
	@Override
	public BuyerResult thirdUserSignin(LoginLog loginLog, UserInfoThird thirdUser, ValidateInfo valiInfo) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
		try {
			//1.==============验证验证码==================
			String mobile=thirdUser.getMobile();
			String valiNum=valiInfo.getValiNum();//验证码
			
			MsgCaptcha ms=new MsgCaptcha();
			logger.info("第三方登陆:验证码=="+valiNum+"  手机:"+mobile);
			
			ms.setMobile(mobile);
			ms.setType(valiInfo.getValiNumType());
			ms.setCaptchaDesc(valiNum);
			
			Result resp=commonClient.validateMsgCaptcha(ms);
			
			logger.info("第三方登陆:验证码 返回信息=="+resp);
			
			if(resp.getCode()!=0){
				FailCode.addFails(result, FailCode.VALIDATE_NUM_ERROR);//--------------------
				return buyerResult;
			}
			//============================================
			//2.========验证openId+access_token是否合法========
			if(!ThirdUserLoginUtil.isValidateWX(valiInfo.getOpenId(),valiInfo.getAccessToken())){
				
				FailCode.addFails(result, FailCode.TOKEN_NUM_ERROR);
				return buyerResult;
			}
			//3.========数据存入数据库==========================
			  //a.============通过手机号查询user表中的数据是否存在========
			TbThirdUserinfo ttu=new TbThirdUserinfo();
			ttu.setMobile(mobile);
			ttu.setThirdType(thirdUser.getThirdType());
			ttu.setCustId(thirdUser.getCustId());
			ttu.setExtInfo(thirdUser.getExtInfo());
			TbUser tbu=new TbUser();
			tbu.setMobile(mobile);
			tbu.setLoginName(mobile);
			tbu.setBirthday(new DateTime());
			
			TbUser user=buyerSvcImpl.addUserAndThirdUser(ttu,tbu);
			if(user==null){
				FailCode.addFails(result, FailCode.ADDSYSTEM_ERROR);
				return buyerResult;
			}else if(user.getUserId()==-1){
				FailCode.addFails(result, FailCode.EREADLYBIND_ERROR);
				return buyerResult;
			}
			
			//===============================================
			
			Buyer buyer=BuyerUtil.tbUser2Buyer(user);
			
			loginLog.setUserId(buyer.getUserId());
//				loginLog.setTokenId(UUID.randomUUID().toString()); //TODO 生成token策略
			buyer.setPwdEnc(null);
			
			//add to success mq
//				String token = AuthenticationUtil.getToken(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser());
//				String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail());
			logger.info("----------------------------登陆开始-------------------------");
			AuthInfo authInfo = buyerSvcImpl.createAuth(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser(),loginLog.getClientType());
			loginLog.setTokenId(authInfo.getToken());
			loginLog.setLogoutTime(PropertiesUtil.getProperty(ConstantUtil.IMAGE_KEY, "def_login_timeout", "0"));
			//write online cache
			buyerSvcImpl.addOnline(user, loginLog);
			//添加在线用户token
			buyerSvcImpl.addOnlineToken(user, loginLog);
			buyerSvcImpl.clearTryFail(user);
			
//				authInfo.setToken(token);
//				authInfo.setPpInfo(ppInfo);
			buyerResult.setAuthInfo(authInfo);
			buyerResult.setBuyer(buyer);
			buyerResult.setLoginLog(loginLog);
			logger.info("----------------------------登陆结束-------------------------");
			
			
		} catch (Exception e) {
			logger.error("thirdUserSignin 失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		
		return buyerResult;
	}
	
	@Override
	public BuyerResult requestHttps(String url, String extInfo) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
		try {
			if ( StringUtil.isNullOrEmpty(url) ) {
				logger.warn(MessageFormat.format("requestHttps请求参数验证失败！url[{0}],custId[{1}],accessToken[{2}],opendId[{3}]",url));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			
			logger.info("----------------HTTPS请求--开始--------------------------");
		
			String res=ThirdUserLoginUtil.requestHttps(url,extInfo);
			
			Buyer buyer=new Buyer();
			buyer.setRemark(res);
			
			buyerResult.setBuyer(buyer);
			logger.info("----------------HTTPS请求--结束--------------------------");
			

		} catch (Exception e) {
			logger.error("requestHttps 失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return buyerResult;
	}

	@Override
	public BuyerListResult getListBuyer(List<Integer> userIdList) throws TException {
		BuyerListResult buyerListResult = new BuyerListResult();
		Result result = new Result();
		result.setCode(0);
		buyerListResult.setResult(result);
		try{
			logger.info("批量查询买家请求：" + userIdList != null ? userIdList.toString(): null);
			if(userIdList.isEmpty()){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerListResult;
			}	
			buyerSvcImpl.getListBuyer(buyerListResult, userIdList);
		}catch(Exception ex){
			logger.error("批量查询买家失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
				
		return buyerListResult;
	}
	@Override
	public H5ThirdLoginResult H5ThirdLogin(H5ThirdLoginParam param) throws TException {
		H5ThirdLoginResult h5ThirdLoginResult = new H5ThirdLoginResult();
		Result result = new Result();
		result.setCode(0);
		h5ThirdLoginResult.setResult(result);
		try{
			logger.info(String.format("$$$$H5第三方登录---请求参数：param:%s", param != null ? param.toString() : null));
			if(StringUtil.isNullOrEmpty(param.getAppCode())){//应用编码
				FailCode.addFails(result, FailCode.NOTNULL_ERROR);
				return h5ThirdLoginResult;
			}if(StringUtil.isNullOrEmpty(param.getRequestDate())){//请求时间
				FailCode.addFails(result, FailCode.NOTNULL_ERROR);
				return h5ThirdLoginResult;
			}if(StringUtil.isNullOrEmpty(param.getSign())){//数字签名
				FailCode.addFails(result, FailCode.NOTNULL_ERROR);
				return h5ThirdLoginResult;
			}if(StringUtil.isNullOrEmpty(param.getMobile())){//设备号
				FailCode.addFails(result, FailCode.NOTNULL_ERROR);
				return h5ThirdLoginResult;
			}if(StringUtil.isNullOrEmpty(param.getWayType())){//用户渠道来源
				FailCode.addFails(result, FailCode.NOTNULL_ERROR);
				return h5ThirdLoginResult;
			}
			buyerSvcImpl.h5ThirdLogin(h5ThirdLoginResult,param);			
		} catch (Exception ex) {
			logger.error("$$$$H5第三方登录---请求失败，系统异常！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return h5ThirdLoginResult;
	}
}
