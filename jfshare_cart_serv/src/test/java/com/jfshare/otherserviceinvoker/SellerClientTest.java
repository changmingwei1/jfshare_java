package com.jfshare.otherserviceinvoker;

import com.jfshare.cart.server.depend.SellerClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SellerClientTest {

	private AbstractApplicationContext context;
	
	private SellerClient sellerClient;
	
	@Before
	public void setUp() {
		this.context = new ClassPathXmlApplicationContext("/spring/stand-alone.xml");
		this.sellerClient = (SellerClient)this.context.getBean("sellerClient");
	}
	
//	@Test
//	public void testGetSellerInfo() {
//		try {
//			int sellerId = 609386;
//			SellerInfo sellerInfo = this.sellerClient.getSellerInfoByCache(sellerId);
//			System.out.println("sellerInfo:" + sellerInfo);
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
