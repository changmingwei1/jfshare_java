package com.jfshare.card.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jfshare.card.dao.mysql.BatchActivityDao;
import com.jfshare.card.dao.mysql.BatchCardDao;
import com.jfshare.card.model.TbBatchActivity;
import com.jfshare.card.model.TbBatchActivityExample;
import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.card.model.mapper.TbBatchActivityMapper;
import com.jfshare.card.model.mapper.ex.TbBatchActivityEx;
import com.jfshare.card.util.ScoreCardUtil;
import com.jfshare.card.util.TypeStatEnum;

/**
 * 批次活动的dao实现类
 * chiwenheng
 * 2016-08-11-43
 */
@Repository
public class BatchActivityDaoImpl  implements BatchActivityDao{

    @Autowired
    private TbBatchActivityMapper batchActivityMapper;

    @Resource(name = "sqlSession")
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private BatchCardDao batchCardDao;

    @Override
    @Transactional
    public int insertOne(TbBatchActivity entity) {

        int result=  batchActivityMapper.insert(entity);

//        ⑥ 卡密：8位数字，密文显示，中间4位显示为“****”

        if(result>0){//操作成功
            int activityId=entity.getId();
            // 生成对应的卡密
            List<TbBatchCardsRecord> list=new ArrayList<TbBatchCardsRecord>();

            // 生成卡密
            Set<String>  set4CardPsd=ScoreCardUtil.genCardPsd(entity.getTotalCount());
            // 生成卡号的后4位
            Set<String>  set4CardNumLast4=ScoreCardUtil.genCardNumLast4Code(entity.getTotalCount());
            List<String> list4CarNumLast4 = new ArrayList<String>(set4CardNumLast4);
            int index=0;
            for(String itemPsd:set4CardPsd){

                TbBatchCardsRecord bean=new TbBatchCardsRecord();
                bean.setActivityId(activityId);
                //        ⑤ 卡号：jfx+批次ID+4位序列号；
                bean.setCardName(ScoreCardUtil.genCardId(activityId,list4CarNumLast4.get(index)));
                bean.setCardPsd(itemPsd);// 随机 8位卡密 ---加密过的 长度为16位
                bean.setSendStatus(TypeStatEnum.Card_Un_Send.getState());// 未发放
                bean.setRechargeStatus(TypeStatEnum.Card_Un_Recharge.getState());//未充值

                list.add(bean);
                index++;
            }

            boolean flag= batchCardDao.batchCommit(1000,list);// 批量插入 --卡号和卡密

            if(!flag){// 批处理失败
                throw new RuntimeException("批处理失败");
            }

        }

        return result;
    }

    @Override
    @Transactional
    public int updateOne(int id, TbBatchActivity entity) {
        int result = batchActivityMapper.updateByPrimaryKey(entity);
        return result;
    }

    @Override
    @Transactional
    public int deleteOne(int id) {
        return batchActivityMapper.deleteByPrimaryKey(id);
    }


    @Override
    public List<TbBatchActivity> queryByParams(TbBatchActivityExample params) {

        return batchActivityMapper.selectByExample(params);
    }
    @Override
    public List<TbBatchActivityEx> queryByParams(Map<String, String> map) {
    	SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession();
    	List<TbBatchActivityEx> list =  session.selectList("exmapper.selectActivityListByPage",map);
    	if (session!=null) {
			session.close();
		}
    	return list;
    }
    @Override
    public TbBatchActivity queryById(int id) {
        return batchActivityMapper.selectByPrimaryKey(id);
    }
}
