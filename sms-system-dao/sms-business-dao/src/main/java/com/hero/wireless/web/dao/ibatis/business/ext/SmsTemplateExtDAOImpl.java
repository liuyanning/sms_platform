package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.ISmsTemplateExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.SmsTemplate;
import com.hero.wireless.web.entity.business.SmsTemplateExample;
import com.hero.wireless.web.entity.business.ext.SmsTemplateExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("smsTemplateExtDAO")
public class SmsTemplateExtDAOImpl extends MybatisBaseBusinessExtDao<SmsTemplateExt, SmsTemplateExample, SmsTemplate> implements ISmsTemplateExtDAO{
    @Override
    public List<Map<String, Object>> querySmsTemplateListForExportPage(SmsTemplateExt condition) {
        return getSqlBusinessSessionTemplate().selectList(
                "querySmsTemplateListForExportPage",condition);
    }

}
