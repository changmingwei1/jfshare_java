package com.jfshare.manager.dao;

import com.jfshare.manager.model.TbCommissioner;
import redis.clients.jedis.Jedis;

import java.util.List;

public interface IUserJedis {
	/**
	 * 添加在线用户
	 * @param tbSeller
	 * @param sId 服务器端用户标识
	 */
	int addOnline(TbCommissioner tbCommissioner, String sId);
	
	/**
	 * 删除在线用户
	 * @param uId 用户标识
	 * @return
	 */
	int removeOnline(String uId);
	
	/**
	 * 删除在线用户
	 * @param uId 用户标识
	 * @param sId 服务器端用户标识
	 * @return
	 */
	int removeOnline(String uId, String sId);
	
	/**
	 * 获取在线用户信息
	 * @param sId 服务器端用户标识
	 * @return
	 */
    TbCommissioner getOnlineBySID(String sId);

    /**
     * 获取在线用户信息
     * @param uId 用户标识
     * @return
     */
    TbCommissioner getOnlineByUID(String uId);
    
    /**
     * 获取所有在线用户uId信息
     * @return
     */
    List<String> getOnlines();
    
    /**
     * 分页获取在线用户信息
     * @param curPage 当前页
     * @param pageSize 页大小
     * @return
     */
    List<TbCommissioner> getOnlinesByPage(int curPage, int pageSize);
    
    /**
     * 获取总在线人数
     * @return
     */
    int getOnlinesCount();
    
    /**
     * 用户是否在线
     * @param uId 用户标识
     * @return
     */
	boolean isOnline(String uId);
	
	/**
	 * 用户是否在线
	 * @param uId 用户标识
	 * @param sId 服务器端用户标识
	 * @return
	 */
	boolean isOnline(String uId, String sId);
	
	/**
	 * 记录失败登陆信息
	 * @param tbCommissioner 用户登陆信息
	 * @return
	 */
	int addTryFail(TbCommissioner tbCommissioner);
	
	/**
	 * 获取用户失败登陆次数
	 * @param uId 用户标识
	 * @return
	 */
	int getTryFailCount(String uId);
	
	/**
	 * 清除失败登陆信息
	 * @param uId 用户标识
	 * @return
	 */
	int clearTryFail(String uId);
	
	/**
	 * 获取在线用户登录失效时限
	 * @param uId 用户标识
	 * @return 登录时限s，返回0无时限
	 */
	int getTimeOutByUID(Jedis jedis, String uId);
	
	/**
	 * 更新在线用户登录失效时限
	 * @param uId 用户标识
	 * @param timeout 登录时限s
	 * @return
	 */
	int updateTimeOut(String uId, int timeout);
}
