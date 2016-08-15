package com.xiaoxin.wechat.entity.msg.resp;

import com.xiaoxin.wechat.entity.Music;;

/**
 * 
 * @标题: MusicMessage.java
 * @描述: 音乐消息
 * @作者: chen changxiong
 * @日期: 2015-5-16 下午3:27:53
 * @版本: V1.0
 */
public class MusicMessage extends BaseMessage {
	// 音乐
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
}