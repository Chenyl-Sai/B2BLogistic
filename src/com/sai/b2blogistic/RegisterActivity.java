package com.sai.b2blogistic;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sai.util.HttpRestClient;
import com.sai.util.StrUtil;
import com.sai.util.ToolKits;
import com.sai.vo.LoginRestData;
import com.sai.vo.RestResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity 
{
	/** 手机号*/
	private EditText mobileText;
	/** 密码 */
	private EditText pwd1Text;
	/** 重复密码 */
	private EditText pwd2Text;
	/** 车主姓名 */
	private EditText czText;
	/** 车牌号 */
	private EditText cphText;
	/** 邮箱 */
	private EditText emailText;
	/** 车辆信息 */
	private EditText carmsgText;
	/** 车辆图片按钮 */
	private Button pic1_btn;
	/** 驾驶证图片按钮 */
	private Button pic2_btn;
	/** 行驶证图片按钮 */
	private Button pic3_btn;
	/** 车辆图片路径 */
	private String pic1_path;
	/** 驾驶证图片路径 */
	private String pic2_path;
	/** 行驶证图片路径 */
	private String pic3_path;
	/** 重置按钮 */
	private Button resetButton;
	/** 注册按钮 */
	private Button registerButton;
	
	/** 获取验证码按钮 */
	private Button verifyButton;
	/** 验证码 */
	private EditText verifyCodeText;
	
	/** 正在注册的loding */
	private View lodingView;
	/** 正在注册的文本内容显示 */
	private TextView lodingText;
	
	Gson gson = new Gson();
	
	private TimeCount time;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//获取相应控件
		mobileText = (EditText)findViewById(R.id.mobile_text);
		verifyButton = (Button)findViewById(R.id.get_verifi_btn);
		verifyCodeText = (EditText)findViewById(R.id.verifi_text);
		pwd1Text = (EditText)findViewById(R.id.pwd_text);
		pwd2Text = (EditText)findViewById(R.id.pwd_text2);
		czText = (EditText)findViewById(R.id.reg_cz_text);
		cphText = (EditText)findViewById(R.id.reg_cph_text);
		emailText = (EditText)findViewById(R.id.email_text);
		carmsgText = (EditText)findViewById(R.id.carmsg_text);
		pic1_btn = (Button)findViewById(R.id.reg_pic1_btn);
		pic2_btn = (Button)findViewById(R.id.reg_pic2_btn);
		pic3_btn = (Button)findViewById(R.id.reg_pic3_btn);
		resetButton = (Button)findViewById(R.id.reset_button);
		registerButton = (Button)findViewById(R.id.register_button);
		
		lodingView = (View)findViewById(R.id.loding_view);
		lodingText = (TextView)findViewById(R.id.loading_text);
		
		String phoneNumber = ToolKits.getNativePhoneNumber(this);
		if (!StrUtil.isEmpty(phoneNumber)) 
		{
			mobileText.setText(phoneNumber);
			mobileText.setEnabled(false);
		}
		
		//增加按钮点击监听
		resetButton.setOnClickListener(new MyButtonListener(R.id.reset_button));
		registerButton.setOnClickListener(new MyButtonListener(R.id.register_button));
		pic1_btn.setOnClickListener(new MyButtonListener(R.id.reg_pic1_btn));
		pic2_btn.setOnClickListener(new MyButtonListener(R.id.reg_pic2_btn));
		pic3_btn.setOnClickListener(new MyButtonListener(R.id.reg_pic3_btn));
		verifyButton.setOnClickListener(new MyButtonListener(R.id.get_verifi_btn));
		
		time = new TimeCount(60 * 1000, 1000);
	}
	
	Handler uploadFileHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) 
		{
			lodingView.setVisibility(View.GONE);
			ToolKits.showShortTips(RegisterActivity.this, handlerMsg);
			super.handleMessage(msg);
		}
	};
	
	private String handlerMsg;
	
	/** 按钮点击事件监听 */
	public class MyButtonListener implements OnClickListener
	{
		/** 点击的按钮的id */
		private int buttonId;
		
		public MyButtonListener(int buttonId)
		{
			this.buttonId = buttonId;
		}

		@Override
		public void onClick(View view) 
		{
			switch (buttonId) {
			case R.id.reset_button://重置
				if (mobileText.isEnabled()) 
				{
					mobileText.setText("");
				}
				pwd1Text.setText("");
				pwd2Text.setText("");
				break;
			case R.id.register_button://注册
				if (checkCanRegister()) //判断注册信息是否完整
				{
					//此处显示
					lodingView.setVisibility(View.VISIBLE);
					lodingText.setText("正在注册");
					HttpRestClient.post("/driver/register", initRequestParams(), new JsonHttpResponseHandler(){
						
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								JSONObject response) 
						{
							lodingView.setVisibility(View.GONE);
							try {
								if ("1".equals(response.get("status"))) 
								{
									ToolKits.showShortTips(RegisterActivity.this, (String)response.get("message"));
									RegisterActivity.this.finish(); //返回到登录
								}
								else 
								{
									ToolKits.showShortTips(RegisterActivity.this, (String)response.get("message"));
								}
							} catch (JSONException e) 
							{
								e.printStackTrace();
								ToolKits.showShortTips(RegisterActivity.this, "注册出现错误：" + e.getMessage());
							}
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) 
						{
							lodingView.setVisibility(View.GONE);
							ToolKits.showShortTips(RegisterActivity.this, "登录失败：" + throwable.getMessage());
						}
						
						@Override
						public void onCancel() 
						{
							lodingView.setVisibility(View.GONE);
						}
						
					});
				}
				break;
			case R.id.reg_pic1_btn: //上传车辆图片
			case R.id.reg_pic2_btn: //上传驾驶证图片
			case R.id.reg_pic3_btn: //上传行驶证图片
				Intent intent = new Intent(RegisterActivity.this, SelectPicPopupWindow.class);
				intent.putExtra("pic_key", "" + buttonId);
				startActivityForResult(intent, buttonId);
				break;
			case R.id.get_verifi_btn: //获取验证码按钮
				String phone = mobileText.getText().toString();
				if (StrUtil.isEmpty(phone) || phone.trim().length() < 11) 
				{
					ToolKits.showAlertDialog(RegisterActivity.this, "提示", "请输入正确的手机号后获取");
					return;
				}
				RequestParams params = new RequestParams();
				params.put("phone", phone);
				//此处显示
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("正在获取");
				HttpRestClient.post("/driver/registersms", params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) 
					{
						lodingView.setVisibility(View.GONE);
						RestResult restResult = gson.fromJson(response.toString(), RestResult.class);
						if ("1".equals(restResult.getStatus())) 
						{
							ToolKits.showAlertDialog(RegisterActivity.this, "提示", "短信已发送，如1分钟内没收到短信，可再次点击“获取验证码”按钮");
						}
						else 
						{
							ToolKits.showAlertDialog(RegisterActivity.this, "提示", restResult.getMessage());
						}
						time.start();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) 
					{
						lodingView.setVisibility(View.GONE);
						ToolKits.showAlertDialog(RegisterActivity.this, "提示", "获取验证码失败：" + errorResponse);
					}
					
					@Override
					public void onCancel() 
					{
						lodingView.setVisibility(View.GONE);
					}
				});
				break;
			default:
				break;
			}
		}
		
	}
	
	/** 跳转后回调 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) 
	{
		if (resultCode == RESULT_OK) 
		{
			switch (requestCode) {
			case R.id.reg_pic1_btn: //选择图片后返回
				//此处显示
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("正在上传");
				//上传图片
				new Thread(){
					@Override
					public void run() 
					{
						String resultStr = uploadPic(data.getStringExtra("path"));
						Message msg = uploadFileHandler.obtainMessage();  
						RestResult<String> result = gson.fromJson(resultStr,  new TypeToken<RestResult<String>>(){}.getType());
						handlerMsg = result.getMessage();
						if ("1".equals(result.getStatus()))
						{
							pic1_path = result.getData();
						}
                        msg.sendToTarget();
					}
				}.start();
				break;

			case R.id.reg_pic2_btn: //选择图片后返回
				//此处显示
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("正在上传");
				//上传图片
				new Thread(){
					@Override
					public void run() 
					{
						String resultStr = uploadPic(data.getStringExtra("path"));
						Message msg = uploadFileHandler.obtainMessage();  
						RestResult<String> result = gson.fromJson(resultStr, new TypeToken<RestResult<String>>(){}.getType());
						handlerMsg = result.getMessage();
						if ("1".equals(result.getStatus()))
						{
							pic2_path = result.getData();
						}
                        msg.sendToTarget();
					}
				}.start();
				break;

			case R.id.reg_pic3_btn: //选择图片后返回
				//此处显示
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("正在上传");
				//上传图片
				new Thread(){
					@Override
					public void run() 
					{
						String resultStr = uploadPic(data.getStringExtra("path"));
						Message msg = uploadFileHandler.obtainMessage();  
						RestResult<String> result = gson.fromJson(resultStr, new TypeToken<RestResult<String>>(){}.getType());
						handlerMsg = result.getMessage();
						if ("1".equals(result.getStatus()))
						{
							pic3_path = result.getData();
						}
                        msg.sendToTarget();
					}
				}.start();
				break;
			default:
				break;
			}
		}
		else 
		{
			switch (requestCode) {
			case R.id.reg_pic1_btn:
			case R.id.reg_pic2_btn:
			case R.id.reg_pic3_btn:
//				ToolKits.showAlertDialog(this, "提示", "选择图片失败，未获取到图片！");
				break;
			default:
				break;
			}
		}
		
	}
	
	/** 验证数据是否可提交 */
	public boolean checkCanRegister()
	{
		if (mobileText.getText() == null || StrUtil.isEmpty(mobileText.getText().toString())) 
		{
			ToolKits.showShortTips(this, "必须输入手机号码！");
			return false;
		}
		if (verifyCodeText.getText() == null || StrUtil.isEmpty(verifyCodeText.getText().toString())) 
		{
			ToolKits.showShortTips(this, "请获取验证码后录入！");
			return false;
		}
		if (pwd1Text.getText() == null || StrUtil.isEmpty(pwd1Text.getText().toString())) 
		{
			ToolKits.showShortTips(this, "必须输入密码！");
			return false;
		}
		if (pwd2Text.getText() == null || !pwd1Text.getText().toString().equals(pwd2Text.getText().toString())) 
		{
			ToolKits.showShortTips(this, "两次输入的密码不一致！");
			return false;
		}
		if (StrUtil.isEmpty(pic1_path)) 
		{
			ToolKits.showShortTips(this, "必须上传车辆图片");
			return false;
		}
		if (StrUtil.isEmpty(pic2_path)) 
		{
			ToolKits.showShortTips(this, "必须上传驾驶证图片");
			return false;
		}
		if (StrUtil.isEmpty(pic3_path)) 
		{
			ToolKits.showShortTips(this, "必须上传行驶证图片");
			return false;
		}
		return true;
	}
	
	/** 上传图片 */
	public String uploadPic(String pic_path)
	{
		PostMethod postMethod = new PostMethod(HttpRestClient.getAbsoluteUrl("/driver/upload"));
		try {
			File file = new File(pic_path);
			// FilePart：用来上传文件的类
			FilePart fp = new FilePart("file", file);
			Part[] parts = { fp };

			// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
			MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
			postMethod.setRequestEntity(mre);
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置连接时间
			int status = client.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				return postMethod.getResponseBodyAsString();
			} else {
				ToolKits.showShortTips(this, "上传文件失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToolKits.showShortTips(this, "上传文件失败：" + e.getMessage());
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return null;
	}
	
	/** 构造注册数据 */
	public RequestParams initRequestParams()
	{
		RequestParams params = new RequestParams();
		//参数信息
		params.put("phone", mobileText.getText().toString());
		params.put("password", ToolKits.md5(pwd1Text.getText().toString()));
		params.put("name", czText.getText().toString());
		params.put("carNum", cphText.getText().toString());
		params.put("email", emailText.getText().toString());
		params.put("carInfo", carmsgText.getText().toString());
		//图片
		params.put("carPic", pic1_path);
		params.put("driverLicensePic", pic2_path);
		params.put("drivingLicensePic", pic3_path);
		return params;
	}
	
	/** 按钮倒计时显示 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
		}
		@Override
		public void onFinish() {//计时完毕时触发
			verifyButton.setText("获取验证码");
			verifyButton.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){//计时过程显示
			verifyButton.setClickable(false);
			verifyButton.setText(millisUntilFinished /1000+"秒");
		}
	}
}
