package com.jfshare.manager.dao.impl.redis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

/**
 * *************************************************************************
 * @文件名称: SlotImageJedis.java
 *
 * @包路径  : com.jfshare.manager.dao.impl.redis 
 *				 
 * @版权所有: (C) 2015
 *
 * @类描述:  
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年12月2日 - 上午3:27:50 
 *	
 **************************************************************************
 */
@Repository
public class SlotImageJedis {
	
	private final static transient Logger logger = LoggerFactory.getLogger(SlotImageJedis.class);
	
	@Autowired 
	//private JedisPool jedisPool;
	private JedisSentinelPool jedisPool;
	
	/**
	 * 	方法描述:  保存图片列表列表
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 上午3:29:41
	 */
	public void saveSlotImageKeyType(String key , Map<String, String> hash){
		
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			jedis.hmset(key, hash);
		}catch (Exception e) {
			logger.error("SlotImageJedis saveSlotImageKeyType error！", e);
		} finally{
			jedisPool.returnResourceObject(jedis);
		}
		
	}
	
	/**
	 * 	方法描述:  保存图片列表列表
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 上午3:42:31
	 */
	public void saveSlotImageKeyType(String key , String field, String value){
		
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			jedis.hsetnx(key, field, value);
		}catch (Exception e) {
			logger.error("SlotImageJedis saveSlotImageKeyType error！", e);
		} finally{
			jedisPool.returnResourceObject(jedis);
		}
		
	}
	
	/**
	 * 	方法描述:  查询图片列表
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 上午3:33:49
	 */
	public Map<String, String> querySlotImageByType(String key){
		
		Map<String, String> hash = new HashMap<String, String>();
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			hash = jedis.hgetAll(key);
		}catch (Exception e) {
			logger.error("SlotImageJedis querySlotImageByType error！", e);
		} finally{
			jedisPool.returnResourceObject(jedis);
		}
		
		return hash;
	}
	
	/**
	 * 	方法描述:  删除操作
	 *   
	 *  @author  ljx 创建时间 2015年12月2日 下午10:25:52
	 */
	public void del(String key){
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			jedis.del(key);
		}catch (Exception e) {
			logger.error("SlotImageJedis querySlotImageByType error！", e);
		} finally{
			jedisPool.returnResourceObject(jedis);
		}
	}
	
	/**
	 * 	方法描述:  保存 图片 by id
	 *   
	 *  @author  ljx 创建时间 2015年12月3日 上午7:10:15
	 */
	public String saveSlotImageById(String key, String value){
		
		String result = "";
		
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			result = jedis.set(key, value);
		}catch (Exception e) {
			logger.error("SlotImageJedis querySlotImageByType error！", e);
		} finally{
			jedisPool.returnResourceObject(jedis);
		}
		
		return result;
	}
	
	/**
	 * 	方法描述:  根据id查找一个
	 *   
	 *  @author  ljx 创建时间 2015年12月3日 上午7:08:41
	 */
	public String querySlotImageById(String key){
		
		String result = "";
		
		Jedis jedis = null;
		
		try{
			jedis = jedisPool.getResource();
			result = jedis.get(key);
		}catch (Exception e) {
			logger.error("SlotImageJedis querySlotImageByType error！", e);
		} finally{
			jedisPool.returnResourceObject(jedis);
		}
		
		return result;
	}
}
