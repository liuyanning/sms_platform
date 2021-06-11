package com.hero.wireless.web.util;

import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.business.ext.EnterpriseUserExt;
import com.hero.wireless.web.entity.send.*;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @version V3.0.0
 * @description: 拷贝全局缓存类
 * @author: 刘彦宁
 * @date: 2020年11月19日11:31
 **/
public class CopyUtil {
    public static final BeanCopier SUBMIT_COPIER = BeanCopier.create(Submit.class, Submit.class, false);

    public static final BeanCopier REPORT_REPORTNOTIFY_COPIER = BeanCopier.create(Report.class, ReportNotify.class, false);

    public static final BeanCopier USER_USEREXT_COPIER = BeanCopier.create(EnterpriseUser.class, EnterpriseUserExt.class, false);

    public static final BeanCopier SUBMIT_REPORT_COPIER = BeanCopier.create(Submit.class, Report.class, false);

    public static final BeanCopier INPUT_INPUTLOG_COPIER = BeanCopier.create(Input.class, InputLog.class, false);

    public static final BeanCopier INPUT_SELF_COPIER = BeanCopier.create(Input.class, Input.class, false);

    public static final BeanCopier INPUT_SUBMIT_COPIER = BeanCopier.create(Input.class, Submit.class, false);

    public static final BeanCopier NOTIFY_NOTIFY_COPIER = BeanCopier.create(ReportNotify.class, ReportNotify.class, false);

    public static final BeanCopier SUBMIT_SUBMITAWAIT_COPIER = BeanCopier.create(Submit.class, SubmitAwait.class, false);

    public static final BeanCopier REPORT_REPORTNOTIFYAWAIT_COPIER = BeanCopier.create(Report.class, ReportNotifyAwait.class, false);
}
