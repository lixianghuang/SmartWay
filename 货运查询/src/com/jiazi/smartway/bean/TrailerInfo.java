package com.jiazi.smartway.bean;

import java.io.Serializable;
import java.util.List;

public class TrailerInfo implements Serializable {
	public String id;
	public String date;// 装柜日期
	public String factory_name;// 工厂名
	public String invoice_num;// 发票号
	public String cabin_num;// 订舱号
	public List<String> cabinet_num_type;// 柜号柜型
	public String seal_num;// 封条号
	public String plate_num;// 车牌号
	public String driver_phone;// 司机手机号
}
