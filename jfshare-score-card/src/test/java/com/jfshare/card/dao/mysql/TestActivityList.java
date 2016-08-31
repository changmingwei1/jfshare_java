package com.jfshare.card.dao.mysql;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;

import com.jfshare.finagle.thrift.card.ActivityQueryParam;
import com.jfshare.finagle.thrift.card.CardQueryParam;
import com.jfshare.finagle.thrift.card.ScoreCardServ;
import com.jfshare.finagle.thrift.pagination.Pagination;

public class TestActivityList {
	
	public static void main(String[] args) throws Exception{
		TFramedTransport transport = new TFramedTransport(new TSocket("127.0.0.1", 2005));
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		ScoreCardServ.Client client = new ScoreCardServ.Client(protocol);
		transport.open();
		ActivityQueryParam param = new ActivityQueryParam();
		//param.setCurStatus("1");
		//param.setName("Tes");
		Pagination pagination = new Pagination();
		pagination.setCurrentPage(1);
		pagination.setNumPerPage(2);
		client.queryActivities(param, pagination);
//		CardQueryParam param = new CardQueryParam();
//		Pagination pagination = new Pagination();
//		
//		pagination.setCurrentPage(1);
//		pagination.setNumPerPage(10);
//		
//		client.queryCards(46,param,pagination);
		transport.close();
	}
}
