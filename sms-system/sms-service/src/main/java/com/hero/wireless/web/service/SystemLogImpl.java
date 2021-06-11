package com.hero.wireless.web.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.drondea.wireless.util.DateTime;
import com.hero.wireless.web.dao.business.ISystemLogDAO;
import com.hero.wireless.web.entity.business.SystemLog;
import com.hero.wireless.web.entity.business.SystemLogExample;

@Service("SystemLogManage")
public class SystemLogImpl implements ISystemLogManage {

    @Resource
    private ISystemLogDAO<SystemLog> operateLogDAO;


    @Override
    public List<SystemLog> queryLogList(SystemLog log, Map<String, String> conditionMap) {
        SystemLogExample example = new SystemLogExample();
        SystemLogExample.Criteria cri = example.createCriteria();
        example.setPagination(log.getPagination());
        example.setOrderByClause("id desc");
        if (StringUtils.isNotBlank(log.getReal_Name())) {
            cri.andReal_NameEqualTo(log.getReal_Name());
        }
        if (StringUtils.isNotBlank(log.getUser_Name())) {
            cri.andUser_NameEqualTo(log.getUser_Name());
        }
        if (StringUtils.isNotBlank(log.getModule_Name())) {
            cri.andModule_NameEqualTo(log.getModule_Name());
        }
        if (StringUtils.isNotBlank(log.getSpecific_Desc())){
            cri.andSpecific_DescLike("%" + log.getSpecific_Desc() + "%");
        }
        // 最小提交时间
        String minStatistcDate = conditionMap.get("minStatistcDate");
        // 最大提交时间
        String maxStatistcDate = conditionMap.get("maxStatistcDate");
        if (StringUtils.isBlank(minStatistcDate)) {
            minStatistcDate = new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
        }
        if (StringUtils.isBlank(maxStatistcDate)) {
            maxStatistcDate = new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(new Date());
        }
        cri.andCreate_DateGreaterThanOrEqualTo(DateTime.getDate(minStatistcDate));
        cri.andCreate_DateLessThanOrEqualTo(DateTime.getDate(maxStatistcDate));
        return operateLogDAO.selectByExamplePage(example);
    }
}
