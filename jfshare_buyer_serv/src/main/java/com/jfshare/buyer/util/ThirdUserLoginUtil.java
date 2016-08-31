package com.jfshare.buyer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.text.MessageFormat;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.buyer.server.ServHandle;
import com.jfshare.utils.StringUtil;

@SuppressWarnings("deprecation")
public class ThirdUserLoginUtil {
	private static final transient Logger logger = LoggerFactory.getLogger(ThirdUserLoginUtil.class);
	private final static String VALIDATE_REQUEST_URL="https://api.weixin.qq.com/sns/auth?";
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	//如果校验成功返回true ，否则返回false
	public static boolean isValidateWX(String curt_id, String access_token){
		
		try{
			if(StringUtil.isNullOrEmpty(curt_id)||StringUtil.isNullOrEmpty(access_token)){
				return false;
			}
			
			StringBuffer strBuf=new StringBuffer();
			
			strBuf.append("access_token=");
			strBuf.append(access_token);
			strBuf.append("&openid=");
			strBuf.append(curt_id);
			
			String reUrl=VALIDATE_REQUEST_URL+strBuf.toString();
			
			logger.warn(MessageFormat.format("https请求串！reUrl[{0}]", reUrl));
			
			String responseStr = dohttpsSSLRequest(reUrl,null,null,null,DEFAULT_CHARSET);
			
			System.out.println(responseStr);
			JSONObject jsonRep=JSON.parseObject(responseStr);
			String code=jsonRep.get("errcode").toString();
			
			if("0".equals(code)){//校验成功
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//https请求：H5跨域问题
	public static String requestHttps(String reUrl, String extInfo) throws Exception{
		logger.warn(MessageFormat.format("+++++https请求串+++++++++,reUrl[{0}],extInfo[{1}]", reUrl,extInfo));
		 
		if(!StringUtils.isNotEmpty(extInfo)){
			extInfo=null;
		}else{
			extInfo = new String(extInfo.getBytes("UTF-8"), "ISO8859-1");
		}
		
		return dohttpsSSLRequest(reUrl,extInfo,null,null,DEFAULT_CHARSET);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		
		String data="{\"touser\":\"o4tVauAahjCiGD1waHEachCfVyyU\",\"template_id\":\"J89aK3fYvcxRNDYTpNJEGMPeY6Igf0Poz9NmOpqPfZI\",\"url\":\"http://www.baidu.com\",\"data\":{\"first\":{\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"水杯\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"1999.8元\",\"color\":\"#173177\"},\"keyword3\":{\"value\":\"2016年6月27日\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}}}";
		
		String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=uRx6-CX-3K7ZqKxZHoASWGnQCuYOaByk8CJIH32IJz-gn0f31bSkCQr2dsPF7IAM9MwqD0u9h5enAA7wGzk0nwrLVSkeuryKl1g83Hk_9m8QIVgACAJBO";
		System.out.println("---------------");
		String req=requestHttps(url,data);
//		
//		boolean flag=isValidateWX("oAAbTv3i0yL3wXvRy28f8EbZOYz0","euSsy_796pcZTrNKsitvSdCCDvik5euHRyILlohP0p9xdbR_63VsKGaGZbE75bdiAgrCIVZ-7zaIe1txyiZ1BxUaxSCnE28d-6imZICUSvk");
		System.out.println(req);
		
	}
	
	/**
	 * 执行http请求（带证书,使用传入的编码格式）
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestParam
	 *            请求报文
	 * @param keyID
	 *            证书KEY
	 * @param certPath
	 *            证书路径
	 * @param charset
	 *            编码格式
	 * @return 返回报文
	 */
	public static String dohttpsSSLRequest(String requestUrl, String requestParam, String certKey, String certPath, String charset) {

		String result = "";

		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			
			RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(10000)
					.setStaleConnectionCheckEnabled(true).build();

			HttpPost httpPost = new HttpPost(requestUrl);
			httpPost.setConfig(config);

			if (StringUtils.isNotBlank(requestParam)) {
				StringEntity reqEntity = new StringEntity(requestParam);
				reqEntity.setContentType("charset=" + charset);
				reqEntity.setContentEncoding(charset);
				httpPost.setEntity(reqEntity);
			}

			httpclient = createSSLClientDefault(certKey, certPath);	

			response = httpclient.execute(httpPost);


			HttpEntity entity = response.getEntity();
			StringBuffer sb = new StringBuffer();
			if (entity != null) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), charset));
				String text = null;
				while ((text = bufferedReader.readLine()) != null) {
					sb.append(text);
				}
			}
			result = sb.toString();
			EntityUtils.consume(entity);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 创建CloseableHttpClient
	 * 
	 * @param keyID
	 *            证书密钥
	 * @param keyPath
	 *            证书绝对路径
	 * @return CloseableHttpClient
	 */
	public static CloseableHttpClient createSSLClientDefault(String certKey, String certPath) {

		RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).setConnectionRequestTimeout(10000)
				.setStaleConnectionCheckEnabled(true).build();

		// 判断是否传入证书和密钥
		if (StringUtils.isNotBlank(certPath) && StringUtils.isNotBlank(certKey)) {
			try {
				FileInputStream instream = new FileInputStream(new File(certPath));
				KeyStore keyStore = KeyStore.getInstance("PKCS12");
				try {
					keyStore.load(instream, certKey.toCharArray());
				} finally {
					if (instream != null) {
						instream.close();
					}
				}

				SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, certKey.toCharArray()).loadTrustMaterial(null,
						new TrustSelfSignedStrategy()).build();

				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
				// 设置httpclient的SSLSocketFactory 

				return HttpClients.custom().setDefaultRequestConfig(config).setSSLSocketFactory(sslsf).build();
			} catch (Exception e) {
				e.printStackTrace();
				return HttpClients.custom().setDefaultRequestConfig(config).build();
			}
		} else {
			return HttpClients.custom().setDefaultRequestConfig(config).build();
		}
	}
}
