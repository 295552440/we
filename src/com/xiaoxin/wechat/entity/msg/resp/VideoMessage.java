package com.xiaoxin.wechat.entity.msg.resp;

/**
 * 
 * @描述: 视频消息
 * @标题: VideoMessage.java
 * @作者: chen changxiong
 * @日期: 2015-8-12 下午4:02:54
 * @版本: V1.0
 */
public class VideoMessage extends BaseMessage {
	private String MediaId;
	private String Title;
	private String Description;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

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

}
