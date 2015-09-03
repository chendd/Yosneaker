package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.views.Switch.OnCheckListener;
import com.yosneaker.client.util.SettingUtils;

/**
 * 发布测评标题
 * 
 * @author chendd
 *
 */
public class MineSettingsNetActivity extends BaseActivity{

	private Switch tb_net_bind_weibo;
	private Switch tb_net_bind_qzone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_settings_net);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_home2);
		
		tb_net_bind_weibo = (Switch) findViewById(R.id.tb_net_bind_weibo);
		tb_net_bind_qzone = (Switch) findViewById(R.id.tb_net_bind_qzone);
		
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		getTextViewRight1().setOnClickListener(this);	
		
		tb_net_bind_weibo.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(boolean check) {
				SettingUtils.set(MineSettingsNetActivity.this, SettingUtils.SETTINGS_NET_BIND_WEIBO, check);
			}
		});
		tb_net_bind_qzone.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(boolean check) {
				SettingUtils.set(MineSettingsNetActivity.this, SettingUtils.SETTINGS_NET_BIND_QZONE, check);
			}
		});
	
	}

	@Override
	public void fillDatas() {
		tb_net_bind_weibo.setChecked(SettingUtils.get(this, SettingUtils.SETTINGS_NET_BIND_WEIBO, false));
		tb_net_bind_qzone.setChecked(SettingUtils.get(this, SettingUtils.SETTINGS_NET_BIND_QZONE, false));
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mTextViewLeft:
			finish();
			break;
		case R.id.mTextviewRight1:
			gotoHome();
			break;
		default:
			break;
		}
	}

}
