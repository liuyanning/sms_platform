package com.hero.wireless.notify;

import com.drondea.wireless.util.SecretUtil;
import com.hero.wireless.web.entity.business.EnterpriseUser;

/**
 * 
 * 
 * @author volcano
 * @date 2019年9月21日上午6:03:53
 * @version V1.0
 */
public class NotifyUtil {
	/**
	 * 
	 * @param base
	 * @param user
	 * @return
	 * @author volcano
	 * @date 2019年9月19日上午10:05:37
	 * @version V1.0
	 */
	public static String genSign(EnterpriseUser user, String timestamp) {
		return genSign(user.getEnterprise_No(), user.getUser_Name(), timestamp, user.getHttp_Sign_Key());
	}
	
	/**
	 * 
	 * @param enterpriseNo
	 * @param account
	 * @param timestamp
	 * @param signKey
	 * @return
	 * @author volcano
	 * @date 2019年9月21日上午6:06:20
	 * @version V1.0
	 */
	public static String genSign(String enterpriseNo, String account, String timestamp, String signKey) {
		return SecretUtil.MD5(enterpriseNo + account + timestamp + signKey);
	}
}
