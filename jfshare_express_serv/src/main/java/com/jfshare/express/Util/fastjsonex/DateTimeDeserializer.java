package com.jfshare.express.Util.fastjsonex;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * *************************************************************************
 * @文件名称: DateTimeDeserializer.java
 *
 * @包路径  : org.ljx.fastjsonex 
 *				 
 * @版权所有: 团博百众（北京）科技有限公司 (C) 2014
 *
 * @类描述:  DateTime解析器
 * 
 * @创建人:   liujinxin  
 *
 * @创建时间: 2015年2月3日 - 下午8:28:50 
 *
 * @修改记录:
   -----------------------------------------------------------------------------------------------
             时间						|		修改人		|		修改的方法		|		修改描述                                                                
   -----------------------------------------------------------------------------------------------
							|					|					|                                       
   ----------------------------------------------------------------------------------------------- 	
 
 **************************************************************************
 */
public class DateTimeDeserializer extends AbstractDateDeserializer implements ObjectDeserializer{

    public final static DateTimeDeserializer instance = new DateTimeDeserializer();
    
	@Override
	public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T cast(DefaultJSONParser parser, Type clazz,
			Object fieldName, Object value) {
        
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            
            return (T) new DateTime(Long.parseLong(strVal));
        } else if (value instanceof Long) {
        	
        	return (T) new DateTime(value);
        	
        }

        throw new JSONException("DateTime parse error");
	}

}
