package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * 个人收藏
 * 
 * @author chendd
 *
 */
public class MineCollectActivity extends BaseActivity{

	private ImageView iv_add_collect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_collect_none);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(false);
		
		iv_add_collect = (ImageView) findViewById(R.id.iv_add_collect);
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);	
		iv_add_collect.setOnClickListener(this);	
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
		case R.id.iv_add_collect:
			gotoHome();
			break;
		default:
			break;
		}
	}
}