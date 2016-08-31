package com.jfshare.message;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfshare.message.util.MessageConfig;
import com.jfshare.message.util.MessageUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;

public class testJpush {
	 private static Logger logger = LoggerFactory.getLogger(MessageUtil.class); 
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//      return PushPayload.newBuilder()
//              .setPlatform(Platform.android_ios())
//              .setAudience(Audience.alias("alias1"))
//              .setNotification(Notification.android(ALERT, TITLE, null))
//              .build();
		
		
		
		String userMd5=DigestUtils.md5Hex("129");
		
		System.out.println("mobileMd5=="+userMd5);
		
		//-------------------------
		return PushPayload.newBuilder()
		.setPlatform(Platform.android_ios())
        .setAudience(Audience.newBuilder()
                .addAudienceTarget(AudienceTarget.alias(userMd5, userMd5))
                .build())
        .setMessage(Message.newBuilder()
                .setMsgContent("发货乐乐乐乐乐乐乐")
                .addExtra("from", "JPush")
                .build())
        .build();

		//---------------------------
		
//		  return PushPayload.newBuilder()
//	                .setPlatform(Platform.android_ios())
//	                .setAudience(Audience.newBuilder()
//	                        .addAudienceTarget(AudienceTarget.alias(mobileMd5))
//	                        .build())
//	                .setMessage(Message.newBuilder()
//	                		.setTitle("文恒的发货通知")
//	                		.setContentType("")
//	                        .setMsgContent("发货了，发货了，发货了，发货了，发货了")
//	                        .addExtra("from", "JPush")
//	                        .build())
//	                .build();
	    }
	public static PushPayload buildPushObject_all_alias_alert() {
			String userMd5=DigestUtils.md5Hex("35").toUpperCase();
		
		System.out.println("mobileMd5=="+userMd5);
        return PushPayload.newBuilder()
        		.setPlatform(Platform.all())
                .setAudience(Audience.alias(userMd5))
                .setNotification(Notification.alert("风剑 发货了！发货了！！！！！！！"))
                .build();
    }
	
	
	
	public static void main(String[] args) {
		
		JPushClient jpushClient = new JPushClient(MessageConfig.JPUSH_PUSH_MASTER_SECRET, MessageConfig.JPUSH_PUSH_APPKEY, 3);

      // For push, all you need do is to build PushPayload object.
//      PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras();
      PushPayload payload = buildPushObject_all_alias_alert();

      try {
          PushResult result = jpushClient.sendPush(payload);

          logger.info("=====1111111111========="+result);
      } catch (APIConnectionException e) {
          // Connection error, should retry later

      } catch (APIRequestException e) {
          // Should review the error, and fix the request
      }
	}

}
