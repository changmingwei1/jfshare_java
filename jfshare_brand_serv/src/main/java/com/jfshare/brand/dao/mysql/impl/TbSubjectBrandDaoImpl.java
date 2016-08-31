package com.jfshare.brand.dao.mysql.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jfshare.brand.dao.mysql.ISubjectBrandDao;
import com.jfshare.brand.model.TbSubjectBrandExample;
import com.jfshare.brand.model.TbSubjectBrandKey;
import com.jfshare.brand.model.mapper.TbSubjectBrandMapper;

@Repository
public class TbSubjectBrandDaoImpl extends SqlSessionDaoSupport implements ISubjectBrandDao{
	@Autowired
    private TbSubjectBrandMapper tbSubjectBrandMapper;

	@Override
	public List<TbSubjectBrandKey> queryBySubjectId(int id) {        
        TbSubjectBrandExample example = new TbSubjectBrandExample();
        TbSubjectBrandExample.Criteria criteria = example.createCriteria();
        criteria.andSubjectIdEqualTo(id);
        return tbSubjectBrandMapper.selectByExample(example);
	}
	
	
}
