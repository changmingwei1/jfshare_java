package com.jfshare.cart.dao.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.jfshare.cart.util.NumberUtil;
import com.jfshare.cart.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartQueue {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartQueue.class);

	private static final BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(50000);

	/**
	 * 添加队列
	 * 
	 * @param
	 */
	public static void add(String userId, String productId, String skuNum, Integer flag) {
		if (StringUtil.empty(userId) || StringUtil.empty(productId))
			return;

		// 过滤sessionId OR 0
		if (NumberUtil.parseLong(userId) > Integer.MAX_VALUE || NumberUtil.parseLong(userId) == 0)
			return;

		try {
			Message message = new Message(NumberUtil.parseInteger(userId), productId, skuNum, flag);
			LOGGER.info(userId + "," + productId + "," + skuNum + "," + flag + "添加队列");
			if (!queue.offer(message))
				LOGGER.info(userId + "," + productId + "添加购物车队列失败!");
		} catch (Exception e) {
			LOGGER.error("queue offer error!", e);
		}
	}

	/**
	 * 获取队列大小
	 * 
	 * @return
	 */
	public static int size() {
		return queue.size();
	}

	/**
	 * 获取队列元素
	 * 
	 * @return
	 */
	public static Message get() {
		Message message = null;
		try {
			message = queue.take();
		} catch (Exception e) {
			LOGGER.error("queue take error!", e);
		}
		return message;
	}
}
