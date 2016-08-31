package com.jfshare.message.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.StringValueExp;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.message.dao.mysql.IMessageDao;
import com.jfshare.message.dao.redis.IMessageRedis;
import com.jfshare.message.dao.redis.RedisConst;
import com.jfshare.message.model.TbMessage;
import com.jfshare.message.util.HttpUtil;
import com.jfshare.message.util.MessageConfig;
import com.jfshare.message.util.MessageUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.JsonUtil;


@Component
public class MessageTask {
	private Logger logger = LoggerFactory.getLogger(MessageTask.class);
	@Autowired
	private IMessageDao messageDaoImpl;

	@Autowired
	private IMessageRedis messageJedisImpl;
	
	@Scheduled(cron = "0 0/30 10-21 * * *")
	public void mesasgeTimer() {
		readRedis();
	}
	
	private void readRedis() {
		Map<String, String> messageMap = messageJedisImpl.getHAll(RedisConst._SYSTEM_MESSAGE);
		if(messageMap != null){
			Set<String> keys = messageMap.keySet();
			for (String key : keys) {
				String value = messageMap.get(key);
				logger.info("获取Redis中的数据为：" + value);
				
				Map<String, Object> message = JsonUtil.json2Map(value);
				if(message != null){
					// 判断开始时间 发送ＰＵＳＨ
					int status = Integer.valueOf((String)message.get("status"));
					Date beginDate = DateUtils.str2Date((String)message.get("beginDate"), DateUtils.Simple_Date_Format);
					Date endDate = DateUtils.str2Date((String)message.get("endDate"), DateUtils.Simple_Date_Format);
					
					logger.info("获取Redis中status：" + status);
					
					switch (status) {
					case 1: // 未开始
						if(beginDate.before(DateTime.now().toDate()) && endDate.after(DateTime.now().toDate())){
							// 状态变为进行中
							sendPush(message);
							updateDBAndRedis(message, 2, 1);
						} else if(endDate.before(DateTime.now().toDate())) {
							updateDBAndRedis(message, 3, -1);
						}
						break;
					case 2: // 进行中
						int pushFlag = Integer.valueOf((String)message.get("pushFlag"));
						if(beginDate.before(DateTime.now().toDate()) && 
								endDate.after(DateTime.now().toDate()) && pushFlag == 0){
							sendPush(message);
							updateDBAndRedis(message, -1, 1);
						} else if (endDate.before(DateTime.now().toDate())) {
							updateDBAndRedis(message, 3, -1);
						}
						break;
					case 3: // 已结束
						break;
					default:
						break;
					}
				}
			}
		}
	}
	// 发送ＰＵＳＨ
	private void sendPush(Map<String, Object> message){
		PushMessage pushMessage = new PushMessage();
		pushMessage.setContent((String)message.get("content"));
		pushMessage.setTitle((String)message.get("title"));
		int type = Integer.valueOf(((String)message.get("msgType")));
		pushMessage.setObjType(type);
		pushMessage.setAlert((String)message.get("alert"));
		
		logger.info("================获取Redis中alert============：" + (String)message.get("alert"));
		
		MessageUtil.pushMessage(pushMessage);
	}
	
	/**
	 * 更新数据库及redis
	 * @param message: 消息内容
	 * @param status: 消息状态， －1为保持当前状态
	 * @param pushFlag: 推送标记 0:未处理， 1:已推送. -1表示保持当前状态
	 */
	private void updateDBAndRedis(Map<String, Object> message, int status, int pushFlag){
		// 更新DB
		int id = Integer.valueOf((String)message.get("id"));
		if(pushFlag > -1 ) 
			updateDB(id, pushFlag);
		// 更新Redis
		updateRedis(message, status, pushFlag);
	}
	
	private void updateDB(int id, int pushFlag){
		TbMessage tbMsg = new TbMessage();
		tbMsg.setId(id);	
		tbMsg.setPushFlag(pushFlag);
		messageDaoImpl.updateMessage(tbMsg);
	}
	
	private void updateRedis(Map<String, Object> message, int status, int pushFlag){
		if(status > -1) {
			message.remove("status");
			message.put("status", String.valueOf(status));
		}
		if(pushFlag > -1){
			message.remove("pushFlag");
			message.put("pushFlag", String.valueOf(pushFlag));
		}
		String msg = JsonUtil.mapToJson(message);
		messageJedisImpl.setMap(RedisConst._SYSTEM_MESSAGE, (String)message.get("id"), msg);
	}
}
