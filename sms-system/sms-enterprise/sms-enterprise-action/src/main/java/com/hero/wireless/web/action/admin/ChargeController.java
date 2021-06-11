package com.hero.wireless.web.action.admin;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.util.CertificateUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.json.SmsUIObjectMapper;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Invoice;
import com.hero.wireless.web.entity.business.ext.ChargeOrderExt;
import com.hero.wireless.web.service.IChargeManage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/")
public class ChargeController extends BaseEnterpriseController {

	@Resource(name = "chargeManage")
	private IChargeManage chargeManage;

    /**
     * 充值订单查询
     *
     * @return
     */
    @RequestMapping("charge_chargeOrderList")
    @ResponseBody
    public String chargeOrderList(ChargeOrderExt chargeOrderExt) {
        chargeOrderExt.setEnterprise_No(getLoginEnterprise().getNo());
        chargeOrderExt.setEnterprise_User_Id(getLoginEnterpriseUser().getId());
        List<ChargeOrderExt> chargeOrderExtList = chargeManage
                .queryChargeOrderList(chargeOrderExt);
        return new SmsUIObjectMapper().asSuccessString(chargeOrderExtList, chargeOrderExt.getPagination());
    }


    /**
     * 我要充值前置
     *
     * @return
     */
    @RequestMapping("charge_preCharge")
    public ModelAndView preCharge() throws Exception {
        ModelAndView mv = new ModelAndView("/charge/charge");
        mv.addObject("chargeMoneyCode", DatabaseCache.getCodeListBySortCode("charge_money_list"));
        return mv;
    }

    /**
     * 获取充值二维码
     *
     * @return
     */
    @RequestMapping("charge_preChargeQRCode")
    @ResponseBody
    public LayUiJsonObjectFmt preChargeQRCode(ChargeOrderExt chargeOrderExt) throws Exception {
        try {
            chargeOrderExt.setEnterprise_Name(getLoginEnterprise().getName());
            chargeOrderExt.setEnterprise_No(getLoginEnterprise().getNo());
            chargeOrderExt.setInput_User(getLoginEnterpriseUser().getReal_Name());
            chargeOrderExt.setInput_User_Id(getLoginEnterpriseUser().getId());
            Map<String,String> map = chargeManage.recharge(chargeOrderExt,request);
            return LayuiResultUtil.success(map);
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 查询充值结果
     */
    @RequestMapping("charge_queryChargeResult")
    @ResponseBody
    public LayUiJsonObjectFmt queryChargeResult(ChargeOrderExt chargeOrderExt) throws Exception {
        try {
            chargeOrderExt.setEnterprise_No(getLoginEnterprise().getNo());
            chargeOrderExt.setEnterprise_User_Id(getUserId());
            List<ChargeOrderExt> list = chargeManage.queryChargeOrderList(chargeOrderExt);
            if(list != null && list.size() == 1
                    && Constant.PAY_STATUS_SUCCESS.equals(list.get(0).getPay_Status())){
                return LayuiResultUtil.success();
            }
            return LayuiResultUtil.fail("");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    /**
     * 扫码支付回调
     *
     * @return
     */
    @RequestMapping("charge_payCallBack")
    @ResponseBody
    public String payCallBack() {
//        String dataceshi = "RKXzxcvELxxcJoNdr+iJQ9Odk1oZf7Ce1nNxjItT92BpMGsG48Ogp24/W6wKKNu0pg97HEAz4LKD8UnHpjhvraCpfpfld1h4fdQnQ2EZG0cEHYKRNxk7Zdt40arRJ1AxTQOWnOWttPX4dmJHfjxECxy6hu4xskxgKulRZp4cTM/AWV+xTLdic7VxwkDASMcYvshrBs+u6zdI2KmsAxPBCktuaRswj9Fgt3CbeS4QeWs+TPqJIZChCS6edbgediqUVIDZdFKBDJW3hoDquwa760VHWpWUoX+DEUy4ef9BzBF4P43V+JwsSsB8nhE2PV1MQ6aLhF6gGfRuD+AoJ7RY2xfLGXIqJa853z3AI0Aw9UmpPJDKFvy+lV+yEBBX3GTKQF+52/VJMtnK10tTajcQUoO60CsT6so/JZ51/Cb69ZGC9nR09XuEebX83PElQ23lMrLfadxiyG8fmyKmU5rxqyqbXp3S/ZO9msa0MLT97o0XidMUZRC2otlm9R3vehS3";
        String data = request.getParameter("data");
        SuperLogger.info("充值回调收到数据："+data);
        try {
            String resultData = CertificateUtil.decryptByPrivateKey(data,
                    DatabaseCache.getCodeBySortCodeAndCode("pay_config","pay_private_key").getValue());//商户证书私钥
            chargeManage.rechargePayResultTran(resultData);
            this.response.getOutputStream().write("0".getBytes());
            return null;
        } catch (Exception e) {
            SuperLogger.error("充值回调处理异常" + e.getMessage(), e);
            viewPrint(e.getMessage());
        }
        return null;
    }

    //发票管理列表
    @RequestMapping("charge_invoiceList")
    @ResponseBody
    public String invoiceList(Invoice invoice) {
        invoice.setEnterprise_No(getLoginEnterprise().getNo());
        invoice.setEnterprise_User_Id(getUserId());
        List<Invoice> invoiceList = chargeManage.queryInvoiceList(invoice);
        return new SmsUIObjectMapper().asSuccessString(invoiceList, invoice.getPagination());
    }

    //发票申请
    @RequestMapping("charge_addInvoice")
    @ResponseBody
    @AvoidRepeatableCommitAnnotation(systemModuleName = ENTERPRISE_PLATFORM+"charge_addInvoice")
    public LayUiJsonObjectFmt addInvoice(Invoice invoice) {
        try{
            invoice.setEnterprise_No(getLoginEnterprise().getNo());
            invoice.setName(getLoginEnterprise().getName());
            invoice.setEnterprise_User_Id(getUserId());
            chargeManage.addInvoice(invoice);
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
            chargeOrderExt.setEnterprise_No(getLoginEnterprise().getNo());
            chargeOrderExt.setEnterprise_User_Id(getUserId());
            chargeManage.exportChargeOrderList(
                    DatabaseCache.getCodeBySortCodeAndCode("system_env", "export_dir").getValue(),
                    chargeOrderExt, getEnterpriseDefaultExportFile(), Constant.PLATFORM_FLAG_ENTERPRISE);
            return new LayUiJsonObjectFmt(LayUiObjectMapper.CODE_SUCCESS, "已提交后台导出任务!");
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }
}
