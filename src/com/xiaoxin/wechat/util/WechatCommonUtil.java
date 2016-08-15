package com.xiaoxin.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.xiaoxin.wechat.service.WeChatAPIConfig;

/**
 * 
 * @描述: 微信通用工具类
 * @标题: WechatCommonUtil.java
 * @作者: chen changxiong
 * @日期: 2015-8-7 上午8:43:22
 * @版本: V1.0
 */
public class WechatCommonUtil {

	/**
	 * 
	 * @Title: checkSignature
	 * @Description: 检验signature对请求进行校验
	 * @param @param signature
	 * @param @param timestamp
	 * @param @param nonce
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { WeChatAPIConfig.token, timestamp, nonce };
		// 1、将token,timestamp,nonce三个参数进行字典排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		// MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA
		// 算法。信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
		// MessageDigest 对象开始被初始化。该对象通过使用 update 方法处理数据。任何时候都可以调用 reset
		// 方法重置摘要。一旦所有需要更新的数据都已经被更新了，应该调用 digest 方法之一完成哈希计算。
		MessageDigest md = null;
		String tmpStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			// 2、 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		// System.out.println(tmpStr.equals(signature.toUpperCase()));
		// 3、将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
		// true是前者，false是后者

	}

	/**
	 * 
	 * @Title: byteToStr
	 * @Description: 将字节数组转换为十六进制字符串
	 * @param @param byteArray
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 
	 * @Title: byteToHexStr
	 * @Description: 将字节转换为十六进制字符串
	 * @param @param mByte
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
