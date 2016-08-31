package com.jfshare.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.jfshare.finagle.thrift.message.AppUpgradeInfo;
import com.jfshare.finagle.thrift.message.AppUpgradeResult;
import com.jfshare.finagle.thrift.message.GetUpgradeParam;
import com.jfshare.finagle.thrift.message.MessageServ;
import com.jfshare.finagle.thrift.message.PushMessage;
import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.message.util.HttpUtil;
import com.jfshare.message.util.MessageConfig;
import com.jfshare.utils.DateUtils;

public class MessageTest {
	// 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private MessageServ.Client client;

    private static final String IP = "127.0.0.1";
//    private static final String IP = "120.24.153.102";

    private static final Integer PORT = 2001;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new MessageServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }
    
//    @Test
//    public void testaddMessage() throws Exception {
//    	SystemMessage message = getMessage();
//        Result s = client.addSystemMessage(message);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testupdateMessage() throws Exception {
//    	SystemMessage message = getMessage();
//    	message.setId(1);
//        Result s = client.updateSystemMessage(message);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testgetMessageAll() throws Exception {
//    	
//    	SystemMessageResult s = client.getSystemMessage(null);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testgetMessageById() throws Exception {
//    	SystemMessage message = new SystemMessage();
//    	message.setId(1);
//    	SystemMessageResult s = client.getSystemMessage(message);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testgetMessageByStatus() throws Exception {
//    	SystemMessage message = new SystemMessage();
//    	message.setStatus(2);
//    	SystemMessageResult s = client.getSystemMessage(message);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testgetMessageByTitle() throws Exception {
//    	SystemMessage message = new SystemMessage();
//    	message.setTitle("675055343b0b");
//    	SystemMessageResult s = client.getSystemMessage(message);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testdeleteMessage() throws Exception {
//        Result s = client.deleteSystemMessage(1);
//        System.err.println(s);
//    }
    
//    @Test
    public void testpushMessage() throws Exception {
//    	if (DO_IT == 0)
//            return;
//    	JSONObject extras = new JSONObject();
//		extras.put("obj_type", "message");
//		extras.put("content", "ddd test zsh");
//		extras.put("title", "zsh");
//		
//		Map<String, String> notification = new HashMap<String, String>();
//		notification.put("alert", "test 111111");
//		notification.put("extras", extras.toJSONString());
//		
//		Map<String, Boolean> options = new HashMap<String, Boolean>();
//		options.put("apns_production", MessageConfig.JPUSH_PUSH_IS_PRODUCTION);
//		
//		JSONObject json = new JSONObject();
//		json.put("platform", MessageConfig.JPUSH_PUSH_PLATFORM);
//		json.put("audience", MessageConfig.JPUSH_PUSH_AUDIENCE);
//		json.put("notification", notification);
//		json.put("options", options);
    	
    	PushMessage pm=new PushMessage();
    	pm.setTitle("测试title");
    	pm.setContent("测试content");
    	pm.setObjType(0);
    	pm.setAlert("测试alert");
		
    	 Result s = client.sendPush(pm);
    	 System.err.println(s);
    	 
    	 
//		System.out.println(HttpUtil.jsonHttpPost(json.toJSONString(), MessageConfig.JPUSH_VALIDATE_URL));//校验
////		System.out.println("=============:"+json.toString());
		
//        System.err.println(json.toJSONString());
    }
    
//    @Test
//    public void testAddAppUpgrade() throws Exception {
//    	if (DO_IT == 0)
//            return;
//    	AppUpgradeInfo info = new AppUpgradeInfo();
//    	info.setAppType(1);
//    	info.setMaxVersion(3);
//    	info.setMinVersion(1);
//    	info.setUpgradeType(1);
//    	info.setUrl("www.baidu.com");
//    	info.setVersion(5);
//    	info.setUpgradeDesc("ddddddd");
//        Result s = client.addAppUpgradeInfo(info);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testUpdateAppUpgrade() throws Exception {
//    	if (DO_IT == 0)
//            return;
//    	AppUpgradeInfo info = new AppUpgradeInfo();
//    	info.setAppType(2);
//    	info.setMaxVersion(3);
//    	info.setMinVersion(1);
//    	info.setUpgradeType(1);
//    	info.setUrl("www.baidu.com");
//    	info.setVersion(5);
//    	info.setUpgradeDesc("ddddddd");
//        Result s = client.updateAppUpgradeInfo(info);
//        System.err.println(s);
//    }
//    
//    @Test
//    public void testGetAppUpgrade() throws Exception {
//    	if (DO_IT == 0)
//            return;
//    	GetUpgradeParam param = new GetUpgradeParam();
//    	param.setAppType(1);
//    	param.setVersion(2);
//    	AppUpgradeResult s = client.getAppUpgradeInfo(param);
//        System.err.println(s);
//    }
//    
//    private SystemMessage getMessage(){
//    	SystemMessage message = new SystemMessage();
//    	message.setTitle(UUID.randomUUID().toString());
//    	message.setContent(UUID.randomUUID().toString());
//    	message.setBeginDate(DateUtils.date2Str(DateUtils.getNow()));
//    	message.setEndDate(DateUtils.date2Str(DateUtils.addDay(DateUtils.getNow(), 10)));
//    	message.setPushTarget(0);
//    	return message;
//    }
//    @Test
  public void getMessage() throws TException{
  	if (DO_IT == 0)
    return;
	PushMessage info = new PushMessage();
	info.setAlert("您有1笔订单已发货，订单号*********，查看详情");
	info.setContent("您有1笔订单已发货，订单号*********，查看详情");
	info.setObjType(1);//1 order 2 url 0 message
	info.setTitle("发货通知");
	Result s = client.pushMessageInfo("131",info,"40");
	System.err.println(s);
}
}
