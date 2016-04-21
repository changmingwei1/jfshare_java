package com.jfshare.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by lenovo on 2015/10/7.
 */
public class BeanUtil {
    private transient static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BeanUtil.class);

    /**
     * JavaBean转换为HashMap
     * @param bean 待转换对象
     * @return KEY字段值大写的HashMap,失败返回空HashMap
     */
    public static Map<String, Object> transBean2Map(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(bean == null) return map;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName().toUpperCase();

                // 过滤class属性
                if (!key.equals("CLASS")) {
                    Method getter = property.getReadMethod(); //得到property对应的getter方法
                    if (getter != null) {
                        key = postSpecialMethodKey(key, getter.getReturnType().getSimpleName(), getter.getName());
                        try {
                            Object value = getter.invoke(bean);
                            map.put(key, value);
                        } catch (Exception e) {
                            map.put(key, StringUtil.Empty);
                            log.error(MessageFormat.format("JavaBean[{0}]转换为HashMap失败！检查[{1}]字段，{2}", bean.getClass().getName(), key, e));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(MessageFormat.format("JavaBean[{0}]转换为HashMap失败！检查[{1}]字段，{2}", bean.getClass().getName(), null, e));
        }
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

        return map;
    }

    public static Map<String, String> transThrift2StringMap(Object bean) {
        Map<String, String> map = new HashMap<String, String>();
        if(bean == null) return map;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName().toUpperCase();
                // 过滤class属性
                if (!key.equals("CLASS")&&!key.startsWith("SET")&&!key.endsWith("ISSET")) {
                    Method getter = property.getReadMethod(); //得到property对应的getter方法
                    if (getter != null) {
                        key = postSpecialMethodKey(key, getter.getReturnType().getSimpleName(), getter.getName());
                        try {
                            Object value = getter.invoke(bean);
                            if(value == null) {
                                continue;
                            }
                            if(Map.class.isAssignableFrom(value.getClass())||List.class.isAssignableFrom(value.getClass())) {
                                map.put(key, JsonMapper.toJson(value));
                            } else {
                                map.put(key, String.valueOf(value));
                            }
                        } catch (Exception e) {
                            map.put(key, StringUtil.Empty);
                            log.error(MessageFormat.format("JavaBean[{0}]转换为HashMap失败！检查[{1}]字段，{2}", bean.getClass().getName(), key, e));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(MessageFormat.format("JavaBean[{0}]转换为HashMap失败！检查[{1}]字段，{2}", bean.getClass().getName(), null, e));
        }
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

        return map;
    }

    /**
     * 填充实体值
     * @param bean 待填充实体
     * @param propertyMap 填充项List
     */
    public static void fillBeanData(Object bean, Map<String, Object> propertyMap) {
        Set<Map.Entry<String, Object>> set = propertyMap.entrySet();
        for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
            Object value = entry.getValue();
            fillBeanData(bean, entry.getKey(), value);
        }
    }

    /**
     * 填充实体值
     * @param bean 待填充实体
     * @param propertyMap 填充项List
     */
    public static void fillBeanDataStringMap(Object bean, Map<String, String> propertyMap) {
        Set<Map.Entry<String, String>> set = propertyMap.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            Object value = entry.getValue();
            fillBeanData(bean, entry.getKey(), value);
        }
    }

    /**
     * 填充实体值
     * @param bean 待填充实体
     * @param key 待填充字段
     * @param value 待填充值
     */
    @SuppressWarnings("rawtypes")
    public static void fillBeanData(Object bean, String key, Object value) {
        if (bean == null || key == null || value == null) return;
        key = key.toUpperCase();

        Class<? extends Object> cls = bean.getClass();
        Method[] methods = cls.getMethods(); //取出bean里的所有方法
        for(int i = 0; i < methods.length; i++){
            String method = methods[i].getName(); //取方法名
            if (!method.startsWith("set")) continue;
            Class[] cc = methods[i].getParameterTypes(); //取出方法的类型
            if (cc.length != 1) continue;
            String type = cc[0].getSimpleName(); //类型

            String curKey = method.substring(3).toUpperCase();
            curKey = postSpecialMethodKey(curKey, type, key);
            if(curKey.equals(key)){
                setBeanVal(bean, type, methods[i], value);
                break;
            }
        }
    }

    /**
     * set、get方法名特殊处理
     * @param curKey 当前字段名称
     * @param type 字段类型  boolean
     * @param methodName 属性方法名称
     * @return 正确的大写字段名称
     * 不完全准确，第三个参数不保证为is+大写格式
     * 如ISCONTROLLED = (CONTROLLED, boolean, isControlled)
     */
    private static String postSpecialMethodKey(String curKey, String type, String methodName) {
        if (type.equals(boolean.class.getSimpleName())) {
            if (methodName.toUpperCase().equals("IS" + curKey)) {
                return methodName.toUpperCase();
            }
        }

        return curKey;
    }

    /**
     * 反射set属性值
     * 元数据涉及String、int、boolean类型
     * @param bean bean对象实例名
     * @param type 字段类型
     * @param method set方法名
     * @param value 待set的值
     */
    private static void setBeanVal(Object bean, String type, Method method, Object value) {
        try {
            if (type.equals(String.class.getSimpleName())) {
                method.invoke(bean, new Object[] {ConvertUtil.getString(value)});
            }
            else if (type.equals(boolean.class.getSimpleName())) {
                method.invoke(bean, new Object[] {ConvertUtil.getBoolean(value)});
            }
            else if (type.equals(int.class.getSimpleName()) || type.equals(Integer.class.getSimpleName())) {
                method.invoke(bean, new Object[] {ConvertUtil.getInt(value, 0)});
            }
        } catch (Exception e) {
            log.error(MessageFormat.format("填充JavaBean[{0}]SET方法[{1}]=[{2}]失败！{3}", bean.getClass().getName(), method.getName(), value, e));
        }
    }

    /**
     *
     * @param bean
     * @param name
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     */
    public static String getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

        Method method;

        if (null == bean) {
            throw new NullPointerException("Object Cannot be Null.");
        }
        if (null == name) {
            throw new NullPointerException("Field Name Cannot be Null.");
        }
        method = bean.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));

        return (String) method.invoke(bean, (Object[]) null);

    }

    /**
     *
     * @param bean
     * @param name
     * @param value
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static void setProperty(Object bean, String name, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (null == bean) {
            throw new NullPointerException("Object Cannot be Null.");
        }
        if (null == name) {
            throw new NullPointerException("Field Name Cannot be Null.");
        }
        Class[] parameterTypes = new Class[]{String.class};
        Object[] argumentsList = new Object[]{value};
        Method setterMethod = bean.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), parameterTypes);
        setterMethod.invoke(bean, argumentsList);
    }
}
