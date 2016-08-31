package com.jfshare.message.util;

import com.jfshare.finagle.thrift.message.SystemMessage;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.utils.StringUtil;

public class RequestCheck {
	public static void checkMessage(Result result, SystemMessage message){
		if(StringUtil.isNullOrEmpty(message)) {
			FailCode.addFails(result, FailCode.requestArgsIsNull);
			return;
		}
		if(StringUtil.isNullOrEmpty(message.getTitle())) {
			FailCode.addFails(result, FailCode.titleInvalid);
			return;
		}
		if(StringUtil.isNullOrEmpty(message.beginDate) || StringUtil.isNullOrEmpty(message.endDate)){
			FailCode.addFails(result, FailCode.dateInvalid);
			return;
		}
	}
}
