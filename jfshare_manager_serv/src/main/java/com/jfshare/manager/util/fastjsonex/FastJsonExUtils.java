package com.jfshare.manager.util.fastjsonex;


import org.joda.time.DateTime;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * *************************************************************************
 * @文件名称: FastJsonExUtils.java
 *
 * @包路径  : com.cogtu.ad.util.fastjsonex 
 *				 
 * @版权所有: 北京知图科技有限责任公司 cogtu (C) 2015
 *
 * @类描述:  FastJson扩展工具
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年9月16日 - 上午12:02:31 
 *	
 **************************************************************************
 */
public final class FastJsonExUtils {

	private FastJsonExUtils(){}
	
	public static void initSupportDateTimeEx(){
		
		//fastjson 序列化配置 增加 org.joda.time.DateTime 序列化器
		SerializeConfig.getGlobalInstance().put(DateTime.class, DateTimeSerializer.instance);
		
		//fastjson 解析配置	增加 org.joda.time.DateTime 解析器
		ParserConfig.getGlobalInstance().putDeserializer(DateTime.class, DateTimeDeserializer.instance);
		
	}
}
