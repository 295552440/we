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
 * @描述: 笑话接口
 * @标题: JokeAPI.java
 * @作者: chen changxiong
 * @日期: 2015-8-18 下午2:03:46
 * @版本: V1.0
 */
public class JokeAPI {

	private static Log log = LogFactory.getLog(JokeAPI.class);
	private static final String showapi_url = "http://route.showapi.com/341-1?page=page1&showapi_appid=6120&showapi_timestamp=20150818140050&time=2015-07-10&showapi_sign=2774865ebd834b70959efe0b74379d2a";
	private static int allPages = 1;

	// time 从这个时间以来最新的笑话,格式：yyyy-MM-dd ;page 第几页。每页最多返回20条
	public static String getJoke() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		
		int page=(int) (allPages * Math.random())+1;//随机取页
		log.info("----取当前页数：---"+page);
		String url = showapi_url.replace("20150818140050", timestamp).replace(
				"page1", page+"");
		String res = null;
		try {
			JSONObject jo = JSONObject.fromObject(HttpClientUtils.get(url));
			if (null != jo) {
				if (jo.containsKey("showapi_res_body")) {
					JSONObject jo_res_body = (JSONObject) jo
							.get("showapi_res_body");
					//int allNum = jo_res_body.getInt("allNum");
					allPages = jo_res_body.getInt("allPages");
					log.info("----笑话总页数：---"+allPages);
					//int currentPage = jo_res_body.getInt("currentPage");
					//int maxResult = jo_res_body.getInt("maxResult");
					// 笑话列表
					JSONArray ja = (JSONArray) jo_res_body.get("contentlist");
					int index = (int) (19 * Math.random());//随机去挑
					log.info("----取当前条数：---"+index);
					res = "讲个笑话\ue11b：\n---------------------------------------\n";
					res = res + ja.getJSONObject(index).getString("text")+"\n---------------------------------------\n回复3换个笑话\ue11b\n发送0返回主菜单\ue415";

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
