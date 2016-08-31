package com.jfshare.aftersale.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfshare.aftersale.model.TbAfterSale;
import com.jfshare.finagle.thrift.aftersale.AfterSale;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.DateUtils;

public class AfterSaleUtil {
	public static TbAfterSale afterSale2TbAfterSale(AfterSale afterSale) {
		TbAfterSale tbAfterSale = new TbAfterSale();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(afterSale);
		BeanUtil.fillBeanData(tbAfterSale, stringObjectMap);
		return tbAfterSale;
	}
	
	public static List<AfterSale> process(List<TbAfterSale> tbAfterSales){
		List<AfterSale> afterSales = new ArrayList<AfterSale>();
		for (TbAfterSale tbAfterSale : tbAfterSales) {
			AfterSale sale = new AfterSale();
			if(tbAfterSale.getUserId() > 0)
				sale.setUserId(tbAfterSale.getUserId());
			if(tbAfterSale.getOrderId() != null)
				sale.setOrderId(tbAfterSale.getOrderId());
			if(tbAfterSale.getProductId() != null)
				sale.setProductId(tbAfterSale.getProductId());
			if(tbAfterSale.getSkuNum() != null)
				sale.setSkuNum(tbAfterSale.getSkuNum());
			if(tbAfterSale.getApplyTime() != null) 
				sale.setApplyTime(DateUtils.date2Str(tbAfterSale.getApplyTime().toDate(), DateUtils.Simple_DateTime_Format));
			if(tbAfterSale.getApproveComment() != null)
				sale.setApproveComment(tbAfterSale.getApproveComment());
			if(tbAfterSale.getApproveTime() != null)
				sale.setApproveTime(DateUtils.date2Str(tbAfterSale.getApproveTime().toDate(), DateUtils.Simple_DateTime_Format));
			if(tbAfterSale.getReason() != null)
				sale.setReason(tbAfterSale.getReason());
			if(tbAfterSale.getSellerId() > 0) 
				sale.setSellerId(tbAfterSale.getSellerId());
			if(tbAfterSale.getState() > 0) 
				sale.setState(tbAfterSale.getState());
			if(tbAfterSale.getType() > 0) 
				sale.setType(tbAfterSale.getType());
			if(tbAfterSale.getUserComment() != null)
				sale.setUserComment(tbAfterSale.getUserComment());
			afterSales.add(sale);
		}
		
		return afterSales;
	}
}
