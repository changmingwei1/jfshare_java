package com.jfshare.cart.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;

public class BaseRedisManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseRedisManager.class);

	/*private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return this.jedisPool;
	}

	public BaseRedisManager(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}*/

	@Resource(name = "redisSentinel")
	private JedisSentinelPool jedisPool;

	public JedisSentinelPool getJedisPool() {
		return this.jedisPool;
	}

	/**
	 * Function:
	 * 功能说明：设置某个key的过期时间（单位：秒）,在超过该时间后，Key被自动的删除。如果该Key在超时之前被修改，与该键关联的超时将被移除。
	 * 使用说明：
	 * 
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(final String key, final int seconds) {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.expire(key, seconds);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return result;
	}

}
