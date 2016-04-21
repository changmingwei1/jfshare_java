package com.jfshare.common.util;

import com.jfshare.common.db.redis.ICommonJedis;
import com.jfshare.common.impl.CommonImpl;
import com.jfshare.finagle.thrift.common.AreaInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class Util {
    private Logger logger = LoggerFactory.getLogger(Util.class);
    private List<AreaInfo> provinceList;
//    private static Map<Integer, List<AreaInfo>> provinceCityMap = null;
//    private static Map<Integer, List<AreaInfo>> cityConuntyMap = null;

    @Autowired
    @Qualifier("commonImpl")
    private CommonImpl commonImpl;
	@Autowired
	private ICommonJedis commonJedis;


    @PostConstruct
    public void init() {

        logger.info("初始化省数据...................");
        provinceList = commonImpl.province();
        logger.info("初始化省数据 完成...................");
    }

    public List<AreaInfo> getProvinceList() {
        return provinceList;
    }

}
