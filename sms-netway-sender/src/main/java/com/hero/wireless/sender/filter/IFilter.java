package com.hero.wireless.sender.filter;

import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.business.ProductChannels;
import com.hero.wireless.web.entity.send.Submit;

/**
 * @author zly
 */
public interface IFilter {

    /**
     * @param channel
     * @param submit
     * @return
     */
    Submit execute(Channel channel, Submit submit);

    default Submit execute(Channel channel, Submit submit, ProductChannels productChannel) {
        return null;
    }

}
