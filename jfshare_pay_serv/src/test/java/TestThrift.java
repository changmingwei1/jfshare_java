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

    private  final String IP = "127.0.0.1";//"120.24.153.155";

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
                payReq.setExtraParam("24_4660024");
                payReq.setCustId("100017286150");
                payReq.setCustType("7");
                payReq.setPayChannel(1);
                payReq.setOrderNo("fa44514e9acb568385da064067e00feb");
                payReq.setPrice(3000);
                payReq.setScore2cashAmount(1000);
                payReq.setScore(100);
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
                payRes.setPayChannel(1);
                payRes.setResUrl("xxxxxx");
                StringResult stringResult = client.payNotify(payRes);
                System.out.println(stringResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }
}
