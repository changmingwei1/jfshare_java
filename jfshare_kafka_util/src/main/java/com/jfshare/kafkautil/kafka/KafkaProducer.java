package com.jfshare.kafkautil.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @类描述: Kafka发送数据客户端
 */

public class KafkaProducer {

	private Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private ProducerConfig config;
    private String topic;
    private Producer producer;

    public KafkaProducer(java.util.Properties props, String topic) {
        this.config = new ProducerConfig(props);
        this.topic = topic;

        this.init();
        logger.info("==> 初始化producter完成 for topic:{}", topic);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    private void init() {
        producer = new Producer<String, String>(config);
    }

    /**
     * 向kafka放入消息, 可以批量放入单个, 或者多个数据
     */
    public boolean sendMsg(String... message) {
        if (message == null || message.length == 0) {
            return false;
        }
        List<KeyedMessage<String, String>> list = new ArrayList<KeyedMessage<String, String>>();
        for (String msg : message) {
            list.add(new KeyedMessage<String, String>(topic, msg));
        }
        producer.send(list);
        logger.debug("send " + list.size() + " message to kafka");
        return true;
    }

    /**
     * 可以批量提交一个string list
     * @param listMessage
     * @return
     */
    public boolean sendMsgList(List<String> listMessage) {
        if (listMessage == null || listMessage.size() == 0) {
            return false;
        }else{
            String[] msgs = listMessage.toArray(new String[listMessage.size()]);
            return sendMsg(msgs) ;
        }
    }

    public void close(){
        this.producer.close();
    }
}
