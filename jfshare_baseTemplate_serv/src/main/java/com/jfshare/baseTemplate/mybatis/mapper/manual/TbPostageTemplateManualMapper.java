package com.jfshare.baseTemplate.mybatis.mapper.manual;

import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;

import java.util.List;
import java.util.Map;

public interface TbPostageTemplateManualMapper {

    List<TbPostageTemplate> queryPostageTemplate(Map queryMap);
}