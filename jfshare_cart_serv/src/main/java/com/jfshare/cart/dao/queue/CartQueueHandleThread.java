package com.jfshare.cart.dao.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CartQueueHandleThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartQueueHandleThread.class);

	@Autowired
	private MessageHandle messageHandle;
	
	public void run() {
		while (true) {
			try {
				messageHandle.execute();
			} catch (Exception e) {
				LOGGER.error( "CartQueueHandleThread 异常!!", e);
			}
		}
	}
}
