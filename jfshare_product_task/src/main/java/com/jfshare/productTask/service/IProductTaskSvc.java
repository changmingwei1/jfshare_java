package com.jfshare.productTask.service;

import com.jfshare.productTask.mybatis.model.automatic.TbProductCardStatistics;

import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
public interface IProductTaskSvc {

    /**
     * 统计某日的销售量和验证量
     * @param date
     * @return
     */
    List<TbProductCardStatistics> statisticsProductCard(String date);

    int saveProductCardStatistics(TbProductCardStatistics productCardStatistics);

}
