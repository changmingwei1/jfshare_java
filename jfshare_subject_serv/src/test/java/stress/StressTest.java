package stress;

import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig.Yes;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Duration;
import com.jfshare.finagle.thrift.subject.DisplaySubjectInfo;
import com.jfshare.finagle.thrift.subject.DisplaySubjectParam;
import com.jfshare.finagle.thrift.subject.SubjectServ;
import junit.framework.TestCase;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StressTest extends TestCase {
	private TTransport transport;
	private SubjectServ.Client client;
	
	int sellerId = 368469;
	
	@Override
	public void setUp() throws Exception {
//		transport = new TFramedTransport(new TSocket("192.168.10.57",12306));
		transport = new TFramedTransport(new TSocket("localhost",12303));
		TProtocol protocol = new TBinaryProtocol(transport);
		client = new SubjectServ.Client(protocol);
		transport.open();
	}
	
	@Override
	public void tearDown(){
		transport.close();
	}
	

	public static void main(String[] args) {
//		.hosts(new InetSocketAddress("192.168.200.50", 12306))
		final ClientBuilder<ThriftClientRequest, byte[], Yes, Yes, Yes> clientBuilder = ClientBuilder
				.get().codec(ThriftClientFramedCodec.get())
//				.hosts(new InetSocketAddress("localhost", 12303))
				.hosts(new InetSocketAddress("192.168.10.66", 12342))
				.keepAlive(true)
				.hostConnectionLimit(30000)
				.hostConnectionCoresize(50)
				.hostConnectionIdleTime(new Duration(2 * Duration.NanosPerSecond()))
				.hostConnectionMaxIdleTime(new Duration(10 * Duration.NanosPerSecond()))
				.hostConnectionMaxLifeTime(new Duration(300 * Duration.NanosPerSecond()))
				.tcpConnectTimeout(new Duration(2 * Duration.NanosPerSecond()));
		final Service<ThriftClientRequest, byte[]> service = ClientBuilder.safeBuild(clientBuilder);

		ExecutorService executorService = Executors.newFixedThreadPool(1000);
		for(int i=0;i<100;i++){
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							long longStart = System.currentTimeMillis();
							SubjectServ.ServiceToClient client = new SubjectServ.ServiceToClient(service,new TBinaryProtocol.Factory());

//							Future<StringResult> subject = client.getWholeTree().get();
//							client.getById(700).get();
                            DisplaySubjectParam param = new DisplaySubjectParam();
                            DisplaySubjectInfo subjectInfo = new DisplaySubjectInfo();
                            subjectInfo.setId(1080);
                            param.setDisplaySubjectInfo(subjectInfo);
                            param.setFlag("main");
//							client.getById4dis(param).get();
							client.getSubTree4dis(param).get();
							System.out.println(System.currentTimeMillis() - longStart);
//							Thread.currentThread().sleep(10000000);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}

}
