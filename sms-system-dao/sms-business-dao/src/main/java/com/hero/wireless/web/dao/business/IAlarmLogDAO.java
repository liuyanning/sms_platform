package com.hero.wireless.web.dao.business;

import com.hero.wireless.web.dao.base.IDao;
import com.hero.wireless.web.entity.business.AlarmLog;
import com.hero.wireless.web.entity.business.AlarmLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAlarmLogDAO<T extends AlarmLog> extends IDao<AlarmLog, AlarmLogExample> {
    int countByExample(AlarmLogExample example);

    int deleteByExample(AlarmLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AlarmLog record);

    int insertList(List<AlarmLog> list);

    int insertSelective(AlarmLog record);

    List<AlarmLog> selectByExample(AlarmLogExample example);

    AlarmLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AlarmLog record, @Param("example") AlarmLogExample example);

    int updateByExample(@Param("record") AlarmLog record, @Param("example") AlarmLogExample example);

    int updateByPrimaryKeySelective(AlarmLog record);

    int updateByPrimaryKey(AlarmLog record);
}