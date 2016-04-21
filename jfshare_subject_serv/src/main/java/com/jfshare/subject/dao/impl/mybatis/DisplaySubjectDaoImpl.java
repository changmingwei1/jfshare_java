package com.jfshare.subject.dao.impl.mybatis;

import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.dao.DisplaySubjectDao;
import com.jfshare.subject.dao.SubjectRefDisplayDao;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.util.Commons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Repository
public class DisplaySubjectDaoImpl extends BaseDAOImpl<TbDisplaySubject, Integer> implements DisplaySubjectDao {

    private Logger logger = LoggerFactory.getLogger(DisplaySubjectDaoImpl.class);

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Resource(name = "subjectRefDisplayDaoImpl")
    private SubjectRefDisplayDao subjectRefDisplayDao;

    @Override
    public TbDisplaySubject getById(Integer id) {
        TbDisplaySubject tbDisplaySubject = new TbDisplaySubject();
        //从缓存中获取
        Map<String, String> displaySubject = this.jedisBaseDao.getMapAll(Commons.DISPLAY_SUBJECT_KEY + id);
        if(displaySubject != null) {
            tbDisplaySubject.deSerialize(displaySubject);
        } else {
            //从数据库中查询
            tbDisplaySubject = super.getById(id);
            if (tbDisplaySubject != null) {
                //放入缓存
                this.jedisBaseDao.putMap(tbDisplaySubject.getCacheKey(), tbDisplaySubject.serialize());
            }
        }
        return tbDisplaySubject;
    }

    @Override
    public Integer getId() {
        return (Integer)super.getSqlSession().selectOne(TbDisplaySubject.class.getName() + ".getId");
    }

    @Override
    public void update(TbDisplaySubject tbDisplaySubject) {
        logger.info(">>>> update ---- update display subject ---- id : {}", tbDisplaySubject.getId());
        super.update(tbDisplaySubject);
        logger.info("<<<< update ---- update display subject ---- id : {}", tbDisplaySubject.getId());
    }

    @Override
    public void delete(Integer id) {
        logger.info(">>>> delete ---- delete display subject ---- id : {}", id);
        super.delete(id);
        logger.info("<<<< delete ---- delete display subject ---- id : {}", id);
    }

    @Override
    public void deleteSub(TbDisplaySubject subject) {
        logger.info(">>>> deleteSub ---- delete display subject and sub ---- id : {}", subject.getId());
        String path = subject.getPath();
        //删除本身和子类目
        super.getSqlSession().delete(TbDisplaySubject.class.getName() + ".deleteSub", path);
        //删除关系
        this.subjectRefDisplayDao.deleteByDisplayPath(path);
        logger.info("<<<< deleteSub ---- delete display subject and sub ---- id : {}", subject.getId());
    }

    @Override
    public List<TbDisplaySubject> getWholeTreeByType(String type) {
        List<TbDisplaySubject> displaySubjects = super.getSqlSession().selectList(TbDisplaySubject.class.getName() + ".selectAll", type);
        return displaySubjects;
    }

    @Override
    public List<TbDisplaySubject> getWholeTree() {
        List<TbDisplaySubject> displaySubjects = super.getSqlSession().selectList(TbDisplaySubject.class.getName() + ".selectAll");
        return displaySubjects;
    }
    
	@Override
	public List<TbDisplaySubject> selectByPid(Integer pId) {
        List<TbDisplaySubject> displaySubjects = super.getSqlSession().selectList(TbDisplaySubject.class.getName() + ".selectByPid", pId);
        return displaySubjects;
	}

    @Override
    public List<TbDisplaySubject> selectByParam(Map queryMap) {
        List<TbDisplaySubject> displaySubjects = super.getSqlSession().selectList(TbDisplaySubject.class.getName() + ".selectByParam", queryMap);
        return displaySubjects;
    }

    @Override
    public int selectTotalByParam(Map queryMap) {
        return (int)super.getSqlSession().selectOne(TbDisplaySubject.class.getName() + ".selectTotalByParam", queryMap);
    }
}
