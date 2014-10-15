package com.sai.b2blogistic;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.loopj.android.http.*;

public class SelectPicPopupWindow extends Activity implements OnClickListener
{
	String fileName = "image.jpg";
	
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	
	private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private LinearLayout layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert_dialog);
		btn_take_photo = (Button) this.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (Button) this.findViewById(R.id.btn_pick_photo);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		
		Intent intent = getIntent();
		String pic_key = intent.getStringExtra("pic_key");
		fileName = "image-" + pic_key + ".jpg";
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//���ѡ�񴰿ڷ�Χ�����������Ȼ�ȡ���㣬������ִ��onTouchEvent()��������������ط�ʱִ��onTouchEvent()��������Activity
		layout.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
			}
		});
		//��Ӱ�ť����
		btn_cancel.setOnClickListener(this);
		btn_pick_photo.setOnClickListener(this);
		btn_take_photo.setOnClickListener(this);
	}
	
	//ʵ��onTouchEvent���������������Ļʱ���ٱ�Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_take_photo:
			Uri imageUri = null;
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
//			imageUri = Uri.fromFile(new File(getApplicationContext().getFilesDir(),fileName));
			//ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
			break;
		case R.id.btn_pick_photo:	
			Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
			openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
			break;
		case R.id.btn_cancel:				
			break;
		default:
			break;
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

			case TAKE_PICTURE:
				Intent intent = new Intent();
				intent.putExtra("path", Environment.getExternalStorageDirectory() + "/" +fileName);
				this.setResult(RESULT_OK, intent);
				this.finish();
				break;
			case CHOOSE_PICTURE:
				//��Ƭ��ԭʼ��Դ��ַ
				Uri originalUri = data.getData(); 
	            String[] proj = {MediaStore.Images.Media.DATA};
	            //������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�
	            Cursor cursor = managedQuery(originalUri, proj, null, null, null); 
	            //���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            //�����������ͷ ���������Ҫ����С�ĺ���������Խ��
	            cursor.moveToFirst();
	            //����������ֵ��ȡͼƬ·��
	            String path = cursor.getString(column_index);
	            
				intent = new Intent();
				intent.putExtra("path", path);
				this.setResult(RESULT_OK, intent);
				this.finish();
				break;
			}
		}
		else 
		{
			Intent intent = new Intent();
			this.setResult(resultCode, intent);
			this.finish();
		}
	}
}
