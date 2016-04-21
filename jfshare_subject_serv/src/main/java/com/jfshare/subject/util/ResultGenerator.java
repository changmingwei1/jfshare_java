package com.jfshare.subject.util;

import com.jfshare.finagle.thrift.result.FailDesc;
import com.jfshare.finagle.thrift.result.Result;
import com.jfshare.finagle.thrift.result.StringResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultGenerator {

	private ResultGenerator(){}
	
	public static final FailDesc SUJECT_SYSTEM_ERROR = new FailDesc("Subject", "800000", "系统错误");
	public static final FailDesc SUJECT_PARAM_ERROR = new FailDesc("Subject", "800001", "接口参数不完整");
	public static final FailDesc SUJECT_EXCEPTION_ERROR = new FailDesc("Subject", "800002", "调用异常");
	public static final FailDesc SUJECT_NOT_EXIST = new FailDesc("Subject", "800003", "科目不存在");
	public static final FailDesc SUJECT_PARENT_NOT_LEAF = new FailDesc("Subject", "800004", "父级类目不能选择叶子类目");
	public static final FailDesc SUJECT_NAME_NOT_NULL = new FailDesc("Subject", "800005", "类目名称不能为空");
	public static final FailDesc SUJECT_SORT_NUM = new FailDesc("Subject", "800006", "请输入大于等于0的整数，按数字从小到大排序");


    public static final FailDesc DISPLAY_SUJECT_NOT_EXIST = new FailDesc("DisplaySubject", "900001", "参数中类目渠道错误");
    public static final FailDesc DISPLAY_SUJECT_NOT_LEAF = new FailDesc("DisplaySubject", "900002", "前台类目不是叶子节点");

    public static final FailDesc DISPLAY_CHANNEL_IS_EXIST = new FailDesc("DisplayChannel", "700001", "渠道Key不能重复");
    public static final FailDesc DISPLAY_CHANNEL_ADD_FAIL = new FailDesc("DisplayChannel", "700002", "渠道添加失败");




    /**
	 * 错误描述map，招商平台报名指令，入库活动记录，通知产品服务，是一个事物
	 * 为准确返回错误描述，将部分错误描述对象放入map中，以便查找
	 */
	public static Map<String,FailDesc> failDescMap = new HashMap<String,FailDesc>();
	
	/**
	 * 
	 *  Function:
	 *  功能说明：根据错误描述对象获得错误描述集合
	 *	 使用说明：将错误描述放入的错误描述集合中
	 *  @author  liujinxin  DateTime 2014年3月26日 下午2:42:31
	 *	 返回类型: List<FailDesc>    
	 *  @param failDesc			错误描述对象
	 *  @param failDescList		错误描述集合
	 *  @return
	 */
    public static List<FailDesc> getFailDescList(FailDesc failDesc, List<FailDesc> failDescList){
    	//当错误描述集合为null时，创建一个错误描述集合
        if(failDescList == null)  {
             failDescList = new  ArrayList<FailDesc>();
        }
        failDescList.add(failDesc);
        return failDescList;
    }
    
	/**
	 * 
	 *  Function:
	 *  功能说明：生成code为1的错误结果
	 *	 使用说明：thrift接口正确执行，返回带有失败描述的错误结果
	 *  @author  liujinxin  DateTime 2014年3月26日 下午2:38:43
	 *	 返回类型: Result    
	 *  @param failDesc		失败描述
	 *  @return
	 */
	public static Result createErrorResult(FailDesc failDesc){
		Result result = new Result();
		result.setCode(1);
		result.setFailDescList(getFailDescList(failDesc, null));
		return result;
	}
	
	/**
	 * 
	 *  Function:
	 *  功能说明：生成code为1的错误结果
	 *	 使用说明：thrift接口正确执行，返回带有失败描述的错误结果,将错误描述放入已经存在的错误集合中
	 *  @author  liujinxin  DateTime 2014年3月26日 下午2:41:15
	 *	 返回类型: Result    
	 *  @param failDesc			错误描述对象
	 *  @param failDescList		错误描述集合
	 *  @return
	 */
	public static Result createErrorResult(FailDesc failDesc, List<FailDesc> failDescList){
		Result result = new Result();
		result.setCode(1);
		result.setFailDescList(getFailDescList(failDesc, failDescList));
		return result;
	}

	/**
	 * 
	 *  Function:
	 *  功能说明：生成code为0的正确结果
	 *	 使用说明：thrift接口正确执行，返回正确的结果
	 *  @author  liujinxin  DateTime 2014年3月26日 下午2:38:08
	 *	 返回类型: Result    
	 *  @return
	 */
	public static Result createCorrectResult(){
		Result result = new Result();
		result.setCode(0);
		return result;
	}

	/**
	 *  Function:StringResult生成器
	 *  功能说明：
	 *	 使用说明：
	 *  @author  liujinxin  DateTime 2014年5月8日 下午9:14:24
	 *	 返回类型: StringResult    
	 *  @param value
	 *  @param result
	 *  @return
	 */
	public static StringResult createStringResult(String value, Result result){
		StringResult stringResult = new StringResult();
		stringResult.setValue(value);
		stringResult.setResult(result);
		return stringResult;
	}

}
