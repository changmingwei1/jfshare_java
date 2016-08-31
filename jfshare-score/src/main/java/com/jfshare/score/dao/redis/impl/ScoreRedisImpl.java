package com.jfshare.score.dao.redis.impl;

import java.util.List;

import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.jfshare.score.dao.redis.IScoreRedis;
import com.jfshare.score.util.Constants;
import com.jfshare.score.util.ScoreUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

@Repository
public class ScoreRedisImpl implements IScoreRedis {
	private Logger logger = LoggerFactory.getLogger(ScoreRedisImpl.class);

	@Autowired
	@Qualifier("jedisPool")
    //private JedisPool jedisPool;
	private JedisSentinelPool jedisPool;
	
	@Override
	public String getCashAmountByUserid(String key) {
		Jedis jedis = null;
		String  cachAmount = null;
		try {
			jedis = jedisPool.getResource();

			if (jedis != null){
				String redisKey = Constants.REDIS_USERID_CASH+key;
				cachAmount = jedis.get(redisKey);
			}else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}

		return cachAmount;
	}

	@Override
	public String setCashAmountByUserid(String userId, String value) {
			Jedis jedis = null;
			String ret = null;
			
			try {
				jedis = jedisPool.getResource();

				if (jedis != null){
					String redisKey = Constants.REDIS_USERID_CASH+userId;
					ret = jedis.set(redisKey, value);
					int sec = ScoreUtil.getSeconds();
				    jedis.expire(redisKey, sec);
				}else
					logger.info("redo redis 连接失败");
			} catch (Exception e) {
				logger.error("" + e.getMessage());
			} finally {
				jedisPool.returnResource(jedis);
			}

			return ret;

		}

	@Override
	public String setCashAmountByMobile(String mobile, String value) {
		Jedis jedis = null;
		String ret = null;
		
		try {
			jedis = jedisPool.getResource();

			if (jedis != null){
				String redisKey = Constants.REDIS_MOBILE_CASH + mobile;
				ret = jedis.set(redisKey, value);
				int sec = ScoreUtil.getSeconds();
			    jedis.expire(redisKey, sec);
			}else
				logger.info("redo redis 连接失败");
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}

		return ret;
	}

	@Override
	public String getCashAmountByMobile(String mobile) {
		Jedis jedis = null;
		String  cachAmount = null;
		try {
			jedis = jedisPool.getResource();

			if (jedis != null){
				String redisKey = Constants.REDIS_MOBILE_CASH+mobile;
				cachAmount = jedis.get(redisKey);
			}else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}

		return cachAmount;
	}
	
	//测试获取1000次redis数据性能
	@Override
	public int getRedisbyKey(String key,int count) {
		Jedis jedis = null;
		String  reponse = null;
		int size = -1;
		try {
			jedis = jedisPool.getResource();

			if (jedis != null){
				redis.clients.jedis.Transaction transaction = jedis.multi();
				for(int i=0;i<count;i++){
					transaction.get(key);
				}
				List<Object> reponseList = transaction.exec();
				size = reponseList.size();
			}else
				logger.info("redo redis 连接失败");

		} catch (Exception e) {
			logger.error("" + e.getMessage());
		} finally {
			jedisPool.returnResource(jedis);
		}
		
		return size;
	}

}
