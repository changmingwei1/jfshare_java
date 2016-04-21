package com.jfshare.subject.dependserv;


import com.jfshare.finagle.thrift.brand.BrandInfo;
import com.jfshare.finagle.thrift.brand.BrandResult;
import com.jfshare.finagle.thrift.brand.BrandServ;
import com.jfshare.ridge.PropertiesUtil;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandClient {
	private Logger logger = LoggerFactory.getLogger(BrandClient.class);
	private final static int socketTimeout = 5*1000;

	public List<BrandInfo> getBrandsByIds(List<Integer> brandIds) throws Exception{

        List<BrandInfo> brands = null;
		TTransport transport = null;
		try {
			String ipClient = PropertiesUtil.getProperty("jfx_subject_serv","brand_serv_ip");
			int port = Integer.parseInt(PropertiesUtil.getProperty("jfx_public_client","brand_port"));
            transport = new TFramedTransport(new TSocket(ipClient, port, socketTimeout));
            TProtocol protocol = new TBinaryProtocol(transport);
            BrandServ.Client client = new BrandServ.Client(protocol);
            transport.open();

            BrandResult result = client.queryBatch(brandIds);
            
            if(result.getResult().getCode() == 0) {
                brands =  result.getBrandInfo();
            }
            transport.close();
            
        } catch (Exception e) {
        	logger.info("cityAttribution 异常",e);
            throw new Exception("调用 brand-serv 发生异常!");
        } finally {
			try {
				if(transport != null)
					transport.close(); 
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
        }
		
		return brands;
	}
}
