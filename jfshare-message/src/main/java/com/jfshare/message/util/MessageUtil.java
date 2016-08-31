package com.jfshare.message.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.message.AppUpgradeInfo;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.message.model.TbAppUpgrade;
import com.jfshare.message.model.TbMessage;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.StringUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * Created by lenovo on 2015/11/19.
 */
public class MessageUtil {
	 private static Logger logger = LoggerFactory.getLogger(MessageUtil.class); 
	public static TbMessage Message2TbMessage(SystemMessage sysMessge) {
		TbMessage message = new TbMessage();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(sysMessge);
		BeanUtil.fillBeanData(message, stringObjectMap);
		return message;
	}

	public static TbAppUpgrade AppUpgradeInfo2TbAppUpgrade(AppUpgradeInfo info) {
		TbAppUpgrade message = new TbAppUpgrade();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(info);
		BeanUtil.fillBeanData(message, stringObjectMap);
		return message;
	}
	
	public static List<SystemMessage> process(List<TbMessage> tbMessages) {
		List<SystemMessage> messages = null;
		if (tbMessages != null && tbMessages.size() > 0) {
			messages = new ArrayList<SystemMessage>();
			for (TbMessage tbMessage : tbMessages) {
				SystemMessage message = new SystemMessage();

				if (!StringUtil.isNullOrEmpty(tbMessage.getBeginDate())) {
					message.setBeginDate(DateUtils.date2Str(tbMessage.getBeginDate().toDate()));
				}
				if (!StringUtil.isNullOrEmpty(tbMessage.getContent())) {
					message.setContent(tbMessage.getContent());
				}
				if(!StringUtil.isNullOrEmpty(tbMessage.getCreateTime())){
					message.setCreateTime(DateUtils.date2Str(tbMessage.getCreateTime().toDate()));
				}
				if (!StringUtil.isNullOrEmpty(tbMessage.getEndDate())) {
					message.setEndDate(DateUtils.date2Str(tbMessage.getEndDate().toDate()));
				}
				if (!StringUtil.isNullOrEmpty(tbMessage.getTitle())) {
					message.setTitle(tbMessage.getTitle());
				}
				message.setId(tbMessage.getId());
				message.setPushTarget(tbMessage.getPushtarget());
				// 1:未开始 2:进行中 3:已结束
				if (tbMessage.getBeginDate().isAfterNow()) {
					message.setStatus(1);
				}
				if (tbMessage.getBeginDate().isBeforeNow() && tbMessage.getEndDate().isAfterNow()) {
					message.setStatus(2);
				}
				if (tbMessage.getEndDate().isBeforeNow()) {
					message.setStatus(3);
				}
				message.setMsgType(tbMessage.getMsgType());
				messages.add(message);
			}
		}
		return messages;
	}

	public static Map<String, String> tbMessageToMap(TbMessage tbMessage) {
		Map<String, String> map = new HashMap<String, String>();

		if (!StringUtil.isNullOrEmpty(tbMessage.getBeginDate())) {
			map.put("beginDate", DateUtils.date2Str(tbMessage.getBeginDate().toDate()));
		}
		if (!StringUtil.isNullOrEmpty(tbMessage.getContent())) {
			map.put("content", tbMessage.getContent());
		}
		if (!StringUtil.isNullOrEmpty(tbMessage.getEndDate())) {
			map.put("endDate", DateUtils.date2Str(tbMessage.getEndDate().toDate()));
		}
		if (!StringUtil.isNullOrEmpty(tbMessage.getTitle())) {
			map.put("title", tbMessage.getTitle());
		}
		if (!StringUtil.isNullOrEmpty(tbMessage.getAlert())) {
			map.put("alert", tbMessage.getAlert());
		}
		map.put("id", String.valueOf(tbMessage.getId()));
		map.put("pushTarget", String.valueOf(tbMessage.getPushtarget()));
		// 1:未开始 2:进行中 3:已结束
		if(tbMessage.getBeginDate() != null && tbMessage.getEndDate() != null){
			if (tbMessage.getBeginDate().isAfterNow()) {
				map.put("status", "1");
			}
			if (tbMessage.getBeginDate().isBeforeNow() && tbMessage.getEndDate().isAfterNow()) {
				map.put("status", "2");
			}
			if (tbMessage.getEndDate().isBeforeNow()) {
				map.put("status", "3");
			}
		}
		map.put("pushFlag", String.valueOf(tbMessage.getPushFlag()));
		map.put("msgType", String.valueOf(tbMessage.getMsgType()));

		return map;
	}

	public static int pushMessage(PushMessage message) {
		Map<String, String> extras1 = new HashMap<String, String>();
		Map<String, String> extras2 = new HashMap<String, String>();
		switch(message.getObjType()){
		case 1://1:订单消息
			extras1.put("obj_type", "order");
			extras2.put("obj_type", "order");
			break;
		case 2://2:url类
			extras1.put("obj_type", "url");
			extras2.put("obj_type", "url");
			break;
		default ://0:系统消息
			extras1.put("obj_type", "message");
			extras2.put("obj_type", "message");
			break;
		}
		extras1.put("title", message.getTitle());
		extras2.put("title", message.getTitle());
		
		extras1.put("content", message.getContent());
		extras2.put("content", message.getContent());
		
		Map<String, Object> ios = new HashMap<String, Object>();
		ios.put("alert", message.getAlert());
		ios.put("sound", "sound.caf");
		ios.put("extras", extras1);
		
		Map<String, Object> android = new HashMap<String, Object>();
		android.put("alert", message.getAlert());
		android.put("title", message.getTitle());
		android.put("extras", extras2);
		
		Map<String, Object> notification = new HashMap<String, Object>();
		notification.put("ios", ios);
		notification.put("android", android);
		
		Map<String, Boolean> options = new HashMap<String, Boolean>();
		options.put("apns_production", MessageConfig.JPUSH_PUSH_IS_PRODUCTION);
		
		JSONObject json = new JSONObject();
		json.put("platform", MessageConfig.JPUSH_PUSH_PLATFORM);
		json.put("audience", MessageConfig.JPUSH_PUSH_AUDIENCE);
		json.put("notification", notification);
		json.put("options", options);
		
		return HttpUtil.jsonHttpPost(json.toJSONString(), MessageConfig.JPUSH_PUSH_URL);
	}
	/**
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String localVersion) throws Exception {
		if (version1 == null || localVersion == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
		String[] versionArray2 = localVersion.split("\\.");
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
		int diff = 0;
		while (idx < minLength
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
			++idx;
		}
		//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}
	
	public static PushPayload buildPushObject_android_and_iosByAlias(String alias,String title,String alert,String content,String objType,String orderType) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                		.setAlert(alert)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle(title)
                				.addExtra("obj_type", objType)
                				.addExtra("order_type", orderType)
                				.build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1)
                				.addExtra("title", title)
                				.addExtra("obj_type", objType)
                				.setSound("sound.caf")
                				.addExtra("content", content)
                				.addExtra("order_type", orderType)
                				.build())
                		.build())
                .build();
    }
	
}
