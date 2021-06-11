package com.hero.wireless.okhttp;

import com.drondea.wireless.util.SuperLogger;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http包裝OkHttpClient
 * 
 * @author Lenovo
 *
 */
public class HttpClient {
	/** 单例模式,降低性能开销 */
	private static OkHttpClient.Builder CLIENT_BUILDER = null;
	private static OkHttpClient OK_HTTP_CLINET = null;
	public final static String MEDIA_TYPE_TEXT = "text/html";
	public final static String MEDIA_TYPE_JSON = "application/json";
	public final static String MEDIA_TYPE_XML = "application/xml";
	public final static String MEDIA_TYPE_FORM = "application/x-www-form-urlencoded";
    public final static String MEDIA_TYPE_FILE = "multipart/form-data";

	private String charset = "UTF-8";
	Headers.Builder headerBuilder = new Headers.Builder();
	FormBody.Builder formBodyBuilder = new FormBody.Builder();
	StringBuffer urlParam = new StringBuffer("?");

	public HttpClient() {
		if (CLIENT_BUILDER == null) {
			synchronized (HttpClient.class) {
				if (CLIENT_BUILDER == null) {
					CLIENT_BUILDER = new OkHttpClient.Builder();
					// 可以配置字典里面
					int defaultTimeout = 5 * 1000;
					CLIENT_BUILDER.connectTimeout(defaultTimeout, TimeUnit.MILLISECONDS);
					CLIENT_BUILDER.readTimeout(defaultTimeout, TimeUnit.MILLISECONDS);
                    CLIENT_BUILDER.writeTimeout(defaultTimeout, TimeUnit.MILLISECONDS);
                    //SSLSocketFactory，这个东西就是用来管理证书和信任证书的
                    CLIENT_BUILDER.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getX509TrustManager());
                    //配置一个HostnameVerifier来忽略host验证
                    CLIENT_BUILDER.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                }
			}
		}
		if (OK_HTTP_CLINET == null) {
			synchronized (HttpClient.class) {
				if (OK_HTTP_CLINET == null) {
					OK_HTTP_CLINET = CLIENT_BUILDER.build();
					// 最大线程数
					 OK_HTTP_CLINET.dispatcher().setMaxRequests(100);
					// 同一个主机最大线程数
					OK_HTTP_CLINET.dispatcher().setMaxRequestsPerHost(10);
				}
			}
		}
	}

	public String getCharset() {
		return charset;
	}

	public HttpClient setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	public HttpClient addHeader(Map<String, Object> headersParams) {
		for (String key : headersParams.keySet()) {
			headerBuilder.add(key, headersParams.get(key).toString());
		}
		return this;
	}

	public HttpClient addHeader(String name, String value) {
		headerBuilder.add(name, value);
		return this;
	}

	private void addUrlParam(Map<String, String> params) throws Exception {
		for (String key : params.keySet()) {
			urlParam.append(key).append("=").append(URLEncoder.encode(params.get(key), charset)); // 字符串拼接
			urlParam.append("&");
		}
	}

	public CharsetResponseBody get(String url, Map<String, String> params) {
		ResponseBody responseBody = null;
		try {
			this.addUrlParam(params);
			Request request = new Request.Builder().url(url + this.urlParam).headers(this.headerBuilder.build()).get()
					.build();
			Call call = OK_HTTP_CLINET.newCall(request);
			Response response = call.execute();
			SuperLogger.debug(response.toString());
			responseBody = response.body();
			return new CharsetResponseBody(responseBody, this.charset);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		} finally {
			if (responseBody != null) {
				responseBody.close();
			}
		}
		return null;
	}

	public void getAsync(String url, Map<String, String> params, Callback callback) {
		try {
			this.addUrlParam(params);
			Request request = new Request.Builder().url(url + this.urlParam).headers(this.headerBuilder.build()).get()
					.build();
			Call call = OK_HTTP_CLINET.newCall(request);
			call.enqueue(callback);
			//限制等待队列
			while (OK_HTTP_CLINET.dispatcher().queuedCallsCount() > 50) {
				TimeUnit.MILLISECONDS.sleep(1000);
			}
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}

	private String convertToParams(Map<String, String> mapParams) {
		StringBuffer params = new StringBuffer();
		for (String key : mapParams.keySet()) {
			params.append(key).append("=").append(mapParams.get(key)); // 字符串拼接
			params.append("&");
		}
		return params.toString();
	}

	public CharsetResponseBody post(String url, String params) {
		return post(url, params, MEDIA_TYPE_FORM + ";charset=" + charset);
	}

	public CharsetResponseBody post(String url, Map<String, String> params) {
		return post(url, params, MEDIA_TYPE_FORM + ";charset=" + charset);
	}

	public void postAsync(String url, String params, Callback callback) {
		postAsync(url, params, MEDIA_TYPE_FORM + ";charset=" + charset, callback);
	}

	public void postAsync(String url, Map<String, String> params, Callback callback) {
		postAsync(url, params, MEDIA_TYPE_FORM + ";charset=" + charset, callback);
	}

	public CharsetResponseBody post(String url, Map<String, String> params, String mediaType) {
		return post(url, convertToParams(params), mediaType);
	}

	public CharsetResponseBody post(String url, String params, String mediaType) {
		ResponseBody responseBody = null;
		try {
            RequestBody body = RequestBody.create(MediaType.parse(mediaType), params);
			Request request = new Request.Builder()
                    .url(url)
                    .headers(this.headerBuilder.build())
					.post(body)
                    .build();
			Call call = OK_HTTP_CLINET.newCall(request);
			responseBody = call.execute().body();
			return new CharsetResponseBody(responseBody, this.charset);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		} finally {
			if (responseBody != null) {
				responseBody.close();
			}
		}
		return null;
	}

	public void postAsync(String url, Map<String, String> params, String mediaType, Callback callback) {
		postAsync(url, convertToParams(params), mediaType, callback);
	}

	public void postAsync(String url, String params, String mediaType, Callback callback) {
		if (StringUtils.isEmpty(url)) {
			SuperLogger.error("url不能为空：" + params);
		}
		try {
			Request request = new Request.Builder().url(url).headers(this.headerBuilder.build())
					.post(FormBody.create(MediaType.parse(mediaType), params)).build();
			Call call = OK_HTTP_CLINET.newCall(request);
			if (callback == null) {
				callback = new AbstractCallback(this.charset) {
					@Override
					public void ok(Call call, CharsetResponseBody response) throws Exception {
						SuperLogger.debug(response.string());
					}
				};
			}
			call.enqueue(callback);
			//限制等待队列
			while (OK_HTTP_CLINET.dispatcher().queuedCallsCount() > 50) {
				TimeUnit.MILLISECONDS.sleep(1000);
			}
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
		}
	}


    /**
     * 文件下载
     * @param url
     * @param fileDir
     * @param fileName
     * @return
     */
    public void download(String url,final String fileDir, final String fileName) {
        Request request = new Request.Builder().url(url).headers(this.headerBuilder.build()).build();
//        Response response = OK_HTTP_CLINET.newCall(request).execute();
        //异步请求
        OK_HTTP_CLINET.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SuperLogger.error("文件下载失败："+url+", "+e.getMessage(),e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    File dir = new File(fileDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, fileName);
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    byte[] buf = new byte[2048];
                    int len;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //下载完成
                } catch (Exception e) {
                    SuperLogger.error(e.getMessage(),e);
                }finally {
                    try {
                        if (is != null) {is.close();}
                        if (fos != null) {fos.close();}
                    } catch (IOException e) {}
                }
            }
        });
    }

    public CharsetResponseBody delete(String url, Map<String, String> params, String mediaType) {
        return delete(url, convertToParams(params), mediaType);
    }

    /**
     * http delete请求
     * @param url
     * @return
     */
    public CharsetResponseBody delete(String url,String params,String mediaType) {
        ResponseBody responseBody = null;
        try {
            RequestBody body = RequestBody.create(MediaType.parse(mediaType), params);
            Request request = new Request.Builder().url(url).headers(this.headerBuilder.build())
                    .delete(body).build();
            responseBody = OK_HTTP_CLINET.newCall(request).execute().body();
            return new CharsetResponseBody(responseBody, this.charset);
        } catch (Exception e) {
            SuperLogger.error(e.getMessage(), e);
        } finally {
            if (responseBody != null) {
                responseBody.close();
            }
        }
        return null;
    }

}
