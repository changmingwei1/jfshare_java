package com.jfshare.product.dao.mysql.pageutil;

public interface Dialect {
	
	public boolean supportsLimit();

	public String getLimitString(String sql, int currentPage,int pageSize);
}
