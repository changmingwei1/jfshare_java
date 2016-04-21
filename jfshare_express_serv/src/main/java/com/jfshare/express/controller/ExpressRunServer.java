package com.jfshare.express.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: jerry
 * To change this template use File | Settings | File Templates.
 */
public class ExpressRunServer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressRunServer.class);
	
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("/spring/stand-alone.xml").start();
        LOGGER.info("ExpressRunServer-》服务启动成功!");
    }
}
