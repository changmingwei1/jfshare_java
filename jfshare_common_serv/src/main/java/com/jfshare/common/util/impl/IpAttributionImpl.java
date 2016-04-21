package com.jfshare.common.util.impl;


import com.jfshare.common.impl.CommonImpl;
import com.jfshare.common.model.TbIp2addressDic;
import com.jfshare.common.util.IIpAttribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class IpAttributionImpl implements IIpAttribution  {

	private Logger logger = LoggerFactory.getLogger(IpAttributionImpl.class);
	
    private List<TbIp2addressDic> ipList = new ArrayList<TbIp2addressDic>();

    @Autowired
    @Qualifier("commonImpl")
    private CommonImpl commonImpl;
    
    
//    @PostConstruct
    public void init()
    {
    	
    	logger.info("初始化IP数据...................");
    	ipList = commonImpl.getIpStartList();
    	logger.info("初始化IP数据 完成...................");
    }
    
    public Long getIpInt(String ip)
    {
    	 Long ipInt = (long) 0;
         String[] ipStrs = ip.split("\\.");
         for (int i = 0; i < 4; i++) {
             if (i == 0) {
            	 Long ip1Int = Long.parseLong(ipStrs[i]);
                 ipInt = ipInt + ip1Int * 256 * 256 * 256;
                 logger.info("ip1Int" + ipInt);
             } else if (i == 1) {
            	 Long ip2Int =  Long.parseLong(ipStrs[i]);
                 ipInt = ipInt + ip2Int * 256 * 256;
                 logger.info("ip2Int" + ipInt);
             } else if (i == 2) {
            	 Long ip3Int =  Long.parseLong(ipStrs[i]);
                 ipInt = ipInt + ip3Int * 256;
                 logger.info("ip3Int" + ipInt);
             } else {
            	 Long ip4Int =  Long.parseLong(ipStrs[i]);
                 ipInt = ipInt + ip4Int;
                 logger.info("ip4Int" + ipInt);
             }
         }
         
         return ipInt;
    }
    
    public TbIp2addressDic getIpInfo(Long value) 
    {
    	logger.info("【获取IP归属地】ip:"+ value);
	    int low = 0;
	    int high = ipList.size() - 1;
	    int index = 0;	    
    	while(low <= high) {
    		logger.info("ip value = "+ value + ", low=" + low + ", high=" + high);
	    	int middle = (low + high) / 2;
	    	if(value.equals(ipList.get(middle).getIpStartInt()))
	    	{
	    		logger.info("【获取IP归属地】ip==" + value + ",  获取ip段开始值：" + ipList.get(index).getIpStartInt());
	    		return ipList.get(middle);
	    	} else
	    	if(value > ipList.get(middle).getIpStartInt())
	    	{		
	    		low = middle + 1;
	    		index = middle;
	    	} else
	     	if(value < ipList.get(middle).getIpStartInt())
	    	{
	    		high = middle - 1;
	    	}
         }
	
    	
	    if(ipList.get(index).getIpStartInt() < value) {
	    	logger.info("【获取IP归属地】ip:" + value + ",  获取ip段开始值：" + ipList.get(index).getIpStartInt());
	    	return ipList.get(index);
	    }
	
	    return null;
	}

	@Override
	public TbIp2addressDic getIpAttribution(String ip) {
		 Long ipInt = getIpInt(ip);
		
		return getIpInfo(ipInt);
	}

    
    
}
