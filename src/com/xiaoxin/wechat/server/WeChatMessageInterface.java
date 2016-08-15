package com.xiaoxin.wechat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiaoxin.util.Common;
import com.xiaoxin.wechat.service.WeChatMessageService;
import com.xiaoxin.wechat.util.WechatCommonUtil;

/**
 * 
 * @描述: 微信消息接收接口
 * @标题: WeChatMessageInterface.java
 * @作者: chen changxiong
 * @日期: 2015-8-6 下午4:45:57
 * @版本: V1.0
 */
public class WeChatMessageInterface extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2993718776657731992L;
	private static Log log = LogFactory.getLog(WeChatMessageInterface.class);

	/**
	 * 确认请求来自微信服务器
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		log.info("---doget---");
		/*
		 * signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		 * timestamp 时间戳 nonce 随机数 echostr 随机字符串
		 */
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		log.info("signature:" + signature);
		log.info("timestamp:" + timestamp);
		log.info("nonce:" + nonce);
		log.info("echostr:" + echostr);

		PrintWriter out = resp.getWriter();
		// 通过检验signature对请求进行校验，若检验成功则原样返回echostr，表示接入成功，否则接入失败
		if (WechatCommonUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");

		log.info("dopost sid: " + req.getSession().getId());
		InputStream in = req.getInputStream();
		String msg = "";
		if (in != null) {
			try {
				//接收到的消息不为空，处理消息并返回msg
				msg = new WeChatMessageService(req, resp).service();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("------返回数据 resp --------:\n" + msg);
		if (Common.isNotEmptyString(msg)) {
			resp.getWriter().print(msg);
		} else {
			resp.getWriter().print(" ");
		}
	}

}
