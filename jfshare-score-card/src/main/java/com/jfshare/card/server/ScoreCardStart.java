package com.jfshare.card.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScoreCardStart {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
	}
}

