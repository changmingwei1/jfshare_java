package com.jfshare.test;


import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jfshare.finagle.thrift.common.AreaInfoResult;
import com.jfshare.finagle.thrift.common.AttributionResult;
import com.jfshare.finagle.thrift.common.CommonServ;


public class TestHandler {

	// 执行次数，0的时候不执行测试用例
	private static final Integer DO_IT = 1;

	private TTransport transport;

	private TProtocol protocol;

	private CommonServ.Client client;

	private static final String IP = "127.0.0.1";

	private static final Integer PORT = 1984;
	
	@Before
	public void setUp() throws Exception {
		if (DO_IT == 0)
			return;
		
		transport = new TFramedTransport(new TSocket(IP, PORT));
		protocol = new TBinaryProtocol(transport);
		client = new CommonServ.Client(protocol);
		transport.open();
	}

	@After
	public void tearDown() throws Exception {
		if (DO_IT == 0)
			return;

		transport.close();
	}

	@Test
	public void testProvince() throws TException {
		if (DO_IT == 0)
			return;
		for(int i=0;i<DO_IT;i++){
			AreaInfoResult areaInfoResult = client.province();
			System.out.println(areaInfoResult.toString());
		}
	}

	 

	//@Test
	public void testCounty() throws TException {
		if (DO_IT == 0)
			return;
		
		int cityId = 0;
		AreaInfoResult areaInfoResult = client.county(cityId);
		System.out.println(areaInfoResult.toString());
	}

	@Test
	public void testIpAttribution() throws TException {
		if (DO_IT == 0)
			return;
		for(int i=0;i<DO_IT;i++){
			System.out.println(System.currentTimeMillis());
			int ipTemp1 = (int)(Math.random()*254)+1;
			int ipTemp2 = (int)(Math.random()*254)+1;
			int ipTemp3 = (int)(Math.random()*254)+1;
			int ipTemp4 = (int)(Math.random()*254)+1;
			String ip = ipTemp1 + "." + ipTemp2 + "." +ipTemp3 + "." + ipTemp4;
			AttributionResult attributionResult = client.ipAttribution(ip);
			System.out.println(attributionResult.toString());
			System.out.println(System.currentTimeMillis());
		}
	}

}
