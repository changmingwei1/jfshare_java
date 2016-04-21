package com.jfshare.cart.dao.queue;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jfshare.cart.dao.redis.MapRedisManager;
import com.jfshare.cart.model.entity.TbCartMessageLog;
import com.jfshare.cart.service.impl.TbCartMessageLogService;
import com.jfshare.cart.util.CartMessageStatus;
import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandle {
	
	private Logger LOGGER = LoggerFactory.getLogger(MessageHandle.class);

	private static final ExecutorService executor = Executors.newFixedThreadPool(5);

	@Autowired
	private TbCartMessageLogService tbCartMessageLogService;

	@Autowired
	private MapRedisManager readCartRedisManager;

	public void execute() {
		Message message = CartQueue.get();
		if (message == null || message.getUserId() <= 0 || StringUtil.empty(message.getProductId()))
			return ;
		
		LOGGER.info("cart message:::" + message.getUserId() + "," + message.getProductId());
		// 队列大小
		int size = CartQueue.size();
		LOGGER.info("购物车队列大小" + size);
		int sign = 0;
		if (message.getFlag() == 1) { // 添加操作
			TbCartMessageLog t = tbCartMessageLogService.findByUserIdAndProductIdAndStatus(message.getUserId(), message.getProductId(), CartMessageStatus.NORMAL.getCode());
			if(t == null)
				sign = 1;
		} else if (message.getFlag() == 2) { // 删除操作
			String key = StringUtil.join(Constant.CART_KEY_PREFIX, message.getUserId() + "", Constant.SEPARATOR);
			Map<String, String> cartMap = readCartRedisManager.getMap(key);

			boolean b = isExistOtherSku(cartMap, message.getProductId());
			if(!b)
				sign = 1;
		}
		if(sign == 1){
			if (message.getFlag() == 1) { // 添加操作
				TbCartMessageLog t = new TbCartMessageLog();
				t = new TbCartMessageLog();
				t.setUserId(message.getUserId());
				t.setProductId(message.getProductId());
				t.setStatus(CartMessageStatus.NORMAL.getCode());
				tbCartMessageLogService.insert(t);
			} else if (message.getFlag() == 2) { // 删除操作
				tbCartMessageLogService.deleteByUserIdAndProductIdAndStatus(message.getUserId(), message.getProductId(), CartMessageStatus.NORMAL.getCode());
			}
		}
//			executor.execute(new Task(message));
	}
	
	class Task implements Runnable {
		Message message = null;

		public Task(Message message) { 
			this.message = message;
		}

		@Override
		public void run() {
			try {
				handle(message);
			} catch (Exception e) {
				LOGGER.error("MessageHandle.execute error!", e);
			}
		}
	}

	private void handle(Message message) {
		if (message.getFlag() == 1) { // 添加操作
			TbCartMessageLog t = new TbCartMessageLog();
			t = new TbCartMessageLog();
			t.setUserId(message.getUserId());
			t.setProductId(message.getProductId());
			t.setStatus(CartMessageStatus.NORMAL.getCode());
			tbCartMessageLogService.insert(t);
		} else if (message.getFlag() == 2) { // 删除操作
			tbCartMessageLogService.deleteByUserIdAndProductIdAndStatus(message.getUserId(), message.getProductId(), CartMessageStatus.NORMAL.getCode());
		}
	}

	private boolean isExistOtherSku(Map<String, String> cartMap, String productId) {
		if (cartMap == null || cartMap.isEmpty())
			return false;

		int i = 0;
		for (String s : cartMap.keySet()) {
			if (s.contains(productId)) {
				i++;
				if (i > 1)
					return true;
			}
		}
		return false;
	}
}
