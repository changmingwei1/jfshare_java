package com.jfshare.utils.fastjsonex;

import org.joda.time.DateTime;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
fastJson 自定义 DateTime类型转换为String格式
 应用使用前需FastJsonExUtils.initSupportDateTimeEx();来使之生效
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
