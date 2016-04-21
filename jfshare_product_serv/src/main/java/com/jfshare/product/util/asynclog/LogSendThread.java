package com.jfshare.product.util.asynclog;

import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.product.dependserv.ManagerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lenovo on 2015/12/19.
 */
public class LogSendThread implements Runnable{
    private Logger logger = LoggerFactory.getLogger(LogSendThread.class);
    private ManagerClient managerClient;

    public LogSendThread(ManagerClient managerClient) {
        this.managerClient = managerClient;
    }

    //    public void send() {
//        logger.info("==>> AsyncSendThread，开始运行");
//        List<ProductOpt> optLogs = new ArrayList<ProductOpt>();
//        int logTotal = LogCommon.asyncProductOptLogsQueue.drainTo(optLogs);
//        if(logTotal == 0) {
//            logger.info("<<== AsyncSendQueue 为空，线程退出");
//            return;
//        }
//
//        for(ProductOpt productOpt : optLogs) {
//            managerClient.logProductOpt(productOpt);
//        }
//
//        logger.info("<<== AsyncSendThread，开始运行");
//    }

    @Override
    public void run() {
        while(true) {
            try {
                ProductOpt productOpt = LogCommon.asyncProductOptLogsQueue.take();
                logger.info("== 调用管理中心logProductOpt()接口写入商品状态变更日志，productOpt：{}", productOpt);
                managerClient.logProductOpt(productOpt);
            } catch (InterruptedException e) {
                logger.info("<<== 从sendQueue中获取数据发生异常", e);
            }
        }
    }
}
