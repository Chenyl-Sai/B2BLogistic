package com.sai.util;

import android.content.ContextWrapper;
import android.content.SharedPreferences;

/**
 * �����ļ�ʵ�ó���
 * @author cyl
 */
public class ConfHelper 
{
	private static final String CONF_FILE_NAME = "pid";
	
	/**
	 * ����key/value
	 */
	public static void save(ContextWrapper cw, String key, String value)
	{
		SharedPreferences sp = cw.getSharedPreferences(CONF_FILE_NAME, ContextWrapper.MODE_PRIVATE);	//���Preferences
    	SharedPreferences.Editor editor = sp.edit();//���Editor
    	editor.putString(key, value);//��key/value����Preferences
    	editor.commit();
	}
	
	/**
	 * ��ȡ�ѱ����value
	 */
	public static String read(ContextWrapper cw, String key, String defaultValue)
	{
		SharedPreferences sp = cw.getSharedPreferences(CONF_FILE_NAME, ContextWrapper.MODE_PRIVATE);	//���Preferences
		return sp.getString(key, defaultValue);
	}
	
	
	/**
	 * ����ServerURL
	 */
	public static void saveServerURL(ContextWrapper cw, String serverURL)
	{
		save(cw, "ServerURL", serverURL);
	}
	
	/**
	 * ��ȡ�ѱ����ServerURL
	 */
	public static String readServerURL(ContextWrapper cw)
	{
		return read(cw, "ServerURL", null);
	}
	
	/**
	 * ����sessionID
	 */
	public static void saveSession(ContextWrapper cw, String sessionID)
	{
		save(cw, "sessionID", sessionID);
	}
	
	/**
	 * ��ȡ�ѱ����sessionID
	 */
	public static String readSession(ContextWrapper cw)
	{
		return read(cw, "sessionID", null);
	}
	
	
	/**
	 * ����user
	 */
	public static void saveUser(ContextWrapper cw, String user)
	{
		save(cw, "user", user);
	}
	
	/**
	 * ��ȡ�ѱ����user
	 */
	public static String readUser(ContextWrapper cw)
	{
		return read(cw, "user", null);
	}
	
	/**
	 * ����passwd
	 */
	public static void savePasswd(ContextWrapper cw, String passwd)
	{
		save(cw, "passwd", passwd);
	}
	
	/**
	 * ��ȡ�ѱ����passwd
	 */
	public static String readPasswd(ContextWrapper cw)
	{
		return read(cw, "passwd", null);
	}
	
	/**
	 * ����autoLogin
	 */
	public static void saveAutoLogin(ContextWrapper cw, String autoLogin)
	{
		save(cw, "autoLogin", autoLogin);
	}
	
	/**
	 * ��ȡ�ѱ����autoLogin
	 */
	public static String readAutoLogin(ContextWrapper cw)
	{
		return read(cw, "autoLogin", null);
	}
	
	/**
	 * ����whatsNew
	 */
	public static void saveWhatsNew(ContextWrapper cw, String whatsNew)
	{
		save(cw, "whatsNew", whatsNew);
	}
	
	/**
	 * ��ȡ�ѱ����whatsNew
	 */
	public static String readWhatsNew(ContextWrapper cw)
	{
		return read(cw, "whatsNew", null);
	}
	
	/**
	 * ����userid
	 */
	public static void saveUserId(ContextWrapper cw, String userId)
	{
		save(cw, "userId", userId);
	}
	
	/**
	 * ��ȡ�ѱ����userid
	 */
	public static String readUserId(ContextWrapper cw)
	{
		return read(cw, "userId", null);
	}

}
