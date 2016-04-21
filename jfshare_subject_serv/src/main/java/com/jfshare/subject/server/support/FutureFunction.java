package com.jfshare.subject.server.support;

import com.twitter.util.Function0;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * 结果异步返回实现类
 * @author Lenovo
 *
 * @param <R>
 */
public class FutureFunction<R> extends Function0<R>{

	/**
	 * 被调用方法名称
	 */
	private String methodName;
	
	/**
	 * 方法调用参数
	 */
	private Object[] params;
	
	/**
	 * 实际业务处理类
	 */
	private IHandler handler;
	
	public FutureFunction(String methodName, Object... params) {
		this.methodName = methodName;
		this.params = params;
	}

	public FutureFunction(IHandler handler, String methodName, Object... params) {
		this.handler = handler;
		this.methodName = methodName;
		this.params = params;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R apply() {
		R result = null;
		Class<?>[] paramsClass = null;

		try {
			if (params.length > 0) {
				paramsClass = new Class[params.length];
				for (int i = 0; i < params.length; i++) {
					paramsClass[i] = params[i] == null ? null : params[i].getClass();
				}
			}
		    result = (R) MethodUtils.invokeMethod(handler, methodName, params, paramsClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
