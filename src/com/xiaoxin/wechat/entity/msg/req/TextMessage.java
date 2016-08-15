package com.xiaoxin.wechat.entity.msg.req;

/**
 * 
 * @标题: TextMessage.java
 * @描述:	   文本消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:19:05
 * @版本: V1.0
 */
public class TextMessage extends BaseMessage {

	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

}
