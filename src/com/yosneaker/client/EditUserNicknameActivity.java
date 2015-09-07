package com.yosneaker.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * 编辑用户昵称
 * 
 * @author chendd
 * 
 */
public class EditUserNicknameActivity extends BaseActivity{

	private EditText et_user_nickname;
	private String Nickname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_edit_user_nickname);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_ok);
		
		et_user_nickname = (EditText) findViewById(R.id.et_user_nickname);
		
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
			Nickname = et_user_nickname.getText().toString();
			if (TextUtils.isEmpty(Nickname)) {
				et_user_nickname.setError(getResources().getString(R.string.error_user_no_nickname));
			}else {
				Intent intent =new Intent();
				intent.putExtra("nickname_return", Nickname);
				setResult(RESULT_OK, intent);
				finish();
			}
			break;
		case R.id.mTextViewLeft:
			finish();
			break;

		default:
			break;
		}
	}

}
