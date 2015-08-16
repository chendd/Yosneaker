package com.yosneaker.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * 编辑用户签名
 * 
 * @author chendd
 * 
 */
public class EditUserSignatrueActivity extends BaseActivity{

	private EditText et_user_signature;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_user_signature);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_ok);

		et_user_signature = (EditText) findViewById(R.id.et_user_signature);
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
			Intent intent =new Intent();
			intent.putExtra("signature_return", et_user_signature.getText().toString());
			setResult(RESULT_OK, intent);
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
