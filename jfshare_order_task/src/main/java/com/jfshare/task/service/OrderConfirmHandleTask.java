package com.jfshare.task.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.Thread.State;

@Service
public class OrderConfirmHandleTask {
    private Logger logger = LoggerFactory.getLogger(OrderConfirmHandleTask.class);

    
    @Resource 
    private OrderConfirmThread orderConfirmThread;
    
    
   
    public  void start()
    {
    	if(orderConfirmThread.getState() == State.TERMINATED) {
			logger.error("告警：确认收货线程退出了，工作停止了。");
		} else
		if (orderConfirmThread.getState() == State.NEW)
			orderConfirmThread.start();
		else if (orderConfirmThread.getState() != State.RUNNABLE) {
			synchronized (orderConfirmThread) {
				orderConfirmThread.notify();
			}
		}
    }

}
