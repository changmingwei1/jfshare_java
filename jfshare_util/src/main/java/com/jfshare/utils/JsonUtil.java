package com.jfshare.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.*;

/**
 * JsonUtil
 * 
 * @author CUIJIANBIN
 */
@SuppressWarnings({ "all" })
public class JsonUtil {

	/**
	 * 实体转string
	 * 
	 * @param bean
	 * @return
	 */
	public static String beanToJson(Object bean) {
		JSONObject jsonObject = JSONObject.fromObject(bean);
		return jsonObject.toString();
	}

	public static String mapToJson(Map map) {
		JSONObject jsonObject = new JSONObject();
		for (Object key : map.keySet()) {
			jsonObject.put(key + "", map.get(key));
		}
		return beanToJson(jsonObject);
	}

	public static <T> Object jsonToObject(Object json, Class<T> beanClz) {

		return JSONObject.toBean(JSONObject.fromObject(json), beanClz);
	}

	public static <T> String ListToJson(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);
		return jsonArray.toString();
	}

	public static <T> List<T> JsonToList(String json, Class<T> beanClz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		JSONObject jsonObject;
		Object pojoValue;

		List<T> list = new ArrayList<T>();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, beanClz);
			list.add((T) pojoValue);
		}
		return list;
	}

	public static List<Map> json2Array(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonObject2Map(jsonArray.getJSONObject(i)));
		}
		return list;
	}

	public static Map<String, Object> json2Map(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return jsonObject2Map(jsonObject);
	}

	public static <K, V> void parseJson2Map(String json, Map<K, V> hm) {
		try {
			if (json == null || "".equals(json))
				return;
			ObjectMapper objectMapper = new ObjectMapper();
			Map<K, V> maps = objectMapper.readValue(json, Map.class);
			if (maps != null) {
				hm.putAll(maps);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Map<String, Object> jsonObject2Map(JSONObject jsonObject) {
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
}
