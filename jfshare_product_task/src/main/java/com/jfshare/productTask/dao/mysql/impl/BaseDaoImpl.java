package com.jfshare.productTask.dao.mysql.impl;

import com.jfshare.productTask.dao.mysql.IBaseDao;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Lenovo on 2016/4/25.
 */
public class BaseDaoImpl<T, ID> extends SqlSessionDaoSupport implements IBaseDao<T, ID> {



    /**
     * class type of the given T
     */
    protected Class<T> clazz;

    private String mapperNameSpace;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl(){
        this.clazz = ((Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        mapperNameSpace = this.clazz.getName().replace("model", "mapper") + "Mapper";
    }

    @Override
    public int add(T obj) {
        return this.getSqlSession().insert(this.mapperNameSpace + ".insertSelective", obj);
    }

    @Override
    public int updateById(T obj) {
        return this.getSqlSession().update(this.mapperNameSpace + ".updateByPrimaryKeySelective", obj);
    }

    @Override
    public T getById(ID id) {
        Object obj = this.getSqlSession().selectOne(this.mapperNameSpace + ".selectByPrimaryKey", id);
        if(obj!=null){
            return (T) obj;
        }
        return null;
    }

    @Override
    public int deleteById(ID id) {
        return this.getSqlSession().delete(this.mapperNameSpace + ".deleteByPrimaryKey", id);
    }
}
