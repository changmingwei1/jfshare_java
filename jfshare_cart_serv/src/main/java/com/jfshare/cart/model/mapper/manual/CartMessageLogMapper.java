package com.jfshare.cart.model.mapper.manual;

import com.jfshare.cart.model.entity.TbCartMessageLog;

import java.util.Map;

public interface CartMessageLogMapper extends BaseMapper {

	public TbCartMessageLog findByUserIdAndProductIdAndStatus(Map<String, Object> map);

	public void updateStatus(Map<String, Object> map);

	public void deleteByUserIdAndProductIdAndStatus(Map<String, Object> map);
}