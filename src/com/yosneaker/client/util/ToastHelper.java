package com.yosneaker.client.util;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

	private static Toast mToast=null;
	/**
	 * 
	 * @param mContext
	 * @param msg
	 * @param duration
	 */
	public static void showToast(Context mContext, String msg, int duration){
		if(mToast!=null){
			mToast.cancel();
			mToast=null;
		}
		mToast=Toast.makeText(mContext.getApplicationContext(), msg, duration);
		mToast.show();
	}
	
	/**
	 * 显示Toast提示，时长默认为Toast.LENGTH_SHORT
	 * @param mContext
	 * @param msg
	 */
	public static void showShortToast(Context mContext, String msg){
		if(mToast!=null){
			mToast.cancel();
			mToast=null;
		}
		mToast=Toast.makeText(mContext.getApplicationContext(), msg, Toast.LENGTH_SHORT);
		mToast.show();
	}
	
	/**
	 * 显示Toast提示，时长默认为Toast.LENGTH_LONG
	 * @param mContext
	 * @param msg
	 */
	public static void showLongToast(Context mContext, String msg){
		if(mToast!=null){
			mToast.cancel();
			mToast=null;
		}
		mToast=Toast.makeText(mContext.getApplicationContext(), msg, Toast.LENGTH_LONG);
		mToast.show();
	}
}
