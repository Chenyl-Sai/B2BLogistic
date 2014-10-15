package com.sai.b2blogistic;

import java.security.NoSuchAlgorithmException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.sai.util.HttpRestClient;
import com.sai.util.ToolKits;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

import com.loopj.android.http.*;

public class LoginImplActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_impl);
        String userName = getIntent().getStringExtra("userName");
        String password = getIntent().getStringExtra("password");
        RequestParams params = new RequestParams();
        params.put("phone", userName);
        params.put("password", ToolKits.md5(password));
        HttpRestClient.post("/driver/login", params, new JsonHttpResponseHandler(){
        	
        	@Override
        	public void onSuccess(int statusCode, Header[] headers,
        			JSONObject response) 
        	{
        		Intent intent = new Intent();
				intent.putExtra("msg", response.toString());
				LoginImplActivity.this.setResult(Activity.RESULT_OK, intent);
	    		LoginImplActivity.this.finish();
        	}
        	
        	@Override
        	public void onFailure(int statusCode, Header[] headers,
        			Throwable throwable, JSONObject errorResponse) 
        	{
				Intent intent = new Intent();
				intent.putExtra("msg", errorResponse == null ? "µÇÂ¼Ê§°Ü" : errorResponse.toString());
	    		LoginImplActivity.this.setResult(Activity.RESULT_CANCELED, intent);
	    		LoginImplActivity.this.finish();
        	}
        	
        	@Override
        	public void onCancel() 
        	{
	    		LoginImplActivity.this.finish();
        	}
        	
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login_impl, menu);
        return true;
    }
}
