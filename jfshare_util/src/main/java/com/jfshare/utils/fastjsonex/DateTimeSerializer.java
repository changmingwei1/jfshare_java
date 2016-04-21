package com.jfshare.utils.fastjsonex;

import java.io.IOException;
import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class DateTimeSerializer implements ObjectSerializer {

	public final static DateTimeSerializer instance = new DateTimeSerializer();

	@Override
	public void write(JSONSerializer serializer, Object object,
			Object fieldName, Type fieldType, int features) throws IOException {
		
        SerializeWriter out = serializer.getWriter();

        if (object == null) {
            out.writeNull();
            return;
        }
        
        String dateTimeMillis = String.valueOf(((DateTime)object).getMillis());

        if (out.isEnabled(SerializerFeature.WriteClassName)) {
            if (object.getClass() != fieldType) {
                out.write('{');
                out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                serializer.write(object.getClass().getName());
                out.writeFieldValue(',', "val", dateTimeMillis);
                out.write('}');
                return;
            }
        }
        
        out.writeString(dateTimeMillis);
		
	}

}
