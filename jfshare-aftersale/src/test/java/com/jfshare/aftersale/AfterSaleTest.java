package com.jfshare.aftersale;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jfshare.finagle.thrift.aftersale.AfterSale;
import com.jfshare.finagle.thrift.aftersale.AfterSaleCountResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryCountParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleServ;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;

public class AfterSaleTest {
	// 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private AfterSaleServ.Client client;

    private static final String IP = "127.0.0.1";

    private static final Integer PORT = 2003;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new AfterSaleServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }
    
//    @Test
//    public void testRequest() throws TException{
//    	 if (DO_IT == 0)
//             return;
//
//    	AfterSale sale = new AfterSale();
//    	sale.setUserId(131);
//    	sale.setSellerId(1);
//    	sale.setOrderId("57820131");
//    	sale.setProductId("ze160518143645000161");
//    	sale.setSkuNum("1-4:100-107");
//    	sale.setType(1);
//    	sale.setReason("质量有问题");
//    	sale.setState(1);
//    	sale.setUserComment("werewrdfsdf");
//    	sale.setApproveComment("");
//    	sale.setOrderTime("2016-06-16 14:22:14");
//    	StringResult result = client.request(sale);
//    	System.out.println(result);
//    }
//    
//    @Test
//    public void testPass() throws TException{
//    	 if (DO_IT == 0)
//             return;
//    	 
//    	AfterSale sale = new AfterSale();
//    	sale.setUserId(1);
//    	sale.setSellerId(1);
//    	sale.setOrderId("dd1111");
//    	sale.setProductId("sp2222");
//    	sale.setSkuNum("1-1:100-101");
////    	sale.setType(1);
////    	sale.setReason("dddddddddddddddd");
//    	sale.setState(3);
//    	sale.setUserComment("werewrd");
//    	sale.setApproveComment("eeeeeee3");
//    	
//    	StringResult result = client.pass(sale);
//    	System.out.println(result);
//    }
    
//    @Test
//    public void testQueryAfterSale() throws TException{
//    	 if (DO_IT == 0)
//             return;
//    	 
//    	 AfterSaleQueryParam sale = new AfterSaleQueryParam();
////    	sale.setUserId(1);
////    	sale.setSellerId(1);
////    	sale.setOrderId("dd1111");
////    	sale.setProductId("sp2222");
////    	sale.setSkuNum("1-1:100-101");
////    	sale.setType(1);
////    	sale.setState(3);
//    	 List<String> orderIdList = new ArrayList<>();
////    	 orderIdList.add("47120034");
////    	 orderIdList.add("50400034");
//    	 sale.setOrderIdList(orderIdList);
//    	AfterSaleResult result = client.queryAfterSale(sale);
//    	System.out.println(result);
//    }
    
//  @Test
//  public void testQueryAfterSale() throws TException{
//  	 if (DO_IT == 0)
//           return;
//  	 
//  	 AfterSaleQueryCountParam sale = new AfterSaleQueryCountParam();
//  	sale.setSellerId(13);
//  	sale.setState(1);
//  	AfterSaleCountResult result = client.queryAfterSaleCount(sale);
//  	System.out.println(result);
//  }
      
  @Test
  public void testQueryAfterSale() throws TException{
  	 if (DO_IT == 0)
           return;
  	 
  	AfterSaleOrderParam param = new AfterSaleOrderParam();
//  	param.setUserId(1);
//  	param.setSellerId(1);
//  	param.setOrderId("dd1111");
  	Pagination page = new Pagination();
  	page.setCurrentPage(1);
	page.setNumPerPage(10);

  	AfterSaleOrderResult result = client.queryAfterSaleOrder(param, page);
  	System.out.println(result);
  }
}
