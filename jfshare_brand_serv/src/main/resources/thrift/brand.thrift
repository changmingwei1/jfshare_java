namespace java com.jfshare.finagle.thrift.brand

include "result.thrift"

struct BrandInfo {
    1:i32 id,
    2:string name,
    3:optional string imgKey,
    4:optional string url,
    5:optional i32 serial,
    6:optional string remark,
    7:optional string createTime,
    8:optional i32 createId,
    9:optional string lastUpdateTime,
    10:optional i32 lastUpdateId,
    11:optional i32 state,
}

struct QueryParam {
    1:i32 pageSize,
    2:i32 curPage,

    3:optional i32 id,
    4:optional string name,
	5:optional list<i32> hsubjectIdList
}

struct BrandResult{
	1:result.Result result,
	2:optional i32 total,        
	3:optional i32 pageCount,
	4:optional list<BrandInfo> brandInfo
}

struct BrandInfoResult{
	1:result.Result result,
	2:BrandInfo brandInfo
}

/**
* 1.对于单个brand，需要在redis进行缓存，hash结构 key: 例如 brand:1
* 2.修改和删除brand后，需要清除缓存
* 3.获取brand先在缓存中获取，如果没有，将数据库中的数据加入缓存
**/
/*品牌*/
service BrandServ {
    /* 添加品牌 */
	BrandInfoResult addBrand(1:BrandInfo brand);
	/* 修改品牌 */
	result.Result updateBrand(1:BrandInfo brand);
	/* 删除品牌 */
	result.Result deleteBrand(1:i32 id);

	/*查询所有品牌*/
    BrandResult query();
	/*分页查询*/
	BrandResult queryByPage(1:QueryParam param);
	/*批量查询*/
	BrandResult queryBatch(1:list<i32> idList);
	/*查询类目下的品牌*/
	BrandResult queryBySubject(1:i32 id)
}