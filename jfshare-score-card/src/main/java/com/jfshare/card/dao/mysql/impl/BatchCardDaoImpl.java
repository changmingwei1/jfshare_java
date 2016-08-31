package com.jfshare.card.dao.mysql.impl;

import com.jfshare.card.dao.mysql.BatchCardDao;
import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.card.model.TbBatchCardsRecordExample;
import com.jfshare.card.model.mapper.TbBatchCardsRecordMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 批次活动的dao实现类
 * chiwenheng
 * 2016-08-11-43
 */
@Repository
public class BatchCardDaoImpl  implements BatchCardDao {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbBatchCardsRecordMapper tbBatchCardsRecordMapper;

    @Resource(name = "sqlSession")
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    @Transactional
    public int insertOne(TbBatchCardsRecord entity) {

        int result=  tbBatchCardsRecordMapper.insert(entity);

        return result;
    }






    @Override
    @Transactional
    public int updateOne(int id, TbBatchCardsRecord entity) {
        int result = tbBatchCardsRecordMapper.updateByPrimaryKey(entity);
        return result;
    }

    @Override
    @Transactional
    public int deleteOne(int id) {
        return tbBatchCardsRecordMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<TbBatchCardsRecord> queryByParams(TbBatchCardsRecordExample params) {

        return tbBatchCardsRecordMapper.selectByExample(params);
    }

    @Override
    public TbBatchCardsRecord queryById(int id) {
        return tbBatchCardsRecordMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量提交数据
     * @param commitCountEveryCount 每次提交的记录数
     * @param list 要提交的数据列表
     */
    public  boolean batchCommit( int commitCountEveryCount, List<TbBatchCardsRecord> list) {
        boolean flag =false;
        /** * @param mybatisSQLId SQL语句在Mapper XML文件中的ID*/
        String mybatisSQLId = "com.jfshare.card.model.mapper.BatchAction.insertBatchCards";

        SqlSession session = null;
        try {
            session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            int commitCount = (int) Math.ceil(list.size() / (double) commitCountEveryCount);
            List<TbBatchCardsRecord> tempList = new ArrayList<TbBatchCardsRecord>(commitCountEveryCount);
            int start, stop;
            Long startTime = System.currentTimeMillis();
            for (int i = 0; i < commitCount; i++) {
                tempList.clear();
                start = i * commitCountEveryCount;
                stop = Math.min(i * commitCountEveryCount + commitCountEveryCount - 1, list.size() - 1);
                for (int j = start; j <= stop; j++) {
                    tempList.add(list.get(j));
                }
                session.insert(mybatisSQLId, tempList);
                session.commit();
                session.clearCache();
                flag=true;
            }
            Long endTime = System.currentTimeMillis();
            logger.debug("batchCommit耗时：" + (endTime - startTime) + "毫秒");
        } catch (Exception e) {
            logger.error("batchCommit error!", e);
            e.printStackTrace();
            session.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return flag;

    }
    @Override
	public List<TbBatchCardsRecord> queryUnpaidout(TbBatchCardsRecord tbBatchCardsRecord) {

        TbBatchCardsRecordExample bean=new TbBatchCardsRecordExample();
        TbBatchCardsRecordExample.Criteria criteria=bean.createCriteria();

        if(tbBatchCardsRecord.getActivityId()>0){
            criteria.andActivityIdEqualTo(tbBatchCardsRecord.getActivityId());
        }
       
		return  tbBatchCardsRecordMapper.selectByExample(bean);
	}

}
