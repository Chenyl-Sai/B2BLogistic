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
	/** �ֻ���*/
	private EditText mobileText;
	/** ���� */
	private EditText pwd1Text;
	/** �ظ����� */
	private EditText pwd2Text;
	/** �������� */
	private EditText czText;
	/** ���ƺ� */
	private EditText cphText;
	/** ���� */
	private EditText emailText;
	/** ������Ϣ */
	private EditText carmsgText;
	/** ����ͼƬ��ť */
	private Button pic1_btn;
	/** ��ʻ֤ͼƬ��ť */
	private Button pic2_btn;
	/** ��ʻ֤ͼƬ��ť */
	private Button pic3_btn;
	/** ����ͼƬ·�� */
	private String pic1_path;
	/** ��ʻ֤ͼƬ·�� */
	private String pic2_path;
	/** ��ʻ֤ͼƬ·�� */
	private String pic3_path;
	/** ���ð�ť */
	private Button resetButton;
	/** ע�ᰴť */
	private Button registerButton;
	
	/** ��ȡ��֤�밴ť */
	private Button verifyButton;
	/** ��֤�� */
	private EditText verifyCodeText;
	
	/** ����ע���loding */
	private View lodingView;
	/** ����ע����ı�������ʾ */
	private TextView lodingText;
	
	Gson gson = new Gson();
	
	private TimeCount time;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//��ȡ��Ӧ�ؼ�
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
		
		//���Ӱ�ť�������
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
	
	/** ��ť����¼����� */
	public class MyButtonListener implements OnClickListener
	{
		/** ����İ�ť��id */
		private int buttonId;
		
		public MyButtonListener(int buttonId)
		{
			this.buttonId = buttonId;
		}

		@Override
		public void onClick(View view) 
		{
			switch (buttonId) {
			case R.id.reset_button://����
				if (mobileText.isEnabled()) 
				{
					mobileText.setText("");
				}
				pwd1Text.setText("");
				pwd2Text.setText("");
				break;
			case R.id.register_button://ע��
				if (checkCanRegister()) //�ж�ע����Ϣ�Ƿ�����
				{
					//�˴���ʾ
					lodingView.setVisibility(View.VISIBLE);
					lodingText.setText("����ע��");
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
									RegisterActivity.this.finish(); //���ص���¼
								}
								else 
								{
									ToolKits.showShortTips(RegisterActivity.this, (String)response.get("message"));
								}
							} catch (JSONException e) 
							{
								e.printStackTrace();
								ToolKits.showShortTips(RegisterActivity.this, "ע����ִ���" + e.getMessage());
							}
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								Throwable throwable, JSONObject errorResponse) 
						{
							lodingView.setVisibility(View.GONE);
							ToolKits.showShortTips(RegisterActivity.this, "��¼ʧ�ܣ�" + throwable.getMessage());
						}
						
						@Override
						public void onCancel() 
						{
							lodingView.setVisibility(View.GONE);
						}
						
					});
				}
				break;
			case R.id.reg_pic1_btn: //�ϴ�����ͼƬ
			case R.id.reg_pic2_btn: //�ϴ���ʻ֤ͼƬ
			case R.id.reg_pic3_btn: //�ϴ���ʻ֤ͼƬ
				Intent intent = new Intent(RegisterActivity.this, SelectPicPopupWindow.class);
				intent.putExtra("pic_key", "" + buttonId);
				startActivityForResult(intent, buttonId);
				break;
			case R.id.get_verifi_btn: //��ȡ��֤�밴ť
				String phone = mobileText.getText().toString();
				if (StrUtil.isEmpty(phone) || phone.trim().length() < 11) 
				{
					ToolKits.showAlertDialog(RegisterActivity.this, "��ʾ", "��������ȷ���ֻ��ź��ȡ");
					return;
				}
				RequestParams params = new RequestParams();
				params.put("phone", phone);
				//�˴���ʾ
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("���ڻ�ȡ");
				HttpRestClient.post("/driver/registersms", params, new JsonHttpResponseHandler(){
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONObject response) 
					{
						lodingView.setVisibility(View.GONE);
						RestResult restResult = gson.fromJson(response.toString(), RestResult.class);
						if ("1".equals(restResult.getStatus())) 
						{
							ToolKits.showAlertDialog(RegisterActivity.this, "��ʾ", "�����ѷ��ͣ���1������û�յ����ţ����ٴε������ȡ��֤�롱��ť");
						}
						else 
						{
							ToolKits.showAlertDialog(RegisterActivity.this, "��ʾ", restResult.getMessage());
						}
						time.start();
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
							Throwable throwable, JSONObject errorResponse) 
					{
						lodingView.setVisibility(View.GONE);
						ToolKits.showAlertDialog(RegisterActivity.this, "��ʾ", "��ȡ��֤��ʧ�ܣ�" + errorResponse);
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
	
	/** ��ת��ص� */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) 
	{
		if (resultCode == RESULT_OK) 
		{
			switch (requestCode) {
			case R.id.reg_pic1_btn: //ѡ��ͼƬ�󷵻�
				//�˴���ʾ
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("�����ϴ�");
				//�ϴ�ͼƬ
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

			case R.id.reg_pic2_btn: //ѡ��ͼƬ�󷵻�
				//�˴���ʾ
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("�����ϴ�");
				//�ϴ�ͼƬ
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

			case R.id.reg_pic3_btn: //ѡ��ͼƬ�󷵻�
				//�˴���ʾ
				lodingView.setVisibility(View.VISIBLE);
				lodingText.setText("�����ϴ�");
				//�ϴ�ͼƬ
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
//				ToolKits.showAlertDialog(this, "��ʾ", "ѡ��ͼƬʧ�ܣ�δ��ȡ��ͼƬ��");
				break;
			default:
				break;
			}
		}
		
	}
	
	/** ��֤�����Ƿ���ύ */
	public boolean checkCanRegister()
	{
		if (mobileText.getText() == null || StrUtil.isEmpty(mobileText.getText().toString())) 
		{
			ToolKits.showShortTips(this, "���������ֻ����룡");
			return false;
		}
		if (verifyCodeText.getText() == null || StrUtil.isEmpty(verifyCodeText.getText().toString())) 
		{
			ToolKits.showShortTips(this, "���ȡ��֤���¼�룡");
			return false;
		}
		if (pwd1Text.getText() == null || StrUtil.isEmpty(pwd1Text.getText().toString())) 
		{
			ToolKits.showShortTips(this, "�����������룡");
			return false;
		}
		if (pwd2Text.getText() == null || !pwd1Text.getText().toString().equals(pwd2Text.getText().toString())) 
		{
			ToolKits.showShortTips(this, "������������벻һ�£�");
			return false;
		}
		if (StrUtil.isEmpty(pic1_path)) 
		{
			ToolKits.showShortTips(this, "�����ϴ�����ͼƬ");
			return false;
		}
		if (StrUtil.isEmpty(pic2_path)) 
		{
			ToolKits.showShortTips(this, "�����ϴ���ʻ֤ͼƬ");
			return false;
		}
		if (StrUtil.isEmpty(pic3_path)) 
		{
			ToolKits.showShortTips(this, "�����ϴ���ʻ֤ͼƬ");
			return false;
		}
		return true;
	}
	
	/** �ϴ�ͼƬ */
	public String uploadPic(String pic_path)
	{
		PostMethod postMethod = new PostMethod(HttpRestClient.getAbsoluteUrl("/driver/upload"));
		try {
			File file = new File(pic_path);
			// FilePart�������ϴ��ļ�����
			FilePart fp = new FilePart("file", file);
			Part[] parts = { fp };

			// ����MIME���͵�����httpclient����ȫ��MulitPartRequestEntity���а�װ
			MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
			postMethod.setRequestEntity(mre);
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// ��������ʱ��
			int status = client.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				return postMethod.getResponseBodyAsString();
			} else {
				ToolKits.showShortTips(this, "�ϴ��ļ�ʧ��");
			}
		} catch (Exception e) {
			e.printStackTrace();
			ToolKits.showShortTips(this, "�ϴ��ļ�ʧ�ܣ�" + e.getMessage());
		} finally {
			// �ͷ�����
			postMethod.releaseConnection();
		}
		return null;
	}
	
	/** ����ע������ */
	public RequestParams initRequestParams()
	{
		RequestParams params = new RequestParams();
		//������Ϣ
		params.put("phone", mobileText.getText().toString());
		params.put("password", ToolKits.md5(pwd1Text.getText().toString()));
		params.put("name", czText.getText().toString());
		params.put("carNum", cphText.getText().toString());
		params.put("email", emailText.getText().toString());
		params.put("carInfo", carmsgText.getText().toString());
		//ͼƬ
		params.put("carPic", pic1_path);
		params.put("driverLicensePic", pic2_path);
		params.put("drivingLicensePic", pic3_path);
		return params;
	}
	
	/** ��ť����ʱ��ʾ */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}
		@Override
		public void onFinish() {//��ʱ���ʱ����
			verifyButton.setText("��ȡ��֤��");
			verifyButton.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){//��ʱ������ʾ
			verifyButton.setClickable(false);
			verifyButton.setText(millisUntilFinished /1000+"��");
		}
	}
}
