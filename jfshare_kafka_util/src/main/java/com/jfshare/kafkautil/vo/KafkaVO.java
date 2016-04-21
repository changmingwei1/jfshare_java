package com.jfshare.kafkautil.vo;

/**
 * 用来保存队列中kafka消息的
 * @author zhangbo01
 *
 */
public class KafkaVO{

	/**
	 * 消息的topic，非空
	 */
	private String topic;
	
	/**
	 * 消息的key，可以为空，当为空时值为'-'
	 */
	private String key;

	/**
	 * 消息的message，非空
	 */
	private String message;
	
	public KafkaVO() {}

    public KafkaVO(String topic, String key, String message) {
        this.topic = topic;
        this.key = key;
        this.message = message;
    }

    public KafkaVO(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "KafkaVO{" +
                "topic='" + topic + '\'' +
                ", key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

