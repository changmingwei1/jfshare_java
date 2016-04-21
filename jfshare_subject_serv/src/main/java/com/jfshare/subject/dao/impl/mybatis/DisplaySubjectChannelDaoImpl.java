package com.jfshare.subject.dao.impl.mybatis;

import com.jfshare.subject.bean.TbDisplaySubjectChannel;
import com.jfshare.subject.dao.DisplaySubjectChannelDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class DisplaySubjectChannelDaoImpl extends BaseDAOImpl<TbDisplaySubjectChannel, Integer> implements DisplaySubjectChannelDao {

    private Logger logger = LoggerFactory.getLogger(DisplaySubjectChannelDaoImpl.class);

    @Override
    public TbDisplaySubjectChannel getById(Integer id) {
        TbDisplaySubjectChannel channel = super.getById(id);
        return channel;
    }

    @Override
    public void update(TbDisplaySubjectChannel channel) {
        logger.info(">>>> update ---- update DisplaySubjectChannel ---- id : {}", channel.getId());
        super.update(channel);
        logger.info("<<<< update ---- update DisplaySubjectChannel ---- id : {}", channel.getId());
    }

    @Override
    public void delete(Integer id) {
        logger.info(">>>> delete ---- delete DisplaySubjectChannel ---- id : {}", id);
        super.delete(id);
        logger.info("<<<< delete ---- delete DisplaySubjectChannel ---- id : {}", id);
    }


    @Override
    public List<TbDisplaySubjectChannel> query(Map queryMap) {
        List<TbDisplaySubjectChannel> channels = super.getSqlSession().selectList(TbDisplaySubjectChannel.class.getName() + ".selectByParam", queryMap);
        return channels;
    }

    @Override
    public int queryNum(Map queryMap) {
        return (int)super.getSqlSession().selectOne(TbDisplaySubjectChannel.class.getName() + ".selectTotalByParam", queryMap);
    }
}
