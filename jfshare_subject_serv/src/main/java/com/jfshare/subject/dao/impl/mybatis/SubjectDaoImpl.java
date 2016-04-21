package com.jfshare.subject.dao.impl.mybatis;

import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.subject.dao.SubjectRefDisplayDao;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDaoImpl extends BaseDAOImpl<TbSubject, Integer> implements SubjectDao {

    private Logger logger = LoggerFactory.getLogger(SubjectDaoImpl.class);

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Resource(name = "subjectRefDisplayDaoImpl")
    private SubjectRefDisplayDao subjectRefDisplayDao;

    @Override
    public TbSubject getById(Integer id) {
        TbSubject tbSubject = new TbSubject();
        //从缓存中获取
        Map<String, String> subject = null;
//        Map<String, String> subject = this.jedisBaseDao.getMapAll(Commons.SUBJECT_KEY + id);
        if(MapUtils.isNotEmpty(subject)) {
            tbSubject.deSerialize(subject);
        } else {
            //从数据库中查询
            tbSubject = super.getById(id);
            if(tbSubject == null) {
                return null;
            }
            logger.info("<<<< getById ---- get by id ---- id : {}, from db !!!!", id);
            if (tbSubject != null) {
                //放入缓存
//                this.jedisBaseDao.putMap(tbSubject.getCacheKey(), tbSubject.serialize());
            }
        }
        return tbSubject;
    }

    @Override
    public Integer getId() {
        return (Integer)super.getSqlSession().selectOne(TbSubject.class.getName() + ".getId");
    }

    @Override
    public TbSubject selectByIdManage(Integer id) {
        return (TbSubject)super.getSqlSession().selectOne(TbSubject.class.getName() + ".selectByIdManage", id);
    }

    @Override
    public void update(TbSubject tbSubject) {
        logger.info(">>>> update ---- update subject ---- id : {}", tbSubject.getId());
        super.update(tbSubject);
        logger.info("<<<< update ---- update subject ---- id : {}", tbSubject.getId());
    }

    @Override
    public void delete(Integer id) {
        logger.info(">>>> delete ---- delete subject ---- id : {}", id);
        super.delete(id);
        logger.info("<<<< delete ---- delete subject ---- id : {}", id);
    }

    @Override
    public void deleteSub(TbSubject subject) {
        logger.info(">>>> deleteSub ---- delete subject and sub ---- id : {}", subject.getId());
        String path = subject.getPath();
        super.getSqlSession().delete(TbSubject.class.getName() + ".deleteSub", path);
        logger.info("<<<< deleteSub ---- delete subject and sub ---- id : {}", subject.getId());
    }

    @Override
    public List<TbSubject> selectByPid(Integer pid) {
        return super.getSqlSession().selectList(TbSubject.class.getName() + ".selectByPid", pid);
    }


    @Override
    public List<TbSubject> getWholeTree() {
        List<TbSubject> tbSubjects = super.getSqlSession().selectList(TbSubject.class.getName() + ".selectAll");
        return tbSubjects;
    }

    @Override
    public List<TbSubject> selectByParam(Map queryMap) {
        List<TbSubject> tbSubjects = super.getSqlSession().selectList(TbSubject.class.getName() + ".selectByParam", queryMap);
        return tbSubjects;
    }

    @Override
    public int selectTotalByParam(Map queryMap) {
        return (int)super.getSqlSession().selectOne(TbSubject.class.getName() + ".selectTotalByParam", queryMap);
    }

    @Override
    public List<TbSubject> selectSubjectByDisplayId(Map queryMap) {
        return super.getSqlSession().selectList(TbSubject.class.getName() + ".selectSubjectByDisplayId", queryMap);
    }

    @Override
    public int selectSubjectNumByDisplayId(Map queryMap) {
        return (int)super.getSqlSession().selectOne(TbSubject.class.getName() + ".selectSubjectNumByDisplayId", queryMap);
    }

    @Override
    public List<TbSubject> selectRelationByPath(Map queryMap) {
        return super.getSqlSession().selectList(TbSubject.class.getName() + ".selectRelationByPath", queryMap);
    }

    @Override
    public void deleteRelease(String path) {
        super.getSqlSession().selectList(TbSubject.class.getName() + ".deleteRelease", path);
    }
}
