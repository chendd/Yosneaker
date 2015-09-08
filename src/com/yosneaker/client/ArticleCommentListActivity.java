package com.yosneaker.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yosneaker.client.adapter.FaceGVAdapter;
import com.yosneaker.client.adapter.FaceVPAdapter;
import com.yosneaker.client.app.YosneakerAppState;
import com.yosneaker.client.model.Account;
import com.yosneaker.client.model.Comment;
import com.yosneaker.client.util.AppConstants;
import com.yosneaker.client.util.DateUtil;
import com.yosneaker.client.util.HttpClientUtil;
import com.yosneaker.client.view.CommentItemView;
import com.yosneaker.client.view.CustomEditText;

/**
 * 测评评论列表标题
 * 
 * @author chendd
 *
 */
public class ArticleCommentListActivity extends BaseActivity{

	private LinearLayout ll_hot_comments_list;	
	private LinearLayout ll_all_comments_list;
	
	private ViewPager mViewPager;
	private LinearLayout mDotsLayout;
	private LinearLayout chat_face_container;
	private CustomEditText input;
	private Button send;
	private ImageView image_face;//表情图标
	
	/**  等待框布局 */
	private LinearLayout mProgressDialog;
	
	private int articleId = 0;
	private List<Comment> result = null;
	private int total = 0;
	// 7列3行
	private int columns = 6;
	private int rows = 4;
	private List<View> views = new ArrayList<View>();
	private List<String> staticFacesList;
	
	private boolean isNoComment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_article_comments_list);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		initStaticFaces();
		ll_hot_comments_list = (LinearLayout) findViewById(R.id.ll_hot_comments_list);
		ll_all_comments_list = (LinearLayout) findViewById(R.id.ll_all_comments_list);
		
		mProgressDialog = (LinearLayout) findViewById(R.id.mProgressDialog);
		
		mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
		//表情布局
		chat_face_container=(LinearLayout) findViewById(R.id.chat_face_container);
		//表情下小圆点
		mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
		//表情图标
		image_face=(ImageView) findViewById(R.id.image_face);
		input = (CustomEditText) findViewById(R.id.input_sms);
		send = (Button) findViewById(R.id.send_sms);
		
		setTitleBarText(null);
		showTextViewLeft(true);
		
		InitViewPager();
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);	
		
		mViewPager.setOnPageChangeListener(new PageChange());
		image_face.setOnClickListener(this);
		input.setOnClickListener(this);
		send.setOnClickListener(this);
	}

	@Override
	public void fillDatas() {

		articleId = getIntent().getExtras().getInt("articleId");
		
		result =  YosneakerAppState.db.loadCommentList(articleId);
		if (result!=null) {
			refresh(result);
		}
		
		HttpClientUtil.getCommentsByArticleID(articleId, AppConstants.DEFAULT_PAGE, AppConstants.DEFAULT_ROWS,new JsonHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				mProgressDialog.setVisibility(View.GONE);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					total = (Integer) response.get("total");
					JSONArray list = response.getJSONArray("comments");
					result  = JSON.parseArray(list.toString(),Comment.class);
					if (result!=null) {
						refresh(result);
					}
					for (Comment item : result) {
						YosneakerAppState.db.saveComment(item);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
		});
		
//		CommentItemView commentItemView = new CommentItemView(this);
//		commentItemView.setCommentContent("我是评论");
//		commentItemView.setUserName("可爱大二狗");
//		commentItemView.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView.setPraiseCount("22");
//		
//		CommentItemView commentItemView2 = new CommentItemView(this);
//		commentItemView2.setCommentContent("阿萨德撒旦撒旦的撒啊是的撒大大大撒旦的撒大啊是的撒啊三大啊啊打算");
//		commentItemView2.setUserName("可爱大三狗");
//		commentItemView2.setUserPortrait("drawable://" + R.drawable.list_user_head2);
//		commentItemView2.setPraiseCount("33");
//		
//		ll_hot_comments_list.addView(commentItemView);	
//		ll_hot_comments_list.addView(commentItemView2);
//				
//		CommentItemView commentItemView3 = new CommentItemView(this);
//		commentItemView3.setCommentContent("我是评论");
//		commentItemView3.setUserName("可爱大二狗");
//		commentItemView3.setLocation("1楼");
//		commentItemView3.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView3.setPraiseCount("1");
//		
//		CommentItemView commentItemView4 = new CommentItemView(this);
//		commentItemView4.setCommentContent("阿萨德撒旦撒旦的撒啊是的撒大大大撒旦的撒大啊是的撒啊三大啊啊打算");
//		commentItemView4.setUserName("可爱大三狗");
//		commentItemView4.setLocation("2楼");
//		commentItemView4.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView4.setPraiseCount("3");	
//		
//		CommentItemView commentItemView5 = new CommentItemView(this);
//		commentItemView5.setCommentContent("我是评论");
//		commentItemView5.setUserName("可爱大二狗");
//		commentItemView5.setLocation("3楼");
//		commentItemView5.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView5.setPraiseCount("1");
//		
//		CommentItemView commentItemView6 = new CommentItemView(this);
//		commentItemView6.setCommentContent("阿萨德撒旦撒旦的撒啊是的撒大大大撒旦的撒大啊是的撒啊三大啊啊打算");
//		commentItemView6.setUserName("可爱大三狗");
//		commentItemView6.setLocation("4楼");
//		commentItemView6.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView6.setPraiseCount("3");
//		
//		CommentItemView commentItemView7 = new CommentItemView(this);
//		commentItemView7.setCommentContent("我是评论");
//		commentItemView7.setUserName("可爱大二狗");
//		commentItemView7.setLocation("5楼");
//		commentItemView7.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView7.setPraiseCount("1");
//		
//		CommentItemView commentItemView8 = new CommentItemView(this);
//		commentItemView8.setCommentContent("阿萨德撒旦撒旦的撒啊是的撒大大大撒旦的撒大啊是的撒啊三大啊啊打算");
//		commentItemView8.setUserName("可爱大三狗");
//		commentItemView8.setLocation("6楼");
//		commentItemView8.setUserPortrait("drawable://" + R.drawable.list_user_head);
//		commentItemView8.setPraiseCount("3");
//		
//		ll_all_comments_list.addView(commentItemView3);
//		ll_all_comments_list.addView(commentItemView4);
//		ll_all_comments_list.addView(commentItemView5);
//		ll_all_comments_list.addView(commentItemView6);
//		ll_all_comments_list.addView(commentItemView7);
//		ll_all_comments_list.addView(commentItemView8);

	}

	public void refresh(List<Comment> result) {
		ll_all_comments_list.removeAllViews();
		CommentItemView commentItemView = new CommentItemView(ArticleCommentListActivity.this);
		if(total!=0) {
			isNoComment = false;
			for (Comment comment : result) {
				commentItemView.setCommentContent(comment.getArticleCommentContent());
				commentItemView.setUserName(comment.getAccount().getAccountUsername());
				commentItemView.setCommentTime(DateUtil.getIntervalDate(comment.getArticleCommentPublishTime()));
				commentItemView.setUserPortrait(comment.getAccount().getAccountImages());
				commentItemView.setPraiseCount(comment.getArticleCommentTopNumber()+"");
				ll_all_comments_list.addView(commentItemView);
//				if() { 判断是否属于热门评论 
//					ll_hot_comments_list.addView(commentItemView);
//				}
			}
		}else {
			isNoComment = true;
			commentItemView.setNoComment();
			ll_all_comments_list.addView(commentItemView);
		}
		mProgressDialog.setVisibility(View.GONE);
	}
	
	public void addComment(Comment comment) {
		if(isNoComment) {
			ll_all_comments_list.removeAllViews();
		}
		isNoComment = false;
		CommentItemView commentItemView = new CommentItemView(ArticleCommentListActivity.this);
		commentItemView.setCommentContent(comment.getArticleCommentContent());
		commentItemView.setUserName(comment.getAccount().getAccountUsername());
		commentItemView.setCommentTime(DateUtil.getIntervalDate(comment.getArticleCommentPublishTime()));
		commentItemView.setUserPortrait(comment.getAccount().getAccountImages());
		commentItemView.setPraiseCount(comment.getArticleCommentTopNumber()+"");
		ll_all_comments_list.addView(commentItemView);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			onBackPressed();
		}else if(v == send&&!TextUtils.isEmpty(input.getText().toString())) {
			Comment comment = new Comment();
			comment.setArticleCommentContent(input.getText().toString());
			comment.setArticleCommentArticleId(articleId);
			comment.setArticleCommentPublishTime(new Date());
			comment.setArticleCommentTopNumber(0);
			Account account = new Account();
			account.setAccountUsername("游客");
			account.setAccountImages("drawable://" + R.drawable.list_user_head);
			comment.setAccount(account);
			addComment(comment);
			HttpClientUtil.addComment(comment, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, JSONObject errorResponse) {
					System.out.print("onFailure");
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						JSONObject response) {
					System.out.print("onSuccess");
				}
				
			});
			
			
			input.setText("");
		}else if(v == input) {
			if(chat_face_container.getVisibility()==View.VISIBLE){
				chat_face_container.setVisibility(View.GONE);
			}
		}else if(v == image_face) {
			hideSoftInputView();//隐藏软键盘
			if(chat_face_container.getVisibility()==View.GONE){
				chat_face_container.setVisibility(View.VISIBLE);
			}else{
				chat_face_container.setVisibility(View.GONE);
			}
		}
		
	}
	
	/*
	 * 初始表情 *
	 */
	private void InitViewPager() {
		// 获取页数
		for (int i = 0; i < getPagerCount(); i++) {
			views.add(viewPagerItem(i));
			LayoutParams params = new LayoutParams(16, 16);
			mDotsLayout.addView(dotsItem(i), params);
		}
		FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
		mViewPager.setAdapter(mVpAdapter);
		mDotsLayout.getChildAt(0).setSelected(true);
	}

	private View viewPagerItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.face_gridview, null);//表情布局
		GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
		/**
		 * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns *　rows　－　1; 空出最后一个位置给删除图标
		 * */
		List<String> subList = new ArrayList<String>();
		subList.addAll(staticFacesList
				.subList(position * (columns * rows - 1),
						(columns * rows - 1) * (position + 1) > staticFacesList
								.size() ? staticFacesList.size() : (columns
								* rows - 1)
								* (position + 1)));
		/**
		 * 末尾添加删除图标
		 * */
		subList.add("emotion_del_normal.png");
		FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
		gridview.setAdapter(mGvAdapter);
		gridview.setNumColumns(columns);
		// 单击表情执行的操作
		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				try {
					String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
					if (!png.contains("emotion_del_normal")) {// 如果不是删除图标
						insert(getFace(png));
					} else {
						delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return gridview;
	}

	private SpannableStringBuilder getFace(String png) {
		SpannableStringBuilder sb = new SpannableStringBuilder();
		try {
			/**
			 * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
			 * 所以这里对这个tempText值做特殊处理
			 * 格式：#[face/png/f_static_000.png]#，以方便判斷當前圖片是哪一個
			 * */
			String tempText = "#[" + png + "]#";
			sb.append(tempText);
			sb.setSpan(
					new ImageSpan(ArticleCommentListActivity.this, BitmapFactory
							.decodeStream(getAssets().open(png))), sb.length()
							- tempText.length(), sb.length(),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb;
	}

	/**
	 * 向输入框里添加表情
	 * */
	private void insert(CharSequence text) {
		int iCursorStart = Selection.getSelectionStart((input.getText()));
		int iCursorEnd = Selection.getSelectionEnd((input.getText()));
		if (iCursorStart != iCursorEnd) {
			input.getText().replace(iCursorStart, iCursorEnd, "");
		}
		int iCursor = Selection.getSelectionEnd((input.getText()));
		input.getText().insert(iCursor, text);
	}

	/**
	 * 删除图标执行事件
	 * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
	 * */
	private void delete() {
		if (input.getText().length() != 0) {
			int iCursorEnd = Selection.getSelectionEnd(input.getText());
			int iCursorStart = Selection.getSelectionStart(input.getText());
			if (iCursorEnd > 0) {
				if (iCursorEnd == iCursorStart) {
					if (isDeletePng(iCursorEnd)) {
						String st = "#[face/png/f_static_000.png]#";
						input.getText().delete(
								iCursorEnd - st.length(), iCursorEnd);
					} else {
						input.getText().delete(iCursorEnd - 1,
								iCursorEnd);
					}
				} else {
					input.getText().delete(iCursorStart,
							iCursorEnd);
				}
			}
		}
	}

	/**
	 * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
	 * **/
	private boolean isDeletePng(int cursor) {
		String st = "#[face/png/f_static_000.png]#";
		String content = input.getText().toString().substring(0, cursor);
		if (content.length() >= st.length()) {
			String checkStr = content.substring(content.length() - st.length(),
					content.length());
			String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(checkStr);
			return m.matches();
		}
		return false;
	}

	private ImageView dotsItem(int position) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dot_image, null);
		ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
		iv.setId(position);
		return iv;
	}

	/**
	 * 根据表情数量以及GridView设置的行数和列数计算Pager数量
	 * @return
	 */
	private int getPagerCount() {
		int count = staticFacesList.size();
		return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
				: count / (columns * rows - 1) + 1;
	}

	/**
	 * 初始化表情列表staticFacesList
	 */
	private void initStaticFaces() {
		try {
			staticFacesList = new ArrayList<String>();
			String[
			       ] faces = getAssets().list("face/png");
			//将Assets中的表情名称转为字符串一一添加进staticFacesList
			for (int i = 0; i < faces.length; i++) {
				staticFacesList.add(faces[i]);
			}
			//去掉删除图片
			staticFacesList.remove("emotion_del_normal.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 表情页改变时，dots效果也要跟着改变
	 * */
	class PageChange implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
				mDotsLayout.getChildAt(i).setSelected(false);
			}
			mDotsLayout.getChildAt(arg0).setSelected(true);
		}

	}
	
}
