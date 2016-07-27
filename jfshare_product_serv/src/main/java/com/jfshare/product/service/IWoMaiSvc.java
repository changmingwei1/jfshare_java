package com.jfshare.product.service;

/**
 * Created by Lenovo on 2016/4/28.
 */
public interface IWoMaiSvc {

    boolean importProduct(int sellerId, String path);

    void syncWoMaiProduct();

    String getWoMaiWarehouseId(String storehouseId);

    String getStorehouseId(String warehouseId);

}
