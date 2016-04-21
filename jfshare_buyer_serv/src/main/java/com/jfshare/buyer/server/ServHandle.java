package com.jfshare.buyer.server;

import com.jfshare.buyer.model.TbUser;
import com.jfshare.buyer.model.ThirdPartyType;
import com.jfshare.buyer.service.IBuyerSvc;
import com.jfshare.buyer.util.AuthenticationUtil;
import com.jfshare.buyer.util.BuyerUtil;
import com.jfshare.buyer.util.FailCode;
import com.jfshare.finagle.thrift.buyer.*;
import com.jfshare.finagle.thrift.result.BoolResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.utils.CryptoUtil;
import com.jfshare.utils.StringUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service(value="handler")
public class ServHandle implements BuyerServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
    private IBuyerSvc buyerSvcImpl;

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
			Buyer buyer = buyerSvcImpl.getOnline(loginLog.getUserId(), loginLog.getTokenId());
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
            Buyer rBuyer = buyerSvcImpl.getBuyer(buyer.getUserId());
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


            boolean res = buyerSvcImpl.updateBuyer(buyer);
            if(!res) {
                result.setCode(1);
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
                buyer = buyerSvcImpl.getOnline(loginLog.getUserId(), loginLog.getTokenId());
                if(buyer == null) {
                    //默认注册流程
                    buyer = buyerSvcImpl.createUserThird(thirdUser);
                }
                buyerSvcImpl.insertThirdPartyRel(thirdUser, buyer.getUserId());
            } else {
                if(buyer.getUserId() != loginLog.getUserId()) {
                    logger.warn(MessageFormat.format("第三方账号绑定失败！第三方账号绑定的聚分享账号与当前已登录聚分享账号不一致thirdUser[{0}],loginLog[{1}]", thirdUser, loginLog));
//                    FailCode.addFails(result, FailCode.THIRDPARTY_BIND_ERROR);
//                    return buyerResult;
                }
            }

            loginLog.setUserId(buyer.getUserId());
//            loginLog.setTokenId(UUID.randomUUID().toString());
            buyer.setPwdEnc(null);
            TbUser user = BuyerUtil.buyer2TbUser(buyer);
            //write online cache
            buyerSvcImpl.addOnline(user, loginLog);
            buyerSvcImpl.clearTryFail(user);
            //add to success mq
            
            String token = AuthenticationUtil.getToken(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser());
			String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail());
			
			loginLog.setTokenId(token);
			AuthInfo authInfo = new AuthInfo();
			authInfo.setToken(token);
			authInfo.setPpInfo(ppInfo);
			
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
			if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getMobile())) {
				logger.warn("signin参数验证失败！buyer=" + buyer);
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}

			String pwd = AuthenticationUtil.spa512Encode(buyer.getPwdEnc());
			buyer.setPwdEnc(pwd);
			if(StringUtil.isNullOrEmpty(buyer.getLoginName())) 
				buyer.setLoginName(buyer.getMobile());
			
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
	public BuyerResult newLogin(Buyer buyer, LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
		AuthInfo authInfo = new AuthInfo();
		buyerResult.setAuthInfo(authInfo);
		try {
			if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getMobile()) || StringUtil.isNullOrEmpty(buyer.getPwdEnc())) {
				logger.warn(MessageFormat.format("login参数验证失败！buyer[{0}],loginLog[{1}]", buyer, loginLog));
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return buyerResult;
			}
			
			String pwd = AuthenticationUtil.spa512Encode(buyer.getPwdEnc());
			buyer.setPwdEnc(pwd);
			if(StringUtil.isNullOrEmpty(buyer.getLoginName())) 
				buyer.setLoginName(buyer.getMobile());
			
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
//				loginLog.setTokenId(UUID.randomUUID().toString()); //TODO 生成token策略
				buyer.setPwdEnc(null);
				TbUser user = BuyerUtil.buyer2TbUser(buyer);
				
				//add to success mq
				String token = AuthenticationUtil.getToken(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser());
				String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), buyer.getMobile(), buyer.getEmail());
				
				loginLog.setTokenId(token);
				//write online cache
				buyerSvcImpl.addOnline(user, loginLog);
				buyerSvcImpl.clearTryFail(user);
				
				authInfo.setToken(token);
				authInfo.setPpInfo(ppInfo);
				buyerResult.setAuthInfo(authInfo);
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
	public BuyerResult smsLogin(Buyer buyer, LoginLog loginLog) throws TException {
		BuyerResult buyerResult = new BuyerResult();
		Result result = new Result();
		result.setCode(0);
		buyerResult.setResult(result);
		
		AuthInfo authInfo = new AuthInfo();
		buyerResult.setAuthInfo(authInfo);
		try {
			if (StringUtil.isNullOrEmpty(buyer) || StringUtil.isNullOrEmpty(buyer.getMobile())) {
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
			String token = AuthenticationUtil.getToken(user.getUserId().toString(), dbBuyer.getMobile(), dbBuyer.getEmail(), loginLog.getBrowser());
			String ppInfo = AuthenticationUtil.getPPInfo(user.getUserId().toString(), dbBuyer.getMobile(), dbBuyer.getEmail());
			
			loginLog.setTokenId(token);
			//write online cache
			buyerSvcImpl.addOnline(user, loginLog);
			buyerSvcImpl.clearTryFail(user);
			
			authInfo.setToken(token);
			authInfo.setPpInfo(ppInfo);
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

            String pwd = AuthenticationUtil.spa512Encode(buyer.getPwdEnc());

            buyerSvcImpl.updatePwd(buyer.getUserId(), pwd);
            logger.info("修改密码成功，buyer[{}]", buyer.getLoginName());
        } catch (Exception e) {
            logger.error("修改密码失败！buyer=" + buyer, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return result;
	}

	@Override
	public AuthInfoResult getAuthInfo(AuthInfo authInfo1, Buyer buyer, LoginLog loginLog) throws TException {
		AuthInfoResult authInfoResult = new AuthInfoResult();
		Result result = new Result();
		result.setCode(0);
		authInfoResult.setResult(result);
		try {
			if(StringUtil.isNullOrEmpty(buyer) || buyer.getUserId() <= 0 || StringUtil.isNullOrEmpty(buyer.getMobile())){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return authInfoResult;
			}
			
			AuthInfo authInfo = new AuthInfo();
			
			String token =AuthenticationUtil.getToken(String.valueOf(buyer.getUserId()), buyer.getMobile(), buyer.getEmail(), loginLog.getBrowser());
			String ppInfo = AuthenticationUtil.getPPInfo(String.valueOf(buyer.getUserId()), buyer.getMobile(), buyer.getEmail());
			authInfo.setToken(token);
			authInfo.setPpInfo(ppInfo);
			authInfoResult.setAuthInfo(authInfo);
			
			Buyer buyerInRedis = buyerSvcImpl.getOnline(buyer.getUserId(), authInfo1.getToken());
			TbUser user = BuyerUtil.buyer2TbUser(buyerInRedis);
			buyerSvcImpl.removeOnline(new Result(), buyer.getUserId(), authInfo1.getToken());
			loginLog.setTokenId(token);
			buyerSvcImpl.addOnline(user, loginLog);
			
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
			if(StringUtil.isNullOrEmpty(loginLog) || loginLog.getUserId() <= 0 || StringUtil.isNullOrEmpty(authInfo)){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}
			
			boolean flg = AuthenticationUtil.tokenVerification(authInfo.getToken(), authInfo.ppInfo, loginLog.getBrowser());
			if(!flg){
				FailCode.addFails(result, FailCode.PARAM_ERROR);
			}
		}catch (Exception ex) {
			logger.error("获取鉴权失败！buyer=" + loginLog.getUserId(), ex);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		
		return result;
	}
	
}
