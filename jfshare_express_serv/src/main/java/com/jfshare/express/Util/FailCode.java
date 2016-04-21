
package com.jfshare.express.Util;

/**
 * Created with IntelliJ IDEA.
 * User: xujunchi
 * Date: 14-2-17
 * Time: 下午1:18
 * To change this template use File | Settings | File Templates.
 */
public class FailCode {
    public static com.jfshare.finagle.thrift.result.FailDesc expressIsEmpty;
    public static com.jfshare.finagle.thrift.result.FailDesc expressIsExist;
    public static com.jfshare.finagle.thrift.result.FailDesc nameIsEmpty;
    public static com.jfshare.finagle.thrift.result.FailDesc queryUrlIsEmpty;
    public static com.jfshare.finagle.thrift.result.FailDesc nameRuleIsEmpty;
    public static com.jfshare.finagle.thrift.result.FailDesc kuaidiKeyIsEmpty;
    public static com.jfshare.finagle.thrift.result.FailDesc typeStateIsEmpty;
    
    public static com.jfshare.finagle.thrift.result.FailDesc nameIsExist;
    public static com.jfshare.finagle.thrift.result.FailDesc kuaidiKeyExist;
    public static com.jfshare.finagle.thrift.result.FailDesc nameRuleExist;
    public static com.jfshare.finagle.thrift.result.FailDesc SYSTEM_EXCEPTION;
	public static com.jfshare.finagle.thrift.result.FailDesc userTypeIsEmpty;
	public static com.jfshare.finagle.thrift.result.FailDesc serialNumInvalid;
	public static com.jfshare.finagle.thrift.result.FailDesc subScribeFail;
    public static com.jfshare.finagle.thrift.result.FailDesc PARAMS_INVALID;

    static {
        expressIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("expressId", "1045", "快递公司ID不能为空");
        expressIsExist = new com.jfshare.finagle.thrift.result.FailDesc("expressId", "10045", "此快递公司已存在");
        nameIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("name","1065","快递公司名称不能为空");
        queryUrlIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("queryUrl","1066","物流查询地址不能为空");
        nameRuleIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("nameRule","1067","关键字不能为空");
        kuaidiKeyIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("nameRule","1068","快递100 key不能为空");
        typeStateIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("typeState","1069","请选择配送方式");
        userTypeIsEmpty = new com.jfshare.finagle.thrift.result.FailDesc("userType","1072","请选择使用方");
        serialNumInvalid = new com.jfshare.finagle.thrift.result.FailDesc("serialNum","1073","请填写正整数");
        
        nameIsExist = new com.jfshare.finagle.thrift.result.FailDesc("name","1069","快递公司名称与列表中现有数据重复！");
        kuaidiKeyExist = new com.jfshare.finagle.thrift.result.FailDesc("kuaidiKey","1070","快递100 key与列表中现有数据重复！");
        nameRuleExist = new com.jfshare.finagle.thrift.result.FailDesc("nameRule","1071","关键字与列表中现有数据重复！");
        
        SYSTEM_EXCEPTION = new com.jfshare.finagle.thrift.result.FailDesc("system", "20009", "系统异常");
        PARAMS_INVALID = new com.jfshare.finagle.thrift.result.FailDesc("params", "20010", "无效的参数");
        subScribeFail = new com.jfshare.finagle.thrift.result.FailDesc("subScribe", "3000", "订阅失败");
    }

    public static boolean isEmpty(String field) {
        if (field == null || "".equals(field.trim())) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(int field) {
        if (field == 0) {
            return false;
        }
        return true;
    }

}
