package com.hero.wireless.web.dao.send;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.send.ReportNotifyAwait;
import com.hero.wireless.web.entity.send.ReportNotifyAwaitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IReportNotifyAwaitDAO<T extends ReportNotifyAwait> extends IDao<ReportNotifyAwait, ReportNotifyAwaitExample> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int countByExample(ReportNotifyAwaitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int deleteByExample(ReportNotifyAwaitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int insert(ReportNotifyAwait record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int insertList(List<ReportNotifyAwait> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int insertSelective(ReportNotifyAwait record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    List<ReportNotifyAwait> selectByExample(ReportNotifyAwaitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    ReportNotifyAwait selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int updateByExampleSelective(@Param("record") ReportNotifyAwait record, @Param("example") ReportNotifyAwaitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int updateByExample(@Param("record") ReportNotifyAwait record, @Param("example") ReportNotifyAwaitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int updateByPrimaryKeySelective(ReportNotifyAwait record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table report_notify_await
     *
     * @mbg.generated Fri Jun 04 17:55:09 CST 2021
     */
    int updateByPrimaryKey(ReportNotifyAwait record);
}