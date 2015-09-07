package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * 发布测评标题
 * 
 * @author chendd
 *
 */
public class MineSearchActivity extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_search);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(false);
		
		
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);	
	
	}

	@Override
	public void fillDatas() {

	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			finish();
		}
	}
}