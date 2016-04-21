package com.jfshare.seller.server;

import com.jfshare.finagle.thrift.result.BoolResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.seller.*;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.service.ISellerSvc;
import com.jfshare.seller.util.FailCode;
import com.jfshare.seller.util.SellerUtil;
import com.jfshare.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service(value="handler")
public class ServHandle implements SellerServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);

    @Autowired
    private ISellerSvc sellerSvcImpl;

	//TODO 实现获取卖家
	@Override
	public SellerResult querySeller(int sellerId, SellerRetParam param) throws TException {
		SellerResult sellerResult = new SellerResult();
		Result result = new Result();
		result.setCode(0);
		sellerResult.setResult(result);
		try {
			Seller seller = sellerSvcImpl.getSeller(sellerId);
			sellerResult.setSeller(seller);
		} catch (Exception e) {
			logger.error("获取卖家信息失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return sellerResult;
	}

    @Override
    public SellersResult querySellerBatch(List<Integer> sellerIds, SellerRetParam param) throws TException {
        SellersResult sellersResult = new SellersResult();
        Result result = new Result();
        result.setCode(0);
        sellersResult.setResult(result);
        try {
            if (sellerIds == null || sellerIds.isEmpty() || StringUtil.isNullOrEmpty(param)) {
                logger.warn("querySellerBatch参数验证失败！");
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return sellersResult;
            }
            for (Integer sellerId: sellerIds) {
                if (sellerId == null || sellerId <= 0) {
                    logger.warn("querySellerBatch 参数sellerids有误，验证失败！sellerIds" + sellerIds);
                    FailCode.addFails(result, FailCode.PARAM_ERROR);
                    return sellersResult;
                }
            }

            List<Seller> sellerList = sellerSvcImpl.getSellerList(sellerIds);
            Map<Integer, SellerResult> sellersMap = new HashMap<Integer, SellerResult>();
            for (Seller seller: sellerList) {
                SellerResult sellerRet = new SellerResult();
                sellerRet.setSeller(seller);
                sellersMap.put(seller.getSellerId(), sellerRet);
            }
            sellersResult.setSellerMap(sellersMap);
        } catch (Exception e) {
            logger.error("获取卖家信息失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return sellersResult;
    }

    @Override
    public BoolResult isLoginNameExist(String loginName) throws TException {
        BoolResult boolResult = new BoolResult();
        Result result = new Result();
        result.setCode(0);
        boolResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(loginName)) {
                logger.warn("SellerIsExist参数验证失败！loginName[{}]", loginName);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                boolResult.setResult(result);
                return boolResult;
            }
            loginName = loginName.trim();
            boolean ret = sellerSvcImpl.sellerIsExist(loginName);
            boolResult.setValue(ret);
            logger.info(MessageFormat.format("检测用户名成功，loginName[{0}]", loginName));
        } catch (Exception e) {
            logger.error(MessageFormat.format("检测用户名失败！loginName[{0}]", loginName), e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return boolResult;
    }

    @Override
    public Result signup(Seller seller) throws TException {
        return null;
    }

    @Override
    public SellerResult signin(Seller seller, LoginLog loginLog) throws TException {
        SellerResult sellerResult = new SellerResult();
        Result result = new Result();
        result.setCode(0);
        sellerResult.setResult(result);
        try {
            if (seller == null
                    || StringUtils.isBlank(seller.getLoginName())
                    || StringUtils.isBlank(seller.getPwdEnc())) {
                logger.warn("login参数验证失败！seller[{}],loginLog[{}]", seller, loginLog);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return sellerResult;
            }

            sellerSvcImpl.validLogin(result, seller);
            if (result.getCode() == 1) {
                logger.warn("登陆校验失败，[user:{}] failDescList=[{}]", seller.getLoginName(), result.getFailDescList());
                //user fail times cache，fail log queue
                int ret = sellerSvcImpl.addTryFail(seller);
                if (ret > 0) {
                    //add to fail mq
                }
                return sellerResult;
            } else {
                loginLog.setSellerId(seller.getSellerId());
                loginLog.setTokenId(UUID.randomUUID().toString()); //TODO 生成token策略
                seller.setPwdEnc(null);
                TbSeller tbSeller = SellerUtil.seller2TbSeller(seller);
                //write online cache
                sellerSvcImpl.addOnline(tbSeller, loginLog);
                sellerSvcImpl.clearTryFail(tbSeller);
                //add to success mq

                sellerResult.setSeller(seller);
                sellerResult.setLoginLog(loginLog);
            }
        } catch (Exception e) {
            logger.error("登陆失败！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return sellerResult;
    }

    @Override
    public Result signout(LoginLog loginLog) throws TException {
        Result result = new Result();
        result.setCode(0);
        try {
            if (StringUtil.isNullOrEmpty(loginLog) || StringUtil.isNullOrEmpty(loginLog.getSellerId()) ||
                    StringUtil.isNullOrEmpty(loginLog.getTokenId())) {
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }

            sellerSvcImpl.removeOnline(result, loginLog.getSellerId(), loginLog.getTokenId());
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
    public SellerResult isOnline(LoginLog loginLog) throws TException {
        SellerResult sellerResult = new SellerResult();
        Result result = new Result();
        result.setCode(0);
        sellerResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(loginLog) || StringUtil.isNullOrEmpty(loginLog.getSellerId()) ||
                    StringUtil.isNullOrEmpty(loginLog.getTokenId())) {
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return sellerResult;
            }
            boolean onlineState = sellerSvcImpl.isOnline(loginLog.getSellerId(), loginLog.getTokenId());
            sellerResult.setValue(onlineState);
        } catch (Exception e) {
            logger.error("验证在线状态失败！param=" + loginLog, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return sellerResult;
    }

    @Override
    public SellerResult getOnlineInfo(LoginLog loginLog) throws TException {
        return null;
    }

    @Override
    public StringResult pwdFind(int findWay, String account) throws TException {
        return null;
    }

    @Override
    public Result updateSeller(Seller seller) throws TException {
        return null;
    }

    @Override
    public Result resetSellerPwd(String newPwd, Seller seller) throws TException {
        return null;
    }
}
