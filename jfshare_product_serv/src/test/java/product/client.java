package product;

import com.jfshare.finagle.thrift.pagination.Pagination;
import com.jfshare.finagle.thrift.product.*;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.service.impl.WoMaiSvcImpl;
import com.jfshare.product.util.HttpUtils;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.utils.DateUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.elasticsearch.common.netty.util.internal.NativeLibraryLoader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
//    private static final String IP = "101.201.39.38";
//    private static final String IP = "120.24.153.155";
//    private static final String IP = "101.201.38.182";
//    private static final String IP = "101.201.39.12";

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

        String productId = "ze160415093016000123";
        ProductOpt productOpt = new ProductOpt();
        productOpt.setProductId(productId);
        productOpt.setActiveState(300);
        productOpt.setCurState(100);
//        productOpt.setDesc("审核通过");
        productOpt.setCreateTime("2016-05-25 21:20:50");
        productOpt.setOperatorType(1);
        productOpt.setOperatorId("1");

        Result s = client.setProductState(productOpt);
        System.err.println(s);
    }


    @Test
    public void testQueryProduct() throws Exception {
        for (int i = 0; i < 1; i++) {
            ProductRetParam param = new ProductRetParam();
            param.setBaseTag(1);
            /*param.setSkuTag(1);
            param.setAttributeTag(1);
            param.setSkuTemplateTag(1);*/
            System.out.println(client.queryProduct("ze160525101844000096", param).toString());
        }
    }

    @Test
    public void testQueryProductDetail() throws Exception {

        ProductDetailParam param = new ProductDetailParam();
        param.setProductId("ze160623105125000807");
        param.setDetailKey("576bb2df0cf2e5ea210e52f9");
        int num = 0;
        for (int i = 1; i <= 10000; i++) {

            StringResult result = this.client.queryProductDetail(param);
            if (result.getResult().getCode() == 1) {
                num ++;
                System.out.println(result.toString());
            }
            if (i % 100 == 0) {
                System.out.println(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss") + " >>>> run le : " + i + "次");
            }
        }
        System.err.print(">>>>> fail times : " + num);
    }


    @Test
    public void testAdd() throws Exception {
//        this.client.p
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
        product.setType(3);

        return product;
    }

    @Test
    public void testQueryProductCardViewList() throws Exception {
        ProductCardViewParam param = new ProductCardViewParam();
        param.setSellerId(13);
        param.setProductId("ze160620163153000614");
        param.setSkuNum("");
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);

        System.out.println(this.client.queryProductCardViewList(param, pagination));


    }

    @Test
    public void testStatisticsProductCard() throws Exception {
        ProductCardStatisticsParam param = new ProductCardStatisticsParam();
        param.setSellerId(1);
//        param.setProductName("亿");
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);

        System.out.println(this.client.statisticsProductCard(param, pagination));
    }

    @Test
    public void testStatisticsSkuProductCard() throws Exception {
        ProductCardSkuStatisticsParam param = new ProductCardSkuStatisticsParam();
        param.setSellerId(13);
        param.setProductId("ze160615095640000815");
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);

        System.out.println(this.client.statisticsSkuProductCard(param, pagination));
    }

    @Test
    public void testProductSurveyQuery() throws Exception {

        ProductSurveyQueryParam param = new ProductSurveyQueryParam();
//        param.setProductId("ze160515153359000306");
       /* param.setSubjectId(2077);
        param.setBrandId(591);*/
//        param.setSort("click_rate DESC");
//        param.setSort("cur_price DESC");
        param.setSort("cur_price ASC");
//        param.setSort("create_time DESC");
        param.setActiveState(300);
//        param.setSubjectId(1001);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(500);

        param.setPagination(pagination);
        int num = 0;
        for (int i = 1; i <= 1; i++) {
            ProductSurveyResult result = this.client.productSurveyQuery(param);
            if (result.getResult().getCode() == 1) {
                num ++;
                System.out.println(result.toString());
            }
            if (i % 100 == 0) {
                System.out.println(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss") + " >>>> run le : " + i + "次");
            }
            System.out.println(result);
        }
        System.err.print(">>>>> fail times : " + num);
    }

    @Test
    public void testQueryHotSKU() throws Exception {
        ProductRetParam param = new ProductRetParam();
        param.setSkuTag(1);
        System.out.println(this.client.queryHotSKU("ze160623115143000885", "1-1:100-101", param));
        ProductSkuParam skuParam = new ProductSkuParam();
        skuParam.setProductId("ze160526111619000994");
        skuParam.setSkuNum("");
        skuParam.setStorehouseId(0);
        System.out.println(this.client.queryHotSKUV1(skuParam, param));
    }

    @Test
    public void testProductSurveyBackendQuery() throws Exception {
        ProductSurveyQueryParam param = new ProductSurveyQueryParam();
        param.setSellerId(28);
        param.setSort("click_rate DESC");
        param.setActiveState(300);
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);
        param.setPagination(pagination);
        System.out.println(this.client.productSurveyBackendQuery(param));
    }

    @Test
    public void testHttp() throws Exception {

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        System.out.println(DateTime.parse("20160101050101", format));
    }

    @Test
    public void testImportProductCard() throws Exception {
//        String path = "https://apia.wd2go.com/api/1.0/rest/device_redirect?device_user_id=21397091&device_uri=%2Fapi%2F1.0%2Frest%2Ffile_contents%2Fshare%2F%25E8%2599%259A%25E6%258B%259F%25E5%2595%2586%25E5%2593%2581%25E4%25B8%258A%25E4%25BC%25A0%25E6%25A8%25A1%25E6%259D%25BF.xls%3Fdevice_user_id%3D21397091%26request_auth_code%3D4ede3472d20367f0c0bf9c3797b187824e948be306b875920eb2f3f63a3ae5a0";
//        String path = "http://101.201.39.61:3000/system/v1/jfs_image/D7FB66843726D29D4B51A21D12F4642A.jpg";
        String path = "http://proxy.jfshare.com/system/v1/jfs_image/A72AD0A4C055B296B2A5ABE1A2646227.xls";
        ProductCardImportParam productCardImportParam = new ProductCardImportParam();
        productCardImportParam.setSellerId(1);
        productCardImportParam.setProductId("ze160729110622000450");
        productCardImportParam.setPath(path);
        System.out.println(client.importProductCard(productCardImportParam));
    }

    @Test
    //卖家虚拟商品验证列表明细
    public void testQueryCaptchaDetails() throws Exception {
        CaptchaQueryParam param = new CaptchaQueryParam();
        param.setSellerId(4);
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);
        param.setPagination(pagination);
        param.setProductId("ze160708153557000229");
        param.setMonthQuery("2016-07");
        System.out.println(this.client.queryCaptchaDetails(param));
    }

    @Test
    //查询卖家虚拟商品验证列表
    public void testQueryCaptchaList() throws Exception {
        CaptchaQueryParam param = new CaptchaQueryParam();
        param.setSellerId(1);
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);
        param.setPagination(pagination);
        System.out.println(this.client.queryCaptchaList(param));
    }

    @Test
    //查询卖家虚拟商品验证列表—按月统计每天的验证总数( 查询条件：按月)
    public void testQueryCaptchaTotalList() throws Exception {
        CaptchaQueryParam param = new CaptchaQueryParam();
        param.setSellerId(2);
        param.setMonthQuery("2016-07");
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);
        param.setPagination(pagination);
        System.out.println(this.client.queryCaptchaTotalList(param));
    }

    @Test
    // 根据日期查询商品每天验证码数据
    public void testQueryCaptchaDayTotalList() throws Exception {
        CaptchaDayQueryParam param = new CaptchaDayQueryParam();
        param.setSellerId(2);
        param.setDate("2016-06-27");
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);
        param.setPagination(pagination);
        System.out.println(this.client.queryCaptchaDayTotalList(param));
    }

    @Test
    public void testUseProductCard() throws Exception {
        ProductCard productCard = new ProductCard();
        productCard.setSellerId(13);
        productCard.setCardNumber("600002");
        System.out.println(this.client.useProductCard(productCard));
    }

    @Test
    public void testQueryThirdPartyProduct() throws Exception {
        ThirdPartyProductQueryParam param = new ThirdPartyProductQueryParam();
        param.setThirdPartyIdentify(1);
        param.setActiveState(300);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);

        System.out.println(this.client.queryThirdPartyProduct(param, pagination));
    }

    @Test
    public void testGetThirdPartyProductLog() throws Exception {
        ThirdPartyProductLogParam param = new ThirdPartyProductLogParam();
        param.setThirdPartyIdentify(1);
        param.setThirdPartyProductId("954018");

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setNumPerPage(10);

        System.out.println(this.client.getThirdPartyProductLog(param, pagination));
    }

    @Test
    public void testOfferThirdPartyProduct() throws Exception {

        ThirdPartyProduct thirdPartyProduct = new ThirdPartyProduct();
        thirdPartyProduct.setThirdPartyIdentify(1);
        thirdPartyProduct.setThirdPartyProductId("954018");

        System.out.println(this.client.offerThirdPartyProduct(thirdPartyProduct));
    }

    @Test
    public void testExportThirdPartyProduct() throws Exception {
        ThirdPartyProductQueryParam param = new ThirdPartyProductQueryParam();
        param.setThirdPartyIdentify(1);
        param.setActiveState(300);
        System.out.println(this.client.exportThirdPartyProduct(param));
    }

}