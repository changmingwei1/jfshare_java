package com.jfshare.brand.dao.redis;

import java.util.List;

import com.jfshare.brand.model.TbBrand;
import com.jfshare.brand.model.TbSubjectBrandKey;
import com.jfshare.finagle.thrift.brand.QueryParam;

public interface IBrandRedis {
	void addAllBrand(List<TbBrand> brands);

    List<TbBrand> queryAll();

    List<TbBrand> queryByIds(List<Integer> validIdList);
    
	TbBrand getBrand(int id);
	
	List<TbBrand> queryByPage(QueryParam param);
	
	int addBrand(TbBrand brand);
    
    int deleteBrand(int id);
    
    int updateBrand(TbBrand brand);
}
