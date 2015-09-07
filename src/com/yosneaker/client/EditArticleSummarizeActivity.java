package com.yosneaker.client;

import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Slider.OnValueChangedListener;
import com.yosneaker.client.model.Article;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 编辑测评总评
 * 
 * @author chendd
 *
 */
public class EditArticleSummarizeActivity extends BaseActivity{

	private EditText edit_text;
	private TextView text_view;
	private Slider rating_bar;
	private int BigIndex;
	
	private String sumText;
	private int sumStar;
	
	private boolean rb_flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_edit_article_summarize);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		BigIndex = Integer.parseInt(getResources().getString(R.string.comment_edit_summarize_maxText));
		
		edit_text = (EditText)findViewById(R.id.edit_text);
		text_view = (TextView)findViewById(R.id.text_view);
		rating_bar = (Slider)findViewById(R.id.rating_bar);
		
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_ok);
	}

	@Override
	public void addListnners() {	
		getTextViewLeft().setOnClickListener(this);	
		getTextViewRight1().setOnClickListener(this);
		edit_text.addTextChangedListener(new EditTextWatcher());
		rating_bar.setOnValueChangedListener(new OnValueChangedListener() {
			
			@Override
			public void onValueChanged(int value) {
				rb_flag = true;
			}
		});
	}

	@Override
	public void fillDatas() {
		Intent intent = getIntent();
		Article commentDraft = (Article) intent.getExtras().getSerializable("CommentDraft");
		edit_text.setText(commentDraft.getArticleComment());
		rating_bar.setValue(commentDraft.getArticleLevel());
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			customBackPressed();
		} else if (v == getTextViewRight1()) {
			sumText = edit_text.getText().toString();
			sumStar = (int) (rating_bar.getValue());
			if (!rb_flag) {
				showToast(getResources().getString(
						R.string.error_comment_sumstar_no_null));
			} else {
				gotoEditComment(sumStar, sumText);
			}
		}
	}

	private class EditTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			 String edit = edit_text.getText().toString();
			 
			edit_text.setVisibility(View.VISIBLE);
			if (edit.length() <= BigIndex) {
				text_view.setText(""+(BigIndex - edit.length()));
			} else {
				 edit_text.setText(edit.substring(0,BigIndex));
				 edit_text.setSelection(edit.substring(0,BigIndex).length());
				 String tmp = getResources().getString(R.string.toast_comment_edit_maxText);
				 String toastStr=String.format(tmp,getResources().getString(R.string.comment_detail_intro),BigIndex);
				 showToast(toastStr);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence cs, int arg1, int arg2,int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		}

	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)   {  
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			customBackPressed();
			return false;   
		}
        return super.onKeyDown(keyCode, event);          
    }  
	
	
	public void customBackPressed() {
		sumText = edit_text.getText().toString();
		sumStar = (int) (rating_bar.getValue());
		if ((!TextUtils.isEmpty(sumText))) {
			Builder builder = new Builder(EditArticleSummarizeActivity.this);
            final String[] items = {getResources().getString(R.string.dialog_comment_save_sum),getResources().getString(R.string.dialog_comment_drop_sum) };
            builder.setItems(items, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                	if (which == 0) {
                		gotoEditComment(sumStar, sumText);
					} else if(which == 1){
						setResult(RESULT_CANCELED, new Intent());
						EditArticleSummarizeActivity.this.finish();
					}
                }

            }).show();
		}else {
			super.onBackPressed();
		}
	}
	
	public void gotoEditComment(int sumStar,String sumText) {
		Intent intent = new Intent();
		Article commentDraft = new Article();
		if (rb_flag) {
			if (rb_flag) {
				commentDraft.setArticleLevel(sumStar);
			}
			if (!TextUtils.isEmpty(sumText)) {
				commentDraft.setArticleComment(sumText);
			}
			intent.putExtra("CommentDraft",commentDraft);
			setResult(RESULT_OK, intent);
		}else {
			setResult(RESULT_CANCELED, intent);
		}		
		EditArticleSummarizeActivity.this.finish();
	}
	
	
	
}
