package com.jfshare.brand.service.impl;

import com.jfshare.brand.dao.mysql.IBrandDao;
import com.jfshare.brand.model.TbBrand;
import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.finagle.thrift.brand.QueryParam;
import com.jfshare.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
@Service
public class BrandSvcImpl implements com.jfshare.brand.service.IBrandSvc {
    @Autowired
    private IBrandDao brandDaoImpl;
    @Override
    public int queryCount(QueryParam param) {
        return brandDaoImpl.queryCount(param);
    }

    @Override
    public List<BrandInfo> queryByPage(QueryParam param) {
        List<BrandInfo> brandInfos = new ArrayList<BrandInfo>();
        List<TbBrand> tbBrands = brandDaoImpl.queryByPage(param);
        for(TbBrand item : tbBrands) {
            BrandInfo brandInfo = new BrandInfo();
            Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(item);
            BeanUtil.fillBeanData(brandInfo, stringObjectMap);
            brandInfos.add(brandInfo);
        }

        return brandInfos;
    }

    @Override
    public List<BrandInfo> query() {
        List<BrandInfo> brandInfos = new ArrayList<BrandInfo>();
        List<TbBrand> tbBrands = brandDaoImpl.query();
        for(TbBrand item : tbBrands) {
            BrandInfo brandInfo = new BrandInfo();
            Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(item);
            BeanUtil.fillBeanData(brandInfo, stringObjectMap);
            brandInfos.add(brandInfo);
        }

        return brandInfos;
    }

    @Override
    public List<BrandInfo> queryByIds(List<Integer> validIdList) {
        List<BrandInfo> brandInfos = new ArrayList<BrandInfo>();
        List<TbBrand> tbBrands = brandDaoImpl.queryByIds(validIdList);
        for(TbBrand item : tbBrands) {
            BrandInfo brandInfo = new BrandInfo();
            Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(item);
            BeanUtil.fillBeanData(brandInfo, stringObjectMap);
            brandInfos.add(brandInfo);
        }

        return brandInfos;
    }
}
