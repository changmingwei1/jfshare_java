package com.jfshare.mybatis.generator.api;



import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public class IntrospectedColumn extends
		org.mybatis.generator.api.IntrospectedColumn {
	
	/**
	 *  Function:
	 *  @author : liujinxin  DateTime 2014年12月6日 下午10:49:07
	 *  功能说明：
	 *	使用说明：
	 *	返回类型: void  
	 *	@see org.mybatis.generator.api.IntrospectedColumn#setFullyQualifiedJavaType(org.mybatis.generator.api.dom.java.FullyQualifiedJavaType)
	 *  @param fullyQualifiedJavaType
	 */
	public void setFullyQualifiedJavaType(FullyQualifiedJavaType fullyQualifiedJavaType) {
		
		super.setFullyQualifiedJavaType(fullyQualifiedJavaType);
		
		if ((Types.DATE == getJdbcType()) && ("org.joda.time.DateTime".equals(fullyQualifiedJavaType.getFullyQualifiedName()))) {
			
			this.typeHandler = "com.tuan800.mybatis.typehandler.JodaDateTime2DateTypeHandler";
			
		} else if ((Types.TIMESTAMP == getJdbcType()) && ("org.joda.time.DateTime".equals(fullyQualifiedJavaType.getFullyQualifiedName()))) {
			
			this.typeHandler = "com.tuan800.mybatis.typehandler.JodaDateTime2TimestampTypeHandler";
			
		} else if ((Types.TIME == getJdbcType()) && ("org.joda.time.DateTime".equals(fullyQualifiedJavaType.getFullyQualifiedName()))) {
			
			this.typeHandler = "com.tuan800.mybatis.typehandler.JodaDateTime2TimeTypeHandler";
			
		}
	}
}