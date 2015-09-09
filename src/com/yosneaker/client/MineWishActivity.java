package com.yosneaker.client;

import android.os.Bundle;

/**
 * 个人心愿单
 * 
 * @author crazymongo
 *
 */
public class MineWishActivity extends MyBaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.activity_mine_wish);
	}

}