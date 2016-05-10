package com.jfshare.product.dao.mysql.impl;

import com.jfshare.product.dao.mysql.IProductCardDao;
import com.jfshare.product.model.TbProduct;
import com.jfshare.product.model.TbProductCard;
import com.jfshare.product.model.TbProductCardExample;
import com.jfshare.product.model.manual.ProductCardStatisticsModel;
import com.jfshare.product.model.mapper.TbProductCardMapper;
import com.jfshare.product.model.mapper.manual.ManualTbProductCardMapper;
import org.joda.time.DateTime;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
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
        return this.queryProductCardViewListCount(queryMap);
    }

    @Override
    public int useProductCard(TbProductCard productCard) {
        TbProductCardExample example = new TbProductCardExample();
        TbProductCardExample.Criteria criteria = example.createCriteria();
        criteria.andSellerIdEqualTo(productCard.getSellerId());
        criteria.andCardNumberEqualTo(productCard.getCardNumber());
        // 只有已发放的才能够使用
        criteria.andStateEqualTo(2);

        TbProductCard tbproductCard = new TbProductCard();
        tbproductCard.setState(3);
        tbproductCard.setLastUpdateTime(new DateTime().toDate());

        int num = this.cardMapper.updateByExampleSelective(tbproductCard, example);

        return num;
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
