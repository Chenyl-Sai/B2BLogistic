package com.sai.b2blogistic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sai.util.ConfHelper;
import com.sai.util.StrUtil;
import com.sai.util.ToolKits;
import com.sai.vo.LoginRestData;
import com.sai.vo.RestResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity 
{

	EditText userNameEditText;
	EditText passwordEditText;
	CheckBox autoLoginCheck;
	
	Gson gson = new Gson();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEditText = (EditText)findViewById(R.id.userName);
        passwordEditText = (EditText)findViewById(R.id.password);
        autoLoginCheck = (CheckBox)findViewById(R.id.autoLoginCheck);
        Button login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				login(view);
			}
		});
        
        Button register = (Button)findViewById(R.id.login_registerButton);
        register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				register(view);
			}
		});
    }
    
    public void login(View view)
    {
		String userName = userNameEditText.getText().toString();
		String password = passwordEditText.getText().toString();
		if (StrUtil.isEmptyAny(userName, password)) {
			ToolKits.showLongTips(LoginActivity.this, "用户名/密码不能为空！");
			return;
		}
		Intent intent = new Intent(LoginActivity.this, LoginImplActivity.class);
		intent.putExtra("userName", userName);
		intent.putExtra("password", password);
		startActivityForResult(intent, 0);
    }
    
    public void register(View view)
    {
    	Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
    	startActivity(intent);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    	if (data == null) 
    	{
			return;
		}
		String resultStr = data.getStringExtra("msg");
    	if (resultCode == RESULT_OK) 
    	{
    		RestResult<LoginRestData> result = gson.fromJson(resultStr, new TypeToken<RestResult<LoginRestData>>(){}.getType());
    		if ("1".equals(result.getStatus())) 
    		{
    			//显示登录成功
        		ToolKits.showShortTips(this, result.getMessage());
        		//保存sessionid、userid、密码等
        		String sessionId = result.getData().getSessionId();
    			ConfHelper.saveSession(this, result.getData().getSessionId());
    			((MyApplication)getApplication()).setSessionID(sessionId);
    			String phone = result.getData().getPhone();
    			ConfHelper.saveUser(this, phone);
    			((MyApplication)getApplication()).setUser(phone);
    			String userId = result.getData().getId();
    			ConfHelper.saveUserId(this, userId);
    			((MyApplication)getApplication()).setUserId(userId);
    			String password = "";
    			String autoLogin = "0";
    			if (autoLoginCheck.isChecked()) 
    			{
    				password = result.getData().getPassword();
    			}
    			else 
    			{
    				autoLogin = "1";
				}
    			ConfHelper.savePasswd(this, password);
    			ConfHelper.saveAutoLogin(this, autoLogin);

    			Intent intent = null;
    			if ("true".equalsIgnoreCase(ConfHelper.readWhatsNew(this))) //展示过了
    			{
    				intent = new Intent(LoginActivity.this, MainActivity.class);
    				intent.putExtra("1", "1");
    			}
    			else 
    			{
    				ConfHelper.saveWhatsNew(this, "true");
    				intent = new Intent(LoginActivity.this, WhatsNewActivity.class);
    			}
    			startActivity(intent);
    			LoginActivity.this.finish();
			}
    		else 
    		{
        		ToolKits.showLongTips(this, result.getMessage());
			}
		}
    	else 
    	{
			ToolKits.showAlertDialog(this, "登录失败", resultStr);
		}
    }
    
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// 按下键盘上返回按钮  
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Exit.exit(this);
			return true;
		} else {  
			return super.onKeyDown(keyCode, event);  

		}  
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
}
