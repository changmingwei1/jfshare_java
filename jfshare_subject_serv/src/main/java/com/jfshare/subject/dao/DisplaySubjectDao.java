package com.jfshare.subject.dao;

import com.jfshare.subject.bean.TbDisplaySubject;

import java.util.List;
import java.util.Map;


public interface DisplaySubjectDao extends BaseDAO<TbDisplaySubject, Integer> {

    /**
     * 根据pid查子类目
     *
     * @param pid
     * @return
     */
    //public List<TbDisplaySubject> selectByPid(Integer pid);

    /**
     * 根据subjectId查询类目树
     *
     * @param id
     * @return
     */
    //public DisplaySubjectTreeModel selectTreeById(Integer id);

    /**
     * 根据类别查询表现分类
     *
     * @param type
     * @return
     */
    public List<TbDisplaySubject> getWholeTreeByType(String type);

    /**
     * 查询所有表现分类
     *
     * @return
     */
    public List<TbDisplaySubject> getWholeTree();

    /**
     * 获取自增ID
     * @return
     */
    public Integer getId();

    /**
     * 删除类目，并删除所有子类目
     * @param subject
     */
    public void deleteSub(TbDisplaySubject subject);

    /**
     * 根据父ID查询子类目
     * @param pId
     * @return
     */
	List<TbDisplaySubject> selectByPid(Integer pId);

    /**
     * 条件查询类目数量
     * @param queryMap
     * @return
     */
    public int selectTotalByParam(Map queryMap);

    /**
     * 条件查询类目
     * @param queryMap
     * @return
     */
    public List<TbDisplaySubject> selectByParam(Map queryMap);
}
