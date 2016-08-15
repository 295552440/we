package com.xiaoxin.wechat.entity.msg.req;

/**
 * 
 * @标题: LinkMessage.java
 * @描述: 链接消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:22:56
 * @版本: V1.0
 */
public class LinkMessage extends BaseMessage {
	// 消息标题
	private String Title;
	// 消息描述
	private String Description;
	// 消息链接
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}