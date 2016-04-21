package com.jfshare.cart.model;

public class SkuInfo {

	/*
	 * "vColor": "#ffffb1", "pId": "177", "pName": "颜色", "vName": "浅黄色",
	 * "vPicture": "", "vDefault": "", "vId": "1007"
	 */

	private String vColor;
	private String pId;
	private String pName;
	private String vName;
	private String vPicture;
	private String vDefault;
	private String vId;

	public String getvColor() {
		return vColor;
	}

	public void setvColor(String vColor) {
		this.vColor = vColor;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvPicture() {
		return vPicture;
	}

	public void setvPicture(String vPicture) {
		this.vPicture = vPicture;
	}

	public String getvDefault() {
		return vDefault;
	}

	public void setvDefault(String vDefault) {
		this.vDefault = vDefault;
	}

	public String getvId() {
		return vId;
	}

	public void setvId(String vId) {
		this.vId = vId;
	}
}
