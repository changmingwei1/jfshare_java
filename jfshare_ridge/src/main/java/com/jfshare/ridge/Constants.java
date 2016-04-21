package com.jfshare.ridge;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	/**
	 * 配置项在zookeeper的根目录
	 */
	public static final String ZK_ROOT_PATH = "/.ridge";

	public static final String ZK_FILE_SNAPSHOT_PATH;// Constants.getClassPath()
																	// +
																	// "/zk_snapshot";//"/data/zk_snapshot";

	/**
	 * 如果dns解析失败在会加载该默认配置
	 */
	public static final String DEFAULT_PRODUCTION_ZKADDRESS = "120.24.153.155:2181";
	
	/**
	 * 获取zk地址的域名
	 */
	public static final String ZK_DNS_URL = "zk.jfshare.com";
	
	public static final String ZK_LOG_LEVEL_ERROR = "error";
	
	public static final String ZK_LOG_LEVEL_INFO = "info";
	
	public static final String ZK_LOG_LEVEL_DEBUG = "debug";
	
	protected static final Logger logger = LoggerFactory
            .getLogger(Constants.class);
	
	
	//因为测试环境负载, 兼容开发用户是windows机器时不能生成文件快照的问题
	static{
		String path = System.getProperty("user.home") + ZK_ROOT_PATH + "/zk_snapshot";
		String osFlag = System.getProperty("path.separator");
		//根据系统分隔符判断系统类型 ,如果是 : 则为linux  如果是 ; 则是windows
		if (osFlag != null && ";".equals(osFlag)) {//windows系统
			path = ZK_ROOT_PATH + "/zk_snapshot";
			logger.info(Constants.formatLogMsg("The operating system is WINDOWS, file snapshot path is "+ path, Constants.ZK_LOG_LEVEL_INFO));
		}else{
			logger.info(Constants.formatLogMsg("The operating system is LINUX, file snapshot path is "+ path, Constants.ZK_LOG_LEVEL_INFO));
		}
		ZK_FILE_SNAPSHOT_PATH = path;
	}
	
	
	/**
	 * 格式化日志文件
	 * 
	 * @param logMsg
	 * @return
	 */
	public static String formatLogMsg(String logMsg,String logLevel) {
		String formatStr = "";
		if (logMsg != null) {
			if(logLevel.equals(ZK_LOG_LEVEL_ERROR)){//异常日志
				formatStr = "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$".concat(logMsg).concat("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}
			if(logLevel.equals(ZK_LOG_LEVEL_INFO)){//info日志
				formatStr = "**********".concat(logMsg).concat("**********");
			}
			if(logLevel.equals(ZK_LOG_LEVEL_DEBUG)){//debug日志
				formatStr = "==========".concat(logMsg).concat("==========");
			}
		}
		return formatStr;
	}

	public static String getZkAddress() {
		String result = "";
		List<String> serverList = new ArrayList<String>();
		try {
			InetAddress[] inetHosts = InetAddress
					.getAllByName(ZK_DNS_URL);
			for (InetAddress host : inetHosts) {
				
				if (host.getHostAddress().length() <= 15) {
					String ip = host.getHostAddress().concat(":2181");
					serverList.add(ip);
				}
			}
		} catch (UnknownHostException ex) {
			
			logger.error(formatLogMsg("Parse ZOOKEEPER_DNS_URL:[zk.jfshare.com] error, Will loading default zookeeper address of production "+ DEFAULT_PRODUCTION_ZKADDRESS, Constants.ZK_LOG_LEVEL_ERROR),ex);
			//如果dns解析有问题, 我们为了保证生产可以使用,返回生产的默认地址
			return DEFAULT_PRODUCTION_ZKADDRESS;
		}
		result = org.apache.commons.lang.StringUtils.join(serverList.toArray(),",");
		return result;
	}

	public static void main(String[] args) {
		String str = System.getProperty("user.home");
		System.out.println(str +","+ Constants.getZkAddress());
	}

}
