package com.xiaoxin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaoBanBiao {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println(queryStatesByDate(new Date()));
		System.out.println(queryStatesByString("20150415"));
	}

	// 定义2015年4月15日为基准线
	public static Long Date_Base = 1429027200000L;
	// 规定从4月15日到4月22日期间8天内为一个基准单元
	public static String day_1 = "1320";
	public static String day_2 = "1032";
	public static String day_3 = "0132";
	public static String day_4 = "2103";
	public static String day_5 = "2013";
	public static String day_6 = "3210";
	public static String day_7 = "3201";
	public static String day_8 = "0321";

	/**
	 * 根据日期查询各班的作息情况
	 * 
	 * @param date
	 * @param Ban
	 * @return
	 */

	public static String queryStatesByDate(Date date) {

		Long queryDate = date.getTime();
		String states = null;
		int allDays = (int) ((queryDate - Date_Base) / 86400000);
		int days = allDays % 8;
		// 查询的日期早于基准日期
		if (days < 0) {
			days = days + 8;
		}

		switch (days) {
		case 0:
			states = day_1;
			break;
		case 1:
			states = day_2;
			break;
		case 2:
			states = day_3;
			break;
		case 3:
			states = day_4;
			break;
		case 4:
			states = day_5;
			break;
		case 5:
			states = day_6;
			break;
		case 6:
			states = day_7;
			break;
		case 7:
			states = day_8;
			break;

		default:
			states = null;
			break;
		}

		return transfer(states);
	}
 
	/**
	 * 根据日期查询各班的作息情况:日期格式yyyyMMdd
	 * 
	 * @param str_date
	 * @return
	 */
	public static String queryStatesByString(String str_date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Date date = null;
		try {
			date = sdf.parse(str_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return queryStatesByDate(date);

	}

	/**
	 * 将代号转成实际中文
	 * 
	 * @param states
	 * @return
	 */
	public static String transfer(String states) {
		
		states = states.replace("0", "休").replace("1", "夜").replace("2", "白")
				.replace("3", "中");
		StringBuffer sb=new StringBuffer();
		sb.append(states.substring(0,1)).append("——").append(states.substring(1,2)).append("——").append(states.substring(2,3)).append("——").append(states.substring(3,4));
	    
		return sb.toString();
	}
}
