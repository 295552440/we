package com.xiaoxin.wechat.pojo;

/**
 * 
 * @描述:* 微信通用接口凭证
 * 
 *      调用获取凭证接口后，微信服务器会返回json格式的数据：{"access_token":"ACCESS_TOKEN","expires_in":
 *      7200} ，我们将其封装为一个AccessToken对象，对象有二个属性：token和expiresIn
 * 
 *      access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。开发者需要进行妥善保存。
 *      access_token的存储至少要保留512个字符空间。
 *      access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取的access_token失效。
 * 
 * @标题: AccessToken.java
 * @作者: chen changxiong
 * @日期: 2015-8-6 下午5:20:32
 * @版本: V1.0
 */
public class AccessToken {
	public AccessToken(String access_token, Integer expires_in) {

		this.access_token = access_token;
		this.expires_in = expires_in;
	}

	private String access_token; // 获取到的凭证
	private Integer expires_in; // 凭证有效时间，单位：秒

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

}