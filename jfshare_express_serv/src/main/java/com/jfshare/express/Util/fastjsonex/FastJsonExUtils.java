package com.jfshare.express.Util.fastjsonex;

import org.joda.time.DateTime;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * *************************************************************************
 * @文件名称: FastJsonExUtils.java
 *
 * @包路径  : com.zhe800.order.assist.utils.fastjsonex 
 *				 
 * @版权所有: 团博百众（北京）科技有限公司 (C) 2014
 *
 * @类描述:  FastJson扩展工具
 * 
 * @创建人:   liujinxin  
 *
 * @创建时间: 2015年2月5日 - 上午10:16:32 
 *
 * @修改记录:
   -----------------------------------------------------------------------------------------------
             时间						|		修改人		|		修改的方法		|		修改描述                                                                
   -----------------------------------------------------------------------------------------------
							|					|					|                                       
   ----------------------------------------------------------------------------------------------- 	
 
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
