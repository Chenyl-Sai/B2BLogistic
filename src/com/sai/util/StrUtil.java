package com.sai.util;

import java.util.Vector;

/**
 * �ַ���ʵ�ù��߼�
 * @author cyl
 */
public class StrUtil 
{

	/**
	 * �ж�һ���ַ����Ƿ�Ϊ��, null ���� �㳤��
	 * ����û�п��ǿ��ַ������
	 * @param str ���жϵ��ַ���
	 * @return �Ƿ�Ϊ�մ�
	 */
	public static boolean isEmpty(String str)
	{
		return str == null || str.trim().length() == 0;
	}
	
	/**
	 * ָ������ַ������Ƿ����һ�����ַ���
	 * @param str �ַ�������
	 * @return ����һ��Ϊ�յľͷ���true
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
	 * �ж�һ���ַ����Ƿ�Ϊ��, null ���� �㳤��
	 * �����˿��ַ������
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
	 * �ж������ַ����Ƿ���ȣ�nullҲ��ʾ���ַ���
	 */
	public static boolean equalsString(String str1, String str2)
	{
		//�����һ���ַ���Ϊ�գ�ֻ�еڶ����ַ���ͬʱΪ�յ��������Ϊ����ȵģ�
		if(isEmpty(str1))
		{
			return isEmpty(str2);
		}
		return str1.equals(str2);
	}

	/**
	 * ��ȫ�İ�һ���ַ���ת��Ϊ�������������ת������ֱ�ӷ���0
	 * @param str ��ת�����ַ���
	 * @return ��Ӧ������
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
	 * ��ȫ�İ�һ���ַ���ת��Ϊ���������������ת������ֱ�ӷ���0
	 * @param str ��ת�����ַ���
	 * @return ��Ӧ�ĸ�����
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
	 * ��ld.server.SysLogUtil.maybeNeedCutStr�и��ƹ����ġ�
	 * ����ָ���ĳ��ȶ��ַ������н�ȡ(����byte���������н�ȡ)
	 * @param str ԭʼ�ַ���
	 * @param byteLen ָ������,Byte���ȣ������ַ�����
	 * @return ��ȡ����ַ���
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
		//strȫ�������ĵ�ʱ��byteLen/2Ҳ��byte����
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
	 * ���ַ����е��ض��ַ��滻Ϊָ�����ַ�
	 * @param value String ���滻���ַ���
	 * @param oldStr String ����ԭʼ���ַ���
	 * @param newStr String ��Ҫ�滻���µ��ַ���
	 * @return String �滻��ɵ��ַ���
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
	 * ��ԭ�������Ļ����������µ�����
	 * @param originFilter ԭ�ȵ�����������Ϊ��
	 * @param newFilter �µ�������һ�㲻Ϊ��
	 * @return ����õ�����
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
	 * ���lenλ����������ּ���ĸ���ַ���(0~9+A~Z).
	 * ע:��������ĸ�ĳ���Ҳ�������.
	 * @param len int����ַ�������
	 * @return String ��������ַ���
	 */
	public static String getRandomStr(int len)
	{
		java.util.Random random = new java.util.Random();
		StringBuilder sb = new StringBuilder();
		do
		{
			//�����������ַ���ȡ���ֻ�����ĸ
			//			int decide=Math.abs(random.nextInt()) % 2;
			//			if(decide == 0)
			if(true)
			{
				//����48��57�������(0-9�ļ�λֵ)
				sb.append(Math.abs(random.nextInt()) % 10);
			}
			else
			{
				//����97��122�������(A-Z�ļ�λֵ)
				sb.append((char) (Math.abs(random.nextInt()) % 26 + (int) 'A'));
			}
		} while (sb.length() < len);

		return sb.toString();
	}
	

	/**
	 * ͨ�õļ��ܷ���
	 * admin -> -4776541383678084136
	 * @param password �����ܵ��ַ���
	 * @return ���ܺ���ַ�
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
