package com.jfshare.product.dao;

/**
 * DAO基础类
 * @author zhuxinze
 *
 * @param <T>
 * @param <K>
 */
public interface BaseDAO<T,K> {

	/**
	 * 添加
	 * @param obj
	 * @return
	 */
	public K add(T obj);
	
	/**
	 * 修改
	 * @param obj
	 */
	public void update(T obj);
	
	/**
	 * 根据id查询
	 * @param id
	 */
	public T getById(K id);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(K id);
}
