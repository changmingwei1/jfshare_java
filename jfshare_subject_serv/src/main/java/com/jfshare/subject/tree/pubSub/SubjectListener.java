package com.jfshare.subject.tree.pubSub;

import com.jfshare.subject.tree.SubjectRefDisplayMgr;
import com.jfshare.subject.tree.TreeManager;
import com.jfshare.subject.util.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;


@Component
public class SubjectListener extends JedisPubSub {

    private Logger logger = LoggerFactory.getLogger(SubjectListener.class);

    @Resource
    private TreeManager treeManager;

    @Resource
    private SubjectRefDisplayMgr subjectRefDisplayMgr;

    @Override
    public void onMessage(String channel, String message) {
        logger.info(">>>> onMessage ---- channel : {}, message : {}", channel, message);
        if(Commons.SUBJECT_MESSAGE.equals(message)) {
            this.treeManager.initSubjectTree();
        } else if(Commons.DISPLAY_SUBJECT_MESSAGE.equals(message)) {
            this.treeManager.initDisplaySubjectTreeMap();
        } else if(Commons.SUBJECT_REF_DISPLAY_MESSAGE.equals(message)) {
            this.subjectRefDisplayMgr.reload();
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }
}
