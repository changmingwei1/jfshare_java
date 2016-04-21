package com.jfshare.cart.util;

/**
 * 系统常量
 *
 */
public class Constant {
	/**
	 * 分割器
	 */
	public static final String SEPARATOR = ":";

	/**
	 * 线程超时时间(单位：豪秒)
	 */
	public static int DB_THREAD_TIMEOUT = 5000;

	/**
	 * 购物车失效时间30天
	 */
	public static final int FAILURE_TIME = 30 * 24 * 60 * 60;

	/**
	 * 购物车最大商品数
	 */
	public static final int MAX_CART_COUNT = 50;
	
	/**
	 * 迷你购物车商品数量
	 */
	public static final int MINI_CART_COUNT = 5;

	/**
	 * 购物车缓存key的前缀
	 */
	public static final String CART_KEY_PREFIX = "cart";
	
	/**
	 * 购物车增、删、改、合并 的时间戳 缓存的前缀timestamp
	 */
	public static final String CART_CHANGE_KEY_PREFIX = "cart_ts";
	
	/**
	 * 普通商品的type
	 */
	public static final String COMMON_PRODUCT_TYPE = "2";
	
	/**
	 * 待售状态
	 */
	public static final int PRODUCT_WAIT_STATE = 1;
	
	/**
	 * 下架状态
	 */
	public static final int PRODUCT_SOLDOUT_STATE = 2;
	
	/**
	 * 销售中的状态区间
	 */
	public static final int PRODUCT_SELL_STATE_START = 300;

	public static final int PRODUCT_SELL_STATE_END = 399;


	/**
	 * 商品状态redis前缀
	 */
	public static final String PRODUCT_STATE_PREFIX = "PRODUCT_INFO_";
	
	/**
	 * 商品活动时间redis前缀
	 */
	public static final String PRODUCT_LIFECYCLE_PREFIX = "product_lifecycle_";
	
	/**
	 * 库存信息的缓存前缀
	 */
	public static String STOCK_INFO_LOCK = "STOCK_INFO_LOCK";

	/**
	 * 调用外部接口重试次数
	 */
	public static final int RETRY_NUMS = 1;
	
	/**
	 * 未失效商品不需要商品库存总数和sku的库存总数， 返回-1  表明该库存无效
	 */
	public static final int INVALID_STOCK = -1;
	
	/**
	 * 
	 */
	public static final String PRODUCT_STOCK_LOCK_COUNT ="lockProductCount";
}
