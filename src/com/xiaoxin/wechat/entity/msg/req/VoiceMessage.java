package com.xiaoxin.wechat.entity.msg.req;

/**
 * 
 * @标题: VoiceMessage.java
 * @描述: 音频消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:23:31
 * @版本: V1.0
 */
public class VoiceMessage extends BaseMessage {
	// 媒体ID
	private String MediaId;
	// 语音格式
	private String Format;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
}