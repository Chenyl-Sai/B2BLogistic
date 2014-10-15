package com.sai.b2blogistic;

import java.text.SimpleDateFormat;

import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sai.util.ConfHelper;
import com.sai.util.HttpRestClient;
import com.sai.util.StrUtil;
import com.sai.util.ToolKits;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * 自定义的应用程序，用于保存一些常量
 * @author cyl
 */
public class MyApplication extends Application
{
	private static final String TAG = "MyApplication";  
	/** 上传位置信息时间间隔（分钟） */
	public int uploadLocationInterval = 60;
	/** 请求版本 */
	public String version = null;
	/** sessin */
	public String sessionID = null;
	/** 用户id */
	public String userId = null;
	/** 登录手机号 */
	public String user = null;
	/** 客户端信息 */
	private String appUserAgent = null;
	/** 设备号 */
	private String deviceToken = null;

	/** GIS相关 */
	public LocationClient mLocationClient = null;
	private MyLocationListenner myListener = new MyLocationListenner();
	private String lastGisAddress = null;//最后经纬度对应的地址信息
	private long lastGisTime = 0L; //最后上报时间
	
	@Override  
	public void onCreate()
	{
		super.onCreate();
//		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//		SDKInitializer.initialize(this);
		
		//消息推送SDK初始化
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        
		//初始化地址
		String str = this.getResources().getString(R.string.server_url);
		Log.i(TAG, "MyApplication onCreate:"+str+","+this.getDeviceToken()+","+this.getUserAgent());
		str = ConfHelper.readSession(this);
		if(str != null && str.length() > 0)
		{
			this.setSessionID(str);
		}
		str = ConfHelper.readUserId(this);
		if(str != null && str.length() > 0)
		{
			this.setUserId(str);
		}
		str = ConfHelper.readUser(this);
		if(str != null && str.length() > 0)
		{
			this.setUser(str);
		}
		int uploadLocationInterval = StrUtil.toInt(this.getResources().getString(R.string.refresh_interval));
		if(uploadLocationInterval > 0)
		{
			this.setUploadLocationInterval(uploadLocationInterval);
		}

		//GIS相关
		mLocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener( myListener );
		LocationClientOption option = new LocationClientOption();
		option.setProdName("com.zcsoft.logphone");
		option.setOpenGps(true);//打开gps
		option.setCoorType("bd09ll");//设置坐标类型
		option.setScanSpan(100);//设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		mLocationClient.setLocOption(option);
	}

	/**
	 * 监听函数，有新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		public void onReceiveLocation(BDLocation loc) {
			Log.i(TAG, "onReceiveLocation.");
			String gisTime = null, addr = null;
			double lat = 0, lng = 0;
			float radius = 0;
			if(loc != null)
			{
				gisTime = loc.getTime();
				lat = loc.getLatitude();
				lng = loc.getLongitude();
				radius = loc.getRadius();
				addr = loc.getAddrStr();
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("刷新时间:");
			sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
			sb.append("\n定位时间:");
			sb.append(gisTime);
			sb.append("\n当前纬度 :");
			sb.append(lat);
			sb.append("\n当前经度 :");
			sb.append(lng);
			sb.append("\n误差半径 :");
			sb.append(radius);
			sb.append("米");
			sb.append("\n当前位置:");
			sb.append(addr);
			logMsg(sb.toString());
			
			//判断是否需要请求GIS信息:当前位置信息变化了，或者距离上次上报时间大于5分钟了，就得再次上报
			boolean needRefresh = !StrUtil.equalsString(lastGisAddress, addr);
			if(!needRefresh)
			{
				needRefresh = (System.currentTimeMillis() - lastGisTime) > 5*60*1000;
			}
			if(needRefresh)
			{
				//请求之前设置位置信息，刷新的时间在更新之后设置
				lastGisAddress = addr;

				RequestParams params = new RequestParams();
				params.put("userid", getUserId());
				params.put("longitude", lng);
				params.put("latitude", lat);
				HttpRestClient.post("/driver/gps", params, new JsonHttpResponseHandler() {
					public void onSuccess(int statusCode, org.apache.http.Header[] headers, 
							org.json.JSONObject response) 
					{
						ToolKits.showLongTips(getApplicationContext(), response.toString());
					}
					
					public void onFailure(int statusCode, org.apache.http.Header[] headers, 
							Throwable throwable, org.json.JSONObject errorResponse) 
					{
						ToolKits.showLongTips(getApplicationContext(), errorResponse.toString());
					}
					
					public void onCancel() 
					{
						
					}
					
				});
			}
		}
	}

	/**
	 * 显示字符串
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if ( MainActivity.instance != null )
				MainActivity.instance.textView.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getVersion()
	{
		if(version == null || version.length() == 0)
		{
			version = this.getPackageInfo().versionName;
		}
		return version;
	}
	
	/**
	 * 获取客户端信息
	 * @return
	 */
	public String getUserAgent() {
		if(appUserAgent == null || appUserAgent == "") {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			//国际移动用户识别码 
			String IMSI = telephonyManager.getSubscriberId();
			String providersName = "";
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				providersName = this.getResources().getString(R.string.comm_provider_mobile); //"中国移动";
	        } else if (IMSI.startsWith("46001")) {
	        	providersName = this.getResources().getString(R.string.comm_provider_unicom); //"中国联通";
	        } else if (IMSI.startsWith("46003")) {
	        	providersName = this.getResources().getString(R.string.comm_provider_telecom); //"中国电信";
	        }
	        else
	        {
	        	providersName = "unknown";
	        }
			//获取当前设置的电话号码 
			String nativePhoneNumber = telephonyManager.getLine1Number();
			if(nativePhoneNumber == null)
			{
				nativePhoneNumber = "unknown";
			}
			StringBuilder ua = new StringBuilder(providersName);
			ua.append("/").append(nativePhoneNumber).append("/");
			ua.append(getVersion());//App版本
			ua.append("/Android");//手机系统平台
			ua.append("/").append(android.os.Build.VERSION.RELEASE);//手机系统版本
			ua.append("/").append(android.os.Build.MODEL); //手机型号
			appUserAgent = ua.toString();
			Log.i(TAG, "appUserAgent="+appUserAgent);
		}
		return appUserAgent;
	}
	
	/**
	 * 获取设备号信息
	 * @return
	 */
	public String getDeviceToken() {
		if(deviceToken == null || deviceToken == "") {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			deviceToken = telephonyManager.getDeviceId();
			if(deviceToken == null || deviceToken.length() == 0)
			{
				deviceToken = "unknown";
			}
			Log.i(TAG, "deviceToken="+deviceToken);
		}
		return deviceToken;
	}
	
	/**
	 * 获取App安装包信息
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (Exception e) {    
			e.printStackTrace(System.err);
		}
		if(info == null) info = new PackageInfo();
		return info;
	}

	public int getUploadLocationInterval() {
		return uploadLocationInterval;
	}

	public void setUploadLocationInterval(int uploadLocationInterval) {
		this.uploadLocationInterval = uploadLocationInterval;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
