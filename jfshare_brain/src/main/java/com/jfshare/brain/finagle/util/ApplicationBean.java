package com.jfshare.brain.finagle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ApplicationBean implements ApplicationContextAware{
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationBean.class);

    private static final long  serialVersionUID = 5508512956753757169L;
	private transient static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.info("spring applicationContext is :" + applicationContext);
		this.applicationContext = applicationContext;
	}
}