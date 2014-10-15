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
 * �Զ����Ӧ�ó������ڱ���һЩ����
 * @author cyl
 */
public class MyApplication extends Application
{
	private static final String TAG = "MyApplication";  
	/** �ϴ�λ����Ϣʱ���������ӣ� */
	public int uploadLocationInterval = 60;
	/** ����汾 */
	public String version = null;
	/** sessin */
	public String sessionID = null;
	/** �û�id */
	public String userId = null;
	/** ��¼�ֻ��� */
	public String user = null;
	/** �ͻ�����Ϣ */
	private String appUserAgent = null;
	/** �豸�� */
	private String deviceToken = null;

	/** GIS��� */
	public LocationClient mLocationClient = null;
	private MyLocationListenner myListener = new MyLocationListenner();
	private String lastGisAddress = null;//���γ�ȶ�Ӧ�ĵ�ַ��Ϣ
	private long lastGisTime = 0L; //����ϱ�ʱ��
	
	@Override  
	public void onCreate()
	{
		super.onCreate();
//		// ��ʹ�� SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
//		SDKInitializer.initialize(this);
		
		//��Ϣ����SDK��ʼ��
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        
		//��ʼ����ַ
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

		//GIS���
		mLocationClient = new LocationClient( this );
		mLocationClient.registerLocationListener( myListener );
		LocationClientOption option = new LocationClientOption();
		option.setProdName("com.zcsoft.logphone");
		option.setOpenGps(true);//��gps
		option.setCoorType("bd09ll");//������������
		option.setScanSpan(100);//���ö�λģʽ��С��1����һ�ζ�λ;���ڵ���1����ʱ��λ
		mLocationClient.setLocOption(option);
	}

	/**
	 * ��������������λ�õ�ʱ�򣬸�ʽ�����ַ������������Ļ��
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
			sb.append("ˢ��ʱ��:");
			sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
			sb.append("\n��λʱ��:");
			sb.append(gisTime);
			sb.append("\n��ǰγ�� :");
			sb.append(lat);
			sb.append("\n��ǰ���� :");
			sb.append(lng);
			sb.append("\n���뾶 :");
			sb.append(radius);
			sb.append("��");
			sb.append("\n��ǰλ��:");
			sb.append(addr);
			logMsg(sb.toString());
			
			//�ж��Ƿ���Ҫ����GIS��Ϣ:��ǰλ����Ϣ�仯�ˣ����߾����ϴ��ϱ�ʱ�����5�����ˣ��͵��ٴ��ϱ�
			boolean needRefresh = !StrUtil.equalsString(lastGisAddress, addr);
			if(!needRefresh)
			{
				needRefresh = (System.currentTimeMillis() - lastGisTime) > 5*60*1000;
			}
			if(needRefresh)
			{
				//����֮ǰ����λ����Ϣ��ˢ�µ�ʱ���ڸ���֮������
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
	 * ��ʾ�ַ���
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
	 * ��ȡ�ͻ�����Ϣ
	 * @return
	 */
	public String getUserAgent() {
		if(appUserAgent == null || appUserAgent == "") {
			TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			//�����ƶ��û�ʶ���� 
			String IMSI = telephonyManager.getSubscriberId();
			String providersName = "";
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				providersName = this.getResources().getString(R.string.comm_provider_mobile); //"�й��ƶ�";
	        } else if (IMSI.startsWith("46001")) {
	        	providersName = this.getResources().getString(R.string.comm_provider_unicom); //"�й���ͨ";
	        } else if (IMSI.startsWith("46003")) {
	        	providersName = this.getResources().getString(R.string.comm_provider_telecom); //"�й�����";
	        }
	        else
	        {
	        	providersName = "unknown";
	        }
			//��ȡ��ǰ���õĵ绰���� 
			String nativePhoneNumber = telephonyManager.getLine1Number();
			if(nativePhoneNumber == null)
			{
				nativePhoneNumber = "unknown";
			}
			StringBuilder ua = new StringBuilder(providersName);
			ua.append("/").append(nativePhoneNumber).append("/");
			ua.append(getVersion());//App�汾
			ua.append("/Android");//�ֻ�ϵͳƽ̨
			ua.append("/").append(android.os.Build.VERSION.RELEASE);//�ֻ�ϵͳ�汾
			ua.append("/").append(android.os.Build.MODEL); //�ֻ��ͺ�
			appUserAgent = ua.toString();
			Log.i(TAG, "appUserAgent="+appUserAgent);
		}
		return appUserAgent;
	}
	
	/**
	 * ��ȡ�豸����Ϣ
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
	 * ��ȡApp��װ����Ϣ
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
