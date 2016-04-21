package com.jfshare.common.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CommonStart {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
    }
}
