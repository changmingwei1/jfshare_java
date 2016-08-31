/**
 * 
 */
package com.jfshare.message.dao.redis;

public class RedisConst {

	
	/**
	 * 收件人地址存储的 前缀
	 */
    public static final String _SYSTEM_MESSAGE = "message:systemMessage";
    
    /**
     * 过期时间，7天过期
     */
    public static final int _EXPIRE_TIME= 604800;//7*24*60*60=604800秒

    /**
     * 更新锁，用来控制同时只有一个服务更新redis的地址数据
     * 0：没有服务再进行更新；1：有服务再进行更新
     */
    public static final String _ADD_UPDATE_LOCK= "message:systemMessage:modifylock";
}
