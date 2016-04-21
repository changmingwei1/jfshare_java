package com.jfshare.mybatis.typehandler;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

public class JodaDateTime2TimeTypeHandler extends BaseTypeHandler<DateTime> {
	public void setNonNullParameter(PreparedStatement ps, int i,
			DateTime parameter, JdbcType jdbcType) throws SQLException {
		DateTime dateTime = parameter;
		ps.setTime(i, new Time(dateTime.getMillis()));
	}

	public DateTime getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		DateTime dateTime = null;
		Time time = rs.getTime(columnName);
		if (time != null) {
			dateTime = new DateTime(time.getTime());
		}
		return dateTime;
	}

	public DateTime getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		DateTime dateTime = null;
		Time time = cs.getTime(columnIndex);
		if (time != null) {
			dateTime = new DateTime(time.getTime());
		}
		return dateTime;
	}

	public DateTime getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		DateTime dateTime = null;
		Time time = rs.getTime(columnIndex);
		if (time != null) {
			dateTime = new DateTime(time.getTime());
		}
		return dateTime;
	}
}
