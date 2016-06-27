package com.jfshare.cart.service;

import java.util.ArrayList;
import java.util.List;

import com.jfshare.finagle.thrift.cart.*;
import com.jfshare.finagle.thrift.result.StringResult;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CartTest {

	private TTransport transport;

	private TProtocol protocol;

	private CartServ.Client client;

//	 private static final String IP = "127.0.0.1";
	 private static final String IP = "101.201.38.182";

	private static final Integer PORT = 1992;

	@Before
	public void setUp() throws Exception {
		transport = new TFramedTransport(new TSocket(IP, PORT));
		protocol = new TBinaryProtocol(transport);
		client = new CartServ.Client(protocol);
		transport.open();
	}

	@After
	public void tearDown() throws Exception {
		transport.close();
	}

	@Test
	public void itemCount() {
		try {

			StringResult result = client.countItem("60", 1);
			System.out.println(result.getResult().getCode() + "####" + result.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void addCart() {
		try {
			Item item2 = new Item();
			item2.setCount(1);
			item2.setPrice("0.01");
			item2.setProductId("ze151024205818000350");
			item2.setSkuNum("1-5:100-103");
			CheckCartResult result2 = client.addItem("1111", item2, 2);

			System.out.println("::::" + result2.getResult().getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void updateItem() {
//		try {
//			String productId = "ze150104165958000001";
//			Item item2 = new Item();
//			item2.setCount(10);
//			item2.setPrice("0.1");
//			item2.setProductId(productId);
//			item2.setSkuNum("177-1021");
//
//
//			CartKey cartKey = new CartKey();
//			cartKey.setProductId(productId);
//			cartKey.setSkuNum("177-1021");
//
//			StringResult result = client.updateItem("54561141", cartKey, item2, 0);
//			System.out.println("::::" + result.getResult().getCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void updateCart() {
//		try {
//			String productId = "ze141024185649000001";
//
//			Item item2 = new Item();
//			item2.setCount(38);
//			item2.setPrice("0.1");
//			item2.setProductId(productId);
//			item2.setSkuNum("");
//
//			CartKey cartKey = new CartKey();
//			cartKey.setProductId(productId);
//			cartKey.setSkuNum("");
//
//			List<CartKey> list = new ArrayList<CartKey>();
//			CartKey key = new CartKey();
//			key.setProductId(productId);
//			key.setSkuNum("");
//			list.add(key);
//
//			CheckCartResult result = client.updateCart("user1", list, cartKey, item2, 0);
//			System.out.println("::::" + result.getResult().getCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void deleteItem() {
//		try {
//			List<CartKey> list = new ArrayList<CartKey>();
//			CartKey key = new CartKey();
//			key.setProductId("ze150317124911000001");
//			key.setSkuNum("183-1001:182-1101");
//			list.add(key);
//
//			Result result = client.deleteItem("361125", list, 0);
//			System.out.println("::::" + result.getCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void mergeItem() {
//		try {
//			Result result = client.mergeItem("user1", "111111", 0);
//			System.out.println("::::" + result.getCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Test
	public void cartList() {
		try {
			for (int i = 0; i < 1; i++) {
				// 45923658 压测用户； 淘老大361125
				// 47763855 线上用户：淘学小二
				CartResult result = client.listItem("60", 1);
				System.out.println(result.getResult().getCode() + "---" + result.getItemList().size() + "---"
						+ result.getInvalidItemList().size());
				System.out.println(result.toString());
				System.out.println(result.getItemList().get(0).getItemDetailList().get(0).getProduct().getProduct().getProductSku().getSkuItems().get(0));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
//	public void findListByCartKey() {
//		try {
//
//			List<CartKey> list = new ArrayList<CartKey>();
//			CartKey key = new CartKey();
//			key.setProductId("ze140913t2d6e833f5e");
//			key.setSkuNum("183-1016:182-1125");
//
//			list.add(key);
//			for (int i = 0; i < 100; i++) {
//				CartResult result = client.findListByCartKey("45923432", list, 0);
//				System.out.println(result.getResult().getCode() + "---" + result.getItemList().size() + "---"
//						+ result.getInvalidItemList().size());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void checkItem() {
//		try {
//			List<CartKey> list = new ArrayList<CartKey>();
//			CartKey key = new CartKey();
//			key.setProductId("ze150105115528000001");
//			key.setSkuNum("179-1023:178-1123");
//
//			list.add(key);
//			CheckCartResult result = client.checkItem("55287782", list, 1);
//			System.out.println("::::" + result.getResult().getCode());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void miniCartList() {
//		try {
//			for (int i = 0; i < 100; i++) {
//				// 45923658 压测用户； 淘老大361125
//				// 47763855 线上用户：淘学小二   47763855
//				MiniCartResult result = client.miniCartList("47763855", 0);
//				System.out.println(result.getResult().getCode());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void getTimeStamp(){
		try {
			StringResult s = client.getTimeStamp("361125", 1);
			System.out.println(s.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
