package com.jfshare.product.util.asynclog;

import com.jfshare.product.dependserv.ManagerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Lenovo on 2015/12/21.
 */
public class LogSendTask {
    private Logger logger = LoggerFactory.getLogger(LogSendTask.class);

    private Thread logSendThead;

    @Autowired
    private ManagerClient managerClient;

    @PostConstruct
    private void init() {
        logSendThead = new Thread(new LogSendThread(managerClient));
        logSendThead.setDaemon(true);
        logSendThead.setName("LogSendTask");
    }

    public void threadRunMonit() {
        logger.info("threadRunMonit start，logSendThead状态是" + logSendThead.getState());
        if (logSendThead.getState() == Thread.State.TERMINATED) {
            logger.error("告警：orderOperatorHandleThread线程退出了，工作停止了。");
            init();
        } else if (logSendThead.getState() == Thread.State.NEW) {
            logSendThead.start();
        }
    }
}
