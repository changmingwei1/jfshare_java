package com.jfshare.subject.bean.support;

public class PageBean {

    /* 总数 */
    private int total;
    /* 总页数 */
    private int pageCount;
    /* 每页数量，默认为10 */
    private int pageSize;
    /* 当前页数，默认从第一页开始 */
    private int curPage;
    /* 开始值 */
    private int start;
    /* 结束值 */
    private int end;

    public PageBean(int pageSize, int curPage) {
        //默认为每页10条记录
        this.pageSize = pageSize == 0 ? 10 : pageSize;
        //默认当前页数为1
        this.curPage = curPage == 0 ? 1 : curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return (int) Math.ceil(total / pageSize) + 1;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getStart() {
        return (this.curPage - 1) * pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.curPage * pageSize;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}
