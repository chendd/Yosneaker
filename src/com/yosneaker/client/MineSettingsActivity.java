package com.yosneaker.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gc.materialdesign.views.ButtonRectangle;
import com.yosneaker.client.util.DataCleanManager;

/**
 * 用户登录界面
 * 
 * @author chendd
 *
 */
public class MineSettingsActivity extends BaseActivity{
	
	private RelativeLayout rl_settings_net;
	private RelativeLayout rl_settings_notice;
	private RelativeLayout rl_settings_clean_cache;
	private RelativeLayout rl_settings_check_newversoin;
	private RelativeLayout rl_settings_feedback;
	
	private ButtonRectangle btn_logout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_mine_settings);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		
		rl_settings_net = (RelativeLayout) findViewById(R.id.rl_settings_net);
		rl_settings_notice = (RelativeLayout) findViewById(R.id.rl_settings_notice);
		rl_settings_clean_cache = (RelativeLayout) findViewById(R.id.rl_settings_clean_cache);
		rl_settings_check_newversoin = (RelativeLayout) findViewById(R.id.rl_settings_check_newversoin);
		rl_settings_feedback = (RelativeLayout) findViewById(R.id.rl_settings_feedback);
		
		btn_logout = (ButtonRectangle) findViewById(R.id.btn_logout);
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		
		rl_settings_net.setOnClickListener(this);		
		rl_settings_notice.setOnClickListener(this);		
		rl_settings_clean_cache.setOnClickListener(this);		
		rl_settings_check_newversoin.setOnClickListener(this);		
		rl_settings_feedback.setOnClickListener(this);		
		
		btn_logout.setOnClickListener(this);
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
		case R.id.rl_settings_net:
			gotoExistActivity(MineSettingsNetActivity.class,new Bundle());
			break;
		case R.id.rl_settings_notice:
			gotoExistActivity(MineSettingsNoticeActivity.class,new Bundle());
			break;
		case R.id.rl_settings_clean_cache:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
	    	dialog.setTitle(R.string.app_name);
	    	dialog.setMessage(R.string.dialog_mine_settings_clean_cache);
	    	dialog.setCancelable(false);
	    	dialog.setPositiveButton(R.string.dialog_user_ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					DataCleanManager.cleanApplicationData(MineSettingsActivity.this);
					showToast(getResources().getString(R.string.toast_mine_settings_clean_cache));
				}
			});
	    	dialog.setNegativeButton(R.string.dialog_user_cancle,null);
	    	dialog.show();
			break;
		case R.id.rl_settings_check_newversoin:
			showToast(getResources().getString(R.string.toast_mine_settings_check_newversoin));
			break;
		case R.id.rl_settings_feedback:
			gotoExistActivity(MineSettingsFeedBackActivity.class, new Bundle());
			break;
		case R.id.btn_logout:
			
			break;
		default:
			break;
		}
	}
	
}