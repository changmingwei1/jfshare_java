package product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.product.model.TbThirdPartyProductWithBLOBs;
import com.jfshare.product.service.IWoMaiSvc;
import com.jfshare.product.service.impl.WoMaiSvcImpl;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.product.util.HttpUtils;
import com.jfshare.utils.JsonMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.language.Soundex;
import org.elasticsearch.common.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/spring-context.xml")
public class WoMaiTest {

    @Resource
    private IWoMaiSvc woMaiSvc;

    String domain = "http://sandbox.womaiapp.com/api/rest";

    public Map<String, String> initPostParams(String method, Map<String, String> param) {
        Map<String, String> postParams = new HashMap<String, String>();
        String appKey = "160519";
        String appSecret = "a8c5j5nh5g08mpeg401xkspdfjqgx9j6";
        String paramStr = JSON.toJSONString(param);
        StringBuffer sign = new StringBuffer();
        sign.append(method).append(appKey).append(appSecret);
        if (param != null) {
            sign.append(paramStr);
        }

        postParams.put("method", method);
        postParams.put("appKey", appKey);
//        postParams.put("sign", CodeUtil.getMD5Upper(sign.toString()));
        postParams.put("sign", DigestUtils.md5Hex(sign.toString()).toUpperCase());
        if (param != null) {
            postParams.put("param", JSON.toJSONString(param));
        }
        return postParams;
    }

    @Test
    public void getItemPageNum() throws Exception {

        Map<String, String> httpParam = this.initPostParams("womai.itempagenum.get", null);

        String detailJson = HttpUtils.httpPostUTF8(domain, httpParam);

        System.out.println(detailJson);

        JSONObject itemPageNum = JSON.parseObject(detailJson);
        JSONArray pageNums = itemPageNum.getJSONArray("itempagenum");
        for (int i = 0; i < pageNums.size(); i++) {
            JSONObject pageNum = pageNums.getJSONObject(i);
            System.out.println(pageNum.get("page_num"));

        }
    }

    @Test
    public void getItemList() throws Exception {

        Map<String, String> param = new HashMap();
        param.put("pageNum", "1");
        Map<String, String> httpParam = this.initPostParams("womai.itemlist.get", param);
        String detailJson = HttpUtils.httpPostUTF8(domain, httpParam);
        System.out.println(detailJson);
    }

    @Test
    public void getImage() throws Exception {
        List<String> ids = new ArrayList<String>();
        ids.add("564437");
        ids.add("564435");
        ids.add("564436");
        ids.add("564434");
        ids.add("559995");
        ids.add("559994");
        ids.add("559992");
        ids.add("559991");
        ids.add("559990");
        ids.add("559986");
        ids.add("559988");
        ids.add("559984");
        ids.add("559983");
        ids.add("559119");
        ids.add("559053");
        ids.add("559051");
        ids.add("559052");
        ids.add("556500");
        ids.add("558468");
        ids.add("556695");
        ids.add("556571");
        ids.add("556499");
        ids.add("555044");
        ids.add("555042");
        ids.add("554671");
        ids.add("554670");
        ids.add("554599");
        ids.add("549852");
        ids.add("549833");
        ids.add("549832");
        ids.add("548098");
        ids.add("548097");
        ids.add("546430");
        ids.add("545668");
        ids.add("545561");
        ids.add("545560");
        ids.add("545559");
        ids.add("545558");
        ids.add("545557");
        ids.add("545554");
        ids.add("545553");
        ids.add("545552");
        ids.add("545551");
        ids.add("545142");
        ids.add("545143");
        ids.add("545141");
        ids.add("545140");
        ids.add("545139");
        ids.add("545137");
        ids.add("544409");
        ids.add("543224");
        ids.add("543181");
        ids.add("543182");
        ids.add("543180");
        ids.add("543179");
        ids.add("543178");
        ids.add("543177");
        ids.add("543176");
        ids.add("543175");
        ids.add("542152");
        ids.add("534541");
        ids.add("534520");
        ids.add("534524");
        ids.add("534523");
        ids.add("534519");
        ids.add("534518");
        ids.add("534516");
        ids.add("534512");
        ids.add("534515");
        ids.add("533265");
        ids.add("533264");
        ids.add("533263");
        ids.add("533262");
        ids.add("533261");
        ids.add("532072");
        ids.add("532071");
        ids.add("532069");
        ids.add("532066");
        ids.add("532065");
        ids.add("532064");
        ids.add("532055");
        ids.add("532054");
        ids.add("528743");
        ids.add("528740");
        ids.add("528739");
        ids.add("528738");
        ids.add("524469");
        ids.add("524386");
        ids.add("524297");
        ids.add("524296");
        ids.add("524295");

        for (String id : ids) {
            Map param = new HashMap();
            param.put("skuid", id);
            Map<String, String> httpParam = this.initPostParams("womai.itemimage.get", param);

            String imageJson = HttpUtils.httpPostUTF8(domain, httpParam);
            if (!imageJson.contains("error_response")) {
                System.out.println(">>>>> imageJson :" + imageJson);
            }
        }
    }

    @Test
    public void getItemDetail() throws Exception {
        Map<String, String> param = new HashMap();
        param.put("skuid", "954018");
        Map<String, String> httpParam = this.initPostParams("womai.itemdetail.get", param);
        String detailJson = HttpUtils.httpPostUTF8(domain, httpParam);
        System.out.println(detailJson);
    }

    @Test
    public void getStock() throws Exception {
        List<String> woMaiIds = new ArrayList<String>();
        woMaiIds.add("954018");
        woMaiIds.add("65951");
        woMaiIds.add("65950");
        woMaiIds.add("65948");
        woMaiIds.add("1");
        String ids = StringUtils.join(woMaiIds, ",");
        Map param = new HashMap();
        param.put("skuids", ids);
        param.put("warehouseid", "100");
//        param.put("area", "100");

        Map<String, String> httpParam = this.initPostParams("womai.inventory.get", param);
        String detailJson = HttpUtils.httpPostUTF8(domain, httpParam);
        System.out.println(detailJson);
        JSONObject itemDetail = JSON.parseObject(detailJson);

    }

    @Test
    public void getPrice() throws Exception {
        List<String> woMaiIds = new ArrayList<String>();
        woMaiIds.add("954018");
        woMaiIds.add("65951");
        woMaiIds.add("65950");
        woMaiIds.add("65948");
        woMaiIds.add("1");
        String ids = StringUtils.join(woMaiIds, ",");
        Map param = new HashMap();
        param.put("skuids", ids);
        Map<String, String> httpParam = this.initPostParams("womai.price.get", param);
        String priceJson = HttpUtils.httpPostUTF8(domain, httpParam);
        System.out.println(priceJson);
        JSONObject itemDetail = JSON.parseObject(priceJson);

    }

    @Test
    public void testSyncWoMaiProduct() throws Exception {
        woMaiSvc.syncWoMaiProduct();
    }

    @Test
    public void testGetProduct() throws Exception {
        TbThirdPartyProductWithBLOBs productWithBLOBs = this.woMaiSvc.getProduct("954018");
        System.out.println("<<<< productWithBLOBs : " + JsonMapper.toJson(productWithBLOBs));
        this.woMaiSvc.saveWoMaiProduct(productWithBLOBs);

    }


}
