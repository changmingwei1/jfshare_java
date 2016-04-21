/** 
 * Project Name:manager-serv
 * File Name:StockCommons.java 
 * Package Name:com.jfshare.manager
 * Date:2014年9月2日上午11:16:10 
 * 
*/  
  
package com.jfshare.manager.common;


/**
 * ClassName:StockCommons <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2014年9月2日 上午11:16:10 <br/> 
 * @author   sushi 
 * @version    
 * @see       
 */
public class ManagerCommons {

	/**
	 * ===================================常量定义===================================
	 */
    
 	public static final int THRIFT_RESULT_SUCC = 0;
 	public static final int THRIFT_RESULT_FAIL = 1;
 	
 	
 	public static final String RKP_SLOTIMAGE_TYPE = "SlotImage:type:";
 	public static final String RKP_SLOTIMAGE_ID = "SlotImage:id:";

    public enum OptUser{
        SELLER(1, "卖家"),
        CS(2, "客服"),
        SYS(3, "系统"),
        UNKNOWN(9, "未知")
        ;
        private int code;
        private String desc;
        OptUser(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static String getDescByCode(int code) {
            String desc = "";
            switch (code) {
                case 1:{
                    desc = OptUser.SELLER.getDesc();
                    break;
                }
                case 2:{
                    desc = OptUser.CS.getDesc();
                    break;
                }
                case 3:{
                    desc = OptUser.SYS.getDesc();
                    break;
                }
                default:{
                    desc = OptUser.UNKNOWN.getDesc();
                }
            }

            return desc;
        }
    }
}
  