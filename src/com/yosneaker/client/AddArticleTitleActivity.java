package com.yosneaker.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yosneaker.client.model.Article;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

/**
 * 发布测评标题
 * 
 * @author chendd
 *
 */
public class AddArticleTitleActivity extends BaseActivity{

	private EditText et_comment_title;
	private String commentTitle;
	private int action;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_add_article_title);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_next);
		
		et_comment_title = (EditText) findViewById(R.id.et_comment_title);
		
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);		
		getTextViewRight1().setOnClickListener(this);		
	
	}

	@Override
	public void fillDatas() {
		Intent intent = getIntent();
		action = intent.getIntExtra("action", 0);
		if (action == 0) {
			Article commentDraft = (Article) intent.getExtras().getSerializable("CommentDraft");
			et_comment_title.setText(commentDraft.getArticleTitle());
		}

	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			onBackPressed();
		}else if (v == getTextViewRight1()) {
			commentTitle = et_comment_title.getText().toString();
			if (TextUtils.isEmpty(commentTitle)) {
				et_comment_title.setError(getResources().getString(R.string.error_comment_title_no_null));
			}else {				
				Article commentDraft = new Article();
				commentDraft.setArticleTitle(commentTitle);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				commentDraft.setArticleCreateTime(Integer.parseInt(df.format(new Date())));
				if (action == 0) {
					Intent intent = new Intent();
					intent.putExtra("CommentDraft",commentDraft);
					setResult(RESULT_OK, intent);
				}else {
					Intent intent = new Intent(AddArticleTitleActivity.this,EditArticleActivity.class);
					intent.putExtra("CommentDraft",commentDraft);
					startActivity(intent);
				}
				AddArticleTitleActivity.this.finish();
			}
		}
	}

	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)   {  
		customBackPressed();
        return false;           
    }  
	
	
	public void customBackPressed() {
		commentTitle = et_comment_title.getText().toString();
		if (!TextUtils.isEmpty(commentTitle)) {
			Article commentDraft = new Article();
			commentDraft.setArticleTitle(commentTitle);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			commentDraft.setArticleCreateTime(Integer.parseInt(df.format(new Date())));
			Intent intent = new Intent();
			intent.putExtra("CommentDraft",commentDraft);
			setResult(RESULT_OK, intent);
			AddArticleTitleActivity.this.finish();
		}else {
			AddArticleTitleActivity.this.finish();
		}
	}
	
}
