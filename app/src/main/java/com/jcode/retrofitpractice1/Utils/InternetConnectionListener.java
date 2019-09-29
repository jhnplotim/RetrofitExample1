package com.jcode.retrofitpractice1.Utils;

/**
 * Created by otimj on 9/28/2019.
 */
public interface InternetConnectionListener {
	void onInternetUnavailable();

	void onCacheUnavailable();

	void on404Error();

	void on500Error();

	void onAnyOtherError();
}