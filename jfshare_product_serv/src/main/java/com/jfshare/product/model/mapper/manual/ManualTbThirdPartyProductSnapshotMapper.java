package com.jfshare.product.model.mapper.manual;

import com.jfshare.product.model.TbThirdPartyProductSnapshotWithBLOBs;

import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/8/5.
 */
public interface ManualTbThirdPartyProductSnapshotMapper {
    List<TbThirdPartyProductSnapshotWithBLOBs> queryThirdPartyProductSnapshotList(Map queryMap);

    int queryThirdPartyProductSnapshotCount(Map queryMap);
}
