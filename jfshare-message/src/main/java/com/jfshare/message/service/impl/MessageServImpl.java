package com.jfshare.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.message.AppUpgradeInfo;
import com.jfshare.finagle.thrift.message.AppUpgradeInfoStr;
import com.jfshare.finagle.thrift.message.AppUpgradeResult;
import com.jfshare.finagle.thrift.message.AppUpgradeResultStr;
import com.jfshare.finagle.thrift.message.GetUpgradeParam;
import com.jfshare.finagle.thrift.message.GetUpgradeParamStr;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.message.dao.mysql.IMessageDao;
import com.jfshare.message.model.TbAppUpgrade;
import com.jfshare.message.model.TbAppVerinfo;
import com.jfshare.message.model.TbMessage;
import com.jfshare.message.server.ServHandle;
import com.jfshare.message.service.IMessageServ;
import com.jfshare.message.util.FailCode;
import com.jfshare.message.util.HttpUtil;
import com.jfshare.message.util.MessageConfig;
import com.jfshare.message.util.MessageUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

@Service
public class MessageServImpl implements IMessageServ {
	private static final transient Logger logger = LoggerFactory.getLogger(MessageServImpl.class);
	@Autowired
	private IMessageDao messageDaoImpl;

	@Override
	public List<SystemMessage> getAllSystemMessage() {
		List<TbMessage> tbMessages = messageDaoImpl.getAllMessage();
		return MessageUtil.process(tbMessages);
	}

	@Override
	public List<SystemMessage> getSystemMessage(SystemMessage message) {
		
		List<TbMessage> tbMessages = messageDaoImpl.getMessage(message);
		
		return MessageUtil.process(tbMessages);
	}

	@Override
	public int updateSystemMessage(SystemMessage message) {
		TbMessage tbMessage = MessageUtil.Message2TbMessage(message);
		if(!StringUtil.isNullOrEmpty(message.getBeginDate())) {
			tbMessage.setBeginDate(DateUtils.strToDateTime(message.getBeginDate(), DateUtils.Simple_DateTime_Format));
		}
		if(!StringUtil.isNullOrEmpty(message.getEndDate())) {
			tbMessage.setEndDate(DateUtils.strToDateTime(message.getEndDate(), DateUtils.Simple_DateTime_Format));
		}
		return messageDaoImpl.updateMessage(tbMessage);
	}

	@Override
	public int deleteSystemMessage(int id) {
		SystemMessage message = new SystemMessage();
		message.setId(id);
		return messageDaoImpl.deleteMessage(message);
	}

	@Override
	public int addSystemMessage(SystemMessage message) {
		TbMessage tbMessage = MessageUtil.Message2TbMessage(message);
		if(!StringUtil.isNullOrEmpty(message.getBeginDate())) {
//			tbMessage.setBeginDate(DateUtils.strToDateTime(message.getBeginDate(), DateUtils.Simple_Date_Format));
			tbMessage.setBeginDate(DateUtils.strToDateTime(message.getBeginDate(), DateUtils.Simple_DateTime_Format));
		}
		if(!StringUtil.isNullOrEmpty(message.getEndDate())) {
//			tbMessage.setEndDate(DateUtils.strToDateTime(message.getEndDate(), DateUtils.Simple_Date_Format));
			tbMessage.setEndDate(DateUtils.strToDateTime(message.getEndDate(), DateUtils.Simple_DateTime_Format));
		}
		tbMessage.setMsgType(message.getMsgType());
		return messageDaoImpl.addMessage(tbMessage);
	}
	
	@Override
	public int pushMessage(PushMessage message) {
		JSONObject extras = new JSONObject();
		extras.put("obj_type", message.getObjType());
		extras.put("content", message.getContent()==null?"":message.getContent());
		extras.put("title", message.getTitle()==null?"":message.getTitle());
		
		Map<String, String> notification = new HashMap<String, String>();
		notification.put("alert", message.getAlert()==null?"":message.getAlert());
		notification.put("extras", extras.toJSONString());
		
		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put("apns_production", MessageConfig.JPUSH_PUSH_IS_PRODUCTION);
		
		JSONObject json = new JSONObject();
		json.put("platform", MessageConfig.JPUSH_PUSH_PLATFORM);
		json.put("audience", MessageConfig.JPUSH_PUSH_AUDIENCE);
		json.put("notification", notification);
		json.put("options", options);
		
		TbMessage tbMssage=new TbMessage();
		tbMssage.setAlert(message.getAlert()==null?"":message.getAlert());
		tbMssage.setTitle(message.getTitle()==null?"":message.getTitle());
		tbMssage.setContent(message.getContent()==null?"":message.getContent());
		tbMssage.setMsgType(message.getObjType());
		tbMssage.setBeginDate(new DateTime());//
		tbMssage.setEndDate(new DateTime());//
		
		int count = messageDaoImpl.addMessage(tbMssage);
		
		return HttpUtil.jsonHttpPost(json.toJSONString(), MessageConfig.JPUSH_PUSH_URL);
	}

	@Override
	public void getAppUpgradeInfo(AppUpgradeResult result, GetUpgradeParam param) {
		List<TbAppUpgrade> tbAppUpgrades = messageDaoImpl.getAppUpgrade(param);
		if(tbAppUpgrades != null && tbAppUpgrades.size() > 0){
			AppUpgradeInfo info = new AppUpgradeInfo();
			TbAppUpgrade up = tbAppUpgrades.get(0);
			info.setAppType(up.getAppType());
			info.setMaxVersion(up.getMaxVersion());
			info.setMinVersion(up.getMinVersion());
			info.setUpgradeDesc(up.getUpgradeDesc());
			info.setUpgradeType(up.getUpgradeType());
			info.setUrl(up.getUrl());
			info.setVersion(up.getVersion());
			result.setUpgradeInfo(info);
		}
	}

	@Override
	public void getAppUpgradeInfoStr(AppUpgradeResultStr result, GetUpgradeParamStr param) throws Exception {
		List<TbAppVerinfo> tbAppVerinfos = messageDaoImpl.getAppVerinfo(param);
		if(tbAppVerinfos != null && tbAppVerinfos.size() > 0){
			AppUpgradeInfoStr info = new AppUpgradeInfoStr();
			TbAppVerinfo up = tbAppVerinfos.get(0);
			
//			//=====版本升级判断===========START====================
			String minVersion = up.getMinVersion();
			String maxVersion = up.getMaxVersion();
			String version = param.getVersion();

			if(MessageUtil.compareVersion(version,minVersion)<0){
				info.setUpgradeType(MessageConfig.UPGRADE_TYPE_QZ);
			}else if(MessageUtil.compareVersion(version,maxVersion)==0){
				info.setUpgradeType(MessageConfig.UPGRADE_TYPE_BY);
			}else if(MessageUtil.compareVersion(version,minVersion)>0 && MessageUtil.compareVersion(version,maxVersion)<0){
				info.setUpgradeType(MessageConfig.UPGRADE_TYPE_PT);
			}
			//====================END===================================
			
			info.setAppType(up.getAppType());
			info.setMaxVersion(up.getMaxVersion());
			info.setMinVersion(up.getMinVersion());
			info.setUpgradeDesc(up.getUpgradeDesc());
			info.setUrl(up.getUrl());
			info.setVersion(up.getVersion());
			result.setUpgradeInfo(info);
		}
	}
	
	@Override
	@Transactional
	public void addAppUpgradeInfo(Result result, AppUpgradeInfo info) {
		TbAppUpgrade upgrade = MessageUtil.AppUpgradeInfo2TbAppUpgrade(info);
		int count = messageDaoImpl.addAppUpgrade(upgrade);
		
		TbMessage message= new TbMessage();
		
		message.setAlert(upgrade.getUpgradeDesc());
		message.setContent(upgrade.getUpgradeDesc());
		message.setTitle(MessageConfig.UPGRADE_TITLE);
		message.setMsgType(MessageConfig.UPGRADE_MSG_TYPE);
		message.setBeginDate(new DateTime());//
		message.setEndDate(new DateTime());//
		
		int countMs = messageDaoImpl.addMessage(message);
		
		if(count > 0&&countMs>0){
			result.setCode(0);
			addPushMessage(info);
		}
	}

	@Override
	public void updateAppUpgradeInfo(Result result, AppUpgradeInfo info) {
		TbAppUpgrade upgrade = MessageUtil.AppUpgradeInfo2TbAppUpgrade(info);
		int count = messageDaoImpl.updateAppUpgrade(upgrade);
		if(count > 0){
			result.setCode(0);
//			addPushMessage(info);
		}
	}
	
	private void addPushMessage(AppUpgradeInfo info){
		SystemMessage message = new SystemMessage();
		message.setTitle("升级提醒");
		message.setContent(info.getUrl());
		switch(info.getAppType()){
		case 1:// android buyer
			message.setPushTarget(3);
			break;
		case 2: // android seller
			message.setPushTarget(4);
			break;
		case 3:// ios
			message.setPushTarget(5);
			break;
		}
		message.setBeginDate(DateUtils.getCurrentStringDateYMD());
		message.setEndDate(DateUtils.getNextDay(DateUtils.getCurrentStringDateYMD(), "1"));
		addSystemMessage(message);
	}

	@Override
	public void pushMessageBySDK(Result result, String userId, PushMessage message,String orderType) {
		
		if(message==null){
			FailCode.addFails(result, FailCode.requestArgsIsNull);
			return;
		}
		if(StringUtil.isNullOrEmpty(message.getAlert())||StringUtil.isNullOrEmpty(message.getTitle())||
				StringUtil.isNullOrEmpty(message.getObjType())||StringUtil.isNullOrEmpty(message.getContent())){
			FailCode.addFails(result, FailCode.requestArgsIsNull);
			return;
		}
		String title=message.getTitle();
		String content=message.getContent();
		String alert=message.getAlert();
		int objType=message.getObjType();
		
		if(StringUtils.isEmpty(orderType)){
			orderType="";
		}
		
		String objStr=null;
		switch(objType){
		case 1://1:订单消息
			objStr="order";
			break;
		case 2://2:url类
			objStr="url";
			break;
		default ://0:系统消息
			objStr="message";
			break;
		}
		
		String mobileMd5=DigestUtils.md5Hex(userId).toUpperCase();
		
		JPushClient jpushClient = new JPushClient(MessageConfig.JPUSH_PUSH_MASTER_SECRET, MessageConfig.JPUSH_PUSH_APPKEY, 3);
        PushPayload payload = MessageUtil.buildPushObject_android_and_iosByAlias(mobileMd5,title,alert,content,objStr,orderType);

        try {
        	logger.info("消息推送发送报文："+payload.toString());
        	
            PushResult pushResult = jpushClient.sendPush(payload);

            logger.info("---消息推送 返回 result------:"+pushResult);	
        } catch (APIConnectionException e) {
        	e.printStackTrace();
        } catch (APIRequestException e) {
        	e.printStackTrace();
        }
	}
}
