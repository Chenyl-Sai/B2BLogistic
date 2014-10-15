package com.sai.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sai.b2blogistic.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 客户端实用工具
 * @author cyl
 *
 */
public class ToolKits
{

	/**
	 * 弹出警告提示框
	 * @param context 上下文
	 * @param title 标题
	 * @param message 内容
	 */
	public static void showAlertDialog(Context context, String title, String message)
	{
		new AlertDialog.Builder(context)
		.setIcon(context.getResources().getDrawable(R.drawable.login_error_icon))
		.setTitle(title)
		.setMessage(message)
		.create().show();
	}


	/**
	 * 弹出短时间的提示信息
	 * @param context 上下文
	 * @param message 内容
	 */
	public static void showShortTips(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}


	/**
	 * 弹出长时间的提示信息
	 * @param context 上下文
	 * @param message 内容
	 */
	public static void showLongTips(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 获取当前使用的手机号码
	 * @param context	上下文
	 * @return	获取到的手机号码，可能为空
	 * */
	public static String getNativePhoneNumber(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String nativePhoneNumber=null;
		nativePhoneNumber = telephonyManager.getLine1Number();
		return nativePhoneNumber;
	}

	/**
	 * 判断外部存储设备是否可以读
	 * */
	public static boolean canReadSdCard()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}

	/**
	 * 判断外部存储设备是否可写
	 * */
	public static boolean canWriteSdCard()
	{

		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}
	

	public static String getMD5(String val) throws NoSuchAlgorithmException
	{  
		MessageDigest md5 = MessageDigest.getInstance("MD5");  
		md5.update(val.getBytes());  
		byte[] m = md5.digest();//加密  
		return getString(m);  
	}  
	
	private static String getString(byte[] b)
	{  
		StringBuffer sb = new StringBuffer();  
		for(int i = 0; i < b.length; i ++){  
			sb.append(b[i]);  
		}  
		return sb.toString();  
	}  
	
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

}
