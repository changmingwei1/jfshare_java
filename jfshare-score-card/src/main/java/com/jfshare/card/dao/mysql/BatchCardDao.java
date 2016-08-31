package com.jfshare.card.dao.mysql;

import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.card.model.TbBatchCardsRecordExample;

import java.util.List;

/**
 * 批次活动card的dao
 */
public interface BatchCardDao {

    int insertOne(TbBatchCardsRecord entity);

    int updateOne(int id ,TbBatchCardsRecord entity);

    int deleteOne(int id);

    List<TbBatchCardsRecord> queryByParams(TbBatchCardsRecordExample params);

    TbBatchCardsRecord queryById(int id);

    boolean batchCommit(int commitCountEveryCount, List<TbBatchCardsRecord> list) ;
    
    List<TbBatchCardsRecord> queryUnpaidout(TbBatchCardsRecord tbBatchCardsRecord);
}
