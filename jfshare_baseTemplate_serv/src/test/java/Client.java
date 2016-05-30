import com.jfshare.finagle.thrift.baseTemplate.BaseTemplateServ;
import com.jfshare.finagle.thrift.baseTemplate.CalculatePostageParam;
import com.jfshare.finagle.thrift.baseTemplate.DeliverStorehouseParam;
import com.jfshare.finagle.thrift.baseTemplate.Postage;
import com.jfshare.finagle.thrift.baseTemplate.PostageTemplate;
import com.jfshare.finagle.thrift.baseTemplate.PostageTemplateQueryParam;
import com.jfshare.finagle.thrift.baseTemplate.ProductPostageBasic;
import com.jfshare.finagle.thrift.baseTemplate.ProductRefProvince;
import com.jfshare.finagle.thrift.baseTemplate.SellerPostageBasic;
import com.jfshare.finagle.thrift.baseTemplate.Storehouse;
import com.jfshare.finagle.thrift.baseTemplate.StorehouseQueryParam;
import com.jfshare.utils.JsonMapper;
import junit.framework.TestCase;
import org.apache.poi.ss.usermodel.charts.ManuallyPositionable;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/4/26.
 */
public class Client extends TestCase{

    private TTransport transport;
    private BaseTemplateServ.Client client;


    @Override
    public void setUp() throws Exception {
//        transport = new TFramedTransport(new TSocket("localhost", 2004));
        transport = new TFramedTransport(new TSocket("101.201.38.182", 2004));

        TProtocol protocol = new TBinaryProtocol(transport);
        client = new BaseTemplateServ.Client(protocol);
        transport.open();
    }

    @Override
    public void tearDown(){
        transport.close();
    }

    public void testAddStorehouse() throws Exception {
        Storehouse storehouse = new Storehouse();
        storehouse.setSellerId(1);
        storehouse.setName("华中");
        storehouse.setSupportProvince("1123,12317");
        System.out.println(client.addStorehouse(storehouse).toString());
    }

    public void testUpdateStorehouse() throws Exception {
        Storehouse storehouse = new Storehouse();
        storehouse.setId(6);
        storehouse.setSellerId(1);
        storehouse.setName("华中仓");
        storehouse.setSupportProvince("1124,12318,12345");
        System.out.println(client.updateStorehouse(storehouse).toString());
    }

    public void testDeleteStorehouse() throws Exception {
        System.out.println(client.deleteStorehouse(1, 5).toString());
    }

    public void testDeleteStorehouseBatch() throws Exception {
        List<Storehouse> storehouseList = new ArrayList<>();
        Storehouse storehouse = new Storehouse();
        storehouse.setId(1);
        storehouse.setSellerId(123);
        storehouseList.add(storehouse);
        storehouse = new Storehouse();
        storehouse.setId(2);
        storehouse.setSellerId(123);
        storehouseList.add(storehouse);
        System.out.println(client.deleteStorehouseBatch(storehouseList).toString());
    }

    public void testQueryStorehouse() throws Exception {
        StorehouseQueryParam param = new StorehouseQueryParam();
        param.setSellerId(1);
        param.setId(4);
        System.out.println(client.queryStorehouse(param).toString());
    }

    public void testGetStorehouse() throws Exception {
        List<Integer> storehouseIds = new ArrayList<>();
        storehouseIds.add(4);
        storehouseIds.add(6);
        System.out.println(client.getStorehouse(storehouseIds).toString());
    }

    public void testGetDeliverStorehouse() throws Exception {
        DeliverStorehouseParam param = new DeliverStorehouseParam();
        ProductRefProvince productRefProvince = new ProductRefProvince();
        productRefProvince.setSellerId(1);
        productRefProvince.setProductId("12345");
        productRefProvince.setStorehouseIds("6,7");
        productRefProvince.setSendToProvince("1");
        param.addToProductRefProvinceList(productRefProvince);
        System.out.println(this.client.getDeliverStorehouse(param));

    }

    public void testAddPostageTemplate() throws Exception {
        PostageTemplate template = new PostageTemplate();
        template.setSellerId(13);
        template.setName("邮费模板1231");
        template.setType(22);
        Map<String, Integer> map = new HashMap<>();
        map.put("number", 6);
        map.put("amount", 2);
        map.put("limit", 3);
        map.put("postage", 2);
        Postage postage = new Postage("350000,410000,430000", JsonMapper.toJson(map));
        template.addToPostageList(postage);
       /* postage = new Postage("11111,22222,33333", "12365443");
        template.addToPostageList(postage);*/
        System.out.println(client.addPostageTemplate(template).toString());
    }

    public void testUpdatePostageTemplate() throws Exception {
        PostageTemplate template = new PostageTemplate();
        template.setId(11);
        template.setName("邮费模板1231232");
        template.setType(11);
        Map map = new HashMap();
        map.put("number", 2);
        map.put("postage", 5);
        map.put("addNumber", 1);
        map.put("addPostage", 2);
        Postage postage = new Postage("12313,12314,12315,42321", JsonMapper.toJson(map));
        template.addToPostageList(postage);
        map = new HashMap();
        map.put("number", 2);
        map.put("postage", 5);
        map.put("addNumber", 2);
        map.put("addPostage", 2);
        postage = new Postage("11111,22222,33333,44444", JsonMapper.toJson(map));
        template.addToPostageList(postage);
        System.out.println(client.updatePostageTemplate(template).toString());
    }

    public void testDeletePostageTemplate() throws Exception {
        System.out.println(client.deletePostageTemplate(1, 5).toString());
    }

    public void testDeletePostageTemplateBatch() throws Exception {
        List<PostageTemplate> postageTemplateList = new ArrayList<>();
        System.out.println(client.deletePostageTemplateBatch(postageTemplateList).toString());
    }

    public void testQueryPostageTemplate() throws Exception {
        PostageTemplateQueryParam param = new PostageTemplateQueryParam();
        param.setSellerId(13);
        param.setId(17);
        System.out.println(client.queryPostageTemplate(param).toString());
    }

    public void testGetPostageTemplate() throws Exception {
        List<Integer> templateIds = new ArrayList<>();
        templateIds.add(11);
        System.out.println(client.getPostageTemplate(templateIds));
    }

    public void testCalculatePostage() throws Exception {
        CalculatePostageParam postageParam = new CalculatePostageParam();
//        postageParam.setSendToProvince("11111");
        postageParam.setSendToProvince("110000");
        SellerPostageBasic sellerPostageBasic = new SellerPostageBasic();
        sellerPostageBasic.setSellerId(13);
        // 初始化商品
        ProductPostageBasic productPostageBasic = new ProductPostageBasic();
        productPostageBasic.setProductId("ze160526172547000945");
        productPostageBasic.setTemplateId(94);
        productPostageBasic.setAmount("0.05");
        productPostageBasic.setWeight(350);
        productPostageBasic.setNumber(7);
        sellerPostageBasic.addToProductPostageBasicList(productPostageBasic);

        productPostageBasic = new ProductPostageBasic();
        productPostageBasic.setProductId("ze160526172825000873");
        productPostageBasic.setTemplateId(1);
        productPostageBasic.setAmount("0.02");
        productPostageBasic.setWeight(100);
        productPostageBasic.setNumber(2);
        sellerPostageBasic.addToProductPostageBasicList(productPostageBasic);

        productPostageBasic = new ProductPostageBasic();
        productPostageBasic.setProductId("ze160519222333000922");
        productPostageBasic.setTemplateId(94);
        productPostageBasic.setAmount("0.04");
        productPostageBasic.setWeight(200);
        productPostageBasic.setNumber(4);
        sellerPostageBasic.addToProductPostageBasicList(productPostageBasic);
        postageParam.addToSellerPostageBasicList(sellerPostageBasic);

        SellerPostageBasic sellerPostageBasic2 = new SellerPostageBasic();
        sellerPostageBasic2.setSellerId(14);

        // 初始化商品
        ProductPostageBasic productPostageBasic2 = new ProductPostageBasic();
        productPostageBasic2.setProductId("ze160519222546000713");
        productPostageBasic2.setTemplateId(15);
        productPostageBasic2.setAmount("0.03");
        productPostageBasic2.setWeight(150);
        productPostageBasic2.setNumber(6);
        sellerPostageBasic2.addToProductPostageBasicList(productPostageBasic2);
        postageParam.addToSellerPostageBasicList(sellerPostageBasic2);

        System.out.println(client.calculatePostage(postageParam).toString());
    }

}
