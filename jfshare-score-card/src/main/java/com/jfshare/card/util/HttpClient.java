package com.jfshare.card.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {

	private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	private static class SingletonHolder {
		final static HttpClient instance = new HttpClient();
	}

	private HttpClient() {

	}

	public static HttpClient getInstance() {
		return SingletonHolder.instance;
	}

	public static HttpURLConnection getUrlConnection(String url, int bufferSize) throws Exception {
		URL target = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) target.openConnection();
		conn.setConnectTimeout(300000);
		conn.setReadTimeout(300000);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setChunkedStreamingMode(bufferSize);
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("contentType", "UTF-8");
		conn.setRequestProperty("timenow", String.valueOf(System.currentTimeMillis()));
		return conn;
	}

	public String sendPostReq(String url, String postXml, String contentType) {
		String uuid = System.currentTimeMillis() + "";
		logger.info("req_uuid_" + uuid + "_url：" + url + "_postJson:" + postXml + "_contentType:" + contentType);
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		OutputStreamWriter out = null;
		try {
			HttpURLConnection con = getUrlConnection(url, postXml.length());
			System.out.println(con);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", contentType);
			out = new OutputStreamWriter(con.getOutputStream());
			out.write(new String(postXml.getBytes("UTF-8")));
			out.flush();
			out.close();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes());
				sb.append(lines);
			}
			reader.close();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			logger.info("res_uuid_" + uuid + "_error:" + sw.toString());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (url != null && !url.contains("QueryComdodityList")) {
			logger.info("res_uuid_" + uuid + "_res:" + sb.toString());
		}

		return sb.toString();
	}
	public static void main(String args[]){
		HttpClient.getInstance().sendPostReq("http://127.0.0.1", "22222", "text/json");
	}
}
