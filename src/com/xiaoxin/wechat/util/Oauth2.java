package com.xiaoxin.wechat.util;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;


import com.xiaoxin.wechat.entity.User;
import com.xiaoxin.wechat.service.WeChatAPIConfig;

/**
 * 
 * @描述: 网页授权获取用户基本信息
 * @标题: Oauth2.java
 * @作者: chen changxiong
 * @日期: 2015-8-14 上午9:47:23
 * @版本: V1.0
 */
public class Oauth2 {

	// 以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）
	public static final String snsapi_base = "snsapi_base";
	// 以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。
	public static final String snsapi_userinfo = "snsapi_userinfo";

	private static Log log = LogFactory.getLog(Oauth2.class);

	
	private String accessToken = null;

	
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(HttpServletRequest req) {
		this.accessToken = "";
	}


	
	
	/**
	 * 
	 * @Title: getOauth2Url
	 * @Description: 装饰Oauth2请求Code的URL
	 * @param @param scope应用授权作用域
	 * @param @param url授权后重定向的回调链接地址
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	public static String getOauth2CodeUrl(String scope, String url) {

		String Oauth2Url = WeChatAPIConfig.Oauth2_CODE_URL
				.replace("APPID", WeChatAPIConfig.APP_ID)
				.replace("SCOPE", scope)
				.replace("REDIRECT_URI", URLEncoder.encode(url));

		return Oauth2Url;
	}

	/**
	 * 
	 * @Title: getOauth2_AccessToken
	 * @Description: 通过code换取网页授权access_token
	 * @param @param code
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getOauth2_AccessToken_Result(String code) {

		String url = WeChatAPIConfig.Oauth2_ACCESS_TOKEN_URL
				.replace("APPID", WeChatAPIConfig.APP_ID)
				.replace("SECRET", WeChatAPIConfig.APP_SECRET)
				.replace("CODE", code);
		try {
			String json = HttpClientUtils.get(url);
			log.info("----json----:" + json);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getOauth2_openid
	 * @Description: 仅获取openid
	 * @param @param code
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getOauth2_openid(String json) {
		JSONObject jo = JSONObject.fromObject(json);
		if (null != jo) {
			if (jo.containsKey("errcode")) {
				log.info("errmsg:invalid code");
			} else {
				String openid = jo.getString("openid");
				log.info("----openid:----" + openid);
				return openid;
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: getOauth2_AccessToken
	 * @Description: 仅获取AccessToken
	 * @param @param code
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getOauth2_AccessToken(String json) {
		JSONObject jo = JSONObject.fromObject(json);
		if (null != jo) {
			if (jo.containsKey("errcode")) {
				log.info("errmsg:invalid code");
			} else {
				String access_token = jo.getString("access_token");
				log.info("----access_token:----" + access_token);
				return access_token;
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: getOauth2_UserInfo
	 * @Description: 拉取用户信息(需scope为 snsapi_userinfo)
	 * @param @param openid
	 * @param @param accessToken
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static User getOauth2_UserInfo(String openid, String accessToken) {

		String url = WeChatAPIConfig.Oauth2_UserInfo_URL.replace(
				"ACCESS_TOKEN", accessToken).replace("OPENID", openid);
		try {
			String json = HttpClientUtils.get(url);
			log.info("----userinfo---json----:" + json);

			JSONObject jo = JSONObject.fromObject(json);
			if (null != jo) {
				if (jo.containsKey("errcode")) {
					log.info("errmsg:invalid openid ");
				} else {
					User user = new User();
					user.setOpenid(openid);
					user.setNickname(jo.getString("nickname"));
					user.setSex(jo.getInt("sex"));
					user.setCity(jo.getString("city"));
					user.setCountry(jo.getString("country"));
					user.setProvince(jo.getString("province"));
					user.setLanguage(jo.getString("language"));
					user.setHeadimgurl(jo.getString("headimgurl"));
					//user.setUnionid(jo.getString("unionid"));

					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: main
	 * @Description: 生成网页授权获取用户基本信息的地址
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		String url = "http://q295552440.imwork.net/xiaoxin/oauth2-test";
		// System.out.println(getOauth2CodeUrl(snsapi_base, url));
		System.out.println(getOauth2CodeUrl(snsapi_userinfo, url));
	}
}
