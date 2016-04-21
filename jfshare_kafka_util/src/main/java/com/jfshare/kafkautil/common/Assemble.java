package com.jfshare.kafkautil.common;

import com.jfshare.kafkautil.kafka.KafkaProducer;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lenovo on 2015/11/10.
 */
public class Assemble {
    private Logger logger = LoggerFactory.getLogger(Assemble.class);

    public Map<String,KafkaProducer> producerMap;

    public Assemble() {
    }

    public Assemble(Set<KafkaProducer> producers) {
        producerMap = new HashMap<String, KafkaProducer>();
        for(KafkaProducer producer : producers) {
            producerMap.put(producer.getTopic(), producer);
            logger.info("Assemble ==> init AsyncKafkaProducer for topic [{}] success", producer.getTopic());
        }
    }

    public void addProducer(KafkaProducer producer) {
        if(MapUtils.isEmpty(producerMap)) {
            producerMap = new HashMap<String, KafkaProducer>();
        }
        producerMap.put(producer.getTopic(), producer);
    }

    public KafkaProducer getProducer(String topic) {
        if(StringUtils.isBlank(topic)) {
            throw new RuntimeException("topic is null");
        }
        KafkaProducer producer = this.producerMap.get(topic);
        if(producer == null) {
            throw new RuntimeException("producer for topic["+topic+"] not exists");
        }

        return producer;
    }
}
