package com.jfshare.manager.util.fastjsonex;


import java.io.IOException;
import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * *************************************************************************
 * @文件名称: DateTimeSerializer.java
 *
 * @包路径  : com.cogtu.ad.util.fastjsonex 
 *				 
 * @版权所有: 北京知图科技有限责任公司 cogtu (C) 2015
 *
 * @类描述:  DateTime的序列化器
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年9月16日 - 上午12:02:23 
 *	
 **************************************************************************
 */
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
