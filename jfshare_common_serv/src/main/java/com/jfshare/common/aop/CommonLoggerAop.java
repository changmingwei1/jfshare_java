package com.jfshare.common.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.jfshare.finagle.thrift.result.FailDesc;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonLoggerAop {
    private Logger logger = LoggerFactory.getLogger(CommonLoggerAop.class);
    private Map<String, Class<?>> tempClazz = new Hashtable<String, Class<?>>();
    private Map<String, Class<?>> mothedClazz = new Hashtable<String, Class<?>>();
    private Map<String , Object> errResultClazz = new Hashtable<String , Object>();

    @Pointcut("execution(public * com.jfshare.*.server.*.*(..))")
    public void pctMethod() {
    }

    @Around(value = "pctMethod()")
    public Object doLogAndTimer(ProceedingJoinPoint pjp) throws Throwable {
        String mtdName = pjp.getSignature().getName();
        logger.info(">>>[" + mtdName + "] start<<<");
        long startTime = System.currentTimeMillis();
        logger.info("startTime:" + startTime + "...");
        try {
            Object ret = pjp.proceed();
            return ret;
        } catch (Exception e) {
            logger.error(e.getMessage());
            String className = pjp.getSignature().getDeclaringTypeName();
            String methodName = pjp.getSignature().getName();
            Object errresult = errResultClazz.get(className+"_"+methodName);
            if(errresult == null){
                errresult =  exceptionReturn(  pjp);
                errResultClazz.put(className+"_"+methodName,errresult);
            }
            logger.info("function start : "+pjp.getSignature().getName()+"  out  "+System.currentTimeMillis());
            logger.error(e.toString(),e);
            return errresult;
        } finally {
            logger.info(">>>[" + mtdName + "] end<<<");
            long endTime = System.currentTimeMillis();
            logger.info("endTime:" + endTime + "...");
            logger.info("responseTime:" + (endTime - startTime) + "...");
        }
    }

    private Object exceptionReturn(ProceedingJoinPoint pjp) throws InstantiationException, IllegalAccessException,
            ClassNotFoundException, NoSuchFieldException, SecurityException {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        Class clazz = tempClazz.get(className);
        if (clazz == null) {
            clazz = Class.forName(className);
            tempClazz.put(className, clazz);
        }

        Class returnClazz = mothedClazz.get(className + "_" + methodName);
        if(returnClazz==null){
            Method[] mothods = clazz.getMethods();
            if (mothods != null) {
                for (Method m : mothods) {
                    if (methodName.equals(m.getName())) {
                        returnClazz = m.getReturnType();
                        mothedClazz.put(className + "_" + methodName, returnClazz);
                        break;
                    };
                };
            };
        };

        Object pruckResult =  returnClazz.newInstance();
        Field result = null;

        try{
            result =pruckResult.getClass().getField("result");
        }catch(Exception w){
            logger.error(w.toString());
        }
        if(result!=null){
            result.setAccessible(true);
            Object erroCode = result.getType().newInstance();
            result.set(pruckResult, erroCode);
            //返回错误码
            Field codeFiled = null;
            try{
                codeFiled = erroCode.getClass().getField("code");
            }catch(Exception w){
                logger.error( w.toString());
            }
            if(codeFiled!=null){
                codeFiled.setAccessible(true);
                codeFiled.setInt( erroCode, 1);
            }
            //返回错误信息
            Field msgFiled = null;
            try{
                msgFiled = erroCode.getClass().getField("failDescList");
            }catch(Exception w){
                logger.error( w.toString());
            }
            if(codeFiled!=null){
                List errorcodes = new ArrayList();
                errorcodes.add( new FailDesc("system", "20009", "系统异常"));
                msgFiled.setAccessible(true);
                msgFiled.set(erroCode, errorcodes) ;
            }

        }else{
            //错误码
            Field codeFiled =  null;
            try{
                codeFiled =  pruckResult.getClass().getField("code");
            }catch(Exception w ){
                logger.error( w.toString());
            }

            if(codeFiled!=null){
                codeFiled.setAccessible(true);
                codeFiled.setInt( pruckResult, 1);
            }
            //返回错误信息
            Field msgFiled = null;
            try{
                msgFiled = pruckResult.getClass().getField("failDescList");
            }catch(Exception w){
                logger.error( w.toString());
            }
            if(codeFiled!=null){
                List errorcodes = new ArrayList();
                errorcodes.add( new FailDesc("system", "20009", "系统异常"));
                msgFiled.setAccessible(true);
                msgFiled.set(pruckResult, errorcodes) ;
            }
        }

        return pruckResult;
    }
}
