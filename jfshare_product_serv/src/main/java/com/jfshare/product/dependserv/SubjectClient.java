package com.jfshare.product.dependserv;

import com.jfshare.finagle.thrift.subject.SubjectNode;
import com.jfshare.finagle.thrift.subject.SubjectServ;
import com.jfshare.finagle.thrift.subject.SubjectTreeResult;
import com.jfshare.ridge.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectClient {
	private Logger logger = LoggerFactory.getLogger(SubjectClient.class);
	private final static int socketTimeout = 5*1000;

	public SubjectTreeResult getSuperTree(Integer subjectId){

		SubjectTreeResult result = new SubjectTreeResult();
		
		TTransport transport = null;
		try {
			String ipClient = PropertiesUtil.getProperty("jfx_public_client","subject_serv_ips");
			int port = Integer.parseInt(PropertiesUtil.getProperty("jfx_public_client","subject_port"));
            transport = new TFramedTransport(new TSocket(ipClient, port, socketTimeout));
            TProtocol protocol = new TBinaryProtocol(transport);
            SubjectServ.Client client = new SubjectServ.Client(protocol);
            transport.open();

            result = client.getSuperTree(subjectId);
            
        } catch (Exception e) {
            //throw new Exception("调用 subject-serv 发生异常!");
            logger.warn("调用 subject-serv 发生异常!");
        } finally {
			try {
				if(transport != null)
					transport.close(); 
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
        }
		
		return result;
	}

	public List<SubjectNode> getSubTree(Integer subjectId){
		List<SubjectNode> subjectNodes = new ArrayList<SubjectNode>();

		TTransport transport = null;
		try {
			String ipClient = PropertiesUtil.getProperty("jfx_public_client","subject_serv_ips");
			int port = Integer.parseInt(PropertiesUtil.getProperty("jfx_public_client","subject_port"));
			transport = new TFramedTransport(new TSocket(ipClient, port, socketTimeout));
			TProtocol protocol = new TBinaryProtocol(transport);
			SubjectServ.Client client = new SubjectServ.Client(protocol);
			transport.open();

			SubjectTreeResult result = client.getSubTree(subjectId);
			if (result.getResult().getCode() == 1 || CollectionUtils.isEmpty(result.getSubjectNodes())) {
				return subjectNodes;
			}
			return result.getSubjectNodes();

		} catch (Exception e) {
			//throw new Exception("调用 subject-serv 发生异常!");
			logger.warn("调用 subject-serv 发生异常!");
		} finally {
			try {
				if(transport != null)
					transport.close();
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}

		return subjectNodes;
	}

	public List<SubjectNode> getLeavesById(Integer subjectId){
		List<SubjectNode> subjectNodes = new ArrayList<SubjectNode>();

		TTransport transport = null;
		try {
			String ipClient = PropertiesUtil.getProperty("jfx_public_client","subject_serv_ips");
			int port = Integer.parseInt(PropertiesUtil.getProperty("jfx_public_client","subject_port"));
			transport = new TFramedTransport(new TSocket(ipClient, port, socketTimeout));
			TProtocol protocol = new TBinaryProtocol(transport);
			SubjectServ.Client client = new SubjectServ.Client(protocol);
			transport.open();

			SubjectTreeResult result = client.getLeavesById(subjectId);
			if (result.getResult().getCode() == 1 || CollectionUtils.isEmpty(result.getSubjectNodes())) {
				return subjectNodes;
			}
			return result.getSubjectNodes();

		} catch (Exception e) {
			//throw new Exception("调用 subject-serv 发生异常!");
			logger.warn("调用 subject-serv 发生异常!");
		} finally {
			try {
				if(transport != null)
					transport.close();
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}

		return subjectNodes;
	}



}
