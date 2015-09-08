package com.yosneaker.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yosneaker.client.fragment.ArticleFragment;
import com.yosneaker.client.fragment.MineFragment;
import com.yosneaker.client.util.HttpClientUtil;

/**
 * 
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author chendd
 * 
 */
public class HomeActivity extends BaseActivity{

	/** 用于展示评测的Fragment */
	private ArticleFragment mCommentFragment;

	/** 用于展示"我的"的Fragment */
	private MineFragment mMineFragment;

	/** 评测界面布局 */
	private View commentView;

	/** 发布界面布局 */
	private View addView;

	/** "我的"界面布局 */
	private View mineView;

	/** 在Tab布局上显示评测图标的控件 */
	private ImageView commentImage;

	/** 在Tab布局上显示"我的"图标的控件 */
	private ImageView mineImage;

	/** 用于对Fragment进行管理 */
	private FragmentManager fragmentManager;

	private RelativeLayout mNetworkErrorTip;
	
	private long firstTime = 0;
	
	private NetworkChangeReceiver networkChangeReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 注册网络状态监听广播
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver = new NetworkChangeReceiver();
		registerReceiver(networkChangeReceiver, intentFilter);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {

		fragmentManager = getSupportFragmentManager();
		commentView = findViewById(R.id.comment_layout);
		addView = findViewById(R.id.add_layout);
		mineView = findViewById(R.id.mine_layout);
		commentImage = (ImageView) findViewById(R.id.comment_image);
		mineImage = (ImageView) findViewById(R.id.mine_image);
		mNetworkErrorTip = (RelativeLayout) findViewById(R.id.mNetworkErrorTip); 
	}

	private void setTitleBar(int index) {
		switch (index) {
		case 0:
			setTitleBarText(null);
			showTextViewLeft(false);
			showTextViewRight1(false);
			showTextViewRight2(false);
			break;
		case 1:
			setTitleBarText("lj神经刀");
			showTextViewLeft(true);
			showTextViewRight1(true);
			showTextViewRight2(true);
			getTextViewLeft().setBackgroundResource(R.drawable.ic_notice);
			getTextViewRight1().setBackgroundResource(R.drawable.ic_set);
			getTextViewRight2().setBackgroundResource(R.drawable.ic_add_friend);
			break;
		}
	}
	
	
	@Override
	public void addListnners() {

		commentView.setOnClickListener(this);
		mineView.setOnClickListener(this);
		addView.setOnClickListener(this);
		mNetworkErrorTip.setOnClickListener(this);
		getTextViewLeft().setOnClickListener(this);
		getTextViewRight1().setOnClickListener(this);
		getTextViewRight2().setOnClickListener(this);
	}

	@Override
	public void fillDatas() {
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_layout:
			setTabSelection(0);
			break;
		case R.id.add_layout:
//			if (isLogin()) {
				Bundle bundle = new Bundle();
				bundle.putInt("action", 1);
				HomeActivity.this.gotoExistActivity(
						AddArticleTitleActivity.class, bundle);
//			} else {
//				showUnLoginDialog();
//			}
			break;
		case R.id.mine_layout:
			setTabSelection(1);
			break;
		case R.id.mTextViewLeft:
			gotoExistActivity(MineMessageActivity.class, new Bundle());
			break;
		case R.id.mTextviewRight1:
			gotoExistActivity(MineSettingsActivity.class, new Bundle());
			break;
		case R.id.mTextviewRight2:
			gotoExistActivity(MineSearchActivity.class, new Bundle());
			break;
		case R.id.mNetworkErrorTip:
			 Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
			 startActivity(intent);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		setTitleBar(index);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			commentImage.setImageResource(R.drawable.tab_comment_selected);
			// commentText.setTextColor(tabTextSelectedColor);
			if (mCommentFragment == null) {
				// 如果mCommentFragment为空，则创建一个并添加到界面上
				mCommentFragment = new ArticleFragment();
				transaction.add(R.id.content, mCommentFragment);
			} else {
				// 如果mCommentFragment不为空，则直接将它显示出来
				transaction.show(mCommentFragment);
			}
			break;
		case 1:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			mineImage.setImageResource(R.drawable.tab_mine_selected);
			// mineText.setTextColor(tabTextSelectedColor);
			if (mMineFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mMineFragment = new MineFragment();
				transaction.add(R.id.content, mMineFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(mMineFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		commentImage.setImageResource(R.drawable.tab_comment_normal);
		mineImage.setImageResource(R.drawable.tab_mine_normal);
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (mCommentFragment != null) {
			transaction.hide(mCommentFragment);
		}
		if (mMineFragment != null) {
			transaction.hide(mMineFragment);
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
				showToast(getResources().getString(
						R.string.toast_twice_press_exict));
				firstTime = secondTime;// 更新firstTime
				return true;
			} else { // 两次按键小于2秒时，退出应用
				System.exit(0);
			}
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(networkChangeReceiver);
	}

	class NetworkChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent arg1) {
			if (!HttpClientUtil.isNetWorkConnected(context)) {
				mNetworkErrorTip.setVisibility(View.VISIBLE);
			}else {
				mNetworkErrorTip.setVisibility(View.GONE);
			}
		}
		
	}
	
}
