package com.jfshare.card.dao.mysql;

import com.jfshare.card.model.TbBatchRechargeRecord;
import com.jfshare.card.model.TbBatchRechargeRecordExample;

import java.util.List;

/**
 * 批次活动的充值卡的dao
 */
public interface BatchRechargeCardDao {

    int insertOne(TbBatchRechargeRecord entity);

    int updateOne(int id ,TbBatchRechargeRecord entity);

    int deleteOne(int id);

    List<TbBatchRechargeRecord> queryByParams(TbBatchRechargeRecordExample params);

    TbBatchRechargeRecord queryById(int id);

}
