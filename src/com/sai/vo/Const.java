package com.sai.vo;

/** �����ӿ� */
public interface Const
{
	
	String HOST_SERVER_URL = "http://203.195.186.200:8080/dianshang/rest";
	
	/** ��¼ */
	int OPR_LOGIN = 0;
	/** �˳� */
	int OPR_LOGOUT = 1;
	/** ����session�����û��Ƿ���Ҫ��¼ */
	int OPR_LOGIN_CHECK = 2;
	/** ��ȡ���ÿͻ�(��ѯʱѡ��ͻ�ʹ��) */
	int OPR_GET_KHXX = 4;
	/** �����ȡ�����ݵļ�¼����(������ʾtabbar�ϵ�����) */
	int OPR_GET_UNREAD_DATA_COUNT = 5;
	/** ��֤��Ȩ��(��ѯ�����ַ) */
	int OPR_REGISTER = 6;
	/** �汾��� */
	int OPR_CHECK_VERSION = 7;
	/** ��ȡdemo�����ַ */
	int OPR_GET_DEMO_URL = 8;
	
	/** ���� */
	int QUERY_ORDER = 10;
	
	/** �տ� */
	int QUERY_SK = 20;
	
	/** �ͻ� */
	int QUERY_SH = 30;
	/** �ֿ� */
	int QUERY_STOCK = 40;
	/** ��ȡ�ֿ���Ϣ */
	int OPR_GET_STOCK_INFO = 41;
	
	
}
