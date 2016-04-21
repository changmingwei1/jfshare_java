package com.jfshare.kafkautil.common;

import com.jfshare.kafkautil.vo.KafkaVO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 常量类
 * @author zhangbo01
 *
 */
public class ConstantInfo {

    public static BlockingQueue<KafkaVO> msgQueue = new LinkedBlockingQueue<KafkaVO>(10000);
	
	/**
	 * kafka消息队列的前缀
	 */
	public final static String KAFKA_QUEUE_PREFIX = "trade-kafka-queue-prefix";
	
	/**
	 * kafka消息队列的分隔符，为$$
	 */
	public final static String KAFKA_QUEUE_SEPARATOR = "$$";
	
	/**
	 * 字符串的默认空串值为-
	 */
	public final static String NONE_4_STRING = "-";
	
}
