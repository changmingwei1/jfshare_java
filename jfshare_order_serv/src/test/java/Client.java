import com.jfshare.finagle.thrift.order.*;
import com.jfshare.finagle.thrift.pay.PayChannel;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Client extends TestCase{

    private TTransport transport;
    private OrderServ.Client client;

    @Override
    public void setUp() throws Exception {

//        transport = new TFramedTransport(new TSocket("101.201.38.182", 1986));
        transport = new TFramedTransport(new TSocket("localhost", 1986));

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
        //商品id
        String orderId = "1571111";
        try {
            ////////////////////////////////////////////////////

            System.err.println(client.queryOrderDetail(1, 81, orderId));
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
            conditions.setSellerId(2);
            conditions.setOrderState(61);
            conditions.addToOrderIds("18120090");
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
            payParam.setUserId(35);
            payParam.addToOrderIdList("21860035");
            PayChannel payChannel = new PayChannel();
            payChannel.setPayChannel(9);
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

    @Test
    public void testPayFinish() throws Exception {
        try {
          String payRes = "{\"bizCode\":1,\"extraParam\":\"96_23110096\",\"orderNo\":\"bac65b3d404a2cfb8407f2e903d78e63\",\"payAccount\":\"150****8779\",\"payChannel\":6,\"payId\":\"7099e495ceca48da673aec21e1169981\",\"price\":1,\"processTime\":\"2016-05-22 18:28:33\",\"retCode\":3,\"score\":0,\"setBizCode\":true,\"setErrMsg\":false,\"setExtraParam\":true,\"setOrderNo\":true,\"setPayAccount\":true,\"setPayChannel\":true,\"setPayId\":true,\"setPrice\":true,\"setProcessTime\":true,\"setRetCode\":true,\"setScore\":true,\"setSign\":true,\"setThirdPrice\":true,\"setThirdScore\":true,\"setThirdTime\":true,\"setTokenId\":true,\"sign\":\"db656aadd3d46e9863bfddd5ab22d1b4\",\"thirdPrice\":1,\"thirdScore\":0,\"thirdTime\":\"2016-05-22T17:11:11.000+08:00\",\"tokenId\":\"fvBLEJEHNOw=\"}";
            System.err.println(client.payFinish(payRes));
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
}
