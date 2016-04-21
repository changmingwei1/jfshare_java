package com.jfshare.test;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.jfshare.finagle.thrift.address.AddressInfoListResult;
import com.jfshare.finagle.thrift.address.AddressServ;


public class Test {
	public static void main(String[] args) {
	    try {
	        TTransport transport;
	        if (true) {
//	          transport = new TSocket("192.168.10.57", 12315);
	      	  transport = new TSocket("localhost", 12308);
//	          transport = new TSocket("192.168.10.66", 12308);
	          transport = new TSocket("172.16.13.3", 12308);
	          transport.open();
	        }
	        
	        TProtocol protocol = new  TBinaryProtocol(new TFramedTransport(transport));
//	        ProductServ.Client client = new ProductServ.Client(protocol);
//	        StringResult r = client.querySku("1");
//	        System.out.print(r);
	        

	        AddressServ.Client client = new AddressServ.Client(protocol);
	        AddressInfoListResult r = client.queryAddress(49647418);
//	        r.setEmail("1111");
//	        r.setId(1111);
//	        client.updateAddress(r);
//	        AddressInfo r = client.queryAddressById(146, 3563491);
//	        r.getAddressInfo().setAddress("11222222222222222111");
//	        r.getAddressInfo().setUserId(12222);
//	        client.updateAddress(r.getAddressInfo());
	        System.out.print(r);
	        
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
