package com.sai.b2blogistic;

import com.baidu.location.LocationClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{
	
	public static MainActivity instance;
	
	public TextView textView;
	
	public LocationClient mLocClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		textView = (TextView)findViewById(R.id.textView1);
		Intent intent = getIntent();
		if (intent != null) 
		{
			
		}

		mLocClient = ((MyApplication)getApplication()).mLocationClient;
		mLocClient.start();
		int result = mLocClient.requestLocation();
		
		Button reBtn = (Button)findViewById(R.id.button1);
		reBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) 
			{
				int result = mLocClient.requestLocation();	
//				if (mLocClient != null && mLocClient.isStarted()){
//					mLocClient.requestLocation();	
//				}
			}
		});
	}
	
}
