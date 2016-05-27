package com.jfshare.task.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service
public class ListRedisManager {

	@Resource(name = "jedisPool")
	private JedisPool jedisPool;

	private static final Logger LOGGER = LoggerFactory.getLogger(ListRedisManager.class);

	/**
	 *
	 *  Function:
	 *  功能说明：插入元素
	 *	 使用说明：从链表的尾部插入
	 *	 返回类型: Long
	 *  @param key
	 *  @param strings
	 *  @return   列表内的元素数目
	 */
	public Long lpush(final String key, final String... strings){
		Long result = 0L;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			result = jedis.lpush(key, strings);
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	/**
	 *  Function:
	 *  功能说明：该命令将返回链表中指定位置(index)的元素，index是0-based，表示头部元素，如果index为-1，表示尾部元素。
	 *	 使用说明：
	 *	 返回类型: String
	 *  @param key
	 *  @param index
	 *  @return	如果与该Key关联的不是链表，该命令将返回相关的错误信息。
	 */
	public String queryIndex(final String key, final long index){
		String result = null;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			result = jedis.lindex(key, index);
		}catch (Exception e) {
			if("Could not get a resource from the pool".equals(e.getMessage())){
				LOGGER.error(e.getMessage());
			}else{
				LOGGER.error(e.getMessage(), e);
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	/**
	 *  Function:
	 *  功能说明：弹出头元素
	 *	 使用说明：
	 *	 返回类型: String
	 *  @param key
	 *  @return
	 */
	public String rpop(final String key){
		String result = null;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			result = jedis.rpop(key);
		}catch (Exception e) {
			if("Could not get a resource from the pool".equals(e.getMessage())){
				LOGGER.error(e.getMessage());
			}else{
				LOGGER.error(e.getMessage(), e);
			}
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

	/**
	 *  Function:
	 *  功能说明：获取指定list的长度
	 *	 使用说明：如果key不存在，则返回0
	 *	 返回类型: Long
	 *  @param key
	 *  @return
	 */
	public Long queryLength(final String key){
		Long result = 0L;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			result = jedis.llen(key);
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}
}
