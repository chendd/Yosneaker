package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * 意见反馈
 * 
 * @author chendd
 * 
 */
public class MineSettingsFeedBackActivity extends BaseActivity{

	private EditText et_mine_feedback;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mine_settings_feedback);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_ok);

		et_mine_feedback = (EditText) findViewById(R.id.et_mine_feedback);
	}

	@Override
	public void addListnners() {
		getTextViewLeft().setOnClickListener(this);
		getTextViewRight1().setOnClickListener(this);
		
	}

	@Override
	public void fillDatas() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mTextviewRight1:
			
			finish();
			break;
		case R.id.mTextViewLeft:
			finish();
			break;

		default:
			break;
		}
	}

}
