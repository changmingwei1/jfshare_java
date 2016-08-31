package com.jfshare.buyer.server.depend;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfshare.buyer.util.FailCode;
import com.jfshare.finagle.thrift.common.CommonServ;
import com.jfshare.finagle.thrift.common.MsgCaptcha;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.ridge.ConfigManager;
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
public class CommonClient {
    private Logger logger = LoggerFactory.getLogger(CommonClient.class); 
    private CommonServ.ServiceIface service = null;
    @Autowired
    private ConfigManager configManager;
    @PostConstruct
    public void init() {
        String ips = configManager.getConfigValue("jfx_public_client", "common_serv_ips");
        String port = configManager.getConfigValue("jfx_public_client", "common_port");
    	
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
        service = new CommonServ.ServiceToClient(client, new TBinaryProtocol.Factory());
    }

	public Result validateMsgCaptcha(MsgCaptcha captcha) {
		 Result result = new Result(0);

	        if(captcha == null
	                || StringUtils.isBlank(captcha.getMobile())
	                || StringUtils.isBlank(captcha.getType())
	                || StringUtils.isBlank(captcha.getCaptchaDesc())){
	            result.setCode(1);
	            result.addToFailDescList(FailCode.PARAM_ERROR);
	            return result;
	        }
        try {
        	result = Await.result(service.validateMsgCaptcha(captcha));
        	
        	return result;
        } catch (Exception e) {
            logger.error("调用commonServ--getCommon失败！", e);
        }
        return null;

    }
}
