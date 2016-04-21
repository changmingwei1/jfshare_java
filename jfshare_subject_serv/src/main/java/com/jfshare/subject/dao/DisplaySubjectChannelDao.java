package com.jfshare.subject.dao;

import com.jfshare.subject.bean.TbDisplaySubjectChannel;

import java.util.List;
import java.util.Map;


public interface DisplaySubjectChannelDao extends BaseDAO<TbDisplaySubjectChannel, Integer> {

    /**
     * 条件查询渠道
     *
     * @return
     */
    public List<TbDisplaySubjectChannel> query(Map queryMap);

    /**
     * 条件查询渠道数量
     *
     * @return
     */
    public int queryNum(Map queryMap);

}
