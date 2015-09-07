package com.yosneaker.client.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于输入校验的类,根据正则表达式判断是否满足要求
 * @author chendd
 */
public class Validation {
	/**
	 * 不能出现的字符
	 */
	private static String regex = "[,()\\[\\]{}&.\\+:%\"\']";
	/**
	 * 判断输入是否满足用户名的要求(不能出现非法字符)
	 * @param input 输入的字符串
	 * @return 输入是否有效
	 */
	public static Boolean isUsernameValid(String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()||input.length()<3||input.length()>15){
			return false;
		}
		return true;
	}
	/**
	 * 判断输入是否满足密码的要求()
	 * @param input 输入的字符串
	 * @return 输入是否有效
	 */
	public static Boolean isPasswordValid(String input){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if(matcher.find()||input.length()<6||input.length()>16){
			return false;
		}
		return true;
	}
	
	/**
	 * 通过正则表达式验证邮箱
	 * @param email
	 * @return 是否是有效的邮箱格式
	 */
	public static boolean isEmail(String email) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
