package com.sai.b2blogistic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UploadPicActivity extends Activity
{
	
	private Button pic1Button;
	
	private Button pic2Button;
	
	private Button pic3Button;
	
	boolean hasPic1;

	boolean hasPic2;

	boolean hasPic3;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_pic);
		Intent intent = getIntent();
		hasPic1 = intent.getBooleanExtra("pic1", false);
		hasPic2 = intent.getBooleanExtra("pic2", false);
		hasPic3 = intent.getBooleanExtra("pic3", false);
		pic1Button = (Button)findViewById(R.id.pic1_button);
		pic2Button = (Button)findViewById(R.id.pic2_button);
		pic3Button = (Button)findViewById(R.id.pic3_button);
		pic1Button.setOnClickListener(new MyOnClickListener(0));
		pic2Button.setOnClickListener(new MyOnClickListener(1));
		pic3Button.setOnClickListener(new MyOnClickListener(2));
		if (hasPic1) {
//			pic1Button.setVisibility(View.GONE);
			pic1Button.setText(pic1Button.getText().toString() + "(已上传)");
		}
		if (hasPic2) {
//			pic2Button.setVisibility(View.GONE);
			pic2Button.setText(pic2Button.getText().toString() + "(已上传)");
		}
		if (hasPic3) {
//			pic3Button.setVisibility(View.GONE);
			pic3Button.setText(pic3Button.getText().toString() + "(已上传)");
		}
	}
	
	class MyOnClickListener implements OnClickListener
	{

		private int buttonId;
		
		public MyOnClickListener(int buttonId)
		{
			this.buttonId = buttonId;
		}
		
		@Override
		public void onClick(View v) 
		{
			switch (buttonId) {
			case 0:
			case 1:
			case 2:
				Intent intent = new Intent(UploadPicActivity.this, SelectPicPopupWindow.class);
				startActivityForResult(intent, buttonId);
				break;
			default:
				break;
			}
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) 
		{
			switch (requestCode) 
			{
			case R.id.pic1_button:
				if ("1".equals(data.getStringExtra("result"))) 
				{
					pic1Button.setText(pic1Button.getText().toString() + "(已上传)");
				}
				break;
			case R.id.pic2_button:
				if ("1".equals(data.getStringExtra("result"))) 
				{
					pic2Button.setText(pic2Button.getText().toString() + "(已上传)");
				}
				break;
			case R.id.pic3_button:
				if ("1".equals(data.getStringExtra("result"))) 
				{
					pic3Button.setText(pic3Button.getText().toString() + "(已上传)");
				}
				break;
			default:
				break;
			}
		}
	}
}
