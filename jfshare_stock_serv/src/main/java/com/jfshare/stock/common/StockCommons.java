/** 
 * Project Name:stock-serv
 * File Name:StockCommons.java 
 * Package Name:com.jfshare.stock
 * Date:2014年9月2日上午11:16:10 
 * 
*/  
  
package com.jfshare.stock.common;


/** 
 * ClassName:StockCommons <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年9月2日 上午11:16:10 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class StockCommons {

	/**
	 * ===================================常量定义===================================
	 */
    
    /**
     * 异步调用方法前缀
     */
    public static final String ASYNCHRONOUS_FUNCTION_PREFIX = "async";
    
    /**
    * redis 中分布式锁的key
    */
	public static final String  ORDER_LOCK_RECORD_KEY = "ORDER_LOCK_RECORD_";
       
   
	/**
     * 库存消息通知代码
     * 0-卖光
     * 1-有库存
     */
 	public static final int  STOCK_NOTIFY_TYPE_SELL_OUT = 0;
 	public static final int  STOCK_NOTIFY_TYPE_STOCK_IN = 1;
 	
 	  
 	public static final String STOCK_OPT_ADD = "ADD";
 	public static final String STOCK_OPT_UPD = "UPD";
 	public static final String STOCK_OPT_DEL = "DEL";
 	public static final String STOCK_OPT_DELALL = "DELALL";
 	
 	
 	public static final int STOCK_RESULT_SUCC = 0;
 	public static final int STOCK_RESULT_FAIL = 1;

    public static final int LOCK_STOCK_EXPIRED_SECOND = 10*24*60*60;
}
  