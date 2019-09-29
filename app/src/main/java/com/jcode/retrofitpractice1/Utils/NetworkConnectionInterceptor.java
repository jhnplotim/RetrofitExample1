package com.jcode.retrofitpractice1.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by otimj on 9/28/2019.
 */
public abstract class NetworkConnectionInterceptor implements Interceptor {

	public abstract boolean isInternetAvailable();

	public abstract void onInternetUnavailable();

	public abstract void onCacheUnavailable();

	public abstract void on404Error();

	public abstract void on500Error();

	public abstract void onOtherError();

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		//If Internet is unavailable, access the cache
		if (!isInternetAvailable()) {
			onInternetUnavailable();
			request = request.newBuilder().header("Cache-Control",
					"public, only-if-cached, max-stale=" + 60 * 60 * 24).build();
			Response response = chain.proceed(request);
			if (response.cacheResponse() == null) {
				onCacheUnavailable();
			}
			return response;
		}else{
			//If Internet is available, check the response codes
			Response response = chain.proceed(request);
			switch (response.code()){
				case 200:
					break;
				case 404:
					on404Error();
					break;
				case 500:
					on500Error();
					break;
				default:
					onOtherError();
					break;
			}
			return response;
		}
	}
}
