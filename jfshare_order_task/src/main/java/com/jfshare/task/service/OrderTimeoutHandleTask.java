package com.jfshare.task.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.Thread.State;

@Service
public class OrderTimeoutHandleTask {
    private Logger logger = LoggerFactory.getLogger(OrderTimeoutHandleTask.class);

    
    @Resource 
    private OrderTimeoutThread orderTimeoutThread;

   
    public  void start()
    {
    	if(orderTimeoutThread.getState() == State.TERMINATED) {
			logger.error("告警：确认收货线程退出了，工作停止了。");
		} else
		if (orderTimeoutThread.getState() == State.NEW)
			orderTimeoutThread.start();
		else if (orderTimeoutThread.getState() != State.RUNNABLE) {
			synchronized (orderTimeoutThread) {
				orderTimeoutThread.notify();
			}
		}
    }

}
