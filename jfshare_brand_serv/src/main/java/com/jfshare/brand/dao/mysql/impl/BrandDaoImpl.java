package com.jfshare.brand.dao.mysql.impl;

import com.jfshare.brand.dao.mysql.IBrandDao;
import com.jfshare.brand.model.TbBrand;
import com.jfshare.brand.model.TbBrandExample;
import com.jfshare.brand.model.mapper.TbBrandMapper;
import com.jfshare.utils.CommUtil;
import com.jfshare.finagle.thrift.brand.QueryParam;
import com.jfshare.utils.StringUtil;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Repository
public class BrandDaoImpl extends SqlSessionDaoSupport implements IBrandDao {
    @Autowired
    private TbBrandMapper tbBrandMapper;
    @Override
    public int queryCount(QueryParam param) {
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(CommUtil.DELETE_STATE);
        if (!StringUtil.isNullOrEmpty(param.getName())) {
            criteria.andNameLike(param.getName());
        }

        return tbBrandMapper.countByExample(example);
    }

    @Override
    public List<TbBrand> queryByPage(QueryParam param) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("conditions", param);
        int curPage = param.getCurPage() <=1 ? 1 : param.getCurPage();
        if (param.getPageSize() > 0) {
            int start = (curPage-1) * param.getPageSize();
            int end = param.getPageSize();
            paraMap.put("start", start);
            paraMap.put("end", end);
        }

        return this.getSqlSession().selectList("queryByPage", paraMap);
    }

    @Override
    public List<TbBrand> query() {
        TbBrandExample example = new TbBrandExample();
        TbBrandExample.Criteria criteria = example.createCriteria();
        criteria.andStateNotEqualTo(CommUtil.DELETE_STATE);

        return tbBrandMapper.selectByExample(example);
    }

    @Override
    public List<TbBrand> queryByIds(List<Integer> validIdList) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("idList", validIdList);

        return this.getSqlSession().selectList("queryByIds", paraMap);
    }

	@Override
	public int addBrand(TbBrand brand) {
		return tbBrandMapper.insert(brand);
	}

	@Override
	public int deleteBrand(int id) {
//		TbBrandExample example = new TbBrandExample();
//	    TbBrandExample.Criteria criteria = example.createCriteria();
//	    criteria.andIdEqualTo(id);
	    TbBrand brand = new TbBrand();
	    brand.setId(id);
	    brand.setState(1);
	    return tbBrandMapper.updateByPrimaryKeySelective(brand);
	    
	}

	@Override
	public int updateBrand(TbBrand brand) {
		return tbBrandMapper.updateByPrimaryKeySelective(brand);
	}
    
    
}
