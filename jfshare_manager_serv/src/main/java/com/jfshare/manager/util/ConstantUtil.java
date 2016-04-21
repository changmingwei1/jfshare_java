package com.jfshare.manager.util;

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
    public static final String REDIS_UIDS = "cs:online:uids";
    /**
     * 客户端维度在线用户前缀
     */
    public static final String REDIS_UID_PREFIX = "cs:online:uid:";
    /**
     * 服务器端Session维度在线用户前缀
     */
    public static final String REDIS_SID_PREFIX = "cs:online:sid:";

    public static final String REDIS_FAIL_PREFIX = "cs:fail:";
    public static final int REDIS_FAIL_TIMEOUT_S = 1800;

    /**
     * 用户登录时限前缀
     */
    public static final String REDIS_UID_TIMEOUT_PREFIX = "cs:timeout:";
    //[end]
}
