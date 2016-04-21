package com.jfshare.subject.tree.pubSub;

import com.jfshare.ridge.ConfigManager;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.util.Commons;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class PubSubTask extends Thread {

    private Logger logger = LoggerFactory.getLogger(PubSubTask.class);

    private String subjectChannel;

    @Resource
    private ConfigManager configManager;

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Resource(name = "subjectListener")
    private SubjectListener subjectListener;

    @Resource(name = "zkDataListenerImpl")
    private IZkDataListener zkDataListener;

    public String getSubjectChannel() {
        return subjectChannel;
    }

    public void setSubjectChannel(String subjectChannel) {
        this.subjectChannel = subjectChannel;
    }

    @Override
    public void run() {
        /*//类目初始化订阅的channel
        this.jedisBaseDao.subscribe(subjectListener, this.subjectChannel);*/
        configManager.getZkClient().subscribeDataChanges(Commons.SUBJECT_ZK_PATH, zkDataListener);
    }
}
