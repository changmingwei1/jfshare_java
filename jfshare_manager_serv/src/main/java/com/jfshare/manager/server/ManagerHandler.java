package com.jfshare.manager.server;

import com.jfshare.finagle.thrift.manager.*;
import com.jfshare.finagle.thrift.result.BoolResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.manager.common.ManagerCommons;
import com.jfshare.manager.model.TbCommissioner;
import com.jfshare.manager.model.TbProductOperator;
import com.jfshare.manager.service.ProductService;
import com.jfshare.manager.service.SlotImageService;
import com.jfshare.manager.service.UserService;
import com.jfshare.manager.util.BeanConvertUtil;
import com.jfshare.manager.util.FailCode;
import com.jfshare.manager.util.ResultBuilder;
import com.jfshare.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service(value = "managerHandler")
public class ManagerHandler extends BaseHandler implements ManagerServ.Iface {
    private Logger logger = LoggerFactory.getLogger(ManagerHandler.class);
    
    @Resource(name = "productService")
    private ProductService productService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private SlotImageService slotImageService;

    @Override
    public Result logProductOpt(ProductOpt productOpt) throws TException {
        Result result = new Result(ManagerCommons.THRIFT_RESULT_SUCC);
        if(productOpt == null
                || StringUtils.isBlank(productOpt.getProductId())
                || StringUtils.isBlank(productOpt.getOperatorId())
                || productOpt.getActiveState() <= 0)
        {
            result.setCode(ManagerCommons.THRIFT_RESULT_FAIL);
            result.addToFailDescList(FailCode.PARAM_ERROR);
            return result;
        }

        try {
            productService.insertProductOptLog(productOpt);
        } catch (Exception e) {
            result.setCode(ManagerCommons.THRIFT_RESULT_FAIL);
            result.addToFailDescList(FailCode.SYS_ERROR);
            return result;
        }

        return result;
    }

    @Override
    public ProductOptResult queryProductOptRecords(QueryConditions conditions) throws TException {
        if(!super.verifyParams(conditions)) {
            return ResultBuilder.createFailProductOptResult(FailCode.parametersEmpty);
        }

        List<ProductOpt> productOpts = null;
        try {
            List<TbProductOperator> tbProductOperators = this.productService.getProductOptRecord(conditions);
            productOpts = super.rConvertTbProductOperators(tbProductOperators);
        } catch (Exception e) {
            logger.error("查询商品操作日志失败", e);
            return ResultBuilder.createFailProductOptResult(FailCode.SYS_ERROR);
        }
        return ResultBuilder.createProductOptResult(productOpts);
    }

    @Override
    public BoolResult isLoginNameExist(String loginName) throws TException {
        BoolResult boolResult = new BoolResult();
        Result result = new Result();
        result.setCode(0);
        boolResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(loginName)) {
                logger.warn("isLoginNameExist参数验证失败！loginName[{}]", loginName);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                boolResult.setResult(result);
                return boolResult;
            }
            loginName = loginName.trim();
            boolean ret = userService.loginNameIsExist(loginName);
            boolResult.setValue(ret);
            logger.info(MessageFormat.format("检测用户名成功，loginName[{0}]", loginName));
        } catch (Exception e) {
            logger.error(MessageFormat.format("检测用户名失败！loginName[{0}]", loginName), e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return boolResult;
    }

    @Override
    public Result signup(Commissioner cs) throws TException {
        return null;
    }

    @Override
    public CommissionerResult signin(Commissioner cs, LoginLog loginLog) throws TException {
        CommissionerResult commissionerResult = new CommissionerResult();
        Result result = new Result();
        result.setCode(0);
        commissionerResult.setResult(result);
        try {
            if (cs == null
                    || StringUtils.isBlank(cs.getLoginName())
                    || StringUtils.isBlank(cs.getPwdEnc())) {
                logger.warn("login参数验证失败！seller[{}],loginLog[{}]", cs, loginLog);
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return commissionerResult;
            }

            this.userService.validLogin(result, cs);
            if (result.getCode() == 1) {
                logger.warn("登陆校验失败，[user:{}] failDescList=[{}]", cs.getLoginName(), result.getFailDescList());
                //user fail times cache，fail log queue
                int ret = this.userService.addTryFail(cs);
                if (ret > 0) {
                    //add to fail mq
                }
                return commissionerResult;
            } else {
                loginLog.setCsId(cs.getCsId());
                loginLog.setTokenId(UUID.randomUUID().toString()); //TODO 生成token策略
                cs.setPwdEnc(null);
                TbCommissioner tbCommissioner = BeanConvertUtil.commissioner2TbCommissioner(cs);
                //write online cache
                this.userService.addOnline(tbCommissioner, loginLog);
                this.userService.clearTryFail(tbCommissioner);
                //add to success mq

                commissionerResult.setCs(cs);
                commissionerResult.setLoginLog(loginLog);
            }
        } catch (Exception e) {
            logger.error("登陆失败！", e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return commissionerResult;
    }

    @Override
    public Result signout(LoginLog loginLog) throws TException {
        Result result = new Result();
        result.setCode(0);
        try {
            if (StringUtil.isNullOrEmpty(loginLog) || StringUtil.isNullOrEmpty(loginLog.getCsId()) ||
                    StringUtil.isNullOrEmpty(loginLog.getTokenId())) {
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return result;
            }

            this.userService.removeOnline(result, loginLog.getCsId(), loginLog.getTokenId());
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
    public CommissionerResult isOnline(LoginLog loginLog) throws TException {
        CommissionerResult commissionerResult = new CommissionerResult();
        Result result = new Result();
        result.setCode(0);
        commissionerResult.setResult(result);
        try {
            if (StringUtil.isNullOrEmpty(loginLog) || StringUtil.isNullOrEmpty(loginLog.getCsId()) ||
                    StringUtil.isNullOrEmpty(loginLog.getTokenId())) {
                FailCode.addFails(result, FailCode.PARAM_ERROR);
                return commissionerResult;
            }
            boolean onlineState = this.userService.isOnline(loginLog.getCsId(), loginLog.getTokenId());
            commissionerResult.setValue(onlineState);
        } catch (Exception e) {
            logger.error("验证在线状态失败！param=" + loginLog, e);
            FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
        }

        return commissionerResult;
    }

    /**
     * 	方法描述:  保存 广告图片
     *
     *	@see com.jfshare.finagle.thrift.manager.ManagerServ.Iface#saveSlotImage(com.jfshare.finagle.thrift.manager.SlotImage)
     *  @author : ljx 创建时间 2015年12月2日 上午3:05:14
     */
	@Override
	public StringResult saveSlotImage(SlotImage slotImage) throws TException { 
		StringResult stringResult = new StringResult();
	    Result result = new Result();
	    result.setCode(0);
	    stringResult.setResult(result);
	    try {
	       
	        String value = this.slotImageService.saveSlotImage(slotImage);
	        stringResult.setValue(value);
	    } catch (Exception e) {
	        logger.error("SlotImage save fail！param=" + slotImage, e);
	        FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
	    }
	
	    return stringResult;
	}

	/**
	 * 	方法描述:  查询广告图片
	 *
	 *	@see com.jfshare.finagle.thrift.manager.ManagerServ.Iface#querySlotImageList(com.jfshare.finagle.thrift.manager.QuerySlotImageParam)
	 *  @author : ljx 创建时间 2015年12月2日 上午3:05:28
	 */
	@Override
	public QuerySlotImageResult querySlotImageList(
			QuerySlotImageParam querySlotImageParam) throws TException {
		QuerySlotImageResult querySlotImageResult = new QuerySlotImageResult();
	    Result result = new Result();
	    result.setCode(0);
	    querySlotImageResult.setResult(result);
	    try {
	       
	    	List<SlotImage> slotImageList = this.slotImageService.querySlotImageList(querySlotImageParam);
	        querySlotImageResult.setSlotImageList(slotImageList);
	    } catch (Exception e) {
	        logger.error("SlotImage querySlotImageList fail！param=" + querySlotImageParam, e);
	        FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
	    }
	
	    return querySlotImageResult;
	}

	@Override
	public StringResult updateSlotImage(SlotImage slotImage) throws TException {
		
		StringResult stringResult = new StringResult();
	    Result result = new Result();
	    result.setCode(0);
	    stringResult.setResult(result);
	    try {
	       
	        String value = this.slotImageService.updateSlotImage(slotImage);
	        stringResult.setValue(value);
	    } catch (Exception e) {
	        logger.error("SlotImage updateSlotImage fail！param=" + slotImage, e);
	        FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
	    }
	
	    return stringResult;
	}

	@Override
	public QuerySlotImageResult querySlotImageOne(int id) throws TException {

		QuerySlotImageResult querySlotImageResult = new QuerySlotImageResult();
	    Result result = new Result();
	    result.setCode(0);
	    querySlotImageResult.setResult(result);
	    try {
	       
	    	SlotImage slotImage = this.slotImageService.querySlotImageOne(id);
	        querySlotImageResult.setSlotImage(slotImage);
	    } catch (Exception e) {
	        logger.error("SlotImage querySlotImageOne fail！param=" + id, e);
	        FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
	    }
	
	    return querySlotImageResult;
	}


}
