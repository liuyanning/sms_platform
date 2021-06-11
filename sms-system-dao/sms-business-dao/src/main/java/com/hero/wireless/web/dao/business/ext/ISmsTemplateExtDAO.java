package com.hero.wireless.web.dao.business.ext;

import com.hero.wireless.web.dao.business.ISmsTemplateDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.SmsTemplate;
import com.hero.wireless.web.entity.business.SmsTemplateExample;
import com.hero.wireless.web.entity.business.ext.SmsTemplateExt;

import java.util.List;
import java.util.Map;

public interface ISmsTemplateExtDAO extends ISmsTemplateDAO<SmsTemplate>, IExtDAO<SmsTemplateExt, SmsTemplateExample> {
    List<Map<String, Object>> querySmsTemplateListForExportPage(SmsTemplateExt condition);
}
