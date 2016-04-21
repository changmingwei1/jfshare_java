package com.jfshare.cart.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.log4j.Logger;

import com.jfshare.utils.PriceUtils;

public class CopyUtil {

	private static final Logger logger = Logger.getLogger(CopyUtil.class);

	public static void copy(Object source, Object dest) {
		try {
			// 获取属性
			BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), java.lang.Object.class);
			PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

			BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), java.lang.Object.class);
			PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

			for (int i = 0; i < sourceProperty.length; i++) {
				for (int j = 0; j < destProperty.length; j++) {
					if (sourceProperty[i].getName().equals(destProperty[j].getName())) {
						// 调用source的getter方法和dest的setter方法
						destProperty[j].getWriteMethod().invoke(dest, sourceProperty[i].getReadMethod().invoke(source));
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("属性cope异常!", e);
		}
	}
}
