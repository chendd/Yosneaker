package com.yosneaker.client.fragment;

import com.yosneaker.client.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 
 * 想入Fragment
 * 
 * @author chendd
 *
 */
public class WishWantFragment extends BaseFragment {

	private View viewFragment;
	private ImageView iv_add_wish_want;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewFragment = inflater.inflate(R.layout.fragment_wish_want_none,
				container, false);
		initViews();
		addListnners();
		return viewFragment;
	}

	private void initViews(){
		iv_add_wish_want = (ImageView) viewFragment.findViewById(R.id.iv_add_wish_want);
	}
	
	private void addListnners() {
		iv_add_wish_want.setOnClickListener(this);	
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add_wish_want:
			gotoHome();
			break;
		default:
			break;
		}
	}
	
}
