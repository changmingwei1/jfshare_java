
import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.trade.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;


public class Test_trade {

    // 0的时候不执行测试用例，其他值的时候执行
    private  final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private TradeServ.Client client;

    private  final String IP = "127.0.0.1";//"120.24.153.155";

    private  final Integer PORT = 1987;
	
	
/*	public  static void main(String...client ) throws Exception{
	    setUp();
	    testQueryAddress();
	    tearDown();
	}
	*/

    @Before
    public  void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new TradeServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    @org.junit.Test
    public void aaa() throws TException {
        try{
            if (DO_IT == 0)
                return;
            System.out.println("start:" + System.currentTimeMillis());
            for(int i=0; i<DO_IT; i++){
                BuyInfo buyInfo = new BuyInfo();
                buyInfo.setUserId(33);
                DeliverInfo deliverInfo = new DeliverInfo();
                deliverInfo.setAddressId(29);
                buyInfo.setDeliverInfo(deliverInfo);

                List<BuySellerDetail> sellerDetailList = new ArrayList<BuySellerDetail>();
                BuySellerDetail sellerDetail = new BuySellerDetail();
                    sellerDetail.setSellerId(8888);
                    List<OrderInfo> productInfos = new ArrayList<OrderInfo>();
                    OrderInfo productInfo = new OrderInfo();
                    productInfo.setProductId("ze151210145613000059");
                    productInfo.setSkuNum("1-1:100-102");
                    productInfo.setCount(2);
                    productInfo.setCurPrice("10.44");
                    productInfos.add(productInfo);
                sellerDetail.setProductList(productInfos);
                sellerDetailList.add(sellerDetail);
                buyInfo.setSellerDetailList(sellerDetailList);
                buyInfo.setAmount("81.76");
                buyInfo.setFromBatch(1);
                OrderConfirmResult createOrderResult = client.orderConfirm(buyInfo);
                System.out.println(createOrderResult);
            }
            System.out.println("end:" + System.currentTimeMillis());
        }catch(Exception e){System.out.println(e);}
    }
}
