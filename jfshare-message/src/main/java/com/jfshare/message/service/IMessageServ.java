package com.jfshare.message.service;

import java.util.List;

import com.jfshare.finagle.thrift.message.AppUpgradeInfo;
import com.jfshare.finagle.thrift.message.AppUpgradeResult;
import com.jfshare.finagle.thrift.message.AppUpgradeResultStr;
import com.jfshare.finagle.thrift.message.GetUpgradeParam;
import com.jfshare.finagle.thrift.message.GetUpgradeParamStr;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.finagle.thrift.result.Result;

public interface IMessageServ {
	List<SystemMessage> getAllSystemMessage();
	List<SystemMessage> getSystemMessage(SystemMessage message); 
	int updateSystemMessage(SystemMessage message);
	int deleteSystemMessage(int id);
	int addSystemMessage(SystemMessage message);
	int pushMessage(PushMessage message);
	void getAppUpgradeInfo(AppUpgradeResult result, GetUpgradeParam param);
	void getAppUpgradeInfoStr(AppUpgradeResultStr result, GetUpgradeParamStr param)  throws Exception ;
	void addAppUpgradeInfo(Result result, AppUpgradeInfo info);
	void updateAppUpgradeInfo(Result result, AppUpgradeInfo info);
	void pushMessageBySDK(Result result, String userId, PushMessage message,String orderType);
}
