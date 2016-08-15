package com.xiaoxin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoxin.wechat.util.HttpClientUtils;

/**
 * 
 * @描述: 图灵机器人
 * @标题: TulingRobot.java
 * @作者: chen changxiong
 * @日期: 2015-8-19 上午8:54:15 
 * @版本: V1.0
 */
public class TulingRobot {
	private static Log log = LogFactory.getLog(TulingRobot.class);
	private static final String showapi_url = "http://route.showapi.com/60-27?info=我是谁&showapi_appid=6120&showapi_timestamp=20150819084326&userid=userid&showapi_sign=2774865ebd834b70959efe0b74379d2a";
	public static String getRobotReply(String content) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp=sdf.format(new Date());
		String url = showapi_url.replace("我是谁", content).replace("20150819084326",timestamp);
		String res = null;
		try {
			JSONObject jo = JSONObject.fromObject(HttpClientUtils.get(url));
			if (null != jo) {
				if (jo.containsKey("showapi_res_body")) {
					JSONObject jo_res_body = (JSONObject) jo
							.get("showapi_res_body");

					if ("0".equals(jo_res_body.getString("ret_code"))) {
						//今天的天气预报 
						
						
						res =jo_res_body.getString("text");
						
					}else{
						res = "呃呃，人家脑子有点乱~~";
						
					}

				} else {
					res = "呃呃，人家脑子有点乱~~";
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = "呃呃，人家脑子有点乱~~";
		}

		return res;
	}



}
