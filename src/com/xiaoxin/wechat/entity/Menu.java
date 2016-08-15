package com.xiaoxin.wechat.entity;

import java.util.List;

/**
 * 
 * @描述: 菜单
 * @标题: Menu.java
 * @作者: chen changxiong
 * @日期: 2015-8-13 上午10:03:54
 * @版本: V1.0
 */
public class Menu {

	public Menu() {

	}

	/**
	 * 一级菜单
	 * 
	 * @param name
	 * @param sub_button
	 *            子菜单列表
	 */
	public Menu(String name, List<Menu> sub_button) {
		super();
		this.name = name;
		this.sub_button = sub_button;
	}

	/**
	 * 二级菜单
	 * 
	 * @param type（view、click）
	 * @param name
	 * @param key
	 * @param url
	 */
	public Menu(String type, String name, String key, String url) {
		super();
		this.type = type;
		this.name = name;
		this.key = key;
		this.url = url;
	}

	private String type; // 菜单的响应动作类型，目前有click、view两种类型
	private String name; // 菜单标题，不超过16个字节，子菜单不超过40个字节
	private String key; // 菜单KEY值，用于消息接口推送，不超过128字节 click类型必须

	private String url; // 网页链接，用户点击菜单可打开链接，不超过256字节 view类型必须

	private List<Menu> sub_button; // 子菜单

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Menu> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Menu> sub_button) {
		this.sub_button = sub_button;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
