import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.Map;

/**
* Created by lenovo on 2015/9/28.
*/
public class Test_product {
    // 执行次数，0的时候不执行测试用例
    private static Integer DO_IT = 100;

    private TTransport transport;

    private TProtocol protocol;

    private ProductServ.Client client;

//    private static final String IP = "127.0.0.1";
    private static final String IP = "101.201.38.182";

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
        while(DO_IT>0) {

        ProductDetailParam param = new ProductDetailParam();
//        param.setDetailKey("5380eaefeaa797d9e53d6eef");
            param.setProductId("ze160414115412000461");
            StringResult ret = client.queryProductDetail(param);
            System.out.println("返回码="+ret);
            --DO_IT;
        }
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

    @Test
    public void testJson() {
        String json = "{\"itemdetail\":[{\"goodsid\":\"516764\",\"weight\":\"40\",\"brandName\":\"\",\"category\":\"15073\",\"goodsname\":\"MISS\tFACE\t透明质酸清润焕采眼霜\t30g\",\"place_production\":\"杭州\",\"goodsbarcode\":\"6922239920352\",\"unit\":\"个\",\"prodescription\":\"\",\"specification\":\"1*30g\",\"pubflag\":\"1\"}]}";

        JSONObject itemDetail = JSON.parseObject(json);
        JSONArray details = itemDetail.getJSONArray("itemdetail");

        System.out.println(((Map)details.get(0)).get("goodsid"));


        String errorJson = "{\"error_response\":{\"code\":10001,\"msg\":null,\"sub_code\":11001,\"sub_msg\":null,\"memo\":\"查不到该商品信息\"}}";
        JSONObject error = JSON.parseObject(errorJson);
        System.out.println(error.getJSONObject("error_response").getString("code"));
    }
}
