package com.jfshare.aftersale.dao.mysql.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jfshare.aftersale.dao.mysql.IAfterSaleDao;
import com.jfshare.aftersale.model.TbAfterSale;
import com.jfshare.aftersale.model.TbAfterSaleExample;
import com.jfshare.aftersale.model.TbAfterSaleOrder;
import com.jfshare.aftersale.model.TbAfterSaleOrderExample;
import com.jfshare.aftersale.model.mapper.TbAfterSaleMapper;
import com.jfshare.aftersale.model.mapper.TbAfterSaleOrderMapper;
import com.jfshare.finagle.thrift.aftersale.AfterSaleOrderParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryCountParam;
import com.jfshare.finagle.thrift.aftersale.AfterSaleQueryParam;
import com.jfshare.utils.StringUtil;
@Repository
public class AfterSaleDaoImpl implements IAfterSaleDao {
	
	@Autowired
	private TbAfterSaleMapper tbAfterSaleMapper;
	
	@Autowired
	private TbAfterSaleOrderMapper tbAfterSaleOrderMapper;
	
	DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");//日期格式化

	@Override
	public int insert(TbAfterSale tbAfterSale) {
		return tbAfterSaleMapper.insertSelective(tbAfterSale);
	}

	@Override
	public int update(TbAfterSale tbAfterSale) {
		TbAfterSaleExample example = new TbAfterSaleExample();
		TbAfterSaleExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(tbAfterSale.getUserId());
		criteria.andOrderIdEqualTo(tbAfterSale.getOrderId());
		criteria.andProductIdEqualTo(tbAfterSale.getProductId());
		criteria.andSkuNumEqualTo(tbAfterSale.getSkuNum());
		
		return tbAfterSaleMapper.updateByExampleSelective(tbAfterSale, example);
	}

	@Override
	public List<TbAfterSale> query(AfterSaleQueryParam param) {
		TbAfterSaleExample example = new TbAfterSaleExample();
		TbAfterSaleExample.Criteria criteria = example.createCriteria();
		if(param.getUserId() > 0)
			criteria.andUserIdEqualTo(param.getUserId());
		if(param.getSellerId() > 0)
			criteria.andSellerIdEqualTo(param.getSellerId());
		if(param.getOrderId() != null)
			criteria.andOrderIdEqualTo(param.getOrderId());
		if(param.getProductId() != null)
			criteria.andProductIdEqualTo(param.getProductId());
		if(param.getSkuNum() != null)
			criteria.andSkuNumEqualTo(param.getSkuNum());
		if(param.getState() > 0)
			criteria.andStateEqualTo(param.getState());
		
		return tbAfterSaleMapper.selectByExample(example);
	}

	@Override
	public List<TbAfterSaleOrder> queryAfterSaleOrder(AfterSaleOrderParam param) {
		TbAfterSaleOrderExample example = new TbAfterSaleOrderExample();
		TbAfterSaleOrderExample.Criteria criteria = example.createCriteria();	
		if(param.getUserId() > 0){
			criteria.andUserIdEqualTo(param.getUserId());
		}if(param.getSellerId() > 0){
			criteria.andSellerIdEqualTo(param.getSellerId());
		}if(!StringUtil.isNullOrEmpty(param.getOrderId())){
			criteria.andOrderIdEqualTo(param.getOrderId());
		}if(!StringUtil.isNullOrEmpty(param.getStartTime())){
			criteria.andOrderTimeGreaterThanOrEqualTo(DateTime.parse(param.getStartTime(),format));
		}
		if(!StringUtil.isNullOrEmpty(param.getEndTime())){
			criteria.andOrderTimeLessThanOrEqualTo(DateTime.parse(param.getEndTime(),format));
		}
		example.setOrderByClause("order_time desc");
		return tbAfterSaleOrderMapper.selectByExample(example);
	}

	@Override
	public boolean isExistAfterSaleOrder(int userId, String orderId) {
		TbAfterSaleOrderExample example = new TbAfterSaleOrderExample();
		TbAfterSaleOrderExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		criteria.andOrderIdEqualTo(orderId);
		int count = tbAfterSaleOrderMapper.countByExample(example);
		return count > 0 ? true : false;
	}

	@Override
	public int insertAfterSaleOrder(TbAfterSaleOrder order) {
		return tbAfterSaleOrderMapper.insertSelective(order);
	}

	@Override
	public List<TbAfterSale> queryAfterSale(AfterSaleQueryParam param) {
		TbAfterSaleExample example = new TbAfterSaleExample();
		TbAfterSaleExample.Criteria criteria = example.createCriteria();
		if(param.getOrderIdList().isEmpty()){
			if(param.getUserId() > 0)
				criteria.andUserIdEqualTo(param.getUserId());
			if(param.getSellerId() > 0)
				criteria.andSellerIdEqualTo(param.getSellerId());
			if(param.getOrderId() != null)
				criteria.andOrderIdEqualTo(param.getOrderId());
			if(param.getProductId() != null)
				criteria.andProductIdEqualTo(param.getProductId());
			if(param.getSkuNum() != null)
				criteria.andSkuNumEqualTo(param.getSkuNum());
			if(param.getState() > 0)
				criteria.andStateEqualTo(param.getState());
		}else{
			criteria.andOrderIdIn(param.getOrderIdList());
		}
		return tbAfterSaleMapper.selectByExample(example);
	}

	@Override
	public List<TbAfterSale> queryAfterSaleCount(AfterSaleQueryCountParam param) {
		TbAfterSaleExample example = new TbAfterSaleExample();
		TbAfterSaleExample.Criteria criteria = example.createCriteria();
			if(param.getSellerId() > 0)
				criteria.andSellerIdEqualTo(param.getSellerId());
			if(param.getState() > 0)
				criteria.andStateEqualTo(param.getState());
			example.setDistinct(true);
		return tbAfterSaleMapper.selectCountByExample(example);
	}
	
}
