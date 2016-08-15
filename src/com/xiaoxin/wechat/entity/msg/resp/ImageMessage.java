package com.xiaoxin.wechat.entity.msg.resp;

/**
 * 
 * @标题: ImageMessage.java
 * @描述: 图片消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:20:45
 * @版本: V1.0
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String MediaId ;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String MediaId) {
		this.MediaId = MediaId;
	}


}
