package com.jfshare.subject.dao.impl.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

import java.util.Map;

@Repository
public class JedisBaseDao {

    Logger logger = LoggerFactory.getLogger(JedisBaseDao.class);
    private static int TIME_LIMIT = 100;

    //@Resource(name="shardedJedisPool")
    //private ShardedJedisPool shardedJedisPool;

    @Resource(name="jedisPoolWrite")
    private JedisPool jedisPoolWrite;
    
//    @Resource(name="jedisCluster")
//    private JedisCluster jedisCluster;
    
    /**
     * 给定KEY的对象是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.exists(key);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * redis保存key-value数据
     * @param key
     * @param value
     * @return
     */
    public String putKV(String key ,String value) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.set(key,value);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * redis 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则不做任何动作。
     * @param key
     * @param value
     * @return
     */
    public boolean putNxKV(String key ,String value) {
        boolean re = true;
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            long set = jedis.setnx( key, value);
            re = set > 0 ? true : false;
            return re;
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }

    }

    /**
     * 返回redis 中 key 所关联的字符串值。
     * @param key
     * @return
     */
    public String getKV(String key) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.get(key);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 返回redis 中 key 所关联的字符串值。
     * @param key
     * @return
     */
    public Long incrKV(String key) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.incr(key);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 删除redis中给定的 key 。不存在的 key 会被忽略。
     * @param key
     * @return 删除条数
     */
    public String removeKV(String key) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.del(key)+"";
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 将redis哈希表 key 中的域 field 的值设为 value 。
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean putMap(String key, String field, String value) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.hset(key, field, value) > 0;
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 会覆盖哈希表中已存在的域。
     * @param key
     * @param map
     * @return
     */
    public void putMap(String key,Map map) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            jedis.hmset(key, map);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 获取redis哈希表key中的域 field 的值。
     * @param key
     * @param field
     * @return
     */
    public String getMap(String key,String field) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.hget(key, field);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }


    /**
     * 获取redis中的整个哈希
     * @param key
     * @return
     */
    public Map<String, String> getMapAll(String key) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.hgetAll(key);
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 删除redis中key的field。
     * @param key
     * @param field
     * @return
     */
    public boolean removeMap(String key,String field) {
        //ShardedJedis jedis = null;
    	Jedis jedis = null;
        try {
            jedis =  jedisPoolWrite.getResource();
            return jedis.hdel(key, field) > 0;
        } finally {
            if(jedis!=null){
                jedisPoolWrite.returnResource(jedis);
            }
        }
    }

    /**
     * 设置key的生存时间
     * @param key
     * @param seconds
     * @return
     */
//    public boolean setExiperse(String key,int seconds) {
//        return jedisCluster.expire(key, seconds) > 0;
//
//    }
}
