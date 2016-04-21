package com.jfshare.brand.dao.mysql;

import com.jfshare.brand.model.TbBrand;
import com.jfshare.finagle.thrift.brand.QueryParam;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IBrandDao {
    int queryCount(QueryParam param);

    List<TbBrand> queryByPage(QueryParam param);

    List<TbBrand> query();

    List<TbBrand> queryByIds(List<Integer> validIdList);
}
