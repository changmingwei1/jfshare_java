package com.jfshare.seller.util;

/**
 * Created by lenovo on 2015/11/17.
 */
public class ConstantUtil {
    public static final String IMAGE_KEY = "jfx_seller_serv";

    //[start] redis
    /**
     * 有序在线用户key集合
     * 由于member有过期登陆限制，zset数据需要定时手动设置过期，有一定延迟性
     */
    public static final String REDIS_UIDS = "seller:online:uids";
    /**
     * 客户端维度在线用户前缀
     */
    public static final String REDIS_UID_PREFIX = "seller:online:uid:";
    /**
     * 服务器端Session维度在线用户前缀
     */
    public static final String REDIS_SID_PREFIX = "seller:online:sid:";

    public static final String REDIS_FAIL_PREFIX = "seller:fail:";
    public static final int REDIS_FAIL_TIMEOUT_S = 60*30;

    /**
     * 用户登录时限前缀
     */
    public static final String REDIS_UID_TIMEOUT_PREFIX = "seller:timeout:";

    public static final int SIGNIN_MODEL_SINGLE = 1;
    public static final java.lang.String SIGNIN_MODEL = "login_way";
    //[end]
}
