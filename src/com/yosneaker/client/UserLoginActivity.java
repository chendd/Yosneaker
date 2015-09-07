package com.yosneaker.client;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.yosneaker.client.util.Validation;

/**
 * 用户登录界面
 * 
 * @author chendd
 *
 */
public class UserLoginActivity extends BaseActivity{

	private EditText et_login_username;
	private EditText et_login_password;
	private TextView tv_login_find_password;
	private ButtonRectangle btn_login;
	
	private String Username;
	private String Password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_user_login);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		
		et_login_username = (EditText) findViewById(R.id.et_login_username);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		tv_login_find_password = (TextView) findViewById(R.id.tv_login_find_password);
		btn_login = (ButtonRectangle) findViewById(R.id.btn_login);
		
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);			
		tv_login_find_password.setOnClickListener(this);	
		btn_login.setOnClickListener(this);	
	}

	@Override
	public void fillDatas() {
		

	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login_find_password:
			showToast("求我啊！求我再告诉你密码~");
			break;
		case R.id.btn_login:
			Username = et_login_username.getText().toString();
			Password = et_login_password.getText().toString();
			if (TextUtils.isEmpty(Username)) {
				et_login_username.setError(getResources().getString(R.string.error_login_username_no_null));
			}else if(!Validation.isUsernameValid(Username)){
				et_login_username.setError(getResources().getString(R.string.error_login_username_illegal));
			}else if (TextUtils.isEmpty(Password)) {
				et_login_password.setError(getResources().getString(R.string.error_login_password_no_null));
			}else if(!Validation.isPasswordValid(Password)){
				et_login_password.setError(getResources().getString(R.string.error_login_re_password_illegal));
			}else {
				gotoExistActivity(EditUserInfoActivity.class, new Bundle());
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
