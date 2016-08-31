package com.jfshare.aftersale.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfshare.aftersale.dao.mysql.IAfterSaleDao;
import com.jfshare.aftersale.model.TbAfterSale;
import com.jfshare.aftersale.model.TbAfterSaleOrder;
import com.jfshare.aftersale.service.IAfterSaleService;
import com.jfshare.aftersale.util.AfterSaleUtil;
import com.jfshare.aftersale.util.FailCode;
import com.jfshare.finagle.thrift.aftersale.AfterSale;
import com.jfshare.finagle.thrift.aftersale.AfterSaleCountResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrder;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderResult;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryCountParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleResult;
import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.utils.StringUtil;
@Service
public class AfterSaleServiceImpl implements IAfterSaleService {
	@Autowired
	private IAfterSaleDao afterSaleDaoImpl;
	@Override
	public void applyAfterSale(StringResult stringResult, AfterSale afterSale) {
		TbAfterSale tbAfterSale = AfterSaleUtil.afterSale2TbAfterSale(afterSale);
		
		// 先查询记录是否存在
		int count = 0;
		if(!isExist(afterSale)){
			count = afterSaleDaoImpl.insert(tbAfterSale);
		} else {
			count = afterSaleDaoImpl.update(tbAfterSale);
		}

		if(count<= 0){
			FailCode.addFails(stringResult.getResult(), FailCode.dbOptionFailed);
		}
		if(!afterSaleDaoImpl.isExistAfterSaleOrder(afterSale.getUserId(), afterSale.getOrderId())){
			TbAfterSaleOrder tbOrder = new TbAfterSaleOrder();
			tbOrder.setUserId(afterSale.getUserId());
			tbOrder.setSellerId(afterSale.getSellerId());
			tbOrder.setOrderId(afterSale.getOrderId());
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			tbOrder.setOrderTime(DateTime.parse(afterSale.getOrderTime(), format));
			afterSaleDaoImpl.insertAfterSaleOrder(tbOrder);
		}
	}

/*	@Override
	public void approveAfterSale(StringResult stringResult, AfterSale afterSale) {
		TbAfterSale tbAfterSale = AfterSaleUtil.afterSale2TbAfterSale(afterSale);
		int count = afterSaleDaoImpl.update(tbAfterSale);
		if(count <= 0){
			FailCode.addFails(stringResult.getResult(), FailCode.dbOptionFailed);
		}
	}*/
	
	@Override
	public void approveAfterSale(StringResult stringResult, AfterSale afterSale) {
		int type = queryTypeByPK(afterSale);
		afterSale.setType(type);//设置type		
		TbAfterSale tbAfterSale = AfterSaleUtil.afterSale2TbAfterSale(afterSale);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		tbAfterSale.setApproveTime(DateTime.parse(df.format(new Date()), format));//设置审核通过时间	
		int count = afterSaleDaoImpl.update(tbAfterSale);
		if(count <= 0){
			FailCode.addFails(stringResult.getResult(), FailCode.dbOptionFailed);
		}
	}
	
	public int  queryTypeByPK(AfterSale afterSale) {
		int type = 0;
		AfterSaleQueryParam param = new AfterSaleQueryParam();
		param.setOrderId(afterSale.getOrderId());
		param.setUserId(afterSale.getUserId());
		param.setProductId(afterSale.getProductId());
		param.setSkuNum(afterSale.getSkuNum());
		List<TbAfterSale> tbAfterSales = afterSaleDaoImpl.query(param);
		if(tbAfterSales != null && tbAfterSales.size() > 0){
			for(TbAfterSale tb : tbAfterSales){
				if(!StringUtil.isNullOrEmpty(tb.getType())){
					type = tb.getType();
				}
			}
		}
		return type;
	}

	@Override
	public void queryAfterSale(AfterSaleResult afterSaleResult, AfterSaleQueryParam param) {
		List<TbAfterSale> tbAfterSales = afterSaleDaoImpl.queryAfterSale(param);
		if(tbAfterSales != null && tbAfterSales.size() > 0){
			List<AfterSale> afterSales = AfterSaleUtil.process(tbAfterSales);
			afterSaleResult.setAfterSaleList(afterSales);
		}
	}
	
	
	private boolean isExist(AfterSale afterSale){
		boolean flg = false;
		AfterSaleQueryParam param = new AfterSaleQueryParam();
		param.setUserId(afterSale.getUserId());
		param.setOrderId(afterSale.getOrderId());
		param.setProductId(afterSale.getProductId());
		param.setSkuNum(afterSale.getSkuNum());
		
		List<TbAfterSale> tbAfterSales = afterSaleDaoImpl.query(param);
		if(tbAfterSales != null && tbAfterSales.size() > 0)
			flg = true;
		return flg;
	}

	@Override
	public void queryAfterSaleOrder(AfterSaleOrderResult afterSaleOrderResult,AfterSaleOrderParam param, Pagination pagination) {
		AfterSaleQueryParam pam= new AfterSaleQueryParam();
		List<String> orderIdList = new ArrayList<>();	
		List<TbAfterSaleOrder> orders = afterSaleDaoImpl.queryAfterSaleOrder(param);
		if(orders != null && orders.size() > 0){
			List<AfterSaleOrder> afterSaleOrders = new ArrayList<AfterSaleOrder>();
			for (TbAfterSaleOrder order : orders) {
				AfterSaleOrder saleOrder = new AfterSaleOrder();
				saleOrder.setUserId(order.getUserId());
				saleOrder.setSellerId(order.getSellerId());
				saleOrder.setOrderId(order.getOrderId());
				orderIdList.add(order.getOrderId());
				afterSaleOrders.add(saleOrder);
			}
			pam.setOrderIdList(orderIdList);
			List<TbAfterSale> tbAfterSales = afterSaleDaoImpl.queryAfterSale(pam);
			if(tbAfterSales != null && tbAfterSales.size() > 0){
				List<AfterSale> afterSales = AfterSaleUtil.process(tbAfterSales);
				afterSaleOrderResult.setAfterSaleList(afterSales);
			}
			//按照分页的参数自己处理list的获取范围
			if(afterSaleOrders != null && afterSaleOrders.size() > 0){
				if(pagination != null){
					int fromIndex = pagination.getNumPerPage() * (pagination.getCurrentPage() - 1);
					int toIndex = pagination.getNumPerPage() * pagination.getCurrentPage();
					if(fromIndex < 0) fromIndex = 0;
					if(toIndex > afterSaleOrders.size()) toIndex = afterSaleOrders.size();
					
					pagination.setTotalCount(afterSaleOrders.size());
					pagination.setPageNumCount((int) Math.ceil(pagination.totalCount / (double) pagination.numPerPage));
					
					List<AfterSaleOrder> trades = afterSaleOrders.subList(fromIndex, toIndex);
					afterSaleOrderResult.setAfterSaleOrders(trades);
					afterSaleOrderResult.setPagination(pagination);
				} else {
					afterSaleOrderResult.setAfterSaleOrders(afterSaleOrders);
				}
			}
		} else {
//			afterSaleOrderResult.getResult().setCode(1);
		}
	}

	@Override
	public void queryAfterSaleCount(AfterSaleCountResult afterSaleCountResult, AfterSaleQueryCountParam param) {
		List<TbAfterSale> tbAfterSales = afterSaleDaoImpl.queryAfterSaleCount(param);
		int size = tbAfterSales.size();
		afterSaleCountResult.setCount(size);	
	}

}
