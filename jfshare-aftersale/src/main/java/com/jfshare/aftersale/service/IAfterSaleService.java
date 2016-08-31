package com.jfshare.aftersale.service;

import com.jfshare.finagle.thrift.aftersale.AfterSale;
import com.jfshare.finagle.thrift.aftersale.AfterSaleCountResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryCountParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleResult;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;

public interface IAfterSaleService {
	void applyAfterSale(StringResult stringResult, AfterSale afterSale);
	void approveAfterSale(StringResult stringResult, AfterSale afterSale);
	void queryAfterSale(AfterSaleResult afterSaleResult, AfterSaleQueryParam param);
	void queryAfterSaleCount(AfterSaleCountResult afterSaleCountResult, AfterSaleQueryCountParam param);
	void queryAfterSaleOrder(AfterSaleOrderResult afterSaleOrderResult, AfterSaleOrderParam param, Pagination pagination);
    int  queryTypeByPK(AfterSale afterSale);
}
