package com.jfshare.subject.dao.impl.mybatis;

import com.jfshare.subject.bean.TbProductAttribute;
import com.jfshare.subject.dao.ProductAttributeDao;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.util.Commons;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductAttributeDaoImpl extends BaseDAOImpl<TbProductAttribute, Integer> implements ProductAttributeDao {

    private Logger logger = LoggerFactory.getLogger(ProductAttributeDaoImpl.class);

    @Resource
    private JedisBaseDao jedisBaseDao;

    @Override
    public TbProductAttribute getById(Integer id) {
        TbProductAttribute tbProductAttribute = new TbProductAttribute();
        Map attribute = this.jedisBaseDao.getMapAll(Commons.PRODUCT_ATTRIBUTE_KEY + id);
        //Map attribute = null;
        if(MapUtils.isNotEmpty(attribute)) {
            tbProductAttribute.deSerialize(attribute);
            return tbProductAttribute;
        } else {
            //从数据库中查询
            tbProductAttribute = super.getById(id);
            if (tbProductAttribute != null) {
                //加入缓存
                this.jedisBaseDao.putMap(Commons.PRODUCT_ATTRIBUTE_KEY + id, tbProductAttribute.serialize());
            }
        }
        return tbProductAttribute;
    }

    @Override
    public Integer add(TbProductAttribute tbProductAttribute) {
        logger.info(">>>> add ---- add attribute ---- id : {}", tbProductAttribute.getId());
        this.jedisBaseDao.removeMap(Commons.SUBJECT_KEY + tbProductAttribute.getSubjectId(), "attributeIds");
        return super.add(tbProductAttribute);
    }

    @Override
    public void update(TbProductAttribute tbProductAttribute) {
        logger.info(">>>> update ---- update attribute ---- id : {}", tbProductAttribute.getId());
        super.update(tbProductAttribute);
        this.jedisBaseDao.removeKV(tbProductAttribute.getCacheKey());
        this.jedisBaseDao.removeMap(Commons.SUBJECT_KEY + tbProductAttribute.getSubjectId(), "attributeIds");
        logger.info("<<<< update ---- update attribute ---- id : {}", tbProductAttribute.getId());
    }

    @Override
    public void delete(Integer id) {
        logger.info(">>>> delete ---- delete attribute ---- id : {}", id);
        TbProductAttribute tbProductAttribute = this.getById(id);
        super.delete(id);
        this.jedisBaseDao.removeMap(Commons.SUBJECT_KEY + tbProductAttribute.getSubjectId(), "attributeIds");
        this.jedisBaseDao.removeKV(Commons.PRODUCT_ATTRIBUTE_KEY + id);
        logger.info("<<<< delete ---- delete attribute ---- id : {}", id);
    }

    @Override
    public List<TbProductAttribute> selectBySubjectId(Integer subjectId) {
        List<TbProductAttribute> tbProductAttributes = null;
        //从缓存中获取科目和属性关系
        String attributeIds = this.jedisBaseDao.getMap(Commons.SUBJECT_KEY + subjectId, "attributeIds");
        if(StringUtils.isNotBlank(attributeIds)) {
            //从缓存中获取
            tbProductAttributes = new ArrayList<>();
            for(String id : attributeIds.split(",")) {
                TbProductAttribute tbProductAttribute = this.getById(Integer.parseInt(id));
                tbProductAttributes.add(tbProductAttribute);
            }
        } else {
            //从数据库中获取类目和属性关系，并放置到缓存中
            tbProductAttributes = this.getSqlSession().selectList(TbProductAttribute.class.getName() + ".selectBySubjectId", subjectId);
            //放入到缓存中
            String conStr = "";
            StringBuffer sb = new StringBuffer("");
            for (TbProductAttribute tbProductAttribute : tbProductAttributes) {
                sb.append(conStr).append(tbProductAttribute.getId());
                conStr = ",";
            }
            if(StringUtils.isNotBlank(sb.toString())) {
                this.jedisBaseDao.putMap(Commons.SUBJECT_KEY + subjectId, "attributeIds", sb.toString());
            }

        }
        return tbProductAttributes;
    }

    @Override
    public List<TbProductAttribute> selectByIds(String ids) {
        logger.info("selectByIds ==> ids:{}", ids);
        List<TbProductAttribute> attributes = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)) {
            //从缓存中获取
            attributes = new ArrayList<>();
            for (String id : ids.split(",")) {
                TbProductAttribute tbProductAttribute = this.getById(Integer.parseInt(id));
                attributes.add(tbProductAttribute);
            }
        }
        return attributes;
    }
}
