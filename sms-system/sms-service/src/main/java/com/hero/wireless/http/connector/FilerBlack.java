package com.hero.wireless.http.connector;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.drondea.wireless.util.DateTime;
import com.drondea.wireless.util.RandomUtil;
import com.drondea.wireless.util.SecretUtil;
import com.hero.wireless.notify.JsonQueryResult;
import com.hero.wireless.okhttp.AbstractCallback;
import com.hero.wireless.okhttp.CharsetResponseBody;
import com.hero.wireless.okhttp.HttpClient;
import com.hero.wireless.web.entity.business.EnterpriseUser;
import com.hero.wireless.web.entity.send.Report;

import okhttp3.Call;

public class FilerBlack {

	public static void main(String[] args) {
		
		//String response = httpClient.post("http://210.76.73.185/api/zw_get_back_msm", content, "application/json").string();
		//System.out.println(response);
		String ak = "624d0708fe90fed62e2dcbb7c13a4921";
		
		String sk = "5053c3b7245b52613fcb7cc44c53aabb";
		HttpClient httpClient = new HttpClient();
		
		ExecutorService  pool = Executors.newCachedThreadPool();
		for(int i=0; i<20; i++) {
			pool.execute(new Runnable() {
				@Override
				public void run() {
					String callId = DateTime.getString(new Date(),DateTime.Y_M_D_H_M_S_S_2)+RandomUtil.randomStr(8);
					Map<String, String> params = new HashMap<String, String>();
					params.put("ak", ak);
					params.put("callId", callId);
					params.put("caller", RandomUtil.randomStr(8));
					params.put("callee", "1803265345");
					String sign=SecretUtil.MD5(ak+callId+sk).toLowerCase();
					params.put("sign", sign);
					long start = System.currentTimeMillis();
					for(int i=0; i<100; i++) {
						String res = httpClient.post("http://www.bsats.cn/forbid.php", params).string();
					}
					long end = System.currentTimeMillis();
					System.out.println("耗时"+(end-start));
				}
			});
		}
		pool.shutdown();
	}
	
	
	public void testCallBack() {
		HttpClient httpClient = new HttpClient();
		Report report = new Report();
		
		report.setPhone_No("18927039666");
		report.setSub_Code("");
		report.setMsg_Batch_No("4A84416FA2F32C39");
		report.setSequence(1);
		report.setStatus_Code("success");
		
		EnterpriseUser user = new EnterpriseUser();
		user.setEnterprise_No("B94016E3E4FCEBD");
		user.setUser_Name("gdggzy");
		user.setHttp_Sign_Key("E420016E44DA041D");
		String content = new JsonQueryResult("0", user, Arrays.asList(report)).toReportJson();
		System.out.println(content);
		httpClient.postAsync("http://210.76.73.185/api/zw_get_back_msm", content, "application/json", new AbstractCallback() {
			
			@Override
			public void ok(Call call, CharsetResponseBody response) throws Exception {
				System.out.println(response.string());
			}
		});
	}
	
}
