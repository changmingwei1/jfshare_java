package com.jfshare.product.dao.mysql.pageutil.diclect;

import com.jfshare.product.dao.mysql.pageutil.Dialect;


public class MySQLDialect implements Dialect {
	
	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int currentPage, int pageSize) {
		int limit = (currentPage-1)*pageSize;
		int offset = pageSize;
		StringBuilder sb = new StringBuilder(sql);
		sb.append(" limit ").append(limit).append(" , ").append(offset);
		return sb.toString();
	}
	
	public static void main(String[] args) {
		
		MySQLDialect mySQLDialect = new MySQLDialect();
		
		String a = mySQLDialect.getLimitString("select * from tb_promotion", 3, 10);
		
		System.out.println(a);
	}
}
