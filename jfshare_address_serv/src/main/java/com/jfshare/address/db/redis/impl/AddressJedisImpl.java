package com.jfshare.address.db.redis.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfshare.address.db.redis.IAddressJedis;
import com.jfshare.address.db.redis.RedisConst;
import com.jfshare.utils.JsonMapper;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.jfshare.finagle.thrift.address.AddressInfo;

@Repository
public class AddressJedisImpl extends SqlSessionDaoSupport implements IAddressJedis {

	private Logger logger = LoggerFactory.getLogger(AddressJedisImpl.class);

	@Autowired
	@Qualifier("jedisPool")
	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	@Autowired
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

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

	/**
	 * 根据用户ID获取收件人地址列表
	 * @param userId
	 * @return
	 */
	@Override
	public List<AddressInfo> getAddressByUserId(String userId) {
		List<AddressInfo> addressInfoList = new ArrayList<AddressInfo>();
		Jedis jedis = null;
		Map<String, String> map = null;
		try {
			jedis = jedisPool.getResource();
			if (jedis != null) {
				map = jedis.hgetAll(RedisConst._RECEIVER_ADDRESS + userId);
				if (map != null) {
					if (map.size() > 0) {
						Iterator<String> it = map.keySet().iterator();
						String key = "";
						AddressInfo addressInfo;
						while (it.hasNext()) {
							key = it.next();
							addressInfo = JsonMapper.toObject(map.get(key), AddressInfo.class);
							addressInfoList.add(addressInfo);
						}
					}
				}
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return addressInfoList;
	}
	/**
	 * 获取收件人地址
	 * @param id
	 * @param userId
	 * @return
	 */
	@Override
	public AddressInfo getAddress(String id, String userId) {
		Jedis jedis = null;
		String ret = null;
		AddressInfo addressInfo = new AddressInfo();
		try {

			jedis = jedisPool.getResource();

			if (jedis != null) {
				ret = jedis.hget(RedisConst._RECEIVER_ADDRESS + userId, id);
				addressInfo = JsonMapper.toObject(ret, AddressInfo.class);
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return addressInfo;

	}	
	/**
	 * 保存有序集合
	 * @param info
	 * @return
	 */
	@Override
	public long saveSortAddress(String key,int score, String info) {
		Jedis jedis = null;
		long ret = 0;
		try {

			jedis = jedisPool.getResource();

			if (jedis != null) {
				ret = jedis.zadd(key, score, info);
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return ret;

	}

	@Override
	public List<AddressInfo> getAddressInfoByUserId(String userId) {
		Jedis jedis = null;
		List<AddressInfo> addressInfoList = new ArrayList<AddressInfo>();
		try {

			jedis = jedisPool.getResource();

			if (jedis != null) {
				Set<String> set = jedis.zrange(RedisConst._RECEIVER_ADDRESS + userId, 0, -1);
				if (set != null) {
					if (set.size() > 0) {
						for (String string : set) {
							AddressInfo addressInfo = new AddressInfo();
							addressInfo = JsonMapper.toObject(string, AddressInfo.class);
							addressInfoList.add(addressInfo);
						}
					}
				}
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return addressInfoList;
	}

	@Override
	public long zremAddress(String key,String info) {
		Jedis jedis = null;
		long ret = 0;
		try {

			jedis = jedisPool.getResource();

			if (jedis != null) {
				ret = jedis.zrem(key, info);
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		return ret;

	}
}
