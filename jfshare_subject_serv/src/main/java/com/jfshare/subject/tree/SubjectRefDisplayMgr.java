package com.jfshare.subject.tree;

import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.SubjectRefDisplayDao;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.model.DisplaySubjectTreeModel;
import com.jfshare.subject.model.SubjectRefDisplayQueryModel;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SubjectRefDisplayMgr {

    Logger logger = LoggerFactory.getLogger(SubjectRefDisplayMgr.class);

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Resource(name = "subjectRefDisplayDaoImpl")
    private SubjectRefDisplayDao subjectRefDisplayDao;

    String version = "";

    //后台类目与前台类目的对应关系
    private Map<Integer, String> subjectRefDisplayMap;

    //前台类目与后台类目的对应关系
    private Map<Integer, String> displayRefSubjectMap;

    public SubjectRefDisplayMgr() {
    }

    @PostConstruct
    public void reload() {
        //初始化关系
        this.initDisplayRefSubjectMap();
        this.initSubjectRefDisplayMap();
    }

    public Map<Integer, String> getSubjectRefDisplayMap() {
        return subjectRefDisplayMap;
    }

    public void setSubjectRefDisplayMap(Map<Integer, String> subjectRefDisplayMap) {
        this.subjectRefDisplayMap = subjectRefDisplayMap;
    }

    public Map<Integer, String> getDisplayRefSubjectMap() {
        return displayRefSubjectMap;
    }

    public void setDisplayRefSubjectMap(Map<Integer, String> displayRefSubjectMap) {
        this.displayRefSubjectMap = displayRefSubjectMap;
    }

    private void initDisplayRefSubjectMap() {
        logger.info(">>>> initSubjectRefDisplayMap ---- init subjectRefDisplayMap ----");
        Map<Integer, String> map = new HashMap<>();

        //获取所有数据
        List<SubjectRefDisplayQueryModel> models = this.subjectRefDisplayDao.selectAllDisplayRefSubject();
        for (SubjectRefDisplayQueryModel model : models) {
            map.put(model.getId(), model.getIds());
        }
        this.displayRefSubjectMap = map;
        //同步版本号
        /*String redisVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        if(redisVersion == null) {
            redisVersion = String.valueOf(this.jedisBaseDao.incrKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY));
        }
        version = redisVersion;*/
        logger.info("<<<< initSubjectRefDisplayMap ---- init subjectRefDisplayMap ----");
    }

    private void initSubjectRefDisplayMap() {
        logger.info(">>>> initSubjectRefDisplayMap ---- init displayRefSubjectMap ----");
        Map<Integer, String> map = new HashMap<>();

        //获取所有数据
        List<SubjectRefDisplayQueryModel> models = this.subjectRefDisplayDao.selectAllSubjectRefDisplay();

        Map<String, String> ref = new HashMap<>();
        //记录是一个ID
        int id = models.get(0).getId();
        for (SubjectRefDisplayQueryModel model : models) {
        	System.out.println("id:"+model.getId() +"   type =" +model.getType()  +"  ids=" +model.getIds());
            //将相同的id放入到一个map中 拼成 结构如：{"main":"1,2,3", "wireless":"4,5,6"}
        	if(id == model.getId().intValue())
            {
        		ref.put(model.getType(), model.getIds());
            }
            if(id != model.getId().intValue()) {
                map.put(id, JsonMapper.toJson(ref));
                id = model.getId();
                ref = new HashMap<>();
                ref.put(model.getType(), model.getIds());
            }

        } 
        /*最后一个放进来后 ，没有添加到map，补上      */
        map.put(id, JsonMapper.toJson(ref));
        this.subjectRefDisplayMap = map;
        //同步版本号
        /*String redisVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        if(redisVersion == null) {
            redisVersion = String.valueOf(this.jedisBaseDao.incrKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY));
        }
        version = redisVersion;*/
        logger.info("<<<< initSubjectRefDisplayMap ---- init displayRefSubjectMap ----");
    }


    /**
     * 根据后台类目获取前台类目
     * @return
     */
    public String getDisplayIdsBySubjectId(Integer subjectId) {
       /* String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        logger.info(">>>> getDisplayIdsBySujectId --- cacheVersion : {},  version : {}", cacheVersion, this.version);
        if(version == null || !version.equals(cacheVersion)) {
            this.reload();
        }
        logger.info("<<<< getDisplayIdsBySujectId --- cacheVersion : {},  version : {}", cacheVersion, this.version);*/
        return this.subjectRefDisplayMap.get(subjectId);
    }

    /**
     * 根据前台类目获取后台类目
     * @return
     */
    public String getSubjectIdsByDisplayId(Integer displayId) {
        /*String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        logger.info(">>>> getSubjectIdsByDisplayId --- cacheVersion : {},  version : {}", cacheVersion, this.version);
        if(version == null || !version.equals(cacheVersion)) {
            this.reload();
        }
        logger.info("<<<< getSubjectIdsByDisplayId --- cacheVersion : {},  version : {}", cacheVersion, this.version);*/
        return this.displayRefSubjectMap.get(displayId);
    }

    /**
     * 批量设置前台类目对应的后台类目
     * @param models
     */
    public void setModelsSubjectIds(List<DisplaySubjectTreeModel> models) {
        /*String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        logger.info(">>>> setModelsSubjectIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
        if(version == null || !version.equals(cacheVersion)) {
            this.reload();
        }*/
        //设置关系
        for (DisplaySubjectTreeModel model : models) {
            String ids = this.displayRefSubjectMap.get(model.getId());
            model.setSubjectIds(this.displayRefSubjectMap.get(model.getId()));
        }
        //logger.info("<<<< setModelsSubjectIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
    }

    /**
     * 批量设置前台类目对应的后台类目
     * @param displaySubjects
     */
    public void setEntitySubjectIds(List<TbDisplaySubject> displaySubjects) {
        /*String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        logger.info(">>>> setEntitySubjectIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
        if(version == null || !version.equals(cacheVersion)) {
            this.reload();
        }*/
        //设置关系
        for (TbDisplaySubject displaySubject : displaySubjects) {
            displaySubject.setSubjectIds(this.displayRefSubjectMap.get(displaySubject.getId()));
        }
        //logger.info("<<<< setEntitySubjectIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
    }

    /**
     * 批量设置后台类目对应的前台台类目
     * @param models
     */
    public void setModelsDisplayIds(List<SubjectTreeModel> models) {
       /* String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        logger.info(">>>> setModelsDisplayIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
        if(version == null || !version.equals(cacheVersion)) {
            this.reload();
        }*/
        //设置关系
        for (SubjectTreeModel model : models) {
            model.setDisplayIds(this.subjectRefDisplayMap.get(model.getId()));
        }
        //logger.info("<<<< setModelsDisplayIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
    }

    /**
     * 批量设置后台类目对应的前台台类目
     * @param subjects
     */
    public void setEntityDisplayIds(List<TbSubject> subjects) {
        /*String cacheVersion = this.jedisBaseDao.getKV(Commons.SUBJECT_REF_DISPLAY_VERSION_KEY);
        logger.info(">>>> setEntityDisplayIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
        if(version == null || !version.equals(cacheVersion)) {
            this.reload();
        }*/
        //设置关系
        for (TbSubject subject : subjects) {
            subject.setDisplayIds(this.subjectRefDisplayMap.get(subject.getId()));
        }
        //logger.info("<<<< setEntityDisplayIds --- cacheVersion : {},  version : {}", cacheVersion, this.version);
    }
}
