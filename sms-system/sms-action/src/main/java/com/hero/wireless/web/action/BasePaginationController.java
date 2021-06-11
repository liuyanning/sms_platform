package com.hero.wireless.web.action;

import com.hero.wireless.web.entity.base.Pagination;


/**
 * 有分页功能的action
 * @author Volcano
 *
 */
public class BasePaginationController extends BaseController {

	
	private Pagination pagination = null;
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	

	private int pageIndex = 1;
	
	private int numPerPage=-1;
	

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
     * @return the pageIndex
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * @param pageIndex the pageIndex to set
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    
}
