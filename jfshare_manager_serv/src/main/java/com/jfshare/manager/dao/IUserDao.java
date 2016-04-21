package com.jfshare.manager.dao;

import com.jfshare.finagle.thrift.manager.Commissioner;
import com.jfshare.manager.model.TbCommissioner;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IUserDao {
    int loginNameIsExist(String loginName);

    int insert(TbCommissioner tbCommissioner);

    int update(TbCommissioner tbCommissioner);

    TbCommissioner getCommissioner(int csId);

    List<TbCommissioner> checkLogin(Commissioner cs);
}
