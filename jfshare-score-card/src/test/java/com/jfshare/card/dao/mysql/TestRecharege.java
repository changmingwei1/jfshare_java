package com.jfshare.card.dao.mysql;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jfshare.finagle.thrift.card.DirectRechargeResult;
import com.jfshare.finagle.thrift.card.ScoreCardServ;
import com.jfshare.finagle.thrift.card.ToRechargeParams;
import com.jfshare.finagle.thrift.result.Result;

/**
* Created by lenovo on 2015/9/28.
*/
public class TestRecharege {
    // 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private ScoreCardServ.Client client;

    private static final String IP = "127.0.0.1";
//    private static final String IP = "120.24.153.155";

    private static final Integer PORT = 2003;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new ScoreCardServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }


  @Test
  public void testdirectRecharge() throws Exception{
  		DirectRechargeResult directRechargeResult=new DirectRechargeResult();
  	
  		Result result=new Result();
  		directRechargeResult.setResult(result);
  	
  		ToRechargeParams params=new ToRechargeParams();
  		params.setActivityId("46");
  		params.setFilePath("F:\\");
  		params.setValidataStr("123123123");
  		directRechargeResult=client.directRecharge(params);
  		System.out.println("返回码="+directRechargeResult);
  }
}
