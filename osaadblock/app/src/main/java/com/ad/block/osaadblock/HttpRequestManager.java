package com.ad.block.osaadblock;

import android.content.Context;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpRequestManager {
	private static HttpRequestManager requestManager = null;
	private static String Stock_CONTROL_URL = "http://hq.sinajs.cn/list=";
	private static String Whether_CONTROL_URL =
			"http://php.weather.sina.com.cn/xml.php?city=ylc&password=DJOYnieT8234jlsK&day=0";
	private Context context;

	public static HttpRequestManager getInstance(Context context) {
		if (requestManager == null) {
			synchronized (HttpRequestManager.class) {
				if (requestManager == null) {
					requestManager = new HttpRequestManager(context.getApplicationContext());
				}
			}
		}
		return requestManager;
	}

	private HttpRequestManager(Context context) {
		this.context = context;
	}

	public boolean request_stock(
			String stocknum,
			final Callback callback) {
		if (!NetUtils.checkNetState(context)) {
			return false;
		}

		if(TextUtils.isEmpty(stocknum)){
			return false;
		}

		if(!stocknum.startsWith("sh") && !stocknum.startsWith("sz")){
			return false;
		}

		RequestBody formBody = new FormEncodingBuilder()
				.build();
		OkHttpUtils.enqueue(Stock_CONTROL_URL + stocknum, formBody, callback);

		return true;
	}

	public boolean request_whether(
			String w,
			final Callback callback) {
		if (!NetUtils.checkNetState(context)) {
			return false;
		}

		if(TextUtils.isEmpty(w)){
			return false;
		}

		RequestBody formBody = new FormEncodingBuilder()
				.build();
		String t  = null;
		try {
			t = Whether_CONTROL_URL.replace("ylc", URLEncoder.encode(w, "gb2312"));
			OkHttpUtils.enqueue(t, formBody, callback);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return true;
	}
}
