package com.jfshare.baseTemplate.service.impl;

import com.jfshare.baseTemplate.dao.mysql.IStorehouseDao;
import com.jfshare.baseTemplate.dao.redis.BaseRedis;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;
import com.jfshare.baseTemplate.service.IStorehouseSvc;
import com.jfshare.baseTemplate.util.Constant;
import com.jfshare.baseTemplate.util.ConvertUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class StorehouseSvcImpl implements IStorehouseSvc {

    @Resource
    private IStorehouseDao storehouseDao;

    @Resource
    private BaseRedis baseRedis;

    @Override
    public int addStorehouse(TbStorehouse tbStorehouse) {
        DateTime now = new DateTime();
        tbStorehouse.setCreateTime(now);
        tbStorehouse.setLastUpdateTime(now);
        return this.storehouseDao.add(tbStorehouse);
    }

    @Override
    public int updateStorehouse(TbStorehouse tbStorehouse) {
        tbStorehouse.setLastUpdateTime(new DateTime());
        return this.storehouseDao.updateById(tbStorehouse);
    }

    @Override
    public int deleteStorehouse(int sellerId, int id) {
        return this.storehouseDao.deleteStorehouse(sellerId, id);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int deleteStorehouseBatch(List<TbStorehouse> storehouseList) {
        int num = 0;
        for (TbStorehouse tbStorehouse : storehouseList) {
            num = num + this.deleteStorehouse(tbStorehouse.getSellerId(), tbStorehouse.getId());
        }
        return num;
    }

    @Override
    public List<TbStorehouse> queryStorehouse(Map queryMap) {
        return this.storehouseDao.queryStorehouse(queryMap);
    }

    @Override
    public TbStorehouse getById(int id) {
        TbStorehouse tbStorehouse = null;
        // 从缓存获取
        Map<String, String> storehouseMap = this.baseRedis.getMapAll(Constant.STOREHOUSE_REDIS_KEY + id);
        if (storehouseMap != null) {
            tbStorehouse = ConvertUtil.redis2TbStorehouse(storehouseMap);
        } else {
            // 缓存中不存在，从数据库中获取，并放入缓存
            tbStorehouse = this.addCache(id);
        }
        return tbStorehouse;
    }


    @Override
    public void removeCache(int id) {
        this.baseRedis.removeKV(Constant.STOREHOUSE_REDIS_KEY + id);
    }

    @Override
    public TbStorehouse addCache(int id) {
        TbStorehouse tbStorehouse = this.storehouseDao.getById(id);
        if (tbStorehouse != null) {
            this.baseRedis.putMap(Constant.STOREHOUSE_REDIS_KEY + id, ConvertUtil.tbStorehouse2Redis(tbStorehouse));
        }
        return tbStorehouse;
    }
}
