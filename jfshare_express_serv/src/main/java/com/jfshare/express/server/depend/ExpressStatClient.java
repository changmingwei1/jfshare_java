package com.jfshare.express.server.depend;

import com.jfshare.finagle.thrift.expressstat.ExpressWayBillStatService;
import com.jfshare.finagle.thrift.expressstat.ExpressWayInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
 
 
/**
 * 物流接口客户端
 */
//@Service
public class ExpressStatClient {
    private static Logger logger = LoggerFactory.getLogger(ExpressStatClient.class);
    
    @Resource(name = "expressWayBillStatService")
    private ExpressWayBillStatService.ServiceIface expressWayBillStatService;
    
    /**
     * 同步至物流
     * @createTime 2014年5月22日 上午11:10:29
     * @return
     * @return int 失败返回-1
     */
    public int syncExpressWayInfo(ExpressWayInfo expressWayInfo){
    	int ret = -1;
//        try {
//             ret = Await.result(expressWayBillStatService.syncExpressWayInfo(expressWayInfo));
//             logger.info("----------syncExpressWayInfo by ExpressWayBill service--------------------"+ ret);
//        }
//        catch(Exception e){
//            logger.error( e.getMessage(),e);
//        }
        return ret;
    }
    
} 
 