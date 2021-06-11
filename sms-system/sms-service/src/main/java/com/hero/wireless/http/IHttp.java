package com.hero.wireless.http;

import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;

import java.util.Map;

/**
 * HTTP通道接口
 * 
 * @author Lenovo
 *
 */
public interface IHttp {
	/**
	 * 主动推送数据结构
	 * 
	 * @author volcano
	 * @date 2019年9月18日上午2:58:36
	 * @version V1.0
	 */
	public static class ChannelReport {
		private String channelNo;
		private Object data;

		public ChannelReport(String channelNo, Object data) {
			super();
			this.channelNo = channelNo;
			this.data = data;
		}

		public String getChannelNo() {
			return channelNo;
		}

		public void setChannelNo(String channelNo) {
			this.channelNo = channelNo;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}

	/**
	 * 上行
	 * 
	 * @param http
	 * @throws Exception
	 */
	void mo(Channel channel) throws Exception;

	/**
	 * 上游是主动推送模式
	* @param channelReport
	* @return
	* @throws Exception
	* @author volcano
	* @date 2019年9月18日上午3:00:31
	* @version V1.0
	 */
	default Object mo(ChannelReport channelReport) throws Exception {
		return null;
	};

	/**
	 * 上游是主动推送模式
	* @param channelReport
	* @return
	* @throws Exception
	* @author volcano
	* @date 2019年9月18日上午3:00:27
	* @version V1.0
	 */
	default Object report(ChannelReport channelReport) throws Exception {
		return null;
	};

	/**
	 * 状态报告
	 * 
	 * @param channel
	 * @throws Exception
	 */
	void report(Channel channel) throws Exception;

	/**
	 * 提交短信
	 * 
	 * @param submit
	 * @return
	 * @throws Exception
	 */
	SubmitStatus submit(Submit submit) throws Exception;

	/**
	 * 余额
	 * 
	 * @param channel
	 * @return
	 * @throws Exception
	 */
	String balance(Channel channel) throws Exception;

	/**
	 * 查询转网号码
	 * @param mobiles 手机号码，多个英文逗号隔开 token：加密用的token  url:请求地址
	 * @return  code=0表示查询成功，查询成功时data的数据 key是手机号，value:1表示携号转网变成移动 2表示变成联通 3表示变成电信
	 * 注意：只有系统系别出来的携号转网号码才会返还，这样客户端只需要关注返回的携号转网数据即可
	 */
	Map<String, Integer> queryTransferPhone(String token, String mobiles, String url);
}
