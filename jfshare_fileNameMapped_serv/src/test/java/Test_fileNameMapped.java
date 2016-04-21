
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedInfo;
import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedResult;
import com.jfshare.finagle.thrift.fileNameMapped.FileNameMappedServ;
import com.jfshare.finagle.thrift.fileNameMapped.QueryParam;
import com.jfshare.finagle.thrift.result.StringResult;

/**
 * Created by lenovo on 2015/9/28.
 */
public class Test_fileNameMapped {
    // 执行次数，0的时候不执行测试用例
    private static final Integer DO_IT = 1;

    private TTransport transport;

    private TProtocol protocol;

    private FileNameMappedServ.Client client;

    private static final String IP = "127.0.0.1";

    private static final Integer PORT = 1989;

    @Before
    public void setUp() throws Exception {
        if (DO_IT == 0)
            return;

        transport = new TFramedTransport(new TSocket(IP, PORT));
        protocol = new TBinaryProtocol(transport);
        client = new FileNameMappedServ.Client(protocol);
        transport.open();
    }

    @After
    public void tearDown() throws Exception {
        if (DO_IT == 0)
            return;

        transport.close();
    }

    //@Test
    public void testQuery() throws Exception {
        if (DO_IT == 0)
            return;
        
        String filename = "trade/750x3056.3038e664e44f999c153dd5dd47752afc.jpg";
        
        QueryParam queryParam = new QueryParam();
        queryParam.setFilename(filename);
        
        FileNameMappedResult fileNameMappedResult = client.queryFileNameMapped(queryParam);

        System.out.println(fileNameMappedResult);
    }

    //@Test
    public void testSave() throws Exception {
        if (DO_IT == 0)
            return;
        
        String filename = "trade/750x3056.3038e664e44f999c153dd5dd47752afc.jpg";
        String fileid = "group1/M00/00/0B/wKgKU1PkdYCAEUsRAAS69DXn2GM830.jpg";
        
        FileNameMappedInfo fileNameMappedInfo = new FileNameMappedInfo();
        fileNameMappedInfo.setFilename(filename);
        fileNameMappedInfo.setFileid(fileid);
        
        StringResult stringResult = client.saveFileNameMappedInfo(fileNameMappedInfo);
        System.out.println(stringResult);
    }
}
