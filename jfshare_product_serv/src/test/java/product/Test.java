package product;

import com.jfshare.finagle.thrift.product.ProductSku;
import com.jfshare.finagle.thrift.product.ProductSkuItem;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.JsonMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2015/11/22.
 */
public class Test {
    public static void main(String[] str) {
        ProductSku productSku = new ProductSku();
        productSku.setCurPrice("100");
        try {
            String jsonStr = "{\"1-2:100-101\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-3:100-101\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-2:100-102\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-3:100-102\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-2:100-103\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-3:100-103\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-5:100-103\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"B154F52CCD0593A05C02F5D56F524EB8.jpg\",\"skuName\":\"白色-m\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"B154F52CCD0593A05C02F5D56F524EB8.jpg\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":true},\"1-5:100-102\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"B154F52CCD0593A05C02F5D56F524EB8.jpg\",\"skuName\":\"白色-xl\",\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"B154F52CCD0593A05C02F5D56F524EB8.jpg\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":true},\"1-5:100-101\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-4:100-101\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-4:100-102\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false},\"1-4:100-103\":{\"sellerClassNum\":\"1\",\"shelf\":\"1\",\"curPrice\":\"4088.00\",\"orgPrice\":\"6088.00\",\"vPicture\":\"1\",\"skuName\":null,\"setOrgPrice\":true,\"setCurPrice\":true,\"setSellerClassNum\":true,\"vpicture\":\"1\",\"setShelf\":true,\"setVPicture\":true,\"setSkuName\":false}}";
//            Map<String, ProductSkuItem> map = (HashMap<String,ProductSkuItem>) JSON.parse(jsonStr);
            Map<String, ProductSkuItem> map = null;

            map = (Map<String, ProductSkuItem>) JsonMapper.toGenericMapEx(jsonStr, String.class, ProductSkuItem.class);


//            for(HashMap<String,String> m : map.entrySet()) {
//                ProductSkuItem psi = new ProductSkuItem();
//                BeanUtil.fillBeanDataStringMap(psi, m);
//            }
//            Map<String,ProductSkuItem> rMap = convert(map);
            productSku.setProductSkuMap(map);

            System.err.println(productSku);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,ProductSkuItem> convert(Map<String,Map> pMap){
        Map<String,ProductSkuItem> rMap = new HashMap<String, ProductSkuItem>();
        for(String key : pMap.keySet()) {
            ProductSkuItem productSkuItem = new ProductSkuItem();
            BeanUtil.fillBeanDataStringMap(productSkuItem, pMap.get(key));
            rMap.put(key, productSkuItem);
        }

        return rMap;
    }
}
