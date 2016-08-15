package com.xiaoxin.wechat.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.xiaoxin.dao.UserDao;
import com.xiaoxin.util.DaoBanBiao;
import com.xiaoxin.util.HistoryAPI;
import com.xiaoxin.util.JokeAPI;
import com.xiaoxin.util.TulingRobot;
import com.xiaoxin.util.WeatherAPI;
import com.xiaoxin.wechat.entity.Article;
import com.xiaoxin.wechat.entity.User;
import com.xiaoxin.wechat.entity.msg.resp.NewsMessage;
import com.xiaoxin.wechat.entity.msg.resp.TextMessage;
import com.xiaoxin.wechat.service.WeChatAPIConfig;
import com.xiaoxin.wechat.service.WeChatAPIRequest;

/**
 * 
 * @描述: 消息业务处理
 * @标题: MessageServiceUtil.java
 * @作者: chen changxiong
 * @日期: 2015-8-17 下午2:40:57
 * @版本: V1.0
 */
public class MessageServiceUtil {
	private static final String HOME = "home";
	private static final String WEATHER = "weather";
	private static final String DAOBANBIAO = "daobanbiao";
	private static final String CHAT = "chat";

	private static String state = HOME;
	private static UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 图文导航
	 * 
	 * @param content
	 * @param myUserName
	 * @param openid
	 * @return
	 */
	public static String textMessageService2(String content, String myUserName,
			String openid) {
		content = content.trim();
		TextMessage tm = new TextMessage();
		tm.setFromUserName(myUserName);
		tm.setToUserName(openid);
		String respContent = "";
		com.xiaoxin.entity.User u;
		// 保存用户信息
		u = userDao.queryUserByOpenid(openid);
		if (u == null) {
			u = new com.xiaoxin.entity.User();
			u.setOpenid(openid);
			userDao.save(u);
		}

		// 任何情况下，回复0都能回到首页
		if ("0".equals(content)) {
			state = HOME;

			NewsMessage nm = new NewsMessage();
			nm.setFromUserName(myUserName);
			nm.setToUserName(openid);
			nm.setArticleCount(2);
			// 文章1
			Article a1 = getAtricleByCity(u.getCity());
			// 文章2
			Article a2 = new Article();
			a2.setDescription("文章2内容");
			a2.setPicUrl("http://img03.sogoucdn.com/app/a/100520024/16bea55a8a18c9b099706cb5409cba63");
			a2.setTitle("文章2标题");
			a2.setUrl("https://www.baidu.com");
			List<Article> articles = new ArrayList<Article>();
			articles.add(a1);
			articles.add(a2);
			nm.setArticles(articles);
			return MessageUtil.newsMessageToXml(nm);
			// respContent = WeChatAPIConfig.HOME_MENU;
		} else if ("1".equals(content) && state == HOME) {
			state = WEATHER;
			respContent = "请输入要查询的城市\ue43d";

		} else if (state == WEATHER) {

			respContent = WeatherAPI.getWeatherByCityName(content);
		} else if ("2".equals(content) && state == HOME) {

			respContent = HistoryAPI.getHistoryByDay();
		} else if ("3".equals(content) && state == HOME) {

			respContent = JokeAPI.getJoke();
		} else if ("4".equals(content) && state == HOME) {
			state = DAOBANBIAO;

			StringBuffer buffer = new StringBuffer();
			buffer.append(
					"今日各班的作息情况\ue025：\n---------------------------------------\n(上下依次对应)\n")
					.append("甲——乙——丙——丁\n")
					.append(DaoBanBiao.queryStatesByDate(new Date()))
					.append("\n---------------------------------------\n查询其他日期请直接输入日期，如'20150612'(注意格式)\n发送0返回主菜单\ue415");

			respContent = buffer.toString();
		} else if (state == DAOBANBIAO) {
			try {
				StringBuffer buffer = new StringBuffer();
				buffer.append(
						content
								+ "各班的作息情况\ue025：\n---------------------------------------\n(上下依次对应)\n")
						.append("甲——乙——丙——丁\n")
						.append(DaoBanBiao.queryStatesByString(content))
						.append("\n---------------------------------------\n查询其他日期请直接输入日期，如'20150612'(注意格式)\n发送0返回主菜单\ue415");

				respContent = buffer.toString();
			} catch (Exception e) {
				respContent = "输入错误，请注意格式：如'20150612'";
			}

		} else if ("5".equals(content) && state == HOME) {

			String accessToken = WeChatAPIRequest.getWeChatAPIRequest()
					.getAccessToken().getAccess_token();
			try {
				User user = UserUtil.requestUserInfo(openid, accessToken);
				String userInfo = "昵称：" + user.getNickname() + "\n" + "城市："
						+ user.getProvince() + user.getCity() + "\n" + "性别："
						+ ((user.getSex() == 1) ? "男\ue138" : "女\ue139");
				StringBuffer buffer = new StringBuffer();
				buffer.append(
						"您的个人信息\ue51a：\n---------------------------------------\n")
						.append(userInfo)
						.append("\n---------------------------------------\n发送0返回主菜单\ue415");

				respContent = buffer.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// respContent = "发生错误，请重试";
				String url = "http://q295552440.imwork.net/xiaoxin/oauth2-test";
				url = Oauth2.getOauth2CodeUrl("snsapi_base", url);
				respContent = "需要特殊授权，请点击链接\n<a href=\'" + url
						+ "\'>我的个人信息</a>";
			}

		} else if ("6".equals(content) && state == HOME) {
			state = CHAT;
			respContent = "客官，你好！我是小信，随便说点什么吧\ue327";
		} else if (state == CHAT) {

			if ("你是谁".equals(content) || "你叫什么名字".equals(content)
					|| "你是谁？".equals(content) || "你叫什么名字？".equals(content)
					|| "你的名字".equals(content)) {

				respContent = "都告诉你我叫小信啦~";
			} else {
				respContent = TulingRobot.getRobotReply(content);
			}
		} else {
			state = HOME;
			respContent = WeChatAPIConfig.HOME_MENU;
		}
		tm.setContent(respContent);

		return MessageUtil.textMessageToXml(tm);
	}

	/**
	 * 文本导航
	 * 
	 * @param content
	 * @param myUserName
	 * @param openid
	 * @return
	 */
	public static String textMessageService(String content, String myUserName,
			String openid) {
		content = content.trim();
		TextMessage tm = new TextMessage();
		tm.setFromUserName(myUserName);
		tm.setToUserName(openid);
		String respContent = "";
		// 任何情况下，回复0都能回到首页
		if ("0".equals(content)) {
			state = HOME;
			respContent = WeChatAPIConfig.HOME_MENU;
		} else if ("1".equals(content) && state == HOME) {
			state = WEATHER;
			respContent = "请输入要查询的城市\ue43d";

		} else if (state == WEATHER) {

			respContent = WeatherAPI.getWeatherByCityName(content);
		} else if ("2".equals(content) && state == HOME) {

			respContent = HistoryAPI.getHistoryByDay();
		} else if ("3".equals(content) && state == HOME) {

			respContent = JokeAPI.getJoke();
		} else if ("4".equals(content) && state == HOME) {
			state = DAOBANBIAO;

			StringBuffer buffer = new StringBuffer();
			buffer.append(
					"今日各班的作息情况\ue025：\n---------------------------------------\n(上下依次对应)\n")
					.append("甲——乙——丙——丁\n")
					.append(DaoBanBiao.queryStatesByDate(new Date()))
					.append("\n---------------------------------------\n查询其他日期请直接输入日期，如'20150612'(注意格式)\n发送0返回主菜单\ue415");

			respContent = buffer.toString();
		} else if (state == DAOBANBIAO) {
			try {
				StringBuffer buffer = new StringBuffer();
				buffer.append(
						content
								+ "各班的作息情况\ue025：\n---------------------------------------\n(上下依次对应)\n")
						.append("甲——乙——丙——丁\n")
						.append(DaoBanBiao.queryStatesByString(content))
						.append("\n---------------------------------------\n查询其他日期请直接输入日期，如'20150612'(注意格式)\n发送0返回主菜单\ue415");

				respContent = buffer.toString();
			} catch (Exception e) {
				respContent = "输入错误，请注意格式：如'20150612'";
			}

		} else if ("5".equals(content) && state == HOME) {

			String accessToken = WeChatAPIRequest.getWeChatAPIRequest()
					.getAccessToken().getAccess_token();
			try {
				User user = UserUtil.requestUserInfo(openid, accessToken);
				String userInfo = "昵称：" + user.getNickname() + "\n" + "城市："
						+ user.getProvince() + user.getCity() + "\n" + "性别："
						+ ((user.getSex() == 1) ? "男\ue138" : "女\ue139");
				StringBuffer buffer = new StringBuffer();
				buffer.append(
						"您的个人信息\ue51a：\n---------------------------------------\n")
						.append(userInfo)
						.append("\n---------------------------------------\n发送0返回主菜单\ue415");

				respContent = buffer.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// respContent = "发生错误，请重试";
				String url = "http://q295552440.imwork.net/xiaoxin/oauth2-test";
				url = Oauth2.getOauth2CodeUrl("snsapi_base", url);
				respContent = "需要特殊授权，请点击链接\n<a href=\'" + url
						+ "\'>我的个人信息</a>";
			}

		} else if ("6".equals(content) && state == HOME) {
			state = CHAT;
			respContent = "客官，你好！我是小信，随便说点什么吧\ue327";
		} else if (state == CHAT) {

			if ("你是谁".equals(content) || "你叫什么名字".equals(content)
					|| "你是谁？".equals(content) || "你叫什么名字？".equals(content)
					|| "你的名字".equals(content)) {

				respContent = "都告诉你我叫小信啦~";
			} else {
				respContent = TulingRobot.getRobotReply(content);
			}
		} else {
			state = HOME;
			respContent = WeChatAPIConfig.HOME_MENU;
		}
		tm.setContent(respContent);

		return MessageUtil.textMessageToXml(tm);
	}

	/**
	 * 返回天气文章
	 */

	public static Article getAtricleByCity(String city) {
		Article a1 = new Article();
		JSONObject jo = WeatherAPI.getWeatherJsonByCityName(city);
		String titie = "暂无天气信息";
		String picUrl = "";
		if (jo != null) {
			JSONObject now = jo.getJSONObject("now");
			JSONObject aqiDetail_now = now.getJSONObject("aqiDetail");
			titie = city + "：" + now.getString("weather") + ""
					+ now.getString("temperature") + "℃ " + " "
					+ now.getString("wind_direction") + ""
					+ now.getString("wind_power") + " 空气质量"
					+ aqiDetail_now.getString("quality");
			picUrl = now.getString("weather_pic");
		}
		a1.setDescription(null);
		a1.setPicUrl(picUrl);
		a1.setTitle(titie);
		a1.setUrl("https://www.baidu.com");
		return a1;
	}
}
