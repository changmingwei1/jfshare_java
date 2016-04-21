package com.jfshare.task.server;

import com.jfshare.task.service.OrderConfirmHandleTask;
import com.jfshare.task.service.OrderTimeoutHandleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class ServHandle extends Thread  {
	private static final transient Logger logger = LoggerFactory.getLogger(ServHandle.class);
	@Resource(name="orderConfirmHandleTask")
	private OrderConfirmHandleTask orderConfirmHandleTask;
	@Resource(name="orderTimeoutHandleTask")
	private OrderTimeoutHandleTask orderTimeoutHandleTask;

	public void run(){
		logger.info("PollingTask开始");
		orderConfirmHandleTask.start();
		orderTimeoutHandleTask.start();
	}

}
