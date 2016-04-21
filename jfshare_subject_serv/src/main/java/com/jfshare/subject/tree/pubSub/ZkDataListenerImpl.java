package com.jfshare.subject.tree.pubSub;

import com.jfshare.subject.tree.SubjectRefDisplayMgr;
import com.jfshare.subject.tree.TreeManager;
import com.jfshare.subject.util.Commons;
import org.I0Itec.zkclient.IZkDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ZkDataListenerImpl implements IZkDataListener {

    private Logger logger = LoggerFactory.getLogger(ZkDataListenerImpl.class);

    @Resource
    private TreeManager treeManager;

    @Resource
    private SubjectRefDisplayMgr subjectRefDisplayMgr;

    @Override
    public void handleDataChange(String path, Object data) throws Exception {
        String strData = new String((byte[])data);
        logger.info(">>>> handleDataChange ---- path : {}, data : {}", path, strData);
        if(Commons.SUBJECT_MESSAGE.equals(strData)) {
            this.treeManager.initSubjectTree();
        } else if(Commons.DISPLAY_SUBJECT_MESSAGE.equals(strData)) {
            this.treeManager.initDisplaySubjectTreeMap();
        } else if(Commons.SUBJECT_REF_DISPLAY_MESSAGE.equals(strData)) {
            this.subjectRefDisplayMgr.reload();
        }
    }

    @Override
    public void handleDataDeleted(String s) throws Exception {

    }
}
