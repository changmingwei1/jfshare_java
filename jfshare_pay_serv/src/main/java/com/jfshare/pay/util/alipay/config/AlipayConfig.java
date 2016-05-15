package com.jfshare.pay.util.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088701691132875";
	
	// 收款支付宝账号，一般情况下收款账号就是签约账号
	public static String seller_email = "windy@jfshare.com";
	// 商户的私钥
	public static String key = "err1r74okaardf0zrm3cir1o1zznrvzp";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
//	public static String sign_type = "MD5";


	public static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?" +
			"_input_charset=" + AlipayConfig.input_charset;

	/**
	 * RSA私钥
	 */
	public static final String ALIPAY_RSA_PRIVATE = "MIICXAIBAAKBgQDGZlD7QjGX5l2GYhqEoVLG+pV9OWQsU0sqvYk8irAbTe1+wcP5\n" +
			"04U9aVP8zSfJr0yX5/UY+QxNmCYH5xWmcc2YkfZlmFzs6FHhNPTbjHqejDQmDaKj\n" +
			"y2ABzw8ky2R8rjYFemQgS8k9Dcd6UvTP/BVIab0G2Q6AWFda4+Zlm1Tv5QIDAQAB\n" +
			"AoGAWxRBfPdIWRlyOezzi4ubuqjy04fzSr36NebJB7gLXJ2Lt7MwSpImCqk920I7\n" +
			"T8Vf/iSISiyaz0xXuS0GrWB1JxR8wBVMGDexN844IkE/utp5gOQ14z7PHl8nwmWS\n" +
			"tDg8vH7j4alouCnpOUbKxm13NNYBvTXRPu353uGMCdlk2MkCQQDjgNdwPhkhiI0l\n" +
			"Za8onKc7CPS7IMHmVYZZ1tYvOyGVjkf5CJPEmIhH5EILy4T92hMQIYFnsy1D1GkU\n" +
			"JUlVEk8bAkEA30A7wXeQB7bm+x8OOP/HnTxRa6LZxW+GPrGL2JUqd9y1735/vJbR\n" +
			"K6SIakBxo367mAHW5Od+lZ6Oq5m8d/as/wJBAMNWaiTOvOdFBuQYVfgJ4bDXtMJD\n" +
			"kxlBT9UtmMP/AowWGYgjLDV+Pm1z+J2GBs+sAfg0vJaciv4VkI+ioD9yNAcCQD/D\n" +
			"Os3w9iQd6y7CGe/RrhOMjLyvUH+BcNgrIYQEZpLBaP00JguZD7jlT0j5ivtlUPMD\n" +
			"Y3sRWygklx8P07J3s8UCQBcG6bYXhl0eYG+tqN0D385m2zL7f0pP5jcUv1kh1eZK\n" +
			"hzzRcaDDY4SBsgZqVXtJARPldPwucSD2edfR2o6iNaw=";

	//"lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D";

	/**
	 * 以下为商户配置参数
	 */
//	public static String notify_url = "http://120.24.153.102:13005/pay/notify/alipay"; //后端支付通知地址
//	public static String return_url = "http://120.24.153.102:13003/pay/jump/alipay"; //前端支付通知地址
}
