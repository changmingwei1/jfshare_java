namespace java com.jfshare.finagle.thrift.result
/*接口返回结果*/

struct FailDesc {
         /*失败字段*/ 
	1:string name,   
	/*失败码*/
	2:string failCode, 
	/*失败描述*/
	3:string desc     
}

struct Result {
        /*0:失败; 1:成功*/  
	1:i32 code,   
        /*失败详情*/
	2:optional list<FailDesc> failDescList
}

/*文本型返回结果*/
struct StringResult {
	1:Result result,
	2:optional string value
}

/*布尔型返回结果*/
struct BoolResult {
	1:Result result,
	2:optional bool value
}


