package com.jfshare.product.service;

import com.jfshare.product.model.TbThirdPartyProductSnapshotWithBLOBs;
import com.jfshare.product.model.TbThirdPartyProductWithBLOBs;

import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/28.
 */
public interface IWoMaiSvc {

    boolean importProduct(int sellerId, String path);

    void syncWoMaiProduct();

    TbThirdPartyProductWithBLOBs getProduct(String woMaiId);

    void saveWoMaiProduct(TbThirdPartyProductWithBLOBs productWithBLOBs);

    String getWoMaiWarehouseId(String storehouseId);

    List<String> getAllWoMaiWarehouseId();

    String getStorehouseId(String warehouseId);

    List<TbThirdPartyProductWithBLOBs> queryThirdPartyProductList(Map queryMap);

    int queryThirdPartyProductCount(Map queryMap);

    List<TbThirdPartyProductSnapshotWithBLOBs> queryThirdPartyProductSnapshotList(Map queryMap);

    int queryThirdPartyProductSnapshotCount(Map queryMap);

}
