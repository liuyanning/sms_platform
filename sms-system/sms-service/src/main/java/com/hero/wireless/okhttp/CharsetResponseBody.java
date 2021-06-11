package com.hero.wireless.okhttp;

import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.BufferedSource;

public class CharsetResponseBody {
	private String string;

	public CharsetResponseBody(ResponseBody responseBody, String charset) throws Exception {
		super();
		BufferedSource source = responseBody.source();
		Charset charsetObj = Util.bomAwareCharset(source, Charset.forName(charset));
		string = source.readString(charsetObj);
	}
	public String string() {
		return string;
	}
}
