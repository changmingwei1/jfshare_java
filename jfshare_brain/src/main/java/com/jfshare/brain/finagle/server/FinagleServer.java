package com.jfshare.brain.finagle.server;

import com.jfshare.brain.finagle.util.ApplicationBean;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.thrift.ThriftServerFramedCodec;
import com.twitter.util.*;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import java.lang.reflect.*;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Finagle 服务端代理类
 * 服务启动、调用器
 * Created by lilx on 2015/9/28.
 */
public class FinagleServer  implements InvocationHandler {
    private transient static Logger logger = Logger.getLogger(FinagleServer.class);

    //线程池
    private ExecutorServiceFuturePool futurePool = null;
    //要代理的原始对象
    private Object service;
    private FinagleServerCfg cfg;

    public FinagleServer (FinagleServerCfg cfg) {
        this.cfg = cfg;
        try {
            this.service = ApplicationBean.getApplicationContext().getBean(cfg.getRef());
            logger.info("服务启动信息，params=" + cfg);
            this.start();
        } catch (Exception e) {
            logger.error("服务初始化失败 !params=" + cfg , e);
        }
    }

    /**
     * 启动服务
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public void start() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //finagle接口文件父类
        Class thriftApi = Class.forName(cfg.getApi());
        //提供服务的接口
        Class serviceIface = null;
        Class serviceImpl = null;
        //启动服务的实现类
        Class serviceClass = null;
        for (Class c : thriftApi.getDeclaredClasses()) {
            if ("Service".equals(c.getSimpleName())) {
                serviceClass = c;
            }
            if ("ServiceIface".equals(c.getSimpleName())) {
                serviceIface = c;
            }
            if ("ServiceToClient".equals(c.getSimpleName())) {
                serviceImpl = c;
            }
        }

        //1、生成代理类（此处借用finagle生成ServiceToClient类。）
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), serviceImpl.getInterfaces(), this);
        //2、获取构造函数
        Constructor con = serviceClass.getConstructor(serviceIface, TProtocolFactory.class);
        //3、反射生成service对象(相当于shell类)
        Service service = (Service) con.newInstance(proxy, new TBinaryProtocol.Factory());
        //4、创建finagle启动服务需要的对象
        ServerBuilder serverBuilder = ServerBuilder.get()
                .name(cfg.getSid())
                .codec(ThriftServerFramedCodec.get())
                .requestTimeout(new Duration(cfg.getStimeout() * Duration.NanosPerMillisecond()))//请求超时时间
                .keepAlive(true)
//                .hostConnectionMaxIdleTime(new Duration(10 * Duration.NanosPerSecond()))
//                .hostConnectionMaxLifeTime(new Duration(300 * Duration.NanosPerSecond()))//如果加上,压测时候超过5分钟就会挂掉
                .bindTo(new InetSocketAddress(cfg.getPort()));
        ExecutorService executorService = Executors.newFixedThreadPool(cfg.getThreads());
        futurePool = new ExecutorServiceFuturePool(executorService);
        //5、启动服务
        ServerBuilder.safeBuild(service, serverBuilder);
        logger.info("Finagle thrift [" + cfg.getApi() + "], sid [" + cfg.getSid()+ "], port [" + cfg.getPort()+ "], version [" + cfg.getVersion() + "]启动成功 !");
    }

    /**
     * 方法调用器
     * @param proxy finagle代理
     * @param method 方法
     * @param args  参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, final Method method, Object[] args) throws Throwable {
        Object result = null;
        result = futurePool.apply(new FinagleProxy(method.getName(), args));
        if(result instanceof Future) {
            ((Future) result).addEventListener(
                    new FutureEventListener() {
                        @Override
                        public void onSuccess(Object res) {
                            logger.info("方法调用成功，" + method.getName());
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            logger.error("方法调用失败，" + method.getName(), throwable);
                        }
                    }
            );
        }

        return result;
    }

    /**
     * 供thrift接口壳调用，内部调用具体实现类，传入参数为对象类型
     * @author lilx
     **/
    private class FinagleProxy extends Function0 {
        private String methodName;
        private Object[] params;

        public FinagleProxy(String methodName, Object... params) {
            this.methodName = methodName;
            this.params = params;
        }

        @Override
        public Object apply() {
            Object result = null;
            Class[] paramsClass = null;
            if(params != null && params.length > 0){
                paramsClass = new Class[params.length];
                for(int i = 0; i < params.length; i++){
                    //如果传递参数为null,会报nullpoint异常,需要做判断
                    paramsClass[i] = params[i] == null ? null : params[i].getClass();
                }
            }

            long timePoint1 =  System.currentTimeMillis();
            try {
                //solved param is a super class error
                result = MethodUtils.invokeMethod(service, methodName, params, paramsClass);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("FinagleProxy call ReflectiveOperationException",e);
            } finally {
                long timePoint2 = System.currentTimeMillis();
                logger.info("FinagleProxy call method[" + methodName + "], params[" + (params==null?null:Arrays.asList(params)) + "] "+
                        (result==null ? "failure!" : "success:ret[" + result + "]") +", Time consuming[" + (timePoint2 - timePoint1) + " ms]");
            }

            return result;
        }
    }
}
