package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.SmsTemplate;
import com.hero.wireless.web.entity.business.SmsTemplateExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISmsTemplateDAO<T extends SmsTemplate> extends IDao<SmsTemplate, SmsTemplateExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int countByExample(SmsTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int deleteByExample(SmsTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int insert(SmsTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int insertList(List<SmsTemplate> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int insertSelective(SmsTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    List<SmsTemplate> selectByExample(SmsTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    SmsTemplate selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int updateByExampleSelective(@Param("record") SmsTemplate record, @Param("example") SmsTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int updateByExample(@Param("record") SmsTemplate record, @Param("example") SmsTemplateExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int updateByPrimaryKeySelective(SmsTemplate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_template
     *
     * @mbg.generated Tue Sep 17 06:01:12 CST 2019
     */
    int updateByPrimaryKey(SmsTemplate record);
}