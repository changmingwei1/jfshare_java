package com.jfshare.address.util;


import com.jfshare.finagle.thrift.result.FailDesc;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created with IntelliJ IDEA.
 * User: xujunchi
 * Date: 14-2-20
 * Time: 上午9:16
 * To change this template use File | Settings | File Templates.
 */
public class FailCode {
    public static FailDesc systemError;
    public static FailDesc idIsEmpty;
    public static FailDesc idIsNone;
    public static FailDesc userIdIsEmpty;
    public static FailDesc receiverNameIsEmpty;
    public static FailDesc receiverNameOverLength;
    public static FailDesc receiverMobileIsEmpty;
    public static FailDesc receiverTelIsEmpty;
    public static FailDesc telCodeIsEmpty;
    public static FailDesc receiverMobileTelIsEmpty;
    public static FailDesc receiverMobileNotLegal;
    public static FailDesc receiverAddressIsEmpty;
    public static FailDesc receiverAddressOverLength;
    public static FailDesc receiverAddressShort;
    public static FailDesc receiverAddressOverCount;
    public static FailDesc provinceIdIsEmpty;
    public static FailDesc cityIdIsEmpty;
    public static FailDesc countyIdIsEmpty;
    public static FailDesc postCodeIsEmpty;
    public static FailDesc postCodeNotLegal;
    public static FailDesc DATA_NO_EXIST;
    public static FailDesc emailOverLength;
    public static FailDesc provinceNameIsEmpty;
    public static FailDesc cityNameIsEmpty;
    public static FailDesc telCodeNotLegal;
    public static FailDesc telNotLegal;
    public static FailDesc telExtNotLegal;

    static {
    	systemError = new FailDesc("systemError", "1000", "系统异常错误");
        idIsEmpty = new FailDesc("id", "100025", "ID不能为空");
        idIsNone = new FailDesc("id", "100026", "该用户无此ID记录");
        userIdIsEmpty = new FailDesc("userId", "1025", "用户ID不能为空");
        receiverNameIsEmpty = new FailDesc("receiverName", "1047", "收货人姓名不能为空");
        receiverNameOverLength = new FailDesc("receiverName", "1048", "收货人姓名不能大于15个字符");
        receiverMobileIsEmpty = new FailDesc("receiverMobile", "1049", "收货人手机号不能为空");
        receiverTelIsEmpty = new FailDesc("receiverTel", "1059", "收货人电话不能为空");
        receiverMobileTelIsEmpty = new FailDesc("receiverTelMobile", "1060", "收货人电话和手机至少填写一个");
        telCodeIsEmpty = new FailDesc("telCode", "1061", "电话区号不能为空");
        receiverMobileNotLegal = new FailDesc("receiverMobile", "1050", "收货人手机号码不合法");
        receiverAddressIsEmpty = new FailDesc("receiverAddress", "1051", "收货地址不能为空");
        provinceIdIsEmpty = new FailDesc("provinceId", "1052", "收货人省区信息不正确");
        cityIdIsEmpty = new FailDesc("cityId", "1053", "收货人市区信息不正确");
        countyIdIsEmpty = new FailDesc("countyId", "1054", "收货人县级信息不能为空");
        postCodeIsEmpty = new FailDesc("postCode", "1062", "邮编不能为空");
        postCodeNotLegal = new FailDesc("postCode", "1063", "邮编必须是6位数字");
        receiverAddressOverLength = new FailDesc("receiverAddress", "1064", "收货地址不能超过80个字符");
        receiverAddressShort = new FailDesc("receiverAddress", "1065", "收货地址不能少于6个字符");
        receiverAddressOverCount = new FailDesc("receiverAddress", "1066", "收货地址数量不能超过20个");
        DATA_NO_EXIST = new FailDesc("data", "1067", "没有查到数据记录");
        emailOverLength = new FailDesc("email", "1068", "email长度不能超过80个字符");
        provinceNameIsEmpty = new FailDesc("provinceName", "1069", "收货人省区信息不能为空");
        cityNameIsEmpty = new FailDesc("cityName", "1070", "收货人市区信息不能为空");
        telCodeNotLegal = new FailDesc("telCode", "1071", "电话区号必须是不超过10位的数字");
        telNotLegal = new FailDesc("tel", "1072", "固定电话必须是不超过20位的数字");
        telExtNotLegal = new FailDesc("telExtNumber", "1073", "电话分机号必须是不超过10位的数字");
    }

    public static boolean isEmpty(String field) {
        if (field == null || field.trim().equals("")) {
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

	public static boolean receiverMobileNotLegal(String mobile) {
		// if (!mobile.matches("^0?1[34578]\\d{9}$")) {
		// return false;
		// }
		// 手机号码长度不等于11位
		if (mobile.trim().length() != 11) {
			return false;
		} // 手机号码第一个不是数字1
		else if (!mobile.trim().substring(0, 1).equals("1")) {
			return false;
		}
		return true;
	}

    public static boolean receiverNameOverLength(String receiverName) {
        if (receiverName.length() > 15) {
            return false;
        }
        return true;
    }

    public static boolean receiverAddrOverLength(String receiverAddr) {
        if (receiverAddr.length() > 80) {
            return false;
        }
        return true;
    }

    public static boolean emailOverLength(String email) {
        if (email.length() > 80) {
            return false;
        }
        return true;
    }

    public static boolean receiverAddrShort(String receiverAddr) {
        if (receiverAddr.length() < 6) {
            return false;
        }
        return true;
    }

    public static boolean postCodeNotLegal(String postCode) {
        if (!postCode.matches("\\d{6}")) {
            return false;
        }
        return true;
    }
    
    //校验，如果城市ID长度小于6位，认为非法
    public static boolean cityIdNotLegal(int cityId){
    	if(String.valueOf(cityId).length()<6){
    		return false;
    	}
    	return true;
    }    
    //校验，如果省的ID长度小于6位，认为非法
    public static boolean provinceIdNotLegal(int provinceId){
    	if(String.valueOf(provinceId).length()<6){
    		return false;
    	}
    	return true;
    }

    //校验，如果区号非数字、长度大于10位，认为非法
    public static boolean telCodeNotLegal(String telCode) {
        if (!NumberUtils.isDigits(telCode)) {
            return false;
        }
        if (telCode.length() > 10) {
            return false;
        }

        return true;
    }

    //校验，如果电话非数字、长度大于20位，认为非法
    public static boolean telNotLegal(String tel) {
        if (!NumberUtils.isDigits(tel)) {
            return false;
        }
        if (tel.length() > 20) {
            return false;
        }

        return true;
    }

    //校验，如果分机号非数字、长度大于10位，认为非法
    public static boolean telExtNotLegal(String telExt) {
        if (!NumberUtils.isDigits(telExt)) {
            return false;
        }
        if (telExt.length() > 10) {
            return false;
        }

        return true;
    }
}
