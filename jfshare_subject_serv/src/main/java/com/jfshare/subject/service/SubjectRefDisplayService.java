package com.jfshare.subject.service;

import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.bean.TbSubjectRefDisplay;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.subject.dao.SubjectRefDisplayDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class SubjectRefDisplayService {

    private Logger logger = LoggerFactory.getLogger(SubjectRefDisplayService.class);

    @Resource(name = "subjectRefDisplayDaoImpl")
    private SubjectRefDisplayDao subjectRefDisplayDao;

    @Resource(name = "subjectDaoImpl")
    private SubjectDao subjectDao;

    /**
     * 添加前台类目和后台类目的对应关系
     * @param relation
     */
    public List<TbSubject> addRelation(TbSubjectRefDisplay relation) {
        //查询添加关系中的后台类目的path
        TbSubject subject = this.subjectDao.getById(relation.getSubjectId());
        String path = subject.getPath();
        //查询关系中已经存在的后台类目的子类目,如果没有添加关系
        Map queryMap = new HashMap();
        queryMap.put("path", path);
        queryMap.put("displayId", relation.getDisplayId());
        List<TbSubject> subjects =this.subjectDao.selectRelationByPath(queryMap);
        if(subjects == null || subjects.size() == 0) {
            this.subjectRefDisplayDao.add(relation);
            return null;
        }
        //如果有，返回存在关系的后台类目列表
        return subjects;
    }

    /**
     * 删除前台类目和后台类目的对应关系
     * @param relation
     */
    public void deleteRelation(TbSubjectRefDisplay relation) {
        this.subjectRefDisplayDao.deleteRelation(relation);
    }

    /**
     * 按后台类目path批量删除关系
     * @param path
     */
    public void deleteBySubjectPath(String path) {
        this.subjectRefDisplayDao.deleteBySubjectPath(path);
    }

    /**
     * 按前台类目path批量删除关系
     * @param path
     */
    public void deleteByDisplayPath(String path) {
        this.subjectRefDisplayDao.deleteByDisplayPath(path);
    }

}
