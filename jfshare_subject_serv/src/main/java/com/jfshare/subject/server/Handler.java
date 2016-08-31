package com.jfshare.subject.server;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.subject.*;
import com.jfshare.ridge.ConfigManager;
import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.bean.TbDisplaySubjectChannel;
import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.bean.support.PageBean;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.model.DisplaySubjectTreeModel;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.subject.service.*;
import com.jfshare.subject.util.Commons;
import com.jfshare.subject.util.ResultGenerator;
import com.jfshare.subject.util.ZookeeperUtil;
import com.jfshare.utils.JsonMapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("subjectHandler")
public class Handler extends BaseHandler implements SubjectServ.Iface{
	
	private Logger logger = LoggerFactory.getLogger(Handler.class);

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Resource
    private SubjectService subjectService;

    @Resource
    private ProductAttributeService productAttributeService;

    @Resource
    private DisplaySubjectService displaySubjectService;

    @Resource
    private DisplaySubjectChannelService displaySubjectChannelService;

    @Resource
    private SubjectRefDisplayService subjectRefDisplayService;

    @Resource
    private ConfigManager configManager;

    @Autowired
    private SubjectRefBrandService subjectRefBrandService;

    /**
     * 添加后台类目
     * @param subject
     * @return
     */
    @Override
    public Result add(SubjectInfo subject) {
        logger.info(">>>> add ---- add subject ---- subject : {}", JsonMapper.toJson(subject));
        Result result = new Result(0);
        try {
            result = super.verifySubject(subject);
            if(result.getCode() != 0) {
                return result;
            }
            this.subjectService.add(super.convertSubject(subject));
            //更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_MESSAGE.getBytes());
        } catch (Exception e) {
            logger.error("<<<<<<<< add ---- add subject ---- error !!! " + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< add ---- add subject ---- success finished !!! ");
        return result;
    }

    /**
     * 修改后台类目
     * @param subject
     * @return
     */
    @Override
    public Result update(SubjectInfo subject) {
        logger.info(">>>> update ---- update subject ---- subject : {}", JsonMapper.toJson(subject));
        Result result = new Result(0);
        try {
            result = super.verifySubject(subject);
            if(result.getCode() != 0) {
                return result;
            }
            TbSubject tbSubject = super.convertSubject(subject);
            this.subjectService.update(tbSubject);
            //清除缓存
            this.jedisBaseDao.removeKV(tbSubject.getCacheKey());
            //更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_MESSAGE.getBytes());
        } catch (Exception e) {
            logger.error("<<<<<<<< update ---- update subject ---- error !!! " + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< update ---- update subject ---- success finished !!! ");
        return result;
    }

    /**
     * 删除后台类目,包括子类目
     * @param subjectId
     * @return
     */
    @Override
    public Result deleteById(int subjectId) {
        logger.info(">>>> delete ---- delete subject ---- subjectId : {}", subjectId);
        Result result = new Result(0);
        try {
            TbSubject subject = this.subjectService.getById(subjectId);
            this.subjectService.delete(subject);
            //清除本身和子类目缓存
            Map queryMap = new HashMap();
            queryMap.put("path", subject.getPath());
            List<TbSubject> subs = this.subjectService.querySubjects(queryMap);
            for (TbSubject tbSubject : subs) {
                this.jedisBaseDao.removeKV(Commons.SUBJECT_KEY + tbSubject.getId());
            }
            //更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_MESSAGE.getBytes());
        } catch (Exception e) {
            logger.error("<<<<<<<< delete ---- delete subject ---- error !!! " + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< delete ---- delete subject ---- success finished !!! ");
        return result;
    }

    /**
     * 根据后台类目ID，获取后台类目信息
     * @param subjectId
     * @return
     */
    @Override
    public SubjectInfoResult getById(int subjectId) {
        SubjectInfoResult subjectInfoResult = new SubjectInfoResult();
        TbSubject tbSubject;
        try {
            tbSubject = this.subjectService.getById(subjectId);
            if(tbSubject == null) {
                return subjectInfoResult.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_NOT_EXIST));
            }

            subjectInfoResult.setSubjectInfo(rConvertSubject(tbSubject));
            subjectInfoResult.setSubjectAttributes(super.rConvertAttributes(this.productAttributeService.selectByIds(tbSubject.getAttributes())));
            subjectInfoResult.setBrandList(this.subjectRefBrandService.getBrands(tbSubject));
            subjectInfoResult.setResult(ResultGenerator.createCorrectResult());
            return subjectInfoResult;
        } catch (Exception e) {
            logger.error("<<<<<<<< getById ---- get subject by id ---- error !!! " + e.getMessage(), e);
            return new SubjectInfoResult().setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
    }

    /**
     * 获取当前后台类目和直接子类目信息
     * @param subjectId
     * @return
     */
    @Override
    public SubjectTreeResult getSubTree(int subjectId) {
        SubjectTreeResult subjectTreeResult = new SubjectTreeResult();
        List<SubjectTreeModel> models;
        try {
            models = this.subjectService.getSubTree(subjectId);
            subjectTreeResult.setSubjectNodes(super.rConvertSubjectTreeModels(models));
            subjectTreeResult.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< getSubTree ---- get subject sub node ---- error !!! " + e.getMessage(), e);
            subjectTreeResult.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return subjectTreeResult;
    }


    /**
     * 获取当前后台类目和直接子类目信息，包括新增状态的类目，类目管理页面专用
     * @param subjectId
     * @return
     */
    @Override
    public SubjectTreeResult getSubTreeForManage(int subjectId) {
        SubjectTreeResult subjectTreeResult = new SubjectTreeResult();
        List<SubjectTreeModel> models;
        try {
            models = this.subjectService.getSubTreeForManage(subjectId);
            subjectTreeResult.setSubjectNodes(super.rConvertSubjectTreeModels(models));
            subjectTreeResult.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< getSubTreeForManage ---- get subject sub node ---- error !!! " + e.getMessage(), e);
            subjectTreeResult.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return subjectTreeResult;
    }

    /**
     * 获取当前后台类目路径
     * @param subjectId 后台类目ID
     * @return
     */
    @Override
    public SubjectTreeResult getSuperTree(int subjectId) {
        SubjectTreeResult subjectTreeResult = new SubjectTreeResult();
        List<SubjectTreeModel> models;
        try {
            models = this.subjectService.getSuperTree(subjectId);
            subjectTreeResult.setSubjectNodes(super.rConvertSubjectTreeModels(models));
            subjectTreeResult.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< getSuperTree ---- get subject super node ---- error !!! " + e.getMessage(), e);
            subjectTreeResult.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return subjectTreeResult;
    }

    /**
     * 根据科目ID查询所有叶子节点
     * @param subjectId 后台类目ID
     * @return
     */
    @Override
    public SubjectTreeResult getLeavesById(int subjectId) {
        SubjectTreeResult subjectTreeResult = new SubjectTreeResult();
        List<SubjectTreeModel> models;
        try {
            models = this.subjectService.getLeaves(subjectId);
            subjectTreeResult.setSubjectNodes(super.rConvertSubjectTreeModels(models));
            subjectTreeResult.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< getLeavesById ---- get leaves by id ---- error !!! " + e.getMessage(), e);
            subjectTreeResult.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return subjectTreeResult;
    }

    /**
     * 获取整个后台类目树结构 结果为json格式
     * @return
     */
    @Override
    public StringResult getWholeTree() {
        StringResult stringResult = new StringResult();
        List<SubjectTreeModel> models;
        try {
            models = this.subjectService.getWholeTree();
            stringResult.setValue(JsonMapper.toJson(models));
            stringResult.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
             stringResult.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
            logger.error("<<<<<<<< getWholeTree ---- get subject all node ----- error !!!!!" + e.getMessage(), e);
        }
        return stringResult;
    }

    /**
     * 获取整个后台类目树结构 结果为对象格式
     * @return
     */
    @Override
    public SubjectTreeResult getWholeTreeList() {
        SubjectTreeResult result = new SubjectTreeResult();
        List<SubjectTreeModel> models;
        try {
            models = this.subjectService.getWholeTree();
            result.setSubjectNodes(super.rConvertSubjectTreeModels(models));
            result.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            result.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
            logger.error("<<<<<<<< getWholeTreeList ---- get subject all node ----- error !!!!!" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 发布后台类目，状态：新增 --> 已发布， 删除 --> 已删除
     * @param subjectId
     * @return
     * @throws TException
     */
    @Override
    public Result releaseSubject(int subjectId) throws TException {
        logger.info(">>>> releaseSubject ---- release subject --- subjectId : {}", subjectId);
        try {
            //发布
            //获取类目信息
            TbSubject subject = this.subjectService.getById(subjectId);
            //状态为“删除”的类目修改为“已删除”，并将子类目状态统一修改“已删除”
            if(subject.getStatus().intValue() == 2) {
                this.subjectService.deleteRelease(subject.getPath());
                //清除本身和子类目缓存
                Map queryMap = new HashMap();
                queryMap.put("path", subject.getPath());
                List<TbSubject> subs = this.subjectService.querySubjects(queryMap);
                for (TbSubject tbSubject : subs) {
                    this.jedisBaseDao.removeKV(Commons.SUBJECT_KEY + tbSubject.getId());
                }
            } else if(subject.getStatus().intValue() == 1) {
                //状态为“新增”的类目改为“已发布”，并且将父类目状态统一修改为“已发布”
                List<SubjectTreeModel> models = this.subjectService.getSuperTreeForManage(subjectId);
                for (SubjectTreeModel model : models) {
                    TbSubject tbSubject = new TbSubject();
                    tbSubject.setId(model.getId());
                    tbSubject.setPid(model.getPid());
                    tbSubject.setStatus(3);
                    this.subjectService.update(tbSubject);
                    //清除缓存
                    this.jedisBaseDao.removeKV(Commons.SUBJECT_KEY + subjectId);
                }
                /*for (String  id : subject.getPath().split("/")) {
                    TbSubject tbSubject = this.subjectService.selectByIdManage(Integer.parseInt(id));
                    tbSubject.setStatus(3);
                    this.subjectService.update(tbSubject);
                    //清除缓存
                    this.jedisBaseDao.removeKV(Commons.SUBJECT_KEY + subjectId);
                }*/
            }
            //更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_MESSAGE.getBytes());
        } catch (Exception e) {
            logger.error("<<<<<<<<  releaseSubject ---- release subject --- error!!!" + e.getMessage(), e);
        }
        logger.info("<<<< releaseSubject ---- release subject --- subjectId : {}", subjectId);
        return ResultGenerator.createCorrectResult();
    }

    /**
     * 添加前台类目
     * @param displaySubjectParam
     * @return
     */
    @Override
    public Result add4dis(DisplaySubjectParam displaySubjectParam) {
        logger.info(">>>> add4dis ---- add display subject ---- subject : {} , type : {}", JsonMapper.toJson(displaySubjectParam.getDisplaySubjectInfo()) , displaySubjectParam.getFlag());
        if(displaySubjectParam.getDisplaySubjectInfo() == null) {
            return ResultGenerator.createErrorResult(ResultGenerator.SUJECT_PARAM_ERROR);
        }
        Result result;
        //校验
        result = super.verifyDisplaySubject(displaySubjectParam.getDisplaySubjectInfo());
        if(result.getCode() != 0) {
            return result;
        }
        TbDisplaySubject displaySubject = super.convertDisSubject(displaySubjectParam.getDisplaySubjectInfo());
        displaySubject.setType(displaySubjectParam.getFlag());
        try {
            displaySubjectService.add(displaySubject);
            //更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.DISPLAY_SUBJECT_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.DISPLAY_SUBJECT_MESSAGE.getBytes());
            result = ResultGenerator.createCorrectResult();
        } catch (Exception e) {
            logger.error("<<<<<<<< ---- add4dis ---- add display subject ----- error !!!" + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< add4dis ---- add display subject ---- success finished !!!");
        return result;
    }

    /**
     * 修改前台类目
     * @param displaySubjectParam
     * @return
     */
    @Override
    public Result update4dis(DisplaySubjectParam displaySubjectParam) {
        logger.info(">>>> update4dis ---- update display subject ---- subject : {} , type : {}", JsonMapper.toJson(displaySubjectParam.getDisplaySubjectInfo()) , displaySubjectParam.getFlag());
    	TbDisplaySubject displaySubject = super.convertDisSubject(displaySubjectParam.getDisplaySubjectInfo());
        displaySubject.setType(displaySubjectParam.getFlag());
        Result result;
        //校验
        result = super.verifyDisplaySubject(displaySubjectParam.getDisplaySubjectInfo());
        if(result.getCode() != 0) {
            return result;
        }
        try {
            this.displaySubjectService.update(displaySubject);
            //清除缓存
            this.jedisBaseDao.removeKV(displaySubject.getCacheKey());
            //更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.DISPLAY_SUBJECT_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.DISPLAY_SUBJECT_MESSAGE.getBytes());
            result = ResultGenerator.createCorrectResult();
        } catch (Exception e) {
            logger.error("<<<<<<<< ---- update4dis ---- update display subject ----- error !!!" + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< update4dis ---- update display subject ---- success finished !!!");
        return result;
    }

    /**
     * 删除前台类目，同时删除子类目和与后台类目的关系
     * @param displaySubjectParam
     * @return
     */
    @Override
    public Result delete4dis(DisplaySubjectParam displaySubjectParam) {
        logger.info(">>>> delete4dis ---- delete display subject ---- subjectId : {} ", displaySubjectParam.getDisplaySubjectInfo().getId());
    	TbDisplaySubject displaySubject = super.convertDisSubject(displaySubjectParam.getDisplaySubjectInfo());
        Result result;
        try {
            TbDisplaySubject subject = this.displaySubjectService.getById(displaySubject.getId());
            Map queryMap = new HashMap();
            queryMap.put("path", subject.getPath());
            queryMap.put("type", displaySubjectParam.getFlag());
            List<TbDisplaySubject> subs = this.displaySubjectService.queryDisplaySubjects(queryMap);
            this.displaySubjectService.delete(subject);
            //清除本身及子类目缓存
            for (TbDisplaySubject tbDisplaySubject : subs) {
                this.jedisBaseDao.removeKV(Commons.DISPLAY_SUBJECT_KEY + tbDisplaySubject.getId());
            }
            //删除关系
            this.subjectRefDisplayService.deleteByDisplayPath(subject.getPath());
            //更新版本
            /*this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.DISPLAY_SUBJECT_MESSAGE);
            this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_REF_DISPLAY_MESSAGE);*/
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.DISPLAY_SUBJECT_MESSAGE.getBytes());
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_REF_DISPLAY_MESSAGE.getBytes());
            result = ResultGenerator.createCorrectResult();
        } catch (Exception e) {
            logger.error("<<<<<<<< ---- delete4dis ---- delete display subject ----- error !!!" + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< delete4dis ---- delete display subject ---- success finished !!!");
        return result;
    }

    /**
     * 根据前台类目ID获取前台类目信息
     * @param displaySubjectParam
     * @return
     */
    @Override
    public DisplaySubjectResult getById4dis(DisplaySubjectParam displaySubjectParam) {
        DisplaySubjectResult resultSubject = new DisplaySubjectResult();
    	TbDisplaySubject displaySubject = super.convertDisSubject(displaySubjectParam.getDisplaySubjectInfo());
        try {
            displaySubject = this.displaySubjectService.getById(displaySubject.getId());
            if(displaySubject == null) {
                return resultSubject.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_NOT_EXIST));
            }
            resultSubject.setDisplaySubjectInfo(super.rConvertDisSubject(displaySubject));
            resultSubject.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< ---- getById4dis ---- get display subject ----- error !!!" + e.getMessage(), e);
            resultSubject.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return resultSubject;
    }

    /**
     * 获取当前前台类目和直接子类目信息
     * @param displaySubjectParam
     * @return
     */
    @Override
    public DisplaySubjectTreeResult getSubTree4dis(DisplaySubjectParam displaySubjectParam) {
    	DisplaySubjectTreeResult resultSubject = new DisplaySubjectTreeResult();
    	TbDisplaySubject displaySubject = super.convertDisSubject(displaySubjectParam.getDisplaySubjectInfo());
        try {
            List<DisplaySubjectTreeModel> nodes = this.displaySubjectService.getSubTree(displaySubjectParam.getDisplaySubjectInfo().getId(), displaySubjectParam.getFlag());
            resultSubject.setDisplaySubjectNodes(super.rConvertDisSubjectNode(nodes));
            resultSubject.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< ---- getSubTree4dis ---- get display sub subject ----- error !!!" + e.getMessage(), e);
            resultSubject.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return resultSubject;
    }

    
    /**
     * 获取当前前台类目路径
     * @param displaySubjectParam
     * @return
     */
    @Override
    public DisplaySubjectTreeResult getSuperTree4dis(DisplaySubjectParam displaySubjectParam) {
    	DisplaySubjectTreeResult resultSubject = new DisplaySubjectTreeResult();
        try {
            List<DisplaySubjectTreeModel> nodes = this.displaySubjectService.getSuperTree(displaySubjectParam.getDisplaySubjectInfo().getId(), displaySubjectParam.getFlag());
            resultSubject.setDisplaySubjectNodes(super.rConvertDisSubjectNode(nodes));
            resultSubject.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            logger.error("<<<<<<<< ---- getSuperTree4dis ---- get display super subject ----- error !!!" + e.getMessage(), e);
            resultSubject.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return resultSubject;
    }
    
   /**
    * 根据后台类目查询对应的所有的前台类目，包括此后台类目上级类目对应的前台类目
    * @param subjectId
    * @return
    */
   @Override
	public StringResult getDisplayIdsBySubjectId(int subjectId) {
		 StringResult sr = new StringResult();
	     Result r = new Result(0);
	     try{
	    	 sr.setResult(r);
	 		 String value = this.subjectService.getDisplayIdsBySubjectId(subjectId) ;
	 		 sr.setValue(value);
	     }catch(Exception e){
	    	 logger.error("<<<<<<<< ---- getSuperTree4dis ---- get display super subject ----- error !!!" + e.getMessage(), e);
	         r.setCode(1);
	     }
		return sr;
	}

    /**
     * 根据标识，获取所有前台类目
     * @param type
     * @return
     */
    @Override
    public DisplaySubjectTreeResult getWholeTreeList4dis(String type) {
        DisplaySubjectTreeResult result = new DisplaySubjectTreeResult();
        List<DisplaySubjectTreeModel> models;
        try {
            models = this.displaySubjectService.getWholeTree(type);
            result.setDisplaySubjectNodes(super.rConvertDisSubjectNode(models));
            result.setResult(ResultGenerator.createCorrectResult());
        } catch (Exception e) {
            result.setResult(ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR));
        }
        return result;
    }

    /**
     * 条件查询后台分类
     * @param queryParam
     * @param page
     * @return
     * @throws TException
     */
    @Override
    public SubjectQueryResult querySubjects(SubjectQueryParam queryParam, Page page) throws TException {
        logger.info(">>>> querySubjects ---- query subject ---- ");
        SubjectQueryResult result = new SubjectQueryResult();
        result.setResult(new Result());
        Map queryMap = new HashMap();
        PageBean pageBean = super.convertPage(page);
        if(page != null) {
            queryMap.put("start", pageBean.getStart());
            queryMap.put("count", pageBean.getPageSize());
        }
        queryMap.put("status", queryParam.getStatus());
        queryMap.put("name", queryParam.getName());
        queryMap.put("pId", queryParam.getPId());

        List<SubjectInfo> subjectInfos = null;
        try {
            if(page != null) {
                //查询总数
                int total = this.subjectService.querySubjectsTotal(queryMap);
                //为page赋值
                page.setTotal(total);
                page.setPageCount(pageBean.getPageCount());
            }
            //查询记录
            List<TbSubject> subjects = this.subjectService.querySubjects(queryMap);
            subjectInfos = new ArrayList<>();
            //转换实体
            for (TbSubject subject : subjects) {
                subjectInfos.add(super.rConvertSubject(subject));
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< querySubjects ---- query subject error !!! \n" + e.getMessage(), e);
            result.getResult().setCode(1);
            return result;
        }
        result.getResult().setCode(0);
        result.setPage(page);
        result.setSubjectInfos(subjectInfos);
        logger.info("<<<< querySubjects ---- query subject ---- ");
        return result;
    }

    /**
     * 条件查询前台分类
     * @param queryParam
     * @param page
     * @return
     * @throws TException
     */
    @Override
    public DisplaySubjectQueryResult querySubjects4dis(DisplaySubjectQueryParam queryParam, Page page) throws TException {
        logger.info(">>>> querySubjects4dis ---- query display subject ---- ");
        DisplaySubjectQueryResult result = new DisplaySubjectQueryResult();
        result.setResult(new Result());
        Map queryMap = new HashMap();
        if(StringUtils.isBlank(queryParam.getType())) {
            result.setResult(ResultGenerator.createErrorResult(ResultGenerator.DISPLAY_SUJECT_NOT_EXIST));
            return result;
        }
        PageBean pageBean = super.convertPage(page);
        if(page != null) {
            queryMap.put("start", pageBean.getStart());
            queryMap.put("count", pageBean.getPageSize());
        }
        queryMap.put("type", queryParam.getType());
        queryMap.put("name", queryParam.getName());
        queryMap.put("pId", queryParam.getPId());

        List<DisplaySubjectInfo> subjectInfos = null;
        try {
            if(page != null) {
                //查询总数
                int total = this.displaySubjectService.queryDisplaySubjectsTotal(queryMap);
                //为page赋值
                page.setTotal(total);
                page.setPageCount(pageBean.getPageCount());
            }
            //查询记录
            List<TbDisplaySubject> subjects = this.displaySubjectService.queryDisplaySubjects(queryMap);
            subjectInfos = new ArrayList<>();
            //转换实体
            for (TbDisplaySubject subject : subjects) {
                subjectInfos.add(super.rConvertDisSubject(subject));
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< querySubjects4dis ---- query display subject error !!! \n" + e.getMessage(), e);
            result.getResult().setCode(1);
            return result;
        }
        result.getResult().setCode(0);
        result.setPage(page);
        result.setDisplaySubjectInfos(subjectInfos);
        logger.info("<<<< querySubjects4dis ---- query display subject ---- ");
        return result;
    }

    @Override
    public Result addChannel(DisplaySubjectChannelInfo channel) throws TException {
        logger.info(">>>> addChannel ---- add display channel ---- ");
        Result result = new Result(0);
        try {
            this.displaySubjectChannelService.add(super.convertDisplaySubjectChannel(channel));
        } catch (DuplicateKeyException e1) {
            logger.error("<<<<<<< addChannel ---- add display channel error !!! channel code : " + channel.getCode(), e1);
            return ResultGenerator.createErrorResult(ResultGenerator.DISPLAY_CHANNEL_IS_EXIST);
        } catch (Exception e) {
            logger.error("<<<<<<< addChannel ---- add display channel error !!! " + e.getMessage(), e);
            return ResultGenerator.createErrorResult(ResultGenerator.DISPLAY_CHANNEL_ADD_FAIL);
        }
        logger.info("<<<< addChannel ---- add display channel ---- ");
        return result;
    }

    @Override
    public Result updateChannel(DisplaySubjectChannelInfo channel) throws TException {
        logger.info(">>>> updateChannel ---- update display channel ---- ");
        Result result = new Result(0);
        try {
            this.displaySubjectChannelService.update(super.convertDisplaySubjectChannel(channel));
        } catch (Exception e) {
            logger.error("<<<<<<< updateChannel ---- update display channel error !!! " + e.getMessage(), e);
            result.setCode(1);
            return result;
        }
        logger.info("<<<< updateChannel ---- update display channel ---- ");
        return result;
    }

    @Override
    public Result deleteChannelById(int channelId) throws TException {
        logger.info(">>>> deleteChannelById ---- delete display channel ---- channelId : {}", channelId);
        Result result = new Result(0);
        try {
            this.displaySubjectChannelService.delete(channelId);
        } catch (Exception e) {
            logger.error("<<<<<<< deleteChannelById ---- update display channel error !!! " + e.getMessage(), e);
            result.setCode(1);
            return result;
        }
        logger.info("<<<< deleteChannelById ---- update display channel ---- channelId : {}", channelId);
        return result;
    }

    @Override
    public DisplaySubjectChannelQueryResult queryChannels(DisplaySubjectChannelQueryParam queryParam, Page page) throws TException {
        logger.info(">>>> queryChannels ---- query display channel ---- ");
        DisplaySubjectChannelQueryResult result = new DisplaySubjectChannelQueryResult();
        Map queryMap = new HashMap();
        PageBean pageBean = super.convertPage(page);
        if(page != null) {
            queryMap.put("start", pageBean.getStart());
            queryMap.put("count", pageBean.getPageSize());
        }
        queryMap.put("name", queryParam.getName());
        List<DisplaySubjectChannelInfo> channelInfos = new ArrayList<>();
        try {
            if(page != null) {
                int total = this.displaySubjectChannelService.queryChannelNum(queryMap);
                page.setTotal(total);
                page.setPageCount(pageBean.getPageCount());
            }
            List<TbDisplaySubjectChannel> channels = this.displaySubjectChannelService.queryChannel(queryMap);
            for (TbDisplaySubjectChannel channel : channels) {
                channelInfos.add(super.rConvertDisplaySubjectChannel(channel));
            }
        } catch (Exception e) {
            logger.error("<<<<<<< queryChannels ---- query display channel error !!! " + e.getMessage(), e);
            result.setResult(new Result(1));
            result.setPage(page);
            return result;
        }
        logger.info("<<<< queryChannels ---- query display channel ---- ");
        result.setResult(new Result(0));
        result.setChannelInfos(channelInfos);
        result.setPage(page);
        return result;
    }

    /**
     * 添加前台类目和后台类目关系
     * 1、如果映射关系中已存在选择的类目，或者选择类目的子类目[任一level子类目]，提示“与类目[类目ID:类目名称]重复”
     * 2、否则，保存前后台类目映射关系。
     * @param relation
     * @return
     * @throws TException
     */
    @Override
    public Result addRelation(SubjectRefDisplayInfo relation) throws TException {
        logger.info(">>>> addRelation ----  add subject and display relation ---- subjectId : {} -- displayId : {}", relation.getSubjectId(), relation.getDisplayId());
        List<TbSubject> subjects = null;
        try {
            TbSubject subject = this.subjectService.getById(relation.getSubjectId());
            //后台类目不存在
            if (subject == null) {
                return ResultGenerator.createErrorResult(ResultGenerator.SUJECT_NOT_EXIST);
            }
            //判断前台类目是否是叶子节点
            TbDisplaySubject displaySubject = this.displaySubjectService.getById(relation.getDisplayId());
            if(displaySubject.getIsLeaf() == 0) {
                return ResultGenerator.createErrorResult(ResultGenerator.DISPLAY_SUJECT_NOT_LEAF);
            }
            subjects = this.subjectRefDisplayService.addRelation(super.convertSubjectRefDisplay(relation));
        } catch (Exception e) {
            logger.error("<<<<<<<< addRelation ----  add subject and display relation ---- error!!!" + e.getMessage(), e);
            return ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        if (subjects == null) {
            logger.info("<<<< addRelation ----  add subject and display relation ---- subjectId : {} -- displayId : {}", relation.getSubjectId(), relation.getDisplayId());
            //添加成功，更新版本
            //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_REF_DISPLAY_MESSAGE);
            ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_REF_DISPLAY_MESSAGE.getBytes());
            return ResultGenerator.createCorrectResult();
        } else {
            List<FailDesc> failDescList = new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            for (TbSubject subject : subjects) {
                sb.append("与类目[").append(subject.getId()).append(":").append(subject.getName()).append("]重复");
                failDescList.add(new FailDesc("SubjectRefDisplay", "000000", sb.toString()));
                sb.delete(0, sb.length() - 1);
            }
            logger.error("<<<< addRelation ----  add subject and display relation ---- subjectId : {} -- displayId : {} error!!!", relation.getSubjectId(), relation.getDisplayId());
            Result result = new Result(1);
            result.setFailDescList(failDescList);
            return result;
        }
    }

    /**
     * 删除前台类目和后台类目的对应关系
     * @param relation
     * @return
     * @throws TException
     */
    @Override
    public Result deleteRelation(SubjectRefDisplayInfo relation) throws TException {
        logger.info(">>>> deleteRelation ---- delete relation --- subjectId : {}, displayId : {}", relation.getSubjectId(), relation.getDisplayId());
        Result result = new Result();
        try {
            this.subjectRefDisplayService.deleteRelation(convertSubjectRefDisplay(relation));
            result = ResultGenerator.createCorrectResult();
        } catch (Exception e) {
            logger.error("<<<<<<<< deleteRelation ---- delete relation ----- error!!!!" + e.getMessage(), e);
            result = ResultGenerator.createErrorResult(ResultGenerator.SUJECT_EXCEPTION_ERROR);
        }
        logger.info("<<<< deleteRelation ---- delete relation --- subjectId : {}, displayId : {}", relation.getSubjectId(), relation.getDisplayId());
        //删除成功，更新版本
        //this.jedisBaseDao.publish(Commons.SUBJECT_CHANNEL, Commons.SUBJECT_REF_DISPLAY_MESSAGE);
        ZookeeperUtil.writeData(Commons.SUBJECT_ZK_PATH, Commons.SUBJECT_REF_DISPLAY_MESSAGE.getBytes());
        return result;
    }

    @Override
    public SubjectQueryResult querySubjectsRelation(int displayId, Page page) throws TException {
        SubjectQueryResult result = new SubjectQueryResult();
        result.setResult(new Result(0));
        PageBean pageBean = super.convertPage(page);
        Map queryMap = new HashMap();
        queryMap.put("displayId", displayId);
        queryMap.put("start", pageBean.getStart());
        queryMap.put("count", pageBean.getPageSize());
        List<SubjectInfo> subjectInfos = new ArrayList<>();
        try {
            int total = this.subjectService.querySubjectsRelationNum(queryMap);
            page.setTotal(total);
            page.setPageCount(pageBean.getPageCount());
            List<TbSubject> subjects = this.subjectService.querySubjectsRelation(queryMap);
            for (TbSubject subject : subjects) {
                subjectInfos.add(rConvertSubject(subject));
            }
        } catch (Exception e) {
            logger.error("<<<<<<<< querySubjectsRelation ---- query subject by displayId ---- displayId : " + displayId + " --- error !!! " + e.getMessage(), e);
            result.getResult().setCode(1);
            result.setPage(page);
            return result;
        }
        result.setSubjectInfos(subjectInfos);
        result.setPage(page);
        return result;
    }

	@Override
	public Result addSubjectAttribute(SubjectAttribute atrribute)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result updateSubjectAttribute(SubjectAttribute atrribute)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result deleteSubjectAttribute(SubjectAttribute atrribute)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result deleteSubjectAttributeBatch(
			List<SubjectAttribute> atrributeList) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubjectAttributeResult querySubjectAttribute(
			SubjectAttributeQueryParam param) throws TException {
		// TODO Auto-generated method stub
		return null;
	}
    
}
