package com.jfshare.subject.service;

import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.subject.tree.Node;
import com.jfshare.subject.tree.SubjectRefDisplayMgr;
import com.jfshare.subject.tree.Tree;
import com.jfshare.subject.tree.TreeManager;
import com.jfshare.utils.JsonMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional
@Service
public class SubjectService {

    private Logger logger = LoggerFactory.getLogger(SubjectService.class);

    @Resource
    private TreeManager treeManager;

    @Resource
    private SubjectRefDisplayMgr subjectRefDisplayMgr;

    @Resource(name = "subjectDaoImpl")
    private SubjectDao subjectDao;

    /**
     * 根据类目ID查询类目信息
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public TbSubject getById(Integer id) {
        TbSubject tbSubject = this.subjectDao.getById(id);

        if(tbSubject != null) {
            //加上关系
            tbSubject.setDisplayIds(this.subjectRefDisplayMgr.getDisplayIdsBySubjectId(id));
            //加上是否是叶子节点
            tbSubject.setIsLeaf(this.treeManager.getSubjectTreeForManage().findNode(id).getData().getIsLeaf());
        }
        return tbSubject;
    }

    /**
     * 根据类目ID查询类目信息
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public TbSubject selectByIdManage(Integer id) {
        TbSubject tbSubject = this.subjectDao.selectByIdManage(id);
        return tbSubject;
    }

    /**
     * 从数据库中获取即将插入的ID
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    private Integer getId() {
        return this.subjectDao.getId();
    }

    /**
     * 添加类目
     * @param tbSubject
     */
    public void add(TbSubject tbSubject) {
        tbSubject.setStatus(1);
        tbSubject.setCreateTime(new DateTime());
        tbSubject.setUpdateTime(new DateTime());
        tbSubject.setId(this.getId());
        createPathAndLevel(tbSubject);
        this.subjectDao.add(tbSubject);
    }

    /**
     * 修改类目
     * @param tbSubject
     */
    public void update(TbSubject tbSubject) {
        tbSubject.setUpdateTime(new DateTime());
        //新增状态可以修改pid、path和level
        if(tbSubject.getStatus().intValue() == 1) {
            createPathAndLevel(tbSubject);
        } else {
            //其他状态pid、path和level都不修改
            TbSubject subjectDB = this.getById(tbSubject.getId());
            tbSubject.setPid(subjectDB.getPid());
            tbSubject.setPath(subjectDB.getPath());
            tbSubject.setLevel(subjectDB.getLevel());
        }
        this.subjectDao.update(tbSubject);
    }

    /**
     * 删除类目，逻辑删除
     * 如果类目当前状态为新增，则直接变为已删除
     * 如果类目当前状态为已发布，则变为删除
     * @param subject
     */
    public void delete(TbSubject subject) {
        this.subjectDao.deleteSub(subject);
    }

    /**
     * 获取当前类目下级类目，只获取下一级
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<SubjectTreeModel> getSubTree(Integer id) {
        //return this.subjectDao.selectTreeById(id);
        List<SubjectTreeModel> models = new ArrayList<>();
        Node<SubjectTreeModel> node = this.treeManager.getSubjectTree().findNode(id);
        if (node == null || node.getChildren() == null) {
            return models;
        } else {
            for (Node<SubjectTreeModel> n : node.getChildren()) {
                models.add(n.getData());
            }
        }
        //设置关系
        this.subjectRefDisplayMgr.setModelsDisplayIds(models);
        return models;
    }

    /**
     * 获取当前类目下级类目，只获取下一级，专为管理页面用
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<SubjectTreeModel> getSubTreeForManage(Integer id) {
        //return this.subjectDao.selectTreeById(id);
        List<SubjectTreeModel> models = new ArrayList<>();
        Node<SubjectTreeModel> node = this.treeManager.getSubjectTreeForManage().findNode(id);
        if (node == null || node.getChildren() == null) {
            return models;
        } else {
            for (Node<SubjectTreeModel> n : node.getChildren()) {
                models.add(n.getData());
            }
        }
        //设置关系
        this.subjectRefDisplayMgr.setModelsDisplayIds(models);
        return models;
    }

    /**
     * 获取当前类目上级类目，全路径
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<SubjectTreeModel> getSuperTree(Integer id) {
        List<SubjectTreeModel> models = new ArrayList<>();
        Node<SubjectTreeModel> node = this.treeManager.getSubjectTree().findNode(id);
        if(node == null) {
            return models;
        }
        models = node.getPath();
        //设置关系
        this.subjectRefDisplayMgr.setModelsDisplayIds(models);
        /*for (SubjectTreeModel model : models) {
            model.setDisplayIds(this.subjectRefDisplayMgr.getDisplayIdsBySubjectId(model.getId()));
        }*/
        return models;
    }

    /**
     * 获取当前类目上级类目，全路径，专为管理页面用
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<SubjectTreeModel> getSuperTreeForManage(Integer id) {
        List<SubjectTreeModel> models = new ArrayList<>();
        Node<SubjectTreeModel> node = this.treeManager.getSubjectTreeForManage().findNode(id);
        if(node == null) {
            return models;
        }
        models = node.getPath();
        //设置关系
        //this.subjectRefDisplayMgr.setModelsDisplayIds(models);
        return models;
    }

    /**
     * 根据id获取所有的叶子节点
     * @param id
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<SubjectTreeModel> getLeaves(Integer id) {
        List<SubjectTreeModel> models = new ArrayList<>();
        Tree<SubjectTreeModel> subjectTree = this.treeManager.getSubjectTree();
        Node<SubjectTreeModel> node = subjectTree.findNode(id);
        subjectTree.getLeaves(node, models);
        //设置关系
        this.subjectRefDisplayMgr.setModelsDisplayIds(models);
        /*for (SubjectTreeModel model : models) {
            model.setDisplayIds(this.subjectRefDisplayMgr.getDisplayIdsBySubjectId(model.getId()));
        }*/
        return models;
    }


    /**
     * 获取整个类目树
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<SubjectTreeModel> getWholeTree() {
        List<SubjectTreeModel> models = new ArrayList<>();
        Map<Integer, Node<SubjectTreeModel>> nodes = this.treeManager.getSubjectTree().getTreeMap();
        if(nodes == null) {
            return models;
        }
        for (Node<SubjectTreeModel> node : nodes.values()) {
            //去掉root节点
            if(node.getData().getId() != 0) {
                models.add(node.getData());
            }
        }
        //设置关系
        this.subjectRefDisplayMgr.setModelsDisplayIds(models);
        return models;
    }

    /**
     * 生成path
     * @param tbSubject
     */
    @Transactional(propagation= Propagation.NEVER)
    private void createPathAndLevel(TbSubject tbSubject) {
        TbSubject parent = this.getById(tbSubject.getPid());
        if(parent != null) {
            tbSubject.setPath(parent.getPath() + "/" + tbSubject.getId());
        } else {
            tbSubject.setPath(tbSubject.getId() + "");
        }
        //设置level
        tbSubject.setLevel(tbSubject.getPath().split("/").length);
    }

    /**
     * 通过后台的  ID 获取对应前台 ID，     通过后台的ID 获取  整个  以上的树形结构，然后获取整条链的ID
     * @param subjectId
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public String getDisplayIdsBySubjectId(int subjectId) {
    	long start = System.currentTimeMillis();
    	List<SubjectTreeModel> subjects = 	this.getSuperTree(subjectId);

    	Map<String,String>  value = new HashMap<String,String>();
    	if( subjects !=null){
    		for(SubjectTreeModel model : subjects){
    			if(model !=null && model.getDisplayIds()!=null && !model.getDisplayIds().equalsIgnoreCase("{}")){
    				Map<String,String> temp = JsonMapper.toMap(model.getDisplayIds());
    				if(temp !=null && !temp.isEmpty() ){
    					Set<String> keys =  temp.keySet() ;
    					for(String key : keys){
    						if(value.containsKey(key)){
    							value.put(key, value.get(key)+","+temp.get(key));
    						}else{
    							value.put(key,  temp.get(key));
    						}
    					}
    				}
    			}
    		}
    	}
    	String jsonvalue = JsonMapper.toJsonNotNull(value);
        long time = System.currentTimeMillis()-start;
    	if(time > 200){
    		logger.error("<<<<<<<< getDisplayIdsBySubjectId ---- use time ---- " + time + "ms!");
    	}
    	return jsonvalue;
    }

    /**
     * 查询类目信息
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<TbSubject> querySubjects(Map queryMap) {
        List<TbSubject> subjects = this.subjectDao.selectByParam(queryMap);
        //加上关系
        this.subjectRefDisplayMgr.setEntityDisplayIds(subjects);
        Tree<SubjectTreeModel> tree = this.treeManager.getSubjectTreeForManage();
        for (TbSubject subject : subjects) {
            //加上是否是叶子节点，如果在内存中找不到节点，说明数据没加载完就进行了数据查询，默认设为叶子节点
            Node<SubjectTreeModel> modelNode = tree.findNode(subject.getId());
            if(modelNode == null) {
                subject.setIsLeaf(1);
            } else {
                subject.setIsLeaf(modelNode.getData().getIsLeaf());
            }
        }
        return subjects;
    }

    /**
     * 条件查询总数
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public int querySubjectsTotal(Map queryMap) {
        return this.subjectDao.selectTotalByParam(queryMap);
    }


    /**
     * 根据前台分类查询后台分类
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<TbSubject> querySubjectsRelation(Map queryMap) {
        List<TbSubject> subjects = this.subjectDao.selectSubjectByDisplayId(queryMap);
        this.subjectRefDisplayMgr.setEntityDisplayIds(subjects);
        Tree<SubjectTreeModel> tree = this.treeManager.getSubjectTreeForManage();
        for (TbSubject subject : subjects) {
            //加上是否是叶子节点
            subject.setIsLeaf(tree.findNode(subject.getId()).getData().getIsLeaf());
        }
        return subjects;
    }

    /**
     * 根据前台分类查询后台分类
     * @param queryMap
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public int querySubjectsRelationNum(Map queryMap) {
        return this.subjectDao.selectSubjectNumByDisplayId(queryMap);
    }

    /**
     * 发布后台类目，状态：新增 --> 已发布， 删除 --> 已删除
     * @param path
     */
    public void deleteRelease(String path) {
        this.subjectDao.deleteRelease(path);
    }
    /*public void releaseSubject(Integer subjectId) {
        //获取类目信息
        TbSubject subject = this.getById(subjectId);
        //状态为“删除”的类目修改为“已删除”，并将子类目状态统一修改“已删除”
        if(subject.getStatus().intValue() == 2) {
            this.subjectDao.deleteRelease(subject.getPath());
        } else if(subject.getStatus().intValue() == 1) {
            //状态为“新增”的类目改为“已发布”，并且将父类目状态统一修改为“已发布”
            List<SubjectTreeModel> subjectList = this.getSuperTree(subjectId);
            for (SubjectTreeModel model : subjectList) {
                TbSubject tbSubject = new TbSubject();
                tbSubject.setId(model.getId());
                tbSubject.setStatus(3);
                this.update(tbSubject);
            }
        }

    }*/
}
