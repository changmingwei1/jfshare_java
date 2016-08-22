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

    /**
     * 同步我买网商品
     */
    void syncWoMaiProduct();

    /**
     * 获取我买网单个商品
     * @param woMaiId
     * @return
     */
    TbThirdPartyProductWithBLOBs getProduct(String woMaiId);

    /**
     * 保存我买网商品
     * @param productWithBLOBs
     */
    void saveWoMaiProduct(TbThirdPartyProductWithBLOBs productWithBLOBs);

    /**
     * 根据仓库ID获取我买网仓库ID
     * @param storehouseId
     * @return
     */
    String getWoMaiWarehouseId(String storehouseId);

    /**
     * 获取所有我买网仓库ID
     * @return
     */
    List<String> getAllWoMaiWarehouseId();

    /**
     * 根据我买网仓库ID获取仓库ID
     * @param warehouseId
     * @return
     */
    String getStorehouseId(String warehouseId);

    /**
     * 查询同步的我买网商品信息
     * @param queryMap
     * @return
     */
    List<TbThirdPartyProductWithBLOBs> queryThirdPartyProductList(Map queryMap);

    /**
     * 查询商品数量
     * @param queryMap
     * @return
     */
    int queryThirdPartyProductCount(Map queryMap);

    /**
     * 查询日志
     * @param queryMap
     * @return
     */
    List<TbThirdPartyProductSnapshotWithBLOBs> queryThirdPartyProductSnapshotList(Map queryMap);

    /**
     * 查询日志数量
     * @param queryMap
     * @return
     */
    int queryThirdPartyProductSnapshotCount(Map queryMap);

    /**
     * 提报商品
     * @param productWithBLOBs
     * @return
     */
    int offerThirdPartyProduct(TbThirdPartyProductWithBLOBs productWithBLOBs);

    /**
     * 导出第三方商品
     * 表格字段包含：商家名称、商品名称、商品在第三方平台的id、商品在聚分享的id、当前结算价、聚分享销售价（如有）、
     * 库存（按仓库分别显示）、商品在第三方平台的上架状态、商品提报状态、第三方平台商品链接、聚分享平台商品链接
     * @param queryMap
     * @return
     */
    String exportThirdPartyProduct(Map queryMap);

}
