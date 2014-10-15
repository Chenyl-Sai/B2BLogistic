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
 * �ͻ���ʵ�ù���
 * @author cyl
 *
 */
public class ToolKits
{

	/**
	 * ����������ʾ��
	 * @param context ������
	 * @param title ����
	 * @param message ����
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
	 * ������ʱ�����ʾ��Ϣ
	 * @param context ������
	 * @param message ����
	 */
	public static void showShortTips(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}


	/**
	 * ������ʱ�����ʾ��Ϣ
	 * @param context ������
	 * @param message ����
	 */
	public static void showLongTips(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * ��ȡ��ǰʹ�õ��ֻ�����
	 * @param context	������
	 * @return	��ȡ�����ֻ����룬����Ϊ��
	 * */
	public static String getNativePhoneNumber(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String nativePhoneNumber=null;
		nativePhoneNumber = telephonyManager.getLine1Number();
		return nativePhoneNumber;
	}

	/**
	 * �ж��ⲿ�洢�豸�Ƿ���Զ�
	 * */
	public static boolean canReadSdCard()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}

	/**
	 * �ж��ⲿ�洢�豸�Ƿ��д
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
		byte[] m = md5.digest();//����  
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