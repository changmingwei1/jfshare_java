/** 
 * Project Name:manager-serv
 * File Name:StockRunServer.java 
 * Package Name:com.jfshare.manager.controller
 * Date:2014年8月30日下午2:10:50 
 * 
*/  
  
package com.jfshare.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName:StockRunServer <br/> 
 * Function: <br/> 
 * Reason:   <br/> 
 * Date:     2014年8月30日 下午2:10:50 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class ManagerRunServer {

	private ManagerRunServer(){}
	
	private final static Logger logger = LoggerFactory.getLogger(ManagerRunServer.class);
	
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
		logger.info("$$$manager-serv服务启动成功!!!");
		//fastjson初始化
		com.jfshare.manager.util.fastjsonex.FastJsonExUtils.initSupportDateTimeEx();
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
  