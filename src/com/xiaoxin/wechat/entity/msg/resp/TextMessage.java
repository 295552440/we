package com.xiaoxin.wechat.entity.msg.resp;

/**
 * 
 * @标题: TextMessage.java
 * @描述: 文本消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:26:41
 * @版本: V1.0
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}