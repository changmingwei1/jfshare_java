package com.jfshare.subject.dao.impl.mybatis;

import com.jfshare.subject.bean.TbSubjectRefDisplay;
import com.jfshare.subject.dao.SubjectRefDisplayDao;
import com.jfshare.subject.model.SubjectRefDisplayQueryModel;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SubjectRefDisplayDaoImpl extends BaseDAOImpl<TbSubjectRefDisplay, Integer> implements SubjectRefDisplayDao {

    @Override
    public List<Object> selectBySubjectId(Integer subjectId) {
        return super.getSqlSession().selectList(TbSubjectRefDisplay.class.getName() + ".selectBySubjectId", subjectId);
    }

    @Override
    public String selectByDisplayId(Integer displayId) {
        return (String)super.getSqlSession().selectOne(TbSubjectRefDisplay.class.getName() + ".selectByDisplayId", displayId);
    }

    @Override
    public List<SubjectRefDisplayQueryModel> selectAllSubjectRefDisplay() {
        return super.getSqlSession().selectList(TbSubjectRefDisplay.class.getName() + ".selectAllSubjectRefDisplay");
    }

    @Override
    public List<SubjectRefDisplayQueryModel> selectAllDisplayRefSubject() {
        return super.getSqlSession().selectList(TbSubjectRefDisplay.class.getName() + ".selectAllDisplayRefSubject");
    }

    @Override
    public void deleteRelation(TbSubjectRefDisplay relation) {
        super.getSqlSession().delete(TbSubjectRefDisplay.class.getName() + ".delete", relation);
    }

    @Override
    public void deleteBySubjectPath(String path) {
        super.getSqlSession().delete(TbSubjectRefDisplay.class.getName() + ".deleteBySubjectPath", path);
    }

    @Override
    public void deleteByDisplayPath(String path) {
        super.getSqlSession().delete(TbSubjectRefDisplay.class.getName() + ".deleteByDisplayPath", path);
    }
}
