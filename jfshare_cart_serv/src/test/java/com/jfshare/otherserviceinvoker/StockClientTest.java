package com.jfshare.otherserviceinvoker;

import com.jfshare.cart.server.depend.StockClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockClientTest {

	private AbstractApplicationContext context;
	
	private StockClient stockClient;
	
	@Before
	public void setUp() {
		this.context = new ClassPathXmlApplicationContext("/spring/stand-alone.xml");
		this.stockClient = (StockClient)this.context.getBean("stockClient");
	}

	
	@After
	public void stop() {
		if (this.context != null) {
			this.context.stop();
		}
	}
	
//	@Test
//	public void testGetStockForSku() {
//		try {
//			String productId = "ze140526113510000001";
//			String skuNum = "181-1001:180-1123";
//
//			StockInfo stockInfo = this.stockClient.getStockForSku(productId, skuNum);
//			System.out.println(stockInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
}
