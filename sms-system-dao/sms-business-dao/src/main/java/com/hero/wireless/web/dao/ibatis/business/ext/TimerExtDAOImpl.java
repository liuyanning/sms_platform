package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.ITimerExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.ext.AlarmExt;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

@Repository("timerExtDAO")
public class TimerExtDAOImpl extends MybatisBaseBusinessExtDao<T, T, T> implements ITimerExtDAO {

    @Override
    public Integer getReportSuccessTotalForReception(AlarmExt alarmExt) {
        return getSqlSendSessionTemplate().selectOne("getReportSuccessTotalForReception", alarmExt);
    }

    @Override
    public Integer getReportTotalForReturnRate(AlarmExt alarmExt) {
        return getSqlSendSessionTemplate().selectOne("getReportTotalForReturnRate", alarmExt);
    }

    @Override
    public Integer getSubmitSuccessTotal(AlarmExt alarmExt) {
        return getSqlSendSessionTemplate().selectOne("getSubmitSuccessTotal", alarmExt);
    }

    @Override
    public Integer getSubmitTotal(AlarmExt alarmExt) {
        return getSqlSendSessionTemplate().selectOne("getSubmitTotal", alarmExt);
    }

    @Override
    public Integer getSubmitTotalForVelocity(AlarmExt alarmExt) {
        return getSqlSendSessionTemplate().selectOne("getSubmitTotalForVelocity", alarmExt);
    }

    @Override
    public Integer getChannelReportStatusCode(AlarmExt alarmExt) {
        return getSqlSendSessionTemplate().selectOne("getChannelReportStatusCode", alarmExt);
    }
}
