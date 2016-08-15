package com.xiaoxin.wechat.service;

public class WeChatAPIConfig {
	
	public static final String token = "xiaoxin2015";// 与接口配置信息中的Token要一致,令牌
	
	//小信
	/*public static final String APP_ID = "wxd03f5f8e57702976";
	   
	public static final String APP_SECRET ="2d2d65484312b9477915c1536879a5e1";
		
	public static final String TO_USER_NAME = "gh_e75b3c0bb4e0";*/
	//管理测试号
	public static final String APP_ID = "wx094a5185bade240d";
   
	public static final String APP_SECRET ="845c717b50f0f8073dfbaece823fdcd9";
	
	public static final String TO_USER_NAME = "gh_6e6a93ea5326";
	
	
	public static final String WEIXIN_SERVER_URL = "https://api.weixin.qq.com/cgi-bin";
	public static final String ACCESS_TOKEN_URL = "/token?grant_type=client_credential";
	public static final String WECHAT_SERVER_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	
	// 菜单创建（POST） 限100（次/天）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取用户基本信息（包括UnionID机制）
	public final static String User_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 网页授权获取用户基本信息(用户同意授权，获取code)
	public final static String Oauth2_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	// 网页授权获取用户基本信息(通过code换取网页授权access_token)
	public final static String Oauth2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 网页授权获取用户基本信息(拉取用户信息(需scope为 snsapi_userinfo))
	public final static String Oauth2_UserInfo_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";




//回复菜单
	public static final String HOME_MENU= "Hi\ue415，欢迎关注“你好小信”！\n"
			+ "---------------------------------------\n"
			+ "1、天气怎么样\ue04a\n" + "2、历史上的今天\ue536\n"
			+ "3、讲个笑话呗\ue11b\n" + "4、倒班表查询\ue025\n"
			+ "5、我的个人信息\ue51a\n" + "6、和小信聊天\ue327\n"
			+ "---------------------------------------\n"
			+ "回复数字查看相应内容\n发送0返回本菜单";

}
