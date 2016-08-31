package com.jfshare.message.dao.redis.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.message.dao.redis.IMessageRedis;
import com.jfshare.message.dao.redis.RedisConst;
import com.jfshare.message.model.TbMessage;
import com.jfshare.message.model.TbMessageExample;
import com.jfshare.message.model.TbMessageExample.Criteria;
import com.jfshare.message.util.MessageUtil;
import com.jfshare.utils.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

@Repository
public class MessageRedisImpl implements IMessageRedis {
	private Logger logger = LoggerFactory.getLogger(MessageRedisImpl.class);

//	@Autowired
//	@Qualifier("jedisPool")
//	private JedisPool jedisPool;

	@Autowired
	@Qualifier("redisSentinel")
	private JedisSentinelPool jedisPool;
	
	public JedisSentinelPool getJedisPool() {
		return jedisPool;
	}
	
//	public JedisPool getJedisPool() {
//		return jedisPool;
//	}
//	
	
	@Override
	public boolean setExpire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long r = jedis.expire(key, seconds);
			if (r > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public boolean isExists(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public boolean isHExists(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.hexists(key, field);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = null;
		String ret = null;
		try {
			
			logger.info("set========jedisPool对象类型=============:"+jedisPool);
			jedis = jedisPool.getResource();

			if (jedis != null)
				ret = jedis.set(key, value);
			else
				logger.info("redo redis 连接失败");
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}

		return ret;

	}

	@Override
	public String get(String key) {
		Jedis jedis = null;
		String ret = null;
		try {
			
			logger.info("get========jedisPool对象类型=============:"+jedisPool);
			jedis = jedisPool.getResource();

			if (jedis != null)
				ret = jedis.get(key);
			else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}

		return ret;
	}

	@Override
	public String getMap(String key, String field) {
		Jedis jedis = null;
		String ret = null;
		try {

			jedis = jedisPool.getResource();

			if (jedis != null)
				ret = jedis.hget(key, field);
			else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}

		return ret;

	}

	@Override
	public Long setMap(String key, String field, String value) {
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPool.getResource();

			if (jedis != null)
				ret = jedis.hset(key, field, value);
			else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return ret;
	}

	@Override
	public boolean deleteKeyField(String key, String field) {
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis != null) {
				ret = jedis.hdel(key, field);// 返回成功删除的个数
				if (ret > 0)
					return true;
			} else {
				logger.info("redo redis 连接失败");
				return false;
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public boolean deleteKeys(String... keys) {
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis != null) {
				ret = jedis.del(keys);// 返回成功删除的个数
				if (ret > 0)
					return true;
			} else {
				logger.info("redo redis 连接失败");
				return false;
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
		return false;
	}

	@Override
	public Map<String, String> getHAll(String key) {
		Jedis jedis = null;
		Map<String, String> map = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis != null) {
				map = jedis.hgetAll(key);
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return map;
	}

	@Override
	public long deleteLikeKey(String key) {
		Jedis jedis = null;
		long count = 0;
		try {
			jedis = jedisPool.getResource();
			if (jedis != null) {
				Set<String> keys = jedis.keys(key + "*");
				count = jedis.del(keys.toArray(new String[keys.size()]));
			} else {
				logger.info("redo redis 连接失败");
			}
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return count;
	}
	
	@Override
	public TbMessage getMessageById(int id) {
		return null;
	}

	@Override
	public List<TbMessage> getMessageByTitle(String title) {
		return null;
	}

	@Override
	public List<TbMessage> getMessageByStatus(int status) {
		return null;
	}

	@Override
	public List<TbMessage> getAllMessage() {
		return null;
	}

	@Override
	public int updateMessage(TbMessage tbMessage) {
		return 0;
	}

	@Override
	public int deleteMessage(SystemMessage tbMessage) {
		return 0;
	}

	@Override
	public int addMessage(TbMessage tbMessage) {
		
		return 0;
	}

	@Override
	public List<TbMessage> getMessage(SystemMessage tbMessage) {
		return null;
	}
}
