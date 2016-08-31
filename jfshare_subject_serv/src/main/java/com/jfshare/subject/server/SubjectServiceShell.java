package com.jfshare.subject.server;

import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;
import com.jfshare.finagle.thrift.subject.*;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.subject.server.support.FutureFunction;
import com.jfshare.subject.server.support.IHandler;
import com.jfshare.subject.util.Commons;
import com.twitter.finagle.builder.Server;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.thrift.ThriftServerFramedCodec;
import com.twitter.util.ExecutorServiceFuturePool;
import com.twitter.util.Future;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Finagle接口具体实现类
 * @author Lenovo
 *
 */

public class SubjectServiceShell implements SubjectServ.ServiceIface {
	
	private Logger logger = LoggerFactory.getLogger(SubjectServiceShell.class);
	private ExecutorServiceFuturePool futurePool;

	@Resource(name = "subjectHandler")
	private IHandler handler;

	/**
	 * 构建内部处理线程池
	 */
	public SubjectServiceShell() {
		ExecutorService pool = Executors.newFixedThreadPool(Integer.parseInt(PropertiesUtil.getProperty(Commons.APP_NAME, "workThreadCount")));
		futurePool = new ExecutorServiceFuturePool(pool);
	}
	
	/**
	 * 服务启动方法
	 */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public void start(){
		try {
			ServerBuilder serverBuilder = ServerBuilder
					.get()
					.name("SubjectService")
					.codec(ThriftServerFramedCodec.get())
					.bindTo(new InetSocketAddress(Integer.parseInt(PropertiesUtil.getProperty(Commons.APP_NAME, "serverPort"))));
                    //.tracer(ZipkinTracer.mk("192.168.10.67", 7001, new NullStatsReceiver(), (float) 1.0));
			Server productServ = ServerBuilder.safeBuild(new SubjectServ.Service(this, new TBinaryProtocol.Factory()), serverBuilder);

			logger.info(" subject service start... port : {}...", PropertiesUtil.getProperty(Commons.APP_NAME, "serverPort"));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
    
	}

    @Override
    public Future<Result> add(SubjectInfo subject) {
        return futurePool.apply(new FutureFunction<Result>(handler, "add", subject));
    }

    @Override
    public Future<Result> update(SubjectInfo subject) {
        return futurePool.apply(new FutureFunction<Result>(handler, "update", subject));
    }

    @Override
    public Future<Result> deleteById(int subjectId) {
        return futurePool.apply(new FutureFunction<Result>(handler, "deleteById", subjectId));
    }

    @Override
    public Future<SubjectInfoResult> getById(int subjectId) {
        return futurePool.apply(new FutureFunction<SubjectInfoResult>(handler, "getById", subjectId));
    }

    @Override
    public Future<SubjectTreeResult> getSubTree(int subjectId) {
        return futurePool.apply(new FutureFunction<SubjectTreeResult>(handler, "getSubTree", subjectId));
    }

    @Override
    public Future<SubjectTreeResult> getSuperTree(int subjectId) {
        return futurePool.apply(new FutureFunction<SubjectTreeResult>(handler, "getSuperTree", subjectId));
    }

    @Override
    public Future<SubjectTreeResult> getLeavesById(int subjectId) {
        return futurePool.apply(new FutureFunction<SubjectTreeResult>(handler, "getLeavesById", subjectId));
    }

    @Override
    public Future<StringResult> getWholeTree() {
        return futurePool.apply(new FutureFunction<StringResult>(handler, "getWholeTree"));
    }

    @Override
    public Future<SubjectTreeResult> getWholeTreeList() {
        return futurePool.apply(new FutureFunction<SubjectTreeResult>(handler, "getWholeTreeList"));
    }

    @Override
    public Future<Result> add4dis(DisplaySubjectParam displaySubjectParam) {
        return futurePool.apply(new FutureFunction<Result>(handler, "add4dis", displaySubjectParam));
    }

    @Override
    public Future<Result> update4dis(DisplaySubjectParam displaySubjectParam) {
        return futurePool.apply(new FutureFunction<Result>(handler, "update4dis", displaySubjectParam));
    }

    @Override
    public Future<Result> delete4dis(DisplaySubjectParam displaySubjectParam) {
        return futurePool.apply(new FutureFunction<Result>(handler, "delete4dis", displaySubjectParam));
    }

    @Override
    public Future<DisplaySubjectResult> getById4dis(DisplaySubjectParam displaySubjectParam) {
        return futurePool.apply(new FutureFunction<DisplaySubjectResult>(handler, "getById4dis", displaySubjectParam));
    }

    @Override
    public Future<DisplaySubjectTreeResult> getSubTree4dis(DisplaySubjectParam displaySubjectParam) {
        return futurePool.apply(new FutureFunction<DisplaySubjectTreeResult>(handler, "getSubTree4dis", displaySubjectParam));
    }

    @Override
    public Future<DisplaySubjectTreeResult> getSuperTree4dis(DisplaySubjectParam displaySubjectParam) {
        return futurePool.apply(new FutureFunction<DisplaySubjectTreeResult>(handler, "getSuperTree4dis", displaySubjectParam));
    }

	@Override
	public Future<StringResult> getDisplayIdsBySubjectId(int subjectId) {
	    return futurePool.apply(new FutureFunction<StringResult>(handler, "getDisplayIdsBySubjectId", subjectId));
	}

    @Override
    public Future<DisplaySubjectTreeResult> getWholeTreeList4dis(String type) {
        return futurePool.apply(new FutureFunction<DisplaySubjectTreeResult>(handler, "getWholeTreeList4dis", type));
    }

    @Override
    public Future<SubjectQueryResult> querySubjects(SubjectQueryParam queryParam, Page page) {
        return null;
    }

    @Override
    public Future<DisplaySubjectQueryResult> querySubjects4dis(DisplaySubjectQueryParam queryParam, Page page) {
        return null;
    }

    @Override
    public Future<Result> addChannel(DisplaySubjectChannelInfo channel) {
        return null;
    }

    @Override
    public Future<Result> updateChannel(DisplaySubjectChannelInfo channel) {
        return null;
    }

    @Override
    public Future<Result> deleteChannelById(int channelId) {
        return null;
    }

    @Override
    public Future<Result> addRelation(SubjectRefDisplayInfo relation) {
        return null;
    }

    @Override
    public Future<Result> deleteRelation(SubjectRefDisplayInfo relation) {
        return null;
    }

    @Override
    public Future<Result> releaseSubject(int subjectId) {
        return null;
    }

    @Override
    public Future<DisplaySubjectChannelQueryResult> queryChannels(DisplaySubjectChannelQueryParam queryParam, Page page) {
        return null;
    }

    @Override
    public Future<SubjectTreeResult> getSubTreeForManage(int subjectId) {
        return null;
    }

    @Override
    public Future<SubjectQueryResult> querySubjectsRelation(int displayId, Page page) {
        return null;
    }

	@Override
	public Future<Result> addSubjectAttribute(SubjectAttribute atrribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Result> updateSubjectAttribute(SubjectAttribute atrribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Result> deleteSubjectAttribute(SubjectAttribute atrribute) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Result> deleteSubjectAttributeBatch(
			List<SubjectAttribute> atrributeList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<SubjectAttributeResult> querySubjectAttribute(
			SubjectAttributeQueryParam param) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
