package com.jfshare.product.model.vo;

import java.io.Serializable;

/**
 * *************************************************************************
 * @文件名称: Page.java
 *
 * @包路径  : com.jfshare.product.model.vo 
 *				 
 * @版权所有: (C) 2015
 *
 * @类描述:  分页模版
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2015年10月19日 - 下午9:38:41 
 *	
 **************************************************************************
 */
public class Page implements Serializable{

	private static final long serialVersionUID = -6579435031214235862L;

	private int totalCount;				// 总记录数
	private int pageNumCount;			// 总页数
	private int numPerPage = 10;		// 每页记录数
	private int currentPage = 1;		// 当前页数
	
	public Page(String currentPage, String numPerPage) {
		if (currentPage == null) {
			this.currentPage = 1;
		} else {
			this.currentPage = Integer.parseInt(currentPage);
		}
		if (numPerPage == null) {
			this.numPerPage = 10;
		} else {
			this.numPerPage = Integer.parseInt(numPerPage);
		}
		// 计算总页数
		this.pageNumCount = (int) Math.ceil(this.totalCount / (double) this.numPerPage);
	}
	
	public Page pagination() {
		if (currentPage == 0) {
			this.currentPage = 1;
		}
		// 计算总页数
		this.pageNumCount = (int) Math.ceil(this.totalCount / (double) this.numPerPage);
		return this;
	} 

	public int getNumPerPage() {
		return numPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageNumCount() {
		return pageNumCount;
	}

	public void setPageNumCount(int pageNumCount) {
		this.pageNumCount = pageNumCount;
	}

	public int getCurrentMinRow() {
		return (currentPage-1) * pageNumCount;
	}

	public int getCurrentMaxRow() {
		return currentPage * pageNumCount - 1;
	}

}


