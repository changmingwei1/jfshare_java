package com.jfshare.productTask.service.impl;

import com.jfshare.productTask.mybatis.mapper.automatic.TbProductCardMapper;
import com.jfshare.productTask.mybatis.mapper.automatic.TbProductCardStatisticsMapper;
import com.jfshare.productTask.mybatis.mapper.manual.TbProductCardManualMapper;
import com.jfshare.productTask.mybatis.model.automatic.TbProductCardStatistics;
import com.jfshare.productTask.service.IProductTaskSvc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class ProductTaskSvcImpl implements IProductTaskSvc {

    private Logger logger = LoggerFactory.getLogger(ProductTaskSvcImpl.class);

    @Resource
    private TbProductCardStatisticsMapper productCardStatisticsMapper;

    @Resource
    private TbProductCardManualMapper manualMapper;

    public List<TbProductCardStatistics> statisticsProductCard(String date) {
        return this.manualMapper.statisticsProductCard(date);
    }

    @Override
    public int saveProductCardStatistics(TbProductCardStatistics productCardStatistics) {
        try {
            return this.productCardStatisticsMapper.insertSelective(productCardStatistics);
        } catch (Exception e) {
            logger.error("<<<<<<<< saveProductCardStatistics error !!", e);
        }
        return 0;
    }
}
