package com.ad.block.osaadblock;

import android.content.Context;
import android.text.TextUtils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

public class HttpRequestManager {
	private static HttpRequestManager requestManager = null;
	private static String REMOTE_CONTROL_URL = "http://hq.sinajs.cn/list=";
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
		OkHttpUtils.enqueue(REMOTE_CONTROL_URL + stocknum, formBody, callback);

		return true;
	}

}
