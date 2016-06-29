package com.jfshare.product.dao.mysql.impl;

import com.jfshare.finagle.thrift.product.AldCaptchaItem;
import com.jfshare.finagle.thrift.product.DayAldCaptchaCount;
import com.jfshare.finagle.thrift.product.DayAldCaptchaItem;
import com.jfshare.product.dao.mysql.IProductCardDao;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.TbProductCardExample;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;
import com.jfshare.product.model.mapper.TbProductCardMapper;
import com.jfshare.product.model.mapper.manual.ManualTbProductCardMapper;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
@Repository
public class ProductCardDaoImpl implements IProductCardDao {

    @Resource
    private TbProductCardMapper cardMapper;

    @Resource
    private ManualTbProductCardMapper manualCardMapper;

    @Override
    public List<TbProductCard> selectAvailableCard(Map queryMap) {
        return this.manualCardMapper.selectAvailableCard(queryMap);

    }

    @Override
    public int lockProductCard(Map lockMap) {
        return this.manualCardMapper.lockProductCard(lockMap);
    }

    @Override
    public int releaseProductCard(Map releaseMap) {
        return this.manualCardMapper.releaseProductCard(releaseMap);
    }

    @Override
    public List<ProductCardStatisticsModel> statisticsProductCard(Map queryMap) {
        return this.manualCardMapper.statisticsProductCard(queryMap);
    }

    @Override
    public int statisticsProductCardCount(Map queryMap) {
        return this.manualCardMapper.statisticsProductCardCount(queryMap);
    }

    @Override
    public List<TbProductCard> queryProductCardViewList(Map queryMap) {
        return this.manualCardMapper.queryProductCardViewList(queryMap);
    }

    @Override
    public int queryProductCardViewListCount(Map queryMap) {
        return this.manualCardMapper.queryProductCardViewListCount(queryMap);
    }

    @Override
    public TbProductCard useProductCard(TbProductCard productCard) {
        TbProductCardExample example = new TbProductCardExample();
        TbProductCardExample.Criteria criteria = example.createCriteria();
        criteria.andSellerIdEqualTo(productCard.getSellerId());
        criteria.andCardNumberEqualTo(productCard.getCardNumber());
        // 只有已发放的才能够使用
        criteria.andStateEqualTo(2);

        List<TbProductCard> tbProductCards = this.cardMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(tbProductCards)) {
            return null;
        }

        TbProductCard tbproductCard = tbProductCards.get(0);
        tbproductCard.setState(3);
        Date now = new Date();
        tbproductCard.setLastUpdateTime(now);
        tbproductCard.setCheckedTime(now);

        int num = this.cardMapper.updateByPrimaryKey(tbproductCard);
        return tbproductCard;
    }

    @Override
    public int sellerProductCardCount(Map queryMap) {
        return this.manualCardMapper.sellerProductCardCount(queryMap);
    }

    @Override
    public List<AldCaptchaItem> sellerProductCardList(Map queryMap) {
        return this.manualCardMapper.sellerProductCardList(queryMap);
    }

    @Override
    public int sellerProductCardDayAllCount(Map queryMap) {
        return this.manualCardMapper.sellerProductCardDayAllCount(queryMap);
    }

    @Override
    public List<DayAldCaptchaCount> sellerProductCardDayAllList(Map queryMap) {
        return this.manualCardMapper.sellerProductCardDayAllList(queryMap);
    }

    @Override
    public int sellerProductCardDayCount(Map queryMap) {
        return this.manualCardMapper.sellerProductCardDayCount(queryMap);
    }

    @Override
    public List<DayAldCaptchaItem> sellerProductCardDayList(Map queryMap) {
        return this.manualCardMapper.sellerProductCardDayList(queryMap);
    }

    @Override
    public int getProductCardCount(Map queryMap) {
        return this.manualCardMapper.getProductCardCount(queryMap);
    }

    @Override
    public int queryProductCardCheckCount(Map queryMap) {
        return this.manualCardMapper.queryProductCardCheckCount(queryMap);
    }

    @Override
    public List<TbProductCard> queryProductCardCheckList(Map queryMap) {
        return this.manualCardMapper.queryProductCardCheckList(queryMap);
    }

    @Override
    public Integer add(TbProductCard obj) {
        return this.cardMapper.insertSelective(obj);
    }

    @Override
    public void update(TbProductCard obj) {
        this.cardMapper.updateByPrimaryKeySelective(obj);
    }

    @Override
    public TbProductCard getById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
