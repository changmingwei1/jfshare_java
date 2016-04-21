package com.jfshare.express.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jfshare.express.model.pojo.TbExpress;
import com.jfshare.finagle.thrift.express.ExpressInfo;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.joda.time.DateTime;

public class CommUtil {
	
	/**
	 * 获取名称对应的所有前缀字符（若为中文则包含汉字和拼音和简拼前缀）
	 * @param trim
	 * @return
	 */
	public static Set<String> getAllPrefixByName(String str) {		
		Set<String> prefixs = new HashSet<String>();
		if (isNullOrEmpty(str)) return prefixs;
		str = str.trim();
		
		prefixs.addAll(getAllPrefix(str)); //本身的前缀
		
		String lowFull = cnPinYin(str, PinyinTypeEnum.LOW_FULL);
		prefixs.addAll(getAllPrefix(lowFull)); //全拼的前缀
		
		String lowPrefix = cnPinYin(str, PinyinTypeEnum.LOW_PREFIX);
        prefixs.addAll(getAllPrefix(lowPrefix)); //缩写的前缀
        
		return prefixs;
	}
	
	/**
	 * 获取字符串所有小写前缀
	 * @param lowFull
	 * @return 所有前缀集合，失败返回空集合
	 */
	private static Set<String> getAllPrefix(String lowFull) {
		Set<String> prefixs = new HashSet<String>();
		if (isNullOrEmpty(lowFull)) return prefixs;
		lowFull = lowFull.toLowerCase();
		StringBuilder curPrefix = new StringBuilder();
		for (char item : lowFull.toCharArray()) {
			curPrefix.append(item);
			prefixs.add(curPrefix.toString());
		}
		
		return prefixs;
	}


	/**
     * 获取汉字拼音
     * @param src 待转换字符串
     * @param pinyinType 转换格式
     * 1表示小写全拼，默认，2表示首字母大写全拼，3表示小写首字母
     * @return 指定格式的拼音
     */
    public static String cnPinYin(String src, PinyinTypeEnum pinyinTypeEnum) { 
    	int pinyinType = pinyinTypeEnum.getEnumVal();
        
        char[] t1 = null;     
        t1 = src.toCharArray();         
        String[] t2 = new String[t1.length];         
        // 设置汉字拼音输出的格式     
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();         
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);     
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);     
        String t4 = "";     
        int t0 = t1.length;     
        try {     
            for (int i = 0; i < t0; i++) {     
                // 判断能否为汉字字符         
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {     
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中     
                    if (pinyinType == 1) {
                    t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后     
                    }
                    else if (pinyinType == 2) {
                        t4 += t2[0].substring(0, 1).toUpperCase() + t2[0].substring(1);
                    }
                    else if (pinyinType == 3) {
                        t4 += t2[0].substring(0, 1);
                    }
                } else {     
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4后     
                    t4 += Character.toString(t1[i]).toLowerCase();     
                }     
            }     
        } catch (Exception e) {     
            e.printStackTrace();     
        }    
        
        return t4;     
    }

    public static boolean isNullOrEmpty(Object input) {
        return input == null || input.toString().equalsIgnoreCase("null") || input.toString().trim().isEmpty();
    }
    
    enum PinyinTypeEnum {
    	/**
    	 * 小写全拼
    	 */
        LOW_FULL(1),
        /**
         * 首字母大写全拼
         */
        PREFIX_UP_FULL(2),
        /**
         * 小写首字母
         */
        LOW_PREFIX(3);
        
        private int enumVal;
        private PinyinTypeEnum(int enumVal) {
            this.enumVal = enumVal;
        }
        public int getEnumVal() {return enumVal;}
    }
    

	public static ExpressInfo tbExpress2ExpressInfo(TbExpress tbExpress) {
		ExpressInfo info = new ExpressInfo();
		if (tbExpress != null) {
			info.setId(tbExpress.getId());
			info.setName(tbExpress.getName());
			info.setQueryUrl(tbExpress.getQueryUrl());
			info.setStatus(tbExpress.getStatus());
			info.setComment(tbExpress.getComment());
			if (tbExpress.getLastUpdateTime() != null)
				info.setLastUpdateTime(getDBTime(tbExpress.getLastUpdateTime().toDate()));
			info.setLastUpdateUserId(tbExpress.getLastUpdateUserId());
			if (tbExpress.getCreateTime() != null)
				info.setCreateTime(getDBTime(tbExpress.getCreateTime().toDate()));
			info.setCreateUserId(tbExpress.getCreateUserId());
			info.setKuaidiKey(tbExpress.getKuaidiKey());
			info.setNameRule(tbExpress.getNameRule());
			info.setExpressNoRule(tbExpress.getExpressNoRule());
			info.setGrabState(tbExpress.getGrabState());
			info.setTypeState(tbExpress.getTypeState());
			info.setDnfTel(tbExpress.getDnfTel());
			if (tbExpress.getUserType() != null)
			  info.setUserType(tbExpress.getUserType());
			if (tbExpress.getSerialNum() != null)
			  info.setSerialNum(tbExpress.getSerialNum());
		}
		
		return info;
	}
	
	public static TbExpress expressInfo2tbExpress(ExpressInfo info) {
		TbExpress tbExpress = new TbExpress();
		if (info != null) {
			tbExpress.setId(info.getId());
			tbExpress.setName(info.getName());
			tbExpress.setQueryUrl(info.getQueryUrl());
			tbExpress.setStatus(info.getStatus());
			tbExpress.setComment(info.getComment());
			if (info.getLastUpdateTime() != null)
				tbExpress.setLastUpdateTime(strToDate(info.getLastUpdateTime()));
			tbExpress.setLastUpdateUserId(info.getLastUpdateUserId());
			if (info.getCreateTime() != null)
				tbExpress.setCreateTime(strToDate(info.getCreateTime()));
			tbExpress.setCreateUserId(info.getCreateUserId());
			tbExpress.setKuaidiKey(info.getKuaidiKey());
			tbExpress.setNameRule(info.getNameRule());
			tbExpress.setExpressNoRule(info.getExpressNoRule());
			tbExpress.setGrabState(info.getGrabState());
			tbExpress.setTypeState(info.getTypeState());
			tbExpress.setDnfTel(info.getDnfTel());
			tbExpress.setUserType(info.getUserType());
			tbExpress.setSerialNum(info.getSerialNum());
		}
		
		return tbExpress;
	}
	
	public enum ExpressStatus {
    	/**
    	 * 启用
    	 */
        USE(1),
        /**
         * 停用
         */
        STOP(2);
        
        private int enumVal;
        private ExpressStatus(int enumVal) {
            this.enumVal = enumVal;
        }
        public int getEnumVal() {return enumVal;}
    }
	
    public static String getDBTime(Date date)
    {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();
            return timeFormat.format(new Date(cur));
        } else
            return timeFormat.format(date);
    }
    
    public static DateTime strToDate(String strTime)
    {
    	return strToDate(strTime, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static DateTime strToDate(String strTime, String format)
    {
        if(strTime == null || strTime.isEmpty())
    		return null;
    	 SimpleDateFormat timeFormat = new SimpleDateFormat(format);

    	 Date date = null;
	     try{
	        date = timeFormat.parse(strTime);
	     }catch(ParseException e){
	         System.out.println(e.getMessage());
	     }
         
         if(date != null)
        	 return new DateTime(date.getTime());
         return null;
    }
}
