package com.jfshare.message.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 消息入口服务
 * @author zhaoshenghai
 *
 */
public class MessageStart {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
	}
}
