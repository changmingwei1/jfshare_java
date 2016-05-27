package com.jfshare.product.commons;

/**
 * Created by Lenovo on 2015/10/20.
 */
public class ProductCommons {

    public static String APP_KEY = "jfx_product_serv";

    //下架状态
    public static int PRODUCT_STATE_CREATE = 100;
    public static int PRODUCT_STATE_OFFLINE_SELLER = 101;
    public static int PRODUCT_STATE_REVIEW_UNPASS = 102;
    public static int PRODUCT_STATE_OFFLINE_CS = 103;

    //审核状态
    public static int PRODUCT_STATE_REVIEW_APPLY = 200;
    //销售中状态
    public static int PRODUCT_STATE_ONSELL = 300;

    public static final String PRODUCT_KAFAK_TOPIC = "jfshare_product_push";
    
    public static final String FLOOR_PRODUCT = "jfshare:product:floor_";
    
    //商品列表key的前缀
    public static final String RK_PRODUCT_LIST = "jfshare:product:list_";
    
    public static final String RK_PRODUCTSURVEY_MAP = "jfshare:product:map:productId";

}
