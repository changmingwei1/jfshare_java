package com.jfshare.cart.model.mapper.manual;

import java.util.List;
import java.util.Map;

public interface BaseMapper {

	/**
	 * 新增记录
	 * 
	 * @param t
	 */
	public <T> int insert(T t);

	/**
	 * 更新记录
	 * 
	 * @param t
	 */
	public <T> void update(T t);

	/**
	 * 根据ID查询记录
	 * 
	 * @param id
	 * @return
	 */
	public <T> T queryById(int id);

	/**
	 * 根据ID删除记录
	 * 
	 * @param id
	 */
	public <T> void deleteById(int id);

	/**
	 * 查询记录总数
	 * 
	 * @return
	 */
	public int count(Map<String, Object> map);

	/**
	 * 分页查询
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public <T> List<T> queryForPage(Map<String, Object> map);
}