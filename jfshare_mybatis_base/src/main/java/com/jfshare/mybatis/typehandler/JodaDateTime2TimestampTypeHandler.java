package com.jfshare.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.joda.time.DateTime;

public class JodaDateTime2TimestampTypeHandler extends
		BaseTypeHandler<DateTime> {
	public void setNonNullParameter(PreparedStatement ps, int i,
			DateTime parameter, JdbcType jdbcType) throws SQLException {
		DateTime dateTime = parameter;
		ps.setTimestamp(i, new Timestamp(dateTime.getMillis()));
	}

	public DateTime getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		DateTime dateTime = null;
		Timestamp timestamp = rs.getTimestamp(columnName);
		if (timestamp != null) {
			dateTime = new DateTime(timestamp.getTime());
		}
		return dateTime;
	}

	public DateTime getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		DateTime dateTime = null;
		Timestamp timestamp = cs.getTimestamp(columnIndex);
		if (timestamp != null) {
			dateTime = new DateTime(timestamp.getTime());
		}
		return dateTime;
	}

	public DateTime getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		DateTime dateTime = null;
		Timestamp timestamp = rs.getTimestamp(columnIndex);
		if (timestamp != null) {
			dateTime = new DateTime(timestamp.getTime());
		}
		return dateTime;
	}
}
