package com.yosneaker.client.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yosneaker.client.ArticleDetailActivity;
import com.yosneaker.client.R;
import com.yosneaker.client.adapter.ArticleAdapter;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.util.HttpClientUtil;
import com.yosneaker.client.view.XListView;
import com.yosneaker.client.view.XListView.IXListViewListener;

/**
 * 
 * 好友评测Fragment
 * 
 * @author chendd
 *
 */
public class ArticleFriendFragment extends BaseFragment implements IXListViewListener{
	
	private View viewFragment;
	private XListView xListView=null;
	private ArticleAdapter mAdapter;
	private ArrayList<ArticleList> items = new ArrayList<ArticleList>();
	private Handler mHandler;
	
	// 测试数据
	int start = 1;
	int rows = 5;
	ArrayList<String> heads = new ArrayList<String>();
	ArrayList<String> covers = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewFragment = inflater.inflate(R.layout.fragment_friend_article,
				container, false);
		
		// 模拟测试数据
		heads.add("drawable://" + R.drawable.list_user_head);
		heads.add("drawable://" + R.drawable.list_user_head2);		
		covers.add("drawable://" + R.drawable.list_bg);
		covers.add("drawable://" + R.drawable.list_bg2);
		
		geneItems();
		initViews();
		return viewFragment;
	}

	private void initViews(){
		xListView=(XListView) viewFragment.findViewById(R.id.xListView);
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

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				items.clear();
				geneItems();
				mAdapter = new ArticleAdapter(getActivity(),items);
				xListView.setAdapter(mAdapter);
				onLoad();
			}
		}, 2000);
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
		HttpClientUtil.getPublicArticle(++start, rows, new JsonHttpResponseHandler(){
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				System.out.println("==========4"+errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				int total = 0;
				List<ArticleList> result = null;
				try {
					total = (Integer) response.get("total");
					JSONArray list = response.getJSONArray("articles");
					result  = JSON.parseArray(list.toString(),ArticleList.class);
					items.addAll(result);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("==========5"+total+"result:"+items+"resp"+response);
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	private void onLoad() {
		xListView.stopRefresh();
		xListView.stopLoadMore();
		xListView.setRefreshTime("刚刚");
	}
}
