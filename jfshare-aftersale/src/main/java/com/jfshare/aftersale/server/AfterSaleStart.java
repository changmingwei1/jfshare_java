package com.jfshare.aftersale.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * @author zhaoshenghai
 *
 */
public class AfterSaleStart {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
	}

}
