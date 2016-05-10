package com.jfshare.baseTemplate.dao.mysql;

/**
 * Created by Lenovo on 2016/4/26.
 */
public interface IBaseDao<T, ID> {

    int add(T obj);

    int updateById(T obj);

    T getById(ID id);

    int deleteById(ID id);
}
