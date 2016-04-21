package com.jfshare.kafkautil.kafka;

import com.jfshare.kafkautil.common.ConstantInfo;
import com.jfshare.kafkautil.vo.KafkaVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @类描述: Kafka发送数据客户端
 */

public class KafkaAsyncProducer {

	private static Logger logger = LoggerFactory.getLogger(KafkaAsyncProducer.class);

    public static void sendMessage(String topic, String msg) {
        KafkaVO kafkaVO = new KafkaVO(topic, msg);
        if(!ConstantInfo.msgQueue.offer(kafkaVO)) {
            logger.warn("==> 队列已满， 消息放入失败 需要通过其他方式补发该消息");
        } else {
            logger.debug("send [message] to asyncQueue: [" + kafkaVO + "] ");
        }
    }
}
