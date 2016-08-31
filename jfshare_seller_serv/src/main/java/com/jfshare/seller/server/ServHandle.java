package com.jfshare.seller.server;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.BoolResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.seller.LoginLog;
import com.jfshare.finagle.thrift.seller.Seller;
import com.jfshare.finagle.thrift.seller.SellerListResult;
import com.jfshare.finagle.thrift.seller.SellerResult;
import com.jfshare.finagle.thrift.seller.SellerRetParam;
import com.jfshare.finagle.thrift.seller.SellerServ;
import com.jfshare.finagle.thrift.seller.SellerVip;
import com.jfshare.finagle.thrift.seller.SellerVipResult;
import com.jfshare.finagle.thrift.seller.SellersResult;
import com.jfshare.seller.model.TbSeller;
import com.jfshare.seller.service.ISellerSvc;
import com.jfshare.seller.util.FailCode;
import com.jfshare.seller.util.SellerUtil;
import com.jfshare.utils.StringUtil;

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
    	Result result = new Result();
		result.setCode(0);
		try {
			if (StringUtil.isNullOrEmpty(seller)) {
				logger.warn("signin参数验证失败！seller=" + seller);
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}

			sellerSvcImpl.validSeller(result, seller);
			if (result.getCode() == 0) {
				sellerSvcImpl.insert(seller);
			    logger.info(MessageFormat.format("注册成功，buyer[{0}]", seller.getLoginName()));
			}
		} catch (Exception e) {
			logger.error("注册失败！buyer=" + seller, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
//        return null;
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
    	Result result = new Result();
		result.setCode(0);
		try {
			if (StringUtil.isNullOrEmpty(seller)) {
				logger.warn("updateSeller参数验证失败！seller=" + seller);
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}
			
			sellerSvcImpl.updateSeller(seller);
		    logger.info(MessageFormat.format("更新成功，seller[{0}]", seller.getLoginName()));

		} catch (Exception e) {
			logger.error("更新失败！buyer=" + seller, e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
    }

    @Override
    public Result resetSellerPwd(String newPwd, Seller seller) throws TException {
        return null;
    }

	@Override
	public SellerListResult querySellerList(Seller seller, Pagination pagination) throws TException {
		SellerListResult sellerResult = new SellerListResult();
		Result result = new Result();
		result.setCode(0);
		sellerResult.setResult(result);
		try {
			List<Seller> sellers = sellerSvcImpl.getSellerBySeller(seller);
			
			//按照分页的参数自己处理list的获取范围
			if(sellers != null && sellers.size() > 0){
				if(pagination != null){
					int fromIndex = pagination.getNumPerPage() * (pagination.getCurrentPage() - 1);
					int toIndex = pagination.getNumPerPage() * pagination.getCurrentPage();
					if(fromIndex < 0) fromIndex = 0;
					if(toIndex > sellers.size()) toIndex = sellers.size();
					
					pagination.setTotalCount(sellers.size());
					pagination.setPageNumCount((int) Math.ceil(pagination.totalCount / (double) pagination.numPerPage));
					
					List<Seller> trades = sellers.subList(fromIndex, toIndex);
					sellerResult.setSellerList(trades);
					sellerResult.setPagination(pagination);
				} else {
					sellerResult.setSellerList(sellers);
				}
			}
		} catch (Exception e) {
			logger.error("获取卖家信息失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return sellerResult;
	}

	@Override
	public Result insertUserSellerReal(String userId, String sellerId) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(sellerId)) {
				logger.warn("insertUserSellerReal增加会员信息参数验证失败！卖家sellerID=" + sellerId+"卖家userID="+userId  );
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}
			
			int rows = sellerSvcImpl.insertUserSellerRela(userId, sellerId);
			if(rows > 0){
				  logger.info("增加会员列表成功");
			}  
		} catch (Exception e) {
			logger.error("insertUserSellerReal增加会员列表失败    卖家sellerID=" + sellerId+"user买家ID="+userId);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
	}

	@Override
	public Result deleteUserSellerRealByuserId(String userId) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			if (StringUtil.isNullOrEmpty(userId)) {
				logger.warn("deleteUserSellerRealByuserId 删除会员参数验证失败！买家sellerID=" +userId  );
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return result;
			}
			
			int rows = sellerSvcImpl.deleteUserSellerRealByuserId(userId);
			if(rows == 0){
				  logger.info("没有需要删除的会员");
			}  
			if(rows > 0){
				  logger.info("删除会员列表成功");
			}  
		} catch (Exception e) {
			logger.error("deleteUserSellerRealByuserId 删除会员信息失败    买家userID="+userId);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return result;
	}

	@Override
	public SellerVipResult querySellerVipList(String sellerId, Pagination pagination) throws TException {
		SellerVipResult sellerVipResult = new SellerVipResult();
		Result result = new Result();
		result.setCode(0);
		sellerVipResult.setResult(result);
		try {
			if (StringUtil.isNullOrEmpty(sellerId)) {
				logger.warn("querySellerVipList 查询会员信息列表参数验证失败！商家ID=" +sellerId  );
				FailCode.addFails(result, FailCode.PARAM_ERROR);
				return sellerVipResult;
			}
			//查询商家会员列表
			List<SellerVip> vipList = sellerSvcImpl.querySellerVipList(sellerId);
			int vipTotal = 0;//会员总数
			//按照分页的参数自己处理list的获取范围
			if(vipList != null && vipList.size() > 0){
				vipTotal = vipList.size();
				if(pagination != null){
					int fromIndex = pagination.getNumPerPage() * (pagination.getCurrentPage() - 1);
					int toIndex = pagination.getNumPerPage() * pagination.getCurrentPage();
					if(fromIndex < 0) fromIndex = 0;
					if(toIndex > vipList.size()) toIndex = vipList.size();
					
					pagination.setTotalCount(vipList.size());
					pagination.setPageNumCount((int) Math.ceil(pagination.totalCount / (double) pagination.numPerPage));
					
					List<SellerVip> trades = vipList.subList(fromIndex, toIndex);
					sellerVipResult.setVipList(trades);
					sellerVipResult.setPagination(pagination);
					sellerVipResult.setVipTotal(vipTotal);

				} else {
					sellerVipResult.setVipList(vipList);
				}
			}
		} catch (Exception e) {
			logger.error("获取卖家会员信息失败，系统异常！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return sellerVipResult;
	}
}
