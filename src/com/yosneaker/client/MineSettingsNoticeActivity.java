package com.yosneaker.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.gc.materialdesign.views.Switch;
import com.gc.materialdesign.views.Switch.OnCheckListener;
import com.yosneaker.client.util.SettingUtils;

/**
 * 消息推送设置
 * 
 * @author chendd
 *
 */
public class MineSettingsNoticeActivity extends BaseActivity{

	private Switch tb_notice_handpick;
	private Switch tb_notice_collect;
	private Switch tb_notice_wish;
	private Switch tb_notice_comment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_settings_notice);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_home2);
		
		tb_notice_handpick = (Switch) findViewById(R.id.tb_notice_handpick);
		tb_notice_collect = (Switch) findViewById(R.id.tb_notice_collect);
		tb_notice_wish = (Switch) findViewById(R.id.tb_notice_wish);
		tb_notice_comment = (Switch) findViewById(R.id.tb_notice_comment);
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		getTextViewRight1().setOnClickListener(this);		
	
		tb_notice_handpick.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(boolean check) {
				SettingUtils.set(MineSettingsNoticeActivity.this, SettingUtils.settings_notice_handpick, check);
			}
		});
		tb_notice_collect.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(boolean check) {
				SettingUtils.set(MineSettingsNoticeActivity.this, SettingUtils.settings_notice_collect, check);
			}
		});
		tb_notice_wish.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(boolean check) {
				SettingUtils.set(MineSettingsNoticeActivity.this, SettingUtils.settings_notice_wish, check);
			}
		});
		tb_notice_comment.setOncheckListener(new OnCheckListener() {
			
			@Override
			public void onCheck(boolean check) {
				SettingUtils.set(MineSettingsNoticeActivity.this, SettingUtils.settings_notice_comment, check);
			}
		});
		
	}

	@Override
	public void fillDatas() {
		tb_notice_handpick.setChecked(SettingUtils.get(this, SettingUtils.settings_notice_handpick, false));
		tb_notice_collect.setChecked(SettingUtils.get(this, SettingUtils.settings_notice_collect, false));
		tb_notice_wish.setChecked(SettingUtils.get(this, SettingUtils.settings_notice_wish, false));
		tb_notice_comment.setChecked(SettingUtils.get(this, SettingUtils.settings_notice_comment, false));
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
