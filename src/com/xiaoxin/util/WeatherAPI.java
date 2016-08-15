package com.xiaoxin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.xiaoxin.wechat.util.HttpClientUtils;

/**
 * 
 * @描述: 天气接口
 * @标题: WeatherAPI.java
 * @作者: chen changxiong
 * @日期: 2015-8-17 下午3:42:07
 * @版本: V1.0
 */
public class WeatherAPI {
	private static Log log = LogFactory.getLog(WeatherAPI.class);

	private static final String showapi_url = "http://route.showapi.com/9-2?area=丽江&needIndex=0&needMoreDay=0&showapi_appid=6120&showapi_timestamp=20150817171859&showapi_sign=2774865ebd834b70959efe0b74379d2a";

	public static String getWeatherByCityName(String name) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		String url = showapi_url.replace("丽江", name).replace("20150817171859",
				timestamp);
		String res = null;
		try {
			JSONObject jo = JSONObject.fromObject(HttpClientUtils.get(url));
			if (null != jo) {
				if (jo.containsKey("showapi_res_body")) {
					JSONObject jo_res_body = (JSONObject) jo
							.get("showapi_res_body");

					if ("0".equals(jo_res_body.getString("ret_code"))) {
						// 今天的天气预报
						JSONObject f1 = (JSONObject) jo_res_body.get("f1");
						String day_weather = f1.getString("day_weather");
						String night_weather = f1.getString("night_weather");
						String day_air_temperature = f1
								.getString("day_air_temperature");
						String night_air_temperature = f1
								.getString("night_air_temperature");
						String day_wind_direction = f1
								.getString("day_wind_direction");
						String day_wind_power = f1.getString("day_wind_power");
						String night_wind_power = f1
								.getString("night_wind_power");
						String sun_begin_end = f1.getString("sun_begin_end");

						res = "今天天气情况：\n"
								+ "---------------------------------------\n"
								+ "\ue110白天："
								+ day_weather
								+ "，"
								+ day_air_temperature
								+ "℃，"
								+ day_wind_direction
								+ "，"
								+ day_wind_power
								+ ";\n\ue110夜间："
								+ night_weather
								+ "，"
								+ night_air_temperature
								+ "℃，"
								+ night_wind_power
								+ "\n\ue110日出日落："
								+ sun_begin_end
								+ "\n---------------------------------------\n输入城市名查询别的城市天气\n发送0返回主菜单\ue415";

					} else if ("-1".equals(jo_res_body.getString("ret_code"))) {

						res = "找不到此地名,请正确输入！";
					} else {
						res = "发生错误，请重试";

					}

				} else {
					res = "发生错误，请重试";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = "发生错误，请重试";
		}

		return res;
	}

	public static JSONObject getWeatherJsonByCityName(String name) {
		JSONObject json = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		String url = showapi_url.replace("丽江", name).replace("20150817171859",
				timestamp);
		try {
			JSONObject jo = JSONObject.fromObject(HttpClientUtils.get(url));
			if (null != jo) {
				if (jo.containsKey("showapi_res_body")) {
					JSONObject jo_res_body = (JSONObject) jo
							.get("showapi_res_body");
					if ("0".equals(jo_res_body.getString("ret_code"))) {
						// 现在和未来几天天气预报
						return jo_res_body;
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
