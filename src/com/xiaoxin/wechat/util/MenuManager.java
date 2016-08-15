package com.xiaoxin.wechat.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoxin.wechat.entity.Menu;
import com.xiaoxin.wechat.service.WeChatAPIConfig;
import com.xiaoxin.wechat.service.WeChatAPIRequest;

/**
 * 
 * @描述: 菜单管理器类
 * @标题: MenuManager.java
 * @作者: chen changxiong
 * @日期: 2015-8-13 上午10:08:46
 * @版本: V1.0
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) throws Exception {

		// 重构菜单
		requestMenu(createMenu(), WeChatAPIRequest.getWeChatAPIRequest()
				.getAccessToken().getAccess_token());
	}

	public static List<Menu> createMenu() {
		// 整个菜单列表
		List<Menu> menuList = new ArrayList<Menu>();
		// 一级菜单1的子菜单
		Menu m_01_01 = new Menu("click", "菜单1-1(查看我的信息)", "0101", null);
		Menu m_01_02 = new Menu("click", "菜单1-2", "0102", null);
		Menu m_01_03 = new Menu("click", "菜单1-3", "0103", null);
		// 一级菜单2的子菜单
		String url = "http://q295552440.imwork.net/xiaoxin/oauth2-test";
		Menu m_02_01 = new Menu("view", "菜单2-1(snsapi_base静默授权)", null,Oauth2.getOauth2CodeUrl("snsapi_base", url));
		Menu m_02_02 = new Menu("view", "菜单2-2(snsapi_userinfo手动授权)", null,Oauth2.getOauth2CodeUrl("snsapi_userinfo", url));
		// 一级菜单3的子菜单
		Menu m_03_01 = new Menu("view", "菜单3-1", null, "http://www.baidu.com/");
		Menu m_03_02 = new Menu("view", "菜单3-2", null, "http://www.baidu.com/");

		List<Menu> m1 = new ArrayList<Menu>();
		List<Menu> m2 = new ArrayList<Menu>();
		List<Menu> m3 = new ArrayList<Menu>();

		m1.add(m_01_01);
		m1.add(m_01_02);
		m1.add(m_01_03);

		m2.add(m_02_01);
		m2.add(m_02_02);

		m3.add(m_03_01);
		m3.add(m_03_02);

		// 一级菜单
		Menu m_01 = new Menu("菜单1", m1);
		Menu m_02 = new Menu("菜单2", m2);
		Menu m_03 = new Menu("菜单3", m3);

		// 只有一级菜单的情况
		// Menu m_01=new Menu("view","菜单1",null,"http://www.baidu.com/");
		// Menu m_02=new Menu("view","菜单2",null,"http://www.baidu.com/");
		// Menu m_03=new Menu("view","菜单3",null,"http://www.baidu.com/");

		menuList.add(m_01);
		menuList.add(m_02);
		menuList.add(m_03);

		return menuList;
	}

	public static void requestMenu(List<Menu> list, String accessToken)
			throws Exception {

		// 拼装创建菜单的url
		String url = WeChatAPIConfig.MENU_CREATE_URL.replace("ACCESS_TOKEN",
				accessToken);
		// 将菜单对象转换成json字符串
		JSONArray ja = JSONArray.fromObject(list);
		JSONObject jo = new JSONObject();
		jo.put("button", ja);
		String jmenu = jo.toString();
		// 调用接口创建菜单
		JSONObject jsonObject = JSONObject.fromObject(HttpClientUtils.post(url,
				jmenu, ""));
		int result = 0;
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			} else {

				log.info("==========创建菜单成功！=============");
			}
		}
	}
}
