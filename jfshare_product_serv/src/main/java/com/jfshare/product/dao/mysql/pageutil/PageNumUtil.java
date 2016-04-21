package com.jfshare.product.dao.mysql.pageutil;

import com.jfshare.finagle.thrift.pagination.Pagination;

public class PageNumUtil {

	public static Pagination setPageNumCount(Pagination pagination){
		int pageNumCount;
		pageNumCount = (int) Math.ceil(pagination.totalCount / (double) pagination.numPerPage);
		pagination.setPageNumCount(pageNumCount);
		return pagination;
	}
}
