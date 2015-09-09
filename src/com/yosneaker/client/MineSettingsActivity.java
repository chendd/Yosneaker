package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yosneaker.client.view.OptionItemView;

/**
 * 设置界面
 * 
 * @author crazymongo
 *
 */
public class MineSettingsActivity extends MyBaseActivity implements View.OnClickListener{
	
	private OptionItemView clear_cache, feedback;
	private Button btn_logout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		initUI();
	}

	private void initUI() {
		setContentView(R.layout.activity_mine_settings);
		
		clear_cache=(OptionItemView) findViewById(R.id.clear_cache);
		feedback=(OptionItemView) findViewById(R.id.feedback);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		
		clear_cache.setOnClickListener(this);
		feedback.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.clear_cache:
			
			break;
		case R.id.feedback:
//			gotoExistActivity(MineSettingsNetActivity.class,new Bundle());
			break;
		case R.id.btn_logout:
			
			break;
		default:
			break;
		}
	}
	
}