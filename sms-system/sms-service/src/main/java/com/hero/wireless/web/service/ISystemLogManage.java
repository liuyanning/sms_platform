package com.hero.wireless.web.service;

import java.util.List;
import java.util.Map;

import com.hero.wireless.web.entity.business.SystemLog;

public interface ISystemLogManage {

    /**
     * 日志列表
     *
     * @param log
     * @param conditionMap
     * @return
     */
    List<SystemLog> queryLogList(SystemLog log, Map<String, String> conditionMap);
}
