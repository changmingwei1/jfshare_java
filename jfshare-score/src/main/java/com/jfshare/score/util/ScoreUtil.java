package com.jfshare.score.util;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfshare.finagle.thrift.score.ScoreAccount;
import com.jfshare.finagle.thrift.score.ScoreTrade;
import com.jfshare.finagle.thrift.score.ScoreUser;
import com.jfshare.score.model.TbScoreAccountRela;
import com.jfshare.score.model.TbScoreTrade;
import com.jfshare.score.model.TbUserInfo;
import com.jfshare.utils.BeanUtil;
import com.jfshare.utils.DateUtils;

public class ScoreUtil {
	public static TbScoreTrade scoreTrade2TbScoreTrade(ScoreTrade scoreTrade){
		TbScoreTrade tbScoreTrade = new TbScoreTrade();
		Map<String, Object> stringObjectMap = BeanUtil.transBean2Map(scoreTrade);
		BeanUtil.fillBeanData(tbScoreTrade, stringObjectMap);
		return tbScoreTrade;
	}
	
	
	
	public static List<ScoreTrade> process(List<TbScoreTrade> TbScoreTrades){
		
		List<ScoreTrade> scoreTrades = new ArrayList<ScoreTrade>();
		for (TbScoreTrade tbScoreTrade : TbScoreTrades) {
			ScoreTrade scoreTrade = new ScoreTrade();
			if(tbScoreTrade.getId() > 0) {
				scoreTrade.setTradeId(String.valueOf(tbScoreTrade.getId()));
			}
			scoreTrade.setAmount(tbScoreTrade.getAmount());
			scoreTrade.setInOrOut(tbScoreTrade.getInOrOut());
			scoreTrade.setTrader(tbScoreTrade.getTrader());
			scoreTrade.setTradeTime(DateUtils.date2Str(tbScoreTrade.getTradeTime().toDate(), DateUtils.Simple_DateTime_Format));
			scoreTrade.setType(tbScoreTrade.getType());
			scoreTrade.setUserId(tbScoreTrade.getUserId());
			
			scoreTrades.add(scoreTrade);
		}
		
		return scoreTrades;
	}
	
	public static ScoreAccount process(TbScoreAccountRela tbsar){
			ScoreAccount scoreAccount = new ScoreAccount();
			scoreAccount.setUserId(tbsar.getUserId());
			scoreAccount.setAccount(tbsar.getAccount());
			scoreAccount.setCreateTime(DateUtils.date2Str(tbsar.getCreateTime().toDate(), DateUtils.Simple_DateTime_Format));
			scoreAccount.setType(tbsar.getType());
			scoreAccount.setState(tbsar.getState());
			return scoreAccount;
	}


	////DB的数据转换到thrift返回对象
	public static List<ScoreUser> processUserInfo(List<TbUserInfo> TbUserInfos){
		
		List<ScoreUser> scoreUsers = new ArrayList<ScoreUser>();
		for (TbUserInfo tbUserInfo : TbUserInfos) {
			ScoreUser scoreUser = new ScoreUser();
			scoreUser.setUserId(tbUserInfo.getUserId());
			scoreUser.setMobile(tbUserInfo.getMobile());
			scoreUser.setCreateTime(DateUtils.date2Str(tbUserInfo.getCreateTime().toDate(), DateUtils.Simple_DateTime_Format));
			scoreUser.setAmount(tbUserInfo.getAmount());
			
			scoreUsers.add(scoreUser);
		}
		
		return scoreUsers;
	}
	
	
	/**  
	   * 手机号验证   
	   * @param  str  
	   * @return 验证通过返回true  
	   */  
    public static boolean isMobile(String str) {    
    	  Pattern p = null;   
    	  Matcher m = null;   
    	  boolean b = false;    
          p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号   
          m = p.matcher(str);   
	      b = m.matches();    
	      return b;       
	}
    
    //获取当前时间截止到24:00剩余的秒数
    public static int getSeconds(){
    	Date date = new Date();   
	    SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm:ss");//24小时制   
	    String LgTime = sdformat.format(date);
		String[] my =LgTime.split(":");        
		int hour =Integer.parseInt(my[0]);        
		int min =Integer.parseInt(my[1]);        
		int sec =Integer.parseInt(my[2]);        
		int zong =hour*3600+min*60+sec;
		int daySeconds = 86400;
		return daySeconds-zong;
    }
    //获取当前时间
    public static String getTime(){
    	Date date = new Date();   
	    SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmmss");//24小时制   
	    String lgTime = sdformat.format(date);
	    return lgTime;
    }
    
    public static String getLastTime(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
        //获取前月的最后一天
	  	Calendar cal = Calendar.getInstance();    
	  	  cal.add(Calendar.YEAR,+1);
		  cal.add(Calendar.MONTH, +1);
		  cal.set(Calendar.DAY_OF_MONTH, 1);
		  cal.add(Calendar.DAY_OF_MONTH, -1); 
        String lastDay = format.format(cal.getTime());
		return lastDay;

    }
    /***
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr) throws Exception {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    //比较过期时间是否正确
    public static boolean  getCompare(String requestDate ,String expTime){
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    	Date date1 = null;
    	Date date2 = null;
		try {
			date1 = sdf1.parse(requestDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		try {
			date2 = sdf2.parse(expTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return date1.before(date2); 
    	
    }
  //验证过期时间格式
    public static boolean  getExpTimeMatches(String expTime){
    	 // 指定好一个日期格式的字符串         
    	 String pat = "\\d{4}-\\d{2}-\\d{2}" ;    
    	 // 指定好正则表达式         
    	 Pattern p = Pattern.compile(pat) ;   
    	 // 实例化Pattern类        
    	 Matcher m = p.matcher(expTime) ;    
    	 // 实例化Matcher类         
    	 return m.matches();
    	
    }
  //验证手机号类型
    public static int matchesPhoneNumber(String phone_number) { 

//    	String cm = "^((13[4-9])|(147)|(15[0-2,7-9])|(18[2-3,7-8]))\\d{8}$"; //移动
//    	String cu = "^((13[0-2])|(145)|(15[5-6])|(186))\\d{8}$"; //联通
    	String ct = "^((133)|(153)|(18[0,9]))\\d{8}$"; 

    	int flag = 0; 
    	if (phone_number.matches(ct)) { //电信
    	    flag = 1; 
    	} else{//联通或者联通
    	   flag = 2; 
    	}
    	return flag; 

    	}
}
