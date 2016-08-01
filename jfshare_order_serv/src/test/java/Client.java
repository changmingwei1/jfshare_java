import com.jfshare.finagle.thrift.order.*;
import com.jfshare.finagle.thrift.pay.PayChannel;
import junit.framework.TestCase;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client extends TestCase{

    private TTransport transport;
    private OrderServ.Client client;

    @Override
    public void setUp() throws Exception {

//        transport = new TFramedTransport(new TSocket("101.201.39.12", 1986));
        transport = new TFramedTransport(new TSocket("101.201.38.182", 1986));
//        transport = new TFramedTransport(new TSocket("101.201.37.237", 1986));
//        transport = new TFramedTransport(new TSocket("120.24.153.155", 1986));
//        transport = new TFramedTransport(new TSocket("127.0.0.1", 1986));

        TProtocol protocol = new TBinaryProtocol(transport);
        client = new OrderServ.Client(protocol);
        transport.open();
    }

    @Override
    public void tearDown(){
        transport.close();
    }

    /**
     * @throws Exception
     */

    public void testQueryOrderDetail() throws Exception {
        try {
            ////////////////////////////////////////////////////

            System.err.println(client.queryOrderDetail(1, 62, "100105438"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }


    public void testOrderProfileQuery() throws Exception {
        //商品id
        String orderId = "1";
        try {
            ////////////////////////////////////////////////////
            OrderQueryConditions conditions = new OrderQueryConditions();
            conditions.setSellerId(13);
            conditions.setCurPage(172);
//            conditions.setOrderState(61);
//            conditions.addToOrderIds("18120090");
            System.err.println(client.orderProfileQuery(2, 1, conditions));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }


    public void testOrderProfileQueryBuyer() throws Exception {
        //商品id
        String orderId = "1";
        try {
            ////////////////////////////////////////////////////
            OrderQueryConditions conditions = new OrderQueryConditions();
            conditions.setUserId(1111);
            conditions.setCurPage(1);
            conditions.setCount(3);
            conditions.setOrderState(1);
            System.err.println(client.orderProfileQuery(2, 1, conditions));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    public void testOrderProfileQueryFull() throws Exception {

        String orderId = "1";
        try {
            ////////////////////////////////////////////////////
            OrderQueryConditions conditions = new OrderQueryConditions();
//            conditions.setCurPage(2);
//            conditions.setCount(3);
//            conditions.setStartTime("2016-07-01 00:00:00");
//            conditions.setEndTime("2016-07-20 00:00:00");
//            conditions.setOrderState(5);
            conditions.setOrderId("100105438");
//            conditions.addToOrderIds("100105438");
//            conditions.addToOrderIds("95200327");
            System.err.println(client.orderProfileQueryFull(conditions));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }


    public void testDeliver() throws Exception {
        //商品id
        String orderId = "1";
        try {
            ////////////////////////////////////////////////////
            DeliverInfo deliverInfo = new DeliverInfo();
            deliverInfo.setOrderId("1911111");
            deliverInfo.setUserId(1111);
            System.err.println(client.deliver(1000, deliverInfo));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    public void testDeliverVir() throws Exception {
        //商品id
        String orderId = "1";
        try {
            ////////////////////////////////////////////////////
            DeliverVirParam param = new DeliverVirParam();
            param.setOrderId("21330017");
            param.setSellerId(1);
            System.err.println(client.deliverVir(param));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }



    public void testConfirmReceipt() throws Exception {
        //商品id
        String orderId = "3450002";
        try {
            System.err.println(client.confirmReceipt(1, 2, orderId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }


    public void testCancelOrder() throws Exception {
        //商品id
        String orderId = "21330017";
        try {
            System.err.println(client.cancelOrder(1, 17, orderId, 9));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }


    public void testOrderStateQueryBuyer() throws Exception {
        //商品id
        String orderId = "1";
        try {
            ////////////////////////////////////////////////////
            OrderQueryConditions conditions = new OrderQueryConditions();
            conditions.setUserId(2);
//            conditions.setCurPage(1);
//            conditions.setCount(3);
//            conditions.setOrderState(1);
            System.err.println(client.orderStateQuery(2, 2, conditions));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }





    public void testPayApply() throws Exception {
        try {
            PayParam payParam = new PayParam();
            payParam.setUserId(222);
            payParam.addToOrderIdList("98600222");
            PayChannel payChannel = new PayChannel();
            payChannel.setPayChannel(0);
            payParam.setExchangeCash("0.06");
            payParam.setExchangeScore(6);
            payParam.setPayChannel(payChannel);
            System.err.println(client.payApply(payParam));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    public void testPayState() throws Exception {
        try {
            PayState payState = new PayState();
            payState.setPayId("xxxxx");
            payState.setRetCode(2);
            payState.setCancelTime(1212121);
            System.err.println(client.payState(payState));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    public void testBatchExport() throws Exception {
        try {
            OrderQueryConditions queryConditions = new OrderQueryConditions();
            queryConditions.setOrderId("53390083");
            System.err.println(client.batchExportOrder(13, queryConditions));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    public void testBatchExportFull() throws Exception {
        try {
            OrderQueryConditions queryConditions = new OrderQueryConditions();
//            queryConditions.setUserId(99);
            queryConditions.setStartTime("2016-07-01 00:00:00");
            queryConditions.setEndTime("2016-07-21 23:00:00");
            queryConditions.setOrderState(1000);
                queryConditions.setOrderId("93890041");
            System.err.println(client.batchExportOrderFull(queryConditions));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    public void testQueryExportFileKey() throws TException {
        System.err.println(client.getExportOrderResult("6ea7e10c58fa14f752d040cf6e5a14bd"));
    }

    public void testOrderProfileQueryOffline() throws Exception {
        OrderQueryConditions queryConditions = new OrderQueryConditions();
        queryConditions.setSellerId(4);
        queryConditions.setPayTimeStart("2016-7-22");
        queryConditions.setPayTimeEnd("2016-7-23");
        queryConditions.setCurPage(1);
        queryConditions.setCount(5);
        System.out.println(this.client.orderProfileQueryOffline(2, 4, queryConditions));
    }
}
