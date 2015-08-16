package com.yosneaker.client;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yosneaker.client.app.YosneakerAppState;
import com.yosneaker.client.model.Account;
import com.yosneaker.client.model.ArticleDetails;
import com.yosneaker.client.model.ArticleItem;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.model.Brand;
import com.yosneaker.client.model.IntentionInfo;
import com.yosneaker.client.model.Model;
import com.yosneaker.client.util.BitmapUtil;
import com.yosneaker.client.util.Constants;
import com.yosneaker.client.util.HttpClientUtil;
import com.yosneaker.client.view.ArticleHeadView;
import com.yosneaker.client.view.ArticleItemView;
import com.yosneaker.client.view.CommentItemView;
import com.yosneaker.client.view.CustomScrollView;
import com.yosneaker.client.view.CustomScrollView.OnScrollListener;
import com.yosneaker.client.view.PersonalDataView;

/**
 * 测评详情
 * 
 * @author chendd
 *
 */
public class ArticleDetailActivity extends BaseActivity implements OnScrollListener{

	/**  测评布局 */
	private LinearLayout mLayout;
	
	/**  自定义的ScrollView */
	private CustomScrollView mScrollView;
	/**  在MyScrollView里面的购买布局 */
	private LinearLayout mBuyLayout;
	/**  位于顶部的购买布局 */
	private LinearLayout mTopBuyLayout;
	
	/**  等待框布局 */
	private LinearLayout mProgressDialog;
	
	private ImageView iv_want;
	private ImageView iv_buy;
	private LinearLayout ll_want_count;
	private LinearLayout ll_buy_count;
	private TextView tv_want_count;
	private TextView tv_buy_count;
	
	private ImageView iv_top_want;
	private ImageView iv_top_buy;
	private TextView tv_top_want_count;
	private TextView tv_top_buy_count;
	
	private LinearLayout ll_hot_comments;
	
	private TextView tv_detail_brand;
	private TextView tv_detail_model;
	private TextView tv_detail_intro_content;
	
	private LinearLayout ll_detail_comment_item;
	private TextView tv_see_more;
	
	private ArticleHeadView ahv_article_detail_head;
	
	private PersonalDataView pdv_article_personal_data;
	
	private int articleId = 0;
	
	private ArticleDetails detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		articleId = getIntent().getExtras().getInt("articleId");
		Log.d("articleId",""+ articleId);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_article_detail);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		mLayout = (LinearLayout) findViewById(R.id.activity_comment_detail_layout);
		mScrollView = (CustomScrollView) findViewById(R.id.scrollView);
		mBuyLayout = (LinearLayout) findViewById(R.id.buy);
		mTopBuyLayout = (LinearLayout) findViewById(R.id.top_buy_layout);
		
		mProgressDialog = (LinearLayout) findViewById(R.id.mProgressDialog);
		
		iv_want = (ImageView) mBuyLayout.findViewById(R.id.iv_top_want);
		iv_buy = (ImageView) mBuyLayout.findViewById(R.id.iv_top_buy);
		ll_want_count = (LinearLayout) findViewById(R.id.ll_want_count);
		ll_buy_count = (LinearLayout) findViewById(R.id.ll_buy_count);
		tv_want_count = (TextView) findViewById(R.id.tv_want_count);
		tv_buy_count = (TextView) findViewById(R.id.tv_buy_count);
		
		iv_top_want = (ImageView) mTopBuyLayout.findViewById(R.id.iv_top_want);
		iv_top_buy = (ImageView) mTopBuyLayout.findViewById(R.id.iv_top_buy);
		tv_top_want_count = (TextView) mTopBuyLayout.findViewById(R.id.tv_top_want_count);
		tv_top_buy_count = (TextView) mTopBuyLayout.findViewById(R.id.tv_top_buy_count);
		
		ll_hot_comments = (LinearLayout) findViewById(R.id.ll_hot_comments_list);
		
		tv_detail_brand = (TextView) findViewById(R.id.tv_detail_brand);
		tv_detail_model = (TextView) findViewById(R.id.tv_detail_model);
		tv_detail_intro_content = (TextView) findViewById(R.id.tv_detail_intro_content);
		
		ll_detail_comment_item = (LinearLayout) findViewById(R.id.ll_detail_comment_item);
		tv_see_more = (TextView) findViewById(R.id.tv_see_more);
		
		ahv_article_detail_head = (ArticleHeadView) findViewById(R.id.ahv_article_detail_head);
		
		pdv_article_personal_data = (PersonalDataView) findViewById(R.id.pdv_article_personal_data);
		
		
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		showTextViewRight2(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_share);
		getTextViewRight2().setBackgroundResource(R.drawable.ic_unstar);
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		getTextViewRight1().setOnClickListener(this);		
		getTextViewRight2().setOnClickListener(this);
		
		mScrollView.setOnScrollListener(this);
		mLayout.getViewTreeObserver()
		.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 这一步很重要，使得上面的购买布局和下面的购买布局重合
				onScroll(mScrollView.getScrollY());

				System.out.println(mScrollView.getScrollY());
			}
		});
		iv_want.setOnClickListener(this);
		iv_buy.setOnClickListener(this);
		iv_top_want.setOnClickListener(this);
		iv_top_buy.setOnClickListener(this);
		tv_see_more.setOnClickListener(this);
	}

	@Override
	public void fillDatas() {
		
		ahv_article_detail_head.setArticleEditVisibility(View.GONE);
		
		HttpClientUtil.getArticleDetial(articleId, new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				System.out.println("*********"+errorResponse);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				System.out.println("*********"+response);
				detail = JSON.parseObject(response.toString(), ArticleDetails.class);
				
				
				Model model = detail.getModel();
				if (model!=null) {
					//设置介绍
					tv_detail_intro_content.setText(model.getModelStory());
					//设置型号
					tv_detail_model.setText(model.getModelName());
				}
				
				Brand brand = detail.getBrand();
				if (brand!=null) {
					//设置品牌
					tv_detail_brand.setText(brand.getBrandName());
				}
				
				ArticleList article = detail.getArticle();
				if(article!=null) {
					//设置标题
					ahv_article_detail_head.setArticleTitle(article.getArticleTitle());
					ahv_article_detail_head.setArticleDate(""+article.getArticleCreateTime());
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmdd", Locale.CHINA);
					ahv_article_detail_head.setArticleDate(simpleDateFormat.format(article.getArticleCreateTime()));
				}
				
				Account authorInfo = detail.getAuthorInfo();
				if(authorInfo != null) {
					//个人数据相关
					ahv_article_detail_head.setArticleUserPortrait(authorInfo.getAccountImages());
					pdv_article_personal_data.setPersonalBounce(""+authorInfo.getAccountBounce());
					pdv_article_personal_data.setPersonalHeight(""+authorInfo.getAccountStature());
					pdv_article_personal_data.setPersonalWeight(""+authorInfo.getAccountWeight());
				}
				
				IntentionInfo intendInfo = detail.getIntendInfo();
				if(intendInfo!= null) {
					tv_top_want_count.setText(""+intendInfo.getWantCount());
					tv_top_buy_count.setText(""+intendInfo.getBuyCount());
					tv_want_count.setText(""+intendInfo.getWantCount());
					tv_buy_count.setText(""+intendInfo.getBuyCount());
				}
				
				
				// 模拟数据
				Bitmap bitmap = BitmapFactory.decodeResource(ArticleDetailActivity.this.getResources(), R.drawable.list_bg);
				try {
					bitmap = BitmapUtil.getScaleBitmap(bitmap, YosneakerAppState.getInstance().mWidth,Constants.BG_SCALE);
					ahv_article_detail_head.setArticleBg(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				int i = 1;
				List<ArticleItem> items = detail.getItems();
				if(items!= null) {
					for(ArticleItem articleItem : items){
						ArticleItemView articleItemView = new ArticleItemView(ArticleDetailActivity.this);
						articleItemView.setItemOrder(++i);
						articleItemView.setItemName(articleItem.getItemTitle());
						articleItemView.setItemContent(articleItem.getItemContent());
						articleItemView.setItemAssess(3);
						for(String image : articleItem.getImagesList()){
							articleItemView.addItemImage(image);
						}
						ll_detail_comment_item.addView(articleItemView);
					}
				}
				mProgressDialog.setVisibility(View.GONE);
			}
			
		});
		
/*		ArticleItemView articleItemView = new ArticleItemView(ArticleDetailActivity.this);
		articleItemView.setItemOrder(1);
		articleItemView.setItemName("外观");
		articleItemView.setItemContent("谈论Return of The Mac的外观是一件颇令人伤感的事情，这双似是而非的低端鞋拥有adidas T-Mac II大部分的外观基因，这是人们喜欢Return of The Mac的原因，同时也成为了部分鞋友厌恶它的理由。鞋头的线条依然是外观的重点");
		articleItemView.setItemAssess(3);
		articleItemView.addItemImage("drawable://" + R.drawable.list_bg);
		articleItemView.addItemImage("drawable://" + R.drawable.list_bg2);
		
		ArticleItemView articleItemView2 = new ArticleItemView(ArticleDetailActivity.this);
		articleItemView2.setItemOrder(2);
		articleItemView2.setItemName("还是外观");
		articleItemView2.setItemContent("再谈论Return of The Mac的外观是一件颇令人伤感的事情，这双似是而非的低端鞋拥有adidas T-Mac II大部分的外观基因，这是人们喜欢Return of The Mac的原因，同时也成为了部分鞋友厌恶它的理由。鞋头的线条依然是外观的重点");
		articleItemView2.setItemAssess(4);
		
		ll_detail_comment_item.addView(articleItemView);
		ll_detail_comment_item.addView(articleItemView2);*/
		
		CommentItemView commentItemView = new CommentItemView(ArticleDetailActivity.this);
		commentItemView.setCommentContent("我是评论");
		commentItemView.setUserName("可爱大二狗");
		commentItemView.setUserPortrait("drawable://" + R.drawable.list_user_head);
		commentItemView.setPraiseCount("22");
		
		CommentItemView commentItemView2 = new CommentItemView(ArticleDetailActivity.this);
		commentItemView2.setCommentContent("阿萨德撒旦撒旦的撒啊是的撒大大大撒旦的撒大啊是的撒啊三大啊啊打算");
		commentItemView2.setUserName("可爱大三狗");
		commentItemView2.setUserPortrait("drawable://" + R.drawable.list_user_head2);
		commentItemView2.setPraiseCount("33");
		
		ll_hot_comments.addView(commentItemView);
		ll_hot_comments.addView(commentItemView2);
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			onBackPressed();
		}else if (v == getTextViewRight1()) {
			showToast("分享");
		}else if (v == getTextViewRight2()) {
			showToast("收藏");
		}else if(v == tv_see_more) {
			gotoExistActivity(ArticleCommentListActivity.class, new Bundle());
		}else if(v == iv_top_want || v == iv_want) {
			showToast("想入");
		}else if(v == iv_top_buy || v == iv_buy) {
			showToast("已入");
		}
	}

	@Override
	public void onScroll(int scrollY) {
		int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
		mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(),
				mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
	}


}
