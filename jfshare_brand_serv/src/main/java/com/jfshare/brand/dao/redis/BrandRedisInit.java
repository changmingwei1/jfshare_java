package com.jfshare.brand.dao.redis;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.jfshare.brand.dao.mysql.IBrandDao;
import com.jfshare.brand.dao.redis.impl.BrandRedisImpl;
import com.jfshare.brand.model.TbBrand;

@Repository
public class BrandRedisInit {
	private Logger logger = LoggerFactory.getLogger(BrandRedisInit.class);
	@Autowired
	@Qualifier("brandRedisImpl")
	private IBrandRedis brandRedisImpl;
	
	@Autowired
    private IBrandDao brandDaoImpl;

	@PostConstruct
	public void init() {
		
		/*List<TbBrand> totalBrands = brandDaoImpl.querySort();
		brandRedisImpl.addAllBrand(totalBrands);*/
	}
}
