package com.hero.wireless.netway.controller;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.http.IHttp.ChannelReport;
import com.hero.wireless.http.connector.ChuZhongImpl;
import com.hero.wireless.notify.ChuZhongSmsDeliverRequestMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Title: ChuZhongController
 * Description:触众状态通知控制类
 *
 * @author yjb
 * @date 2020-04-30
 */
@Controller
@RequestMapping("/cz")
public class ChuZhongController {
    @ResponseBody
    @RequestMapping("/{channelNo}/notify")
    public String report(ChuZhongSmsDeliverRequestMessage data, @PathVariable("channelNo") String channelNo) {
        try {
            SuperLogger.debug("http返回数据：" + data.toString() + "。channelNo is " + channelNo);
            if (data.getvType().equals("12")) {
                new ChuZhongImpl().report(new ChannelReport(channelNo, data));
            }
            if (data.getvType().equals("11")) {
                new ChuZhongImpl().mo(new ChannelReport(channelNo, data)).toString();
            }
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        }
        return "success";
    }

}
