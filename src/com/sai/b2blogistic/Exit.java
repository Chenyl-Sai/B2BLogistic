package com.sai.b2blogistic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

/**
 * 退出本地应用，服务端保持登录状态
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
	 * 点击了否
	 */
	public void exitNo(View v)
	{
		this.finish();
	}
	
	/**
	 * 点击了是
	 */
	public void exitYes(View v)
	{
		//关闭Main 这个Activity
//		exit(this, ERPMainActivity.instance);
	}

	/**
	 * 最终退出应用程序
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
