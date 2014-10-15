package com.sai.util;

import java.util.Vector;

/**
 * 字符串实用工具集
 * @author cyl
 */
public class StrUtil 
{

	/**
	 * 判断一个字符串是否为空, null 或者 零长度
	 * 但是没有考虑空字符的情况
	 * @param str 待判断的字符串
	 * @return 是否为空串
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * 指定多个字符串，是否存在一个空字符串
	 * @param str 字符串数组
	 * @return 存在一个为空的就返回true
	 */
	public static boolean isEmptyAny(String... str)
	{
		for (int i = 0; i < str.length; i++) {
			if(isEmpty(str[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断一个字符串是否为空, null 或者 零长度
	 * 考虑了空字符的情况
	 * @param str
	 * @return
	 */
	public static boolean isEmptyConsideBlank(String str)
	{
		boolean flag = false;
		if(isEmpty(str))
		{
			flag = true;
		}
		else if(str.indexOf(' ') != -1)
		{
			flag = isEmpty(str.trim());
		}
		return flag;
	}
	

	/**
	 * 判断两个字符串是否相等，null也表示空字符串
	 */
	public static boolean equalsString(String str1, String str2)
	{
		//如果第一个字符串为空，只有第二个字符串同时为空的情况才认为是相等的！
		if(isEmpty(str1))
		{
			return isEmpty(str2);
		}
		return str1.equals(str2);
	}

	/**
	 * 安全的把一个字符串转换为整数，如果出现转换错误，直接返回0
	 * @param str 待转换的字符串
	 * @return 对应的整数
	 */
	public static int toInt(String str)
	{
		int value = 0;
		if(!isEmptyConsideBlank(str))
		{
			try{
				value = Integer.parseInt(str);
			}
			catch(Exception e){}
		}
		return value;
	}

	/**
	 * 安全的把一个字符串转换为浮点数，如果出现转换错误，直接返回0
	 * @param str 待转换的字符串
	 * @return 对应的浮点数
	 */
	public static double toDouble(String str)
	{
		double value = 0;
		if(!isEmptyConsideBlank(str))
		{
			try{
				value = Double.parseDouble(str);
			}
			catch(Exception e){}
		}
		return value;
	}	

	/**
	 * 从ld.server.SysLogUtil.maybeNeedCutStr中复制过来的。
	 * 根据指定的长度对字符串进行截取(根据byte长度来进行截取)
	 * @param str 原始字符串
	 * @param byteLen 指定长度,Byte长度，不是字符个数
	 * @return 截取后的字符串
	 */
	public static String maybeNeedCutStr(String str, int byteLen)
	{
		if(str == null || byteLen < 0)
		{
			return null;
		}
		if(str.getBytes().length <= byteLen)
		{
			return str;
		}
		int size = str.length();
		//str全部是中文的时候，byteLen/2也是byte够的
		StringBuffer sb = new StringBuffer(str.substring(0, byteLen/2));
		int pos = sb.toString().length();
		if(pos <= size)
		{
			char c = str.charAt(pos++);
			while(pos <= size && sb.toString().getBytes().length+
					String.valueOf(c).getBytes().length <= byteLen)
			{
				sb.append(c);
				c = str.charAt(pos++);
			}
		}
		return sb.toString();
	}


	public static Vector getSplitedValue(String str, char ch)
    {
		Vector ret = new Vector();
        if(str != null && str.length() > 0)
        {
            int point = 0;
            int start = 0;
            for(; point < str.length(); point++)
                if(str.charAt(point) == ch)
                {
                    ret.add(str.substring(start, point).trim());
                    start = point + 1;
                }
            ret.add(str.substring(start, point).trim());
        }
        return ret;
    }


	/**
	 * 把字符串中的特定字符替换为指定的字符
	 * @param value String 待替换的字符串
	 * @param oldStr String 其中原始的字符串
	 * @param newStr String 需要替换成新的字符串
	 * @return String 替换完成的字符串
	 */
	public static String replaceAll(String value, String oldStr, String newStr)
	{
		int pos = -1;
		if(value != null && (pos=value.indexOf(oldStr)) != -1)
		{
			int start = 0;
			StringBuffer newValue = new StringBuffer();
			while(pos != -1)
			{
				newValue.append(value.substring(start, pos));
				newValue.append(newStr);
				start = pos + oldStr.length();
				pos = value.indexOf(oldStr, start);
			}
			newValue.append(value.substring(start, value.length()));
			return newValue.toString();
		}
		return value;
	}
	
	/**
	 * 在原先条件的基础上增加新的条件
	 * @param originFilter 原先的条件，可能为空
	 * @param newFilter 新的条件，一般不为空
	 * @return 构造好的条件
	 */
	public static String appendFilter(String originFilter, String newFilter)
	{
		if(isEmptyConsideBlank(originFilter))
		{
			return newFilter;
		}
		else
		{
			return originFilter+" AND "+newFilter;
		}
	}
	
	
	/**
	 * 获得len位长的随机数字加字母的字符串(0~9+A~Z).
	 * 注:数字与字母的出现也是随机的.
	 * @param len int随机字符串长度
	 * @return String 返回随机字符串
	 */
	public static String getRandomStr(int len)
	{
		java.util.Random random = new java.util.Random();
		StringBuilder sb = new StringBuilder();
		do
		{
			//决定该随字字符是取数字还是字母
			//			int decide=Math.abs(random.nextInt()) % 2;
			//			if(decide == 0)
			if(true)
			{
				//产生48到57的随机数(0-9的键位值)
				sb.append(Math.abs(random.nextInt()) % 10);
			}
			else
			{
				//产生97到122的随机数(A-Z的键位值)
				sb.append((char) (Math.abs(random.nextInt()) % 26 + (int) 'A'));
			}
		} while (sb.length() < len);

		return sb.toString();
	}
	

	/**
	 * 通用的加密方法
	 * admin -> -4776541383678084136
	 * @param password 待加密的字符串
	 * @return 加密后的字符
	 */
	public static final String encyptPassword(String password)
	{
		if (password == null)
		{
			return null;
		}
		byte[] byteRaw = password.getBytes();
		try
		{
			byteRaw = java.security.MessageDigest.getInstance("SHA").digest(byteRaw);
			long v = 0;
			int len = byteRaw.length;
			int n = Math.min(len, 8);
			for (int i = 0; i < n; i++)
			{
				v += (long)((byteRaw[i] + n) & 255) << (i * 8);
			}
			byteRaw = Long.toString(v).getBytes();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new String(byteRaw);
	}
}
