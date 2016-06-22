package com.jfshare.test;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.finagle.thrift.address.AddressInfoListResult;
import com.jfshare.finagle.thrift.address.AddressServ;
import org.junit.*;


public class TestAddress {

	// 0的时候不执行测试用例，其他值的时候执行
	private static final Integer DO_IT = 1;

	static private TTransport transport;

	static private TProtocol protocol;

	static private AddressServ.Client client;

	private static final String IP = "127.0.0.1";// "120.24.153.155";
//	private static final String IP = "101.201.38.182";// "120.24.153.155";

	private static final Integer PORT = 1985;
	
	
/*	public  static void main(String...client ) throws Exception{
	    setUp();
	    testQueryAddress();
	    tearDown();
	}
	*/
	
	@Before
	public  void setUp() throws Exception {
		if (DO_IT == 0)
			return;
		
		transport = new TFramedTransport(new TSocket(IP, PORT));
		protocol = new TBinaryProtocol(transport);
		client = new AddressServ.Client(protocol);
		transport.open();
	}

	@After
	 public void tearDown() throws Exception {
		if (DO_IT == 0)
			return;

		transport.close();
	}

	@org.junit.Test
	 public void testQueryAddress() throws TException {
		if (DO_IT == 0)
			return;
		
		for(int i=0;i<DO_IT;i++){
			int userIds[] = {1111};
			int index = (int)(Math.random()*1);
			int userId = userIds[index];
			
			AddressInfoListResult addressInfoListResult = client.queryAddress(17);
			
			System.out.println(addressInfoListResult.toString());
		}
	}

	//@Test
	public void testQueryAddressById() throws TException {
    	    try{
    		if (DO_IT == 0)
    			return;
    		System.out.println("start:" + System.currentTimeMillis());
    		for(int i=0; i<DO_IT; i++){
    			int ids[] = {2,3,116,124,126,17,29};
    			int userIds[] = {13627153,13627153,7060800,3563491,349913,1427759,4763301};
    			int index = (int)(Math.random()*6);
    			int id = ids[index];
    			int userId = userIds[index];
    			//System.out.println(System.currentTimeMillis());
    			AddressInfo addressInfo = client.queryAddressById(id, userId);
    			//System.out.println(System.currentTimeMillis());
    			
    			//System.out.println(addressInfo.toString());
    			
    		}
    		System.out.println("end:" + System.currentTimeMillis());
    	    }catch(Exception e){}
	}
}
