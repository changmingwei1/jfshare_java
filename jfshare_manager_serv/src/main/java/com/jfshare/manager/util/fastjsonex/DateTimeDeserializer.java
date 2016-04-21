package com.jfshare.manager.util.fastjsonex;


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
 * @包路径  : com.cogtu.ad.util.fastjsonex 
 *				 
 * @版权所有: 北京知图科技有限责任公司 cogtu (C) 2015
 *
 * @类描述:  DateTime解析器
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年9月15日 - 下午11:55:20 
 *	
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
        }

        if (value instanceof Long) {
        	
        	return (T) new DateTime(value);
        	
        }
        
		
        throw new JSONException("DateTime parse error");
	}

}
