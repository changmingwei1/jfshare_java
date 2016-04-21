package com.jfshare.subject.dao;

import com.jfshare.finagle.thrift.brand.BrandInfo;

import java.util.List;

/**
 * @author sus
 */
public interface APIServerDAO {

    /**
     * 根据品牌id列表查询对应品牌详情
     * @param brandIds
     * @return
     */
	public List<BrandInfo> queryBrands(List<Integer> brandIds) throws Exception;

}
