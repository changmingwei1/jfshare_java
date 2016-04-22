import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.finagle.thrift.brand.BrandResult;
import com.jfshare.finagle.thrift.brand.BrandServ;
import com.jfshare.finagle.thrift.result.Result;


public class Test_brand {
    // 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private BrandServ.Client client;

    private static final String IP = "127.0.0.1";

    private static final Integer PORT = 1981;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new BrandServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    @Test
    public void testM1() throws Exception {
        if (DO_IT == 0)
            return;
        
 /*       BrandInfo info = new BrandInfo();
        info.setCreateId(33);
        info.setName("hewei");
        info.setImgKey("heweikey");
        Result result = client.addBrand(info);
        System.out.print("返回码=" + result);*/
        
/*        BrandInfo info = new BrandInfo();
        info.setId(834);
        info.setCreateId(33);
        info.setName("hewei");
        info.setImgKey("heweikey2");
        info.setLastUpdateId(22222);
        Result result = client.updateBrand(info);
        System.out.print("返回码=" + result);*/
        
        
        /*Result result = client.deleteBrand(833);
        System.out.print("返回码=" + result);*/
        
        BrandResult result = client.queryBySubject(3001);
        System.out.print("返回码=" + result);

//        QueryParam param = new QueryParam();
////        param.setId(1);
//param.setPageSize(2);
//        param.setCurPage(1);
//        BrandResult ret = client.query();
//        System.out.println("返回码="+ret);

/*        List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);ids.add(2);
        BrandResult brandResult = client.queryBatch(ids);
        System.out.println("返回码="+brandResult);*/
    }
}
