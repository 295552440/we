package com.xiaoxin.entity;

public class User implements java.io.Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String id;
private String openid;
private String nickName;
private String headImage;
private Integer sex; //性别
private String city;//城市
private String country;//国家
private String province;//省
private String language; //语言
private String weatherCity;
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getOpenid() {
	return openid;
}
public void setOpenid(String openid) {
	this.openid = openid;
}
public String getNickName() {
	return nickName;
}
public void setNickName(String NickName) {
	this.nickName = NickName;
}
public String getHeadImage() {
	return headImage;
}
public void setHeadImage(String headImage) {
	this.headImage = headImage;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getWeatherCity() {
	return weatherCity;
}
public void setWeatherCity(String weatherCity) {
	this.weatherCity = weatherCity;
}
public Integer getSex() {
	return sex;
}
public void setSex(Integer sex) {
	this.sex = sex;
}
public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public String getProvince() {
	return province;
}
public void setProvince(String province) {
	this.province = province;
}
public String getLanguage() {
	return language;
}
public void setLanguage(String language) {
	this.language = language;
}

}
