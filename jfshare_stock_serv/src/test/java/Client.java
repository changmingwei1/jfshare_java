import com.jfshare.finagle.thrift.stock.*;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Client extends TestCase {

    private TTransport transport;
    private StockServ.Client client;

    @Override
    public void setUp() throws Exception {

        transport = new TFramedTransport(new TSocket("120.24.153.155", 1983));
//        transport = new TFramedTransport(new TSocket("localhost", 1983));

        TProtocol protocol = new TBinaryProtocol(transport);
        client = new StockServ.Client(protocol);
        transport.open();
    }

    @Override
    public void tearDown(){
        transport.close();
    }

    /**
     * 添加后台类目
     * @throws Exception
     */
    @Test
    public void testAddSubject() throws Exception {
        //商品id
        String productId = "ze0000test";
        try {
            ////////////////////////////////////////////////////
            StockInfo si = new StockInfo();
            List<StockItem> list = new ArrayList<StockItem>();
            StockItem item3 = new StockItem();
            item3.setSkuNum("200-200:200-200");
            item3.setCount(100);
            list.add(item3);
            StockItem item5 = new StockItem();
            item5.setSkuNum("100-100:100-100");
            item5.setCount(100);
            list.add(item5);

            si.setProductId(productId);
            si.setStockItems(list);

            ////////////////////////////////////////////////////

//            System.err.println(client.removeStock(productId));
//            System.err.println(client.setStock(productId, si));
//            StockResult sr = client.getStock(productId);

//            System.err.println(sr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    @Test
    public void testGetSkuPart() throws Exception {
        //商品id
        String productId = "ze151115005107000757";
        try {
            ////////////////////////////////////////////////////
            List<String> list = new ArrayList<>();
            list.add("");

            ////////////////////////////////////////////////////

//            System.err.println(client.removeStock(productId));
//            System.err.println(client.createStock(productId, si));
            StockResult sr = client.getStockForSku(productId, list);

            System.err.println(sr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }

    @Test
    public void testLockStock() throws Exception {
        //商品id
        String productId = "ze0000test";
        try {
            ////////////////////////////////////////////////////
            LockInfo lockInfo  = new LockInfo();
            lockInfo.setProductId(productId);
            lockInfo.setApplyCount(1);
            lockInfo.setSkuNum("200-200:200-200");
            List<LockInfo> lockInfos = new ArrayList<>();
            lockInfos.add(lockInfo);
//            System.err.println("getStock:==>" + client.getStock(productId));
//            System.err.println("lockStock:==>" + client.lockStock("testLockStock",lockInfos));
//            System.err.println("getStock:==>" + client.getStock(productId));
//            System.err.println("releaseLockCount:==>" + client.releaseLockCount("testLockStock", lockInfos));
//            System.err.println("getStock:==>" + client.getStock(productId));
//
//            System.err.println(sr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(transport!=null){
                transport.close();
            }
        }
    }
}
