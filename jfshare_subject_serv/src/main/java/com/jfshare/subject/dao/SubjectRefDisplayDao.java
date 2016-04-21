package com.jfshare.subject.dao;

import com.jfshare.subject.bean.TbSubjectRefDisplay;
import com.jfshare.subject.model.SubjectRefDisplayQueryModel;

import java.util.List;


public interface SubjectRefDisplayDao extends BaseDAO<TbSubjectRefDisplay, Integer> {

    /**
     * 根据后台类目查询前台类目id，结果为多个以“,”隔开
     *
     * @param subjectId
     * @return
     */
    public List<Object> selectBySubjectId(Integer subjectId);

    /**
     * 根据前台类目查询后台类目id，结果为多个以“,”隔开
     *
     * @param displayId
     * @return
     */
    public String selectByDisplayId(Integer displayId);

    /**
     * 获取所有后台分类和前台分类的对应关系
     * @return
     */
    public List<SubjectRefDisplayQueryModel> selectAllSubjectRefDisplay();

    /**
     * 获取所有前台分类和后台分类的对应关系
     * @return
     */
    public List<SubjectRefDisplayQueryModel> selectAllDisplayRefSubject();

    /**
     * 删除一条或者多条关系记录
     * @param relation
     */
    public void deleteRelation(TbSubjectRefDisplay relation);

    /**
     * 按后台类目path批量删除关系
     * @param path
     */
    public void deleteBySubjectPath(String path);

    /**
     * 按前台类目path批量删除关系
     * @param path
     */
    public void deleteByDisplayPath(String path);
}
