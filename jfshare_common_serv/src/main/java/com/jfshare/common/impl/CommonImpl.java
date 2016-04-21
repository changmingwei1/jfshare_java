package com.jfshare.common.impl;

import java.util.HashMap;
import java.util.List;

import com.jfshare.common.model.TbIp2addressDic;
import com.jfshare.finagle.thrift.common.AreaInfo;
import com.jfshare.finagle.thrift.common.Attribution;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jfshare.finagle.thrift.common.AttributionId;
import com.jfshare.finagle.thrift.common.AttributionOperator;

@Repository
public class CommonImpl extends SqlSessionDaoSupport {
    public List<AreaInfo> province() {
        logger.info("get provinces...");
        List<AreaInfo> areaInfoList = this.getSqlSession().selectList("getProvince");

        return areaInfoList;
    }

    public List<AreaInfo> city(int provinceId) {
        List<AreaInfo> areaInfoList = this.getSqlSession().selectList("getCity", provinceId);

        return areaInfoList;
    }

    public List<AreaInfo> county(int cityId) {
        List<AreaInfo> areaInfoList = this.getSqlSession().selectList("getCounty", cityId);

        return areaInfoList;
    }

    public List<AreaInfo> street(int countyId) {
//        List<AreaInfo> areaInfoList = this.getSqlSession().selectList("getStreet");

        return null;
    }

    public Attribution numberAttribution(String number) {
        Attribution attribution = (Attribution) this.getSqlSession().selectOne("numberAttribution", number);
        return attribution;
    }

    public AttributionOperator numberAttributionOperator(String number) {
        AttributionOperator attributionOperator = (AttributionOperator) this.getSqlSession().selectOne("numberAttributionOperator", number);
        return attributionOperator;
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Attribution ipAttribution(String ip) {
        long ipInt = 0l;
        String[] ipStrs = ip.split("\\.");
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                long ip1Int = Long.parseLong(ipStrs[i]);
                ipInt = ipInt + ip1Int * 256 * 256 * 256;
                logger.info("ip1Int" + ipInt);
            } else if (i == 1) {
                int ip2Int = Integer.parseInt(ipStrs[i]);
                ipInt = ipInt + ip2Int * 256 * 256;
                logger.info("ip2Int" + ipInt);
            } else if (i == 2) {
                int ip3Int = Integer.parseInt(ipStrs[i]);
                ipInt = ipInt + ip3Int * 256;
                logger.info("ip3Int" + ipInt);
            } else {
                int ip4Int = Integer.parseInt(ipStrs[i]);
                ipInt = ipInt + ip4Int;
                logger.info("ip4Int" + ipInt);
            }
        }
        logger.info("ip转成整型:" + ipInt);
        Attribution attribution = (Attribution) this.getSqlSession().selectOne("ipAttribution", ipInt);
        return attribution;
    }

    public Attribution cityAttribution(int cityId) {
        Attribution attribution = (Attribution) this.getSqlSession().selectOne("cityAttribution", cityId);
        return attribution;
    }

    public AttributionId idByName(String provinceName, String cityName, String countyName) {
        HashMap paramap = new HashMap();
        paramap.put("provinceName", provinceName);
        paramap.put("cityName", cityName);
        paramap.put("countyName", countyName);
        int proviceId = 0;
        Object provinceObj = this.getSqlSession().selectOne("getProvinceId", provinceName);
        if (provinceObj != null) {
            proviceId = Integer.parseInt(provinceObj.toString());
        }
        paramap.put("provinceId", proviceId);
        int cityId = 0;
        Object cityObj = this.getSqlSession().selectOne("getCityId", paramap);
        if (cityObj != null) {
            cityId = Integer.parseInt(cityObj.toString());
        }
        paramap.put("cityId", cityId);
        int countyId = 0;
        Object countyObj = this.getSqlSession().selectOne("getCountyId", paramap);
        if (countyObj != null) {
            countyId = Integer.parseInt(countyObj.toString());
        }
        AttributionId attributionId = new AttributionId(proviceId, cityId, countyId);
        return attributionId;
    }
    
    public List<TbIp2addressDic> getIpStartList()
    {
    	return  this.getSqlSession().selectList("selectIpStartIntList");
    }

    public AreaInfo getAreaInfoById(int id) {
        HashMap paramap = new HashMap();
        paramap.put("id", id);
        AreaInfo areaInfo= (AreaInfo) this.getSqlSession().selectOne("getAreaInfoById", paramap);

        return areaInfo;
    }
}
