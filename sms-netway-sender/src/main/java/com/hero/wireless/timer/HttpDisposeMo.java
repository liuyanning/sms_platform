package com.hero.wireless.timer;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.ProtocolType;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.util.ChannelUtil;
import com.hero.wireless.web.util.ChannelUtil.OtherParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HTTP接口需要主动查询的上行
 * 
 * @author Lenovo
 *
 */
@Component
public class HttpDisposeMo {
	/* http通道定时线程池 */
	private static ExecutorService httpThreadPool = Executors.newCachedThreadPool();

//	每10秒执行一次
	@Scheduled(cron = "0/60 * *  * * ? ")
	public void execute() {
		Channel channel = new Channel();
		channel.setProtocol_Type_Code(ProtocolType.HTTP.toString());
		DatabaseCache.getChannelList(channel).forEach(item -> {
			Map<String, OtherParameter> parameterMap = ChannelUtil.getParameter(item);
			try {
				if (item.getStatus_Code().equalsIgnoreCase("Stop")) {
					return;
				}
			
				if (StringUtils.isEmpty(ChannelUtil.getParameterValue(parameterMap, "mo_url", null))) {
					return;
				}
				String fullClassImpl=ChannelUtil.getParameterValue(parameterMap, "full_class_impl", null);
				if (StringUtils.isEmpty(fullClassImpl)) {
					return;
				}
				final AbstractHttp mo = (AbstractHttp) Class.forName(fullClassImpl).newInstance();
				httpThreadPool.execute(() -> {
					try {
						mo.mo(item);
					} catch (Exception e) {
						SuperLogger.error(e.getMessage(), e);
					}
				});
			} catch (Exception e) {
				SuperLogger.error(e.getMessage(), e);
			}
		});
	}
}
