package com.jfshare.common.db.redis;

import java.util.List;
import java.util.Map;

import com.jfshare.finagle.thrift.common.AreaInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public interface ICommonJedis {
	public boolean setExpire(String key, int seconds);
    public String set(String key, String value);
    public String get(String key);	
	public Map getMap(String key);
	public String getMap(String key, String field);
	public Long setMap(String key, String field, String value);
	public boolean isExists(String key);
	public boolean deleteKeys(String... keys);
	public boolean deleteKeyField(String key, String field);
	public boolean isHExists(String key, String field);
	public Map<String, String> getHAll(String key);
	public boolean rpush(String key, String value);
	public List<String> getList(String key);

	/**
	 * 获取省列表
	 * @return
	 */
	public List<AreaInfo> getProvinceList();
	/**
	 * 获取市列表
	 * @return
	 */
	public List<AreaInfo> getCityList(String provinceId);
	/**
	 * 获取区县列表
	 * @return
	 */
	public List<AreaInfo> getCountyList(String cityId);
    public Jedis getJedisPoolResource();
	public Long deleteKeys(Transaction transaction,String... keys);
	public Long rpush(Transaction transaction,String key, String value);
	public Long set(Transaction transaction,String key, String value);
}
