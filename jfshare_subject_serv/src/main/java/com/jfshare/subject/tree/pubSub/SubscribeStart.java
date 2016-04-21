package com.jfshare.subject.tree.pubSub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jfshare.subject.util.Commons;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;


public class SubscribeStart {

    private Logger logger = LoggerFactory.getLogger(SubscribeStart.class);

    @Resource
    private PubSubTask pubSubTask;

    public void start() {
        logger.info(">>>> check subscribe thread -----");
        if(pubSubTask.getState() == Thread.State.TERMINATED) {
            logger.error("<<<<<<<< subscribe thread TERMINATED, restart!!");
            this.pubSubTask = (PubSubTask)new ClassPathXmlApplicationContext("/spring/stand-alone.xml").getBean("pubSubTask");
            this.pubSubTask.setSubjectChannel(Commons.SUBJECT_CHANNEL);
            this.pubSubTask.start();
        } else if (pubSubTask.getState() == Thread.State.NEW) {
            this.pubSubTask.setSubjectChannel(Commons.SUBJECT_CHANNEL);
            pubSubTask.start();
        } else if (pubSubTask.getState() != Thread.State.RUNNABLE) {
            synchronized (pubSubTask) {
                pubSubTask.notify();
            }
        }
    }
}
