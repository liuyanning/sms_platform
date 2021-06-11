package com.hero.wireless.netway.controller;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.http.IHttp.ChannelReport;
import com.hero.wireless.http.connector.DrondeaImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/drondea/")
public class DrondeaController {
    @ResponseBody
    @RequestMapping("report")
    public String report(@RequestBody String xml, @RequestParam String channelNo) {
        try {
            SuperLogger.debug("http返回数据：" + xml + "。channelNo is " + channelNo);
            return new DrondeaImpl().report(new ChannelReport(channelNo, xml)).toString();
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("mo")
    public String mo(@RequestBody String xml, @RequestParam String channelNo) {
        try {
            return new DrondeaImpl().mo(new ChannelReport(channelNo, xml)).toString();
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return null;
    }
}
