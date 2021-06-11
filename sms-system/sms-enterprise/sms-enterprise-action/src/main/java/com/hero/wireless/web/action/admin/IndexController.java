package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.DateTime;
import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.web.action.BaseEnterpriseController;
import com.hero.wireless.web.action.admin.config.MenuInfo;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.config.SystemKey;
import com.hero.wireless.web.entity.business.*;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.business.ext.SmsRealTimeStatisticsExt;
import com.hero.wireless.web.entity.ext.SqlStatisticsEntity;
import com.hero.wireless.web.entity.send.ext.InputLogExt;
import com.hero.wireless.web.entity.send.ext.ReportExt;
import com.hero.wireless.web.entity.send.ext.SubmitExt;
import com.hero.wireless.web.service.ISendManage;
import com.hero.wireless.web.service.IStatisticsManage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class IndexController extends BaseEnterpriseController {
    @Resource(name = "statisticsManage")
    private IStatisticsManage statisticsManage;
    @Resource(name = "sendManage")
    private ISendManage sendManage;
    @RequestMapping("/initMenu")
    @ResponseBody
    public Map<String,Object> initMenu() {
        Map<String,Object> menuInfoMap = new HashMap();

        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setTitle("首页");
        menuInfo.setHref("/admin/home/console.jsp");
        menuInfoMap.put( "homeInfo", menuInfo);

        menuInfo = new MenuInfo();
        String menuTitle = DatabaseCache.getStringValueBySortCodeAndCode("page_configuration", "enterprise_menu_title", "菜单目录");
        menuInfo.setTitle(menuTitle);
        Code code = DatabaseCache.getCodeBySortCodeAndCode("page_configuration", "enterprise_menu_image");
        if(code!=null){
            String menuImage  = code.getValue();
            menuInfo.setImage(menuImage);
        }
        menuInfoMap.put( "logoInfo", menuInfo);

        HttpSession session = request.getSession();
        EnterpriseUserExt users = (EnterpriseUserExt) session
                .getAttribute(SystemKey.ADMIN_USER.toString());
        List<EnterpriseLimit> limitList = users.getLimits();

        //菜单
        List<MenuInfo> menuInfoList = new ArrayList<>();
        MenuInfo menuInfoBase = new MenuInfo();;
        List<MenuInfo> child = new ArrayList<>();
        for (int i = 0; i < limitList.size(); i++) {
            if(limitList.get(i).getType_Code()!=null && limitList.get(i).getType_Code().equals("menu")){
                menuInfo = new MenuInfo();
                menuInfo.setTitle(limitList.get(i).getName());
                menuInfo.setHref(limitList.get(i).getUrl());
                menuInfo.setIcon(limitList.get(i).getIcon()==null ? "fa fa-bars":limitList.get(i).getIcon());
                menuInfo.setCode(limitList.get(i).getCode());
                menuInfo.setTarget("_self");
                if(limitList.get(i).getUp_Code().equals("00")){
                    if( menuInfoBase.getTitle()!=null){
                        menuInfoBase.setChild(child);
                        menuInfoList.add(menuInfoBase);
                        menuInfoBase = menuInfo;
                        child = new ArrayList<>();
                    }else{
                        menuInfoBase = menuInfo;
                    }
                }else{
                    child.add(menuInfo);
                }
            }

        }
        menuInfoBase.setChild(child);
        menuInfoList.add(menuInfoBase);

        menuInfoMap.put("menuInfo",menuInfoList);

        return menuInfoMap;
    }
    @RequestMapping("/index_currentOperatorSendMapData")
    @ResponseBody
    public String index_currentOperatorSendMapData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SmsRealTimeStatisticsExt statisticsExt = new SmsRealTimeStatisticsExt();
            statisticsExt.setEnterprise_User_Id(getUserId());
            statisticsExt.setMin_Submit_Date(sdf.parse(DateTime.getCurrentDayMinDate()));
            statisticsExt.setGroupStr("Operator");
            List<SmsRealTimeStatisticsExt> smsRealTimeStatisticsExts = statisticsManage.querySmsRealTimeStatisticsGroupDataList(statisticsExt);
            for (SmsRealTimeStatisticsExt source: smsRealTimeStatisticsExts){
                Map<String, Object> map = new HashMap<>();
                map.put("count", source.getSubmit_Success_Total());
                map.put("successCount", source.getSend_Success_Total());
                map.put("operator", source.getOperator());
                list.add(map);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            return new LayUiObjectMapper().asSuccessString(list);
        }
    }
    @RequestMapping("/index_currentDataMap")
    @ResponseBody
    public Map currentDataMap() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        double cost = 0;
        int submitTotal = 0;
        int sendSuccessTotal = 0;
        ReportExt reportExt = new ReportExt();
        reportExt.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
        reportExt.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
        reportExt.setEnterprise_No(getLoginEnterprise().getNo());
        reportExt.setEnterprise_User_Id(getUserId());
        SqlStatisticsEntity reportStatistics = sendManage.queryReportListTotalData(reportExt);

        SubmitExt submitExt = new SubmitExt();
        submitExt.setMinSubmitDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
        submitExt.setMaxSubmitDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
        submitExt.setEnterprise_No(getLoginEnterprise().getNo());
        submitExt.setEnterprise_User_Id(getUserId());
        SqlStatisticsEntity submitStatistics = sendManage.querySubmitListTotalData(submitExt);

        InputLogExt inputLogExt = new InputLogExt();
        inputLogExt.setMinCreateDate(DateTime.getDate(DateTime.getCurrentDayMinDate()));
        inputLogExt.setMaxCreateDate(DateTime.getDate(DateTime.getCurrentDayMaxDate()));
        inputLogExt.setEnterprise_No(getLoginEnterprise().getNo());
        inputLogExt.setEnterprise_User_Id(getUserId());
        SqlStatisticsEntity inputLogStatistics = sendManage.queryInputLogListTotalData(inputLogExt);

        submitTotal = submitStatistics.getSubmit_Success_Total();
        sendSuccessTotal = reportStatistics.getSend_Success_Total();
        cost = inputLogStatistics.getSale_Amount()==null?0:inputLogStatistics.getSale_Amount().doubleValue();
        map.put("submitTotal", submitTotal);
        map.put("sendSuccessCount", sendSuccessTotal);
        map.put("costTotal", cost);
        return map;
    }
}
