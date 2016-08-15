package com.xiaoxin.wechat.service;

import java.util.Timer;
import java.util.TimerTask;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoxin.wechat.entity.User;
import com.xiaoxin.wechat.pojo.AccessToken;
import com.xiaoxin.wechat.util.HttpClientUtils;
import com.xiaoxin.wechat.util.MenuManager;

public class WeChatAPIRequest {

	private static Log log = LogFactory.getLog(WeChatAPIRequest.class);

	private static WeChatAPIRequest weChatAPIRequest = new WeChatAPIRequest();
	// 刷新token时间
	private long time = 1000 * 60 * 50 * 2;
	// token
	private AccessToken accessToken = null;

	/**
	 * 构造WeChatAPIRequest
	 */
	private WeChatAPIRequest() {
		try {
			setAccessToken();
			log.info("setAccessToken");

			Timer t = new Timer();
			t.schedule(new TimerTask() {
				public void run() {
					try {
						WeChatAPIRequest.resetAccessToken();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, time, time);
			// 第一个参数是要指定的任务，第二个参数是要设定延迟的时间，第三个参数是周期的设定，每隔多长时间执行该操作
			// timer.schedule(task, delay,
			// period)delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: requestAccessToken
	 * @Description:向微信请求获取AccessToken
	 */
	private AccessToken requestAccessToken() throws Exception {
		StringBuffer buffer = new StringBuffer(
				WeChatAPIConfig.WEIXIN_SERVER_URL);
		buffer.append(WeChatAPIConfig.ACCESS_TOKEN_URL);
		buffer.append("&appid=").append(WeChatAPIConfig.APP_ID);
		buffer.append("&secret=").append(WeChatAPIConfig.APP_SECRET);

		String json = HttpClientUtils.get(buffer.toString());
		JSONObject jo = JSONObject.fromObject(json);
		if (jo != null) {
			return new AccessToken(jo.getString("access_token"),
					jo.getInt("expires_in"));
		}
		return null;
	}

	/**
	 * 
	 * @Title: requestWeChatServerIP
	 * @Description: 获取微信服务器IP
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private String requestWeChatServerIP() throws Exception {
		String requestUrl = WeChatAPIConfig.WECHAT_SERVER_IP_URL.replace(
				"ACCESS_TOKEN", accessToken.getAccess_token());

		String json = HttpClientUtils.get(requestUrl);
		JSONObject jo = JSONObject.fromObject(json);
		if (jo != null) {
			log.info(jo.getString("ip_list"));
			return jo.getString("ip_list");

		}
		return null;

	}



	/**
	 * 
	 * @Title: resetAccessToken
	 * @Description:重置AccessToken
	 */
	public static void resetAccessToken() throws Exception {
		weChatAPIRequest.setAccessToken();
		log.info("resetAccessToken");
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken() throws Exception {
		this.accessToken = requestAccessToken();
	}

	public static WeChatAPIRequest getWeChatAPIRequest() {
		return weChatAPIRequest;
	}

	public static void main(String[] args) throws Exception {
		// 1、获得AccessToken
		// WeChatAPIRequest.getWeChatAPIRequest().getAccessToken();
		// 2、获得服务器IP地址
		// WeChatAPIRequest.getWeChatAPIRequest().requestWeChatServerIP();
	}

}
