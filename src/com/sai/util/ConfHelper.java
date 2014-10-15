package com.sai.util;

import android.content.ContextWrapper;
import android.content.SharedPreferences;

/**
 * 配置文件实用程序
 * @author cyl
 */
public class ConfHelper 
{
	private static final String CONF_FILE_NAME = "pid";
	
	/**
	 * 保存key/value
	 */
	public static void save(ContextWrapper cw, String key, String value)
	{
		SharedPreferences sp = cw.getSharedPreferences(CONF_FILE_NAME, ContextWrapper.MODE_PRIVATE);	//获得Preferences
    	SharedPreferences.Editor editor = sp.edit();//获得Editor
    	editor.putString(key, value);//将key/value存入Preferences
    	editor.commit();
	}
	
	/**
	 * 读取已保存的value
	 */
	public static String read(ContextWrapper cw, String key, String defaultValue)
	{
		SharedPreferences sp = cw.getSharedPreferences(CONF_FILE_NAME, ContextWrapper.MODE_PRIVATE);	//获得Preferences
		return sp.getString(key, defaultValue);
	}
	
	
	/**
	 * 保存ServerURL
	 */
	public static void saveServerURL(ContextWrapper cw, String serverURL)
	{
		save(cw, "ServerURL", serverURL);
	}
	
	/**
	 * 读取已保存的ServerURL
	 */
	public static String readServerURL(ContextWrapper cw)
	{
		return read(cw, "ServerURL", null);
	}
	
	/**
	 * 保存sessionID
	 */
	public static void saveSession(ContextWrapper cw, String sessionID)
	{
		save(cw, "sessionID", sessionID);
	}
	
	/**
	 * 读取已保存的sessionID
	 */
	public static String readSession(ContextWrapper cw)
	{
		return read(cw, "sessionID", null);
	}
	
	
	/**
	 * 保存user
	 */
	public static void saveUser(ContextWrapper cw, String user)
	{
		save(cw, "user", user);
	}
	
	/**
	 * 读取已保存的user
	 */
	public static String readUser(ContextWrapper cw)
	{
		return read(cw, "user", null);
	}
	
	/**
	 * 保存passwd
	 */
	public static void savePasswd(ContextWrapper cw, String passwd)
	{
		save(cw, "passwd", passwd);
	}
	
	/**
	 * 读取已保存的passwd
	 */
	public static String readPasswd(ContextWrapper cw)
	{
		return read(cw, "passwd", null);
	}
	
	/**
	 * 保存autoLogin
	 */
	public static void saveAutoLogin(ContextWrapper cw, String autoLogin)
	{
		save(cw, "autoLogin", autoLogin);
	}
	
	/**
	 * 读取已保存的autoLogin
	 */
	public static String readAutoLogin(ContextWrapper cw)
	{
		return read(cw, "autoLogin", null);
	}
	
	/**
	 * 保存whatsNew
	 */
	public static void saveWhatsNew(ContextWrapper cw, String whatsNew)
	{
		save(cw, "whatsNew", whatsNew);
	}
	
	/**
	 * 读取已保存的whatsNew
	 */
	public static String readWhatsNew(ContextWrapper cw)
	{
		return read(cw, "whatsNew", null);
	}
	
	/**
	 * 保存userid
	 */
	public static void saveUserId(ContextWrapper cw, String userId)
	{
		save(cw, "userId", userId);
	}
	
	/**
	 * 读取已保存的userid
	 */
	public static String readUserId(ContextWrapper cw)
	{
		return read(cw, "userId", null);
	}

}
