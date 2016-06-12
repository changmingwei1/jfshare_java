import com.jfshare.finagle.thrift.pay.PayReq;
import com.jfshare.finagle.thrift.pay.PayRes;
import com.jfshare.finagle.thrift.pay.PayServ;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.utils.CryptoUtil;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class TestThrift {
    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private PayServ.Client client;

    private  final String IP = "101.201.38.182";//"120.24.153.155";

    private  final Integer PORT = 1993;


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
        client = new PayServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    @org.junit.Test
    public void payUrl() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
//                params[[PayReq(tokenId:fvBLEJEHNOw=, orderNo:fa44514e9acb568385da064067e00feb, extraParam:24_4660024, title:聚分享订单, price:10, score:100, payChannel:1, payIp:null, returnUrl:, remark:null, custId:100017286150)]
                PayReq payReq = new PayReq();
                payReq.setTokenId("fvBLEJEHNOw=");
                payReq.setPayChannel(1);
                payReq.setOrderNo("9f7724514ddd94cf5a60352a2a01aa733");

                payReq.setPrice(1000);
                payReq.setScore(100);
                payReq.setPayChannel(1);
                payReq.setCustId("18979177165");
                payReq.setScore2cashAmount(100);
                payReq.setCustType("7");
                payReq.setProcustID("15");
                payReq.setTitle("jfx订单");
                StringResult stringResult = client.payUrl(payReq);
                System.out.println(stringResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void payRes() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                PayRes payRes = new PayRes();
                payRes.setPayChannel(2);
                payRes.setResUrl("{\"discount\":\"0.00\",\"payment_type\":\"1\",\"subject\":\"聚分享订单\",\"trade_no\":\"2016052221001004190216261526\",\"buyer_email\":\"huazhehuo110@163.com\",\"gmt_create\":\"2016-05-22 19:30:06\",\"notify_type\":\"trade_status_sync\",\"quantity\":\"1\",\"out_trade_no\":\"0c34b1140a80ab2c5d024213221d159b\",\"seller_id\":\"2088701691132875\",\"notify_time\":\"2016-05-22 19:44:51\",\"body\":\"订单支付\",\"trade_status\":\"TRADE_SUCCESS\",\"is_total_fee_adjust\":\"N\",\"total_fee\":\"0.01\",\"gmt_payment\":\"2016-05-22 19:30:07\",\"seller_email\":\"windy@jfshare.com\",\"price\":\"0.01\",\"buyer_id\":\"2088012954102192\",\"notify_id\":\"0e32c605099e9a23c7ad2886cf62b52hgu\",\"use_coupon\":\"N\",\"sign_type\":\"RSA\",\"sign\":\"aNrgIFblGOtum6pN6MyuRE8L3b9QlzICJzBkKt8xwU4iv4JJ3M+poW7mq66WdV3LWaBf2SwclGYugUBtKkfmxe5sVZ7XSCbsCrISUIZ1kuP3qCPr9qaOgAr4MkJr/elC47aGKMw5BUNRkAj32XdEYIKZGDfcVXXII8VSOd0xczI=\"}");

                StringResult stringResult = client.payNotify(payRes);
                System.out.println(stringResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }
}
