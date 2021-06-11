package com.hero.wireless.web.dao.business.ext;


import com.hero.wireless.web.dao.business.IChannelDAO;
import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.entity.business.ChannelExample;
import com.hero.wireless.web.entity.business.ext.ChannelExt;

public interface IChannelExtDAO extends IChannelDAO<ChannelExt>, IExtDAO<ChannelExt, ChannelExample> {

    /**
     * 查询通道最大分组编码
     */
    Integer selectMaxGroupCode();

}
