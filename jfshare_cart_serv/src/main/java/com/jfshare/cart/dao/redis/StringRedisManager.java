package com.jfshare.cart.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class StringRedisManager extends BaseRedisManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringRedisManager.class);

	/*public StringRedisManager(JedisPool jedisPool) {
		super(jedisPool);
	}*/

	/**
	 * Function: 功能说明：设置
	 * 
	 * @param key
	 * @param value
	 */
	public void set(final String key, final String value) {
		Jedis jedis = null;
		try {
			jedis = this.getJedisPool().getResource();
			jedis.set(key, value);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
	}

	/**
	 * Function: 功能说明：获取 使用说明：
	 * 
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = this.getJedisPool().getResource();
			String v = jedis.get(key);
			if(v == null){
				LOGGER.info("读取redis无数据：key=" + key);
				return null;
			}
			result = new String(v);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return result;
	}

}
