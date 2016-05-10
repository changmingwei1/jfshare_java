package com.jfshare.product.model.vo;

import java.io.Serializable;

/**
 * *************************************************************************
 *
 * @文件名称: Page.java
 * @包路径 : com.jfshare.product.model.vo
 * @版权所有: (C) 2015
 * @类描述: 分页模版
 * @创建人: ljx
 * @创建时间: 2015年10月19日 - 下午9:38:41
 * <p>
 * *************************************************************************
 */
public class Page implements Serializable {

    private static final long serialVersionUID = -6579435031214235862L;

    /* 总数 */
    private int total;
    /* 总页数 */
    private int pageCount;
    /* 每页数量，默认为10 */
    private int count;
    /* 当前页数，默认从第一页开始 */
    private int curPage;
    /* 开始值 */
    private int start;

    public Page(int curPage, int count) {
        this.count = count == 0 ? 10 : count;
        this.curPage = curPage == 0 ? 1 : curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return (int) Math.ceil(total / count) + 1;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getStart() {
        return (this.curPage - 1) * count;
    }

    public void setStart(int start) {
        this.start = start;
    }


}
//</editor-fold>
//</editor-fold>


