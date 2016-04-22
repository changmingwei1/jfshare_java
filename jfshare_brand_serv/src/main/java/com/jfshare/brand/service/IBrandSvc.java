package com.jfshare.brand.service;

import java.util.List;

import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.finagle.thrift.brand.QueryParam;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IBrandSvc {
    int queryCount(QueryParam param);

    List<BrandInfo> queryByPage(QueryParam param);

    List<BrandInfo> query();

    List<BrandInfo> queryByIds(List<Integer> validIdList);
    
	public int addBrand(BrandInfo brand);

	public int deleteBrand(int id);

	public int updateBrand(BrandInfo brand);

	public List<BrandInfo> queryBySubject(int id);
}
