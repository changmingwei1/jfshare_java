package com.jfshare.express.Util.fastjsonex;

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
 * @包路径  : org.ljx.fastjsonex 
 *				 
 * @版权所有: 团博百众（北京）科技有限公司 (C) 2014
 *
 * @类描述:  DateTime的序列化器
 * 
 * @创建人:   liujinxin  
 *
 * @创建时间: 2015年2月3日 - 下午8:28:23 
 *
 * @修改记录:
   -----------------------------------------------------------------------------------------------
             时间						|		修改人		|		修改的方法		|		修改描述                                                                
   -----------------------------------------------------------------------------------------------
							|					|					|                                       
   ----------------------------------------------------------------------------------------------- 	
 
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
