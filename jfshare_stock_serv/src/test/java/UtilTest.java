import com.jfshare.stock.dao.impl.redis.ConstRedis;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2015/11/23.
 */
public class UtilTest {

    @Test
    public void testGetSkuNums() {
        Map<String,String> map = new HashMap<>();
        map.put(ConstRedis.STOCK_FIELD_COUNT+"","");
        map.put(ConstRedis.STOCK_FIELD_COUNT+"111","");
        map.put(ConstRedis.STOCK_FIELD_COUNT+"22-22","");

//        System.out.println(new StockRedisImpl().getSkuNums(map));
    }
}
