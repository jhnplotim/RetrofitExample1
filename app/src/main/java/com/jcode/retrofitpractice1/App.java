package com.jcode.retrofitpractice1;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.jcode.retrofitpractice1.Utils.ApiService;
import com.jcode.retrofitpractice1.Utils.InternetConnectionListener;
import com.jcode.retrofitpractice1.Utils.NetworkConnectionInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by otimj on 9/28/2019.
 */
public class App extends Application {
	private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB;
	private ApiService apiService;
	private InternetConnectionListener mInternetConnectionListener;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public void setInternetConnectionListener(InternetConnectionListener listener) {
		mInternetConnectionListener = listener;
	}

	public void removeInternetConnectionListener() {
		mInternetConnectionListener = null;
	}

	public ApiService getApiService() {
		if (apiService == null) {
			apiService = provideRetrofit(ApiService.BASE_URL).create(ApiService.class);
		}
		return apiService;
	}

	private boolean isInternetAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private Retrofit provideRetrofit(String url) {
		return new Retrofit.Builder()
				.baseUrl(url)
				.client(provideOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create(new Gson()))
				.build();
	}

	private OkHttpClient provideOkHttpClient() {
		OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
		okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
		okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
		okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);

		okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
			@Override
			public boolean isInternetAvailable() {
				return App.this.isInternetAvailable();
			}

			@Override
			public void onInternetUnavailable() {
				if (mInternetConnectionListener != null) {
					mInternetConnectionListener.onInternetUnavailable();
				}
			}

			@Override
			public void onCacheUnavailable() {
				if (mInternetConnectionListener != null) {
					mInternetConnectionListener.onCacheUnavailable();
				}

			}

			@Override
			public void on404Error() {
				if (mInternetConnectionListener != null) {
					mInternetConnectionListener.on404Error();
				}
			}

			@Override
			public void on500Error() {
				if (mInternetConnectionListener != null) {
					mInternetConnectionListener.on500Error();
				}
			}

			@Override
			public void onOtherError() {
				if (mInternetConnectionListener != null) {
					mInternetConnectionListener.onAnyOtherError();
				}
			}
		});

		if(BuildConfig.DEBUG){
			//Use only in development mode. In production, disable this HttpLogging Interceptor
			//HttpLoggingInterceptor is responsible for logging the Requests & Responses of the okhttpclient
			//It is recommended to add this interceptor as the last interceptor
			okhttpClientBuilder.addInterceptor(getHttpLoggingInterceptor());
		}


		okhttpClientBuilder.cache(getCache());

		return okhttpClientBuilder.build();
	}

	public Cache getCache() {
		File cacheDir = new File(getCacheDir(), "cache");
		Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
		return cache;
	}

	private HttpLoggingInterceptor getHttpLoggingInterceptor(){
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
		return  loggingInterceptor;
	}
}
