package com.jfshare.score.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScoreStart {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
	}
}

