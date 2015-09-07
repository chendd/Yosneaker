package com.yosneaker.client;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.gc.materialdesign.views.ButtonRectangle;
import com.yosneaker.client.util.Validation;

/**
 * 用户登录界面
 * 
 * @author chendd
 *
 */
public class UserRegisterActivity extends BaseActivity{

	private EditText et_register_username;
	private EditText et_register_password;
	private EditText et_register_repassword;
	private ButtonRectangle btn_user_register;
	
	private String Username;
	private String Password;
	private String RePassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_user_register);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		
		et_register_username = (EditText) findViewById(R.id.et_register_username);
		et_register_password = (EditText) findViewById(R.id.et_register_password);
		et_register_repassword = (EditText) findViewById(R.id.et_register_repassword);
		btn_user_register = (ButtonRectangle) findViewById(R.id.btn_user_register);
		
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);			
		btn_user_register.setOnClickListener(this);			
	}

	@Override
	public void fillDatas() {


	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_user_register:
			Username = et_register_username.getText().toString();
			Password = et_register_password.getText().toString();
			RePassword = et_register_repassword.getText().toString();
			if (TextUtils.isEmpty(Username)) {
				et_register_username.setError(getResources().getString(R.string.error_login_username_no_null));
			}else if(!Validation.isUsernameValid(Username)){
				et_register_username.setError(getResources().getString(R.string.error_login_username_illegal));
			}else if (TextUtils.isEmpty(Password)) {
				et_register_password.setError(getResources().getString(R.string.error_login_password_no_null));
			}else if(!Validation.isPasswordValid(Password)){
				et_register_password.setError(getResources().getString(R.string.error_login_password_illegal));
			}else if (TextUtils.isEmpty(RePassword)) {
				et_register_password.setError(getResources().getString(R.string.error_login_re_password_no_null));
			}else if(!Validation.isPasswordValid(RePassword)){
				et_register_password.setError(getResources().getString(R.string.error_login_re_password_illegal));
			}else if(Password.equals(RePassword)){
				et_register_password.setError(getResources().getString(R.string.error_login_passwords_no_same));
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
