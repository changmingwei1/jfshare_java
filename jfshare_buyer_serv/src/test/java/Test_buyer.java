import com.jfshare.finagle.thrift.buyer.*;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.utils.CryptoUtil;

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

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_buyer {
    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private BuyerServ.Client client;

    //private  final String IP = "127.0.0.1";//"120.24.153.155"; //
    private  final String IP = "127.0.0.1";
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

//    @org.junit.Test
//    public void signin() throws TException {
//        try{
//            if (DO_IT == 0)
//                return;
//            System.out.println("start:" + System.currentTimeMillis());
//            for(int i=0; i<DO_IT; i++){
//                Buyer buyer = new Buyer();
//                buyer.setLoginName("18910573200");
//                buyer.setMobile("18910573200");
//                buyer.setPwdEnc("123456");
//
//                Result signin = client.newSignin(buyer);
//                System.out.println(signin);
//            }
//            System.out.println("end:" + System.currentTimeMillis());
//        }catch(Exception e){System.out.println(e);}
//    }
//

////
//    @org.junit.Test
//    public void isOnline() throws TException {
//        try{
//            if (DO_IT == 0)
//                return;
//            System.out.println("start:" + System.currentTimeMillis());
//            for(int i=0; i<DO_IT; i++){
//                Buyer buyer = new Buyer();
//                buyer.setLoginName("ssssss");
//                buyer.setPwdEnc("222");
//
//                LoginLog loginLog = new LoginLog();
//                loginLog.setUserId(2);
//                loginLog.setTokenId("a6f9e7ec-9f61-4f2e-9997-ac73c56411f7");
//                BuyerResult isOnline = client.isOnline(loginLog);
//                System.out.println(isOnline);
//            }
//            System.out.println("end:" + System.currentTimeMillis());
//        }catch(Exception e){System.out.println(e);}
//    }
//
//    @org.junit.Test
//    public void isBindThirdPartyTest() throws TException {
//        try{
//            if (DO_IT == 0)
//                return;
//            System.out.println("start:" + System.currentTimeMillis());
//            for(int i=0; i<DO_IT; i++){
//                LoginLog loginLog = new LoginLog();
//                loginLog.setUserId(2);
//                loginLog.setTokenId("a6f9e7ec-9f61-4f2e-9997-ac73c56411f7");
//                BuyerResult isOnline = client.isBindThirdParty("TY", loginLog);
//                System.out.println(isOnline);
//            }
//            System.out.println("end:" + System.currentTimeMillis());
//        }catch(Exception e){System.out.println(e);}
//    }
//
//    @org.junit.Test
//    public void signinThirdPartyTest() throws TException {
//        try{
//            if (DO_IT == 0)
//                return;
//            System.out.println("start:" + System.currentTimeMillis());
//            for(int i=0; i<DO_IT; i++){
//                LoginLog loginLog = new LoginLog();
////                loginLog.setUserId(2);
////                loginLog.setTokenId("a6f9e7ec-9f61-4f2e-9997-ac73c56411f7");
//                ThirdpartyUser thirdpartyUser = new ThirdpartyUser();
//                thirdpartyUser.setThirdType("H5_FOSHAN");
//                thirdpartyUser.setAccountNo("18610418281");
//                thirdpartyUser.setUserName("18610418281");
//                thirdpartyUser.setCustId("18610418281");
//
//                BuyerResult isOnline = client.signinThirdParty(loginLog, thirdpartyUser);
//                System.out.println(isOnline);
//            }
//            System.out.println("end:" + System.currentTimeMillis());
//        }catch(Exception e){System.out.println(e);}
//    }
//
//    public static void main(String[] args) {
//        System.err.println("guangdongdebo  " + CryptoUtil.encryptDBPwd("gddb123"));
//        System.err.println("shanghaituoying  "+ CryptoUtil.encryptDBPwd("shty123"));
//        System.err.println("deyixuan  "+ CryptoUtil.encryptDBPwd("dyx123"));
//        System.err.println("shanghaizhenyue  "+ CryptoUtil.encryptDBPwd("shzy123"));
//        System.err.println("shangshenggongmao  "+ CryptoUtil.encryptDBPwd("ssgm123"));
//    }


    
  @Test
  public void testgetBuyer(){
	  AuthInfo authInfo = new AuthInfo();
	  authInfo.setToken("0IV3Rf7n4ARYF3tKTbHMh3dWKjeNsUGxkpzwXDaC1e+llC2cSQOB2w==");
	  					 //l0COn8Fd0TCBijXqHweAg2LS+Wsi1nGkLNfCS62f9XynoR7WTgAa0w==
	  authInfo.setPpInfo("A2MjU4Mg==%MzUxNTYwMT");
	  
    
	  LoginLog loginLog = new LoginLog();
	  loginLog.setClientType(3);
	  loginLog.setBrowser("742095D3-09A5-400E-8AD5-65233340D636");
	  loginLog.setUserId(34);
			   
  	try {
			Result result = client.validAuth(loginLog, authInfo);
			System.out.println(result);
		} catch (TException e) {
			e.printStackTrace();
		}
  }
  

  
//  @Test
//  public void testgetBuyer(){
//	  Buyer buyer = new Buyer();
//	  buyer.setMobile("18301689968");
//	  buyer.setPwdEnc("111111");
//	  LoginLog loginLog = new LoginLog();
//	  loginLog.setBrowser("android#appVersion#1.0#ffffffff-b283-6f68-8a46-7b9a71334867]");
//			   
//  	try {
//			BuyerResult result = client.newLogin(buyer,loginLog);
//			System.out.println(result);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//  }
    
//  @Test
//  public void testgetBuyer(){
//	  LoginLog loginLog = new LoginLog();
//	  loginLog.setBrowser("android#appVersion#1.0#ffffffff-b283-6f68-8a46-7b9a71334867]");
//		
//	  ValidateInfo validateInfo=new ValidateInfo();
//	  validateInfo.setAccessToken("euSsy_796pcZTrNKsitvSdCCDvik5euHRyILlohP0p9xdbR_63VsKGaGZbE75bdiAgrCIVZ-7zaIe1txyiZ1BxUaxSCnE28d-6imZICUSvk");
//	  validateInfo.setCustId("oAAbTv3i0yL3wXvRy28f8EbZOYz01");
//	  validateInfo.setOpenId("oAAbTv3i0yL3wXvRy28f8EbZOYz0");
//	  validateInfo.setThirdType(1);
//	  
//  	try {
//			BuyerResult result = client.isExitsThirdUser(loginLog,validateInfo);
//			
//			System.out.println(result);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//  }
//  
//  @Test
//  public void testLogin(){
//	  UserInfoThird uit=new UserInfoThird();
//	  uit.setCustId("oAAbTv3i0yL3wXvRy28f8EbZOYz0");
//	  uit.setExtInfo("111111");
//	  uit.setMobile("13133333365");
//	  uit.setThirdType(2);
//	  
//	  LoginLog loginLog = new LoginLog();
//	  loginLog.setBrowser("android#appVersion#1.0#ffffffff-b283-6f68-8a46-7b9a71334867]");
//		
//	  ValidateInfo validateInfo=new ValidateInfo();
//	  validateInfo.setAccessToken("euSsy_796pcZTrNKsitvSdCCDvik5euHRyILlohP0p9xdbR_63VsKGaGZbE75bdiAgrCIVZ-7zaIe1txyiZ1BxUaxSCnE28d-6imZICUSvk");
//	  validateInfo.setCustId("oAAbTv3i0yL3wXvRy28f8EbZOYz01");
//	  validateInfo.setOpenId("oAAbTv3i0yL3wXvRy28f8EbZOYz0");
//	  validateInfo.setThirdType(1);
//	  validateInfo.setValiNum("121212");
//	  
//  	try {
//			BuyerResult result = client.thirdUserSignin(loginLog,uit,validateInfo);
//			
//			System.out.println(result);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//  }
//    @Test
//    public void getListBuyer(){
//  	
//  	  List<Integer> list = new ArrayList<Integer>();
//  	  list.add(1);
//  	  list.add(3);
//    	try {
//    		BuyerListResult result = client.getListBuyer(list);
//  			
//  			System.out.println(result);
//  		} catch (TException e) {
//  			e.printStackTrace();
//  		}
//    }
    
 /* @Test
  public void getAuthInfo(){
	  AuthInfo authInfo1 = new AuthInfo();
	  Buyer buyer = new Buyer();
	  buyer.setUserId(187);
	  buyer.setMobile("18910573200");
	  LoginLog loginLog = new LoginLog();
	  loginLog.setClientType(2);
	  
	  
  	try {
  		AuthInfoResult result = client.getAuthInfo(authInfo1,buyer,loginLog);
			
			System.out.println(result);
		} catch (TException e) {
			e.printStackTrace();
		}
  }*/
//  @org.junit.Test
//  public void newLogin() throws TException {
//     try{
//         if (DO_IT == 0)
//             return;
//         System.out.println("start:" + System.currentTimeMillis());
//         for(int i=0; i<DO_IT; i++){
//             Buyer buyer = new Buyer();
//             buyer.setMobile("18910573200");
//             buyer.setPwdEnc("123456");
//             //buyer.setState(88);
//             
//             LoginLog loginLog = new LoginLog();
//             loginLog.setClientType(4);
//             BuyerResult login = client.newLogin(buyer, loginLog);
//             System.out.println(login);
//         }
//         System.out.println("end:" + System.currentTimeMillis());
//     }catch(Exception e){System.out.println(e);}
// }  
//  
//  @Test
//  public void testUpdateUser(){
//  	Buyer buyer = new Buyer();
//  	buyer.setBirthday("2008-12-11");
//  	buyer.setSex(2);
//  	buyer.setUserId(187);
//  	buyer.setClientType(1);
//  	try {
//			Result result = client.updateBuyer(buyer);
//			System.out.println(result);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//  }
//  @Test
//  public void testgetBuyer(){
//  	Buyer buyer = new Buyer();
//  	buyer.setUserId(187);
//  	buyer.setClientType(1);
//  	try {
//			BuyerResult result = client.getBuyer(buyer);
//			System.out.println(result);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//  }
    
//  @Test
//public void testgetBuyer(){
//	  H5ThirdLoginParam param = new H5ThirdLoginParam();
//	  param.setAppCode("TEL");
//	  param.setSign("d367406f7b9954a1356ac5426a55ee23");
//	  param.setRequestDate("2016-08-23");
//	  param.setWayType("1");
//	  param.setMobile("18910573200");
//  	try {
//  		H5ThirdLoginResult result = client.H5ThirdLogin(param);
//			System.out.println(result);
//		} catch (TException e) {
//			e.printStackTrace();
//		}
//} 
}

