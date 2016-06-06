package com.jfshare.productTask.mybatis.mapper.manual;

import com.jfshare.productTask.mybatis.model.automatic.TbProductCardStatistics;

import java.util.List;

public interface TbProductCardManualMapper {

    List<TbProductCardStatistics> statisticsProductCard(String date);
}