import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.seller.*;
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
 * Created by lenovo on 2015/9/28.
 */
public class TestClient {
    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private SellerServ.Client client;

    private  final String IP = "127.0.0.1";//"120.24.153.155"; //

    private  final Integer PORT = 1991;


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
        client = new SellerServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;
        System.out.println("end:" + System.currentTimeMillis());
        transport.close();
    }

    @org.junit.Test
    public void signup() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                Seller seller = new Seller();
                seller.setLoginName("jfshare");
                seller.setPwdEnc("123456");

                Result signup = client.signup(seller);
                System.out.println(signup);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void signin() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                Seller seller = new Seller();
                seller.setLoginName("jfshare");
                seller.setPwdEnc("123456");

                LoginLog loginLog = new LoginLog();

                SellerResult signin = client.signin(seller, loginLog);
                System.out.println(signin);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void isOnline() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                LoginLog loginLog = new LoginLog();
                loginLog.setSellerId(1);
                loginLog.setTokenId("daab3016-977f-4a61-9712-07a436f9b3c3");

                SellerResult isOnline = client.isOnline(loginLog);
                System.out.println(isOnline);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void querySeller() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                SellerRetParam param = new SellerRetParam();
                param.setBaseTag(1);

                SellerResult sellerResult = client.querySeller(1, param);
                System.out.println(sellerResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void querySellerBatch() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                SellerRetParam param = new SellerRetParam();
                param.setBaseTag(1);

                List<Integer> sellerIds = new ArrayList<Integer>();
                sellerIds.add(1);
                sellerIds.add(4);
                SellersResult sellersResult = client.querySellerBatch(sellerIds, param);
                System.out.println(sellersResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }
}
