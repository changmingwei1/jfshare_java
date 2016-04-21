package com.jfshare.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.debug("注入ApplicationContext到SpringContextHolder:" + applicationContext);
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}

}
