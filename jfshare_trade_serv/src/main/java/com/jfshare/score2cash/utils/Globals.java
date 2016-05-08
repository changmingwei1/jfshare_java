package com.jfshare.score2cash.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 读取配置文件
 * 
 * @author CUIJIANBIN
 *
 */
public class Globals {

	private static final Logger LOGGER = LoggerFactory.getLogger(Globals.class);

	private static Properties properties = new Properties();

	static {
		try {
			// 服务变量配置文件路径
			String FileConfig = "/config/globals.properties";
			properties.load(Globals.class.getResourceAsStream(FileConfig));
		} catch (Exception e) {
			LOGGER.error("读取globals配置文件出错", e);
		}
	}
	
	/**
	 * 购物车服务APP_KEY
	 */
	public static final String APP_KEY = "trade-score2cash-serv";
	public static final int SERVER_PORT = NumberUtil.parseInteger(getProperty("serverPort"));
	public static final int WORK_THREAD_COUNT = NumberUtil.parseInteger(getProperty("workthreadCount"));
	
	/*public static final String SCORE_SERVER_IP = getProperty((String) properties.get("score_server_ip_key"));
	public static final int SCORE_SERVER_PORT = NumberUtil.parseInteger(getProperty((String) properties.get("score_server_port_key")));*/
	
	/**
	 * 订单金额兑换比例
	 */
	public static final double SCORE_TO_CASH_RULE = NumberUtil.parseDouble(getProperty("score_to_cash_rule"));
	
	/**
	 * 一元兑换多少积分
	 */
	public static final int SCORE_PER_YUAN = NumberUtil.parseInteger(getProperty("score_per_yuan"));

	/************************** 读取zookeeper配置 ******************************/
	/**
	 * 读取配置文件
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return getProperty(APP_KEY, key);
	}

	/**
	 * 读取配置文件
	 * 
	 * @param appKey
	 * @param key
	 * @return
	 */
	public static String getProperty(String appKey, String key) {
		return PropertiesUtil.getProperty(appKey, key);
	}
}
