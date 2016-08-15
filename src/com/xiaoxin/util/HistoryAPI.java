package com.xiaoxin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoxin.wechat.util.HttpClientUtils;

/**
 * 
 * @描述: 历史上的今天
 * @标题: HistoryAPI.java
 * @作者: chen changxiong
 * @日期: 2015-8-18 上午11:44:51
 * @版本: V1.0
 */
public class HistoryAPI {
	private static Log log = LogFactory.getLog(HistoryAPI.class);
	private static final String showapi_url = "http://route.showapi.com/119-42?date=0705&showapi_appid=6120&showapi_timestamp=20150818114112&showapi_sign=2774865ebd834b70959efe0b74379d2a";

	public static String getHistoryByDay() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		String url = showapi_url.replace("0705", "").replace("20150818114112",
				timestamp);
		String res = null;
		try {
			JSONObject jo = JSONObject.fromObject(HttpClientUtils.get(url));
			if (null != jo) {
				if (jo.containsKey("showapi_res_body")) {
					JSONObject jo_res_body = (JSONObject) jo
							.get("showapi_res_body");

					if ("0".equals(jo_res_body.getString("ret_code"))) {

						res = "历史上的今天\ue536：\n---------------------------------------\n";
						// 历史上的今天列表
						JSONArray ja = (JSONArray) jo_res_body.get("list");
						for (int i = 0; i < ja.size(); i++) {
							String day = i + 1 + "、";
							day = day + ja.getJSONObject(i).get("year") + "年"
									+ ja.getJSONObject(i).get("month") + "月"
									+ ja.getJSONObject(i).get("day") + "日："
									+ ja.getJSONObject(i).get("title") + "\n";
							res = res + day;
						}
						res = res
								+ "\n---------------------------------------\n发送0返回主菜单\ue415";

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

}
