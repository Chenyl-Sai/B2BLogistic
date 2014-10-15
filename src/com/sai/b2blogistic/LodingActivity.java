package com.sai.b2blogistic;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sai.util.ConfHelper;
import com.sai.util.HttpRestClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class LodingActivity extends Activity {

	Gson gson = new Gson();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loding);

		Intent intent = new Intent (LodingActivity.this,LoginActivity.class);
		startActivity(intent);
		LodingActivity.this.finish();
		
		RequestParams params = new RequestParams();
		params.put("sessionId", ConfHelper.readSession(this));
		HttpRestClient.get((MyApplication)getApplication(), "check_login", params, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) 
			{
				Intent intent = null;
				try {
					String logined = response.getString("");
					if (!"1".equals(logined)) 
					{
						intent = new Intent (LodingActivity.this,LoginActivity.class);
					}
					else 
					{
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
					intent = new Intent (LodingActivity.this,LoginActivity.class);
				}
				startActivity(intent);
				LodingActivity.this.finish();
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse)
			{
				//自动登录失败，直接跳转到登录
				Log.e("Sai", gson.toJson(errorResponse));
				Intent intent = new Intent (LodingActivity.this,LoginActivity.class);
				startActivity(intent);
				LodingActivity.this.finish();
			}
			
			@Override
			public void onCancel() 
			{
				Exit.exit(LodingActivity.this);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_loding, menu);
		return true;
	}
}
