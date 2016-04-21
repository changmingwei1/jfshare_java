package com.jfshare.otherserviceinvoker;

import com.jfshare.cart.server.depend.ProductClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProductClientTest {

	private AbstractApplicationContext context;
	
	private ProductClient productClient;
	
	@Before
	public void setUp() {
		this.context = new ClassPathXmlApplicationContext("/spring/stand-alone.xml");
		this.productClient = (ProductClient)this.context.getBean("productClient");
	}
	
	@Test
	public void testSkuProductDetail() {
		try {
			String productId = "ze140526094037000001";
			String skuNum = "187-1024";
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	
}
