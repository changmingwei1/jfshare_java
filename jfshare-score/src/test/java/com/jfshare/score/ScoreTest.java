package com.jfshare.score;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.CachAmountCallParam;
import com.jfshare.finagle.thrift.score.CachAmountQueryParam;
import com.jfshare.finagle.thrift.score.CachAmountResult;
import com.jfshare.finagle.thrift.score.RelaAccountRequestParam;
import com.jfshare.finagle.thrift.score.ResponseCachResult;
import com.jfshare.finagle.thrift.score.ResponseRelaAcoountResult;
import com.jfshare.finagle.thrift.score.ResponseScoreResult;
import com.jfshare.finagle.thrift.score.ScoreAccountResult;
import com.jfshare.finagle.thrift.score.ScoreRequestParam;
import com.jfshare.finagle.thrift.score.ScoreResult;
import com.jfshare.finagle.thrift.score.ScoreServ;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreTradeQueryParam;
import com.jfshare.finagle.thrift.score.ScoreTradeResult;
import com.jfshare.finagle.thrift.score.ScoreUserQueryParam;
import com.jfshare.finagle.thrift.score.ScoreUserResult;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.score.util.Constants;
public class ScoreTest {
	// 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT =1;

    private TTransport transport;

    private TProtocol protocol;

    private ScoreServ.Client client;

    private static final String IP = "127.0.0.1";
    //private static final String IP = "127.0.0.1";
    private static final Integer PORT = 2002;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new ScoreServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }
    
//    @Test
//    public void testIncome() throws TException {
//    	ScoreTrade scoreTrade = new ScoreTrade();
//    	scoreTrade.setAmount(200);
//    	scoreTrade.setUserId(187);
//    	scoreTrade.setInOrOut(1);
//    	scoreTrade.setType(1);
//    	scoreTrade.setTrader(2);
//    	scoreTrade.setTradeId("zj0000000");
//    	//for(int i=0;i<1000;i++){
//    	StringResult result = client.income(scoreTrade);
//    	System.err.println(result);
//    	//}  	
//    }
    
//    @Test
//    public void expenditure() throws TException {
//    	ScoreTrade scoreTrade = new ScoreTrade();
//    	scoreTrade.setAmount(1);
//    	scoreTrade.setUserId(187);
//    	scoreTrade.setInOrOut(2);
//    	scoreTrade.setType(1);
//    	scoreTrade.setTrader(1);
//    	for(int i=0;i<500;i++){
//    	StringResult result = client.expenditure(scoreTrade);  
//    	System.err.println(result); 
//    	}
//    }
//    
//    @Test
//    public void testExpenditure() throws TException {
//    	ScoreTrade scoreTrade = new ScoreTrade();
//    	scoreTrade.setAmount(5000);
//    	scoreTrade.setUserId(187);
//    	scoreTrade.setInOrOut(2);
//    	scoreTrade.setType(1);
//    	scoreTrade.setTrader(1);
//    	scoreTrade.setTradeId("aaa1111234");
//    	StringResult result = client.expenditure(scoreTrade);
//    	
//    	System.err.println(result);
//    }
    
//    @Test
//    public void testGetScore() throws TException {
//
//    	ScoreResult result = client.getScore(1);
//    	
//    	System.err.println(result);
//    }
    
//    @Test
//    public void testQueryScoreTrade() throws TException {
//    	ScoreTradeQueryParam param = new ScoreTradeQueryParam();
//    	param.setUserId(60);
//    	//param.setStartTime("20160520190433");
////    	param.setEndTime("20160520190433");
////    	//param.setTradeTime("2016-05-04 10:25:58");
////    	param.setInOrOut(1);
//    	//param.setType(0);
//    	
//    	Pagination page = new Pagination();
//    	//page.setCurrentPage(1);
//    	//page.setNumPerPage(10);
//    	ScoreTradeResult result = client.queryScoreTrade(param, page);
//    	
//    	System.err.println(result);
//    }
////    
//    @Test
//    public void testQueryScoreInfo() throws TException {
//    	ScoreUserQueryParam param = new ScoreUserQueryParam();
//    	//param.setUserId(35);
//    	param.setStartTime("20150330235746");
//    	param.setEndTime("20160526180445");
////    	param.setMobile("13558731840");
////    	param.setAmount(0);
//    	Pagination page = new Pagination();
//    	page.setCurrentPage(1);
//    	page.setNumPerPage(10);
//    	ScoreUserResult result = client.queryScoreUser(param, page);
//    	
//    	System.err.println(result);
//    }
//    @Test
//    public void testQueryScoreInfo() throws TException {
//    CachAmountQueryParam param = new CachAmountQueryParam();
//  	param.setUserId(1);
//  	CachAmountResult result = client.queryCachAmount(param);  	
//  	System.err.println(result);
//  }
    
    //积分兑出测试
//  @Test
//  public void cachAmountCall() throws TException {
//	  CachAmountCallParam param = new CachAmountCallParam();
//  	param.setUserId(187);
//  	param.setCachAmount(1000);
//  	param.setMobile("13512837852");
//  	for(int i = 0 ;i <10 ;i++){
//  	ResponseCachResult result = client.cachAmountCall(param);
//  	}
//}
    //积分兑入测试
//    @Test
//    public void enterAmountCall() throws TException {
//    	ScoreRequestParam param = new ScoreRequestParam();
//    	param.setAppCode("JFX");
//    	param.setRequestDate("20160517201532");
//    	param.setSign("06c1ff94296292bf7e16ad9bf1c3343a");
//    	param.setSpID("171045");
//    	param.setOutOrderID("DC20160517201532");
//    	param.setDeviceNo("13161351365");
//    	param.setDeviceType("18");
//    	param.setProvinceID("19");
//    	param.setCustID("");
//    	param.setNum("1000");
//    	param.setRemark("兑入积分");
//    	param.setActiveID("");
//    	param.setExpTime("2016-05-31");
//    	//for(int i = 0 ;i <15 ;i++){
//    	ResponseScoreResult result = client.enterAmountCall(param);
//    	//}
//    	//System.err.println(result);
//    }
    //压力测试
//  @Test
//  public void cachAmountCall() throws TException {
//
//  	StringResult result = client.getRedisbyKey("subject:Info1001",10);
//  	System.out.println(result);
//  }
//  @Test
//  public void cachAmountCall() throws TException {
//	  CachAmountCallParam param = new CachAmountCallParam();
//  	param.setUserId(187);
//  	param.setCachAmount(1000);
//  	param.setMobile("13512837852");
//  	ResponseCachResult result = client.cachAmountCall(param);
//  	System.out.println(result);
//}
  
  //判断用户是否绑定账号
//@Test
//public void isUserIdRela() throws TException {
//
//	ScoreAccountResult result = client.isUserIdRela(187);
//	System.out.println(result);
//}
    //判断账号是否绑定
//@Test
//public void isAccountRela() throws TException {
//
//	ScoreAccountResult result = client.isAccountRela("18910573200");
//	System.out.println(result);
//} 
    
//    //账号绑定测试
//  @Test
//  public void enterAmountCall() throws TException {
//	RelaAccountRequestParam param = new RelaAccountRequestParam();
//  	param.setAppCode("TEL");
//  	param.setRequestDate("20160517201532");
//  	param.setSign("06c1ff94296292bf7e16ad9bf1c3343a");
//  	param.setSpID("171045");
//  	param.setDeviceNo("18910573200");
//  	param.setDeviceType("187");
//  	param.setOutCustID("13161351375");
//  	param.setToken("OO");
//  	param.setExpTime("2016-08-31");
//  	ResponseRelaAcoountResult result = client.relaAccountCall(param);
// 
//  	System.err.println(result);
//  }
}
