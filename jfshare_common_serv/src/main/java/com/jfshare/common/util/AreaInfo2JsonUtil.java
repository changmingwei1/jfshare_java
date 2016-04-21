package com.jfshare.common.util;

import com.jfshare.finagle.thrift.common.AreaInfo;
import com.jfshare.utils.JsonMapper;

import java.util.HashMap;

/**
 * Created by lenovo on 2015/10/24.
 */
public class AreaInfo2JsonUtil {
    public static String areaInfoToJson(AreaInfo areaInfo){
        if(areaInfo != null){
            //将AddressInfo对象转换成map，再转换成json
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("id",areaInfo.getId());
            map.put("name",areaInfo.getName());
            map.put("shortName",areaInfo.getShortName());
            map.put("postCode",areaInfo.getPostCode());
            map.put("pinyin",areaInfo.getPinyin());
            map.put("initial",areaInfo.getInitial());
            return JsonMapper.toJson(map);
        }else{
            return "";
        }
    }
}
