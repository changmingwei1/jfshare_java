package com.jfshare.subject.dao.impl.mybatis;

import com.jfshare.subject.dao.BaseDAO;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.ParameterizedType;

public class BaseDAOImpl<T, K> extends SqlSessionDaoSupport implements BaseDAO<T, K> {

	/**
	 * class type of the given T
	 */
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDAOImpl(){
		this.clazz = ((Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	@Override
	public Integer add(T obj) {
		return this.getSqlSession().insert(clazz.getName() + ".insert", obj);
	}

	@Override
	public void update(T obj) {
		this.getSqlSession().update(clazz.getName() + ".update", obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(K id) {
		Object obj = this.getSqlSession().selectOne(clazz.getName() + ".selectById", id);
		if(obj!=null){
			return (T) obj;
		}
		return null;
	}

	@Override
	public void delete(K id) {
		this.getSqlSession().delete(clazz.getName() + ".delete", id);
	}

}
