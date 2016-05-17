package com.jfshare.product.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author tsw
 */
public class HttpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	//http默认连接超时以及读超时时间
	public static final int DEFAULT_CONNECTION_TIMEOUT = 1000*10;
	public static final int DEFAULT_READ_TIMEOUT = 1000*10;
	public static final int MAX_TOTAL_CONNECTIONS = 500;
	public static final int DEFAULT_MAX_CONNECTIONSPERHOST = 200;
	public static final int IDLETHREAD_TIMEOUT_INTERVAL = 1000*150;
	public static final int IDLETHREAD_CONNECTION_TIMEOUT = 1000*150;
	
	private static final MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager(); 
	private static final HttpClient client = new HttpClient(connectionManager);
	static {
		
		/*System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");  
	    System.setProperty("org.apache.commons.logging.simplelog.showdatetime","true");  
	    System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "error");  */
		 
		
		client.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
		client.getHttpConnectionManager().getParams().setSoTimeout(DEFAULT_READ_TIMEOUT);
		client.getHttpConnectionManager().getParams().setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
		client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONSPERHOST);
		//检查 空闲连接线程
		IdleConnectionTimeoutThread idleThread = new IdleConnectionTimeoutThread(); 
		idleThread.setTimeoutInterval(IDLETHREAD_TIMEOUT_INTERVAL); 
		idleThread.setConnectionTimeout(IDLETHREAD_CONNECTION_TIMEOUT); 
		idleThread.addConnectionManager(connectionManager); 
		idleThread.start(); 
	}
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> params )
			throws Exception {
		return httpPost( url,  params,false);
	}
	public static String httpPostUTF8(String url, Map<String, String> params )
			throws Exception {
		return httpPostUTF8( url,  params,false);
	}
	/**
	 * 以HTTP post方式访问指定URL
	 * 
	 * @param url
	 *            访问URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应内容
	 * @throws Exception
	 */
	public static String httpPost(String url, Map<String, String> params,boolean keepLive)
			throws Exception {
		
		
		PostMethod post = new PostMethod(url);
		if(keepLive){
			post.setRequestHeader("Connection" , "Keep-Alive" ) ;
		}
		NameValuePair[] nameValuePairs = buildNameValuePairs(params);
		if (nameValuePairs != null) {
			post.setRequestBody(nameValuePairs);
		}
        try {
            post.getParams().setContentCharset("utf-8");
            client.executeMethod(post);
            if(post.getStatusCode() != HttpStatus.SC_OK){
                logger.error("<<<<<<<< httpPostUTF8 ----  HttpStatus 错误， HttpStatus : {}", post.getStatusCode());
                return null;
            }
            return post.getResponseBodyAsString();
        } catch (Exception ex) {
			logger.error("==访问[" + url + "]出现异常：" + ex.getMessage());
			throw new Exception("", ex);
		} finally {
			post.releaseConnection();
		}
	}

	public static String httpPostUTF8(String url, Map<String, String> params,boolean keepLive)
			throws Exception {
		
		
		PostMethod post = new PostMethod(url);
		if(keepLive){
			post.setRequestHeader("Connection" , "Keep-Alive" ) ;
		}
		//post.addRequestHeader("Content-Type","text/html;charset=UTF-8");  
		NameValuePair[] nameValuePairs = buildNameValuePairs(params);
		if (nameValuePairs != null) {
			post.setRequestBody(nameValuePairs);
		}
        try {
            post.getParams().setContentCharset("utf-8");
            client.executeMethod(post);
            if(post.getStatusCode() != HttpStatus.SC_OK){
                logger.error("<<<<<<<< httpPostUTF8 ----  HttpStatus 错误， HttpStatus : {}", post.getStatusCode());
                return null;
            }
            return post.getResponseBodyAsString();
        } catch (Exception ex) {
			logger.error("==访问[" + url + "]出现异常：" + ex.getMessage());
			throw new Exception("", ex);
		} finally {
			post.releaseConnection();
		}
	}


	public static String httpPost(String url, Map<String, String> params, String body) {
		return httpPost(url, params, body, null, null, DEFAULT_CONNECTION_TIMEOUT,
				DEFAULT_READ_TIMEOUT);
	}

	public static String httpPost(String url, Map<String, String> params, String body,
			String contentType, String charset, int connectionTimeout, int readTimeout) {
		
		PostMethod post = new PostMethod(url);
		NameValuePair[] nameValuePairs = buildNameValuePairs(params);
		if (nameValuePairs != null) {
			post.setQueryString(nameValuePairs);
		}
		try {
			post.setRequestEntity(new StringRequestEntity(body, contentType, charset));
			client.executeMethod(post);
			if(post.getStatusCode()>299){
				return null;
			}
			return post.getResponseBodyAsString();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.getStackTrace();
		} finally {
			post.releaseConnection();
		}
		return charset;
	}

	/**
	 * @see #httpPost(String, Map)
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String httpPost(String url) throws Exception {
		return httpPost(url, null);
	}

	// 辅助方法，根据给定键值对，构造请求参数数组
	private static NameValuePair[] buildNameValuePairs(Map<String, String> params) {
		NameValuePair[] data = null;
		if (params != null && params.size() > 0) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			data = new NameValuePair[entrySet.size()];

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : entrySet) {
				nameValuePairs.add(new NameValuePair(entry.getKey(), entry.getValue()));
			}
			nameValuePairs.toArray(data);
		}
		return data;
	}
	// POST请求
	public static String xmlHttpPost(String xmlRequest, String url) throws Exception{
		String resXml = "";
		// 创建HttpClient实例
		//HttpClient client = new HttpClient(connectionManager);
		// 创建PostMethod方法实例
		PostMethod method = new PostMethod(url);
		try {
			//method.setHttp11(true);
			// 指定请求内容的类型
			method.setRequestHeader("Content-type", "text/xml; charset=utf-8");
			// 添加请求参数
			method.setRequestEntity(new StringRequestEntity(xmlRequest, "text/xml", "utf-8"));
			// 使用系统提供的默认的恢复策略
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			int status = client.executeMethod(method);
			// 判断是否返回成功
			if (status == HttpStatus.SC_OK) {
				try {
					resXml = method.getResponseBodyAsString();
				} catch (Exception e) {
					logger.error("==访问[" + url + "]出现异常!" + e.getMessage());
					throw e;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
			//关闭连接。
			//((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();  

		}
		return resXml;
	}
	
	
	
	
	/**
	 * 添加http post请求
	 * @param map 请求对象key value
	 * @param url 请求的url
	 * */
	public static String postHttpInfo(Map<String,String> map,String url){
		String rslVal = "";
		HttpClient client = new HttpClient();
		client.getParams().setSoTimeout(10000);// 设置超时时间
		client.getParams().setContentCharset("UTF-8");
		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		HttpMethod post = getPostMethod(url,map);
		
		
		try {
			int statusCode = client.executeMethod(post);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Method faild:" + post.getStatusLine());
				// log.error("Method faild:" + post.getStatusLine());
			}
			byte[] responseBody = post.getResponseBody();
			rslVal = new String(responseBody, "UTF-8");
		} catch (HttpException e) {
			// log.error(e.getMessage());
		} catch (IOException e) {
			// log.error(e.getMessage());
		} finally {
			post.releaseConnection();
		}
		return rslVal;
	}
	
	
	private static HttpMethod getPostMethod(String postUrl, Map postMap) {
		PostMethod post = new PostMethod(postUrl);
		int pos = 0;
		NameValuePair[] namePairs = new NameValuePair[postMap.size()];
		Iterator keyIterator = postMap.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			namePairs[pos] = new NameValuePair(key, (String) postMap.get(key));
			pos++;
		}
		post.setRequestBody(namePairs);
		return post;
	}

}
