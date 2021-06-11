package com.hero.wireless.web.service.base;


import com.drondea.wireless.util.ServiceException;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.*;
import com.hero.wireless.web.dao.business.ext.IBlackListExtDAO;
import com.hero.wireless.web.dao.business.ext.ICodeExtDAO;
import com.hero.wireless.web.dao.business.ext.ISmsRouteExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportExtDAO;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.List;


public class BaseBusinessManage extends BaseService {
    @Resource(name = "IBlackListDAO")
    protected IBlackListDAO<BlackList> blackListDAO;
    @Resource(name = "ICodeSortDAO")
    protected ICodeSortDAO<CodeSort> codeSortDAO;
    @Resource(name = "codeExtDAO")
    protected ICodeExtDAO codeExtDAO;
    @Resource(name = "ICodeDAO")
    protected ICodeDAO<? extends Code> codeDAO;
    @Resource(name = "ISensitiveWordDAO")
    protected ISensitiveWordDAO<SensitiveWord> sensitiveWordDAO;
    @Resource(name = "IMobileAreaDAO")
    protected IMobileAreaDAO<MobileArea> mobileAreaDAO;
    @Resource(name = "ISmsRouteDAO")
    protected ISmsRouteDAO<SmsRoute> smsRouteDAO;
    @Resource(name = "smsRouteExtDAO")
    protected ISmsRouteExtDAO smsRouteExtDAO;
    @Resource(name = "blackListExtDAO")
    protected IBlackListExtDAO blackListExtDAO;
    @Resource(name = "IWhiteListDAO")
    protected IWhiteListDAO<WhiteList> whiteListDAO;
    @Resource(name = "IInterceptStrategyDAO")
    protected IInterceptStrategyDAO<InterceptStrategy> interceptStrategyDAO;
    @Resource(name = "IAlarmDAO")
    protected IAlarmDAO<Alarm> alarmDAO;
    @Resource(name = "IEnterpriseUserDAO")
    protected IEnterpriseUserDAO<EnterpriseUser> enterpriseUserDAO;
    @Resource(name = "IExportFileDAO")
    protected IExportFileDAO<ExportFile> exportFileDAO;
    @Resource(name = "IProductChannelsDiversionDAO")
    protected IProductChannelsDiversionDAO<ProductChannelsDiversion> productChannelsDiversionDAO;

    @Resource(name = "IPropertiesDAO")
    protected IPropertiesDAO<Properties> propertiesDAO;
    @Resource
    protected ISystemLogDAO<SystemLog> systemLogDAO;
    @Resource(name = "reportExtDAO")
    protected IReportExtDAO reportExtDAO;
    @Resource
    protected IAlarmLogDAO<AlarmLog> alarmLogDAO;


    protected CodeSortExample assemblyCodeSortConditon(CodeSort condition) {
        CodeSortExample example = new CodeSortExample();
        CodeSortExample.Criteria cri = example.createCriteria();
        if (condition.getId() != null) {
            cri.andIdEqualTo(condition.getId());
        }
        if (!StringUtils.isEmpty(condition.getName())) {
            cri.andNameLike("%" + condition.getName() + "%");
        }
        if (!StringUtils.isEmpty(condition.getCode())) {
            cri.andCodeEqualTo(condition.getCode());
        }
        return example;
    }

    protected ProductChannelsExample assemblyProductChannelsListConditon(ProductChannels productChannels) {
        ProductChannelsExample example = new ProductChannelsExample();
        ProductChannelsExample.Criteria criteria = example.createCriteria();
        if (null != productChannels.getId()) {
            criteria.andIdEqualTo(productChannels.getId());
        }
        if (StringUtils.isNotBlank(productChannels.getProduct_No())) {
            criteria.andProduct_NoEqualTo(productChannels.getProduct_No());
        }
        if (null != productChannels.getWeight()) {
            criteria.andWeightEqualTo(productChannels.getWeight());
        }
        if (StringUtils.isNotBlank(productChannels.getChannel_No())) {
            criteria.andChannel_NoEqualTo(productChannels.getChannel_No());
        }
        if (StringUtils.isNotBlank(productChannels.getOperator())) {
            criteria.andOperatorEqualTo(productChannels.getOperator());
        }
        if (StringUtils.isNotBlank(productChannels.getMessage_Type_Code())) {
            criteria.andMessage_Type_CodeEqualTo(productChannels.getMessage_Type_Code());
        }
        if (StringUtils.isNotBlank(productChannels.getCountry_Number())) {
            criteria.andCountry_NumberEqualTo(productChannels.getCountry_Number());
        }
        return example;
    }

    protected WhiteListExample assemblyAddWhiteListConditon(WhiteList whiteList) {
        WhiteListExample example = new WhiteListExample();
        WhiteListExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(whiteList.getPhone_No())) {
            criteria.andPhone_NoEqualTo(whiteList.getPhone_No());
        }
        if (StringUtils.isNotBlank(whiteList.getEnterprise_No())) {
            criteria.andEnterprise_NoEqualTo(whiteList.getEnterprise_No());
        } else {
            criteria.andEnterprise_NoEqualTo("");
        }
        if (StringUtils.isNotBlank(whiteList.getPool_Code())) {
            criteria.andPool_CodeEqualTo(whiteList.getPool_Code());
        } else {
            criteria.andPool_CodeEqualTo("");
        }
        return example;
    }

    protected WhiteListExample assemblyWhiteListConditon(WhiteList whiteList) {
        WhiteListExample example = new WhiteListExample();
        WhiteListExample.Criteria criteria = example.createCriteria();
        if (null != whiteList.getId()) {
            criteria.andIdEqualTo(whiteList.getId());
        }
        if (StringUtils.isNotBlank(whiteList.getPhone_No())) {
            criteria.andPhone_NoEqualTo(whiteList.getPhone_No());
        }
        if (StringUtils.isNotBlank(whiteList.getEnterprise_No())) {
            criteria.andEnterprise_NoEqualTo(whiteList.getEnterprise_No());
        }
        if (StringUtils.isNotBlank(whiteList.getPool_Code())) {
            criteria.andPool_CodeEqualTo(whiteList.getPool_Code());
        }
        if (StringUtils.isNotBlank(whiteList.getChannel_No())) {
            criteria.andChannel_NoEqualTo(whiteList.getChannel_No());
        }
        if (StringUtils.isNotBlank(whiteList.getRoute_Name_Code())) {
            criteria.andRoute_Name_CodeEqualTo(whiteList.getRoute_Name_Code());
        }
        if (StringUtils.isNotBlank(whiteList.getCreate_User())) {
            criteria.andCreate_UserEqualTo(whiteList.getCreate_User());
        }
        if (null != whiteList.getCreate_User_Id()) {
            criteria.andCreate_User_IdEqualTo(whiteList.getCreate_User_Id());
        }
        if (StringUtils.isNotBlank(whiteList.getRemark())) {
            criteria.andRemarkEqualTo(whiteList.getRemark());
        }
        return example;
    }

    protected BlackListExample assemblyBlackListConditon(BlackList blackList) {
        BlackListExample example = new BlackListExample();
        BlackListExample.Criteria criteria = example.createCriteria();
        if (null != blackList.getId()) {
            criteria.andIdEqualTo(blackList.getId());
        }
        if (StringUtils.isNotBlank(blackList.getEnterprise_No())) {
            criteria.andEnterprise_NoEqualTo(blackList.getEnterprise_No());
        }
        if (null != blackList.getEnterprise_User_Id()) {
            criteria.andEnterprise_User_IdEqualTo(blackList.getEnterprise_User_Id());
        }
        if (StringUtils.isNotBlank(blackList.getChannel_No())) {
            criteria.andChannel_NoEqualTo(blackList.getChannel_No());
        }
        if (StringUtils.isNotBlank(blackList.getPhone_No())) {
            criteria.andPhone_NoEqualTo(blackList.getPhone_No());
        }
        if (StringUtils.isNotBlank(blackList.getPool_Code())) {
            criteria.andPool_CodeEqualTo(blackList.getPool_Code());
        }
        if (StringUtils.isNotBlank(blackList.getRoute_Name_Code())) {
            criteria.andRoute_Name_CodeEqualTo(blackList.getRoute_Name_Code());
        }
        if (StringUtils.isNotBlank(blackList.getTrade_Type_Code())) {
            criteria.andTrade_Type_CodeEqualTo(blackList.getTrade_Type_Code());
        }
        if (StringUtils.isNotBlank(blackList.getCreate_User())) {
            criteria.andCreate_UserEqualTo(blackList.getCreate_User());
        }
        if (StringUtils.isNotBlank(blackList.getDescription())) {
            criteria.andDescriptionEqualTo(blackList.getDescription());
        }
        if (StringUtils.isNotBlank(blackList.getRemark())) {
            criteria.andRemarkEqualTo(blackList.getRemark());
        }
        return example;
    }

    protected void verifyAlarm(AlarmExt alarmExt) {
        if(alarmExt.getMax_Alarm_Total() == null){
            throw new ServiceException("请设置告警次数！");
        }
        if(StringUtils.isEmpty(alarmExt.getType_Code())){
            throw new ServiceException("请选择告警类型！");
        }
        if(alarmExt.getProbe_Time() == null){
            throw new ServiceException("请选择告警时间间隔！");
        }
        if(StringUtils.isEmpty(alarmExt.getThreshold_Value())){
            throw new ServiceException("请设置告警阈值！");
        }
        String typeCode = alarmExt.getType_Code();
        if(typeCode.startsWith("account")||typeCode.startsWith("user")){
            if(alarmExt.getBind_Value_User_Id() == null){
                throw new ServiceException("请选择告警用户！");
            }
            EnterpriseUser enterpriseUser = this.enterpriseUserDAO.selectByPrimaryKey(alarmExt.getBind_Value_User_Id());
            alarmExt.setBind_Value(enterpriseUser.getUser_Name());
        }else if(typeCode.startsWith("user")) {
            LocalTime starTime = LocalTime.parse(alarmExt.getMonitorStartTime());
            LocalTime endTime = LocalTime.parse(alarmExt.getMonitorEndTime());
            if(starTime.plusMinutes(alarmExt.getProbe_Time()).isAfter(endTime)){
                throw new ServiceException("预警周期设置过大！");
            }
        }else if(typeCode.startsWith("channel")){
            if(StringUtils.isEmpty(alarmExt.getBind_Value_Channel_No())){
                throw new ServiceException("请选择告警通道！");
            }
            alarmExt.setBind_Value(alarmExt.getBind_Value_Channel_No());
        }else if(typeCode.startsWith("server")){
            if(StringUtils.isEmpty(alarmExt.getBind_Value_Server_Ip())){
                throw new ServiceException("请输入服务器IP！");
            }
            alarmExt.setBind_Value(alarmExt.getBind_Value_Server_Ip());
        }else if(typeCode.startsWith("product")){
            if(StringUtils.isEmpty(alarmExt.getBind_Value_Product_No())){
                throw new ServiceException("请选择告警产品！");
            }
            alarmExt.setBind_Value(alarmExt.getBind_Value_Product_No());
        }else if(typeCode.startsWith("sended")){
            if(alarmExt.getBind_Value_Input_Id() == null){
                throw new ServiceException("请选择告警审核用户！");
            }
            alarmExt.setBind_Value(alarmExt.getBind_Value_Input_Id());
        }
    }

    protected void initSelect(AlarmExt alarm) {
        try {
            String typeCode = alarm.getType_Code();
            if(typeCode.startsWith("account") ||typeCode.startsWith("user")){
                EnterpriseUserExample userExample = new EnterpriseUserExample();
                userExample.createCriteria().andUser_NameEqualTo(alarm.getBind_Value()+"");//防止null
                alarm.setBind_Value_User_Id(this.enterpriseUserDAO.selectByExample(userExample).get(0).getId());
            }else if(typeCode.startsWith("channel")){
                alarm.setBind_Value_Channel_No(alarm.getBind_Value());
            }else if(typeCode.startsWith("server")){
                alarm.setBind_Value_Server_Ip(alarm.getBind_Value());
            }else if(typeCode.startsWith("product")){
                alarm.setBind_Value_Product_No(alarm.getBind_Value());
            }else if(typeCode.startsWith("sended")){
                alarm.setBind_Value_Input_Id(alarm.getBind_Value());
            }
        }catch (Exception e){
        }
    }


}
