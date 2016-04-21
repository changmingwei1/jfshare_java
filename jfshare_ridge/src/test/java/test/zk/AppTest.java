package test.zk;

import com.jfshare.ridge.PropertiesUtil;

/**
 * Hello world!
 *
 */
public class AppTest 
{
    public static void main( String[] args ) throws InterruptedException
    {
//        ConfigManager cfg = new ConfigManager(Constants.DEFAULT_PRODUCTION_ZKADDRESS);
      
//        while (true) {
//
//        	  System.out.println(PropertiesUtil.getProperty("test_conn", "data1"));
//        	  Thread.sleep(5000);
//		}

        System.out.println(PropertiesUtil.getKafkaProperty("znl_public_kafka", "user_1").getZookeeper());
    }
}
