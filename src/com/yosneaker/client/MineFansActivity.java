package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * 个人粉丝
 * 
 * @author chendd
 *
 */
public class MineFansActivity extends BaseActivity{

	private ImageView iv_add_fans;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_fans_none);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_add_friend);
		
		iv_add_fans = (ImageView) findViewById(R.id.iv_add_fans);
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		getTextViewRight1().setOnClickListener(this);		
	
		iv_add_fans.setOnClickListener(this);				
	}

	@Override
	public void fillDatas() {

	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mTextViewLeft:
			finish();
			break;
		case R.id.mTextviewRight1:
		case R.id.iv_add_fans:
			gotoHome();
			break;
		default:
			break;
		}
	}
	
}
