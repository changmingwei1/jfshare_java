package com.jfshare.product.dao.redis;

import com.jfshare.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lenovo on 2015/11/21.
 */
@Repository
public class BaseRedis {
    private Logger logger = LoggerFactory.getLogger(BaseRedis.class);

    @Resource(name="jedisPoolRead")
    private JedisPool jedisPoolRead;

    @Resource(name="jedisPoolWrite")
    private JedisPool jedisPoolWrite;

    /**
     * 给定KEY的对象是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        //logger.info( ">>>> exists ---- is exists key ---- " + key);
        boolean re = true;
        Jedis jedis =null;
        try {
            jedis = jedisPoolRead.getResource();
            re = jedis.exists(key);
        } finally {
            jedisPoolRead.returnResource(jedis);
        }
        //logger.info( "<<<< exists ---- is exists key ---- result: " + re);
        return re;
    }

    /**
     * redis保存key-value数据
     * @param key
     * @param value
     * @return
     */
    public boolean putKV(String key ,String value) {
        //logger.info(">>>> putKV ----- key : {}----value : {}", key, value);
        boolean re = true;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            jedis.set(key, value);
            re = true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putKV ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * redis保存key-value数据
     * @param key
     * @param value
     * @param seconds 大于0表示为给定 key 设置生存时间，小于等于0表示未设置生存时间
     * @return
     */
    public boolean putKV(String key, String value, int seconds) {
        //logger.info(">>>> putKV ---- life time: " + seconds + " -------key : {} ---", key);
        boolean re = true;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            jedis.set(key, value);
            if(seconds > 0) {
                jedis.expire(key, seconds);
            }
            re = true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putKV ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * redis 将 key 的值设为 value ，当且仅当 key 不存在。若给定的 key 已经存在，则不做任何动作。
     * @param key
     * @param value
     * @return
     */
    public boolean putNxKV(String key ,String value) {
        //logger.info(">>>> putNxKV ----- key : " + key);
        boolean re = true;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            long set = jedis.setnx( key, value);
            re = set > 0 ? true : false;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putNxKV ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 返回redis 中 key 所关联的字符串值。
     * @param key
     * @return
     */
    public String getKV(String key) {
        //logger.info(">>>> getKV ----- key : " + key);
        String re = null;
        Jedis jedis =null;
        try {
            jedis = jedisPoolRead.getResource();
            re = jedis.get(key);
        } finally {
            jedisPoolRead.returnResource(jedis);
        }
        //logger.info("<<<< getKV ----- key : {}----result : {}", key, "success!");
        return re;
    }

    /**
     * 删除redis中给定的 key 。不存在的 key 会被忽略。
     * @param key
     * @return 删除条数
     */
    public String removeKV(String key) {
        //logger.info(">>>> removeKV ----- key : " + key);
        String re = null;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            long s = jedis.del(key);
            re = s + "";
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< removeKV ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 删除redis中给定的一个或者多个key 。不存在的 key 会被忽略。
     * @param keys
     * @return 删除条数
     */
    public String removeKV(String... keys) {
        //logger.info(">>>> removeKV ----- keys : " + keys);
        String re = null;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            long s = jedis.del(keys);
            re = s + "";
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< removeKV ----- keys : {}----result : {}", keys, re);
        return re;
    }


    /**
     * 将一个或多个值 value 插入到redis列表 key 的表头
     * @param key
     * @param values
     * @return
     */
    public boolean putList(String key, String... values) {
        //logger.info(">>>> putList ----- key : {} ---- values : {}", key, JsonMapper.toJson(values));
        boolean re = true;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            for(String data : values){
                jedis.lpush(key , data);
            }
            re = true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putList ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 返回redis列表 key 中的第一个元素。不移除。
     * @param key
     * @return
     */
    public String getList(String key) {
        //logger.info(">>>> getList ----- get first value ----- key : {} ", key);
        String re = null;
        Jedis jedis =null;
        try {
            jedis = jedisPoolRead.getResource();
            String value = jedis.lindex(key, 0);
            re = value;
        } finally {
            jedisPoolRead.returnResource(jedis);
        }
        //logger.info("<<<< getList ----- get first value ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 移除并返回redis列表 key 中的第一个元素
     * @param key
     * @return
     */
    public String popList(String key) {
        //logger.info(">>>> popList ----- pop first value ----- key : {} ", key);
        String re = null;
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            String value = jedis.lpop(key);
            re = value;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< popList ----- pop first value ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 将redis哈希表 key 中的域 field 的值设为 value 。
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean putMap(String key, String field, String value) {
        //logger.info(">>>> putMap ----- put value to key's field ----- key : " + key + " --- field : " + field + " --- value : " + value);
        boolean re = true;
        Jedis jedis = null;
        Long newCount = (long) 0;
        try {
            jedis = jedisPoolWrite.getResource();
            newCount = jedis.hset(key, field, value);
            re = newCount== 0 ? false : true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putMap ----- put value to key's field ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 会覆盖哈希表中已存在的域。
     * @param key
     * @param map
     * @return
     */
    public boolean putMap(String key,Map map) {
        //logger.info(">>>> putMap ----- put whole map ----- key : {} --- value : {}", key, JsonMapper.toJson(map));
        Jedis jedis = null;
        boolean re = true;
        try {
            String res;
            jedis = jedisPoolWrite.getResource();
            res = jedis.hmset(key, map);
            re = "OK".equalsIgnoreCase(res) ? false : true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putMap ----- put whole map ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中。 会覆盖哈希表中已存在的域。
     * @param key
     * @param map
     * @param seconds 大于0表示为给定 key 设置生存时间，小于等于0表示未设置生存时间
     * @return
     */
    public boolean putMap(String key, Map map, int seconds) {
        //logger.info(">>>> putMap ----- put whole map with lifetime ----- key : {} --- value : {} --- lifetime : " + seconds , key, JsonMapper.toJson(map));
        Jedis jedis = null;
        boolean re = true;
        try {
            String res = null;
            jedis = jedisPoolWrite.getResource();
            res = jedis.hmset(key, map);
            if(seconds > 0) {
                jedis.expire(key, seconds);
            }
            re = res == "OK" ? false : true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
        //logger.info("<<<< putMap ----- put whole map ----- key : {}----result : {}", key, re);
        return re;
    }

    /**
     * 获取redis哈希表key中的域 field 的值。
     * @param key
     * @param field
     * @return
     */
    public String getMap(String key,String field) {
        //logger.info(">>>> getMap ----- get key's field value ----- key : {} --- field : {}", key, field);
        Jedis jedis = null;
        String re = null;
        try {
            jedis = jedisPoolRead.getResource();
            re = jedis.hget(key, field);
        } finally {
            jedisPoolRead.returnResource(jedis);
        }
        //logger.info("<<<< getMap ----- get key's field value ----- key : {}----result : {}", key, "success!");
        return re;
    }

    /**
     * 获取redis哈希中多个field的value
     * @param key
     * @param fields
     * @return
     */
    public List<String> getMMap(String key, String... fields) {
        //logger.info(">>>> getMMap ----- get key's many field values ----- key : {} --- fields : {}", key, JsonMapper.toJson(fields));
        List<String> values = new ArrayList<String>();
        Jedis jedis = null;
        try {
            jedis = jedisPoolRead.getResource();
            values = jedis.hmget(key, fields);
        } finally {
            jedisPoolRead.returnResource(jedis);
        }
        //logger.info("<<<< getMMap ----- get key's many field values ----- key : {} --- result : {}", key, "success!");
        return values;
    }

    /**
     * 获取redis中的整个哈希
     * @param key
     * @return
     */
    public Map<String, String> getMapAll(String key) {
        //logger.info(">>>> getMapAll ----- get whole map ----- key : {} ", key);
        Jedis jedis = null;
        Map<String, String> map = null;
        try {
            jedis = jedisPoolRead.getResource();
            map = jedis.hgetAll(key);
            //logger.info("<<<< getMapAll ----- get whole map ----- key : {} --- result : {}", key, "success!");
            return map.size() == 0 ? null : map;
        } finally {
            jedisPoolRead.returnResource(jedis);
        }

    }

    /**
     * 为哈希表 key 中的域 field 的值加上增量 value 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。
     * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long incredMap(String key,String field,int value ) {
        //logger.info(">>>> incredMap ----- increase map field's value ----- key : " + key + " --- field : " + field + " --- value : " + value);
        Jedis jedis =null;
        Long newCount = Long.parseLong("-1");
        try {
            jedis = jedisPoolWrite.getResource();
            newCount = jedis.hincrBy(key,field, value);
            //logger.info("<<<< incredMap ----- increase map field's value ----- key : {} --- field : {} --- result : " + newCount, key, field);
            return newCount;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }

    }

    /**
     * 删除redis中key的field。
     * @param key
     * @param field
     * @return
     */
    public boolean removeMap(String key,String field) {
        //logger.info(">>>> removeMap ----- remove map's field ----- key : {} --- field : {}", key, field);
        Jedis jedis = null;
        Long newCount = (long) 0;
        boolean re = true;
        try {
            jedis = jedisPoolWrite.getResource();
            newCount = jedis.hdel(key, field);
            re = newCount == 0 ? false : true;
            //logger.info("<<<< removeMap ----- remove map's field ----- key : {} --- field : {} --- result : " + re, key, field);
            return re;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }

    /**
     * 给redis中的多个哈希设置生存时间
     * @param keys
     * @param fields
     * @param value
     * @param seconds
     * @return
     */
    public boolean putMapExiperse(String[] keys,String[] fields,String[] value, int[] seconds) {
        Jedis jedis = null;

        try {
            jedis = jedisPoolWrite.getResource();
            Pipeline plin = jedis.pipelined() ;
            for(int i = 0 ;i < keys.length;i++ ){
                plin.hset(keys[i], fields[i], value[i]);
                plin.expire(keys[i], seconds[i]);
            }
            List<Object> r = plin.syncAndReturnAll();
            return r == null ? false : true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }

    /**
     * 获取有序集合中所有的数据
     * @param key
     * @return
     */
    public Set<String> getSortSet(String key) {
        //logger.info(">>>> getSortSet ----- get sort set ----- key : {}", key);
        Jedis jedis =null;
        try {
            jedis = jedisPoolRead.getResource();
            Set<String> res = jedis.zrangeByScore(key, "-inf", "+inf");
            return res.size() == 0 ? null : res;
        } finally {
            jedisPoolRead.returnResource(jedis);
        }
    }

    /**
     * 向有序集合中放入数据，优先级按照时间排序
     * @param key
     * @param value
     * @return
     */
    public boolean putSortSet(String key,String value) {
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            Long result = jedis.zadd(key, System.currentTimeMillis(), value);
            return  true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }

    /**
     * 向有序集合中放入数据，自定义优先级
     * @param key
     * @param value
     * @param proi 优先级
     * @return
     */
    public boolean putSortSet(String key,String value,long proi) {
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            Long result = jedis.zadd(key, proi, value);
            return  true;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * @param key
     * @param values
     * @return
     */
    public long removeSortSet(String key,String... values) {
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            Long res = jedis.zrem(key, values)   ;
            return  res;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }

    /**
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
     * 本操作的值限制在 64 位(bit)有符号数字表示之内。
     * @param key
     * @return
     */
    public long incr(String key) {
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            Long res = jedis.incr(key)   ;
            return  res;
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }

    /**
     * 设置key的生存时间
     * @param key
     * @param seconds
     * @return
     */
    public boolean setExiperse(String key,int seconds) {
        //logger.info(">>>> setExiperse ----- set key's lifetime ----- key : {}", key);
        Jedis jedis =null;
        try {
            jedis = jedisPoolWrite.getResource();
            long r =  jedis.expire(key, seconds);
            if(r>0){
                //logger.info( "<<<< setExiperse---- set key's lifetime ----- key : {} --- seconds : {}", key, seconds);
                return true;
            }else{
                return false;
            }
        } finally {
            jedisPoolWrite.returnResource(jedis);
        }
    }
}