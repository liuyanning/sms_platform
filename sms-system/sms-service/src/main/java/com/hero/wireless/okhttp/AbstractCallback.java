package com.hero.wireless.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.drondea.wireless.util.SuperLogger;
import com.hero.wireless.web.exception.BaseException;

public abstract class AbstractCallback implements Callback {
	private String charset = "UTF-8";

	public AbstractCallback(String charset) {
		super();
		this.charset = charset;
	}

	public AbstractCallback() {
		super();
	}

	@Override
	public void onFailure(Call call, IOException e) {
		try {
			faild(call, e);
		} catch (Exception e1) {
			SuperLogger.error(e.getMessage(), e1);
			throw new BaseException(e1.getMessage());
		}
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException {
		ResponseBody responseBody = null;
		try {
			responseBody = response.body();
			ok(call, new CharsetResponseBody(responseBody, charset));
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage());
		} finally {
			if (responseBody != null) {
				responseBody.close();
			}
		}
	}

	public abstract void ok(Call call, CharsetResponseBody response) throws Exception;

	public void faild(Call call, IOException e) throws Exception {
		SuperLogger.error(e.getMessage(), e);
	}
}
