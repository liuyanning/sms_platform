package com.hero.wireless.web.action.admin;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hero.wireless.web.entity.business.ext.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.entity.BaseParamEntity;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.ChargeOrder;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.Invoice;
import com.hero.wireless.web.service.IChargeManage;
import com.hero.wireless.web.service.IEnterpriseManage;
import com.hero.wireless.web.util.UploadUtil;

@Controller
@RequestMapping("/admin/")
public class ChargeController extends BaseAdminController {

    @Resource(name = "chargeManage")
    private IChargeManage chargeManage;
    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseManage;


    /**
     * 充值
     *
     */
    @RequestMapping("charge_charge")
    @ResponseBody
    @OperateAnnotation(moduleName = "充值管理", option = "充值")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"charge_charge")
    public LayUiJsonObjectFmt charge(ChargeOrder chargeOrder , @RequestParam(value="file",required = false) MultipartFile file) throws IOException {
        try {
			chargeOrder.setInput_User(getLoginRealName());
			chargeOrder.setInput_User_Id(getLoginUser().getId());
			if(file!= null){
			    Map<String, String> map = UploadUtil.uploadFile(file,"transferImgs");
                if(!"true".equals(map.get("status"))){
                    return LayuiResultUtil.fail("凭证上传失败");
                }
			    chargeOrder.setTransfer_Img(map.get("url"));
			}
			this.chargeManage.charge(chargeOrder);
		} catch (ServiceException e) {
			 return LayuiResultUtil.fail(e.getMessage());
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(),e);
			return LayuiResultUtil.error(e);
		}
        return LayuiResultUtil.success("操作成功");
    }

    @RequestMapping("charge_preCharge")
    public String preCharge(BaseParamEntity entity) {
        if (entity.getCkIds() != null && entity.getCkIds().size() > 0) {
            Enterprise enterprise = this.enterpriseManage
                    .queryEnterpriseById(entity.getCkIds().get(0));
            request.setAttribute("enterprise", enterprise);

            //获取企业所有账户
            EnterpriseUserExt user = new EnterpriseUserExt();
            user.setEnterprise_No(enterprise.getNo());
            request.setAttribute("userList", this.enterpriseManage.queryEnterpriseUserList(user));
        }
        return "/charge/charge";
    }

    /**
     * 充值订单查询
     *
     */
    @RequestMapping("/charge_chargeOrderList")
    @ResponseBody
    public String chargeOrderList(ChargeOrderExt chargeOrderExt) {
        List<ChargeOrderExt> chargeOrderExtList = chargeManage.queryChargeOrderList(chargeOrderExt);
        return new LayUiObjectMapper().asSuccessString(chargeOrderExtList, chargeOrderExt.getPagination().getTotalCount());
    }


    @RequestMapping("charge_openChargeOrderList")
    @ResponseBody
    public String openChargeOrderList(ChargeOrder chargeOrder) {
        List<ChargeOrder> chargeOrderList = chargeManage.queryOpenChargeOrderList(chargeOrder);
        return new LayUiObjectMapper().asSuccessString(chargeOrderList, chargeOrder.getPagination().getTotalCount());
    }

    @RequestMapping("charge_preOpenChargeOrder")
    public String preOpenChargeOrder(@RequestParam(name = "ckIds") List<Integer> ckIds) {
        ChargeOrder chargeOrder = this.chargeManage.queryChargeOrderById(ckIds.get(0));
        request.setAttribute("chargeOrder", chargeOrder);
        if(chargeOrder!= null && chargeOrder.getEnterprise_User_Id()!=null){
            EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
            enterpriseUserExt.setId(chargeOrder.getEnterprise_User_Id());
            request.setAttribute("enterpriseUser", enterpriseManage.queryEnterpriseUserList(enterpriseUserExt).get(0));
        }
        return "/charge/open";
    }

    @RequestMapping("charge_preEditChargeOrderPayType")
    public ModelAndView preEditChargeOrderPayType(@RequestParam(name = "ckIds") List<Integer> ckIds) {
        ModelAndView mv = new ModelAndView("/charge/edit_pay_type");
        ChargeOrder chargeOrder = this.chargeManage.queryChargeOrderById(ckIds.get(0));
        mv.addObject("chargeOrder", chargeOrder);
        if(chargeOrder!= null && chargeOrder.getEnterprise_User_Id()!=null){
            EnterpriseUserExt enterpriseUserExt = new EnterpriseUserExt();
            enterpriseUserExt.setId(chargeOrder.getEnterprise_User_Id());
            request.setAttribute("enterpriseUser", enterpriseManage.queryEnterpriseUserList(enterpriseUserExt).get(0));
        }
        return mv;
    }

    @RequestMapping("charge_editChargeOrderPayType")
    @ResponseBody
    @OperateAnnotation(moduleName = "充值记录", option = "修改充值类型")
    public LayUiJsonObjectFmt editChargeOrderPayType(ChargeOrderExt chargeOrderExt, @RequestParam(value="file",required = false) MultipartFile file) throws Exception{
        if(file != null){
            Map<String, String> map = UploadUtil.uploadFile(file,"transferImgs");
            if(!"true".equals(map.get("status"))){
                return LayuiResultUtil.fail("上传失败");
            }
            chargeOrderExt.setTransfer_Img(map.get("url"));
        }
        this.chargeManage.editChargeOrder(chargeOrderExt);
        return LayuiResultUtil.success();
    }

    @RequestMapping("charge_open")
    @ResponseBody
    @OperateAnnotation(moduleName = "充值管理", option = "开通充值")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM+"charge_open")
    public LayUiJsonObjectFmt charge_open(ChargeOrder chargeOrder) {
        try {
            chargeOrder.setOpen_User(getLoginRealName());
            this.chargeManage.open(chargeOrder);
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.fail("开通异常！");
        }
        return LayuiResultUtil.success();
    }

    //发票管理列表
    @RequestMapping("charge_invoiceList")
    @ResponseBody
    public String invoiceList(Invoice invoice) {
        List<Invoice> invoiceList = chargeManage.queryInvoiceList(invoice);
        return new LayUiObjectMapper().asSuccessString(invoiceList, invoice.getPagination().getTotalCount());
    }

    //发票审核前置
    @RequestMapping("charge_preEditInvoice")
    public String preEditInvoice(BaseParamEntity entity) {
        Invoice invoice = new Invoice();
        invoice.setId(entity.getCkIds().get(0));
        request.setAttribute("invoice",chargeManage.queryInvoiceList(invoice).get(0));
        return "/charge/invoice_edit";
    }

    //发票审核保存
    @RequestMapping("charge_editInvoice")
    @ResponseBody
    @OperateAnnotation(moduleName = "发票管理", option = "发票审核")
    public LayUiJsonObjectFmt editInvoice(Invoice invoice) {
        try{
            invoice.setUpdate_User(getLoginRealName());
            invoice.setUpdate_Date(new Date());
            chargeManage.editInvoice(invoice);
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
        return LayuiResultUtil.success();
    }

    /**
     * 导出充值记录
     */
    @RequestMapping("charge_exportChargeOrderList")
    @ResponseBody
    public LayUiJsonObjectFmt exportChargeOrderList(ChargeOrderExt chargeOrderExt) {
        try {
            chargeManage.exportChargeOrderList(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    chargeOrderExt, getAdminDefaultExportFile(), Constant.PLATFORM_FLAG_ADMIN);
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

}