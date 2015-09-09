package com.yosneaker.client;

import android.os.Bundle;

/**
 * 个人收藏
 * 
 * @author crazymongo
 *
 */
public class MineCollectActivity extends MyBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.activity_mine_collect_none);
	}
}