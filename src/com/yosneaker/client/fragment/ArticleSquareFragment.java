package com.yosneaker.client.fragment;

import java.util.ArrayList;
import java.util.Date;
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
import com.yosneaker.client.app.YosneakerAppState;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.util.Constants;
import com.yosneaker.client.util.DateUtil;
import com.yosneaker.client.util.HttpClientUtil;
import com.yosneaker.client.view.XListView;
import com.yosneaker.client.view.XListView.IXListViewListener;

/**
 * 
 * 广场Fragment
 * 
 * @author chendd
 *
 */
public class ArticleSquareFragment extends BaseFragment implements IXListViewListener{
	
	private View viewFragment;
	private XListView xListView=null;
	private ArticleAdapter mAdapter;
	private ArrayList<ArticleList> items = new ArrayList<ArticleList>();
	private Handler mHandler;
	private Date lastRefreshDate;//最后一次刷新时间
	
	int page;
	int rows;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewFragment = inflater.inflate(R.layout.fragment_square_article,
				container, false);
		
		page = Constants.DEFAULT_PAGE;
		rows = Constants.DEFAULT_ROWS;
		lastRefreshDate = new Date();
		
		initViews();
		xListView.setPullLoadEnable(false);
		xListView.autoRefresh();
		return viewFragment;
	}

	private void initViews(){
		xListView=(XListView) viewFragment.findViewById(R.id.xListView);	
		xListView.setPullLoadEnable(true);
		items.addAll(YosneakerAppState.db.loadArticleList(page, rows));
		mAdapter = new ArticleAdapter(getActivity(),items);
		xListView.setAdapter(mAdapter);
		xListView.setXListViewListener(this);
		xListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				ArticleList item = (ArticleList)parent.getItemAtPosition(position);
				bundle.putInt("articleId",item.getArticleId());
				System.out.println("=======>articleId is :"+item.getArticleId());
				gotoExistActivity(ArticleDetailActivity.class,bundle);
			}
			
		});
		mHandler = new Handler();
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems(Constants.DEFAULT_PAGE,rows*page);
				mAdapter = new ArticleAdapter(getActivity(),items);
				xListView.setAdapter(mAdapter);
				onLoad();
				lastRefreshDate = new Date();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems(++page, rows);
				mAdapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	
	private void geneItems(final int page,int rows) {
		HttpClientUtil.getPublicArticle(page, rows, new JsonHttpResponseHandler(){
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				System.out.println("==========4"+errorResponse);
				items.clear();
				items.addAll(YosneakerAppState.db.loadArticleList(Constants.DEFAULT_PAGE, Constants.DEFAULT_ROWS));
				mAdapter.notifyDataSetChanged();
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
					for (ArticleList articleList : result) {
						YosneakerAppState.db.saveArticleList(articleList);
					}
					if(page == 1) {
						items.clear();
					}
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
		xListView.setRefreshTime(DateUtil.getLocalDate(lastRefreshDate));
		xListView.stopRefresh();
		xListView.stopLoadMore();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				xListView.setPullLoadEnable(true);
			}
		}, 2000);
	} 
}
