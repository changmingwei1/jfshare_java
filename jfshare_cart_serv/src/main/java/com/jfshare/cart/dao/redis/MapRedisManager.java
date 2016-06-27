package com.jfshare.cart.dao.redis;

import java.util.List;
import java.util.Map;

import com.jfshare.cart.util.Constant;
import com.jfshare.cart.util.NumberUtil;
import com.jfshare.cart.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MapRedisManager extends BaseRedisManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapRedisManager.class);

	/*public MapRedisManager(JedisPool jedisPool) {
		super(jedisPool);
	}*/
	
	public int getMapSize(String key) {
		if (StringUtil.empty(key))
			return 0;

		Jedis jedis = null;
		int ret = 0;

		try {
			jedis = this.getJedisPool().getResource();
			Long result = jedis.hlen(key);
			ret = result == null ? 0 : result.intValue();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return ret;
	}

	public int getMapTotal(String key) {
		if (StringUtil.empty(key))
			return 0;

		int ret = 0;
		Map<String, String> map = getMap(key);
		for (Map.Entry<String, String> item :map.entrySet()) {
			String value = item.getValue();
			String[] values = value.split(Constant.SEPARATOR);
			if (values == null || values.length < 3)
				continue;
			ret += NumberUtil.parseInteger(values[0]);
		}

		return ret;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> getMap(String key) {
		if (StringUtil.empty(key))
			return null;

		Jedis jedis = null;
		Map<String, String> ret = null;

		try {
			jedis = this.getJedisPool().getResource();
			ret = jedis.hgetAll(key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return ret;
	}
	
	/**
	 * 获取数据
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String get(String key, String field) {
		if (StringUtil.empty(key) || StringUtil.empty(field))
			return "";

		Jedis jedis = null;
		String ret = null;

		try {
			jedis = this.getJedisPool().getResource();
			ret = jedis.hget(key, field);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return ret;
	}

	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long setMap(String key, String field, String value) {
		if (StringUtil.empty(key) || StringUtil.empty(field))
			return null;

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = this.getJedisPool().getResource();
			result = jedis.hset(key, field, value);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}

		return result;
	}
	
	/**
	 * 添加数据
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public String setMap(String key, Map<String, String> map) {
		if (StringUtil.empty(key) || map == null || map.size() <= 0)
			return null;

		Jedis jedis = null;
		String result = null;
		try {
			jedis = this.getJedisPool().getResource();
			result = jedis.hmset(key, map);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}

		return result;
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public List<String> get(String key, String... fields) {
		if (StringUtil.empty(key) || fields == null)
			return null;

		Jedis jedis = null;
		List<String> ret = null;

		try {
			jedis = this.getJedisPool().getResource();
			ret = jedis.hmget(key, fields);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return ret;
	}
	
	public String getStockCount(String key, String field) {
		if (StringUtil.empty(key) || StringUtil.empty(field))
			return "";

		Jedis jedis = null;
		String ret = "";

		try {
			long doneTime = System.currentTimeMillis();
			jedis = this.getJedisPool().getResource();
			ret = jedis.hget(key, field);
			LOGGER.info(field + ",获取商品库存信息调用时间：" + (System.currentTimeMillis() - doneTime) + " ms!!");
			return StringUtil.empty(ret) ? "0" : ret;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return "";
	}
	
	/**
	 * 删除fields
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public Long del(String key, String... fields) {
		Long result = 0L;
		Jedis jedis = null;
		try {
			jedis = this.getJedisPool().getResource();
			result = jedis.hdel(key, fields);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}
		return result;
	}

	/**
	 * 清除数据
	 * 
	 * @param key
	 * @return
	 */
	public Long delete(String key) {
		if (StringUtil.empty(key))
			return null;

		Jedis jedis = null;
		Long result = null;
		try {
			jedis = this.getJedisPool().getResource();
			result = jedis.del(key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			this.getJedisPool().returnResource(jedis);
		}

		return result;
	}

	public void set(String key, String value) {
		if (StringUtil.empty(key) || StringUtil.empty(value))
			return;

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
	
	public void setex(String key, int expire, String value) {
		if (StringUtil.empty(key) || StringUtil.empty(value))
			return;

		Jedis jedis = null;
		try {
			jedis = this.getJedisPool().getResource();
			jedis.setex(key, expire, value);
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
