package com.jfshare.express.kuaidi100.utils;

import com.jfshare.express.Util.MD5Util;
import com.jfshare.express.kuaidi100.pojo.TaskRequest;
import com.jfshare.express.kuaidi100.pojo.TaskResponse;
import org.joda.time.DateTime;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;


public class PostOrder {

	private void main1(String[] args){
		TaskRequest req = new TaskRequest();
		req.setCompany("tiantian");
		req.setFrom("温州");
		req.setTo("北京海淀区");
		req.setNumber("550285247179");
			req.getParameters().put("callbackurl", "http://120.24.153.102:13006/kuaidi/subscribe");
		req.setKey("OIUcmqpE5378");
		
		HashMap<String, String> p = new HashMap<String, String>(); 
		p.put("schema", "json");
		p.put("param", JacksonHelper.toJSON(req));
		try {
			String ret = HttpRequest.postData("http://www.kuaidi100.com/poll", p, "UTF-8");
			System.out.println(ret);
			TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);

			if(resp.getResult()==true){
				System.out.println("订阅成功");
			}else{
				System.out.println("订阅失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		HashMap<String, String> p = new HashMap<String, String>();
		long date_now = System.currentTimeMillis()/1000;
		String pwd = "587321";
		String pwdMD5 = "";
		String message = "";
		String messageStr = "您购买的商品返还200积分已到账，积分有效期到2016-12-31日，如有疑问请访问www.jfshare.com客服咨询";
		try {
			pwdMD5 = MD5Util.md5Encode(pwd+"_"+date_now+"_topsky");
			message = URLEncoder.encode("您购买的商品返还200积分已到账， 积分有效期到2016-12-31日， 请及时使用， 如有疑问请访问www.jfshare.com客服咨询", "gbk");
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.put("cpid", "13088");
		p.put("password", pwdMD5);
		p.put("channelid", "1462");
		p.put("msg", messageStr);
		p.put("tele","18610418281");
		p.put("timestamp", String.valueOf(date_now));

		try {
			String ret = HttpRequest.postData("http://admin.sms9.net/houtai/sms.php", p, "gbk");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
