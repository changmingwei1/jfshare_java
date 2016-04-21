package com.jfshare.cart.service;

import java.util.ArrayList;
import java.util.List;

import com.jfshare.finagle.thrift.product.ProductServ;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

	private TTransport transport;

	private TProtocol protocol;

	private ProductServ.Client client;

	private static final String IP = "192.168.10.57";
	// 线上购物车
	private static final Integer PORT = 12306;

	@Before
	public void setUp() throws Exception {
		transport = new TFramedTransport(new TSocket(IP, PORT));
		protocol = new TBinaryProtocol(transport);
		client = new ProductServ.Client(protocol);
		transport.open();
	}

	@After
	public void tearDown() throws Exception {
		transport.close();
	}


}
