package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ChannelStatus;
import com.hero.wireless.enums.ProductChannelDiversionType;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Product;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.business.ProductChannelsDiversion;
import com.hero.wireless.web.entity.business.ext.ProductExt;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.IProductChannelManage;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/product/")
public class ChannelProductController extends BaseAdminController {

	@Resource(name = "productChannelManage")
	private IProductChannelManage productChannelManage;


	/**
	 * 产品列表查询
	 *
	 * @param productExt
	 * @return
	 */
	@RequestMapping("productList")
	@ResponseBody
	public String productList(ProductExt productExt) {
		List<Product> productLists = productChannelManage.queryProductList(productExt);
		return new LayUiObjectMapper().asSuccessString(productLists, productExt.getPagination().getTotalCount());
	}

	/**
	 * 添加产品
	 *
	 */
	@RequestMapping("addProduct")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "新增产品")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:addProduct")
	public LayUiJsonObjectFmt addProduct(Product product) {
		try {
			this.productChannelManage.addProduct(product);
		} catch (BaseException e) {
			return LayuiResultUtil.error(e);
		}
		return LayuiResultUtil.success();
	}

	/**
	 * 删除产品
	 *
	 * @param ckIds
	 * @return
	 */
	@RequestMapping("delProduct")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "删除产品")
	public LayUiJsonObjectFmt delProduct(@RequestParam(value = "ckIds") List<Integer> ckIds) {
		this.productChannelManage.deleteProductByIdList(ckIds);
		return LayuiResultUtil.success();
	}

	@RequestMapping("startProduct")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "启动产品")
	public LayUiJsonObjectFmt updateStatusStartProduct(@RequestParam(value = "ckIds") List<Integer> ckIds) {
		this.productChannelManage.updateStatusProduct(ckIds, Constant.STATUS_CODE_START);
		return LayuiResultUtil.success();
	}

	@RequestMapping("stopProduct")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "停用产品")
	public LayUiJsonObjectFmt updateStatusStopProduct(@RequestParam(value = "ckIds") List<Integer> ckIds) {
		this.productChannelManage.updateStatusProduct(ckIds, Constant.STATUS_CODE_STOP);
		return LayuiResultUtil.success();
	}

	/**
	 * 产品修改前置
	 *
	 * @param entity
	 * @return
	 */
	@RequestMapping("preProductEdit")
	public String preRoleEdit(BaseParamEntity entity) {
		request.setAttribute("product", productChannelManage.queryProductById(entity.getCkIds().get(0)));
		return "/product/product_edit";
	}

	/**
	 * 产品修改
	 *
	 * @param product
	 * @return
	 */
	@RequestMapping("editProduct")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "修改产品")
	public LayUiJsonObjectFmt editProduct(Product product) {
		try {
			this.productChannelManage.editProduct(product);
		} catch (BaseException e) {
			return LayuiResultUtil.error(e);
		}
		return LayuiResultUtil.success();
	}



	/**
	 * 产品管理》》》产品通道页面
	 *
	 */
	@RequestMapping("productChannelsPage")
	public String productChannelsPage(String limitCode, String channelTypeCode, String ckNos) {
		request.setAttribute("limitCode",limitCode);
		request.setAttribute("channelTypeCode",channelTypeCode);
		request.setAttribute("product_No",ckNos);
		return "/product/product_channels_list";
	}
	/**
	 * 产品管理》》》加载产品通道列表数据
	 *
	 */
	@RequestMapping("productChannelsList")
	@ResponseBody
	public String productChannelsList(ProductChannels productChannels) {
		List<ProductChannels> productChannelsList = productChannelManage.queryProductChannelsList(productChannels);
		return new SmsUIObjectMapper().asSuccessString(productChannelsList,
				productChannels.getPagination());
	}

	/**
	 * 添加产品通道前置
	 *
	 */
	@RequestMapping("preAddProductChannels")
	public ModelAndView perAddProductChannels(String channelTypeCode) {
		ModelAndView mv = new ModelAndView("/product/product_channels_add");
		mv.addObject("channelTypeCode", channelTypeCode);
		return mv;
	}
	/**
	 * 添加产品通道
	 *
	 */
	@RequestMapping("addProductChannels")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "新增产品的通道")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:addProductChannels")
	public LayUiJsonObjectFmt addProductChannels(ProductChannels ProductChannels) {
		try {
			this.productChannelManage.addProductChannels(ProductChannels);
		} catch (BaseException e) {
			return LayuiResultUtil.error(e);
		}
		return LayuiResultUtil.success();
	}

	/**
	 * 产品通道修改前置
	 *
	 */
	@RequestMapping("preEditProductChannels")
	public ModelAndView preEditProductChannels(BaseParamEntity entity) {
		ModelAndView mv = new ModelAndView("/product/product_channels_edit");
		ProductChannels productChannels = productChannelManage.queryProductChannelsById(entity.getCkIds().get(0));
        mv.addObject("productChannels", productChannels);
		return mv;
	}
	/**
	 * 产品通道修改
	 *
	 */
	@RequestMapping("editProductChannels")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "通道产品修改")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:editProductChannels")
	public LayUiJsonObjectFmt editProductChannels(ProductChannels productChannels) {
		try{
			this.productChannelManage.editProductChannels(productChannels);
		} catch (ServiceException se) {
			se.printStackTrace();
			return LayuiResultUtil.fail(se.getMessage());
		}
		return LayuiResultUtil.success();
	}
	/**
	 * 删除产品通道
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("delProductChannels")
	@ResponseBody
	@OperateAnnotation(moduleName = "通道产品", option = "删除产品通道")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:delProductChannels")
	public LayUiJsonObjectFmt delProductChannels(@RequestParam(value = "id") Integer id) {
		this.productChannelManage.deleteProductChannelsById(id);
		return LayuiResultUtil.success();
	}
	/**
	 * 导流策略包含关键字前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionKeyWord")
	public ModelAndView preProductChannelsDiversionKeyWord(String limitCode, String ckIds) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("limitCode",limitCode);
		mv.addObject("productChannelsId",ckIds);
		mv.setViewName("/product/diversion/include_keyword_list");
		return mv;
	}
	/**
	 * 导流策略包含关键字列表
	 *
	 */
	@RequestMapping("productChannelsDiversionKeyWordList")
	@ResponseBody
	public String productChannelsDiversionKeyWordList(ProductChannelsDiversion diversion) {
		diversion.setStrategy_Type_Code(ProductChannelDiversionType.INCLUDE_KEYWORD.toString());
		// 产品策略id不能为空
		if(diversion.getProduct_Channels_Id() == null || diversion.getProduct_Channels_Id() ==0 ) {
			return null;
		}
		List<ProductChannelsDiversion> list = this.productChannelManage.queryProductChannelsDiversionList(diversion);
		return new SmsUIObjectMapper().asSuccessString(list, diversion.getPagination());
	}
	/**
	 * 导流策略包含关键字新增保存
	 *
	 */
	@RequestMapping("addProductChannelsDiversionKeyWord")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略关键字新增")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:addProductChannelsDiversionKeyWord")
	public LayUiJsonObjectFmt addProductChannelsDiversionKeyWord(ProductChannelsDiversion diversion) {
		diversion.setStrategy_Type_Code(ProductChannelDiversionType.INCLUDE_KEYWORD.toString());
		this.productChannelManage.addProductChannelsDiversion(diversion);
		return LayuiResultUtil.success();
	}
	/**
	 * 导流策略包含关键字修改前置
	 *
	 */
	@RequestMapping("perEditProductChannelsDiversionKeyWord")
	public ModelAndView perEditProductChannelsDiversionKeyWord(Integer ckIds) {
		ModelAndView mv = new ModelAndView("/product/diversion/include_keyword_edit");
		mv.addObject("diversion",productChannelManage.queryProductChannelsDiversionById(ckIds));
		return mv;
	}
	/**
	 * 导流策略包含关键字修改
	 *
	 */
	@RequestMapping("updateProductChannelsDiversionKeyWord")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略关键字修改")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:updateProductChannelsDiversionKeyWord")
	public LayUiJsonObjectFmt updateProductChannelsDiversionKeyWord(ProductChannelsDiversion diversion) {
		this.productChannelManage.updateProductChannelsDiversion(diversion);
		return LayuiResultUtil.success();
	}

	//导流策略导入包含关键字
	@RequestMapping("importProductChannelsKeyword")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略导入关键字")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:importProductChannelsKeywords")
	public LayUiJsonObjectFmt importProductChannelsKeyword(@RequestParam(value = "importFile") MultipartFile importFile,
			ProductChannelsDiversion productChannelsDiversion) {
		try{
			String fileTyle = importFile.getOriginalFilename().substring(importFile.getOriginalFilename().lastIndexOf("."));
			if (!fileTyle.equalsIgnoreCase(".txt")){
				throw new BaseException("文件格式不正确!");
			}
			productChannelsDiversion.setName("包含关键字");
			this.productChannelManage.importProductChannelsDiversion(importFile, productChannelsDiversion, ProductChannelDiversionType.INCLUDE_KEYWORD.toString());
		}catch (ServiceException se){
			return LayuiResultUtil.fail(se.getMessage());
		}
		return LayuiResultUtil.success();
	}

	//导流策略导出包含关键字
	@RequestMapping("exportProductChannelsKeyword")
	@ResponseBody
	public LayUiJsonObjectFmt exportProductChannelsKeyword(Integer productChannelsId) {
		try {
			productChannelManage.exportProductChannelsDiversion(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(), productChannelsId,
					getAdminDefaultExportFile(),ProductChannelDiversionType.INCLUDE_KEYWORD.toString());
			return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
		}catch (ServiceException e){
			return LayuiResultUtil.fail(e.getMessage());
		}catch (Exception e){
			SuperLogger.error(e.getMessage(),e);
			return LayuiResultUtil.error(e);
		}
	}

	/**
	 * 导流策略删除
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("delProductChannelsDiversion")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略删除")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:delProductChannelsDiversion")
	public LayUiJsonObjectFmt delProductChannelsDiversion(BaseParamEntity entity) {
		if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
			throw new ServiceException("至少选择一条数据");
		}
		productChannelManage.delProductChannelsDiversion(entity.getCkIds(),null);
		return LayuiResultUtil.success();
	}

	/**
	 * 导流策略签名前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionSignature")
	public ModelAndView preProductChannelsDiversionSignature(String limitCode, String ckIds) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("limitCode",limitCode);
		mv.addObject("productChannelsId",ckIds);
		mv.setViewName("/product/diversion/include_signature_list");
		return mv;
	}
	/**
	 * 导流策略签名列表
	 *
	 */
	@RequestMapping("productChannelsDiversionSignatureList")
	@ResponseBody
	public String productChannelsDiversionSignatureList(ProductChannelsDiversion diversion) {
		diversion.setStrategy_Type_Code(ProductChannelDiversionType.SIGNATURE.toString());
		if (StringUtils.isNotEmpty(diversion.getStrategy_Rule())){
			diversion.setStrategy_Rule("%"+ diversion.getStrategy_Rule()+ "%");
		}
		List<ProductChannelsDiversion> list = this.productChannelManage.queryProductChannelsDiversionList(diversion);
		return new SmsUIObjectMapper().asSuccessString(list, diversion.getPagination());
	}
	/**
	 * 导流策略签名保存
	 *
	 */
	@RequestMapping("addProductChannelsDiversionSignature")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略签名新增")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:addProductChannelsDiversionSignature")
	public LayUiJsonObjectFmt addProductChannelsDiversionSignature(ProductChannelsDiversion diversion) {
		diversion.setStrategy_Type_Code(ProductChannelDiversionType.SIGNATURE.toString());
		this.productChannelManage.addProductChannelsDiversion(diversion);
		return LayuiResultUtil.success();
	}
	/**
	 * 导流策略签名修改前置
	 *
	 */
	@RequestMapping("perEditProductChannelsDiversionSignature")
	public ModelAndView perEditProductChannelsDiversion(Integer ckIds) {
		ModelAndView mv = new ModelAndView("/product/diversion/include_signature_edit");
		mv.addObject("diversion",productChannelManage.queryProductChannelsDiversionById(ckIds));
		return mv;
	}
	/**
	 * 导流策略签名修改
	 *
	 */
	@RequestMapping("updateProductChannelsDiversionSignature")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略签名修改")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:addProductChannelsDiversionSignature")
	public LayUiJsonObjectFmt updateProductChannelsDiversionSignature(ProductChannelsDiversion diversion) {
		this.productChannelManage.updateProductChannelsDiversion(diversion);
		return LayuiResultUtil.success();
	}

	//导流策略导入签名
	@RequestMapping("importProductChannelsSignature")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略导入签名")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:importProductChannelsSignature")
	public LayUiJsonObjectFmt importProductChannelsSignature(@RequestParam(value = "importFile") MultipartFile importFile,
															 ProductChannelsDiversion productChannelsDiversion) {
		try{
			String fileTyle = importFile.getOriginalFilename().substring(importFile.getOriginalFilename().lastIndexOf("."));
			if (!fileTyle.equalsIgnoreCase(".txt")){
				throw new BaseException("文件格式不正确!");
			}
			productChannelsDiversion.setName(ProductChannelDiversionType.SIGNATURE.getName());
			this.productChannelManage.importProductChannelsDiversion(importFile, productChannelsDiversion,
					ProductChannelDiversionType.SIGNATURE.toString());
		}catch (ServiceException se){
			return LayuiResultUtil.fail(se.getMessage());
		}
		return LayuiResultUtil.success();
	}

	//导流策略导出签名
	@RequestMapping("exportProductChannelsSignature")
	@ResponseBody
	public LayUiJsonObjectFmt exportProductChannelsSignature(Integer productChannelsId) {
		try {
			productChannelManage.exportProductChannelsDiversion(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(), productChannelsId,
					getAdminDefaultExportFile(), ProductChannelDiversionType.SIGNATURE.toString());
			return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
		}catch (ServiceException e){
			return LayuiResultUtil.fail(e.getMessage());
		}catch (Exception e){
			SuperLogger.error(e.getMessage(),e);
			return LayuiResultUtil.error(e);
		}
	}

	/**
	 * 导流策略排除关键字前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionExcludeKeyWord")
	public ModelAndView preProductChannelsDiversionExcludeKeyWord(String limitCode,  String ckIds) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("limitCode",limitCode);
		mv.addObject("productChannelsId",ckIds);
		mv.setViewName("/product/diversion/exclude_keyword_list");
		return mv;
	}
	/**
	 * 导流策略排除关键字列表
	 *
	 */
	@RequestMapping("productChannelsDiversionExcludeKeyWordList")
	@ResponseBody
	public String productChannelsDiversionExcludeKeyWordList(ProductChannelsDiversion diversion) {
		diversion.setStrategy_Type_Code(ProductChannelDiversionType.EXCLUDE_KEYWORD.toString());
		// 产品策略id不能为空
		if(diversion.getProduct_Channels_Id() == null || diversion.getProduct_Channels_Id() ==0 ) {
			return null;
		}
		List<ProductChannelsDiversion> list = this.productChannelManage.queryProductChannelsDiversionList(diversion);
		return new SmsUIObjectMapper().asSuccessString(list, diversion.getPagination());
	}
	/**
	 * 导流策略排除关键字新增保存
	 *
	 */
	@RequestMapping("addProductChannelsDiversionExcludeKeyWord")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略排除关键字新增")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:addProductChannelsDiversionExcludeKeyWord")
	public LayUiJsonObjectFmt addProductChannelsDiversionExcludeKeyWord(ProductChannelsDiversion diversion) {
		diversion.setStrategy_Type_Code(ProductChannelDiversionType.EXCLUDE_KEYWORD.toString());
		this.productChannelManage.addProductChannelsDiversion(diversion);
		return LayuiResultUtil.success();
	}
	/**
	 * 导流策略排除关键字修改前置
	 *
	 */
	@RequestMapping("perEditProductChannelsDiversionExcludeKeyWord")
	public ModelAndView perEditProductChannelsDiversionExcludeKeyWord(Integer ckIds) {
		ModelAndView mv = new ModelAndView("/product/diversion/exclude_keyword_edit");
		mv.addObject("diversion",productChannelManage.queryProductChannelsDiversionById(ckIds));
		return mv;
	}
	/**
	 * 导流策略排除关键字修改
	 *
	 */
	@RequestMapping("updateProductChannelsDiversionExcludeKeyWord")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略排除关键字修改")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:updateProductChannelsDiversionExcludeKeyWord")
	public LayUiJsonObjectFmt updateProductChannelsDiversionExcludeKeyWord(ProductChannelsDiversion diversion) {
		this.productChannelManage.updateProductChannelsDiversion(diversion);
		return LayuiResultUtil.success();
	}

	//导流策略导入排除关键字
	@RequestMapping("importProductChannelsExcludeKeyword")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略导入排除关键字")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:importProductChannelsExcludeKeywords")
	public LayUiJsonObjectFmt importProductChannelsExcludeKeyword(@RequestParam(value = "importFile") MultipartFile importFile,
																  ProductChannelsDiversion productChannelsDiversion) {
		try{
			String fileTyle = importFile.getOriginalFilename().substring(importFile.getOriginalFilename().lastIndexOf("."));
			if (!fileTyle.equalsIgnoreCase(".txt")){
				throw new BaseException("文件格式不正确!");
			}
			productChannelsDiversion.setName(ProductChannelDiversionType.EXCLUDE_KEYWORD.getName());
			this.productChannelManage.importProductChannelsDiversion(importFile, productChannelsDiversion,
					ProductChannelDiversionType.EXCLUDE_KEYWORD.toString());
		}catch (ServiceException se){
			return LayuiResultUtil.fail(se.getMessage());
		}
		return LayuiResultUtil.success();
	}

	//导流策略导出排除关键字
	@RequestMapping("exportProductChannelsExcludeKeyword")
	@ResponseBody
	public LayUiJsonObjectFmt exportProductChannelsExcludeKeyword(Integer productChannelsId) {
		try {
			productChannelManage.exportProductChannelsDiversion(DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(), productChannelsId,
					getAdminDefaultExportFile(),ProductChannelDiversionType.EXCLUDE_KEYWORD.toString());
			return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
		}catch (ServiceException e){
			return LayuiResultUtil.fail(e.getMessage());
		}catch (Exception e){
			SuperLogger.error(e.getMessage(),e);
			return LayuiResultUtil.error(e);
		}
	}
	/**
	 * 导流策略短信长度前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionLengthLimit")
	public ModelAndView preProductChannelsDiversionLengthLimit(BaseParamEntity entity) {
		if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
			throw new ServiceException("请选择一条数据");
		}
		ModelAndView mv = new ModelAndView("/product/diversion/length_limit_edit");
		mv.addObject("diversion",
				this.productChannelManage.queryTypeDiversionByProductChannelId(entity.getCkIds().get(0),
						ProductChannelDiversionType.SMS_LENGTH_LIMIT.toString()));
		mv.addObject("productChannelsId", entity.getCkIds().get(0));
		return mv;
	}

	/**
	 * 导流策略短信长度保存
	 *
	 */
	@RequestMapping("editProductChannelsDiversionLengthLimit")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略短信长度保存")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:editProductChannelsDiversionLengthLimit")
	public LayUiJsonObjectFmt editProductChannelsDiversionLengthLimit(ProductChannelsDiversion diversion) {
		try{
			diversion.setStrategy_Type_Code(ProductChannelDiversionType.SMS_LENGTH_LIMIT.toString());
			diversion.setName(ProductChannelDiversionType.SMS_LENGTH_LIMIT.getName());
			this.productChannelManage.updateOrInsertDiversion(diversion);
		}catch (Exception e){
			SuperLogger.error(e);
			return LayuiResultUtil.fail("操作异常");
		}
		return LayuiResultUtil.success("操作成功");
	}
	/**
	 * 导流策略频率限制前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionIntervalLimit")
	public ModelAndView preProductChannelsDiversionIntervalLimit(BaseParamEntity entity) {
		if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
			throw new ServiceException("请选择一条数据");
		}
		ModelAndView mv = new ModelAndView("/product/diversion/interval_limit_edit");
		mv.addObject("limitCode", DatabaseCache.getCodeListBySortCode("limit_repeat"));
		mv.addObject("diversion",
				this.productChannelManage.queryTypeDiversionByProductChannelId(entity.getCkIds().get(0),
						ProductChannelDiversionType.INTERVAL_LIMIT.toString()));
		mv.addObject("productChannelsId", entity.getCkIds().get(0));
		return mv;
	}
	/**
	 * 导流策略频率限制保存
	 *
	 */
	@RequestMapping("editProductChannelsDiversionIntervalLimit")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略手机号限制保存")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:editProductChannelsDiversionIntervalLimit")
	public LayUiJsonObjectFmt editProductChannelsDiversionIntervalLimit(ProductChannelsDiversion diversion) {
		try{
			diversion.setStrategy_Type_Code(ProductChannelDiversionType.INTERVAL_LIMIT.toString());
			diversion.setName(ProductChannelDiversionType.INTERVAL_LIMIT.getName());
			diversion.setStrategy_Rule(StringEscapeUtils.unescapeJavaScript(diversion.getStrategy_Rule()));
			this.productChannelManage.updateOrInsertDiversion(diversion);
		}catch (Exception e){
			SuperLogger.error(e);
			return LayuiResultUtil.fail("操作异常");
		}
		return LayuiResultUtil.success("操作成功");
	}
	/**
	 * 导流策略区域限制前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionAreaLimit")
	public ModelAndView preProductChannelsDiversionAreaLimit(BaseParamEntity entity) {
		if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
			throw new ServiceException("请选择一条数据");
		}
		ModelAndView mv = new ModelAndView("/product/diversion/area_limit_edit");
		mv.addObject("locationCode", DatabaseCache.getCodeListBySortCode("location"));
		mv.addObject("diversion",
				this.productChannelManage.queryTypeDiversionByProductChannelId(entity.getCkIds().get(0),
						ProductChannelDiversionType.AREAS.toString()));
		mv.addObject("productChannelsId", entity.getCkIds().get(0));
		return mv;
	}
	/**
	 * 导流策略区域限制保存
	 *
	 */
	@RequestMapping("editProductChannelsDiversionAreaLimit")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略区域限制保存")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:editProductChannelsDiversionAreaLimit")
	public LayUiJsonObjectFmt editProductChannelsDiversionAreaLimit(ProductChannelsDiversion diversion) {
		try{
			diversion.setStrategy_Type_Code(ProductChannelDiversionType.AREAS.toString());
			diversion.setName(ProductChannelDiversionType.AREAS.getName());
			this.productChannelManage.updateOrInsertDiversion(diversion);
		}catch (Exception e){
			SuperLogger.error(e);
			return LayuiResultUtil.fail("操作异常");
		}
		return LayuiResultUtil.success("操作成功");
	}

	/**
	 * 导流策略号码池列表
	 * 
	 * @param limitCode
	 * @param ckIds
	 * @return
	 */
	@RequestMapping("diversionPhoneNoPoolPage")
	public String diversionPhoneNoPoolPage(String limitCode, String ckIds) {
		if (StringUtils.isBlank(ckIds)) {
			throw new ServiceException("请选择一条数据");
		}
		request.setAttribute("limitCode, ", limitCode);
		request.setAttribute("productChannelsId", ckIds);
		return "/product/diversion/phoneno_pool_list";
	}

	/**
	 * 导流策略号码池列表加载数据
	 * 
	 * @param productChannelsDiversion
	 * @return
	 */
	@RequestMapping("diversionPhoneNoPoolList")
	@ResponseBody
	public String diversionPhoneNoPoolList(ProductChannelsDiversion productChannelsDiversion) {
		productChannelsDiversion.setStrategy_Type_Code(ProductChannelDiversionType.PHONE_NO_POLL.toString());
		// 产品策略id不能为空
		if (productChannelsDiversion.getProduct_Channels_Id() == null || productChannelsDiversion.getProduct_Channels_Id() == 0) {
			return null;
		}
		if (StringUtils.isNotEmpty(productChannelsDiversion.getStrategy_Rule())){
			productChannelsDiversion.setStrategy_Rule("%"+productChannelsDiversion.getStrategy_Rule()+ "%");
		}
		List<ProductChannelsDiversion> list = productChannelManage.queryProductChannelsDiversionList(productChannelsDiversion);
		return new SmsUIObjectMapper().asSuccessString(list, productChannelsDiversion.getPagination());
	}

	/**
	 * 导流策略导入号码池
	 * 
	 * @param phoneNoFile
	 * @param productChannelsDiversion
	 * @param appendRandomSize 随机位数
	 * @return
	 */
	@RequestMapping("diversionPhoneNoPoolImport")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略导入号码池")
	@AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "product:diversionPhoneNoImport")
	public LayUiJsonObjectFmt diversionPhoneNoPoolImport(@RequestParam(value = "phoneNoFile") MultipartFile phoneNoFile,
			ProductChannelsDiversion productChannelsDiversion,Integer appendRandomSize) {
		productChannelManage.importProductChannelsPhoneNoPool(phoneNoFile, productChannelsDiversion,appendRandomSize);
		return LayuiResultUtil.success();
	}

	/**
	 * 
	 * 导流策略跳转到修改号码池页面
	 * 
	 * @param ckIds
	 * @return
	 */
	@RequestMapping("diversionPhoneNoPoolToEdit")
	public String diversionPhoneNoPoolToEdit(Integer ckIds) {
		ProductChannelsDiversion ProductChannelsDiversion = productChannelManage
				.queryProductChannelsDiversionById(ckIds);
		request.setAttribute("productChannelsDiversion", ProductChannelsDiversion);
		return "/product/diversion/phoneno_pool_edit";
	}

	/**
	 * 修改号码池保存
	 * 
	 * @param productChannelsDiversion
	 * @return
	 */
	@RequestMapping("diversionPhoneNoPoolEditSave")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "修改号码池")
	public LayUiJsonObjectFmt diversionPhoneNoPoolEditSave(ProductChannelsDiversion productChannelsDiversion
            ,String callerNo,Integer appendRandomSize) {
		try {
            Map<String,Object> map = new TreeMap<>();
            map.put("appendRandomSize",appendRandomSize);
            map.put("callerNo",callerNo);
            productChannelsDiversion.setStrategy_Rule(JsonUtil.STANDARD.writeValueAsString(map));
			// 调用修改方法
			productChannelManage.updateProductChannelsDiversion(productChannelsDiversion);
		} catch (ServiceException se) {
			SuperLogger.error(se.getMessage(), se);
			return LayuiResultUtil.fail(se.getMessage());
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return LayuiResultUtil.fail(e.getMessage());
		}
		return LayuiResultUtil.success();
	}

    /**
     * 批量启动号码池
     *
     * @param entity
     * @return
     */
    @RequestMapping("startProductChannelsDiversionPhonePool")
    @ResponseBody
    public LayUiJsonObjectFmt startProductChannelsDiversionPhonePool(BaseParamEntity entity) {
        if (ObjectUtils.isEmpty(entity.getCkIds())) {
            throw new ServiceException("至少选择一条数据");
        }
        productChannelManage.updateDiversionStatus(entity.getCkIds()
                , ChannelStatus.START.toString(),ProductChannelDiversionType.PHONE_NO_POLL.toString());
        return LayuiResultUtil.success();
    }

    /**
     * 批量停止号码池
     *
     * @param entity
     * @return
     */
    @RequestMapping("stopProductChannelsDiversionPhonePool")
    @ResponseBody
    public LayUiJsonObjectFmt stopProductChannelsDiversionPhonePool(BaseParamEntity entity) {
        if (ObjectUtils.isEmpty(entity.getCkIds())) {
            throw new ServiceException("至少选择一条数据");
        }
        productChannelManage.updateDiversionStatus(entity.getCkIds()
                , ChannelStatus.STOP.toString(),ProductChannelDiversionType.PHONE_NO_POLL.toString());
        return LayuiResultUtil.success();
    }

    /**
     * 批量删除号码池
     *
     * @param entity
     * @return
     */
    @RequestMapping("delProductChannelsDiversionPhonePool")
    @ResponseBody
    @OperateAnnotation(moduleName = "产品管理", option = "号码池删除")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"product:delProductChannelsDiversionPhonePool")
    public LayUiJsonObjectFmt delProductChannelsDiversionPhonePool(BaseParamEntity entity) {
        if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
            throw new ServiceException("至少选择一条数据");
        }
        productChannelManage.delProductChannelsDiversion(entity.getCkIds(),ProductChannelDiversionType.PHONE_NO_POLL.toString());
        return LayuiResultUtil.success();
    }

    /**
	 * 批量启动策略  公用
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping("startProductChannelsDiversion")
	@ResponseBody
	public LayUiJsonObjectFmt startProductChannelsDiversion(BaseParamEntity entity) {
		if (ObjectUtils.isEmpty(entity.getCkIds())) {
			throw new ServiceException("至少选择一条数据");
		}
		productChannelManage.updateDiversionStatus(entity.getCkIds(), ChannelStatus.START.toString(),null);
		return LayuiResultUtil.success();
	}

	/**
	 * 批量停止导流策略 公用
	 *
	 * @param entity
	 * @return
	 */
	@RequestMapping("stopProductChannelsDiversion")
	@ResponseBody
	public LayUiJsonObjectFmt stopProductChannelsDiversion(BaseParamEntity entity) {
		if (ObjectUtils.isEmpty(entity.getCkIds())) {
			throw new ServiceException("至少选择一条数据");
		}
		productChannelManage.updateDiversionStatus(entity.getCkIds(), ChannelStatus.STOP.toString(),null);
		return LayuiResultUtil.success();
	}
	
	/**
	 * 导流策略防屏码前置
	 *
	 */
	@RequestMapping("preProductChannelsDiversionPreventShield")
	public ModelAndView preProductChannelsDiversionPreventShield(BaseParamEntity entity) {
		if (entity.getCkIds() == null || entity.getCkIds().size() < 1) {
			throw new ServiceException("请选择一条数据");
		}
		ModelAndView mv = new ModelAndView("/product/diversion/prevent_shield_edit");
		mv.addObject("diversion",
				this.productChannelManage.queryTypeDiversionByProductChannelId(entity.getCkIds().get(0),
						ProductChannelDiversionType.PREVENT_SHIELD_CODE.toString()));
		mv.addObject("productChannelsId", entity.getCkIds().get(0));
		return mv;
	}
	/**
	 * 导流策略防屏码保存
	 *
	 */
	@RequestMapping("editProductChannelsDiversionPreventShield")
	@ResponseBody
	@OperateAnnotation(moduleName = "产品管理", option = "导流策略防屏码保存")
	public LayUiJsonObjectFmt editProductChannelsDiversionPreventShield(ProductChannelsDiversion diversion) {
		try{
			diversion.setStrategy_Type_Code(ProductChannelDiversionType.PREVENT_SHIELD_CODE.toString());
			diversion.setName(ProductChannelDiversionType.PREVENT_SHIELD_CODE.getName());
			diversion.setStrategy_Rule(StringEscapeUtils.unescapeJavaScript(diversion.getStrategy_Rule()));
			this.productChannelManage.updateOrInsertDiversion(diversion);
		}catch (Exception e){
			SuperLogger.error(e);
			return LayuiResultUtil.fail("操作异常");
		}
		return LayuiResultUtil.success("操作成功");
	}

	/**
	 * 跳到空号检测页面
	 * @param entity 选中的id
	 * @return 返回页面
	 */
	@RequestMapping("diversionPhoneNoCheck")
	public ModelAndView diversionPhoneNoCheck(BaseParamEntity entity) {
		List<Integer> chkIds = entity.getCkIds();
		if (chkIds == null || chkIds.size() != 1) {
			throw new ServiceException("请选择一条数据");
		}
		ModelAndView mv = new ModelAndView("/product/diversion/phone_no_check");
		mv.addObject("diversion", productChannelManage.queryTypeDiversionByProductChannelId(chkIds.get(0),
			ProductChannelDiversionType.PHONE_NO_CHECK.toString()));
		mv.addObject("productChannelsId", entity.getCkIds().get(0));
		return mv;
	}

	/**
	 * 修改或者新增空号检测规则
	 * @param diversion 空号检测规则
	 * @return 执行结果，没有异常返回操作成功
	 */
	@RequestMapping("editDiversionPhoneNoCheck")
	@ResponseBody
	@OperateAnnotation(moduleName = "导流策略", option = "空号检测修改")
	public LayUiJsonObjectFmt editDiversionPhoneNoCheck(ProductChannelsDiversion diversion){
		try {
			diversion.setStrategy_Type_Code(ProductChannelDiversionType.PHONE_NO_CHECK.toString());
			diversion.setName(ProductChannelDiversionType.PHONE_NO_CHECK.getName());
			productChannelManage.updateOrInsertDiversion(diversion);
		} catch (Exception e){
			SuperLogger.error("操作异常", e);
			return LayuiResultUtil.fail("操作异常"+e.getMessage());
		}
		return LayuiResultUtil.success("操作成功");
	}
}