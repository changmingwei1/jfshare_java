package com.jfshare.message.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.IdleConnectionTimeoutThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUtil {
	private static final Log logger = LogFactory.getLog(HttpUtil.class);

	public static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 10;
	public static final int DEFAULT_READ_TIMEOUT = 1000 * 10;
	public static final int MAX_TOTAL_CONNECTIONS = 500;
	public static final int DEFAULT_MAX_CONNECTIONSPERHOST = 200;
	public static final int IDLETHREAD_TIMEOUT_INTERVAL = 1000 * 150;
	public static final int IDLETHREAD_CONNECTION_TIMEOUT = 1000 * 150;

	private static final MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	private static final HttpClient client = new HttpClient(connectionManager);
	static {

		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "error");

		client.getHttpConnectionManager().getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
		client.getHttpConnectionManager().getParams().setSoTimeout(DEFAULT_READ_TIMEOUT);
		client.getHttpConnectionManager().getParams().setMaxTotalConnections(MAX_TOTAL_CONNECTIONS);
		client.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTIONSPERHOST);

		IdleConnectionTimeoutThread idleThread = new IdleConnectionTimeoutThread();
		idleThread.setTimeoutInterval(IDLETHREAD_TIMEOUT_INTERVAL);
		idleThread.setConnectionTimeout(IDLETHREAD_CONNECTION_TIMEOUT);
		idleThread.addConnectionManager(connectionManager);
		idleThread.start();
	}

	public static int jsonHttpPost(String jsonRequest, String url) {
		
		int status = 200;
		PostMethod method = new PostMethod(url);
		try {
			String keyStr = MessageConfig.JPUSH_PUSH_APPKEY + ":" + MessageConfig.JPUSH_PUSH_MASTER_SECRET;
			String key = Base64.encodeBase64String(keyStr.getBytes("UTF-8"));
			
			method.setRequestHeader("Content-type", "application/json; charset=utf-8");
			method.setRequestHeader("Authorization", "Basic " + key);
			method.setRequestEntity(new StringRequestEntity(jsonRequest, "application/json", "utf-8"));
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

			status = client.executeMethod(method);
			logger.info("--------pushMessage-----jsonRequest:" + jsonRequest);
			logger.info("--------pushMessage-----url:" + url);
			logger.info("--------pushMessage-----status:" + status);
			if (status == HttpStatus.SC_OK) {
				try {
					String res = method.getResponseBodyAsString();
					logger.info("response:" + res);
				} catch (Exception e) {
					logger.error("failed url:" + url, e);
					throw e;
				}
			}
		} catch (Exception e) {
			logger.error("error:", e);
			return 500;
		} finally {
			method.releaseConnection();
		}
		return status;
	}
}
