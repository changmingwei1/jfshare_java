package com.jfshare.subject.dao;

import com.jfshare.subject.bean.TbSubject;

import java.util.List;
import java.util.Map;


public interface SubjectDao extends BaseDAO<TbSubject, Integer> {

    /**
     * 根据pid查子类目，无缓存
     *
     * @param pid
     * @return
     */
    public List<TbSubject> selectByPid(Integer pid);

    /**
     * 查询整个类目树
     * @return
     */
    public List<TbSubject> getWholeTree();

    /**
     * 获取自增ID
     * @return
     */
    public Integer getId();

    public TbSubject selectByIdManage(Integer id);

    /**
     * 删除类目，并删除所有子类目
     * @param subject
     */
    public void deleteSub(TbSubject subject);

    /**
     * 条件查询
     * @param queryMap
     * @return
     */
    public List<TbSubject> selectByParam(Map queryMap);

    /**
     * 根据条件查询类目总数
     * @return
     */
    public int selectTotalByParam(Map queryMap);

    /**
     * 根据前台类目ID查询对应的后台类目
     * @param queryMap
     * @return
     */
    public List<TbSubject> selectSubjectByDisplayId(Map queryMap);

    /**
     * 根据前台类目ID查询对应的后台类目数量
     * @param queryMap
     * @return
     */
    public int selectSubjectNumByDisplayId(Map queryMap);


    /**
     * 查询与前台类目有关系的后台子类目
     * @param queryMap
     * @return
     */
    public List<TbSubject> selectRelationByPath(Map queryMap);

    /**
     * 删除状态的类目发布操作
     * @param path
     */
    public void deleteRelease(String path);
}
