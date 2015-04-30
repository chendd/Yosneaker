package com.yosneaker.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yosneaker.client.R;

/**
 * 自定义评论项视图
 * @author chendd
 *
 */
public class CommentItemView extends LinearLayout {

	private Context context;
	private LayoutInflater inflater;

	private RoundImageView riv_user_portrait;
	private TextView tv_user_name;
	private TextView tv_comment_content;
	private TextView tv_praise_count;
	private TextView tv_location;
	
	public CommentItemView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public CommentItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}

	/**
	 * 初始化
	 */
	private void init() {		
//		iv_deletes = new ArrayList<ImageView>();
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_article_comment_sun, this, true);
		riv_user_portrait = (RoundImageView) findViewById(R.id.riv_user_portrait);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_comment_content = (TextView) findViewById(R.id.tv_comment_content);
		tv_praise_count = (TextView) findViewById(R.id.tv_praise_count);
		tv_location = (TextView) findViewById(R.id.tv_location);
	}
	
	/**
	 * 设置评论用户头像
	 * @param imageUri
	 */
	public void setUserPortrait(String imageUri) {
		ImageLoader.getInstance().displayImage(imageUri, riv_user_portrait);
	}
	
	/**
	 * 设置评论用户名
	 * @param userName
	 */
	public void setUserName(String userName) {
		tv_user_name.setText(userName);
	}
	
	/**
	 * 设置评论位置描述并显示(如:11楼)
	 * @param location
	 */
	public void setLocation(String location) {
		tv_location.setVisibility(View.VISIBLE);
		tv_location.setText(location);
	}
	
	/**
	 * 设置评论内容
	 * @param commentContent
	 */
	public void setCommentContent(String commentContent) {
		tv_comment_content.setText(commentContent);
	}
	
	/**
	 * 设置评论点赞数
	 * @param praiseCount
	 */
	public void setPraiseCount(String praiseCount) {
		tv_praise_count.setText(praiseCount);
	}
	
}
