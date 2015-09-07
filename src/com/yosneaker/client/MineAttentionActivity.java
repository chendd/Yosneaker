package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

/**
 * 个人关注
 * 
 * @author chendd
 *
 */
public class MineAttentionActivity extends BaseActivity{

	private ImageView iv_add_attention;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_attention_none);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_add_friend);
		
		iv_add_attention = (ImageView) findViewById(R.id.iv_add_attention);
		
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		getTextViewRight1().setOnClickListener(this);		
	
		iv_add_attention.setOnClickListener(this);		
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
		case R.id.iv_add_attention:
			gotoExistActivity(MineSearchActivity.class, new Bundle());
			break;
		default:
			break;
		}
	}
	
}
