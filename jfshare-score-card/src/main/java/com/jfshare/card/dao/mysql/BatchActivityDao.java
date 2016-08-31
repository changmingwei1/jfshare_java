package com.jfshare.card.dao.mysql;

import java.util.List;
import java.util.Map;

import com.jfshare.card.model.TbBatchActivity;
import com.jfshare.card.model.TbBatchActivityExample;
import com.jfshare.card.model.mapper.ex.TbBatchActivityEx;

/**
 * 批次活动的dao
 */
public interface BatchActivityDao {


    int insertOne(TbBatchActivity entity);

    int updateOne(int id ,TbBatchActivity entity);

    int deleteOne(int id);

    List<TbBatchActivity> queryByParams(TbBatchActivityExample params);

    List<TbBatchActivityEx> queryByParams(Map<String, String> map);
    
    TbBatchActivity queryById(int id);





}
