package com.jfshare.mybatis.generator.types;


import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.DateTime;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

public class JavaTypeResolverImpl implements JavaTypeResolver {
	
	protected List<String> warnings;
	protected Properties properties;
	protected Context context;
	protected boolean forceBigDecimals;
	protected Map<Integer, JdbcTypeInformation> typeMap;

	public JavaTypeResolverImpl() {
		
		this.properties = new Properties();
		this.typeMap = new HashMap<Integer, JdbcTypeInformation>();

		this.typeMap.put(Integer.valueOf(Types.ARRAY), new JdbcTypeInformation("ARRAY", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.BIGINT), new JdbcTypeInformation("BIGINT", new FullyQualifiedJavaType(Long.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.BINARY), new JdbcTypeInformation("BINARY", new FullyQualifiedJavaType("byte[]")));
		this.typeMap.put(Integer.valueOf(Types.BIT), new JdbcTypeInformation("BIT", new FullyQualifiedJavaType(Boolean.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.BLOB), new JdbcTypeInformation("BLOB", new FullyQualifiedJavaType("byte[]")));
		this.typeMap.put(Integer.valueOf(Types.BOOLEAN), new JdbcTypeInformation("BOOLEAN", new FullyQualifiedJavaType(Boolean.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.CHAR), new JdbcTypeInformation("CHAR", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.CLOB), new JdbcTypeInformation("CLOB", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.DATALINK), new JdbcTypeInformation("DATALINK", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.DISTINCT), new JdbcTypeInformation("DISTINCT", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.DOUBLE), new JdbcTypeInformation("DOUBLE", new FullyQualifiedJavaType(Double.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.FLOAT), new JdbcTypeInformation("FLOAT", new FullyQualifiedJavaType(Double.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.INTEGER), new JdbcTypeInformation("INTEGER", new FullyQualifiedJavaType(Integer.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.JAVA_OBJECT), new JdbcTypeInformation("JAVA_OBJECT", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.LONGVARBINARY), new JdbcTypeInformation( "LONGVARBINARY", new FullyQualifiedJavaType("byte[]")));
		this.typeMap.put(Integer.valueOf(Types.LONGVARCHAR), new JdbcTypeInformation("LONGVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.NCHAR), new JdbcTypeInformation("NCHAR", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.NCLOB), new JdbcTypeInformation("NCLOB", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.NVARCHAR), new JdbcTypeInformation("NVARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.NULL), new JdbcTypeInformation("NULL", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.OTHER), new JdbcTypeInformation("OTHER", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.REAL), new JdbcTypeInformation("REAL", new FullyQualifiedJavaType(Float.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.REF), new JdbcTypeInformation("REF", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.SMALLINT), new JdbcTypeInformation("SMALLINT", new FullyQualifiedJavaType(Short.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.STRUCT), new JdbcTypeInformation("STRUCT", new FullyQualifiedJavaType(Object.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.TINYINT), new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Byte.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.VARBINARY), new JdbcTypeInformation("VARBINARY", new FullyQualifiedJavaType("byte[]")));
		this.typeMap.put(Integer.valueOf(Types.VARCHAR), new JdbcTypeInformation("VARCHAR", new FullyQualifiedJavaType(String.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.DATE), new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(DateTime.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.TIME), new JdbcTypeInformation("TIME", new FullyQualifiedJavaType(DateTime.class.getName())));
		this.typeMap.put(Integer.valueOf(Types.TIMESTAMP), new JdbcTypeInformation("TIMESTAMP", new FullyQualifiedJavaType(DateTime.class.getName())));
	
	}

	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);

		this.forceBigDecimals = StringUtility.isTrue(properties.getProperty("forceBigDecimals"));
	}

	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		JdbcTypeInformation jdbcTypeInformation = (JdbcTypeInformation) this.typeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
		FullyQualifiedJavaType answer;
		if (jdbcTypeInformation == null) {
			switch (introspectedColumn.getJdbcType()) {
			case 2:
			case 3:
				if ((introspectedColumn.getScale() > 0)
						|| (introspectedColumn.getLength() > 18)
						|| (this.forceBigDecimals)) {
					answer = new FullyQualifiedJavaType(
							BigDecimal.class.getName());
				} else {
					if (introspectedColumn.getLength() > 9) {
						answer = new FullyQualifiedJavaType(
								Long.class.getName());
					} else {
						if (introspectedColumn.getLength() > 4) {
							answer = new FullyQualifiedJavaType(
									Integer.class.getName());
						} else {
							answer = new FullyQualifiedJavaType(
									Short.class.getName());
						}
					}
				}
				break;
			default:
				answer = null;
				break;
			}
		} else {
			answer = jdbcTypeInformation.getFullyQualifiedJavaType();
		}
		return answer;
	}

	public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
		JdbcTypeInformation jdbcTypeInformation = (JdbcTypeInformation) this.typeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
		String answer;
		if (jdbcTypeInformation == null) {
			switch (introspectedColumn.getJdbcType()) {
			case 3:
				answer = "DECIMAL";
				break;
			case 2:
				answer = "NUMERIC";
				break;
			default:
				answer = null;
				break;
			}
		} else {
			answer = jdbcTypeInformation.getJdbcTypeName();
		}
		return answer;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	protected static class JdbcTypeInformation {
		
		private String jdbcTypeName;
		private FullyQualifiedJavaType fullyQualifiedJavaType;

		public JdbcTypeInformation(String jdbcTypeName, FullyQualifiedJavaType fullyQualifiedJavaType) {
			this.jdbcTypeName = jdbcTypeName;
			this.fullyQualifiedJavaType = fullyQualifiedJavaType;
		}

		public String getJdbcTypeName() {
			return this.jdbcTypeName;
		}

		public FullyQualifiedJavaType getFullyQualifiedJavaType() {
			return this.fullyQualifiedJavaType;
		}
	}
}
