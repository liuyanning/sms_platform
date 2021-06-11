/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hero.wireless.excel;

/**
 *
 * @author Volcano
 */
public class Pagination {
	// 一页默认显示10条
	public static final int DEFAULT_PAGE_SIZE = 20;
	// 当前页
	private int pageIndex;
	// 每页显示多少条
	private int pageSize;
	// 总记录
	private int totalCount;
	// 总页数
	private int pageCount;

	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 返回第一行记录的起始位置
	 */
	public int getFirstResult() {
		return (pageIndex - 1) * pageSize;
	}

	private void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		setPageCount(totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1));
		if (totalCount == 0) {
			this.pageIndex = 1;
		} else {
			if (pageIndex > pageCount) {
				this.pageIndex = this.pageCount;
			}
		}
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		}
		this.pageSize = pageSize;
	}

	/**
	 * 判断有没有数据
	 */
	public boolean isEmpty() {
		return getTotalCount() == 0;
	}

	/**
	 * 此构造使用默认的pageSize去初始化每页的记录数
	 *
	 */
	public Pagination() {
		this.setPageIndex(1);
		this.setPageSize(DEFAULT_PAGE_SIZE);
	}

	public Pagination(int pageIndex, int pageSize) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		this.setPageIndex(pageIndex);
		this.setPageSize(pageSize);
	}

	public Pagination(int pageSize) {
		this.setPageIndex(1);
		this.setPageSize(pageSize);
	}
}
