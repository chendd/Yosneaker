package com.yosneaker.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gc.materialdesign.views.NiceSpinner;
import com.yosneaker.client.model.Article;
import com.yosneaker.client.util.AppConstants;
import com.yosneaker.client.view.RadioGroup;
import com.yosneaker.client.view.RadioGroup.OnCheckedChangeListener;

/**
 * 编辑测评简介
 * 
 * @author chendd
 * 
 */
public class EditArticleIntroActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private EditText et_intro;
	private TextView tv_intro;
	private int BigIndex;
	private int checkIndex = -1;
	private RadioGroup rg_brand;
	private RadioButton rb_nick, rb_adidas, rb_lining, rb_reebok, rb_ua,
			rb_pick, rb_anta, rb_361, rb_other;

	private NiceSpinner et_model;

	private String modelText;
	private String introText;

	private ArrayList<String> brands = new ArrayList<String>();
	private ArrayList<RadioButton> brandsView = new ArrayList<RadioButton>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_article_intro);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void initViews() {

		BigIndex = Integer.parseInt(getResources().getString(
				R.string.comment_edit_intro_maxText));

		et_intro = (EditText) findViewById(R.id.et_intro);
		tv_intro = (TextView) findViewById(R.id.tv_intro);
		et_intro.addTextChangedListener(new EditTextWatcher());

		rg_brand = (RadioGroup) findViewById(R.id.rg_brand);

		rb_nick = (RadioButton) findViewById(R.id.rb_nick);
		rb_adidas = (RadioButton) findViewById(R.id.rb_adidas);
		rb_lining = (RadioButton) findViewById(R.id.rb_lining);
		rb_reebok = (RadioButton) findViewById(R.id.rb_reebok);
		rb_ua = (RadioButton) findViewById(R.id.rb_ua);
		rb_pick = (RadioButton) findViewById(R.id.rb_pick);
		rb_anta = (RadioButton) findViewById(R.id.rb_anta);
		rb_361 = (RadioButton) findViewById(R.id.rb_361);
		rb_other = (RadioButton) findViewById(R.id.rb_other);

		et_model = (NiceSpinner) findViewById(R.id.et_model);

		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_ok);
	}

	@Override
	public void addListnners() {

		rg_brand.setOnCheckedChangeListener(this);

		getTextViewLeft().setOnClickListener(this);
		getTextViewRight1().setOnClickListener(this);
	}

	@Override
	public void fillDatas() {
		Intent intent = getIntent();
		brands.add(getResources().getString(R.string.hot_brand_nike));
		brands.add(getResources().getString(R.string.hot_brand_adidas));
		brands.add(getResources().getString(R.string.hot_brand_lining));
		brands.add(getResources().getString(R.string.hot_brand_reebok));
		brands.add(getResources().getString(R.string.hot_brand_ua));
		brands.add(getResources().getString(R.string.hot_brand_pick));
		brands.add(getResources().getString(R.string.hot_brand_anta));
		brands.add(getResources().getString(R.string.hot_brand_361));
		brands.add(getResources().getString(R.string.hot_brand_other));
		brandsView.add(rb_nick);
		brandsView.add(rb_adidas);
		brandsView.add(rb_lining);
		brandsView.add(rb_reebok);
		brandsView.add(rb_ua);
		brandsView.add(rb_pick);
		brandsView.add(rb_anta);
		brandsView.add(rb_361);
		brandsView.add(rb_other);
		
		Article commentDraft = (Article) intent.getExtras().getSerializable("CommentDraft");
		et_intro.setText(commentDraft.getArticleDescription());
//		et_model.setText(""+commentDraft.getArticleModelId());
		List<String> dataset = new LinkedList<String>(Arrays.asList("热门型号One", "热门型号Two", "热门型号Three", "热门型号Four", "热门型号Five"));
		et_model.attachDataSource(dataset);
		int brandId = brands.indexOf(commentDraft.getArticleTrademarkId());
		checkIndex = brandId;
		if (brandId!=-1&&brandId<brands.size()) {
			brandsView.get(brandId).setChecked(true);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			customBackPressed();
		} else if (v == getTextViewRight1()) {
			introText = et_intro.getText().toString();
			modelText = et_model.getText().toString();
			if (checkIndex == -1) {
				showToast(getResources().getString(
						R.string.error_comment_brand_no_null));
			} else if (TextUtils.isEmpty(modelText)) {
				et_model.setError(getResources().getString(
						R.string.error_comment_model_no_null));
			} else if (TextUtils.isEmpty(introText)) {
				et_intro.setError(getResources().getString(
						R.string.error_comment_intro_no_null));
			} else {
				gotoEditComment(checkIndex, modelText, introText);
			}
		}
	}

	private class EditTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			String edit = et_intro.getText().toString();

			et_intro.setVisibility(View.VISIBLE);
			if (edit.length() <= BigIndex) {
				tv_intro.setText("" + (BigIndex - edit.length()));
			} else {
				et_intro.setText(edit.substring(0, BigIndex));
				et_intro.setSelection(edit.substring(0, BigIndex).length());
				String tmp = getResources().getString(
						R.string.toast_comment_edit_maxText);
				String toastStr = String
						.format(tmp,
								getResources().getString(
										R.string.comment_detail_intro),
								BigIndex);
				showToast(toastStr);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence cs, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if (group == rg_brand) {
			if (checkedId == rb_nick.getId()) {
				checkIndex = 0;
			} else if (checkedId == rb_adidas.getId()) {
				checkIndex = 1;
			} else if (checkedId == rb_lining.getId()) {
				checkIndex = 2;
			} else if (checkedId == rb_reebok.getId()) {
				checkIndex = 3;
			} else if (checkedId == rb_ua.getId()) {
				checkIndex = 4;
			} else if (checkedId == rb_pick.getId()) {
				checkIndex = 5;
			} else if (checkedId == rb_anta.getId()) {
				checkIndex = 6;
			} else if (checkedId == rb_361.getId()) {
				checkIndex = 7;
			} else if (checkedId == rb_other.getId()) {
				checkIndex = 8;
			}
			Log.d(AppConstants.TAG, "checkIndex:" + checkIndex);
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
		introText = et_intro.getText().toString();
		modelText = et_model.getText().toString();
		if ((!TextUtils.isEmpty(modelText))||(!TextUtils.isEmpty(introText))) {
			Builder builder = new Builder(EditArticleIntroActivity.this);
            final String[] items = {getResources().getString(R.string.dialog_comment_save_intro),getResources().getString(R.string.dialog_comment_drop_intro) };
            builder.setItems(items, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                	if (which == 0) {
						gotoEditComment(checkIndex, modelText, introText);
					} else if(which == 1){
						setResult(RESULT_CANCELED, new Intent());
						EditArticleIntroActivity.this.finish();
					}
                }

            }).show();
		}else {
			super.onBackPressed();
		}
	}

	
	public void gotoEditComment(int checkIndex,String modelText,String introText) {
		Intent intent = new Intent();
		Article commentDraft = new Article();
		if (checkIndex != -1||!TextUtils.isEmpty(modelText)||!TextUtils.isEmpty(introText)) {
			if (checkIndex != -1) {
				//commentDraft.setArticleTrademarkId(brands.get(checkIndex));
				commentDraft.setArticleTrademarkId(1);
			}
			if (!TextUtils.isEmpty(modelText)) {
				commentDraft.setArticleModelId(1);
				//commentDraft.setArticleModelId(modelText);
			}
			if (!TextUtils.isEmpty(introText)) {
				commentDraft.setArticleDescription(introText);
			}
			intent.putExtra("CommentDraft",commentDraft);
			setResult(RESULT_OK, intent);
		}else {
			setResult(RESULT_CANCELED, intent);
		}		
		EditArticleIntroActivity.this.finish();
	}
	
}
