package com.jfshare.common.db.redis.impl;

import com.jfshare.common.db.redis.ICommonJedis;
import com.jfshare.common.db.redis.RedisConst;
import com.jfshare.finagle.thrift.common.AreaInfo;
import com.jfshare.utils.JsonMapper;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CommonJedisImpl extends SqlSessionDaoSupport implements ICommonJedis {

	private Logger logger = LoggerFactory.getLogger(CommonJedisImpl.class);

	@Resource(name = "jedisPoolWrite")
	private JedisPool jedisPoolWrite;

	public JedisPool getJedisPool() {
		return jedisPoolWrite;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPoolWrite = jedisPool;
	}

	@Override
	public boolean setExpire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPoolWrite.getResource();
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
			jedisPoolWrite.returnResource(jedis);
		}
	}

	@Override
	public boolean isExists(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPoolWrite.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
	}
	@Override
	public boolean isHExists(String key,String field) {
		Jedis jedis = null;
		try {
			jedis = jedisPoolWrite.getResource();
			return jedis.hexists(key,field);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return false;
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = null;
		String ret = null;
		try {
			jedis = jedisPoolWrite.getResource();

			if (jedis != null)
				ret = jedis.set(key, value);
			else
				logger.info("redo redis 连接失败");
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}

		return ret;

	}

	@Override
	public String get(String key) {
		Jedis jedis = null;
		String ret = null;
		try {
			jedis = jedisPoolWrite.getResource();

			if (jedis != null)
				ret = jedis.get(key);
			else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}

		return ret;
	}

	@Override
	public Map getMap(String key) {
		Jedis jedis = null;
		Map<String, String> ret = null;
		try {
			jedis = jedisPoolWrite.getResource();

			if (jedis != null)
				ret = jedis.hgetAll(key);
			else
				logger.info("redo redis 连接失败");
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}

		return ret;
	}

	@Override
	public String getMap(String key, String field) {
		Jedis jedis = null;
		String ret = null;
		try {

			jedis = jedisPoolWrite.getResource();

			if (jedis != null)
				ret = jedis.hget(key, field);
			else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}

		return ret;

	}

	@Override
	public Long setMap(String key, String field, String value) {
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPoolWrite.getResource();

			if (jedis != null)
				ret = jedis.hset(key, field, value);
			else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
		return ret;
	}

	@Override
	public boolean deleteKeys(String... keys) {
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
				ret = jedis.del(keys);//返回成功删除的个�?
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
			jedisPoolWrite.returnResource(jedis);
		}
		return false;
	}
	
	@Override
	public boolean deleteKeyField(String key, String field) {
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
				ret = jedis.hdel(key, field);//返回成功删除的个�?
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
			jedisPoolWrite.returnResource(jedis);
		}
		return false;
	}
	
	@Override
	public Map<String, String> getHAll(String key) {
		Jedis jedis = null;
		Map<String, String> map = null;
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
				map = jedis.hgetAll(key);
			} else {
				logger.info("redo redis 连接失败");
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
		return map;
	}
	
	@Override
	public boolean rpush(String key, String value){
		Jedis jedis = null;
		Long ret = null;
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
				ret = jedis.rpush(key, value);
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
			jedisPoolWrite.returnResource(jedis);
		}
		return false;
		
	}

	@Override
	public List<String> getList(String key){
		Jedis jedis = null;
		List<String> list = new ArrayList<String>();
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
			    list = jedis.lrange(key, 0, -1);
			} else {
				logger.info("redo redis 连接失败");
				return list;
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return list;
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
		return list;
		
	}

	/**
	 * 获取省列�?
	 * @return
	 */
	@Override
	public List<AreaInfo> getProvinceList(){
		Jedis jedis = null;
		List<AreaInfo> listAreaInfo = new ArrayList<AreaInfo>();
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
			    List<String>list = jedis.lrange(RedisConst._ADD_COUNTRY_CN_MORE, 0, -1);
			    if(list != null){
				    for (String string : list) {
				    	AreaInfo areaInfo = (AreaInfo) JsonMapper.toObject(string, AreaInfo.class);
				    	listAreaInfo.add(areaInfo);
					}
			    }
			} else {
				logger.info("redo redis 连接失败");
				return listAreaInfo;
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return listAreaInfo;
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
		return listAreaInfo;
	}
	/**
	 * 获取市列�?
	 * @return
	 */
	@Override
	public List<AreaInfo> getCityList(String provinceId){
		Jedis jedis = null;
		List<AreaInfo> listAreaInfo = new ArrayList<AreaInfo>();
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
			    List<String>list = jedis.lrange(RedisConst._ADD_PROVINCE_MORE+provinceId, 0, -1);
			    if(list != null){
				    for (String string : list) {
				    	AreaInfo areaInfo = (AreaInfo)JsonMapper.toObject(string, AreaInfo.class);
				    	listAreaInfo.add(areaInfo);
					}
			    }
			} else {
				logger.info("redo redis 连接失败");
				return listAreaInfo;
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return listAreaInfo;
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
		return listAreaInfo;
	}
	/**
	 * 获取区县列表
	 * @return
	 */
	@Override
	public List<AreaInfo> getCountyList(String cityId){
		Jedis jedis = null;
		List<AreaInfo> listAreaInfo = new ArrayList<AreaInfo>();
		try {
			jedis = jedisPoolWrite.getResource();
			if (jedis != null) {
			    List<String>list = jedis.lrange(RedisConst._ADD_CITY_MORE+cityId, 0, -1);
			    if(list != null){
				    for (String string : list) {
				    	AreaInfo areaInfo = (AreaInfo)JsonMapper.toObject(string, AreaInfo.class);
				    	listAreaInfo.add(areaInfo);
					}
			    }
			} else {
				logger.info("redo redis 连接失败");
				return listAreaInfo;
			}

		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return listAreaInfo;
		} finally {
			jedisPoolWrite.returnResource(jedis);
		}
		return listAreaInfo;
	}

	@Override
    public Jedis getJedisPoolResource()
    {
    	try {
    		return jedisPoolWrite.getResource();
    	} catch (Exception e) {
    		logger.error("" + e.getMessage());
    		return null;
    	}
    }

	@Override
	public Long deleteKeys(Transaction transaction, String... keys) {
		if (transaction == null)
			return null;

		try {
			transaction.del(keys);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return (long) -1;
		}
		return (long) 1;
	}

	@Override
	public Long rpush(Transaction transaction, String key, String value) {
		if (transaction == null)
			return null;

		try {
			transaction.rpush(key, value);

		} catch (Exception e) {
			logger.error("" + e.getMessage());

			return (long) -1;
		}
		return (long) 1;
	}

	@Override
	public Long set(Transaction transaction, String key, String value) {
		if (transaction == null)
			return null;

		try {
			transaction.set(key, value);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return (long) -1;
		}
		return (long) 1;

	}
}
