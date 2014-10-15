package com.sai.util;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.SyncHttpClient;
import com.sai.b2blogistic.MyApplication;
import com.sai.vo.Const;

/**
 * @ClassName: HttpRestClient.java
 * @Description: TODO
 * @author FrankWong
 * @version V1.0
 * @Date 2013-10-24 9:37:16
 */
public class HttpRestClient {

	private static AsyncHttpClient client = new AsyncHttpClient();
	
	private static SyncHttpClient syncClient = new SyncHttpClient();

	static {
		client.setTimeout(8000); 
	}

	public static void get(MyApplication app, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.cancelRequests(app, true);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void getDirectly(MyApplication app, String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.cancelRequests(app, true);
		client.get(url, params, responseHandler);
	}

	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void post(MyApplication app, String url, RequestParams params, Header[] headers, String contentType,
			AsyncHttpResponseHandler responseHandler) {
		client.post(app, getAbsoluteUrl(url), headers, params, contentType, responseHandler);
	}
	
	public static void syncPost(String url, RequestParams params, ResponseHandlerInterface responseHandler)
	{
		syncClient.post(getAbsoluteUrl(url), params, responseHandler);
	}
	
	public static void postBusRequest(String url, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		client.post(url, params, responseHandler);
	}

	public static String getAbsoluteUrl(String relativeUrl) {
		return Const.HOST_SERVER_URL + relativeUrl;
	}
	
}
