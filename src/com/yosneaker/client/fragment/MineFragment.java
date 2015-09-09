package com.yosneaker.client.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yosneaker.client.EditUserInfoActivity;
import com.yosneaker.client.MineCollectActivity;
import com.yosneaker.client.MineMessageActivity;
import com.yosneaker.client.MineSettingsActivity;
import com.yosneaker.client.MineWishActivity;
import com.yosneaker.client.R;
import com.yosneaker.client.adapter.ArticleAdapter;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.view.CustomTitleBar;
import com.yosneaker.client.view.RoundImageView;
import com.yosneaker.client.view.XListView;
import com.yosneaker.client.view.XListView.IXListViewListener;

/**
 * 
 * 我的Fragment
 * 
 * @author crazymongo
 *
 */
public class MineFragment extends MyBaseFragment implements IXListViewListener, OnClickListener{

	private CustomTitleBar titleBar;
	private ImageView iv_mine_edit_info;
	private RoundImageView riv_mine_user_icon;
	private LinearLayout ll_tab_wish;
	private LinearLayout ll_tab_collect;
	private RelativeLayout rl_mine_no_article;//该用户没有测评 显示这个布局,否则显示底下XListView;
	
	// "我的"测评
	private XListView xListView=null;
	private ArticleAdapter mAdapter;
	private ArrayList<ArticleList> items = new ArrayList<ArticleList>();
	private Handler mHandler;
	
	private SharedPreferences sp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp=getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
		mHandler = new Handler();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_mine, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		titleBar=(CustomTitleBar) view.findViewById(R.id.titleBar);
		titleBar.setCenterTitle(sp.getString("nick", "nick"));
		titleBar.setOnClickLeftIconListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MineMessageActivity.class));
			}
		});
		titleBar.setOnClickFirstRightIconListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MineSettingsActivity.class));
			}
		});
		
		iv_mine_edit_info = (ImageView) view.findViewById(R.id.iv_mine_edit_info);
		riv_mine_user_icon = (RoundImageView) view.findViewById(R.id.riv_mine_user_icon);
		ll_tab_wish = (LinearLayout) view.findViewById(R.id.ll_tab_wish);
		ll_tab_collect = (LinearLayout) view.findViewById(R.id.ll_tab_collect);
		rl_mine_no_article = (RelativeLayout) view.findViewById(R.id.rl_mine_no_article);
		xListView=(XListView) view.findViewById(R.id.xlv_mine_articles);
		
		iv_mine_edit_info.setOnClickListener(this);
		riv_mine_user_icon.setOnClickListener(this);
		ll_tab_wish.setOnClickListener(this);
		ll_tab_collect.setOnClickListener(this);
		
		xListView.setPullLoadEnable(true);
		mAdapter = new ArticleAdapter(getActivity(),items);
		xListView.setAdapter(mAdapter);
		xListView.setXListViewListener(this);
		xListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				gotoExistActivity(ArticleDetailActivity.class, new Bundle());
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		switch (v.getId()) {
		case R.id.iv_mine_edit_info:
			intent.setClass(getActivity(), EditUserInfoActivity.class);
			break;
		case R.id.ll_tab_wish:
			intent.setClass(getActivity(), MineWishActivity.class);
			break;
		case R.id.ll_tab_collect:
			intent.setClass(getActivity(), MineCollectActivity.class);
			break;
		default:
			break;
		}
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	private void geneItems() {
		// 添加 我的测评
	}
	
	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}

	
}
