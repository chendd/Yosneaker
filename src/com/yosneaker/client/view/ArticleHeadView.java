package com.yosneaker.client.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yosneaker.client.R;

/**
 * 自定义测评顶部视图
 * @author chendd
 *
 */
public class ArticleHeadView extends LinearLayout {

	private Context context;
	private LayoutInflater inflater;
	
	private ImageView iv_article_bg;
	private RoundImageView riv_article_user_icon;
	private TextView tv_article_date;
	private TextView tv_article_title;
	private ImageView iv_article_edit;
	
	public ArticleHeadView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public ArticleHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}

	/**
	 * 初始化
	 */
	private void init() {		
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_article_head, this, true);
		iv_article_bg = (ImageView) findViewById(R.id.iv_article_bg);
		riv_article_user_icon = (RoundImageView) findViewById(R.id.riv_article_user_icon);
		tv_article_date = (TextView) findViewById(R.id.tv_article_date);
		tv_article_title = (TextView) findViewById(R.id.tv_article_title);
		iv_article_edit = (ImageView) findViewById(R.id.iv_article_edit);
	}
	
	public void setArticleBg(String imageUri) {
		ImageLoader.getInstance().displayImage(imageUri, iv_article_bg);
	}
	
	public void setArticleBg(Bitmap bitmap) {
		iv_article_bg.setImageBitmap(bitmap);
	}
	
	public void setArticleUserPortrait(String imageUri) {
		ImageLoader.getInstance().displayImage(imageUri, riv_article_user_icon);
	}
	
	public void setArticleDate(String articleDate) {
		tv_article_date.setText(articleDate);
	}
	
	public void setArticleTitle(String articleTitle) {
		tv_article_title.setText(articleTitle);
	}
	
	public void setArticleEditVisibility(int visibility) {
		iv_article_edit.setVisibility(visibility);
	}
	
	public ImageView getArticleEditView() {
		return iv_article_edit;
	}
	
	public void setArticleEditListener(OnClickListener l) {
		iv_article_edit.setOnClickListener(l);
	}
	
}
