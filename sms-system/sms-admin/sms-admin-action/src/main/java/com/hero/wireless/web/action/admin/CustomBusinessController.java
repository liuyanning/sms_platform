package com.hero.wireless.web.action.admin;

import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.entity.business.ext.CodeExt;
import com.hero.wireless.web.service.IBusinessManage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.annotation.Resource;

/**
 * 个性化定制
 * @author yjb
 *
 */
@Controller
@RequestMapping("/admin/")
public class CustomBusinessController extends BaseAdminController {

	@Resource(name = "businessManage")
	private IBusinessManage businessManage;

	private static String errorCode = "errorCode";
	/**
	 * 错误代码字典查询
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping("customBusiness_errorCodeList")
	@ResponseBody
	public String errorCodeList(CodeExt code) {
		code.setSort_Code(errorCode);
		List<CodeExt> list = businessManage.queryCodeList(code);
		return new LayUiObjectMapper().asSuccessString(list, code.getPagination().getTotalCount());
	}

	/**
	 * 新增错误代码
	 *
	 * @return
	 */
	@RequestMapping("customBusiness_addErrorCode")
	@ResponseBody
	@OperateAnnotation(moduleName = "定制业务管理", option = "新增错误代码")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"customBusiness_addErrorCode")
	public LayUiJsonObjectFmt addErrorCode(CodeExt code) {
		code.setSort_Code(errorCode);
		businessManage.addCode(code);
		return LayuiResultUtil.success();
	}

}
