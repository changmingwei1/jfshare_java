package com.jfshare.card.server.depend;

import javax.annotation.PostConstruct;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfshare.card.util.FailCode;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.score.ScoreServ;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.ridge.ConfigManager;
import com.jfshare.utils.StringUtil;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.builder.ClientConfig;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Await;

/**
 * Created by lenovo on 2015/10/31.
 */
@org.springframework.stereotype.Service
public class ScoreClient {
    private Logger logger = LoggerFactory.getLogger(ScoreClient.class); 
    private ScoreServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;
    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "score_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "score_serv_port");
    	
//        String ips = "101.201.38.182";
//        String port = "1984";
        
        StringBuilder hosts = new StringBuilder();
        String[] ipArray = ips.split(";");
        for(String ip : ipArray) {
            hosts.append(ip.trim()).append(":").append(port);
        }
        ClientBuilder<ThriftClientRequest, byte[], ClientConfig.Yes, ClientConfig.Yes, ClientConfig.Yes> clientBuilder = ClientBuilder.get()
                .codec(ThriftClientFramedCodec.get())   //设置解码器类型
                .hosts(hosts.toString())    //设置访问ip和端口
                .hostConnectionLimit(100)  //连接池
                //.timeout(new Duration(5000L))  //总的请求超时
                .retries(3)   // 重试次数
                .keepAlive(true)     //是否是长连接
                ;
        Service<ThriftClientRequest, byte[]> client = ClientBuilder.safeBuild(clientBuilder);
        client.isAvailable();
        service = new ScoreServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

	public StringResult income(ScoreTrade scoreTrade) {
		StringResult stringResult = new StringResult();
		Result result = new Result();
		result.setCode(0);
		stringResult.setResult(result);
		if (StringUtil.isNullOrEmpty(scoreTrade) || scoreTrade.getUserId() <= 0 || scoreTrade.getInOrOut() != 1
				|| scoreTrade.getAmount() <= 0) {
			FailCode.addFails(result, FailCode.requestArgsIsNull);
			return stringResult;
		}
        try {
        	stringResult = Await.result(service.income(scoreTrade));
        	return stringResult;
        } catch (Exception e) {
            logger.error("调用ScoreServ--income失败！", e);
        }
        return null;
    }
}
