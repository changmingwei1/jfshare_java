package com.jfshare.card.util;

import com.jfshare.card.model.TbBatchCardsRecord;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.DateUtils;
import com.jfshare.utils.FastDFSUtils;
import com.jfshare.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScoreCardUtil {

	private static final transient Logger logger = LoggerFactory.getLogger(ScoreCardUtil.class);

	/** 针对卡密 加密的 密码 */
	public static final String PASSWORD="123456";


	private ScoreCardUtil(){}


	/**
	 * 活动名称   +    导出活动信息
	 * @param activityName
	 * @param listRR
     * @return
     */
	public static String excelExportRedbag(String activityName,List<TbBatchCardsRecord> listRR) {
		// 创建excel
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建sheet
		HSSFSheet sheet = wb.createSheet(activityName +"的卡密数据");
		// 创建一行
		HSSFRow rowTitle = sheet.createRow(0);
		// 创建标题栏样式
		HSSFCellStyle styleTitle = wb.createCellStyle();
		styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中
		HSSFFont fontTitle = wb.createFont();
		// 宋体加粗
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontTitle.setFontName("宋体");
		fontTitle.setFontHeight((short) 200);
		styleTitle.setFont(fontTitle);

		// 在行上创建1列
		HSSFCell cellTitle = rowTitle.createCell(0);

		// 列标题及样式
		cellTitle.setCellValue("卡号");
		cellTitle.setCellStyle(styleTitle);

		// 在行上创建2列
		cellTitle = rowTitle.createCell(1);
		cellTitle.setCellValue("卡密");
		cellTitle.setCellStyle(styleTitle);

		// 在行上创建3列
		cellTitle = rowTitle.createCell(2);
		cellTitle.setCellValue("发放状态");
		cellTitle.setCellStyle(styleTitle);

		// 在行上创建4列
		cellTitle = rowTitle.createCell(3);
		cellTitle.setCellValue("充值卡号");
		cellTitle.setCellStyle(styleTitle);

		// 在行上创建5列
		cellTitle = rowTitle.createCell(4);
		cellTitle.setCellValue("充值时间");
		cellTitle.setCellStyle(styleTitle);




		HSSFCellStyle styleCenter = wb.createCellStyle();
		styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中

		// 取数据
		for (int i = 0; i < listRR.size(); i++) {

			TbBatchCardsRecord item = listRR.get(i);
			HSSFRow row = sheet.createRow(i + 1);

			HSSFCell cell = row.createCell(0);
			cell.setCellValue(item.getCardName());
			cell.setCellStyle(styleCenter);

			cell = row.createCell(1);
			cell.setCellValue(item.getCardPsd());
			cell.setCellStyle(styleCenter);


			cell = row.createCell(2);
			if(item.getSendStatus().equals("0")){
				cell.setCellValue("未发放");
			}else{
				cell.setCellValue("已发放");
			}
			cell.setCellStyle(styleCenter);


			cell = row.createCell(3);// 充值账号
			if(StringUtil.isNullOrEmpty(item.getRechargeAccount())){
				cell.setCellValue("");
			}else{
				cell.setCellValue(item.getRechargeAccount());
			}
			cell.setCellStyle(styleCenter);

			cell = row.createCell(4);// 充值时间
			if (item.getRechargeTime()!=null) {
				cell.setCellValue(DateUtils.date2Str(item.getRechargeTime().toDate()));
			}else{
				cell.setCellValue("");

			}
			cell.setCellStyle(styleCenter);


		}

//		FileOutputStream fout;
		String path =  null;
		try {
			String exportName="exFile"+UUID.randomUUID().toString();
//			String ip = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_fastdfs_ip", "0");
			//解析从zk获取到的ips ：120.24.153.155:22122,120.24.153.155:22122，使用第一个
			String ipStr = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "1.1", "0");
			String[] ipArr=ipStr.split(",");
			String addres=ipArr[0];
			String ip=addres.split(":")[0];
			String port=addres.split(":")[1];
//			String port = PropertiesUtil.getProperty(Constants.ZK_POSTURL, "def_fastdfs_port", "0");

			ByteArrayOutputStream fout = new ByteArrayOutputStream();
			wb.write(fout);
			fout.close();
			wb.close();

			FastDFSUtils fastDFSUtils = new FastDFSUtils(ip, Integer.parseInt(port));
			String fileExtName ="xls";
			path = fastDFSUtils.uploadFile(fout.toByteArray(),exportName,fileExtName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(String.format("$$$$上传完成--fastdfs路径为："+path));
		return path;
	}



	/**  日期转化*/
	public static String date2Str(Date date){
		return DateUtils.date2Str(date, DateUtils.Simple_DateTime_Format);
	}
	/**  日期转化*/
	public static String dateTime2Str(DateTime dateTime){
		return DateUtils.date2Str(dateTime.toDate(), DateUtils.Simple_DateTime_Format);
	}


	/**
	 * 日期比较工具类
	 * @param DATE1
	 * @param DATE2
     * @return  1 为 date1 >date2
     */
	public static int compareDate(String DATE1, String DATE2) {


		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}


	/**
	 *  生成邀请码的卡密
	 * @param count  生成数量
	 * @return
     */
	public static  Set<String> genCardPsd(int count){
		Set<String> retSet=new HashSet<String>();
		for (int i = 0; i  < count; i++) {
			String value="";
			// 这里生成8位  -- 密文都成了我们所熟知的字母和数字，但是长度变为了原字符长度的2倍
			//string.format("%04d", 12); 结果:"0012"
			value=RC4.encry_RC4_string(String.format("%04d",i), UUID.randomUUID().toString());
//			System.out.println(value);
			retSet.add(encrytCardPsd(value));// 存放数据库 的卡密是加密过的
		}
		System.out.println("size  == "+retSet.size());

		return retSet;
	}
	/**
	 *  生成邀请码的卡号的后四位
	 * @param count  生成数量
	 * @return
     */
	public static  Set<String> genCardNumLast4Code(int count){
		return UniqueRandom.genCardNumLast4(count);
	}


	/**
	 * 机密卡密
	 * @param inputStr
	 * @return
     */
	public  static String  encrytCardPsd(String inputStr){
		return RC4.encry_RC4_string(inputStr, PASSWORD);
	}


	/**
	 * 机密卡密
	 * @param encryStr
	 * @return
     */
	public static String decryptPsd(String encryStr){

		return RC4.decry_RC4(encryStr, PASSWORD);
	}


	/**
	 * 生成卡片的ID
	 * 卡号：jfx+批次ID+4位序列号；
	 * @param activityId
	 * @return
     */
	public  static  String genCardId(int activityId,String last4Code){
		StringBuffer sb= new StringBuffer();
		sb.append("jfx").append(activityId);
		sb.append(last4Code);
//		Random random=new Random();
//		for(int i=0;i<4;i++){// 4位随机数字
//			int temp =random.nextInt(9);
//			sb.append(temp);
//		}

		return sb.toString();
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
    
    /**
	 * 比较传入日期与当前日期大小
	 * 0:无效 ; 1:可使用 ; 2:已过期
	 * @return
	 */
	 public static int compare_date(DateTime startDate, DateTime endDate) {
	        
        Date nowDate=new Date();
        
        Date startDateObj=startDate.toDate();
        Date endDateObj=endDate.toDate();
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	Date dtNow=df.parse(df.format(nowDate));
            Date dtStart = df.parse(df.format(startDateObj));
            Date dtEnd = df.parse(df.format(endDateObj));
            if (dtNow.getTime() <= dtStart.getTime()) {
                return 0;
            } else if(dtNow.getTime() >= dtStart.getTime()&&dtNow.getTime() <= dtEnd.getTime()){
            	return 1;
            } else{
                return 2;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
