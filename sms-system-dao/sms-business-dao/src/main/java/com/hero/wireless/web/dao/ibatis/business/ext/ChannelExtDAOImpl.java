package com.hero.wireless.web.dao.ibatis.business.ext;

import com.hero.wireless.web.dao.business.ext.IChannelExtDAO;
import com.hero.wireless.web.dao.ibatis.MybatisBaseBusinessExtDao;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.ChannelExample;
import com.hero.wireless.web.entity.business.ext.ChannelExt;
import org.springframework.stereotype.Repository;

@Repository("channelExtDAO")
public class ChannelExtDAOImpl
		extends MybatisBaseBusinessExtDao<ChannelExt, ChannelExample, Channel>
		implements IChannelExtDAO {

    @Override
    public Integer selectMaxGroupCode() {
        return getSqlBusinessSessionTemplate().selectOne("selectMaxGroupCode");
    }
}
