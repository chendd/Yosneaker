package com.yosneaker.client.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yosneaker.client.R;
import com.yosneaker.client.util.AnimatedGifDrawable;
import com.yosneaker.client.util.AnimatedImageSpan;

/**
 * 自定义评论项视图
 * @author chendd
 *
 */
public class CommentItemView extends RelativeLayout {

	private Context context;
	private LayoutInflater inflater;

	private RoundImageView riv_user_portrait;
	private TextView tv_user_name;
	private TextView tv_comment_time;
	private TextView tv_comment_content;
	private TextView tv_praise_count;
	private TextView tv_location;
	private RelativeLayout rl_comment;
	private TextView tv_no_comment;
	
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
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_article_comment_sun, this, true);
		riv_user_portrait = (RoundImageView) findViewById(R.id.riv_user_portrait);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_comment_time = (TextView) findViewById(R.id.tv_comment_time);
		tv_comment_content = (TextView) findViewById(R.id.tv_comment_content);
		tv_praise_count = (TextView) findViewById(R.id.tv_praise_count);
		tv_location = (TextView) findViewById(R.id.tv_location);
		rl_comment = (RelativeLayout) findViewById(R.id.rl_comment);
		tv_no_comment = (TextView) findViewById(R.id.tv_no_comment);
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
	 * 设置评论时间
	 * @param userName
	 */
	public void setCommentTime(String time) {
		tv_comment_time.setText(time);
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
		// 对内容做处理
		SpannableStringBuilder sb = handler(tv_comment_content,commentContent);
		tv_comment_content.setText(sb);
	}
	
	private SpannableStringBuilder handler(final TextView gifTextView,String content) {
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String tempText = m.group();
			try {
				String num = tempText.substring("#[face/png/f_static_".length(), tempText.length()- ".png]#".length());
				String gif = "face/gif/f" + num + ".gif";
				/**
				 * 如果open这里不抛异常说明存在gif，则显示对应的gif
				 * 否则说明gif找不到，则显示png
				 * */
				InputStream is = context.getAssets().open(gif);
				sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,new AnimatedGifDrawable.UpdateListener() {
							@Override
							public void update() {
								gifTextView.postInvalidate();
							}
						})), m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				is.close();
			} catch (Exception e) {
				String png = tempText.substring("#[".length(),tempText.length() - "]#".length());
				try {
					sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m.start(), m.end(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return sb;
	}
	
	/**
	 * 设置评论点赞数
	 * @param praiseCount
	 */
	public void setPraiseCount(String praiseCount) {
		tv_praise_count.setText(praiseCount);
	}
	
	/**
	 * 设置没评论时显示的内容
	 * @param commentContent
	 */
	public void setNoComment() {
		rl_comment.setVisibility(View.GONE);
		tv_no_comment.setVisibility(View.VISIBLE);
		tv_no_comment.setText(R.string.no_comment);
	}
	
}
