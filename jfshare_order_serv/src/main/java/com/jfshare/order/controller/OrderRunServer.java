/** 
 * Project Name:order-serv
 * File Name:StockRunServer.java 
 * Package Name:com.jfshare.order.controller
 * Date:2014年8月30日下午2:10:50 
 * 
*/  
  
package com.jfshare.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName:OrderRunServer <br/>
 * Function: <br/> 
 * Reason:   <br/> 
 * Date:     2014年8月30日 下午2:10:50 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class OrderRunServer {

	private OrderRunServer(){}
	
	private final static Logger logger = LoggerFactory.getLogger(OrderRunServer.class);
	
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
		applicationContext = new ClassPathXmlApplicationContext("/spring/stand-alone.xml");
		applicationContext.start();
		logger.info("$$$order-serv服务启动成功!!!");
    }
    
    /**
     * 服务停止
     */
    public static void serverStop(){
    	if(applicationContext == null){
    		return;
    	}
    	applicationContext.stop();
    }

}
  