package com.yosneaker.client.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类
 * 
 * @author chendd
 *
 */
public class SettingUtils {
	public static final String IS_FIRST_LAUNCHER = "is_first_launcher";// 是否第一次启动
	public static final String SETTINGS_NET_BIND_WEIBO = "settings_net_bind_weibo";// 绑定微博
	public static final String SETTINGS_NET_BIND_QZONE = "settings_net_bind_qzone";// 绑定qq空间
	public static final String SETTINGS_NOTICE_HANDPICK = "settings_notice_handpick";// 推送"精选推荐"
	public static final String SETTINGS_NOTICE_COLLECT = "settings_notice_collect";// 推送"收藏测评"
	public static final String SETTINGS_NOTICE_WISH = "settings_notice_wish";// 推送"想入/已入"
	public static final String SETTINGS_NOTICE_COMMENT = "settings_notice_comment";// 推送"评论"
	
	
	/**
	 * 获取配置
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean get(Context context, String name, boolean defaultValue) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean value = prefs.getBoolean(name, defaultValue);
		return value;
	}
	
	/**
	 * 保存用户配置
	 * @param context
	 * @param name
	 * @param value
	 * @return
	 */
	public static boolean set(Context context, String name, boolean value) {
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(name, value);
		return editor.commit();	//提交
	}
}

