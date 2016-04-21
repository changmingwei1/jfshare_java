/**
 * 
 */
package com.jfshare.common.db.redis;


public class RedisConst {

	
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
    
	//------------------带有短地址和邮编---------------
	/**
	 * 存储省份地址的前缀，以国家为准
	 */
    public static final String _ADD_COUNTRY_CN_MORE= "addCountryMore:CN";
	
	/**
	 * 存储城市地址的前缀，以省份为准
	 */
    public static final String _ADD_PROVINCE_MORE= "addProvinceMore:";
	
	/**
	 * 存储区县地址的前缀，以城市为准
	 */
    public static final String _ADD_CITY_MORE= "addCityMore:";	
	//------------------带有短地址和邮编---------------
    

	/**
	 *根据 区县地址的ID，保存省市区的id
	 */
    public static final String _ADD_COUNTYID= "addCountyId:";	
    
    /**
	 * 更新锁，用来控制同时只有一个服务更新redis的省市区基础数据
	 * 0：没有服务再进行更新；1：有服务再进行更新
	 */
    public static final String _ADD_UPDATE_LOCK= "common:modifylock";

    /**
     * 过期时间，10分钟
     */
    public static final int _EXPIRE_TIME= 600;

    /**
     * 验证码过期时间，10分钟
     */
    public static final int _CAPTCHA_EXPIRE_TIME= 600;

    /**
     * 图片验证码的前缀
     */
    public static final String _CAPTCHA = "captcha:";
    
}
