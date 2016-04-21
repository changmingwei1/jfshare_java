package com.jfshare.buyer.util;

/**
 * Created by lenovo on 2015/11/17.
 */
public class ConstantUtil {
    public static final String IMAGE_KEY = "jfx_buyer_serv";

    //[start] redis
    /**
     * 有序在线用户key集合
     * 由于member有过期登陆限制，zset数据需要定时手动设置过期，有一定延迟性
     */
    public static final String REDIS_UIDS = "user:online:uids";
    /**
     * 客户端维度在线用户前缀
     */
    public static final String REDIS_UID_PREFIX = "user:online:uid:";
    /**
     * 服务器端Session维度在线用户前缀
     */
    public static final String REDIS_SID_PREFIX = "user:online:sid:";

    public static final String REDIS_FAIL_PREFIX = "user:fail:";
    public static final int REDIS_FAIL_TIMEOUT_S = 60*60*24;

    public static final int SIGNIN_MODEL_SINGLE = 1;

    /**
     * 用户登录时限前缀
     */
    public static final String REDIS_UID_TIMEOUT_PREFIX = "user:timeout:";
    public static final java.lang.String SIGNIN_MODEL = "login_way";
    //[end]
}
