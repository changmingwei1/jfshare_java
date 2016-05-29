import com.alibaba.fastjson.JSON;
import com.jfshare.elasticsearch.drive.ESClient;
import com.jfshare.task.elasticsearch.models.EsOrder;
import com.jfshare.task.util.DateTimeUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_xxx {
    public static void main(String[] str) {
        ESClient esClient = new ESClient("jfshare-app", "120.24.153.155:9300");
        EsOrder esOrder = new EsOrder();
        esOrder.setOrderId("111");
        esOrder.setOrderCreateTime(DateTimeUtil.strToDateOfDefaulfFormat("2016-05-10 00:00:00"));

        try {
            esClient.addOrUpdateByCustomId("jfshare_order_2016-05", "esOrder","111", JSON.toJSONString(esOrder));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
