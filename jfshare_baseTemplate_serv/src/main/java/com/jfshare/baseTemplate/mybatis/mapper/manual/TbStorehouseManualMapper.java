package com.jfshare.baseTemplate.mybatis.mapper.manual;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;

import java.util.List;
import java.util.Map;

public interface TbStorehouseManualMapper {

    List<TbStorehouse> queryStorehouse(Map queryMap);
}