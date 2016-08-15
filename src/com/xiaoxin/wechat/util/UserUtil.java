package com.xiaoxin.wechat.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONObject;

import com.xiaoxin.wechat.entity.User;
import com.xiaoxin.wechat.service.WeChatAPIConfig;


public class UserUtil {

	private static Log log = LogFactory.getLog(UserUtil.class);
	/**
	 * @throws Exception
	 * 
	 * @Title: requestUserInfo
	 * @Description: 获取用户基本信息（包括UnionID机制）
	 * @param @return 设定文件
	 * @return User 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	public static User requestUserInfo(String openid, String accessToken)
			throws Exception {
		
		String url = WeChatAPIConfig.User_INFO_URL.replace("ACCESS_TOKEN",
				accessToken).replace("OPENID", openid);
		JSONObject jo = JSONObject.fromObject(HttpClientUtils.get(url));
		if (null != jo) {
			if (jo.containsKey("errcode")) {
				log.info("errmsg:invalid appid");
			} else {
				User user = new User();
				user.setOpenid(openid);
				user.setSubscribe(jo.getInt("subscribe"));
				user.setNickname(jo.getString("nickname"));
				user.setSex(jo.getInt("sex"));
				user.setCity(jo.getString("city"));
				user.setCountry(jo.getString("country"));
				user.setProvince(jo.getString("province"));
				user.setLanguage(jo.getString("language"));
				user.setHeadimgurl(jo.getString("headimgurl"));
				user.setSubscribe_time(jo.getString("subscribe_time"));
				user.setRemark(jo.getString("remark"));
				//user.setUnionid(jo.getString("unionid"));
				user.setGroupid(jo.getString("groupid"));

				return user;

			}
		}
		return null;
	}
}
