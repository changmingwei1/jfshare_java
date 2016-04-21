/**
 * 
 */
package com.jfshare.stock.dao.impl.redis;

/**
 * @author Thinkpad
 *
 */
public class ConstRedis {

	//Redis库存的key前缀
    public final static String STOCK_KEY = "cache_tock:";
    
    //Redis库存field（总库存量）
    public final static String STOCK_FIELD_TOTAL = "total";
    
    //Redis库存field（总锁定量）
    public final static String STOCK_FIELD_TOTALLOCK = "locktotal";
    
    //Redis库存field前缀（sku库存量）
    public final static String STOCK_FIELD_COUNT = "count:";
    //修改存储 的版本前缀
    public final static String STOCK_FIELD_LOCKCOUNT = "lockcount:";

    /*
     * 锁定库存订单信息前缀 
     */
    public static String  ORDER_LOCK_STOCK = "ORDER_LOCK_STOCK:";
    
    /*
     * 锁定库存订单信息前缀 
     */
    public static String  LAST_UPDATE_TIME = "last_update_time";
}
