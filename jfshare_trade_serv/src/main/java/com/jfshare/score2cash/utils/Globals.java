package com.jfshare.score2cash.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfshare.ridge.PropertiesUtil;
/**
 * 读取配置文件
 * 
 * @author CUIJIANBIN
 *
 */
public class Globals {

	private static final Logger LOGGER = LoggerFactory.getLogger(Globals.class);

	/**
	 * 购物车服务APP_KEY
	 */
	public static final String APP_KEY = "trade-score2cash-serv";

	/**
	 * 订单金额兑换比例
	 */
	public static final double SCORE_TO_CASH_RULE = NumberUtil.parseDouble(PropertiesUtil.getProperty(APP_KEY, "score_to_cash_rule", "1"));
	
	/**
	 * 一元兑换多少积分
	 */
	public static final int SCORE_PER_YUAN = NumberUtil.parseInteger(PropertiesUtil.getProperty(APP_KEY, "score_per_yuan", "100"));
}
