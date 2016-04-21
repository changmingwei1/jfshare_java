package com.jfshare.utils.fastjsonex;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

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
