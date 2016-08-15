package com.xiaoxin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Common {
	/**
	 * inputstream 转换 string bufferreader方式
	 * @param is
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String isToStr(InputStream is) throws UnsupportedEncodingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try {

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 判断字符串是否为null或空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmptyString(String str){
		if(str == null){
			return false;
		}
		if(str.trim().equals("")){
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
}
