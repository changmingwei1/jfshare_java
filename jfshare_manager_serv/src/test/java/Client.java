import com.jfshare.finagle.thrift.manager.ManagerServ;
import com.jfshare.finagle.thrift.manager.ProductOpt;
import com.jfshare.finagle.thrift.manager.ProductOptResult;
import com.jfshare.finagle.thrift.manager.QueryConditions;
import com.jfshare.finagle.thrift.result.Result;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;

public class Client extends TestCase {

    private TTransport transport;
    private ManagerServ.Client client;

    @Override
    public void setUp() throws Exception {

        transport = new TFramedTransport(new TSocket("localhost", 1988));

        TProtocol protocol = new TBinaryProtocol(transport);
        client = new ManagerServ.Client(protocol);
        transport.open();
    }

    @Override
    public void tearDown(){
        transport.close();
    }


//    @Test
    public void testQueryProductOptRecords() throws Exception {
        //商品id
        String productId = "ze151024205818000350";
        QueryConditions conditions = new QueryConditions();
        conditions.setProductId(productId);
        try {
            ProductOptResult sr = client.queryProductOptRecords(conditions);

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
    public void testLogProductOptRecords() throws Exception {
        try {
            //商品id
            String productId = "ze151024205818000350";
            ProductOpt po = new ProductOpt();
            po.setOperatorType(1);
            po.setProductId("ze151210145613000059");
            po.setOperatorId("1");
            po.setCreateTime("2015-12-19 22:22:22");
            po.setActiveState(101);
            po.setDesc("商家下架");
            Result sr = client.logProductOpt(po);

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
    
    
}
