package com.jfshare.cart.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class PropertiesUtil {

	private PropertiesUtil() {
	}

	/**
	 * 获取properties文件属性值
	 * 
	 * @param propertiesFileName
	 * @param key
	 * @return
	 */
	public static String getProperty(String propertiesFileName, String key) {
		Properties props = new Properties();
		try {
			props.load(PropertiesUtil.class.getResourceAsStream("/" + propertiesFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (String) props.get(key);
	}

	/**
	 * 更新properties文件属性值
	 * 
	 * @param propertiesFileName
	 * @param key
	 * @param value
	 */
	public static void setProperty(String propertiesFileName, String key, String value) {
		Properties props = new Properties();
		OutputStream os = null;
		try {
			String classRootPath = PropertiesUtil.class.getResource("/").toString();

			if ("Windows".indexOf(System.getProperty("os.name")) != -1) {
				classRootPath = classRootPath.replace("file:/", "");
			} else {
				classRootPath = classRootPath.replace("file:", "");
			}
			props.load(PropertiesUtil.class.getResourceAsStream("/" + propertiesFileName));
			os = new FileOutputStream(classRootPath + propertiesFileName);
			props.put(key, value);
			props.store(os, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
}
