package com.yosneaker.client;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yosneaker.client.util.AppConstants;
import com.yosneaker.client.util.ToastHelper;


public class ThirdPartyLoginActivity extends MyBaseActivity implements OnClickListener{

	private final String Tag=ThirdPartyLoginActivity.class.getSimpleName();
	private ImageView iv_weibo_login, iv_qq_login;
	private AuthInfo mAuthInfo;
	private SsoHandler mSsoHandler;
	public  Tencent mTencent;
	private IUiListener mQQLoginListener;
	private SharedPreferences mSp;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		initUI();
		mSp=getSharedPreferences(AppConstants.PREFERENCES_NAME_TOKEN, Context.MODE_PRIVATE);
	}
	
	private void initUI(){
		setContentView(R.layout.activity_third_party_login);
		iv_weibo_login=(ImageView) findViewById(R.id.iv_weibo_login);
		iv_qq_login=(ImageView) findViewById(R.id.iv_qq_login);
		iv_weibo_login.setOnClickListener(this);
		iv_qq_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.iv_weibo_login:
			weiboLogin();
			break;
		case R.id.iv_qq_login:
			qqLogin();
			break;
		}
		
	}
	
	private void weiboLogin(){
		mAuthInfo = new AuthInfo(this, AppConstants.APP_KEY_WB, AppConstants.REDIRECT_URL_WB, AppConstants.SCOPE_WB);
		mSsoHandler=new SsoHandler(this, mAuthInfo);
		mSsoHandler.authorize(new WeiboAuthListener() {
			
			@Override
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(Bundle arg0) {
				// TODO Auto-generated method stub
				Oauth2AccessToken mAccessToken=Oauth2AccessToken.parseAccessToken(arg0);
				if(mAccessToken.isSessionValid()){
					Editor editor=mSp.edit();
					editor.putString(AppConstants.KEY_UID_WB, mAccessToken.getUid());
			        editor.putString(AppConstants.KEY_ACCESS_TOKEN_WB, mAccessToken.getToken());
			        editor.putString(AppConstants.KEY_REFRESH_TOKEN_WB, mAccessToken.getRefreshToken());
			        editor.putLong(AppConstants.KEY_EXPIRE_TIME_WB, mAccessToken.getExpiresTime());
			        editor.commit();
				}
				startActivity(new Intent(mContext, HomeActivity.class));
				finish();
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void qqLogin(){
		mTencent=Tencent.createInstance(AppConstants.APP_ID_QQ, this);
		mQQLoginListener=new QQLoginListener();
		if(mTencent.isSessionValid()){
			
		}else{
			mTencent.login(this, AppConstants.SCOPE_QQ, mQQLoginListener);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Log.i(Tag, "onActivityResult ----->" + requestCode  + "; resultCode=" + resultCode);
		mTencent.onActivityResultData(requestCode, resultCode, data, mQQLoginListener);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class QQLoginListener implements IUiListener{

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			Log.i(Tag, arg0.errorDetail);
			Log.i(Tag, arg0.errorMessage);
			Log.i(Tag, arg0.errorCode+"");
			Log.i(Tag, arg0.toString());
			ToastHelper.showLongToast(mContext, "登录出错");
		}
		
		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			JSONObject mJson=(JSONObject) arg0;
//			Gson mGson=new Gson();
//			QQLoginRespInfo responseInfo=mGson.fromJson(mJson.toString(), QQLoginRespInfo.class);
//			mTencent.setAccessToken(responseInfo.accessToken, String.valueOf(responseInfo.expireTime));
//			mTencent.setOpenId(responseInfo.openId);
			
			Log.i(Tag, mJson.toString());
			Log.i(Tag, "AccessToken="+mTencent.getAccessToken());
			Log.i(Tag, "AppId="+mTencent.getAppId());
			Log.i(Tag, "OpenId="+mTencent.getOpenId());
			Log.i(Tag, "ExpiresIn="+mTencent.getExpiresIn());
			QQToken mQQToken=mTencent.getQQToken();
			Log.i(Tag, "AccessToken="+mQQToken.getAccessToken());
			Log.i(Tag, "AppId="+mQQToken.getAppId());
			Log.i(Tag, "OpenId="+mQQToken.getOpenId());
			Log.i(Tag, "ExpiresIn="+mQQToken.getExpireTimeInSecond());
			Log.i(Tag, "AuthSource="+mQQToken.getAuthSource());
			ToastHelper.showLongToast(mContext, "登录成功");
			startActivity(new Intent(mContext, HomeActivity.class));
			finish();
		}
		
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			ToastHelper.showLongToast(mContext, "登录取消");
		}
		
	}
	
	
}
