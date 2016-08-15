package com.xiaoxin.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.xiaoxin.util.Common;

public class HttpClientUtils {

	private static Log log = LogFactory.getLog(HttpClientUtils.class);
	/**
	 * 简单的get请求
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String get(String url) throws Exception{
		log.info("get request :" + url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = getGetMethod(url);
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			// The underlying HTTP connection is still held by the response object
			// to allow the response content to be streamed directly from the network socket.
			// In order to ensure correct deallocation of system resources
			// the user MUST either fully consume the response content  or abort request
			// execution by calling CloseableHttpResponse#close().

			try {
				// do something useful with the response body
				// and ensure it is fully consumed
				HttpEntity entity1 = response1.getEntity();
			
				log.info("get status line :" + response1.getStatusLine());
				if(response1.getStatusLine().getStatusCode() == 200){
					InputStream in= entity1.getContent();
					
					String rs = Common.isToStr(in);
					log.info("get result:" + rs);
					return rs;
				}
				EntityUtils.consume(entity1);
			} finally {
				response1.close();
			}

		} finally {
			httpclient.close();
		}
		return null;
	}
	/**
	 * 简单的post 请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url,Map<String,String> params,String type) throws Exception {
		log.info("post request :" + url);
		log.info("post params :" + params);
		CloseableHttpClient httpclient = null;
		
		if("ssl".equalsIgnoreCase(type))
			httpclient = createHttpClient();
		else
			httpclient = HttpClients.createDefault();
		
		try {
			HttpPost httpPost = getPostMethod(url);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();
			for (String  key : params.keySet()) {
				String val = params.get(key);
				if(val == null) val="";
				nvps.add(new BasicNameValuePair(key, val));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			try {
				HttpEntity entity2 = response2.getEntity();
				log.info("post status line :" + response2.getStatusLine());
				// do something useful with the response body
				// and ensure it is fully consumed
				if(response2.getStatusLine().getStatusCode() == 200){
					InputStream in = entity2.getContent();
					String rs = Common.isToStr(in);
					log.info("post result :" + rs);
					return rs;
				}

				EntityUtils.consume(entity2);
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}

		return null;
	}
	
	public static String post(String url,String param,String type) throws Exception {
		log.info("post request :" + url);
		log.info("post params :" + param);
		CloseableHttpClient httpclient = null;
		
		if("ssl".equalsIgnoreCase(type))
			httpclient = createHttpClient();
		else
			httpclient = HttpClients.createDefault();
		
		try {
			HttpPost httpPost = getPostMethod(url);
			
			httpPost.setEntity(new StringEntity(param, ContentType.create("text/plain", "UTF-8")));
			CloseableHttpResponse response2 = httpclient.execute(httpPost);

			try {
				HttpEntity entity2 = response2.getEntity();
				log.info("post status line :" + response2.getStatusLine());
				// do something useful with the response body
				// and ensure it is fully consumed
				if(response2.getStatusLine().getStatusCode() == 200){
					InputStream in = entity2.getContent();
					String rs = Common.isToStr(in);
					log.info("post result :" + rs);
					return rs;
				}

				EntityUtils.consume(entity2);
			} finally {
				response2.close();
			}
		} finally {
			httpclient.close();
		}

		return null;
	}
	public static CloseableHttpClient createHttpClient() throws Exception{
		// Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        return httpclient;
	}

	public static HttpPost getPostMethod(String url) { 

		HttpPost post = new HttpPost(url); // 设置响应头信息 
		post.addHeader("Connection", "keep-alive"); 
		post.addHeader("Accept", "*/*"); 
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
		post.addHeader("Host", "mp.weixin.qq.com"); 
		post.addHeader("X-Requested-With", "XMLHttpRequest"); 
		post.addHeader("Cache-Control", "max-age=0"); 
		post.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) "); 

		return post; 
	} 

	public static HttpGet getGetMethod(String url) { 

		HttpGet pmethod = new HttpGet(url); 
		// 设置响应头信息 
		pmethod.addHeader("Connection", "keep-alive"); 
		pmethod.addHeader("Cache-Control", "max-age=0"); 
		pmethod.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) "); 
		pmethod.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/;q=0.8"); 
		return pmethod; 
	} 

}
