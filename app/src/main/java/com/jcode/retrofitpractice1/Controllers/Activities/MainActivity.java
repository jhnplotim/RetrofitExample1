package com.jcode.retrofitpractice1.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jcode.retrofitpractice1.App;
import com.jcode.retrofitpractice1.Models.Fact;
import com.jcode.retrofitpractice1.R;
import com.jcode.retrofitpractice1.Utils.InternetConnectionListener;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Typeface.BOLD;

public class MainActivity extends AppCompatActivity implements InternetConnectionListener {

	private TextView mFactTextView;
	private TextView mOtherTextView;
	private TextView mNoInternetTextView;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//TODO: Implement tabs here

		mFactTextView = findViewById(R.id.activity_main_fact_txt_view);
		mOtherTextView = findViewById(R.id.activity_main_other_text_view);
		mProgressBar = findViewById(R.id.activity_main_progress_bar);
		mNoInternetTextView = findViewById(R.id.activity_main_no_internet_txt_view);

		((App) getApplication()).setInternetConnectionListener(this);
		getRandomFact();
	}

	@Override
	protected void onPause() {
		((App) getApplication()).removeInternetConnectionListener();
		super.onPause();
	}

	private void getRandomFact() {
		hideFactViews();
		showProgressBar();
		((App) getApplication()).getApiService().getRandomFact()
				.enqueue(new Callback<Fact>() {
					@Override
					public void onResponse(Call<Fact> call, Response<Fact> response) {
						if (response.isSuccessful()) {
							//Error code is 200
							// return to UI thread
							// display AccountInfo on UI
							hideProgressBar();
							showFactViews();
							Fact fact = response.body();
							mFactTextView.setText(fact.getText());
							String otherText = String.format("SOURCE: %s TYPE: %s USER: %s", fact.getSource(),fact.getType(),fact.getUser());
							if(mOtherTextView.getVisibility() != View.VISIBLE){
								mOtherTextView.setVisibility(View.VISIBLE);
							}
							mOtherTextView.setText(otherText);
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
								mOtherTextView.setTextAppearance(BOLD);
							}

						}else{
							//Could be any unsuccessful error code e.g. 400, 404, 401, 500 etc
							//No implementation here because error code handling has been centralised within the NetworkConnectionInterceptor.java
						}
					}

					@Override
					public void onFailure(Call<Fact> call, Throwable t) {
						// skip for now
						hideProgressBar();
						if (t instanceof IOException) {
							Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
							// logging probably not necessary
						}
						else {
							Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
							// todo log to some central bug tracking service e.g. https://hockeyapp.net/ OR https://instabug.com/
						}
					}
				});
	}

	private void getRandomFactWithError() {
		hideFactViews();
		showProgressBar();
		((App) getApplication()).getApiService().getRandomFactWithError()
				.enqueue(new Callback<Fact>() {
					@Override
					public void onResponse(Call<Fact> call, Response<Fact> response) {
						if (response.isSuccessful()) {


						}else{
							//Could be any unsuccessful error code e.g. 400, 404, 401, 500 etc
							//No implementation here because error code handling has been centralised within the NetworkConnectionInterceptor.java
						}
					}

					@Override
					public void onFailure(Call<Fact> call, Throwable t) {
						// skip for now
						hideProgressBar();
						if (t instanceof IOException) {
							Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
							// logging probably not necessary
						}
						else {
							Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
							// todo log to some central bug tracking service e.g. https://hockeyapp.net/ OR https://instabug.com/
						}
					}
				});
	}

	private void showProgressBar() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	private void hideProgressBar(){
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void onInternetUnavailable() {
		hideFactViews();
		showNoInternetView();
		hideProgressBar();



	}

	@Override
	public void onCacheUnavailable() {
		onInternetUnavailable();
		Toast.makeText(MainActivity.this, "No content available!!!",Toast.LENGTH_SHORT).show();

	}

	@Override
	public void on404Error() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, "404 error thrown",Toast.LENGTH_SHORT).show();
				Log.d(MainActivity.class.getCanonicalName(), "404 error thrown");
				hideFactViews();
				hideProgressBar();
			}
		});

	}

	@Override
	public void on500Error() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, "500 error thrown",Toast.LENGTH_SHORT).show();
				Log.d(MainActivity.class.getCanonicalName(), "500 error thrown");
				hideFactViews();
				hideProgressBar();
			}
		});

	}

	@Override
	public void onAnyOtherError() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, "Other error thrown",Toast.LENGTH_SHORT).show();
				Log.d(MainActivity.class.getCanonicalName(), "Other error thrown");
				hideFactViews();
				hideProgressBar();
			}
		});

	}

	private void showNoInternetView() {
		mNoInternetTextView.setVisibility(View.VISIBLE);
	}
	private void hideNoInternetView() {
		mNoInternetTextView.setVisibility(View.GONE);
	}

	private void hideFactViews() {
		mFactTextView.setVisibility(View.GONE);
		mOtherTextView.setVisibility(View.GONE);
	}

	private void showFactViews() {
		mFactTextView.setVisibility(View.VISIBLE);
		mOtherTextView.setVisibility(View.VISIBLE);
	}

	public void onClickButton(View view) {
		int buttonTag = Integer.valueOf(view.getTag().toString());

		switch(buttonTag){
			case 10:
				getRandomFact();
				break;
			case 20:
				getRandomFactWithError();
				break;
			default:
				break;
		}
	}
}
