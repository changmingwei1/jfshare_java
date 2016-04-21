package com.jfshare.subject.bean.support;

import java.io.Serializable;

/**
 * 序列化接口
 * @author zhuxinze
 *
 */
public interface Serialization<T> extends Serializable{
	
	/**
	 * 序列化
	 * @return
	 */
	public T serialize();
	
	/**
	 * 反序列化
	 * @return
	 */
	public void deSerialize(T obj);
	
	/**
	 * 过期时间，单位：秒
	 * -1:不进行缓存，0：永不失效，其它：缓存时间
	 * @return
	 */
	public int expireTime();
	
	/**
	 * 缓存需要的kEY值
	 * @return
	 */
	public String getCacheKey();
}
