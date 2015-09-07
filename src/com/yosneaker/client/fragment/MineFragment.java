package com.yosneaker.client.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yosneaker.client.ArticleDetailActivity;
import com.yosneaker.client.EditUserInfoActivity;
import com.yosneaker.client.MineAttentionActivity;
import com.yosneaker.client.MineCollectActivity;
import com.yosneaker.client.MineFansActivity;
import com.yosneaker.client.MineWishActivity;
import com.yosneaker.client.R;
import com.yosneaker.client.adapter.ArticleAdapter;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.view.RoundImageView;
import com.yosneaker.client.view.XListView;
import com.yosneaker.client.view.XListView.IXListViewListener;

/**
 * 
 * 我的Fragment
 * 
 * @author chendd
 *
 */
public class MineFragment extends BaseFragment implements IXListViewListener{

	private View viewFragment;
	
	private ImageView iv_mine_edit_info;
	private RoundImageView riv_mine_user_icon;
	private LinearLayout ll_tab_wish;
	private LinearLayout ll_tab_collect;
	private LinearLayout ll_tab_attention;
	private LinearLayout ll_tab_fans;
	private RelativeLayout ll_mine_no_article;//该用户没有测评 显示这个布局,否则显示底下XListView;
	
	// "我的"测评
	private XListView xListView=null;
	private ArticleAdapter mAdapter;
	private ArrayList<ArticleList> items = new ArrayList<ArticleList>();
	private Handler mHandler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewFragment = inflater.inflate(R.layout.fragment_mine,
				container, false);
		initViews();
		addListnners();
		return viewFragment;
	}

	private void initViews(){
		iv_mine_edit_info = (ImageView) viewFragment.findViewById(R.id.iv_mine_edit_info);
		riv_mine_user_icon = (RoundImageView) viewFragment.findViewById(R.id.riv_mine_user_icon);
		ll_tab_wish = (LinearLayout) viewFragment.findViewById(R.id.ll_tab_wish);
		ll_tab_collect = (LinearLayout) viewFragment.findViewById(R.id.ll_tab_collect);
		ll_tab_attention = (LinearLayout) viewFragment.findViewById(R.id.ll_tab_attention);
		ll_tab_fans = (LinearLayout) viewFragment.findViewById(R.id.ll_tab_fans);
		ll_mine_no_article = (RelativeLayout) viewFragment.findViewById(R.id.ll_mine_no_article);
		
		xListView=(XListView) viewFragment.findViewById(R.id.xlv_mine_articles);
		xListView.setPullLoadEnable(true);
		mAdapter = new ArticleAdapter(getActivity(),items);
		xListView.setAdapter(mAdapter);
		xListView.setXListViewListener(this);
		xListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				gotoExistActivity(ArticleDetailActivity.class, new Bundle());
			}
			
		});
		mHandler = new Handler();
	}
	
	private void addListnners() {
		iv_mine_edit_info.setOnClickListener(this);
		riv_mine_user_icon.setOnClickListener(this);
		ll_tab_wish.setOnClickListener(this);
		ll_tab_collect.setOnClickListener(this);
		ll_tab_attention.setOnClickListener(this);
		ll_tab_fans.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_mine_edit_info:
			gotoExistActivity(EditUserInfoActivity.class, new Bundle());
			break;
		case R.id.riv_mine_user_icon:
			
			break;
		case R.id.ll_tab_wish:
			gotoExistActivity(MineWishActivity.class, new Bundle());
			break;
		case R.id.ll_tab_collect:
			gotoExistActivity(MineCollectActivity.class, new Bundle());
			break;
		case R.id.ll_tab_attention:
			gotoExistActivity(MineAttentionActivity.class, new Bundle());
			break;
		case R.id.ll_tab_fans:
			gotoExistActivity(MineFansActivity.class, new Bundle());
			break;
		default:
			break;
		}
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
