package com.hero.wireless.http.connector;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.enums.Charset;
import com.hero.wireless.enums.SubmitStatus;
import com.hero.wireless.http.AbstractHttp;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.entity.business.Channel;
import com.hero.wireless.web.entity.send.Submit;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0.0
 *
 */
public class ZhiMingImpl extends AbstractHttp {

	@Override
	public void mo(Channel channel) throws Exception {

	}

	@Override
	public void report(Channel channel) throws Exception {

	}

	@Override
	public SubmitStatus submit(Submit submit) throws Exception {
		return null;
	}

	@Override
	public String balance(Channel channel) throws Exception {
		String url = balanceUrl(channel);
		Map<String, String> params = new HashMap<String, String>();
		params.put("un", channel.getAccount());
		params.put("pw", channel.getPassword());
		HttpClient httpClient = new HttpClient();
		httpClient.setCharset(Charset.UTF8.toString());
		String result = "";
		CharsetResponseBody response = httpClient.get(url, params);
		result = response.string();
		SuperLogger.debug(result);
		if (StringUtils.isEmpty(result)) {
			return "异常";
		}
		return result.replace("\n", ",");
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
