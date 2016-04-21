package com.jfshare.cart.util;

/**
 * 
 * @author CUIJB
 * @date 2015年4月15日
 */
public enum CartMessageStatus {

	NORMAL("N", "正常"),

	CLOSE("C", "关闭"),

	DELETE("D", "删除");

	private String code;

	private String name;

	/**
	 * @param code
	 * @param name
	 */
	private CartMessageStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public static String getName(String code) {
		for (CartMessageStatus userGender : values()) {
			if (userGender.code.equals(code)) {
				return userGender.name;
			}
		}
		return code;
	}

	public static void main(String[] args) {
		System.out.println(NumberUtil.parseLong("234921312313.123123"));
	}
}
