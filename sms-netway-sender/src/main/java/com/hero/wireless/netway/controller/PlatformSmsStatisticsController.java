package com.hero.wireless.netway.controller;

import com.hero.wireless.netway.service.impl.BaseService;
import com.hero.wireless.notify.JsonResponse;
import com.hero.wireless.web.service.IStatisticsManage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Title: StatisticsController
 * Description:平台短信统计控制类
 *
 * @author lyh
 * @date 2020-06-04
 */
@Controller
public class PlatformSmsStatisticsController extends BaseService {
    @Resource(name = "statisticsManage")
    private IStatisticsManage statisticsManage;

    @RequestMapping("/platformSmsStatisticsData")
    @ResponseBody
    public JsonResponse platformSmsStatisticsData(@RequestBody Map<String, String> map) {
        try {
            this.statisticsManage.insertPlatformSmsStatisticsData(map);
            return new JsonResponse("0", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResponse("-1", e.getMessage());
        }
    }
}
