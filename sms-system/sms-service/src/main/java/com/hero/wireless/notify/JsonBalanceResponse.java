package com.hero.wireless.notify;

import java.math.BigDecimal;

import com.hero.wireless.web.entity.business.EnterpriseUser;

/**
 * 
 * 
 * @author volcano
 * @date 2019年9月19日上午11:23:46
 * @version V1.0
 */
public class JsonBalanceResponse extends JsonResponse {

	private String balance;
	private String sended;
	private String sale;

	public JsonBalanceResponse(String result, EnterpriseUser user) {
		super(result, user);
		this.setBalance(user.getAvailable_Amount().toString());
		this.setSended(user.getSent_Count().toString());
		this.setSale(user.getUsed_Amount().toString());
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getSended() {
		return sended;
	}

	public void setSended(String sended) {
		this.sended = sended;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144542947026539627L;

	public static void main(String[] args) {
		EnterpriseUser user = new EnterpriseUser();
		user.setHttp_Sign_Key("111111111111111111");
		user.setAvailable_Amount(BigDecimal.valueOf(1000.09));
		user.setUsed_Amount(BigDecimal.valueOf(999999));
		user.setSent_Count(100);
		JsonBalanceResponse info = new JsonBalanceResponse("0", user);
		System.out.println(info.toJson());
	}
}
