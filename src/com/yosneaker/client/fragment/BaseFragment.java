package com.yosneaker.client.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

/**
 * 
 * 自定义所有Fragment基类,封装常用方法
 * 
 * @author chendd
 */
public class BaseFragment extends Fragment implements OnClickListener{
	
	
	/**
	 * 从当前activity跳转到目标activity,
	 * 如果目标activity曾经打开过,就重新展现,
	 * 如果从来没打开过,就新建一个打开
	 * 
	 * @param cls bundle
	 */
	public void gotoExistActivity(Class<?> cls, Bundle bundle) {
		Intent intent;
		intent = new Intent(this.getActivity(), cls);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
