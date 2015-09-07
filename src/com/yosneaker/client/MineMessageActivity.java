package com.yosneaker.client;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import com.yosneaker.client.fragment.MessageFragment;

/**
 * 
 * 我的消息界面
 * 
 * @author chendd
 * 
 */
public class MineMessageActivity extends BaseActivity{

	/** 用于展示个人通知的Fragment */
	private MessageFragment mNoticeFragment;

	/** 用于对Fragment进行管理 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mine_message);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {

		fragmentManager = getSupportFragmentManager();
		setTitleBarText(null);
		showTextViewLeft(true);
		
	}
	
	
	@Override
	public void addListnners() {

		getTextViewLeft().setOnClickListener(this);
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
		case R.id.mine_layout:
			setTabSelection(1);
			break;
		case R.id.mTextViewLeft:
			finish();
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
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// commentText.setTextColor(tabTextSelectedColor);
			if (mNoticeFragment == null) {
				// 如果mNoticeFragment为空，则创建一个并添加到界面上
				mNoticeFragment = new MessageFragment();
				transaction.add(R.id.content, mNoticeFragment);
			} else {
				// 如果mNoticeFragment不为空，则直接将它显示出来
				transaction.show(mNoticeFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (mNoticeFragment != null) {
			transaction.hide(mNoticeFragment);
		}
	}
}
