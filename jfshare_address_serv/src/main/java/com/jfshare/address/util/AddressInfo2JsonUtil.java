package com.jfshare.address.util;

import java.util.HashMap;

import com.jfshare.finagle.thrift.address.AddressInfo;
import com.jfshare.utils.JsonMapper;

public class AddressInfo2JsonUtil {
	
	public static String AddressInfoToJson(AddressInfo addressInfo){
		if(addressInfo != null){
			//将AddressInfo对象转换成map，再转换成json
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("userId",addressInfo.getUserId());
			map.put("id",addressInfo.getId());
			map.put("receiverName",addressInfo.getReceiverName());
			map.put("mobile",addressInfo.getMobile());
			map.put("telCode",addressInfo.getTelCode());
			map.put("tel",addressInfo.getTel());
			map.put("telExtNumber",addressInfo.getTelExtNumber());
			map.put("provinceId",addressInfo.getProvinceId());
			map.put("provinceName",addressInfo.getProvinceName());
			map.put("cityId",addressInfo.getCityId());
			map.put("cityName",addressInfo.getCityName());
			map.put("countyId",addressInfo.getCountyId());
			map.put("countyName",addressInfo.getCountyName());			
			map.put("address",addressInfo.getAddress());
			map.put("postCode",addressInfo.getPostCode());
			map.put("isDefault",addressInfo.getIsDefault());
			map.put("email",addressInfo.getEmail());
			map.put("index",addressInfo.getIndex());
			return JsonMapper.toJson(map);
		}else{
			return "";
		}
	}
	
	private static String converJson(String name,String value){
		if(value==null) value="";
		return  "\""+name +"\":"+"\""+value+"\"";		
	}

}
