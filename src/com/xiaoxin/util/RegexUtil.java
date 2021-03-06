package com.xiaoxin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

public static boolean checkDate(String str){
	String regex="(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";
	Pattern pattern=Pattern.compile(regex);
	Matcher matcher=pattern.matcher(str);
	return matcher.matches();
}
	
	
}
