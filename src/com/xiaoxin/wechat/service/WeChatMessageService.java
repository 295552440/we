package com.xiaoxin.wechat.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.xiaoxin.util.XMLUtil;
import com.xiaoxin.wechat.entity.User;
import com.xiaoxin.wechat.entity.msg.resp.TextMessage;
import com.xiaoxin.wechat.util.MessageServiceUtil;
import com.xiaoxin.wechat.util.MessageUtil;
import com.xiaoxin.wechat.util.UserUtil;

public class WeChatMessageService {
	private static Log log = LogFactory.getLog(WeChatMessageService.class);

	private Map<String, String> xmlData = new HashMap<String, String>(); // xml数据缓存
	private HttpServletRequest request;
	private HttpServletResponse response;

	public WeChatMessageService() {

	}

	public WeChatMessageService(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 读取回传的消息xml
		this.request = request;
		this.response = response;
		
		// 从request中取得输入流  
        InputStream is = request.getInputStream();  
		
		// 解析微信发来的请求（XML格式）转换成Map 
		xmlData = XMLUtil.isToMap(is);
		log.info("------接收数据req------:\n" + xmlData.toString());
		
		 // 释放资源  
        is.close();  
        is = null; 
	}

	/**
	 * 接受所有消息,进行处理
	 * 
	 * @return
	 */
	public String service() {
		if (xmlData != null) {
			// 获取msgtype (text:普通消息; event:事件推送)
			String msgType = xmlData.get("MsgType");
			String openid = xmlData.get("FromUserName");
			String myUserName = xmlData.get("ToUserName");
			
			//如果回复的是文本消息
			TextMessage tm=new TextMessage();
			tm.setFromUserName(myUserName);
			tm.setToUserName(openid);
			String respContent="";
			
			
			// 接收普通消息
			if (       "text".equals(msgType)         //文本
					|| "image".equals(msgType)        //图片
					|| "voice".equals(msgType)        //语音
					|| "video".equals(msgType)        //视频
					|| "shortvideo".equals(msgType)   //小视频
					|| "location".equals(msgType)     //位置
					|| "link".equals(msgType)         //链接
				){

				String content = xmlData.get("Content");
				
				
				try {
					if ("text".equals(msgType)) {
						
	//------------------回复文字消息---------------------------------------
						
						return MessageServiceUtil.textMessageService2(content,myUserName, openid);
						
						//tm.setContent("你输入的是文本消息！");
						//return MessageUtil.textMessageToXml(tm);
				
    //------------------回复图文消息 ---------------------------------------						
			
						
      //------------------回复图片消息 ---------------------------------------						
						/*ImageMessage im=new ImageMessage();
						im.setFromUserName(myUserName);
						im.setToUserName(openid);
						im.setMediaId("c6tGwekYvFqIjJ9UQwMUC9Yb0Ptvr_Ozwn8W8TrR2lhpFeMd1gcg9xjrG4N6PZY3");
						return MessageUtil.imageMessageToXml(im);
*/
					} else if("image".equals(msgType)){
						tm.setContent("你输入的是图片消息！");
						return MessageUtil.textMessageToXml(tm);
						 
					}else if("voice".equals(msgType)){
						tm.setContent("你输入的是声音消息！");
						return MessageUtil.textMessageToXml(tm);
						 
					}else if("video".equals(msgType)){
						tm.setContent("你输入的是视频消息！");
						return MessageUtil.textMessageToXml(tm);
						 
					}else if("shortvideo".equals(msgType)){
						tm.setContent("你输入的是小视频消息！");
						return MessageUtil.textMessageToXml(tm);
						 
					}else if("location".equals(msgType)){
						tm.setContent("你输入的是定位消息！");
						return MessageUtil.textMessageToXml(tm);
						 
					}else if("link".equals(msgType)){
						tm.setContent("你输入的是链接消息！");
						return MessageUtil.textMessageToXml(tm);
						 
					}
					
					
					
					
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				
		 //接收事件推送
			} else if ("event".equals(msgType)) {
				String event = xmlData.get("Event");
				String eventKey = xmlData.get("EventKey");
				
				//1、关注/取消关注事件
				if("subscribe".equals(event)){ 
					//关注
				 
					log.info("---------- subscribe ------------");
					tm.setContent(WeChatAPIConfig.HOME_MENU);
					return MessageUtil.textMessageToXml(tm);
				  
				}else if("unsubscribe".equals(event)){ 
					//取消关注
					
					log.info("---------- unsubscribe ------------");

				}else if("SCAN".equals(event)){ 
					//扫描用户已关注时的事件推送

					log.info("---------- SCAN ------------");
				}else if("LOCATION".equals(event)){ 
					//上报地理位置事件
					
					log.info("---------- LOCATION ------------");
					String Latitude = xmlData.get("Latitude");//地理位置纬度
					String Longitude  = xmlData.get("Longitude");//地理位置经度 
					String Precision  = xmlData.get("Precision");//地理位置精度 
					String CreateTime  = xmlData.get("CreateTime");//消息创建时间 
					respContent="纬度："+Latitude+"\n"+"经度："+Longitude+"\n"+"精度："+Precision;
					
					tm.setContent(respContent);			
					return MessageUtil.textMessageToXml(tm);
					
					
				}else if("VIEW".equals(event)){ 
					//点击菜单跳转链接时的事件推送 
					
					log.info("---------- VIEW ------------");
					
				}else if("CLICK".equals(event)){ 
					//点击菜单跳转链接时的事件推送 
					
					log.info("---------- CLICK ------------");
					
					
					if (eventKey.equals("0101")) {
						respContent = "菜单1-1被点击！";
						
						String accessToken=WeChatAPIRequest.getWeChatAPIRequest().getAccessToken().getAccess_token();
						try {
							User user=UserUtil.requestUserInfo(openid, accessToken);
							String userInfo="昵称："+user.getNickname()+"\n"+"城市："+user.getCity()+"\n"+"性别："+((user.getSex()==1)?"男":"女");
							respContent+="\n"+userInfo;
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					} else if (eventKey.equals("0102")) {
						respContent = "菜单1-2被点击！";
					} else if (eventKey.equals("0103")) {
						respContent = "菜单1-3被点击！！";
					} 
					
					tm.setContent(respContent);
					
					return MessageUtil.textMessageToXml(tm);
					
					
				}
				else{
					//没有找到
				}
		

			}
		}
		return "";
	}

}
