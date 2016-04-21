package com.jfshare.subject.service;

import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.dao.DisplaySubjectDao;
import com.jfshare.subject.model.DisplaySubjectTreeModel;
import com.jfshare.subject.tree.Node;
import com.jfshare.subject.tree.SubjectRefDisplayMgr;
import com.jfshare.subject.tree.Tree;
import com.jfshare.subject.tree.TreeManager;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class DisplaySubjectService {

    private Logger logger = LoggerFactory.getLogger(DisplaySubjectService.class);

    @Resource(name = "displaySubjectDaoImpl")
    private DisplaySubjectDao displaySubjectDao;

    @Resource
    private TreeManager treeManager;

    @Resource
    private SubjectRefDisplayMgr subjectRefDisplayMgr;

    /**
     * 根据类目ID查询类目信息
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public TbDisplaySubject getById(Integer id) {
        TbDisplaySubject displaySubject = this.displaySubjectDao.getById(id);
        if (displaySubject != null) {
            //加上关系
            displaySubject.setSubjectIds(this.subjectRefDisplayMgr.getSubjectIdsByDisplayId(id));
            //加上是否是叶子节点
            displaySubject.setIsLeaf(this.treeManager.getDisplaySubjectTree(displaySubject.getType()).findNode(id).getData().getIsLeaf());
        }
        return displaySubject;
    }

    /**
     * 从数据库中获取即将插入的ID
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    private Integer getId() {
        return this.displaySubjectDao.getId();
    }

    /**
     * 添加类目
     * @param tbDisplaySubject
     */
    public void add(TbDisplaySubject tbDisplaySubject) {
        tbDisplaySubject.setCreateTime(new DateTime());
        tbDisplaySubject.setUpdateTime(new DateTime());
        tbDisplaySubject.setId(this.getId());
        createPathAndLevel(tbDisplaySubject);
        this.displaySubjectDao.add(tbDisplaySubject);
    }

    /**
     * 修改类目
     * @param tbDisplaySubject
     */
    public void update(TbDisplaySubject tbDisplaySubject) {
        tbDisplaySubject.setUpdateTime(new DateTime());
        createPathAndLevel(tbDisplaySubject);
        this.displaySubjectDao.update(tbDisplaySubject);
    }

    /**
     * 删除类目，逻辑删除，同时删除子节点和与后台类目关系
     * @param subject
     */
    public void delete(TbDisplaySubject subject) {
        this.displaySubjectDao.deleteSub(subject);
    }

    /**
     * 获取当前类目下级类目，只获取下一级
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<DisplaySubjectTreeModel> getSubTree(Integer id, String type) {

        List<DisplaySubjectTreeModel> models = new ArrayList<>();
        Tree<DisplaySubjectTreeModel> tree = this.treeManager.getDisplaySubjectTree(type);
        //获取不到tree
        if(tree == null) {
            return models;
        }
        Node<DisplaySubjectTreeModel> node = tree.findNode(id);
        if(node == null || node.getChildren() == null) {
            return models;
        } else {
            for (Node<DisplaySubjectTreeModel> n : node.getChildren()) {
                models.add(n.getData());
            }
        }
        //设置关系
        this.subjectRefDisplayMgr.setModelsSubjectIds(models);
        return models;
    }

    /**
     * 获取当前类目上级类目，获取全路径
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<DisplaySubjectTreeModel> getSuperTree(Integer id, String type) {
        List<DisplaySubjectTreeModel> models = new ArrayList<>();
        Node<DisplaySubjectTreeModel> node = this.treeManager.getDisplaySubjectTree(type).findNode(id);
        if(node == null) {
            return models;
        }
        models = node.getPath();
        //设置关系
        this.subjectRefDisplayMgr.setModelsSubjectIds(models);
        return models;
    }

    /**
     * 获取整个类目树
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<DisplaySubjectTreeModel> getWholeTree(String type) {
        List<DisplaySubjectTreeModel> models = new ArrayList<>();
        Map<Integer, Node<DisplaySubjectTreeModel>> nodes = this.treeManager.getDisplaySubjectTree(type).getTreeMap();
        if (nodes == null) {
            return models;
        }
        for (Node<DisplaySubjectTreeModel> node : nodes.values()) {
            //去掉root节点
            if (node.getData().getId() != 0) {
                models.add(node.getData());
            }
        }
        //设置关系
        this.subjectRefDisplayMgr.setModelsSubjectIds(models);
        return models;
    }

    /**
     * 条件查询类目总数
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public int queryDisplaySubjectsTotal(Map queryMap) {
        return this.displaySubjectDao.selectTotalByParam(queryMap);
    }

    @Transactional(propagation= Propagation.NEVER)
    public List<TbDisplaySubject> queryDisplaySubjects(Map queryMap) {
        List<TbDisplaySubject> subjects = this.displaySubjectDao.selectByParam(queryMap);
        //加上关系
        this.subjectRefDisplayMgr.setEntitySubjectIds(subjects);
        Tree<DisplaySubjectTreeModel> tree = this.treeManager.getDisplaySubjectTree((String)queryMap.get("type"));
        for (TbDisplaySubject subject : subjects) {
            //加上是否是叶子节点，如果在内存中找不到节点，说明数据没加载完就进行了数据查询，默认设为叶子节点
            Node<DisplaySubjectTreeModel> modelNode = tree.findNode(subject.getId());
            if(modelNode == null) {
                subject.setIsLeaf(1);
            } else {
                subject.setIsLeaf(modelNode.getData().getIsLeaf());
            }

        }
        return subjects;
    }


    /**
     * 生成path
     * @param tbDisplaySubject
     */
    @Transactional(propagation= Propagation.NEVER)
    private void createPathAndLevel(TbDisplaySubject tbDisplaySubject) {
        TbDisplaySubject parent = this.getById(tbDisplaySubject.getPid());
        if(parent != null) {
            tbDisplaySubject.setPath(parent.getPath() + "/" + tbDisplaySubject.getId());
        } else {
            tbDisplaySubject.setPath(tbDisplaySubject.getId() + "");
        }
        //设置level
        tbDisplaySubject.setLevel(tbDisplaySubject.getPath().split("/").length);
    }
}
