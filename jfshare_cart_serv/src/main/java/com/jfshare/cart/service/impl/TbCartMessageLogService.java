package com.jfshare.cart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfshare.cart.model.mapper.manual.CartMessageLogMapper;
import com.jfshare.cart.model.entity.TbCartMessageLog;
import com.jfshare.cart.util.CartMessageStatus;
import com.jfshare.cart.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TbCartMessageLogService {

	@Autowired
	private CartMessageLogMapper cartMapper;

	public void insert(TbCartMessageLog entity) {
		entity.setAddTime(DateTimeUtil.current());
		entity.setUpdateTime(DateTimeUtil.current());
		cartMapper.insert(entity);
	}

	public TbCartMessageLog findByUserIdAndProductIdAndStatus(int userId, String productId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("productId", productId);
		map.put("status", status);
		return cartMapper.findByUserIdAndProductIdAndStatus(map);
	}

	public void updateStatus(int userId, String productId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("productId", productId);
		map.put("status", status);
		map.put("updateTime", DateTimeUtil.current());
		cartMapper.updateStatus(map);
	}

	public List<TbCartMessageLog> queryForPage(int page, int pageSize) {
		int begin = (page - 1) * pageSize;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", CartMessageStatus.NORMAL.getCode());
		map.put("begin", begin);
		map.put("pageSize", pageSize);
		return cartMapper.queryForPage(map);
	}

	public void deleteByUserIdAndProductIdAndStatus(int userId, String productId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("productId", productId);
		map.put("status", status);
		cartMapper.deleteByUserIdAndProductIdAndStatus(map);
	}
}
