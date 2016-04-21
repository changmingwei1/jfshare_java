package com.jfshare.kafkautil.kafka;

import com.jfshare.kafkautil.Configuration;
import com.jfshare.kafkautil.common.Assemble;
import com.jfshare.kafkautil.common.ConstantInfo;
import com.jfshare.kafkautil.vo.KafkaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用于发送TradeKafkaQueue中消息的类
 * @author zhangbo01
 *
 */
public class AsyncTransfer extends Thread {
	
    private static Logger logger = LoggerFactory.getLogger(AsyncTransfer.class);
	
	private Assemble assemble;

    public void setAssemble(Assemble assemble) {
        this.assemble = assemble;
    }

    public AsyncTransfer() {
        this.init();
    }

    public AsyncTransfer(Assemble assemble) {
        this.assemble = assemble;
        this.init();
    }

    private void init(){
        this.setDaemon(true);
        this.setName("KafkaAsyncSendThread");
    }

    public void run() {
		while (true) {
            logger.debug("-----====>>>>>>>>>>>>thread:{} is running", this.getName());
			try {
                //推送kafka消息
                List<KafkaVO> logList = new ArrayList();
                ConstantInfo.msgQueue.drainTo(logList);
                this.send(logList);

                TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

    private void send(List<KafkaVO> msgList) {
        for(KafkaVO kafkaVO : msgList) {
            try {
                assemble.getProducer(kafkaVO.getTopic()).sendMsg(kafkaVO.getMessage());
            } catch (Exception e) {
                logger.error("==> push to kafka failure [kafkaVO:{}]", kafkaVO);
            }
        }
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (logger.isInfoEnabled()) {
                    logger.info("Run kafka-util java client shutdown hook now! please wait ["+ Configuration.sendFreq+"] ms !");
                }
                //回收所有资源
                try {
                    Thread.sleep(Configuration.sendFreq); // 停留Configuration.sendFreq毫秒用来留出时间将队列里的剩余消息发送给kafka
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("kafka-util java client has stoped ! ");
            }
        }, "KafkaUtilJavaClientShutdownHook"));
    }
}
