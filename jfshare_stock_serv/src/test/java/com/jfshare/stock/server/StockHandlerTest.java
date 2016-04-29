package com.jfshare.stock.server;

import com.jfshare.finagle.thrift.stock.*;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
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
    @org.junit.Test
    public void testCreateStock() throws Exception {
        //商品id
        String productId = "ze0001test";
        try {
            ////////////////////////////////////////////////////
            StockInfo si = new StockInfo();
            StockItem item3 = new StockItem();
            item3.setSkuNum("");
            item3.setCount(100);
            item3.setStorehouseId("0");
            si.addToStockItems(item3);
            si.setProductId(productId);
            ////////////////////////////////////////////////////
            System.err.println(client.createStock("testCreateStock", si));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    /**
     * 新增库存信息
     * @throws Exception
     */
    @org.junit.Test
    public void testQueryStock() throws Exception {
        //商品id
        String productId = "ze0000test";
        try {
            ////////////////////////////////////////////////////
            QueryParam p = new QueryParam();
            p.setStorehouseId("2");
            p.setQueryType("sku");
            p.setProductId(productId);
            p.setSkuNum("100-100:100-100");
            ////////////////////////////////////////////////////
            System.err.println(client.queryStock(p));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }
}
