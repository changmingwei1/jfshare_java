package com.jfshare.subject.service;

import com.jfshare.subject.bean.TbDisplaySubjectChannel;
import com.jfshare.subject.dao.DisplaySubjectChannelDao;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class DisplaySubjectChannelService {

    private Logger logger = LoggerFactory.getLogger(DisplaySubjectChannelService.class);

    @Resource(name = "displaySubjectChannelDaoImpl")
    private DisplaySubjectChannelDao channelDao;

    /**
     * 根据类目ID查询类目渠道信息
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public TbDisplaySubjectChannel getById(Integer id) {
        TbDisplaySubjectChannel channel = this.channelDao.getById(id);
        return channel;
    }

    /**
     * 添加类目渠道
     * @param channel
     */
    public void add(TbDisplaySubjectChannel channel) {
        channel.setCreateTime(new DateTime());
        channel.setUpdateTime(new DateTime());
        this.channelDao.add(channel);
    }

    /**
     * 修改类目渠道
     * @param channel
     */
    public void update(TbDisplaySubjectChannel channel) {
        channel.setUpdateTime(new DateTime());
        this.channelDao.update(channel);
    }

    /**
     * 删除渠道，逻辑删除
     * @param id
     */
    public void delete(Integer id) {
        this.channelDao.delete(id);
    }

    /**
     * 套件查询渠道
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<TbDisplaySubjectChannel> queryChannel(Map queryMap) {
        return this.channelDao.query(queryMap);
    }

    /**
     * 套件查询渠道数量
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public int queryChannelNum(Map queryMap) {
        return this.channelDao.queryNum(queryMap);
    }

}
