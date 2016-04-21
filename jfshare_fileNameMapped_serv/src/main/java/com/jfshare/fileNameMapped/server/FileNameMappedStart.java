package com.jfshare.fileNameMapped.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * *************************************************************************
 * @文件名称: FileNameMappedStart.java
 *
 * @包路径  : com.jfshare.fileNameMapped.server 
 *				 
 * @版权所有: (C) 2015
 *
 * @类描述:  
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年11月29日 - 下午11:16:10 
 *	
 **************************************************************************
 */
public class FileNameMappedStart {
    @SuppressWarnings("resource")
	public static void main(String[] args) {
        new ClassPathXmlApplicationContext("/spring/spring-context.xml").start();
    }
}
