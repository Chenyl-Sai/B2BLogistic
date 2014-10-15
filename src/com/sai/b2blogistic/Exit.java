package com.sai.b2blogistic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * �˳�����Ӧ�ã�����˱��ֵ�¼״̬
 * @author hsw
 */
public class Exit extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.exit_dialog);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.finish();
		return true;
	}

	/**
	 * ����˷�
	 */
	public void exitNo(View v)
	{
		this.finish();
	}
	
	/**
	 * �������
	 */
	public void exitYes(View v)
	{
		//�ر�Main ���Activity
//		exit(this, ERPMainActivity.instance);
	}

	/**
	 * �����˳�Ӧ�ó���
	 * @param activities
	 */
	public static void exit(Activity...activities)
	{
		boolean stoped = false;
		for (Activity activity : activities) {
			if(activity != null)
			{
				activity.finish();
				if(!stoped)
				{
//					PullService.stopAction(activity);
//					activity.stopService(new Intent(activity, PullMessageService.class));
					stoped = true;
				}
			}
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
