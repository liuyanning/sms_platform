package com.hero.wireless.netway.service;

import com.hero.wireless.notify.JsonBalanceResponse;
import com.hero.wireless.notify.JsonBase;
import com.hero.wireless.notify.JsonQueryResult;
import com.hero.wireless.notify.JsonSubmit;
import com.hero.wireless.web.util.QueueUtil.HttpReport;

public interface IHttpService {

    JsonBalanceResponse balance(JsonBase data) throws Exception;

    String submit(JsonSubmit data) throws Exception;

    JsonQueryResult queryMo(JsonBase data) throws Exception;

    JsonQueryResult queryMo(JsonBase data, int pageSize) throws Exception;

    JsonQueryResult queryReport(JsonBase data) throws Exception;

    JsonQueryResult queryReport(JsonBase data, int maxcount) throws Exception;

    void notifyReport(HttpReport entity) throws Exception;
}