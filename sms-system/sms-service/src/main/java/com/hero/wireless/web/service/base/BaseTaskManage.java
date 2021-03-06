package com.hero.wireless.web.service.base;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.dao.business.IChargeOrderDAO;
import com.hero.wireless.web.dao.business.ISmsStatisticsDAO;
import com.hero.wireless.web.dao.business.ext.ISmsStatisticsExtDAO;
import com.hero.wireless.web.dao.send.IInputLogDAO;
import com.hero.wireless.web.dao.send.IReportDAO;
import com.hero.wireless.web.dao.send.ISubmitDAO;
import com.hero.wireless.web.dao.send.ext.IInputLogExtDAO;
import com.hero.wireless.web.dao.send.ext.IReportExtDAO;
import com.hero.wireless.web.dao.send.ext.ISubmitExtDAO;
import com.hero.wireless.web.entity.base.Pagination;
import com.hero.wireless.web.entity.business.ChargeOrder;
import com.hero.wireless.web.entity.business.ChargeOrderExample;
import com.hero.wireless.web.entity.business.SmsStatistics;
import com.hero.wireless.web.entity.business.SmsStatisticsExample;
import com.hero.wireless.web.entity.business.ext.SmsStatisticsExt;
import com.hero.wireless.web.entity.send.InputLog;
import com.hero.wireless.web.entity.send.Report;
import com.hero.wireless.web.entity.send.Submit;
import com.hero.wireless.web.service.IChargeManage;
import com.hero.wireless.web.service.IStatisticsManage;
import com.hero.wireless.web.service.param.TaskParam;
import com.hero.wireless.web.util.GlobalRepeat;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class BaseTaskManage extends BaseService {

    @Resource(name = "statisticsManage")
    protected IStatisticsManage statisticsManage;
    @Resource(name = "chargeManage")
    protected IChargeManage chargeManage;
    @Resource(name = "ISmsStatisticsDAO")
    protected ISmsStatisticsDAO<SmsStatistics> smsStatisticsDAO;
    @Resource(name = "ISmsStatisticsExtDAO")
    protected ISmsStatisticsExtDAO smsStatisticsExtDAO;
    @Resource(name = "IInputLogDAO")
    protected IInputLogDAO<InputLog> inputLogDAO;
    @Resource(name = "inputLogExtDAO")
    protected IInputLogExtDAO inputLogExtDAO;
    @Resource(name = "ISubmitDAO")
    protected ISubmitDAO<Submit> submitDAO;
    @Resource(name = "submitExtDAO")
    protected ISubmitExtDAO submitExtDAO;
    @Resource(name = "IReportDAO")
    protected IReportDAO<Report> reportDAO;
    @Resource(name = "reportExtDAO")
    protected IReportExtDAO reportExtDAO;
    @Resource(name = "IChargeOrderDAO")
    protected IChargeOrderDAO<ChargeOrder> chargeOrderDAO;

    protected void checkSmsStatisticsData(TaskParam task) {
        if (StringUtils.isEmpty(task.getDatabase())) {
            throw new ServiceException("??????????????????????????????");
        }
        SmsStatisticsExample example = new SmsStatisticsExample();
        SmsStatisticsExample.Criteria criteria = example.createCriteria();
        criteria.andStatistics_DateBetween(task.getMinDate(), task.getMaxDate());
        example.setPagination(new Pagination(1, 1));//?????????????????????????????????
        List list = smsStatisticsDAO.selectByExample(example);
        if (list != null && list.size() > 0) {
            throw new ServiceException("????????????????????????????????????????????????");
        }
    }

    protected void checkSmsMigratData(TaskParam task) {
        if (StringUtils.isEmpty(task.getTable())) {
            throw new ServiceException("??????????????????????????????????????????");
        }
    }

    protected void checkSmsSendFailed(TaskParam task) {
        ChargeOrderExample example = new ChargeOrderExample();
        example.createCriteria()
                .andCreate_DateBetween(task.getMinDate(), task.getMaxDate())
                .andPay_Type_CodeEqualTo("failedtoreturn");
        example.setPagination(new Pagination(1, 1));
        List<ChargeOrder> list = chargeOrderDAO.selectByExample(example);
        if (list != null && list.size() > 0) {
            throw new ServiceException("????????????????????????????????????????????????");
        }
    }

    /**
     * ???????????????????????????????????????????????? ?????????????????????
     *
     * @param date ????????????
     */
    protected int doSmsSendFailed(String date) {
        try {
            if(!GlobalRepeat.getAndSet("doSmsSendFailed")){
                SuperLogger.error("??????????????????????????????" );
                return 0;
            }
            SmsStatisticsExt smsStatisticsExt = new SmsStatisticsExt();
            smsStatisticsExt.setStatisticsDateStr(date);
            List<SmsStatisticsExt> smsStatistics = statisticsManage.querySmsSendFailedByExt(smsStatisticsExt);
            if (ObjectUtils.isNotEmpty(smsStatistics)) {
                this.chargeManage.chargeOrderBySmsStatisticsListTran(smsStatistics);
            }
            return smsStatistics.size();
        } catch (Exception e) {
            SuperLogger.error("??????????????????", e);
        } finally{
            GlobalRepeat.remove("doSmsSendFailed");
        }
        return 0;
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     * <p>
     * 1?????????????????????????????????0??? <br>
     * 2????????????30?????? <br>
     * 3?????????????????? <br>
     * 4?????????????????????????????????map??? <br>
     * 5?????????????????? <br>
     * 6???????????????????????????????????????
     *
     * @param startDate ????????????
     * @param endDate   ????????????
     * @param database  ??????????????????DataBaseType ??????
     */
    protected int doSmsStatisticsData(Date startDate, Date endDate, String database) {
        if (startDate == null || endDate == null || StringUtils.isEmpty(database)) {
            SuperLogger.error("??????????????????ERROR???" + startDate + endDate + database);
            return 0;
        }
        if(!GlobalRepeat.getAndSet("doSmsStatisticsData")){
            SuperLogger.error("??????????????????????????????" );
            return 0;
        }
        int timeInterval = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "timer_statistics_time_interval", 1200);
        List<DateTime.DateInterVal> dateList = DateTime.getIntervalDate(timeInterval, startDate, endDate);
        List<Tasks> tasks = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            DateTime.DateInterVal dateInterVal = dateList.get(i);
            tasks.add(new Tasks(dateInterVal.getBegin(), dateInterVal.getEnd(), database));
        }
        List<SmsStatistics> dataList = new ArrayList<>();
        // ???????????????????????????????????????10????????????
        ExecutorService statisticsDataFixedPool = Executors.newFixedThreadPool(1);
        try {
            List<Future<Map<String, SmsStatistics>>> resultList = statisticsDataFixedPool.invokeAll(tasks);
            if (resultList.size() > 0) {
                Map<String, SmsStatistics> resultMap = new ConcurrentHashMap<>();
                //?????????????????????????????????
                for (Future<Map<String, SmsStatistics>> future : resultList) {
                    Map<String, SmsStatistics> tempMap = future.get();
                    tempMap.forEach((k, v) -> {
                        SmsStatistics smsStatistics = resultMap.get(k);
                        if (smsStatistics != null) {
                            v.setSubmit_Total(v.getSubmit_Total() + smsStatistics.getSubmit_Total());
                            v.setSubmit_Success_Total(v.getSubmit_Success_Total() + smsStatistics.getSubmit_Success_Total());
                            v.setSubmit_Faild_Total(v.getSubmit_Faild_Total() + smsStatistics.getSubmit_Faild_Total());
                            v.setSort_Faild_Total(v.getSort_Faild_Total() + smsStatistics.getSort_Faild_Total());
                            v.setSend_Success_Total(v.getSend_Success_Total() + smsStatistics.getSend_Success_Total());
                            v.setSend_Faild_Total(v.getSend_Faild_Total() + smsStatistics.getSend_Faild_Total());
                            v.setProfits(decimalAdd(v.getProfits(), smsStatistics.getProfits()));
                            v.setAgent_Profits(decimalAdd(v.getAgent_Profits(), smsStatistics.getAgent_Profits()));
                            v.setAgent_Unit_Cost(decimalAdd(v.getAgent_Unit_Cost(), smsStatistics.getAgent_Unit_Cost()));
                            v.setUnit_Cost(decimalAdd(v.getUnit_Cost(), smsStatistics.getUnit_Cost()));
                            v.setChannel_Taxes(decimalAdd(v.getChannel_Taxes(), smsStatistics.getChannel_Taxes()));
                            v.setEnterprise_User_Taxes(decimalAdd(v.getEnterprise_User_Taxes(), smsStatistics.getEnterprise_User_Taxes()));
                        }
                        resultMap.put(k, v);
                    });
                }

                resultMap.forEach((k, v) -> {
                    int unknownTotal = v.getSubmit_Success_Total() - v.getSend_Success_Total() - v.getSend_Faild_Total();
                    v.setSend_Unknown_Total(unknownTotal);
                    BigDecimal sendSuccessSmsTotal = new BigDecimal(v.getSend_Success_Total());
                    BigDecimal sendFailedSmsTotal = new BigDecimal(v.getSend_Faild_Total());
                    BigDecimal submitSuccessSmsTotal = new BigDecimal(v.getSubmit_Success_Total() <= 0 ? 1 : v.getSubmit_Success_Total());
                    v.setSuccess_Rate(sendSuccessSmsTotal.divide(submitSuccessSmsTotal, 4, BigDecimal.ROUND_HALF_UP));
                    v.setFailure_Rate(sendFailedSmsTotal.divide(submitSuccessSmsTotal, 4, BigDecimal.ROUND_HALF_UP));
                    dataList.add(v);
                });

                if (dataList.size() > 0) {
                    int selectMax = DatabaseCache.getIntValueBySortCodeAndCode("sys_performance_setup", "insert_max_count", 3000);
                    ListUtils.partition(dataList, selectMax).forEach(item -> {
                        smsStatisticsExtDAO.insertList(item);
                    });
                }
            }
        } catch (Exception e) {
            SuperLogger.error("?????????????????????????????????" + e.getMessage(), e);
        } finally {
            statisticsDataFixedPool.shutdown();
            //???????????????  ??????key
            GlobalRepeat.remove("doSmsStatisticsData");
        }
        return dataList.size();
    }

    private BigDecimal decimalAdd(BigDecimal one, BigDecimal two) {
        return ObjectUtils.defaultIfNull(one, BigDecimal.ZERO).add(ObjectUtils.defaultIfNull(two, BigDecimal.ZERO));
    }

    class Tasks implements Callable<Map<String, SmsStatistics>> {

        private Date startDate;

        private Date endDate;

        private String databaseFlag;//??????????????????s_history???s_send

        public Tasks(Date startDate, Date endDate, String databaseFlag) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.databaseFlag = databaseFlag;
        }

        @Override
        public Map<String, SmsStatistics> call() {
            SmsStatisticsExt smsStatisticsExt = new SmsStatisticsExt();
            smsStatisticsExt.setMinCreateDate(startDate);
            smsStatisticsExt.setMaxCreateDate(endDate);
            List<SmsStatistics> submitList = submitExtDAO.getSubmitSmsStatisticsByExt(smsStatisticsExt);
            List<SmsStatistics> reportList = reportExtDAO.getReportSmsStatisticsByExt(smsStatisticsExt);

            Map<String, SmsStatistics> totalList = getTotalSmsStatistics(submitList, reportList);
            return totalList;
        }

        // ?????????????????????????????????submit???report????????????
        private Map<String, SmsStatistics> getTotalSmsStatistics(
                List<SmsStatistics> submitList, List<SmsStatistics> reportList) {
            Map<String, SmsStatistics> submitMap = submitList.stream().collect(Collectors.toMap(item -> makeStatisticsKey(item), s -> s));
            Map<String, SmsStatistics> reportMap = reportList.stream().collect(Collectors.toConcurrentMap(item -> makeStatisticsKey(item), s -> s));
            submitMap.forEach((k, v) -> {
                if (reportMap.containsKey(k)) {
                    v.setSend_Success_Total(reportMap.get(k).getSend_Success_Total());
                    v.setSend_Faild_Total(reportMap.get(k).getSend_Faild_Total());
                    reportMap.remove(k);
                }
                submitMap.put(k, v);
            });
            //??????????????????????????????
            reportMap.forEach((k, v) -> {
                submitMap.put(k, v);
            });
            return submitMap;
        }
    }

    private String makeStatisticsKey(SmsStatistics smsStatistics) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(smsStatistics.getEnterprise_User_Id()).append(smsStatistics.getProvince_Code())
                .append(smsStatistics.getEnterprise_No()).append(smsStatistics.getChannel_No())
                .append(smsStatistics.getOperator()).append((smsStatistics.getBusiness_User_Id() == null ? "" : smsStatistics.getBusiness_User_Id()))
                .append(smsStatistics.getAgent_No()).append(smsStatistics.getMessage_Type_Code())
                .append(smsStatistics.getEnterprise_User_Unit_Price()).append(smsStatistics.getSignature() == null ? "" : smsStatistics.getSignature());
        return stringBuffer.toString();
    }
}