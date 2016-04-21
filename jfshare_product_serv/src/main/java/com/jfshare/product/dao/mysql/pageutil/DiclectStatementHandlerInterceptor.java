package com.jfshare.product.dao.mysql.pageutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.product.ProductSurveyQueryParam;
import com.jfshare.product.util.ReflectUtil;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class DiclectStatementHandlerInterceptor implements Interceptor {

	private static String SQLDialect = "";// plugin中配置数据库的方言
	private static String pageSqlId = ""; // mapper.xml中需要拦截的ID(正则匹配)

	/**
	 * 拦截器 拦截原sql，进行修改
	 */
	public Object intercept(Invocation invocation) throws Throwable {

		RoutingStatementHandler statement = (RoutingStatementHandler) invocation.getTarget();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil.getFieldValue(statement, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
		
		// 正则匹配需要拦截的方法的id
		if (mappedStatement.getId().matches(pageSqlId)) {
			BoundSql boundSql = statement.getBoundSql();
			// 获得Pagination参数
			// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
			
			Object parameterObject = boundSql.getParameterObject();
			
			if (parameterObject == null || !(parameterObject instanceof ProductSurveyQueryParam)) {
				
				throw new NullPointerException("QueryParam parameterObject is not found!");
				
			} else {
				
				ProductSurveyQueryParam queryParam = (ProductSurveyQueryParam) parameterObject;
				Pagination pagination = queryParam.getPagination();
				
				if(pagination == null){
					
					throw new NullPointerException("pagination parameterObject is not found!");
					
				} else {
					
					String sql = boundSql.getSql();
					Dialect dialect = null;
					try {
						// 方言的创建
						dialect = (Dialect) Class.forName(SQLDialect).newInstance();
						if (dialect.supportsLimit()) {
							pagination.setTotalCount(getCount(invocation, boundSql, mappedStatement, parameterObject));
							//pagination.pagination();//设置page中的属性值
							// 利用方言，改变sql语句
							sql = dialect.getLimitString(sql, pagination.getCurrentPage(), pagination.getNumPerPage());
							// 将sql语句重新放入boundSql的sql属性中
							//System.out.println("sql = " + sql);
							ReflectUtil.setFieldValue(boundSql, "sql", sql);
						} else {
							throw new NoSuchMethodException("the dialect is not support limit!");
						}
					} catch (ClassNotFoundException e) {
						throw new ClassNotFoundException("the dialect class is not found!");
					}
				}
			}
			
		}
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 初始化时获得mybatis-configuration.xml中plugin下的property的两个参数 SQLDialect：数据库方言的种类
	 * pageSqlId：需要分页的方法的id
	 */
	public void setProperties(Properties properties) {
		SQLDialect = properties.getProperty("SQLDialect");
		pageSqlId = properties.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isEmpty(SQLDialect)) {
			try {
				throw new PropertyException("SQLDialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获得当前查询语句的查询结果的总条数
	 * @throws SQLException 
	 */
	public int getCount(Invocation invocation,BoundSql boundSql,MappedStatement mappedStatement,Object parameterObject) throws SQLException {
		Connection connection = (Connection) invocation.getArgs()[0];
		String sql = boundSql.getSql();
		String countSql = "select count(0) from (" + sql + ") as tmp_count"; // 记录统计
		PreparedStatement countStmt = connection.prepareStatement(countSql);
		BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
		setParameters(countStmt, mappedStatement, countBS, parameterObject);
		ResultSet rs = countStmt.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		rs.close();
		countStmt.close();
		//System.out.println(count);
		return count;
	}

	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
	 * DefaultParameterHandler
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
		
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
}