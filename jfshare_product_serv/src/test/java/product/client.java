package product;

import com.jfshare.finagle.thrift.product.*;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by lenovo on 2015/9/28.
*/
public class client {
    // 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private ProductServ.Client client;

    private static final String IP = "127.0.0.1";

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
    public void testAddProduct() throws Exception {
        if (DO_IT == 0)
            return;

        Product product = initProduct(null);
        StringResult s = client.addProduct(product);
        System.err.println(s);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        if (DO_IT == 0)
            return;

        String productId = "ze151022005329000594";
        Product product = initProduct(productId);
        StringResult s = client.updateProduct(product);
        System.err.println(s);
    }

    @Test
    public void testSetProductState() throws Exception {
        if (DO_IT == 0)
            return;

        String productId = "ze151210231353000417";
        ProductOpt productOpt = new ProductOpt();
        productOpt.setProductId(productId);
        productOpt.setActiveState(101);
        productOpt.setCurState(300);
//        productOpt.setDesc("审核通过");
        productOpt.setCreateTime("2015-12-21 12:12:12");
        productOpt.setOperatorType(1);
        productOpt.setOperatorId("1");

        Result s = client.setProductState(productOpt);
        System.err.println(s);
    }


    @Test
    public void testQueryProduct() throws Exception {
        ProductRetParam param = new ProductRetParam();
        param.setBaseTag(1);
        param.setSkuTag(1);
        param.setAttributeTag(1);
        param.setSkuTemplateTag(1);
        System.out.println(client.queryProduct("ze160505170956000167", param).toString());
    }


    private Product initProduct(String productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setDetailKey("detailKey");
        product.setImgKey("imgKeys");
        product.setAttribute("{\"11\",\"22\"}");
        product.setBrandId(1);
        product.setMaxBuyLimit(10);
        product.setProductName("测试创建商品");
        product.setSellerId(1000);
        product.setSkuTemplate("{\"key\":\"1\"}");
        product.setSubjectId(1319);
        product.setViceName("测试副标题");
        product.setDetailContent("<html>这是测试数据</html>");

        ProductSku productSku = new ProductSku();
        productSku.setCurPrice("100");
        productSku.setOrgPrice("200");
        productSku.setShelf("shelf");
        productSku.setSkuNum("");
//        Map<String, ProductSkuItem> productSkuItemMap = new HashMap<String, ProductSkuItem>();
        List<ProductSkuItem> skuItems = new ArrayList<ProductSkuItem>();
        ProductSkuItem productSkuItem = new ProductSkuItem();
        productSkuItem.setShelf("sub-shelf");
        productSkuItem.setCurPrice("100");
        productSkuItem.setOrgPrice("200");
        productSkuItem.setSkuNum("1-2:100-101");
//        productSkuItemMap.put("1-2:100-101",productSkuItem);
        skuItems.add(productSkuItem);
        productSku.setSkuItems(skuItems);
        product.setStorehouseIds("121312,131313");
        product.setPostageId(124);

        product.setProductSku(productSku);

        return product;
    }
}