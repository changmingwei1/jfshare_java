package com.jfshare.express.kuaidi100.pojo;

import java.util.HashMap;

public class TaskRequest {
	private String company;
	private String number;
	private String from;
	private String to;
	private String key;

	private HashMap<String, String> parameters = new HashMap<String, String>();

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "TaskRequest{" +
				"company='" + company + '\'' +
				", number='" + number + '\'' +
				", to='" + to + '\'' +
				", from='" + from + '\'' +
				", parameters=" + parameters +
				'}';
	}
}
