package com.jfshare.subject.service;

import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.bean.TbSubjectBrandExample;
import com.jfshare.subject.bean.TbSubjectBrandKey;
import com.jfshare.subject.bean.mapper.TbSubjectBrandMapper;
import com.jfshare.subject.dao.APIServerDAO;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class SubjectRefBrandService {

    private Logger logger = LoggerFactory.getLogger(SubjectRefBrandService.class);

    @Autowired
    private TbSubjectBrandMapper tbSubjectBrandMapper;

    @Resource(name="apiServerDAOImpl")
    private APIServerDAO apiServerDAO;

    @Autowired
    private JedisBaseDao jedisBaseDao;

    /**
     * 查询科目节点关联的品牌信息
     * @param tbSubject
     */
    public List<BrandInfo> getBrands(TbSubject tbSubject) throws Exception {
//        String str = jedisBaseDao.putKV("test",new Random().nextInt(100)+"");
//        System.out.println("========>>>>"+str);
        //非叶子节点无关联品牌
        if(tbSubject.getIsLeaf() == null || tbSubject.getIsLeaf() == 0) {
            return null;
        }

        List<Integer> brandIds = null;

        TbSubjectBrandExample example = new TbSubjectBrandExample();
        TbSubjectBrandExample.Criteria criteria = example.createCriteria();
        criteria.andSubjectIdEqualTo(tbSubject.getId());
        List<TbSubjectBrandKey> tbSubjectBrandKeys = tbSubjectBrandMapper.selectByExample(example);

        if(CollectionUtils.isEmpty(tbSubjectBrandKeys)) {
            return null;
        }

        brandIds = new ArrayList<>();
        for (TbSubjectBrandKey tbSubjectBrandKey : tbSubjectBrandKeys) {
            brandIds.add(tbSubjectBrandKey.getBrandId());
        }
        return apiServerDAO.queryBrands(brandIds);
    }
}
