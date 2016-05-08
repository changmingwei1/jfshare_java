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
    public final static String STOCK_KEY = "cache_stock" + ConstRedis.SPLIT;
    
    //Redis库存field前缀（sku库存量）
    public final static String STOCK_FIELD_COUNT = "count" + ConstRedis.SPLIT;
    //修改存储 的版本前缀
    public final static String STOCK_FIELD_LOCKCOUNT = "lockcount" + ConstRedis.SPLIT;

    public final static String SPLIT = ":";

    /*
     * 锁定库存订单信息前缀 
     */
    public final static String  ORDER_LOCK_STOCK = "ORDER_LOCK_STOCK"  + ConstRedis.SPLIT;
    
    /*
     * 锁定库存订单信息前缀 
     */
    public final static String  LAST_UPDATE_TIME = "last_update_time";
}
