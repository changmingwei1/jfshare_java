package com.jfshare.subject.dao;

import com.jfshare.subject.bean.TbProductAttribute;
import java.util.List;


public interface ProductAttributeDao extends BaseDAO<TbProductAttribute, Integer> {

    /**
     * 根据科目ID查询所有属性
     * @param subjectId
     * @return
     */
    public List<TbProductAttribute> selectBySubjectId(Integer subjectId);

    /**
     * 根据科目ID查询所有属性
     * @param ids
     * @return
     */
    public List<TbProductAttribute> selectByIds(String ids);
}
