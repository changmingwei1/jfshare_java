package test;

import com.jfshare.kafkautil.common.Assemble;
import com.jfshare.kafkautil.common.ConstantInfo;
import com.jfshare.kafkautil.kafka.AsyncTransfer;
import com.jfshare.kafkautil.kafka.KafkaAsyncProducer;
import com.jfshare.kafkautil.kafka.KafkaProducer;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Lenovo on 2015/11/10.
 */
public class TestAsyncTransfer {

    @Test
    public void testThread() {
        Assert.assertTrue(ConstantInfo.msgQueue.isEmpty());
        KafkaAsyncProducer.sendMessage("TestKafka", "this is a testAsync message");
        KafkaAsyncProducer.sendMessage("TestKafka", "this is a testAsync message1");
        KafkaAsyncProducer.sendMessage("TestKafka", "this is a testAsync message2");
        Assert.assertTrue(!ConstantInfo.msgQueue.isEmpty());

        Set<KafkaProducer> kafkaProducers = new HashSet<KafkaProducer>();
        kafkaProducers.add(initKafkaProducer("TestKafka"));
        Assemble assemble = new Assemble(kafkaProducers);
        AsyncTransfer transfer = new AsyncTransfer(assemble);
        Assert.assertTrue(transfer.isDaemon());
        Assert.assertTrue(transfer.getName().equals("KafkaAsyncSendThread"));
        transfer.start();
    }

    @Test
    public void testAssemble() {
        Set<KafkaProducer> kafkaProducers = new HashSet<KafkaProducer>();
        kafkaProducers.add(initKafkaProducer("TestKafka"));
        Assemble assemble = new Assemble(kafkaProducers);
        KafkaProducer producer = assemble.getProducer("TestKafka");
        Assert.assertTrue(producer!= null);
        boolean r = producer.sendMsg("this is a test message for Assemble");
        Assert.assertTrue(r);
    }

    @Test
    public void testProducer() {
        KafkaProducer producer = this.initKafkaProducer("TestKafka");

        boolean r = producer.sendMsg("this is a test message");
        Assert.assertTrue(r);
    }

    @Test
    public void testAsyncProducer() {
        Assert.assertTrue(ConstantInfo.msgQueue.isEmpty());
        KafkaAsyncProducer.sendMessage("TestKafka", "this is a testAsync message");
        Assert.assertTrue(!ConstantInfo.msgQueue.isEmpty());
    }

    private KafkaProducer initKafkaProducer(String topic) {
        Properties prop = new Properties();
        Map<String,String> confMap = new HashMap<String,String>();
//        confMap.put("metadata.broker.list", "192.168.5.159:9092,192.168.5.158:9092");
        confMap.put("metadata.broker.list", "120.24.153.102:9092,120.24.153.155:9092");
        confMap.put("producer.type", "sync");
        confMap.put("compression.codec", "none");
        confMap.put("serializer.class", "kafka.serializer.StringEncoder");
        confMap.put("request.required.acks", "1");
        prop.putAll(confMap);
        KafkaProducer producer = new KafkaProducer(prop, topic);
        return producer;
    }
}
