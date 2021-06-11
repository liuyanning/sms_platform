package com.hero.wireless.web.action.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hero.wireless.json.LayUiObjectMapper;
import com.hero.wireless.web.action.BaseController;
import com.hero.wireless.web.entity.business.SystemLog;
import com.hero.wireless.web.service.ISystemLogManage;

@Controller
@RequestMapping("/admin/")
public class LogController extends BaseController {

    @Resource(name = "SystemLogManage")
    private ISystemLogManage operateLogManage;


    @RequestMapping("log_list")
    @ResponseBody
    public String queryLogList(SystemLog log, @RequestParam Map<String, String> conditionMap) {
        List<SystemLog> logList = operateLogManage.queryLogList(log, conditionMap);
        return new LayUiObjectMapper().asSuccessString(logList, log.getPagination().getTotalCount());
    }
}
