package product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfshare.product.util.CodeUtil;
import com.jfshare.product.util.HttpUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.common.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lenovo on 2016/5/28.
 */
public class WoMaiTest{

    String domain = "http://sandbox.womaiapp.com/api/rest";

    public Map<String, String> initPostParams(String method, Map<String, String> param) {
        Map<String, String> postParams = new HashMap<String, String>();
        String appKey = "160519";
        String appSecret = "a8c5j5nh5g08mpeg401xkspdfjqgx9j6";
        String paramStr = JSON.toJSONString(param);
        StringBuffer sign = new StringBuffer();
        sign.append(method).append(appKey).append(appSecret);
        if(param != null) {
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
        param.put("warehouseid", "10");
//        param.put("area", "100");

        Map<String, String> httpParam = this.initPostParams("womai.inventory.get", param);
        String detailJson = HttpUtils.httpPostUTF8(domain, httpParam);
        System.out.println(detailJson);
        JSONObject itemDetail = JSON.parseObject(detailJson);

    }

}
