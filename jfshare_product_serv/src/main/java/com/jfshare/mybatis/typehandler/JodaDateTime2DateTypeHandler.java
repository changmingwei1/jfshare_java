package com.jfshare.mybatis.typehandler;


import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

public class JodaDateTime2DateTypeHandler extends BaseTypeHandler<DateTime> {
	public void setNonNullParameter(PreparedStatement ps, int i,
			DateTime parameter, JdbcType jdbcType) throws SQLException {
		DateTime dateTime = parameter;
		ps.setDate(i, new Date(dateTime.getMillis()));
	}

	public DateTime getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		DateTime dateTime = null;
		Date date = rs.getDate(columnName);
		if (date != null) {
			dateTime = new DateTime(date.getTime());
		}
		return dateTime;
	}

	public DateTime getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		DateTime dateTime = null;
		Date date = cs.getDate(columnIndex);
		if (date != null) {
			dateTime = new DateTime(date.getTime());
		}
		return dateTime;
	}

	public DateTime getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		DateTime dateTime = null;
		Date date = rs.getDate(columnIndex);
		if (date != null) {
			dateTime = new DateTime(date.getTime());
		}
		return dateTime;
	}
}
