package com.jfshare.brand.service;

import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.finagle.thrift.brand.QueryParam;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IBrandSvc {
    int queryCount(QueryParam param);

    List<BrandInfo> queryByPage(QueryParam param);

    List<BrandInfo> query();

    List<BrandInfo> queryByIds(List<Integer> validIdList);
}
