package com.xiaoxin.wechat.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoxin.wechat.entity.User;
import com.xiaoxin.wechat.util.Oauth2;

/**
 * 
 * @描述:
 * @标题: Oauth2TestServer.java
 * @作者: chen changxiong
 * @日期: 2015-8-14 上午11:25:23
 * @版本: V1.0
 */
public class Oauth2TestServer extends HttpServlet {
	private static final long serialVersionUID = -2993718776657731992L;
	private static Log log = LogFactory.getLog(Oauth2TestServer.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("---Oauth2TestServer---doget---");
		String code = req.getParameter("code");
		log.info("---code:---" + code);

		String json = Oauth2.getOauth2_AccessToken_Result(code);
		String openid = Oauth2.getOauth2_openid(json);
		String accessToken = Oauth2.getOauth2_AccessToken(json);
		User user = Oauth2.getOauth2_UserInfo(openid, accessToken);
		log.info("---user.getNickname():---" + user.getNickname());

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print("<html><head><title> 我的个人信息</title>");
		
		out.print("<meta content='width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no' name='viewport'>"
				+ "<meta content='yes' name='apple-mobile-web-app-capable'></head>");
		out.print("<body style='text-align: center;font-size:20px'>");
		String userInfo="昵称："+user.getNickname()+"<br>"+"城市："+user.getProvince()+user.getCity()+"<br>"+"性别："+((user.getSex()==1)?"男\ue138":"女\ue139");
		out.print(userInfo);
		out.print("</body></html>");
		out.close();
		out = null;
	}

}
