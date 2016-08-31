package com.jfshare.message.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.message.dao.mysql.IMessageDao;
import com.jfshare.message.dao.redis.IMessageRedis;
import com.jfshare.message.dao.redis.RedisConst;
import com.jfshare.message.model.TbMessage;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.JsonUtil;
import com.jfshare.utils.StringUtil;

@Repository
public class MessageInit {
	private Logger logger = LoggerFactory.getLogger(MessageInit.class);
	@Autowired
	private IMessageDao messageDaoImpl;

	@Autowired
	private IMessageRedis messageJedisImpl;
	
	@PostConstruct
	public void init() {
		// 初始化DB数据到Redis
		loadToRedis();
		// 启动定时任务
		startTask();
	}
	
	private void loadToRedis(){
		try {
			String updateLock = messageJedisImpl.get(RedisConst._ADD_UPDATE_LOCK);
			if (!"1".equals(updateLock)) {
				// 设置更新锁为1，锁定
				messageJedisImpl.set(RedisConst._ADD_UPDATE_LOCK, "1");
				// 设置过期时间
				messageJedisImpl.setExpire(RedisConst._ADD_UPDATE_LOCK, RedisConst._EXPIRE_TIME);
				
				messageJedisImpl.deleteLikeKey(RedisConst._SYSTEM_MESSAGE);

				Long startTime = System.currentTimeMillis();
				List<TbMessage> messageList = messageDaoImpl.getAllMessage();
				if (messageList == null || messageList.size() <= 0) {
					return;
				}
				logger.info("------系统消息初始化写入Redis缓存--开始------共计：" + messageList.size());
				for (TbMessage tbMessage : messageList) {
					try {
						Map<String, String> map = new HashMap<String, String>();
						
						if(!StringUtil.isNullOrEmpty(tbMessage.getBeginDate())){
							map.put("beginDate", DateUtils.date2Str(tbMessage.getBeginDate().toDate()));
						}
						if(!StringUtil.isNullOrEmpty(tbMessage.getContent())){
							map.put("content", tbMessage.getContent());
						}
						if(!StringUtil.isNullOrEmpty(tbMessage.getEndDate())){
							map.put("endDate", DateUtils.date2Str(tbMessage.getEndDate().toDate()));
						}
						if(!StringUtil.isNullOrEmpty(tbMessage.getTitle())){
							map.put("title", tbMessage.getTitle());
						}
						map.put("id", String.valueOf(tbMessage.getId()));
						map.put("pushTarget", String.valueOf(tbMessage.getPushtarget()));
						// 1:未开始 2:进行中 3:已结束
						if(tbMessage.getBeginDate().isAfterNow()){
							map.put("status", "1");
						}
						if(tbMessage.getBeginDate().isBeforeNow() && tbMessage.getEndDate().isAfterNow()) {
							map.put("status", "2");
						}
						if(tbMessage.getEndDate().isBeforeNow()) {
							map.put("status", "3");
						}
						map.put("pushFlag", String.valueOf(tbMessage.getPushFlag()));
						map.put("msgType", String.valueOf(tbMessage.getMsgType()));
					
						String msg = JsonUtil.mapToJson(map);
						messageJedisImpl.setMap(RedisConst._SYSTEM_MESSAGE, map.get("id"), msg);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
				}
				logger.info("-----系统消息初始化写入Redis缓存-----结束----------用时：" + (System.currentTimeMillis() - startTime));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			// 设置更新锁为0，释放
			messageJedisImpl.set(RedisConst._ADD_UPDATE_LOCK, "0");
			logger.info("初始化系统消息到Redis报错，释放锁。");
		}
	}
	
	private void startTask() {
		
		return;
	}
}
