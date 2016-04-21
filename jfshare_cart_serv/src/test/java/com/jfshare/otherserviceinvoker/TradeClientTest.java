package com.jfshare.otherserviceinvoker;

import com.jfshare.cart.server.depend.TradeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TradeClientTest {

	private AbstractApplicationContext context;
	
	private TradeClient tradeClient;
	
	@Before
	public void setUp() {
		this.context = new ClassPathXmlApplicationContext("/spring/stand-alone.xml");
		this.tradeClient = (TradeClient)this.context.getBean("orderClient");
	}
	
//	@Test
//	public void testZheBuyCount() {
//		try {
//			int userId = 13627153;
//			String productId = "ze150428164211000846";
//			int result = this.tradeClient.zheBuyCount(userId, productId);
//			System.out.println("result:" + result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@After
	public void stop() {
		if (this.context != null) {
			this.context.stop();
		}
	}
	
}
