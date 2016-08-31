package com.jfshare.card.dao.mysql.impl;

import com.jfshare.card.dao.mysql.BatchRechargeCardDao;
import com.jfshare.card.model.TbBatchRechargeRecord;
import com.jfshare.card.model.TbBatchRechargeRecordExample;
import com.jfshare.card.model.mapper.TbBatchRechargeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 批次活动的dao实现类
 * chiwenheng
 * 2016-08-11-43
 */
@Repository
public class BatchRechargeCardDaoImpl implements BatchRechargeCardDao{

    @Autowired
    private TbBatchRechargeRecordMapper tbBatchRechargeRecordMapper;


    @Override
    @Transactional
    public int insertOne(TbBatchRechargeRecord entity) {

        int result=  tbBatchRechargeRecordMapper.insert(entity);

        return result;
    }

    @Override
    @Transactional
    public int updateOne(int id, TbBatchRechargeRecord entity) {
        int result = tbBatchRechargeRecordMapper.updateByPrimaryKey(entity);
        return result;
    }

    @Override
    @Transactional
    public int deleteOne(int  key) {
        return tbBatchRechargeRecordMapper.deleteByPrimaryKey(key);
    }

    @Override
    public List<TbBatchRechargeRecord> queryByParams(TbBatchRechargeRecordExample params) {
        return tbBatchRechargeRecordMapper.selectByExample(params);
    }

    @Override
    public TbBatchRechargeRecord queryById(int id) {
        return tbBatchRechargeRecordMapper.selectByPrimaryKey(id);
    }


}
