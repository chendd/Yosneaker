package com.yosneaker.client;

import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.utils.L;
import com.yosneaker.client.model.Account;
import com.yosneaker.client.model.ThridPlatFormInfo;
import com.yosneaker.client.util.HttpClientUtil;

/**
 * 注册/登录界面
 * 
 * @author chendd
 *
 */
public class UserUnLoginActivity extends BaseActivity implements  Callback,PlatformActionListener{
	
	private ImageView iv_qq_login;
	private ImageView iv_weixin_login;
	private ImageView iv_weibo_login;
	
	private TextView tv_mobile_login;
	private TextView tv_mobile_register;
	
	private static final String TAG = "LoginActivity";
	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_user_unlogin);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		iv_qq_login = (ImageView) findViewById(R.id.iv_qq_login);
		iv_weixin_login = (ImageView) findViewById(R.id.iv_weixin_login);
		iv_weibo_login = (ImageView) findViewById(R.id.iv_weibo_login);
		
		tv_mobile_login = (TextView) findViewById(R.id.tv_mobile_login);
		tv_mobile_register = (TextView) findViewById(R.id.tv_mobile_register);
	}

	@Override
	public void addListnners() {	
		iv_qq_login.setOnClickListener(this);
		iv_weixin_login.setOnClickListener(this);
		iv_weibo_login.setOnClickListener(this);
		tv_mobile_login.setOnClickListener(this);
		tv_mobile_register.setOnClickListener(this);
	}

	@Override
	public void fillDatas() {

	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_qq_login:
			showToast("调用QQ sdk登录");
			break;
		case R.id.iv_weixin_login:
			showToast("调用微信 sdk登录");
			break;
		case R.id.iv_weibo_login:
			Platform sinaPlatform = new SinaWeibo(this);
			authorize(sinaPlatform);
			break;
		case R.id.tv_mobile_login:
			gotoExistActivity(UserLoginActivity.class, new Bundle());
			break;
		case R.id.tv_mobile_register:
			gotoExistActivity(UserRegisterActivity.class, new Bundle());
			break;
		default:
			break;
		}
	}
	
	private void authorize(Platform plat) {
		Log.i(TAG, "authorize执行了");
		if (plat == null) {
			return;
		}

		if (plat.isValid()) {
			String userId = plat.getDb().getUserId();
			if (userId != null) {
				UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
				login(plat);
				Log.i(TAG, "id:" + userId);
				Log.i(TAG, "sex:" + plat.getDb().get("sex"));
				Log.i(TAG, "getExpiresIn:" + plat.getDb().getExpiresIn());
				Log.i(TAG, "getExpiresTime:" + plat.getDb().getExpiresTime());
				Log.i(TAG, "getPlatformNname:"
						+ plat.getDb().getPlatformNname());
				Log.i(TAG, "getPlatformVersion:"
						+ plat.getDb().getPlatformVersion());
				Log.i(TAG, "getToken:" + plat.getDb().getToken());
				Log.i(TAG, "getTokenSecret:" + plat.getDb().getTokenSecret());
				Log.i(TAG, "getUserIcon:" + plat.getDb().getUserIcon());
				Log.i(TAG, "getUserId:" + plat.getDb().getUserId());
				Log.i(TAG, "getUserName:" + plat.getDb().getUserName());
				Bundle bundle = new Bundle();
				bundle.putInt("action", 1);
        		UserUnLoginActivity.this.gotoExistActivity(AddArticleTitleActivity.class, bundle);	
				return;
			}
		}
		plat.setPlatformActionListener(this);
		plat.SSOSetting(true);
		plat.showUser(null);
	}
	
	private void login(Platform plat) {
		Log.i(TAG, "login执行了");
		Message msg = new Message();
		msg.what = MSG_LOGIN;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
		
		Account account = new Account();
		account.setAccountName(plat.getDb().getUserName());
		account.setAccountThridPartId(plat.getDb().getPlatformNname()+"-"+plat.getDb().getUserId());
		account.setAccountUsername(plat.getDb().getUserName());
		account.setAccountImages(plat.getDb().getUserIcon());
		//account.setAccountSex();
		//login(plat.getName(), userId, null);
		ThridPlatFormInfo info = new ThridPlatFormInfo();
		info.setExpiresIn(plat.getDb().getExpiresIn());
		info.setExpiresTime(plat.getDb().getExpiresTime());
		info.setPlatformNname(plat.getDb().getPlatformNname());
		info.setId(plat.getDb().getUserId());
		info.setToken(plat.getDb().getToken());
		info.setTokenSecret(plat.getDb().getTokenSecret());
		account.setAccountName(JSON.toJSONString(info));
		System.out.println(account);
		HttpClientUtil.login(account,new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				L.i("===", response);
				Account account = null;
				try {
					JSONObject obj = (JSONObject) response.get("obj");
					account = JSON.parseObject(obj.toString(), Account.class);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
				SharedPreferences.Editor mEditor = sharedPreferences.edit();  
		        mEditor.putInt("accountId",account.getAccountId());  
		        mEditor.commit();  
				super.onSuccess(statusCode, headers, response);
			}
			
		},this);
	}



	@Override
	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		Log.i(TAG, "onComplete执行了");
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
			login(platform);
		}
		Log.i(TAG, res.toString());
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		Log.i(TAG, "onError执行了");
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();
	}

	@Override
	public void onCancel(Platform platform, int action) {
		Log.i(TAG, "onCancel执行了");
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}


	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}
	
}
