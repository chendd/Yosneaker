package com.yosneaker.client.app;

import android.app.Application;

/**
 * 应用启动会先执行Application类,主要做YosneakerAppState初始化
 * @author chendd
 */
public class YosneakerApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		YosneakerAppState.setApplicationContext(this);
		YosneakerAppState.getInstance();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		// TODO
	}

	
	
}


