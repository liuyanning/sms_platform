package com.hero.wireless.netway.controller;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.wireless.http.IHttp.ChannelReport;
import com.hero.wireless.http.connector.AliImpl;
import com.hero.wireless.notify.AliMo;
import com.hero.wireless.notify.AliReport;
import com.hero.wireless.notify.AliResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ali")
public class AliController {
    @ResponseBody
    @RequestMapping("/{channelNo}/report")
    public String report(@RequestBody String jsonArray, @PathVariable("channelNo") String channelNo) {
        try {
            SuperLogger.debug(jsonArray);
            ObjectMapper mapper = new ObjectMapper();
            List<AliReport> aliReportList = mapper.readValue(jsonArray,new TypeReference<List<AliReport>>() { });
            AliReport base = new AliReport();
            base.setAliReportList(aliReportList);
            new AliImpl().report(new ChannelReport(channelNo, base));
            return (new AliResponse(0, "接收成功").toJson());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new AliResponse(1, "接收失败").toJson());
        }
    }

    @ResponseBody
    @RequestMapping("/{channelNo}/mo")
    public String mo(@RequestBody String jsonArray, @PathVariable("channelNo") String channelNo) {
        try {
            SuperLogger.debug(jsonArray);
            ObjectMapper mapper = new ObjectMapper();
            List<AliMo> aliMoList = mapper.readValue(jsonArray,new TypeReference<List<AliMo>>() { });
            AliMo base = new AliMo();
            base.setAliMoList(aliMoList);
            new AliImpl().mo(new ChannelReport(channelNo, base));
            return (new AliResponse(0, "接收成功").toJson());
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
            return (new AliResponse(1, "接收失败").toJson());
        }
    }

}
