package com.yosneaker.client.util;

import android.provider.BaseColumns;

/**
 * 定义系统常量
 * 
 * @author chendd
 * @author crazymongo
 */
public interface AppConstants extends BaseColumns {

	public static final String APP_KEY_WB="568898243";
	public static final String APP_SECRET_WB="38a4f8204cc784f81f9f0daaf31e02e3";
	public static final String REDIRECT_URL_WB = "http://www.sina.com";
	public static final String SCOPE_WB= 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
	
	public static final String APP_ID_QQ="1104848658";
	public static final String APP_KEY_QQ="SMxrHCfe3HyYR5H3";
	public static final String SCOPE_QQ= "all";
	
	public static final String PREFERENCES_NAME_TOKEN="third_party_access_token";
	public static final String KEY_UID_WB="uid_wb";
	public static final String KEY_ACCESS_TOKEN_WB="access_token_wb";
	public static final String KEY_REFRESH_TOKEN_WB="refresh_token_wb";
	public static final String KEY_EXPIRE_TIME_WB="expire_time_wb";
	
	/** 服务器地址 */
	public static final String HTTP_BASE_URL = "http://api.yosneaker.com/";  
	
	/** 网络超时(毫秒) */
	public static final int HTTP_TIME_OUT = 20000;
	
	/** 日志Tag */
	public static final String TAG = "Yosneaker";
	
	/** 编辑测评上方背景高/宽 比例, 9:16 */
	public static final float BG_SCALE = 0.5625f;
	
	/** 测评列表加载 当前页 */
	public static final int DEFAULT_PAGE = 1;
	
	/** 测评列表加载 每页条数 */
	public static final int DEFAULT_ROWS = 5;
	
	//requestCode 10000+
	public static final int COMMENT_TITLE_REQUEST = 10001;
	public static final int COMMENT_INTRO_REQUEST = 10002;
	public static final int COMMENT_ITEM_REQUEST = 10003;
	public static final int COMMENT_SUMMARIZE_REQUEST = 10004;
	public static final int PHOTO_CROP_REQUEST = 10005;
	public static final int PHOTO_CAREMA_REQUEST = 10006;// 拍照
	public static final int PHOTO_GALLERY_REQUEST = 10007;// 从相册中选择
	public static final int USER_NICKNAME_REQUEST = 10008;
	public static final int USER_SIGNATURE_REQUEST = 10009;
	public static final int USER_PLAY_REQUEST = 10010;

	public static final String HTTP_IMAGE_BASE_URL = "http://api.yosneaker.com/upload/resources/generic/";
}
