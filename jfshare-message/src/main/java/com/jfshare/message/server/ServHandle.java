package com.jfshare.message.server;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.finagle.thrift.message.AppUpgradeInfo;
import com.jfshare.finagle.thrift.message.AppUpgradeResult;
import com.jfshare.finagle.thrift.message.AppUpgradeResultStr;
import com.jfshare.finagle.thrift.message.GetUpgradeParam;
import com.jfshare.finagle.thrift.message.GetUpgradeParamStr;
import com.jfshare.finagle.thrift.message.MessageServ;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.finagle.thrift.message.SystemMessageResult;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.message.service.IMessageServ;
import com.jfshare.message.util.FailCode;
import com.jfshare.message.util.RequestCheck;
import com.jfshare.utils.StringUtil;

@Service(value = "handler")
public class ServHandle implements MessageServ.Iface {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Autowired
	private IMessageServ messageServImpl;

	@Override
	public Result addSystemMessage(SystemMessage message) throws TException {
		Result result = new Result();
		result.setCode(0);
		try {
			RequestCheck.checkMessage(result, message);

			if (result.getCode() == 0) {
				messageServImpl.addSystemMessage(message);
			}

		} catch (Exception ex) {
			logger.error("添加系统消息失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return result;
	}

	@Override
	public SystemMessageResult getSystemMessage(SystemMessage message) throws TException {
		SystemMessageResult messageResult = new SystemMessageResult();
		Result result = new Result();
		result.setCode(0);
		messageResult.setResult(result);
		try {
			if (StringUtil.isNullOrEmpty(message)) {
				// 获取全部
				List<SystemMessage> messages = messageServImpl.getAllSystemMessage();
				messageResult.setMessages(messages);

			} else {
				// 按条件查询
//				if (StringUtil.isNullOrEmpty(message.getTitle()) && message.getStatus() <= 0 && message.getId() <= 0) {
//					logger.warn("请求参数错误" + message.toString());
//					FailCode.addFails(result, FailCode.requestArgsIsNull);
//				} else {
//					List<SystemMessage> messages = messageServImpl.getSystemMessage(message);
//					messageResult.setMessages(messages);
//				}
				List<SystemMessage> messages = messageServImpl.getSystemMessage(message);
				messageResult.setMessages(messages);
			}

		} catch (Exception ex) {
			logger.error("获取系统消息失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}

		return messageResult;
	}

	@Override
	public Result deleteSystemMessage(int id) throws TException {
		Result result = new Result();
		result.setCode(0);
		try{
			messageServImpl.deleteSystemMessage(id);
		}catch (Exception ex) {
			logger.error("获取系统消息失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return result;
	}

	@Override
	public Result updateSystemMessage(SystemMessage message) throws TException {
		Result result = new Result();
		result.setCode(0);
		try{
			if(StringUtil.isNullOrEmpty(message) || StringUtil.isNullOrEmpty(message.getId())){
				FailCode.addFails(result, FailCode.requestArgsIsNull);
			}

			if (result.getCode() == 0) {
				messageServImpl.updateSystemMessage(message);
			}
			
		}catch(Exception ex) {
			logger.error("获取系统消息失败！", ex);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return result;
	}
	
	@Override
	public Result sendPush(PushMessage message) throws TException {
		Result result = new Result();
		result.setCode(0);
		try{
			int status = messageServImpl.pushMessage(message);
			if(status != 200){
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
		}catch (Exception e){
			logger.error("发送PUSH消息失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		
		return result;
	}
    
	//根据ID查询tb_app_upgrade表
	@Override
	public AppUpgradeResult getAppUpgradeInfo(GetUpgradeParam param) throws TException {
		AppUpgradeResult appUpqradeResult = new AppUpgradeResult();
		Result result = new Result();
		result.setCode(0);
		appUpqradeResult.setResult(result);
		try{
			messageServImpl.getAppUpgradeInfo(appUpqradeResult, param);
		} catch(Exception e){
			logger.error("获取客户端升级信息失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return appUpqradeResult;
	}

	@Override
	public Result addAppUpgradeInfo(AppUpgradeInfo info) throws TException {
		Result result = new Result();
		result.setCode(0);
		try{
			messageServImpl.addAppUpgradeInfo(result, info);
		} catch(Exception e){
			logger.error("添加客户端升级信息失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return result;
	}

	@Override
	public Result updateAppUpgradeInfo(AppUpgradeInfo info) throws TException {
		Result result = new Result();
		result.setCode(0);
		try{
			messageServImpl.updateAppUpgradeInfo(result, info);
		} catch(Exception e){
			logger.error("更新客户端升级信息失败！", e);
			FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
		}
		return result;
	}
	
	//=================== 获取版本新接口=========================
	//根据ID查询tb_app_verinfo表
		@Override
		public AppUpgradeResultStr getAppUpgradeInfoStr(GetUpgradeParamStr param) throws TException {
			AppUpgradeResultStr appUpqradeResult = new AppUpgradeResultStr();
			Result result = new Result();
			result.setCode(0);
			appUpqradeResult.setResult(result);
			try{
				messageServImpl.getAppUpgradeInfoStr(appUpqradeResult, param);
			} catch(Exception e){
				logger.error("获取客户端升级信息失败！", e);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return appUpqradeResult;
		}
		
	//=================== api形式调用极光推送=========================
	//依据据sdk api形式调用极光推送
		@Override
		public Result pushMessageInfo(String userId, PushMessage pushMessage,String orderType) throws TException {
			Result result = new Result();
			result.setCode(0);
			try{
				messageServImpl.pushMessageBySDK(result,userId, pushMessage, orderType);
			} catch(Exception e){
				logger.error("推送消息失败！", e);
				FailCode.addFails(result, FailCode.SYSTEM_EXCEPTION);
			}
			return result;
		}

}
