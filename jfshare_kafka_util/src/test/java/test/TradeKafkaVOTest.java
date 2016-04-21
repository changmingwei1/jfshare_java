package test;

import com.jfshare.kafkautil.vo.KafkaVO;
import org.junit.Test;


public class TradeKafkaVOTest {

    @Test
	public void testToString() {
        KafkaVO tradeKafkaVO = new KafkaVO();
        tradeKafkaVO.setTopic("product-sale");
        tradeKafkaVO.setKey("orderId");
        tradeKafkaVO.setMessage("orderMessage");
        System.out.println(tradeKafkaVO.toString());

    }

}
