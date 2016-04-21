package com.jfshare.cart.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CartStart {
	
	private CartStart(){
		
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CartStart.class);
	
	private static AbstractApplicationContext applicationContext;
	
	public static AbstractApplicationContext getAbstractApplicationContext(){
		return applicationContext;
	}
	
    public static void main(String[] args) {
    	serverStart();
    }
    
    /**
     * 服务启动
     */
    public static void serverStart(){
    	LOGGER.info("服务准备启动...");
    	applicationContext = new ClassPathXmlApplicationContext("/spring/spring-context.xml");
    	applicationContext.start();
    	LOGGER.info("服务启动成功!");
    }
    
    /**
     * 服务停止
     */
    public static void serverStop(){
    	LOGGER.info("服务准备关闭...");
    	if(applicationContext == null){
    		LOGGER.info("未发现服务");
    		return;
    	}
    	applicationContext.stop();
    	LOGGER.info("服务已经关闭！");
    }
}
