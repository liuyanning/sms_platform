package com.hero.wireless.web.service;

import com.drondea.wireless.config.Constant;
import com.drondea.wireless.config.ResultStatus;
import com.drondea.wireless.util.CertificateUtil;
import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.MoneyUtils;
import com.drondea.wireless.util.QrCodeUtils;
import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.SecretUtil;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.wireless.enums.ChargeType;
import com.hero.wireless.enums.DisposeStateCode;
import com.hero.wireless.enums.InvoiceType;
import com.hero.wireless.enums.PropertiesType;
import com.hero.wireless.enums.RefundAuditStatus;
import com.hero.wireless.json.JsonUtil;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IChargeOrderDAO;
import com.hero.wireless.web.dao.business.IEnterpriseDAO;
import com.hero.wireless.web.dao.business.IEnterpriseUserDAO;
import com.hero.wireless.web.dao.business.IInvoiceDAO;
import com.hero.wireless.web.dao.business.IPropertiesDAO;
import com.hero.wireless.web.dao.business.ISystemLogDAO;
import com.hero.wireless.web.dao.business.ext.IChargeOrderExtDAO;
import com.hero.wireless.web.dao.business.ext.IEnterpriseUserExtDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.ChargeOrder;
import com.hero.wireless.web.entity.business.ChargeOrderExample;
import com.hero.wireless.web.entity.business.Enterprise;
import com.hero.wireless.web.entity.business.EnterpriseExample;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.EnterpriseUserExample;
import com.hero.wireless.web.entity.business.Invoice;
import com.hero.wireless.web.entity.business.InvoiceExample;
import com.hero.wireless.web.entity.business.Properties;
import com.hero.wireless.web.entity.business.PropertiesExample;
import com.hero.wireless.web.entity.business.SystemLog;
import com.hero.wireless.web.entity.business.ext.*;
import com.hero.wireless.web.exception.BaseException;
import com.hero.wireless.web.service.base.BaseService;
import com.hero.wireless.web.util.CodeUtil;
import com.hero.wireless.web.util.SMSUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service("chargeManage")
public class ChargeManageImpl extends BaseService implements IChargeManage {

    @Resource(name = "IChargeOrderDAO")
    private IChargeOrderDAO<ChargeOrder> chargeOrderDAO;
    @Resource(name = "chargeOrderExtDAO")
    private IChargeOrderExtDAO chargeOrderExtDAO;
    @Resource(name = "IEnterpriseDAO")
    private IEnterpriseDAO<Enterprise> enterpriseDAO;
    @Resource(name = "IEnterpriseUserDAO")
    private IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
    @Resource(name = "ISystemLogDAO")
    private ISystemLogDAO<SystemLog> systemLogDAO;
    @Resource(name = "IInvoiceDAO")
    private IInvoiceDAO<Invoice> invoiceDAO;
    @Resource(name = "enterpriseUserExtDAO")
    private IEnterpriseUserExtDAO enterpriseUserExtDAO;
    @Resource(name = "IPropertiesDAO")
    private IPropertiesDAO<Properties> propertiesDAO;
    @Resource(name = "enterpriseManage")
    private IEnterpriseManage enterpriseMange;

    @Override
    public ChargeOrder charge(ChargeOrder data) {
        data.setInput_Date(new Date());
        data.setOrder_No(UUID.randomUUID().toString());
        data.setCreate_Date(new Date());
        // 20190927 新增逻辑 充值直接到开通，这里保存审核默认通过
        data.setApprove_Date(new Date());
        data.setApprove_Remark("默认通过");
        data.setApprove_Status_Code("Agree");
        data.setApprove_User("system");
        EnterpriseUser user = this.enterpriseUserDAO.selectByPrimaryKey(data.getEnterprise_User_Id());
        if (user == null) {
            throw new ServiceException(ResultStatus.USER_NOT_FIND_ERROR);
        }
        data.setBefore_Money(user.getAvailable_Amount());
        // 20190927 逻辑结束。
        this.chargeOrderDAO.insert(data);
        return data;
    }

    @Override
    public ChargeOrder approve(ChargeOrder data) {
        data.setApprove_Date(new Date());
        ChargeOrderExample example = new ChargeOrderExample();
        ChargeOrderExample.Criteria cri = example.createCriteria();
        cri.andIdEqualTo(data.getId());
        cri.andApprove_Status_CodeIsNull();
        chargeOrderDAO.updateByExampleSelective(data, example);
        return data;
    }

    @Override
    @Transactional(transactionManager = "txBusinessManager")
    @SuppressWarnings("all")
    public ChargeOrder open(ChargeOrder data) {
        if (StringUtils.isEmpty(data.getOpen_Status_Code())) {
            throw new ServiceException("请选择开通状态！");
        }
        if ("object".equals(data.getOpen_Status_Code()) && StringUtils.isEmpty(data.getOpen_Remark())) {
            throw new ServiceException("请填写拒绝原因！");
        }
        data.setOpen_Date(new Date());
        ChargeOrderExample example = new ChargeOrderExample();
        ChargeOrderExample.Criteria cri = example.createCriteria();
        example.setDataLock(" FOR UPDATE");
        cri.andIdEqualTo(data.getId());
        cri.andApprove_Status_CodeEqualTo("Agree");
        cri.andOpen_Status_CodeIsNull();
        int result = chargeOrderDAO.updateByExampleSelective(data, example);
        if (result == 0) {
            return data;
        }
        if ("agree".equals(data.getOpen_Status_Code())) {// 同意 更新余额
            // 更新企业金额
            ChargeOrder chargeOrder = chargeOrderDAO.selectByPrimaryKey(data.getId());
            EnterpriseExample eExample = new EnterpriseExample();
            eExample.setDataLock(" FOR UPDATE");
            eExample.createCriteria().andNoEqualTo(chargeOrder.getEnterprise_No());
            Enterprise enterprise = this.enterpriseDAO.selectByExample(eExample).get(0);
            BigDecimal money = chargeOrder.getMoney();// 充值金额
            if (money == null || money.compareTo(BigDecimal.ZERO) == 0) {
                SuperLogger.debug("获取充值金额异常！！");
                throw new BaseException("获取充值金额异常！！");
            }
            BigDecimal availableAmount = enterprise.getAvailable_Amount() == null ? new BigDecimal(0)
                    : enterprise.getAvailable_Amount();
            availableAmount = availableAmount.add(money);
            Enterprise record = new Enterprise();
            record.setId(enterprise.getId());
            record.setAvailable_Amount(availableAmount);
            enterpriseMange.updateByPrimaryKeySelective(record);

            if (chargeOrder.getEnterprise_User_Id() != null) {
                // 更新企业用户余额
                EnterpriseUserExample eUserExample = new EnterpriseUserExample();
                eUserExample.setDataLock(" FOR UPDATE");
                eUserExample.createCriteria().andIdEqualTo(chargeOrder.getEnterprise_User_Id());
                EnterpriseUser user = this.enterpriseUserDAO.selectByExample(eUserExample).get(0);
                BigDecimal userAvailableAmount = user.getAvailable_Amount() == null ? new BigDecimal(0)
                        : user.getAvailable_Amount();
                userAvailableAmount = userAvailableAmount.add(money);
                EnterpriseUserExt newUser = new EnterpriseUserExt();
                newUser.setId(user.getId());
                newUser.setAvailable_Amount(userAvailableAmount);
                enterpriseMange.editUser(newUser);
            }
        }
        return data;
    }

    @Override
    public List<ChargeOrder> queryApproveChargeOrderList(ChargeOrder condition) {
        ChargeOrderExample example = new ChargeOrderExample();
        ChargeOrderExample.Criteria cri = example.createCriteria();
        cri.andApprove_Status_CodeIsNull();
        if (!StringUtils.isEmpty(condition.getEnterprise_Name())) {
            cri.andEnterprise_NameLike("%" + condition.getEnterprise_Name() + "%");
        }
        example.setOrderByClause(" id desc");
        example.setPagination(condition.getPagination());
        return this.chargeOrderDAO.selectByExamplePage(example);
    }

    public int editChargeOrder(ChargeOrderExt data) {
        return this.chargeOrderDAO.updateByPrimaryKeySelective(data);
    }

    @Override
    public List<ChargeOrderExt> queryChargeOrderList(ChargeOrderExt condition) {
        ChargeOrderExample example = getChargeOrderExample(condition);
        example.setOrderByClause(" c.id desc ");
        example.setPagination(condition.getPagination());
        return this.chargeOrderExtDAO.selectExtByExamplePage(example);
    }

    private ChargeOrderExample getChargeOrderExample(ChargeOrderExt condition) {
        ChargeOrderExample example = new ChargeOrderExample();
        ChargeOrderExample.Criteria cri = example.createCriteria();
        if (StringUtils.isEmpty(condition.getMin_Input_Date())) {
            condition.setMin_Input_Date(DateTime.getCurrentDayMinDate());
        }
        if (StringUtils.isEmpty(condition.getMax_Input_Date())) {
            condition.setMax_Input_Date(DateTime.getCurrentDayMaxDate());
        }
        cri.andInput_DateBetween(DateTime.getDate(condition.getMin_Input_Date()),
                DateTime.getDate(condition.getMax_Input_Date()));
        if (!StringUtils.isEmpty(condition.getApprove_Status_Code())) {
            cri.andApprove_Status_CodeEqualTo(condition.getApprove_Status_Code());
        }
        if (!StringUtils.isEmpty(condition.getOrder_No())) {
            cri.andOrder_NoEqualTo(condition.getOrder_No());
        }
        if (!StringUtils.isEmpty(condition.getOpen_Status_Code())) {
            cri.andOpen_Status_CodeEqualTo(condition.getOpen_Status_Code());
        }
        if (!StringUtils.isEmpty(condition.getPay_Type_Code())) {
            cri.andPay_Type_CodeEqualTo(condition.getPay_Type_Code());
        }
        if (!StringUtils.isEmpty(condition.getEnterprise_No())) {
            cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
        }
        if (condition.getEnterprise_User_Id() != null) {
            cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
        }
        if (!StringUtils.isEmpty(condition.getEnterprise_Name())) {
            cri.andEnterprise_NameLike("%" + condition.getEnterprise_Name() + "%");
        }
        return example;
    }

    @Override
    public List<ChargeOrder> queryOpenChargeOrderList(ChargeOrder condition) {
        ChargeOrderExample example = new ChargeOrderExample();
        ChargeOrderExample.Criteria cri = example.createCriteria();
        cri.andOpen_Status_CodeIsNull();
        cri.andApprove_Status_CodeEqualTo("Agree");
        if (!StringUtils.isEmpty(condition.getEnterprise_No())) {
            cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
        }
        example.setOrderByClause(" id desc");
        example.setPagination(condition.getPagination());
        return this.chargeOrderDAO.selectByExamplePage(example);
    }

    @Override
    public ChargeOrder queryChargeOrderById(Integer id) {
        return this.chargeOrderDAO.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(ChargeOrder chargeOrder) {
        this.chargeOrderDAO.updateByPrimaryKeySelective(chargeOrder);
    }

    @Override
    @Transactional(transactionManager = "txBusinessManager")
    public int chargeOrderBySmsStatisticsListTran(List<SmsStatisticsExt> smsList) {
        List<String> logList = new ArrayList<String>();
        smsList.stream().forEach(sms -> {
            int smsTotal = sms.getFaildTotal() + sms.getSubmit_Faild_Total() + sms.getUnknownTotal();// 返还条数
            BigDecimal unit_price = sms.getEnterprise_User_Unit_Price() != null ? sms.getEnterprise_User_Unit_Price()
                    : BigDecimal.ZERO;// 单价
            BigDecimal money = unit_price.multiply(new BigDecimal(smsTotal));// 返还金额
            if (money.compareTo(BigDecimal.ZERO) == 0) {
                return;
            }
            this.chargeOrderDAO.insertSelective(packageChargeOrder(sms, money));
            String isAudit = DatabaseCache.getStringValueBySystemEnvAndCode("is_audit_refund", "");
            if (RefundAuditStatus.FALSE.toString().equals(isAudit)) {// 免审
                // 更新企业用户余额
                updateEnterpriseUserBalance(sms.getEnterprise_User_Id(), money);
                // 更新企业余额
                updateEnterpriseBalance(sms.getEnterprise_No(), money);
            }
            logList.add("短信返还: 企业用户id:" + sms.getEnterprise_User_Id() + ",失败条数:" + smsTotal + ",返还金额:" + money);
        });
        insertSystemLog(logList);
        return logList.size();
    }

    @Override
    public Map<String, String> recharge(ChargeOrderExt chargeOrderExt, HttpServletRequest request) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        checkOrder(chargeOrderExt);// 校验
        ChargeOrder order = convert(chargeOrderExt);// 封装
        Map<String, String> metaSignMap = makeMetaSign(order.getOrder_No(),
                MoneyUtils.changeY2F(order.getMoney()).toString(), order.getPay_Type_Code(), "自主充值");
        SuperLogger.info("充值封装数据：" + metaSignMap.toString());
        String param = CertificateUtil.encryptByPublicKey(JsonUtil.STANDARD.writeValueAsString(metaSignMap),
                DatabaseCache.getCodeBySortCodeAndCode("pay_config", "pay_public_key").getValue());// 支付公钥（服务端）
        String reqParam = "data=" + URLEncoder.encode(param, "UTF-8") + "&merchNo=" + metaSignMap.get("merNo")
                + "&version=V4.0.0.0";
        HttpClient httpClient = new HttpClient();// http://192.144.145.245:9002/api/pay
        CharsetResponseBody payUrl = httpClient
                .post(DatabaseCache.getCodeBySortCodeAndCode("pay_config", "payurl").getValue(), reqParam);
        Map<String, String> resultMap = JsonUtil.STANDARD.readValue(payUrl.string(),
                new TypeReference<Map<String, String>>() {
                });
        SuperLogger.info("支付平台返回数据：" + resultMap.toString());
        String stateCode = resultMap.get("stateCode");
        if (!Constant.RECHARGE_SUBMIT_SUCCESS.equals(stateCode)) {
            String msg = StringUtils.isEmpty(resultMap.get("msg")) ? "下单失败" : resultMap.get("msg");
            insertChargeOrder(order, msg);// 插入
            returnMap.put("status", stateCode);
            returnMap.put("message", "下单失败");
            SuperLogger.error("充值失败：支付系统数据：" + resultMap);
            return returnMap;
        }
        String resultSign = resultMap.get("sign");
        resultMap.remove("sign");
        String targetString = SecretUtil.MD5(JsonUtil.STANDARD.writeValueAsString(resultMap)
                + DatabaseCache.getCodeBySortCodeAndCode("pay_config", "md5key").getValue());
        if (!targetString.equals(resultSign)) {
            insertChargeOrder(order, "充值下单验签失败");// 插入
            SuperLogger.error("充值验签失败。");
            returnMap.put("status", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("message", "充值验签失败");
            return returnMap;
        }
        insertChargeOrder(order, "下单成功，等待支付");// 插入
        returnMap.put("status", Constant.RECHARGE_SUBMIT_SUCCESS);
        returnMap.put("message", "下单成功");
        returnMap.put("qrcodeUrl", getQrCode(resultMap.get("qrcodeUrl"), request));
        returnMap.put("orderNo", order.getOrder_No());
        return returnMap;
    }

    private String getQrCode(String qrcodeUrl, HttpServletRequest request) {
        String uuid = UUID.randomUUID().toString();
        String dirPath = request.getSession().getServletContext().getRealPath("") + File.separator + "payImg"
                + File.separator + "wxsm";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = dirPath + File.separator + uuid + ".jpg";
        String returnPath = File.separator + "payImg" + File.separator + "wxsm" + File.separator + uuid + ".jpg";
        QrCodeUtils.generateQRCode(qrcodeUrl, 300, 300, "jpg", filePath);
        return returnPath;
    }

    @Override
    @Transactional(transactionManager = "txBusinessManager")
    public Map<String, String> rechargePayResultTran(String resultData) throws Exception {
        Map<String, String> returnMap = new HashMap<>();
        // 1. 解析回调报文
        ObjectMapper mapper = JsonUtil.STANDARD;
        Map<String, String> resultMap = mapper.readValue(resultData, new TypeReference<Map<String, String>>() {
        });
        SuperLogger.info(DateTime.getString() + " 充值回调报文解析：" + resultData);
        // 2. 验签
        String resSign = resultMap.remove("sign");
        String jsonStr = mapper.writeValueAsString(resultMap);
        String sign = SecretUtil
                .MD5(jsonStr + DatabaseCache.getCodeBySortCodeAndCode("pay_config", "md5key").getValue(), "UTF-8");
        if (!sign.equals(resSign)) {
            SuperLogger.error("签名验证失败:" + resultMap);
            returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("msg", "签名验证失败");
            return returnMap;
        }
        // 3. 验证订单信息
        ChargeOrderExample example = new ChargeOrderExample();
        example.createCriteria().andOrder_NoEqualTo(resultMap.get("orderNum"));
        List<ChargeOrder> orderList = chargeOrderDAO.selectByExample(example);
        if (orderList.size() != 1) {
            SuperLogger.error("订单号：" + resultMap.get("orderNum") + " 不存在");
            returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("msg", "充值订单不存在");
            return returnMap;
        }
        ChargeOrder chargeOrder = orderList.get(0);
        if (Constant.PAY_STATUS_SUCCESS.equals(chargeOrder.getPay_Status())) {
            SuperLogger.error("状态重复,订单已支付");
            returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("msg", "状态重复,订单已支付");
            return returnMap;
        }
        if (!Constant.RECHARGE_SUBMIT_SUCCESS.equals(resultMap.get("payResult"))) {
            chargeOrder.setPay_Status(Constant.PAY_STATUS_FAILED);
            chargeOrderDAO.updateByPrimaryKeySelective(chargeOrder);
            SuperLogger.error("支付失败,payResult:" + resultMap.get("payResult"));
            returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("msg", "支付失败");
            return returnMap;
        }
        // 4. 更新充值订单表
        chargeOrder.setApprove_Status_Code(Constant.RECHARGE_AGREE);
        chargeOrder.setApprove_Remark("支付成功");
        chargeOrder.setOpen_Status_Code(Constant.RECHARGE_AGREE);
        chargeOrder.setOpen_Date(new Date());
        chargeOrder.setOpen_Remark("支付成功");
        chargeOrder.setPay_Status(Constant.PAY_STATUS_SUCCESS);
        // 5. 更新用户余额信息
        EnterpriseUserExample enterpriseUserExample = new EnterpriseUserExample();
        enterpriseUserExample.createCriteria().andIdEqualTo(chargeOrder.getEnterprise_User_Id());
        List<EnterpriseUser> users = enterpriseUserDAO.selectByExample(enterpriseUserExample);
        if (users.size() != 1) {
            SuperLogger.error("数据错误！Enterprise_User_Id:" + chargeOrder.getEnterprise_User_Id());
            returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("msg", "支付失败");
            return returnMap;
        }
        EnterpriseUser eUser = users.get(0);
        EnterpriseUser updateUser = new EnterpriseUser();
        updateUser.setId(eUser.getId());
        BigDecimal userAvailableAmount = eUser.getAvailable_Amount() == null ? BigDecimal.ZERO
                : eUser.getAvailable_Amount();
        updateUser.setAvailable_Amount(userAvailableAmount.add(chargeOrder.getMoney()));
        // 6. 更新企业余额信息
        EnterpriseExample enterpriseExample = new EnterpriseExample();
        enterpriseExample.createCriteria().andNoEqualTo(chargeOrder.getEnterprise_No());
        List<Enterprise> enterprises = enterpriseDAO.selectByExample(enterpriseExample);
        if (enterprises.size() != 1) {
            SuperLogger.error("数据错误！Enterprise_No:" + chargeOrder.getEnterprise_No());
            returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_FAILED);
            returnMap.put("msg", "支付失败");
            return returnMap;
        }
        Enterprise enterprise = enterprises.get(0);
        Enterprise updateEnterprise = new Enterprise();
        updateEnterprise.setId(enterprise.getId());
        BigDecimal available_amount = enterprise.getAvailable_Amount() == null ? BigDecimal.ZERO
                : enterprise.getAvailable_Amount();
        updateEnterprise.setAvailable_Amount(available_amount.add(chargeOrder.getMoney()));
        // chargeOrder.setAfter_Money(chargeOrder.getBefore_Money().add(chargeOrder.getMoney()));
        chargeOrderDAO.updateByPrimaryKeySelective(chargeOrder);
        enterpriseDAO.updateByPrimaryKeySelective(updateEnterprise);
        enterpriseUserDAO.updateByPrimaryKeySelective(updateUser);
        returnMap.put("stateCode", Constant.RECHARGE_SUBMIT_SUCCESS);
        returnMap.put("msg", "支付成功");
        return returnMap;
    }

    // 主账户划拨到子账户
    @Override
    @Transactional(transactionManager = "txBusinessManager")
    public void user2charge(ChargeOrder chargeOrder, Integer masterUserId) throws Exception {
        if (masterUserId.equals(chargeOrder.getEnterprise_User_Id())){ //不是给自己充值
            throw new ServiceException("不允许给自己划拨余额！");
        }
        BigDecimal money = chargeOrder.getMoney();
        EnterpriseUser masterUser = this.enterpriseUserDAO.selectByPrimaryKey(masterUserId);
        // 获取子账户
        EnterpriseUser subUser = this.enterpriseUserDAO.selectByPrimaryKey(chargeOrder.getEnterprise_User_Id());
        //两个账户所属企业不一致
        if (!subUser.getEnterprise_No().equals(masterUser.getEnterprise_No())){
            throw new ServiceException("企业不一致，无法充值！");
        }
        if (masterUser.getAvailable_Amount() == null || masterUser.getAvailable_Amount().compareTo(money) == -1) {
            throw new ServiceException("当前账户余额不足！");
        }
        BigDecimal subAmount = subUser.getAvailable_Amount() == null ? BigDecimal.ZERO : subUser.getAvailable_Amount();
        if (subAmount.add(money).compareTo(BigDecimal.ZERO) == -1) {
            throw new ServiceException("被划拨账户余额不足！");
        }
        BigDecimal masterAmount = masterUser.getAvailable_Amount() == null ? BigDecimal.ZERO
                : masterUser.getAvailable_Amount();
        // 插入充值记录
        chargeOrder.setEnterprise_No(masterUser.getEnterprise_No());
        chargeOrder.setOrder_No(CodeUtil.buildNoByTime());
        chargeOrder.setDescription(masterUser.getReal_Name() + " 划拨" + money + "元到 " + subUser.getReal_Name());
        chargeOrder.setMoney(money);// 返还金额
        chargeOrder.setBefore_Money(subAmount);//充值前金额
        chargeOrder.setInput_User(masterUser.getReal_Name());
        chargeOrder.setInput_User_Id(masterUser.getId());
        chargeOrder.setInput_Remark("余额划拨");
        chargeOrder.setInput_Date(new Date());
        chargeOrder = packageChargeOrderUser2User(chargeOrder);
        chargeOrderDAO.insert(chargeOrder);

        // 更新子账户
        EnterpriseUserExt newUser = new EnterpriseUserExt();
        newUser.setId(subUser.getId());
        newUser.setAvailable_Amount(subAmount.add(money));
        enterpriseMange.editUser(newUser);
        // 更新主账户
        newUser = new EnterpriseUserExt();
        newUser.setId(masterUser.getId());
        newUser.setAvailable_Amount(masterAmount.subtract(money));
        enterpriseMange.editUser(newUser);
    }

    private void insertChargeOrder(ChargeOrder order, String msg) {
        order.setApprove_Remark(msg);
        order.setOpen_Remark(msg);
        this.chargeOrderDAO.insertSelective(order);
    }

    private void checkOrder(ChargeOrder order) throws Exception {
        if (order == null) {
            throw new ServiceException("订单不能为空!");
        }
        if (order.getMoney() == null || order.getMoney().compareTo(BigDecimal.ZERO) == -1) {
            throw new ServiceException("充值金额不能为空!");
        }
        if (order.getEnterprise_No() == null) {
            throw new ServiceException("充值企业信息不能为空!");
        }
        if (order.getEnterprise_User_Id() == null) {
            throw new ServiceException("用户信息不能为空!");
        }
    }

    // 封装 ChargeOrder
    private ChargeOrder packageChargeOrder(SmsStatisticsExt sms, BigDecimal money) {
        ChargeOrder chargeOrder = new ChargeOrder();
        chargeOrder.setEnterprise_No(sms.getEnterprise_No());
        chargeOrder.setEnterprise_Name(sms.getEnterprise() == null ? "" : sms.getEnterprise().getName());
        chargeOrder.setEnterprise_User_Id(Integer.valueOf(sms.getEnterprise_User_Id()));
        chargeOrder.setPay_Type_Code("failedtoreturn");// 失败返还
        chargeOrder.setMoney(money);// 返还金额
        chargeOrder.setMoney_Letter(MoneyUtils.toChinese(money.toString()));// 返还金额大写
        chargeOrder.setInput_User("system");
        chargeOrder.setInput_User_Id(0);
        chargeOrder.setInput_Remark("提交失败和发送失败短信，返还金额");
        chargeOrder.setInput_Date(new Date());
        chargeOrder.setOrder_No(UUID.randomUUID().toString());
        chargeOrder.setCreate_Date(new Date());
        chargeOrder.setApprove_Status_Code(Constant.RECHARGE_AGREE);
        chargeOrder.setApprove_User("system");
        chargeOrder.setApprove_Remark("默认");
        chargeOrder.setApprove_Date(new Date());
        String isAudit = DatabaseCache.getStringValueBySystemEnvAndCode("is_audit_refund",
                RefundAuditStatus.TRUE.toString());
        if (RefundAuditStatus.TRUE.toString().equals(isAudit)) {// 需要审核
            chargeOrder.setOpen_Status_Code(null);
            chargeOrder.setOpen_User(null);
            chargeOrder.setOpen_Remark(null);
            chargeOrder.setOpen_Date(null);
            chargeOrder.setFinancial_Confirm(0);
        } else {
            chargeOrder.setOpen_Status_Code(Constant.RECHARGE_AGREE);
            chargeOrder.setOpen_User("system");
            chargeOrder.setOpen_Remark("免审开通");
            chargeOrder.setOpen_Date(new Date());
            chargeOrder.setFinancial_Confirm(1);
        }
        return chargeOrder;
    }

    private ChargeOrder convert(ChargeOrderExt chargeOrderExt) {
        ChargeOrder chargeOrder = new ChargeOrder();
        chargeOrder.setEnterprise_No(chargeOrderExt.getEnterprise_No());
        chargeOrder.setEnterprise_User_Id(chargeOrderExt.getEnterprise_User_Id());
        chargeOrder.setEnterprise_Name(chargeOrderExt.getEnterprise_Name());
        String orderNo = DateTime.getString(new Date(), DateTime.Y_M_D_H_M_S_S_2) + RandomUtil.randomStr(3);
        chargeOrder.setOrder_No(orderNo);
        EnterpriseUser user = enterpriseUserDAO.selectByPrimaryKey(chargeOrderExt.getEnterprise_User_Id());
        if (user == null) {
            throw new ServiceException(ResultStatus.USER_NOT_FIND_ERROR);
        }
        chargeOrder.setBefore_Money(user.getAvailable_Amount());// 充值前金额
        chargeOrder.setMoney(chargeOrderExt.getMoney());// 返还金额
        chargeOrder.setMoney_Letter(chargeOrderExt.getMoney_Letter());// 返还金额大写
        chargeOrder.setPay_Type_Code(chargeOrderExt.getPay_Type_Code());// WX ZFB
        chargeOrder.setPay_Status(Constant.PAY_STATUS_WAIT);// 待支付
        chargeOrder.setInput_User(chargeOrderExt.getInput_User());
        chargeOrder.setInput_User_Id(chargeOrderExt.getInput_User_Id());
        chargeOrder.setInput_Remark("客户自助充值");
        chargeOrder.setInput_Date(new Date());
        chargeOrder.setCreate_Date(new Date());
        chargeOrder.setApprove_Status_Code(Constant.RECHARGE_REFUSE);
        chargeOrder.setApprove_User("system");
        chargeOrder.setApprove_Remark("默认拒绝");
        chargeOrder.setApprove_Date(new Date());
        chargeOrder.setOpen_Status_Code(Constant.RECHARGE_REFUSE);
        chargeOrder.setOpen_User("system");
        chargeOrder.setOpen_Remark("默认拒绝");
        chargeOrder.setOpen_Date(new Date());
        chargeOrder.setFinancial_Confirm(0);
        return chargeOrder;
    }

    private Map<String, String> makeMetaSign(String orderNum, String amount, String payType, String goodsName)
            throws JsonProcessingException {
        Map<String, String> metaSignMap = new TreeMap<>();
        metaSignMap.put("orderNum", orderNum);
        metaSignMap.put("version", "V4.0.0.0");
        metaSignMap.put("charset", "UTF-8");//
        metaSignMap.put("random", RandomUtil.randomStr(4));// 4位随机数
        metaSignMap.put("merNo", DatabaseCache.getCodeBySortCodeAndCode("pay_config", "merno").getValue());
        metaSignMap.put("subMerNo", DatabaseCache.getCodeBySortCodeAndCode("pay_config", "merno").getValue());
        metaSignMap.put("netway", payType);// WX:微信支付,ZFB:支付宝支付
        metaSignMap.put("amount", amount);// 单位:分
        metaSignMap.put("goodsName", StringUtils.isNotBlank(goodsName) ? goodsName : "goodsName");// 微信支付:opengId,支付宝支付:authCode
        // metaSignMap.put("goodsName", "oyQny5MLrh__WbL5yiTPn-WuhKAc");//
        // 微信支付:opengId,支付宝支付:authCode
        metaSignMap.put("callBackUrl", DatabaseCache.getCodeBySortCodeAndCode("pay_config", "callbackurl").getValue());// 回调地址
        metaSignMap.put("callBackViewUrl", "http://localhost/view");// 回显地址
        String metaSignJsonStr = JsonUtil.STANDARD.writeValueAsString(metaSignMap);// json
        metaSignMap.put("sign", SecretUtil.MD5(
                metaSignJsonStr + DatabaseCache.getCodeBySortCodeAndCode("pay_config", "md5key").getValue(), "UTF-8"));
        return metaSignMap;
    }

    @Override
    public List<Invoice> queryInvoiceList(Invoice condition) {
        InvoiceExample example = new InvoiceExample();
        InvoiceExample.Criteria cri = example.createCriteria();
        if (!StringUtils.isEmpty(condition.getDispose_State_Code())) {
            cri.andDispose_State_CodeEqualTo(condition.getDispose_State_Code());
        }
        if (!StringUtils.isEmpty(condition.getEnterprise_No())) {
            cri.andEnterprise_NoEqualTo(condition.getEnterprise_No());
        }
        if (condition.getEnterprise_User_Id() != null) {
            cri.andEnterprise_User_IdEqualTo(condition.getEnterprise_User_Id());
        }
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        example.setOrderByClause(" id desc");
        example.setPagination(condition.getPagination());
        return invoiceDAO.selectByExamplePage(example);
    }

    @Override
    public Invoice addInvoice(Invoice invoice) throws Exception {
        if (StringUtils.isEmpty(invoice.getInvoice_Type_Code())) {
            throw new ServiceException("发票类型必填！");
        }
        if (InvoiceType.DEDICATED.value().equals(invoice.getInvoice_Type_Code())) {
            if (StringUtils.isEmpty(invoice.getOpening_Bank())) {
                throw new ServiceException("增值税专票开户行必填！");
            }
            if (StringUtils.isEmpty(invoice.getBank_Account())) {
                throw new ServiceException("增值税专票开户行账户必填！");
            }
        }
        invoice.setDispose_State_Code(DisposeStateCode.UNDISPOSED.value());
        invoice.setCreate_Date(new Date());
        invoiceDAO.insert(invoice);
        return invoice;
    }

    @Override
    public Invoice editInvoice(Invoice invoice) throws Exception {
        if (invoice.getId() == null) {
            throw new ServiceException("发票id不能为空！");
        }
        if (DisposeStateCode.REFUSE.value().equals(invoice.getDispose_State_Code())
                && StringUtils.isEmpty(invoice.getRemark())) {
            throw new ServiceException("拒绝原因不能为空！");
        }
        invoiceDAO.updateByPrimaryKeySelective(invoice);
        return invoice;
    }

    @Override
    public double getEnterpriseUserAllBalance(EnterpriseUserExt enterpriseUserExt) {
        // Double multiple =
        // Double.valueOf(DatabaseCache.getSystemEnvByCode("multiple").getValue());
        return this.enterpriseUserExtDAO.getEnterpriseUserAllBalance(enterpriseUserExt);
    }



    @Override
    public List<Map<String, Object>> getChargeOrderListForExport(ChargeOrderExt condition) {
        ChargeOrderExample example = getChargeOrderExample(condition);
        example.setOrderByClause(" c.id desc ");
        example.setPagination(condition.getPagination());
        return this.chargeOrderExtDAO.getChargeOrderListForExport(example);
    }

    @Override
    public void exportChargeOrderList(final String path, final ChargeOrderExt bean, final ExportFileExt exportFile,
                                      final String exportType) {
        String redisKey = newThreadBefore(Constant.THREAD_TOTAL_EXPORT);// 校验
        new Thread() {
            public void run() {
                int pageSize = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "export_file_size",5000);
                Pagination firstPage = new Pagination(1, pageSize);
                List<Map<String, Object>> beanList;
                exportFile.setBatch_Id(CodeUtil.buildMsgNo());
                while (true) {
                    bean.setPagination(firstPage);
                    beanList = getChargeOrderListForExport(bean);
                    if (beanList == null || beanList.isEmpty())
                        break;
                    exportChargeOrderExcel(path, beanList, exportFile, exportType);
                    if (firstPage.getPageIndex() == firstPage.getPageCount())
                        break;
                    firstPage = new Pagination(firstPage.getPageIndex() + 1, pageSize);
                }
                newThreadAfter(redisKey);
            }
        }.start();

    }

    private void exportChargeOrderExcel(String path, List<Map<String, Object>> beanList, ExportFileExt exportFile,
                                        String exportType) {
        Object[][] titles = null;
        if (Constant.PLATFORM_FLAG_ADMIN.equals(exportType)) {
            titles = new Object[][]{{"Enterprise_Name", "企业名称", 4000}, {"Enterprise_User_Name", "充值用户", 4000},
                    {"Before_Money", "充值前金额", 4000}, {"Money", "充值金额", 4000}, {"Money_Letter", "金额大写", 4000},
                    {"Pay_Type_Code_Name", "支付方式", 3000}, {"Input_User", "充值人", 4000},
                    {"Input_Date", "充值时间", 6000}, {"Open_User", "开通人", 4000},
                    {"Open_Status_Code_Name", "开通状态", 3000}, {"Open_Date", "开通时间", 6000},
                    {"Input_Remark", "充值备注", 6000}, {"Open_Remark", "开通备注", 6000},};
        } else if (Constant.PLATFORM_FLAG_ENTERPRISE.equals(exportType)
                || Constant.PLATFORM_FLAG_AGENT.equals(exportType)) {
            titles = new Object[][]{{"Enterprise_User_Name", "充值用户", 4000}, {"Before_Money", "充值前金额", 4000},
                    {"Money", "充值金额", 4000}, {"Money_Letter", "金额大写", 4000}, {"Pay_Type_Code_Name", "支付方式", 3000},
                    {"Input_Date", "充值时间", 6000}, {"Open_Status_Code_Name", "开通状态", 3000},
                    {"Open_Date", "开通时间", 6000},};
        }
        exportAndInsert(exportFile, path, "充值记录", titles, beanList);// 导出数据
    }









    private void updateEnterpriseBalance(String enterpriseNo, BigDecimal money) {

        EnterpriseExample example = new EnterpriseExample();
        example.setDataLock(" FOR UPDATE");
        example.createCriteria().andNoEqualTo(enterpriseNo);
        Enterprise enterprise = enterpriseDAO.selectByExample(example).get(0);
        BigDecimal availableAmount = enterprise.getAvailable_Amount() == null ? new BigDecimal(0)
                : enterprise.getAvailable_Amount();
        BigDecimal usedMoney = enterprise.getUsed_Amount() == null ? new BigDecimal(0) : enterprise.getUsed_Amount();
        Enterprise updateEnterprise = new Enterprise();
        updateEnterprise.setId(enterprise.getId());
        updateEnterprise.setUsed_Amount(usedMoney.subtract(money));
        updateEnterprise.setAvailable_Amount(availableAmount.add(money));
        enterpriseDAO.updateByPrimaryKeySelective(updateEnterprise);
    }

    private void updateEnterpriseUserBalance(int userId, BigDecimal money) {
        EnterpriseUserExample example = new EnterpriseUserExample();
        example.setDataLock(" FOR UPDATE");
        example.createCriteria().andIdEqualTo(userId);
        EnterpriseUser enterpriseUser = enterpriseUserDAO.selectByExample(example).get(0);
        BigDecimal availableAmount = enterpriseUser.getAvailable_Amount() == null ? new BigDecimal(0)
                : enterpriseUser.getAvailable_Amount();
        BigDecimal usedMoney = enterpriseUser.getUsed_Amount() == null ? new BigDecimal(0)
                : enterpriseUser.getUsed_Amount();
        EnterpriseUser record = new EnterpriseUser();
        record.setId(userId);
        record.setAvailable_Amount(availableAmount.add(money));
        record.setUsed_Amount(usedMoney.subtract(money));
        enterpriseUserDAO.updateByPrimaryKeySelective(record);
    }

    private void insertSystemLog(List<String> logList) {
        SystemLog systemLog = new SystemLog();
        try {
            systemLog.setUser_Id(1);
            systemLog.setReal_Name("系统");
            systemLog.setUser_Name("system");
            systemLog.setModule_Name("定时任务");
            systemLog.setCreate_Date(new Date());
            systemLog.setOperate_Desc("定时返还数据：");
            String desc = JsonUtil.STANDARD.writeValueAsString(logList);
            if (desc.length() > 5000) {
                desc = desc.substring(0,5000);
            }
            systemLog.setSpecific_Desc(desc);
            systemLogDAO.insert(systemLog);
        }  catch (Exception e) {
            e.printStackTrace();
            SuperLogger.error(e);
        }
    }

    // 封装 ChargeOrder 用户划拨
    private ChargeOrder packageChargeOrderUser2User(ChargeOrder chargeOrder) {
        chargeOrder.setCreate_Date(new Date());
        chargeOrder.setPay_Type_Code(ChargeType.USER2USER.toString());// 余额划拨
        chargeOrder.setOrder_No(CodeUtil.buildNo());
        chargeOrder.setApprove_Status_Code(Constant.RECHARGE_AGREE);
        chargeOrder.setApprove_User("system");
        chargeOrder.setApprove_Remark("默认");
        chargeOrder.setApprove_Date(new Date());
        chargeOrder.setOpen_Status_Code(Constant.RECHARGE_AGREE);
        chargeOrder.setOpen_User("system");
        chargeOrder.setOpen_Remark("余额划拨免审开通");
        chargeOrder.setOpen_Date(new Date());
        chargeOrder.setFinancial_Confirm(1);
        return chargeOrder;
    }

}
