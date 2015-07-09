package com.jiazi.smartway.utils;

public class Constant {
	public static final int ARRAY_TYPE_USER = 1;// 用户类型
	public static final int ARRAY_TYPE_WEEKS = 2;// 周一至周日
	public static final int ARRAY_TYPE_CABINETTYPE = 3;// 柜型
	public static final int ARRAY_TYPE_DRIVER_PHONE = 4;// 司机号码

	public static final int USER_TYPE_TRAILER = 2;// 拖车公司
	public static final int USER_TYPE_SMARTWAY = 1;// SmartWay公司
	public static final int USER_TYPE_CUSTOMER = 3;// 用户
	public static final int USER_TYPE_CUSTOMS_BROKER = 4;// 报关行

	public static final String PRE_NAME = "config";// SharePreference名称
	
	public static final String URL_LOGIN = "http://smartway.jiazi-it.com/index.php?m=Home&c=UserApi&a=login";// 登录URL
	
	public static final String URL_TRAILER_SAVE = "http://smartway.jiazi-it.com/index.php?m=Home&c=TrailerApi&a=addInfo";// 提交拖车数据URL
	public static final String URL_TRAILER_QUERY_SW = "http://smartway.jiazi-it.com/index.php?m=Home&c=TrailerApi&a=getInfoBySW";// SW查询拖车URL
	public static final String URL_TRAILER_QUERY_CS = "http://smartway.jiazi-it.com/index.php?m=Home&c=TrailerApi&a=getInfoByCS";// 客户查询拖车URL
	public static final String URL_TRAILER_QUERY_CT = "http://smartway.jiazi-it.com/index.php?m=Home&c=TrailerApi&a=getInfoByCT";// 报关行查询拖车URL
}
