package com.jfshare.subject.dao.impl.api;

import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.subject.dao.APIServerDAO;
import com.jfshare.subject.dependserv.BrandClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * api服务接口
 * @author sus
 *
 */
@Service("apiServerDAOImpl")
public class APIServerDAOImpl implements APIServerDAO {

	@Autowired
	private BrandClient brandClient;

	private static Logger logger = LoggerFactory.getLogger(APIServerDAOImpl.class);

    @Override
    public List<BrandInfo> queryBrands(List<Integer> brandIds) throws Exception{
        return brandClient.getBrandsByIds(brandIds);
    }
}
