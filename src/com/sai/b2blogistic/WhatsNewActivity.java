package com.sai.b2blogistic;

import java.util.ArrayList;

import com.sai.util.ConfHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class WhatsNewActivity extends Activity {

	private ViewPager mViewPager;	
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;

	private int currIndex = 0;
	private String to_token = null;//设置页面的点击，需要退回到设置页面的
	//是否进入主页面
	boolean enterMain = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new);
		mViewPager = (ViewPager)findViewById(R.id.whatsnew_viewpager);        
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());


		mPage0 = (ImageView)findViewById(R.id.page0);
		mPage1 = (ImageView)findViewById(R.id.page1);
		mPage2 = (ImageView)findViewById(R.id.page2);
		mPage3 = (ImageView)findViewById(R.id.page3);

		//将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.loding1, null);
		View view2 = mLi.inflate(R.layout.loding2, null);
		View view3 = mLi.inflate(R.layout.loding3, null);
		View view4 = mLi.inflate(R.layout.loding4, null);

		//每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);

		//填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager)container).removeView(views.get(position));
			}



			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager)container).addView(views.get(position));
				return views.get(position);
			}
		};

		mViewPager.setAdapter(mPagerAdapter);
		this.to_token = this.getIntent().getStringExtra("to");
    }

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:				
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 1:
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage0.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 2:
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage1.setImageDrawable(getResources().getDrawable(R.drawable.page));
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			case 3:
				mPage3.setImageDrawable(getResources().getDrawable(R.drawable.page_now));
				mPage2.setImageDrawable(getResources().getDrawable(R.drawable.page));
				break;
			}
			currIndex = arg0;
			//animation.setFillAfter(true);// True:图片停在动画结束位置
			//animation.setDuration(300);
			//mPageImg.startAnimation(animation);
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	public void startbutton(View v) {
		//设置页面点击进入的欢迎页面
		if("show_welcome".equals(this.to_token))
		{
			enterMain = false;
		}
		Intent intent = null;
		if(enterMain)
		{
			intent = new Intent (this, MainActivity.class);
			startActivity(intent);
		}
		this.finish();
	}


	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// 按下键盘上返回按钮  
		if (this.to_token == null && keyCode == KeyEvent.KEYCODE_BACK) {
			Exit.exit(this);
			return true;
		} else {  
			return super.onKeyDown(keyCode, event);  

		}  
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_whats_new, menu);
        return true;
    }
}
