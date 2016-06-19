import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.jfshare.finagle.thrift.order.Order;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by bq on 16/6/16.
 */
public class Test_JSON {
    public static class Bean{
        private String aSet = "aSet";
        private String isaaaa = "isaaaa";
        private String a = "aa";

        public String getaSet() {
            return aSet;
        }

        public void setaSet(String aSet) {
            this.aSet = aSet;
        }

        public String getIsaaaa() {
            return isaaaa;
        }

        public void setIsaaaa(String isaaaa) {
            this.isaaaa = isaaaa;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }
    }

    public static void main(String[] str) {
        PropertyFilter filter = new PropertyFilter() {
            //过滤不需要的字段
            public boolean apply(Object source, String name, Object value) {
                if(value == null || StringUtils.isBlank(String.valueOf(value))
                        || (name.length()>3 && name.startsWith("set") && name.charAt(3) >=65 && name.charAt(3) <=90)){
                    return false;
                }
                return true;
            }
        };
        Bean bean = new Bean();

        Order order = new Order();
        order.setOrderId("1");
        order.setOrderState(10);
        String s = JSON.toJSONString(order, filter);
        Order order1 = JSON.parseObject(s, Order.class);
        System.err.println("s=>"+ s);
        System.err.println("s1=>" + order1);
    }
}
