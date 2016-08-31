package com.jfshare.aftersale.dao.mysql;

import java.util.List;

import com.jfshare.aftersale.model.TbAfterSale;
import com.jfshare.aftersale.model.TbAfterSaleOrder;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryCountParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryParam;

public interface IAfterSaleDao {
	int insert(TbAfterSale tbAfterSale);
	int update(TbAfterSale tbAfterSale);
	List<TbAfterSale> queryAfterSaleCount(AfterSaleQueryCountParam param);
	List<TbAfterSale> query(AfterSaleQueryParam param);
	List<TbAfterSale> queryAfterSale(AfterSaleQueryParam param);
	List<TbAfterSaleOrder> queryAfterSaleOrder(AfterSaleOrderParam param);
	boolean isExistAfterSaleOrder(int userId, String orderId);
	int insertAfterSaleOrder(TbAfterSaleOrder order);
}
