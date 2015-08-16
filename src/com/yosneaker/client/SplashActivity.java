package com.yosneaker.client;

import com.yosneaker.client.util.HttpClientUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {
	private RelativeLayout rl_splash;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_splash);
		rl_splash = (RelativeLayout) this.findViewById(R.id.rl_splash);

		
		// 判断当前网络状态是否可用
		if(HttpClientUtil.isNetWorkConnected(this)){
			//splash 做一个动画,进入主界面
			AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
			aa.setDuration(2000);
			rl_splash.setAnimation(aa);
			rl_splash.startAnimation(aa);
			//通过handler 延时2秒 执行r任务 
			new Handler().postDelayed(new LoadMainTabTask(), 2000);
		}else{
			showSetNetworkDialog();
		}
	}
	
	private class LoadMainTabTask implements Runnable{

		@Override
		public void run() {
			Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
			startActivity(intent);
			finish();
			
		}
		
	}
	private void showSetNetworkDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("设置网络");
		builder.setMessage("网络错误请检查网络状态");
		builder.setPositiveButton("设置网络", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
				startActivity(intent);
				finish();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		builder.create().show();
		
	}

}
