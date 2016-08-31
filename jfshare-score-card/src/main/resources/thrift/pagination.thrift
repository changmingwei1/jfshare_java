namespace java com.jfshare.finagle.thrift.pagination

/* 页码 */
struct Pagination {

	/* 总记录数 */
	1:i32 totalCount,
	/* 总页数 */
	2:i32 pageNumCount,
	/* 每页记录数 */
	3:i32 numPerPage,
	/* 当前页数 */
	4:i32 currentPage
}