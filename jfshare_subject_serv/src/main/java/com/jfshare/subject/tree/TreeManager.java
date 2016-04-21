package com.jfshare.subject.tree;

import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.DisplaySubjectDao;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.model.DisplaySubjectTreeModel;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.subject.util.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class TreeManager {

    Logger logger = LoggerFactory.getLogger(TreeManager.class);

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Resource(name = "subjectDaoImpl")
    private SubjectDao subjectDao;

    @Resource(name = "displaySubjectDaoImpl")
    private DisplaySubjectDao displaySubjectDao;



    private String subjectVersion = "";
    private Tree<SubjectTreeModel> subjectTree = new Tree<>();
    private Tree<SubjectTreeModel> subjectTreeForManage = new Tree<>();
    private String treeMapVersion = "";
    private Map<String, Tree<DisplaySubjectTreeModel>> displaySubjectTreeMap = new HashMap<>();

    public TreeManager() {
    }

    @PostConstruct
    public void init() {
        //初始化类目树
        this.initSubjectTree();
        this.initDisplaySubjectTreeMap();

    }

    public void initSubjectTree() {
        logger.info(">>>> initSubjectTree ---- init subject tree----");
        //获取整棵树数据
        List<TbSubject> tbSubjects = this.subjectDao.getWholeTree();
        Tree<SubjectTreeModel> newSubjectTree = new Tree<>();
        Tree<SubjectTreeModel> newSubjectTreeForManage = new Tree<>();

        //设置root虚拟节点
        SubjectTreeModel rootData = new SubjectTreeModel();
        rootData.setId(0);
        rootData.setName("root");
        Node<SubjectTreeModel> root = new Node<>(rootData, null);
        Node<SubjectTreeModel> rootManage = new Node<>(rootData, null);
        //添加到map结构中
        newSubjectTree.getTreeMap().put(rootData.getId(), root);
        newSubjectTreeForManage.getTreeMap().put(rootData.getId(), rootManage);

        Node<SubjectTreeModel> node;
        SubjectTreeModel model;
        for (TbSubject tbSubject : tbSubjects) {
            if (tbSubject.getStatus() != 1) {
                model = new SubjectTreeModel();
                model.exchange(tbSubject);
                node = new Node<>();
                node.setData(model);
                //添加到map结构中
                newSubjectTree.getTreeMap().put(model.getId(), node);
            }
            //初始化后台类目树，类目管理专用树
            model = new SubjectTreeModel();
            model.exchange(tbSubject);
            node = new Node<>();
            node.setData(model);
            //添加到map结构中
            newSubjectTreeForManage.getTreeMap().put(model.getId(), node);
        }

        //设置节点与父节点关系
        Iterator iter = newSubjectTree.getTreeMap().keySet().iterator();
        while(iter.hasNext()) {
            Node<SubjectTreeModel> cNode = newSubjectTree.getTreeMap().get(iter.next());
            Node<SubjectTreeModel> pNode = newSubjectTree.findNode(cNode.getData().getPid());
            if(pNode != null) {
                newSubjectTree.add(cNode, pNode);
            }
        }

        //设置节点与父节点关系（管理中心专用）
        Iterator iter4Manager = newSubjectTreeForManage.getTreeMap().keySet().iterator();
        while(iter4Manager.hasNext()) {
            Node<SubjectTreeModel> cNode = newSubjectTreeForManage.getTreeMap().get(iter4Manager.next());
            Node<SubjectTreeModel> pNode = newSubjectTreeForManage.findNode(cNode.getData().getPid());
            if(pNode != null) {
                newSubjectTreeForManage.add(cNode, pNode);
            }
        }

        this.subjectTree = newSubjectTree;
        this.subjectTreeForManage = newSubjectTreeForManage;
        //同步版本号
        /*String redisVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_VERSION_KEY);
        if(redisVersion == null) {
            redisVersion = String.valueOf(this.jedisBaseDao.incrKV(Commons.SUBJECT_VERSION_KEY));
        }
        subjectVersion = redisVersion;*/
        logger.info("<<<< initSubjectTree ---- init subject tree----");
    }

    private void initSubjectTree1() {
        logger.info(">>>> initSubjectTree ---- init subject tree----");
        Tree<SubjectTreeModel> newSubjectTree = new Tree<>();
        Tree<SubjectTreeModel> newSubjectTreeForManage = new Tree<>();

        //设置root虚拟节点
        SubjectTreeModel rootData = new SubjectTreeModel();
        rootData.setId(0);
        rootData.setName("root");
        Node<SubjectTreeModel> root = new Node<>(rootData, null);
        //添加到map结构中
        newSubjectTree.getTreeMap().put(rootData.getId(), root);
        newSubjectTreeForManage.getTreeMap().put(rootData.getId(), root);

        //获取整棵树数据
        List<TbSubject> tbSubjects = this.subjectDao.getWholeTree();
        Node<SubjectTreeModel> node;
        SubjectTreeModel model;
        for (TbSubject tbSubject : tbSubjects) {
            model = new SubjectTreeModel();
            model.exchange(tbSubject);
            node = new Node<>();
            node.setData(model);
            Node<SubjectTreeModel> parent = newSubjectTree.findNode(model.getPid());
            if(parent != null) {
                //设置是否是叶子节点
                node.getData().setIsLeaf(1);
                parent.getData().setIsLeaf(0);
                //添加子节点，状态为新增的只添加到管理页面树中，不添加到前台调用树中
                if(tbSubject.getStatus() != 1) {
                    newSubjectTree.add(node, parent);
                    //添加到map结构中
                    newSubjectTree.getTreeMap().put(model.getId(), node);
                }
                newSubjectTreeForManage.add(node, parent);
                //添加到map结构中
                newSubjectTreeForManage.getTreeMap().put(model.getId(), node);
            }

        }
        this.subjectTree = newSubjectTree;
        this.subjectTreeForManage = newSubjectTreeForManage;
        //同步版本号
        String redisVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_VERSION_KEY);
        if(redisVersion == null) {
            redisVersion = String.valueOf(this.jedisBaseDao.incrKV(Commons.SUBJECT_VERSION_KEY));
        }
        subjectVersion = redisVersion;
        logger.info("<<<< initSubjectTree ---- init subject tree----");
    }

    public void initDisplaySubjectTreeMap() {
        logger.info(">>>> initDisplaySubjectTreeMap ---- init display subject tree----");
        Map<String, Tree<DisplaySubjectTreeModel>> newTreeMap = new HashMap<>();


        //获取整棵树数据
        List<TbDisplaySubject> models = this.displaySubjectDao.getWholeTree();
        Node<DisplaySubjectTreeModel> node;
        DisplaySubjectTreeModel model;
        for (TbDisplaySubject displaySubject : models) {
            model = new DisplaySubjectTreeModel();
            model.exchange(displaySubject);
            node = new Node<>();
            node.setData(model);
            Tree<DisplaySubjectTreeModel> tree = newTreeMap.get(displaySubject.getType());
            if(tree == null) {
                //初始化树
                tree = new Tree<>();
                //设置root虚拟节点
                DisplaySubjectTreeModel rootData = new DisplaySubjectTreeModel();
                rootData.setId(0);
                rootData.setName("root");
                Node<DisplaySubjectTreeModel> root = new Node<>(rootData, null);
                tree.getTreeMap().put(rootData.getId(), root);
                newTreeMap.put(displaySubject.getType(), tree);
            }

            Node<DisplaySubjectTreeModel> parent = tree.findNode(model.getPid());
            if(parent != null) {
                //设置是否是叶子节点
                node.getData().setIsLeaf(1);
                parent.getData().setIsLeaf(0);
                //添加子节点
                tree.add(node, parent);
            }

            //添加到map结构中
            tree.getTreeMap().put(model.getId(), node);
        }
        this.displaySubjectTreeMap = newTreeMap;
        //同步版本号
        /*String redisVersion = this.jedisBaseDao.getKV(Commons.DISPLAY_SUBJECT_VERSION_KEY);
        if(redisVersion == null) {
            redisVersion = String.valueOf(this.jedisBaseDao.incrKV(Commons.DISPLAY_SUBJECT_VERSION_KEY));
        }
        treeMapVersion = redisVersion;*/
        logger.info("<<<< initDisplaySubjectTreeMap ---- init display subject tree----");
    }

    /**
     * 获取后台类目树
     * @return
     */
    public Tree<SubjectTreeModel> getSubjectTree() {
       /* String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_VERSION_KEY);
        logger.info("<<<< getSubjectTree --- cacheVersion : {},  subjectVersion : {}", cacheVersion, this.subjectVersion);
        if(subjectVersion == null || !subjectVersion.equals(cacheVersion)) {
            this.initSubjectTree();
        }*/
        return this.subjectTree;
    }

    /**
     * 获取后台类目树
     * @return
     */
    public Tree<SubjectTreeModel> getSubjectTreeForManage() {
        /*String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_VERSION_KEY);
        logger.info("<<<< getSubjectTreeForManage --- cacheVersion : {},  subjectVersion : {}", cacheVersion, this.subjectVersion);
        if(subjectVersion == null || !subjectVersion.equals(cacheVersion)) {
            this.initSubjectTree();
        }*/
        return this.subjectTreeForManage;
    }

    /**
     * 获取前台类目树
     * @return
     */
    public Tree<DisplaySubjectTreeModel> getDisplaySubjectTree(String key) {
        /*String cacheVersion = this.jedisBaseDao.getKV(Commons.DISPLAY_SUBJECT_VERSION_KEY);
        logger.info("<<<< getDisplaySubjectTree --- cacheVersion : {},  subjectVersion : {}", cacheVersion, this.treeMapVersion);
        if(treeMapVersion == null || !treeMapVersion.equals(cacheVersion) || this.displaySubjectTreeMap.size() == 0) {
            this.initDisplaySubjectTreeMap();
        }*/
        return this.displaySubjectTreeMap.get(key);
    }
}
