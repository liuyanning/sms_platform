package com.hero.wireless.json;

import com.hero.wireless.web.entity.base.IEntity;
import com.hero.wireless.web.entity.base.Pagination;

import java.util.List;
import java.util.Map;

public class LayUiObjectMapper extends FilterObjectMapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1001635641970859865L;

	public final static String CODE_SUCCESS = "0";
	public final static String CODE_SUCCESS_MSG = "成功（SUCCESS）";

	public final static String CODE_FAILD = "-1";
	public final static String CODE_FAILD_MSG = "失败（FAILD）";

	public final static String CODE_ERROR = "99";

	public final static String CODE_SESSION_OUT = "301";
	public final static String CODE_SESSION_OUT_MSG = "会话超时(SESSION_OUT)";

	public LayUiObjectMapper() {
		super();
	}

	

	public String asSuccessString(String code, String msg, Object data) {
		return super.asString(new LayUiJsonObjectFmt(code, msg, data));
	}

	public String asSuccessString(Object data) {
		return super.asString(new LayUiJsonObjectFmt(CODE_SUCCESS, CODE_SUCCESS_MSG, data));
	}

	@Deprecated
	public <T> String asSuccessString(String msg, List<T> data, int count) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, msg, data, count));
	}

	public <T> String asSuccessString(String msg, List<T> data, Pagination pagination) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, msg, data, pagination));
	}

	@Deprecated
	public <T> String asSuccessString(List<T> data, int count) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, CODE_SUCCESS_MSG, data, count));
	}

	public <T> String asSuccessString(List<T> data, Pagination pagination) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, CODE_SUCCESS_MSG, data, pagination));
	}

	@Deprecated
	public <T> String asSuccessString(List<T> data, int count, IEntity statistics) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, CODE_SUCCESS_MSG, data, count, statistics));
	}

	public <T> String asSuccessString(List<T> data, Pagination pagination, IEntity statistics) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, CODE_SUCCESS_MSG, data, pagination, statistics));
	}

	public String asFaildString(String msg, Object data) {
		return super.asString(new LayUiJsonObjectFmt(CODE_FAILD, msg, data));
	}

	public String asFaildString(Object data) {
		return super.asString(new LayUiJsonObjectFmt(CODE_FAILD, CODE_FAILD_MSG, data));
	}

	public <T> String asFaildString(String msg, List<T> data) {
		return super.asString(new LayUIJsonListFmt(CODE_FAILD, msg, data, 0));
	}

	public <T> String asFaildString(List<T> data) {
		return super.asString(new LayUIJsonListFmt(CODE_FAILD, CODE_FAILD_MSG, data, 0));
	}

	public String asString(String code, String msg, Object data) {
		return super.asString(new LayUiJsonObjectFmt(code, msg, data));
	}

	@Deprecated
	public <T> String asString(String code, String msg, List<T> data, int count) {
		return super.asString(new LayUIJsonListFmt(code, msg, data, count));
	}

	public <T> String asString(String code, String msg, List<T> data, Pagination pagination) {
		return super.asString(new LayUIJsonListFmt(code, msg, data, pagination));
	}

	@Deprecated
	public <T> String asString(String code, String msg, List<T> data, int count, IEntity statistics) {
		return super.asString(new LayUIJsonListFmt(code, msg, data, count, statistics));
	}

	public <T> String asString(String code, String msg, List<T> data, Pagination pagination, IEntity statistics) {
		return super.asString(new LayUIJsonListFmt(code, msg, data, pagination, statistics));
	}

	public static void main(String[] args) {
//		LayUiObjectMapper layUiObjectMapper = new LayUiObjectMapper();
//		layUiObjectMapper.addCodeTranslate(A.class, "01", "status");
//		AdminUserInfo uesrInfo = new A();
//		uesrInfo.setStatus("00");
//		uesrInfo.setUser_Name("<>中国");
//		uesrInfo.setCreate_Date(new Date());
//		List<AdminUserInfo> list = new ArrayList<AdminUserInfo>();
//		list.add(uesrInfo);
//		String s = layUiObjectMapper.asString(list);
//		System.out.println(s);
	}

	public <T> String asSuccessString(List<T> data, int count, Map<String, Object> map) {
		return super.asString(new LayUIJsonListFmt(CODE_SUCCESS, CODE_SUCCESS_MSG, data, count,map));
	}
}
