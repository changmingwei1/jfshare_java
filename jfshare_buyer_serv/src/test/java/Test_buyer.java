import com.jfshare.finagle.thrift.buyer.*;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.utils.CryptoUtil;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_buyer {
    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private BuyerServ.Client client;

    private  final String IP = "127.0.0.1";//"120.24.153.155"; //
//    private  final String IP = "120.24.153.155";//"120.24.153.155"; //

    private  final Integer PORT = 1990;


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
        client = new BuyerServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    @org.junit.Test
    public void signin() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                Buyer buyer = new Buyer();
                buyer.setLoginName("ssssss");
                buyer.setPwdEnc("222");

                Result signin = client.signin(buyer);
                System.out.println(signin);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
     public void login() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                Buyer buyer = new Buyer();
                buyer.setLoginName("111111");
                buyer.setPwdEnc("222222");

                LoginLog loginLog = new LoginLog();

                BuyerResult login = client.login(buyer, loginLog);
                System.out.println(login);
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
                Buyer buyer = new Buyer();
                buyer.setLoginName("ssssss");
                buyer.setPwdEnc("222");

                LoginLog loginLog = new LoginLog();
                loginLog.setUserId(2);
                loginLog.setTokenId("a6f9e7ec-9f61-4f2e-9997-ac73c56411f7");
                BuyerResult isOnline = client.isOnline(loginLog);
                System.out.println(isOnline);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void isBindThirdPartyTest() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                LoginLog loginLog = new LoginLog();
                loginLog.setUserId(2);
                loginLog.setTokenId("a6f9e7ec-9f61-4f2e-9997-ac73c56411f7");
                BuyerResult isOnline = client.isBindThirdParty("TY", loginLog);
                System.out.println(isOnline);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void signinThirdPartyTest() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                LoginLog loginLog = new LoginLog();
//                loginLog.setUserId(2);
//                loginLog.setTokenId("a6f9e7ec-9f61-4f2e-9997-ac73c56411f7");
                ThirdpartyUser thirdpartyUser = new ThirdpartyUser();
                thirdpartyUser.setThirdType("H5_FOSHAN");
                thirdpartyUser.setAccountNo("18610418281");
                thirdpartyUser.setUserName("18610418281");
                thirdpartyUser.setCustId("18610418281");

                BuyerResult isOnline = client.signinThirdParty(loginLog, thirdpartyUser);
                System.out.println(isOnline);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    public static void main(String[] args) {
        System.err.println("guangdongdebo  " + CryptoUtil.encryptDBPwd("gddb123"));
        System.err.println("shanghaituoying  "+ CryptoUtil.encryptDBPwd("shty123"));
        System.err.println("deyixuan  "+ CryptoUtil.encryptDBPwd("dyx123"));
        System.err.println("shanghaizhenyue  "+ CryptoUtil.encryptDBPwd("shzy123"));
        System.err.println("shangshenggongmao  "+ CryptoUtil.encryptDBPwd("ssgm123"));
    }
}
