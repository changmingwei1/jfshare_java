package com.jfshare.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("all")
public class HolidayDelayUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HolidayDelayUtil.class);

	private static Properties properties = new Properties();

	private static Map<String, Object> map = new HashMap<String, Object>();

	static {
		try {
			// 服务变量配置文件路径
			String FileConfig = "/config/holidaydelay.properties";
			properties.load(HolidayDelayUtil.class.getResourceAsStream(FileConfig));

			Iterator it = properties.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String key = entry.getKey().toString();
				Object value = entry.getValue();
				if (value != null) {
					String[] values = value.toString().split(",");
					if (values == null || values.length < 3)
						continue;
					map.put(key, values);
				}
				System.out.println(key + ":" + value);
			}
		} catch (Exception e) {
			LOGGER.error("读取holidaydelay配置文件出错", e);
		}
	}

	/**
	 * 查询节假日延期天数
	 * 
	 * @param sDate
	 *            开始时间 格式：yyyy-MM-dd
	 * @param limit
	 *            事件完成天数
	 * @param type
	 *            事件类型： 0 ：发货; 1:售后; 2:维权
	 * @return
	 */
	public static int delayDays(String sDate, int limit, int type) {
		if (sDate == null || "".equals(sDate) || type > 2 || type < 0){
			LOGGER.info("delayDays param is error");
			return 0;
		}
		sDate = sDate.replace("-", "").replace(" ", "");
		
		int total = 0;
		int days = 0;
		for (int i = 1; i <= limit; i++) {
			String date = DateUtil.date2Str(DateUtil.addDay(DateUtil.str2Date(sDate, DateUtils.PATTERN_YYYYMMDD2), i), DateUtils.PATTERN_YYYYMMDD2);
			if (map.containsKey(date)) {
				String[] values = (String[]) map.get(date);
				days += Integer.parseInt(values[type]);
			}
			
			if(limit == i && days > 0)
				total = ss(date, days, type);
		}
		return total;
	}
	
	/**
	 * 递归
	 * @param date
	 * @param days
	 * @param type
	 * @return
	 */
	private static int ss(String date, int days,  int type) {
		if (date == null || "".equals(date) || type > 2 || type < 0)
			return 0;
		
		date = date.replace("-", "").replace(" ", "");

		int total = 0;
		int count = days;
		for (int i = 1; i <= days; i++) {
			String next = DateUtil.date2Str(DateUtil.addDay(DateUtil.str2Date(date, DateUtils.PATTERN_YYYYMMDD2), i), DateUtils.PATTERN_YYYYMMDD2);
			if (map.containsKey(next)) {
				String[] values = (String[]) map.get(next);
				total += Integer.parseInt(values[type]);
			}
			
			if(i == days && total > 0){
				total = ss(next, total, type);
			}
		}
		return count + total;
	}
	
	public static Date delayDate(String sDate, int limit, int type){
		if (sDate == null || "".equals(sDate)){
			LOGGER.info("delayDate param is error");
			return null;
		}
		if(type > 2 || type < 0){
			LOGGER.info("delayDate param is error");
			return DateUtil.str2Date(sDate);
		}
		
		int days = delayDays(sDate, limit, type);
		return DateUtil.addDay(DateUtil.str2Date(sDate), days + limit);
	}

	public static void main(String[] args) {
		int days = delayDays("2015-02-09", 4, 2);
		System.out.println(days);
		
		Date date = delayDate("2015-02-09", 4, 2);
		System.out.println(DateUtil.date2Str(date, DateUtils.PATTERN_YYYYMMDD2));
	}

}
