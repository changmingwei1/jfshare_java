package com.jfshare.product.model.mapper.manual;

import com.jfshare.product.model.TbThirdPartyProductWithBLOBs;

import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/8/5.
 */
public interface ManualTbThirdPartyProductMapper {

    List<TbThirdPartyProductWithBLOBs> queryThirdPartyProductList(Map queryMap);

    int queryThirdPartyProductCount(Map queryMap);
}
