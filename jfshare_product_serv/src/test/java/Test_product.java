import com.jfshare.finagle.thrift.product.*;
import com.jfshare.finagle.thrift.result.StringResult;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
* Created by lenovo on 2015/9/28.
*/
public class Test_product {
    // 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private ProductServ.Client client;

//    private static final String IP = "127.0.0.1";
    private static final String IP = "120.24.153.155";

    private static final Integer PORT = 1980;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new ProductServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    @Test
    public void testM1() throws Exception {
        if (DO_IT == 0)
            return;

        ProductRetParam param = new ProductRetParam();
        param.setBaseTag(1);
        param.setSkuTemplateTag(1);
        param.setAttributeTag(1);
        param.setSkuTag(1);
        ProductResult ret = client.queryProduct("ze151024205818000350", param);
        System.out.println("返回码="+ret);
    }

    @Test
    public void testM2() throws Exception {
        if (DO_IT == 0)
            return;

        ProductSurveyQueryBatchParam param = new ProductSurveyQueryBatchParam();
//        param.setProductId("jfx0001");
//        param.setSellerId(-1);
        param.addToProductIds("ze151224013609000987");
        param.addToProductIds("ze151210231353000417");

        param.setFromType(1);
        ProductSurveyResult ret = client.productSurveyQueryBatch(param);
        System.out.println("返回码="+ret);
    }

    @Test
    public void testM3() throws Exception {
        if (DO_IT == 0)
            return;
        ProductDetailParam param = new ProductDetailParam();
//        param.setDetailKey("5380eaefeaa797d9e53d6eef");
        param.setProductId("jfx0001");
        StringResult ret = client.queryProductDetail(param);
        System.out.println("返回码="+ret);
    }

    @Test
    public void testM4() throws Exception {
        if (DO_IT == 0)
            return;
        ProductRetParam param = new ProductRetParam();
        param.setBaseTag(1);
        param.setSkuTemplateTag(0);
        param.setAttributeTag(0);
        param.setSkuTag(1);
        ProductResult ret = client.queryHotSKU("ze151024205818000350", "1-2:100-101", param);
        System.out.println("返回码="+ret);
    }

    @Test
    public void testM5() throws Exception {
        if (DO_IT == 0)
            return;

        ProductRetParam param = new ProductRetParam();
        param.setBaseTag(1);
        param.setSkuTemplateTag(1);
        param.setAttributeTag(1);
        param.setSkuTag(1);
        ProductResult ret = client.queryProductSnap("zs151206195727000594", param);
        System.out.println("返回码="+ret);
    }
}
