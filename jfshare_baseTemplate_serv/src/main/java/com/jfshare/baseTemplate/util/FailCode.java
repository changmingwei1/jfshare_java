package com.jfshare.baseTemplate.util;

import com.jfshare.finagle.thrift.result.FailDesc;

/**
 * Created by Lenovo on 2016/4/29.
 */
public class FailCode {

    /******************** 系统级别错误 错误编号从10000开始 ********************/
    public static FailDesc SYSTEM_EXCEPTION = new FailDesc("systemError", "10000", "系统异常");


    /******************** 参数级别错误 错误编号从10100开始 ********************/
    public static FailDesc PARAM_ERROR = new FailDesc("paramError", "10100", "参数错误");


    /******************** 提示级别错误 错误编号从10200开始 ********************/
    public static FailDesc STOREHOUSE_NOT_EXIST = new FailDesc("storehouse", "10200", "仓库不存在");
    public static FailDesc POSTAGETEMPLATE_NOT_EXIST = new FailDesc("postageTemplate", "10201", "邮费模板不存在");
    public static FailDesc POSTAGE_NOT_EXIST = new FailDesc("postage", "10202", "邮费不存在");
    public static FailDesc POSTAGE_CALCULATE_FAIL = new FailDesc("postage", "10203", "邮费计算错误");
    public static FailDesc POSTAGE_CALCULATE_PARAM_ERROR = new FailDesc("postage", "10204", "邮费计算参数错误");

}
