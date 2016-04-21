package com.jfshare.address.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AddressStart {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
    }
}
