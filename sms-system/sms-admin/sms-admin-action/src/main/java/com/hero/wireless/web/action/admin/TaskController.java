package com.hero.wireless.web.action.admin;

import com.drondea.wireless.util.ServiceException;
import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.json.LayUiJsonObjectFmt;
import com.hero.wireless.json.LayuiResultUtil;
import com.hero.wireless.web.action.BaseAdminController;
import com.hero.wireless.web.action.interceptor.AvoidRepeatableCommitAnnotation;
import com.hero.wireless.web.action.interceptor.OperateAnnotation;
import com.hero.wireless.web.service.ITaskManage;
import com.hero.wireless.web.service.param.TaskParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/admin/")
public class TaskController extends BaseAdminController {

    @Resource(name = "taskManage")
    private ITaskManage taskManage;

    /**
     * 重新执行定时
     * @param task
     * @return
     */
    @RequestMapping("task_refreshTask")
    @AvoidRepeatableCommitAnnotation(systemModuleName = ADMIN_PLATFORM + "task_refreshTask")
    @OperateAnnotation(moduleName = "系统设置", option = "定时执行")
    @ResponseBody
    public LayUiJsonObjectFmt refreshTask(TaskParam task) {
        try {
            checkParam(task);
            SuperLogger.error("==============重新执行定时任务："+task.getTaskName());
            switch(task.getTaskName()){
                case "smsStatisticsData"://数据汇总
                    taskManage.smsStatisticsDataFromWeb(task);
                    break;
                case "smsSendFailed"://失败返还
                    taskManage.smsSendFailedFromWeb(task);
                    break;
            }
            return LayuiResultUtil.success();
        }catch (ServiceException e){
            return LayuiResultUtil.fail(e.getMessage());
        }catch (Exception e){
            SuperLogger.error(e.getMessage(),e);
            return LayuiResultUtil.error(e);
        }
    }

    //数据校验
    private void checkParam(TaskParam task) {
        if(StringUtils.isEmpty(task.getTaskName())){
            throw new ServiceException("请选择定时任务");
        }
        if(StringUtils.isEmpty(task.getDate())){
            throw new ServiceException("请选择操作日期");
        }
    }

}
