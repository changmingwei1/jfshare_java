package com.jfshare.productTask.task;


import com.jfshare.productTask.mybatis.model.automatic.TbProductCardStatistics;
import com.jfshare.productTask.service.IProductTaskSvc;
import com.jfshare.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Lenovo on 2016/6/6.
 */
@Component
public class ProductCardTask {

    Logger logger = LoggerFactory.getLogger(ProductCardTask.class);

    @Resource(name = "productTaskSvcImpl")
    private IProductTaskSvc productTaskSvc;

    @Scheduled(cron="0 0 3 * * ? ")   //每天凌晨3点执行一次
//    @Scheduled(cron="0/5 * * * * ? ")   //每5s执行一次，测试用
    public void statisticsProductCard(){

        logger.info(">>>> statisticsProductCard begin !!");

        // 统计前一天虚拟商品
        try {
            String date = DateUtils.dateToStr(DateUtils.addDay(new Date(), -1));
            List<TbProductCardStatistics> productCardStatisticses = this.productTaskSvc.statisticsProductCard(date);

            for (TbProductCardStatistics productCardStatistics : productCardStatisticses) {
                // 插入到统计表中
                productCardStatistics.setStatisticsDate(date);
                this.productTaskSvc.saveProductCardStatistics(productCardStatistics);
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< statisticsProductCard error !!", e);
        }
        logger.info("<<<< statisticsProductCard end !!");
    }
}
