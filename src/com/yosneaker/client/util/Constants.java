package com.yosneaker.client.util;

import android.provider.BaseColumns;

/**
 * 定义系统常量
 * 
 * @author chendd
 *
 */
public interface Constants extends BaseColumns {

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
