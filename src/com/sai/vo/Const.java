package com.sai.vo;

/** 常量接口 */
public interface Const
{
	
	String HOST_SERVER_URL = "http://203.195.186.200:8080/dianshang/rest";
	
	/** 登录 */
	int OPR_LOGIN = 0;
	/** 退出 */
	int OPR_LOGOUT = 1;
	/** 根据session检查此用户是否需要登录 */
	int OPR_LOGIN_CHECK = 2;
	/** 获取可用客户(查询时选择客户使用) */
	int OPR_GET_KHXX = 4;
	/** 请求获取新数据的记录条数(用于显示tabbar上的数字) */
	int OPR_GET_UNREAD_DATA_COUNT = 5;
	/** 验证授权码(查询服务地址) */
	int OPR_REGISTER = 6;
	/** 版本检查 */
	int OPR_CHECK_VERSION = 7;
	/** 获取demo服务地址 */
	int OPR_GET_DEMO_URL = 8;
	
	/** 订单 */
	int QUERY_ORDER = 10;
	
	/** 收款 */
	int QUERY_SK = 20;
	
	/** 送货 */
	int QUERY_SH = 30;
	/** 仓库 */
	int QUERY_STOCK = 40;
	/** 获取仓库信息 */
	int OPR_GET_STOCK_INFO = 41;
	
	
}
