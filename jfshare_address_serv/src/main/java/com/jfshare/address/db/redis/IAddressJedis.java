package com.jfshare.address.db.redis;

import java.util.List;
import java.util.Map;
import com.jfshare.finagle.thrift.address.AddressInfo;

public interface IAddressJedis {
	public boolean setExpire(String key, int seconds);
    public String set(String key, String value);
    public String get(String key);	
	public String getMap(String key, String field);
	public Long setMap(String key, String field, String value);
	public boolean isExists(String key);
	public boolean deleteKeyField(String key, String field);
	public boolean deleteKeys(String... keys);
	public boolean isHExists(String key, String field);
	public Map<String, String> getHAll(String key);
	public long deleteLikeKey(String key);

	/**
	 * 根据用户ID获取收件人地址列表
	 * @param userId
	 * @return
	 */
	public List<AddressInfo> getAddressByUserId(String userId);
	/**
	 * 获取收件人地址
	 * @param id
	 * @param userId
	 * @return
	 */
	public AddressInfo getAddress(String id, String userId);
	public long saveSortAddress(String key,int score, String info);
	public List<AddressInfo> getAddressInfoByUserId(String userId);
	public long zremAddress(String key,String info);
}
