import com.jfshare.pay.util.DesUtil;
import com.jfshare.utils.CryptoUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.net.URLEncoder;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_Pay {
    private static Logger logger = LoggerFactory.getLogger(Test_Pay.class);

    private final static String COMMON_KEY = "JFX54475254";
    private final static String SPID = "160260";
    private final static String KEY = "TICKET02";
    private final static String PAY_URL_PREFIX = "http://jf.189.cn/preview/CommonPage/PayPWD.aspx?strParm=";

    public static void main(String[] args) throws Exception {
        logger.info(getPayUrl());
        logger.info("登录请求=" + "http://jf.189.cn/preview/CommonPage/PTLGCK.aspx?Partner=" + SPID + "&Sign=" + CryptoUtil.md5Encode(SPID + COMMON_KEY));
    }

    public static String getPayUrl() {
        String orderNo = "1";
        String point = "100";
        String place_time = "2015/12/13 18:10:41";
        String custID = "100017286150";
        String price = "1000";
        String goodsName = "测试商品1234567890-";
        String dataStr = "spid=" + SPID
                + "&orderNo=" + orderNo
                + "&point=" + point
                + "&place_time=" + place_time
                + "&custID=" + custID
                + "&price=" + price
                + "&goodsName=" + goodsName;
        logger.debug("原始字符串 ==> " + dataStr);
        String urlencodeParams = "";
        try {
            String sign = CryptoUtil.md5Encode(dataStr + COMMON_KEY).toUpperCase();
            logger.debug("md5加密 ==> " + sign);
            String paramStr = dataStr + "&sign="+sign;
            logger.debug("des前数据 ==> " + paramStr);

            BASE64Encoder enc = new BASE64Encoder();
            String data = enc.encode(paramStr.getBytes("UTF-8"));
            String encryptedtext = DesUtil.encrypt(data, KEY, KEY);
            logger.debug("des后数据 ==> " + encryptedtext);
            urlencodeParams = URLEncoder.encode(encryptedtext, "UTF-8");
        } catch (Exception e) {
            logger.error("encode支付url失败", e);
            return "";
        }
        String payUrl = PAY_URL_PREFIX + urlencodeParams;
        logger.debug("最终url ==> " + payUrl);
        return payUrl;
    }

    @org.junit.Test
    public void tokenValid() throws TException {
        try {
            System.out.println("start:" + System.currentTimeMillis());
//            String decryptString = CryptoUtil.decryptString("fvBLEJEHNOw=2");
//            System.out.println(decryptString);
            System.out.println("sdfsd_sdfksd".indexOf("_"));
            System.out.println("end:" + System.currentTimeMillis());
        } catch(Exception e){System.out.println(e);}
    }

    @org.junit.Test
    public void josntool() throws TException {
        try {
            System.out.println("start:" + System.currentTimeMillis());
            System.out.println("end:" + System.currentTimeMillis());
        } catch(Exception e){System.out.println(e);}
    }
}
