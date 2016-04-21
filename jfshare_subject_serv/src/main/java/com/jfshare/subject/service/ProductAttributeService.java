package com.jfshare.subject.service;

import com.jfshare.subject.bean.TbProductAttribute;
import com.jfshare.subject.dao.ProductAttributeDao;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Transactional
@Service
public class ProductAttributeService {

    private Logger logger = LoggerFactory.getLogger(ProductAttributeService.class);

    @Resource(name = "productAttributeDaoImpl")
    private ProductAttributeDao productAttributeDao;

    @Transactional(propagation= Propagation.NEVER)
    public TbProductAttribute getById(Integer id) {
        return this.productAttributeDao.getById(id);
    }

    public void add(TbProductAttribute tbProductAttribute) {
        tbProductAttribute.setCreateTime(new DateTime());
        tbProductAttribute.setUpdateTime(new DateTime());
        this.productAttributeDao.add(tbProductAttribute);
    }

    public void update(TbProductAttribute tbProductAttribute) {
        tbProductAttribute.setUpdateTime(new DateTime());
        this.productAttributeDao.update(tbProductAttribute);
    }

    public void delete(Integer id) {
        this.productAttributeDao.delete(id);
    }

    /**
     * 根据类目ID查询属性信息
     * @param subjectId
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<TbProductAttribute> selectBySubjectId(Integer subjectId) {
        return this.productAttributeDao.selectBySubjectId(subjectId);
    }

    /**
     * 根据类目ID查询属性信息
     * @param ids
     * @return
     */
    @Transactional(propagation= Propagation.NEVER)
    public List<TbProductAttribute> selectByIds(String ids) {
        return this.productAttributeDao.selectByIds(ids);
    }

}
