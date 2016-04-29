package com.jfshare.stock.server;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.stock.*;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StockHandlerTest extends TestCase {

    private TTransport transport;
    private StockServ.Client client;

    @Override
    public void setUp() throws Exception {

//        transport = new TFramedTransport(new TSocket("120.24.153.155", 1983));
        transport = new TFramedTransport(new TSocket("localhost", 1983));

        TProtocol protocol = new TBinaryProtocol(transport);
        client = new StockServ.Client(protocol);
        transport.open();
    }

    @Override
    public void tearDown(){
        transport.close();
    }

    /**
     * 新增库存信息
     * @throws Exception
     */
    public void testCreateStock() throws Exception {
        try {
            //////////////////////sku商品创建//////////////////////////////
            String productId = "ze0000test";
            StockInfo si = new StockInfo();
            StockItem item3 = new StockItem();
            item3.setSkuNum("100-100:100-100");
            item3.setCount(100);
            item3.setStorehouseId(1);
            si.addToStockItems(item3);

            StockItem item5 = new StockItem();
            item5.setSkuNum("100-100:200-200");
            item5.setCount(100);
            item5.setStorehouseId(2);
            si.addToStockItems(item5);

            si.setProductId(productId);
            Result testCreateStock = client.createStock("testCreateStock", si);
            Assert.assertEquals(testCreateStock.getCode(), 0);
            ////////////////////////////////////////////////////

            //////////////////////无sku商品创建//////////////////////////////
            String productId1 = "ze0001test";
            StockInfo si1 = new StockInfo();
            StockItem item1 = new StockItem();
            item1.setSkuNum("");
            item1.setCount(50);
            item1.setStorehouseId(0);
            si1.addToStockItems(item1);

            si1.setProductId(productId1);
            Result testCreateStock1 = client.createStock("testCreateStock", si1);
            Assert.assertEquals(testCreateStock1.getCode(), 0);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 删除库存测试
     */
    public void testRemoveStock(){
        String productId = "ze0001test";

        try {
            Result result = client.removeStock(productId);
            Assert.assertEquals(result.getCode(), 0);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置库存测试
     */
    public void testSetStock(){
        try {
            //////////////////////sku商品创建//////////////////////////////
            String productId = "ze0000test";
            StockInfo si = new StockInfo();
            StockItem item3 = new StockItem();
            item3.setSkuNum("100-100:100-100");
            item3.setCount(100);
            item3.setStorehouseId(1);
            si.addToStockItems(item3);

            StockItem item4 = new StockItem();
            item4.setSkuNum("100-100:200-200");
            item4.setCount(100);
            item4.setStorehouseId(1);
            si.addToStockItems(item4);

            StockItem item5 = new StockItem();
            item5.setSkuNum("100-100:200-200");
            item5.setCount(100);
            item5.setStorehouseId(2);
            si.addToStockItems(item5);

            si.setProductId(productId);
            Result testSetStock = client.setStock("testSetStock", si);
            Assert.assertEquals(testSetStock.getCode(), 0);
            ////////////////////////////////////////////////////

            //////////////////////无sku商品创建//////////////////////////////
            String productId1 = "ze0001test";
            StockInfo si1 = new StockInfo();
            StockItem item1 = new StockItem();
            item1.setSkuNum("");
            item1.setCount(100);
            item1.setStorehouseId(0);
            si1.addToStockItems(item1);

            si1.setProductId(productId1);
            Result testSetStock1 = client.setStock("testSetStock", si1);
            Assert.assertEquals(testSetStock1.getCode(), 0);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 查询商品维度库存
     * @throws Exception
     */
    public void testQueryStock4Sku() throws Exception {

        try {
            ////////////////////////////////////////////////////
            //sku商品
            String productId = "ze0000test";
            QueryParam p = new QueryParam();
            p.setStorehouseId(2);
            p.setQueryType("sku");
            p.setProductId(productId);
            p.setSkuNum("100-100:100-100");
            StockResult stockResult = client.queryStock(p);
            System.err.println("testQueryStock4Sku==> " + stockResult);
            Assert.assertEquals(stockResult.getResult().getCode(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getTotal(), 100);
            Assert.assertEquals(stockResult.getStockInfo().getStockItems().size(), 1);

            //无sku商品
            QueryParam p1 = new QueryParam();
            p1.setProductId("ze0001test");
            p1.setStorehouseId(0);
            p1.setSkuNum("");
            p1.setQueryType("sku");
            StockResult stockResult1 = client.queryStock(p1);
            System.err.println("testQueryStock4Sku==> " + stockResult1);
            Assert.assertEquals(stockResult1.getResult().getCode(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getTotal(), 100);
            Assert.assertEquals(stockResult1.getStockInfo().getStockItems().size(), 1);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询仓库维度库存
     * @throws Exception
     */
    public void testQueryStock4Storehouse() throws Exception {

        try {
            ////////////////////////////////////////////////////
            //sku商品
            QueryParam p = new QueryParam();
            p.setStorehouseId(1);
            p.setProductId("ze0000test");
            p.setQueryType("storehouse");
            StockResult stockResult = client.queryStock(p);
            System.err.println("testQueryStock4Storehouse==> " + stockResult);
            Assert.assertEquals(stockResult.getResult().getCode(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getTotal(), 200);
            Assert.assertEquals(stockResult.getStockInfo().getStockItems().size(), 2);

            //无sku商品
            QueryParam p1 = new QueryParam();
            p1.setProductId("ze0001test");
            p1.setStorehouseId(0);
            p1.setQueryType("storehouse");
            StockResult stockResult1 = client.queryStock(p1);
            System.err.println("testQueryStock4Storehouse==> " + stockResult1);
            Assert.assertEquals(stockResult1.getResult().getCode(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getTotal(), 100);
            Assert.assertEquals(stockResult1.getStockInfo().getStockItems().size(), 1);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询仓库维度库存
     * @throws Exception
     */
    public void testQueryStock4Product() throws Exception {
        try {
            ////////////////////////////////////////////////////
            //sku商品
            QueryParam p = new QueryParam();
            p.setProductId("ze0000test");
            p.setQueryType("product");
            StockResult stockResult = client.queryStock(p);
            System.err.println("testQueryStock4Product==> " + stockResult);
            Assert.assertEquals(stockResult.getResult().getCode(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getTotal(), 300);
            Assert.assertEquals(stockResult.getStockInfo().getStockItems().size(), 3);

            //无sku商品
            QueryParam p1 = new QueryParam();
            p1.setProductId("ze0001test");
            p1.setStorehouseId(0);
            p1.setQueryType("product");
            StockResult stockResult1 = client.queryStock(p1);
            System.err.println("testQueryStock4Product==> " + stockResult1);
            Assert.assertEquals(stockResult1.getResult().getCode(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getTotal(), 100);
            Assert.assertEquals(stockResult1.getStockInfo().getStockItems().size(), 1);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 批量查询仓库维度库存
     * @throws Exception
     */
    public void testBatchQueryStock() throws Exception {
        try {
            ////////////////////////////////////////////////////
            BatchQueryParam p = new BatchQueryParam();
            p.addToQueryContents("ze0001test");
            p.addToQueryContents("ze0000test");
            p.setQueryType("all");
            BatchStockResult batchStockResult = client.batchQueryStock(p);
            System.err.println("testBatchQueryStock==> " + batchStockResult);
            Assert.assertEquals(batchStockResult.getResult().getCode(), 0);
            Assert.assertEquals(batchStockResult.getStockInfosSize(), 2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 锁库存测试
     * @throws Exception
     */
    public void testLockStock() throws Exception {
        try {
            ////////////////////////////////////////////////////
            List<LockInfo> lockInfos = new ArrayList<>();
            LockInfo lockInfoSku = new LockInfo();
            lockInfoSku.setProductId("ze0000test");
            lockInfoSku.setStorehouseId(1);
            lockInfoSku.setSkuNum("100-100:100-100");
            lockInfoSku.setApplyCount(10);
            lockInfos.add(lockInfoSku);

            LockInfo lockInfoNotSku = new LockInfo();
            lockInfoNotSku.setProductId("ze0001test");
            lockInfoNotSku.setStorehouseId(0);
            lockInfoNotSku.setSkuNum("");
            lockInfoNotSku.setApplyCount(10);
            lockInfos.add(lockInfoNotSku);

            LockStockResult testLockStock = client.lockStock("testLockStock", lockInfos);
            System.err.println("testLockStock==> " + testLockStock);

            Assert.assertEquals(testLockStock.result.code, 0);
            Assert.assertEquals(testLockStock.getLockInfoListSize(), 2);
            Assert.assertEquals(testLockStock.getLockInfoList().get(0).getLockCount(), 10);
            Assert.assertEquals(testLockStock.getLockInfoList().get(1).getLockCount(), 10);

            //sku商品
            QueryParam p = new QueryParam();
            p.setProductId("ze0000test");
            p.setQueryType("product");
            StockResult stockResult = client.queryStock(p);
            Assert.assertEquals(stockResult.getResult().getCode(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getTotal(), 290);
            Assert.assertEquals(stockResult.getStockInfo().getLockTotal(), 10);
            Assert.assertEquals(stockResult.getStockInfo().getStockItems().size(), 3);

            //无sku商品
            QueryParam p1 = new QueryParam();
            p1.setProductId("ze0001test");
            p1.setStorehouseId(0);
            p1.setQueryType("product");
            StockResult stockResult1 = client.queryStock(p1);
            Assert.assertEquals(stockResult1.getResult().getCode(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getTotal(), 90);
            Assert.assertEquals(stockResult1.getStockInfo().getLockTotal(), 10);
            Assert.assertEquals(stockResult1.getStockInfo().getStockItems().size(), 1);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 还原库存测试
     * @throws Exception
     */
    public void testReleaseStock() throws Exception {
        try {
            ////////////////////////////////////////////////////
            List<LockInfo> lockInfos = new ArrayList<>();
            LockInfo lockInfoSku = new LockInfo();
            lockInfoSku.setProductId("ze0000test");
            lockInfoSku.setStorehouseId(1);
            lockInfoSku.setSkuNum("100-100:100-100");
            lockInfoSku.setApplyCount(10);
            lockInfos.add(lockInfoSku);

            LockInfo lockInfoNotSku = new LockInfo();
            lockInfoNotSku.setProductId("ze0001test");
            lockInfoNotSku.setStorehouseId(0);
            lockInfoNotSku.setSkuNum("");
            lockInfoNotSku.setApplyCount(10);
            lockInfos.add(lockInfoNotSku);

            Result releaseStock = client.releaseStock("testLockStock", lockInfos);
            System.err.println("testReleaseStock==> " + releaseStock);

            Assert.assertEquals(releaseStock.code, 0);

            //sku商品
            QueryParam p = new QueryParam();
            p.setProductId("ze0000test");
            p.setQueryType("product");
            StockResult stockResult = client.queryStock(p);
            Assert.assertEquals(stockResult.getResult().getCode(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getTotal(), 300);
            Assert.assertEquals(stockResult.getStockInfo().getLockTotal(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getStockItems().size(), 3);

            //无sku商品
            QueryParam p1 = new QueryParam();
            p1.setProductId("ze0001test");
            p1.setStorehouseId(0);
            p1.setQueryType("product");
            StockResult stockResult1 = client.queryStock(p1);
            Assert.assertEquals(stockResult1.getResult().getCode(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getTotal(), 100);
            Assert.assertEquals(stockResult1.getStockInfo().getLockTotal(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getStockItems().size(), 1);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 还原库存测试
     * @throws Exception
     */
    public void testReleaseLockCount() throws Exception {
        try {
            ////////////////////////////////////////////////////
            List<LockInfo> lockInfos = new ArrayList<>();
            LockInfo lockInfoSku = new LockInfo();
            lockInfoSku.setProductId("ze0000test");
            lockInfoSku.setStorehouseId(1);
            lockInfoSku.setSkuNum("100-100:100-100");
            lockInfoSku.setApplyCount(10);
            lockInfos.add(lockInfoSku);

            LockInfo lockInfoNotSku = new LockInfo();
            lockInfoNotSku.setProductId("ze0001test");
            lockInfoNotSku.setStorehouseId(0);
            lockInfoNotSku.setSkuNum("");
            lockInfoNotSku.setApplyCount(10);
            lockInfos.add(lockInfoNotSku);

            Result releaseStock = client.releaseLockCount("testLockStock", lockInfos);
            System.err.println("testReleaseLockCount==> " + releaseStock);

            Assert.assertEquals(releaseStock.code, 0);

            //sku商品
            QueryParam p = new QueryParam();
            p.setProductId("ze0000test");
            p.setQueryType("product");
            StockResult stockResult = client.queryStock(p);
            Assert.assertEquals(stockResult.getResult().getCode(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getTotal(), 290);
            Assert.assertEquals(stockResult.getStockInfo().getLockTotal(), 0);
            Assert.assertEquals(stockResult.getStockInfo().getStockItems().size(), 3);

            //无sku商品
            QueryParam p1 = new QueryParam();
            p1.setProductId("ze0001test");
            p1.setStorehouseId(0);
            p1.setQueryType("product");
            StockResult stockResult1 = client.queryStock(p1);
            Assert.assertEquals(stockResult1.getResult().getCode(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getTotal(), 90);
            Assert.assertEquals(stockResult1.getStockInfo().getLockTotal(), 0);
            Assert.assertEquals(stockResult1.getStockInfo().getStockItems().size(), 1);
            ////////////////////////////////////////////////////
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 核心流程测试
     */
    public void testMain() throws Exception {
        //删除已有库存信息
        client.removeStock("ze0001test");
        client.removeStock("ze0000test");

        //测试创建库存
        testCreateStock();

        //测试修改库存
        testSetStock();

        //测试下单锁库存,未支付返还库存
        testLockStock();
        testReleaseStock();

        //测试商品维度查询库存
        testQueryStock4Product();
        //测试仓库维度查询库存
        testQueryStock4Storehouse();
        //测试SKU维度查询库存
        testQueryStock4Sku();

        //测试批量查询
        testBatchQueryStock();

        //测试下单， 支付成功释放库存
        testLockStock();
        testReleaseLockCount();
    }
}
