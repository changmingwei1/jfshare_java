package com.jfshare.product.util;



import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.log4j.Logger;

public class ReflectUtil {
	private static Logger log = Logger.getLogger(ReflectUtil.class);
	/**
	 * 对字段  进行       取值  和   赋值  的功能
	 * 一    其中并不涉及到  supperClass
	 * 二    依赖于   setter  and    getter  方法
	 * 所以并没有使用
	 *  
	 */
//
//	private static Object operate(Object obj, String fieldName, Object fieldVal, String type) {
//		Object ret = null;
//		try {
//			// 获得对象类型
//			Class<? extends Object> classType = obj.getClass();
//			// 获得对象的所有属性
//			Field fields[] = classType.getDeclaredFields();
//			for (int i = 0; i < fields.length; i++) {
//				Field field = fields[i];
//				if (field.getName().equals(fieldName)) {
//					String firstLetter = fieldName.substring(0, 1).toUpperCase(); // 获得和属性对应的getXXX()方法的名字
//					if ("set".equals(type)) {
//						String setMethodName = "set" + firstLetter + fieldName.substring(1); // 获得和属性对应的getXXX()方法
//						Method setMethod = classType.getMethod(setMethodName, new Class[] { field.getType() }); // 调用原对象的getXXX()方法
//						ret = setMethod.invoke(obj, new Object[] { fieldVal });
//					}
//					if ("get".equals(type)) {
//						String getMethodName = "get" + firstLetter + fieldName.substring(1); // 获得和属性对应的setXXX()方法的名字
//						Method getMethod = classType.getMethod(getMethodName, new Class[] {});
//						ret = getMethod.invoke(obj, new Object[] {});
//					}
//					return ret;
//				}
//			}
//		} catch (Exception e) {
//			log.warn("reflect error:" + fieldName, e);
//		}
//		return ret;
//	}
//
//	public static Object getVal(Object obj, String fieldName) {
//		return operate(obj, fieldName, null, "get");
//	}
//
//	public static void setVal(Object obj, String fieldName, Object fieldVal) {
//		operate(obj, fieldName, fieldVal, "set");
//	}

	/**
	 * 一    得到方法
	 * 二    执行方法
	 */
//	private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
//		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
//			try {
//				// superClass.getMethod(methodName, parameterTypes);
//				return superClass.getDeclaredMethod(methodName, parameterTypes);
//			} catch (NoSuchMethodException e) {
//				// Method 不在当前类定义, 继续向上转型
//			}
//		}
//
//		return null;
//	}
//	
//	public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters)
//			throws InvocationTargetException {
//		Method method = getDeclaredMethod(object, methodName, parameterTypes);
//
//		if (method == null) {
//			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
//		}
//
//		method.setAccessible(true);
//
//		try {
//			return method.invoke(object, parameters);
//		} catch (IllegalAccessException e) {
//
//		}
//
//		return null;
//	}
//	
	/**
	  * <p>makeAccessible</p>
	  * @Description:使此filed不做 java语言访问检测
	  * @param field
	 */
	private static void makeAccessible(Field field) {
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}
	/**
	 * 
	  * <p>getDeclaredField</p>
	  * @Description:得到object中名字为filedName的成员变量
	  * @param object
	  * @param filedName
	  * @return
	 */
	private static Field getDeclaredField(Object object, String filedName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(filedName);
			} catch (NoSuchFieldException e) {
				// Field 不在当前类定义, 继续向上转型
			}
		}
		return null;
	}



	/**
	 * 
	  * <p>setFieldValue</p>
	  * @Description:
	  * @param object
	  * @param fieldName
	  * @param value
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	  * <p>getFieldValue</p>
	  * @Description:
	  * @param object
	  * @param fieldName
	  * @return
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);
		if (field == null)
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return result;
	}

}
