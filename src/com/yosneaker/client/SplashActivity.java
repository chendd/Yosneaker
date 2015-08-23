package com.yosneaker.client;

import android.app.Activity;
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

		
		    //splash 做一个动画,进入主界面
			AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
			aa.setDuration(2000);
			rl_splash.setAnimation(aa);
			rl_splash.startAnimation(aa);
			//通过handler 延时2秒 执行r任务 
			new Handler().postDelayed(new LoadMainTabTask(), 2500);
	}
	
	private class LoadMainTabTask implements Runnable{

		@Override
		public void run() {
			Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
			startActivity(intent);
			finish();
			
		}
		
	}

}
