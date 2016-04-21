package com.jfshare.test;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.jfshare.finagle.thrift.common.AttributionResult;
import com.jfshare.finagle.thrift.common.CommonServ;


public class Test {
	public static void main(String[] args) {
	    try {
	        TTransport transport;
	        if (true) {
	      	  transport = new TSocket("localhost", 12307);
//	          transport = new TSocket("192.168.10.66", 12307);
	          transport.open();
	        }
	        
	        TProtocol protocol = new  TBinaryProtocol(new TFramedTransport(transport));
//	        ProductServ.Client client = new ProductServ.Client(protocol);
//	        StringResult r = client.querySku("1");
//	        System.out.print(r);
	        

	        CommonServ.Client client = new CommonServ.Client(protocol);
	        AttributionResult r = client.getAttributionNameById(130000, 0, 130101);
//	        r = client.county(130100);
	        System.out.print(r);
	        
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
