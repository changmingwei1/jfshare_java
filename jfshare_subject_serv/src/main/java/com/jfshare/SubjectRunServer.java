package com.jfshare;


import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SubjectRunServer {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("/spring/stand-alone.xml").start();

    }
}
