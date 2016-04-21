import com.jfshare.finagle.thrift.manager.Commissioner;
import com.jfshare.finagle.thrift.manager.CommissionerResult;
import com.jfshare.finagle.thrift.manager.LoginLog;
import com.jfshare.finagle.thrift.manager.ManagerServ;
import com.jfshare.finagle.thrift.manager.QuerySlotImageParam;
import com.jfshare.finagle.thrift.manager.SlotImage;
import com.jfshare.finagle.thrift.result.Result;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lenovo on 2015/9/28.
 */
public class TestUserClient {
    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private ManagerServ.Client client;

    private  final String IP = "127.0.0.1";//"120.24.153.155"; //

    private  final Integer PORT = 1988;


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
        client = new ManagerServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;
        System.out.println("end:" + System.currentTimeMillis());
        transport.close();
    }

    //@org.junit.Test
    public void signup() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                Commissioner commissioner = new Commissioner();
                commissioner.setLoginName("jfshare");
                commissioner.setPwdEnc("123456");

                Result signup = client.signup(commissioner);
                System.out.println(signup);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    //@org.junit.Test
    public void signin() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                Commissioner commissioner = new Commissioner();
                commissioner.setLoginName("jfx001");
                commissioner.setPwdEnc("123456");

                LoginLog loginLog = new LoginLog();

                CommissionerResult signin = client.signin(commissioner, loginLog);
                System.out.println(signin);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }

    //@org.junit.Test
    public void isOnline() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                LoginLog loginLog = new LoginLog();
                loginLog.setCsId(1);
                loginLog.setTokenId("c868c65d-d7b7-4720-b4c8-b989b88f1c5a");

                CommissionerResult isOnline = client.isOnline(loginLog);
                System.out.println(isOnline);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }
    
    
    
    @Test
    public void saveImage(){
        //商品id
    	SlotImage slotImage = new SlotImage();
    	slotImage.setImgKey("2131zxczxcasdasd");
    	slotImage.setJump("22222czxczczxzxc222222");
    	slotImage.setType(1);
    	
        try {
        	client.saveSlotImage(slotImage);

            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }
    
    @Test
    public void queryImage(){
    	try {
    		QuerySlotImageParam querySlotImageParam = new QuerySlotImageParam();
    		querySlotImageParam.setType(1);
        	System.out.println(client.querySlotImageList(querySlotImageParam));

            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }
    
    //@Test
    public void updateImage(){
    	SlotImage slotImage = new SlotImage();
    	slotImage.setId(1);
    	slotImage.setImgKey("2222222222222");
    	//slotImage.setJump("22222222222");
    	slotImage.setType(2);
    	
        try {
        	client.updateSlotImage(slotImage);

            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }
}
