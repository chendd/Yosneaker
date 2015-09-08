package com.yosneaker.client;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * 自定义所有Activity基类,封装常用方法
 * 
 * @author crazymongo
 * 
 */
public  class MyBaseActivity extends FragmentActivity{
    protected Context mContext;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		mContext=this;
	}
    
}
