//package com.hero.wireless.web.action.template;
//import static com.hero.wireless.web.config.MessagesManger.getSystemMessagesInt;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.hero.web.action.BasePaginationController;
//import com.hero.web.entity.BaseEntity;
//import com.hero.web.entity.Pagination;
//import com.hero.wireless.web.config.SystemKey;
//
//
///**
// * 分页执行模板
// * @author Volcano
// *
// */
//public abstract class PaginationControllerExecuteTemplate implements
//		ControllerExecuteTemplate {
//
//	private HttpServletRequest request;
//
//	private BasePaginationController basePaginationController;
//
//	private BaseEntity baseEntity;
//
//	public PaginationControllerExecuteTemplate(IndexControllerIndexController
//			BasePaginationController basePaginationController, BaseEntity baseEntity) {
//		request = basePaginationController.request;
//		String numPerPage = request.getParameter("numPerPage");
//		String pageNum = request.getParameter("pageNum");
//		basePaginationController.setPageIndex(StringUtils.isNotBlank(pageNum) ? Integer.valueOf(pageNum) : 0);
//		basePaginationController.setNumPerPage(StringUtils.isNotBlank(numPerPage) ? Integer.valueOf(numPerPage) : basePaginationController.getNumPerPage());
//		this.basePaginationController = basePaginationController;
//		this.baseEntity = baseEntity;
//	}
//
//	public void execute() {
//		if (basePaginationController.getNumPerPage() == -1) {
//			basePaginationController.setNumPerPage(getSystemMessagesInt(SystemKey.DEFAULT_PAGE_SIZE));
//		}
//		execute(basePaginationController.getNumPerPage());
//	}
//
//	public void execute(int pageSize) {
//		basePaginationController.setPagination(new Pagination(basePaginationController
//				.getPageIndex(), pageSize));
//		baseEntity.setPagination(basePaginationController.getPagination());
//		doImpl();
//		basePaginationController.setPageIndex(basePaginationController.getPagination()
//				.getPageIndex());
//		request.setAttribute("limitCode", request.getParameter("limitCode"));
//		request.setAttribute("numPerPage", basePaginationController.getNumPerPage());
//		request.setAttribute("pagination", basePaginationController.getPagination());
//		request.setAttribute("pageIndex", basePaginationController.getPageIndex());
//		request.setAttribute("pageNum", basePaginationController.getPagination().getPageIndex());
//		request.setAttribute("baseEntity", this.baseEntity);
//	}
//
//	public BasePaginationController getBasePaginationController() {
//		return basePaginationController;
//	}
//
//	public void setBasePaginationController(BasePaginationController basePaginationController) {
//		this.basePaginationController = basePaginationController;
//	}
//
//	public BaseEntity getBaseEntity() {
//		return baseEntity;
//	}
//
//	public void setBaseEntity(BaseEntity baseEntity) {
//		this.baseEntity = baseEntity;
//	}
//}
