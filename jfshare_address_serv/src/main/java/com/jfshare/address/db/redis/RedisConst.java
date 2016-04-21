/**
 * 
 */
package com.jfshare.address.db.redis;

public class RedisConst {

	
	/**
	 * 收件人地址存储的 前缀
	 */
    public static final String _RECEIVER_ADDRESS = "receiverAddressNew:";
    
    /**
     * 过期时间，7天过期
     */
    public static final int _EXPIRE_TIME= 604800;//7*24*60*60=604800秒
    
	/**
	 * 存储省份地址的前缀，以国家为准
	 */
    public static final String _ADD_COUNTRY_CN= "addCountry:CN";
	
	/**
	 * 存储城市地址的前缀，以省份为准
	 */
    public static final String _ADD_PROVINCE= "addProvince:";
	
	/**
	 * 存储区县地址的前缀，以城市为准
	 */
    public static final String _ADD_CITY= "addCity:";

    /**
     * 更新锁，用来控制同时只有一个服务更新redis的地址数据
     * 0：没有服务再进行更新；1：有服务再进行更新
     */
    public static final String _ADD_UPDATE_LOCK= "address:modifylock";
}
