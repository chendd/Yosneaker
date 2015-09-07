package com.yosneaker.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gc.materialdesign.views.ButtonRectangle;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.utils.L;
import com.yosneaker.client.app.YosneakerAppState;
import com.yosneaker.client.model.Article;
import com.yosneaker.client.model.ArticleItem;
import com.yosneaker.client.util.BitmapUtil;
import com.yosneaker.client.util.Constants;
import com.yosneaker.client.util.HttpClientUtil;
import com.yosneaker.client.view.ArticleHeadView;
import com.yosneaker.client.view.ArticleItemView;
import com.yosneaker.client.view.AssessStarView;
import com.yosneaker.client.view.ProgressDialog;

/**
 * 编辑测评
 * 
 * @author chendd
 *
 */
public class EditArticleActivity extends BaseActivity implements ArticleItemView.Callbacks{
	
	private Article commentDraft;
	
	private LinearLayout ll_edit_intro;
	private LinearLayout ll_edit_item;
	private LinearLayout ll_edit_summarize;
	private LinearLayout ll_edit_item_detail;
	
	private ButtonRectangle btn_save_draft;
	private ButtonRectangle btn_publish_draft;
	private ButtonRectangle btn_delete_draft;
	
	private ImageView iv_item_edit;
	
	private ArticleHeadView ahv_edit_article_head;

	// in_edit_intro部分控件
	private TextView tv_add_intro;
	private TextView tv_intro_detail;
	private TextView tv_intro_brand;
	private TextView tv_intro_model;
	private LinearLayout ll_intro_detail;
	
	// in_edit_summarize部分控件
	private TextView tv_add_summarize;
	private TextView tv_summarize_detail;
	private TextView asv_sum_assess;
	private LinearLayout ll_summarize_detail;
	
	// ll_edit_item_detail部分控件
	
	private ArrayList<ArticleItemView> commentItemViews;//
	
	private ArrayList<Bitmap> bgBitmaps;//上面的背景
	private Bitmap defaultBitmap;
	private int itemsize;//测评项数目
	private boolean isEdit;//测评项是否处于编辑状态
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_edit_article);
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		itemsize = 0;
		isEdit = true;
		commentDraft = new Article();
		bgBitmaps = new ArrayList<Bitmap>();
		defaultBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_comment_bg);
		bgBitmaps.add(defaultBitmap);
		
		setTitleBarText(null);
		showTextViewLeft(true);

		commentItemViews = new ArrayList<ArticleItemView>();
		
		ll_edit_intro = (LinearLayout) findViewById(R.id.ll_edit_intro);
		ll_edit_item = (LinearLayout) findViewById(R.id.ll_edit_item);
		ll_edit_summarize = (LinearLayout) findViewById(R.id.ll_edit_summarize);
		ll_edit_item_detail = (LinearLayout)findViewById(R.id.ll_edit_item_detail);
		
		btn_save_draft = (ButtonRectangle) findViewById(R.id.btn_save_draft);
		btn_publish_draft = (ButtonRectangle) findViewById(R.id.btn_publish_draft);
		btn_delete_draft = (ButtonRectangle) findViewById(R.id.btn_delete_draft);
		
		iv_item_edit = (ImageView) findViewById(R.id.iv_item_edit);
		iv_item_edit.setBackgroundResource(R.drawable.item_edit);
		
		ahv_edit_article_head = (ArticleHeadView) findViewById(R.id.ahv_edit_article_head);
		
		tv_add_intro = (TextView) findViewById(R.id.tv_add_intro);
		tv_intro_detail = (TextView) findViewById(R.id.tv_intro_detail);
		tv_intro_brand = (TextView) findViewById(R.id.tv_intro_brand);
		tv_intro_model = (TextView) findViewById(R.id.tv_intro_model);
		ll_intro_detail = (LinearLayout) findViewById(R.id.ll_intro_detail);
		
		tv_add_summarize = (TextView) findViewById(R.id.tv_add_summarize);
		tv_summarize_detail = (TextView) findViewById(R.id.tv_summarize_detail);
		asv_sum_assess = (TextView) findViewById(R.id.asv_sum_assess);
		ll_summarize_detail = (LinearLayout) findViewById(R.id.ll_summarize_detail);
		
		setDefaultBg();
	}

	@Override
	public void addListnners() {
		
		getTextViewLeft().setOnClickListener(this);			
		ll_edit_intro.setOnClickListener(this);
		ll_intro_detail.setOnClickListener(this);
		ll_edit_item.setOnClickListener(this);
		ll_edit_summarize.setOnClickListener(this);
		ll_summarize_detail.setOnClickListener(this);
		btn_save_draft.setOnClickListener(this);
		btn_publish_draft.setOnClickListener(this);
		btn_delete_draft.setOnClickListener(this);
		ahv_edit_article_head.setArticleEditListener(this);
		iv_item_edit.setOnClickListener(this);
	}

	@Override
	public void fillDatas() {
		Intent intent = getIntent();
		Article tmpCommentDraft = (Article) intent.getSerializableExtra("CommentDraft");
		ahv_edit_article_head.setArticleTitle(tmpCommentDraft.getArticleTitle());
		int date = tmpCommentDraft.getArticleCreateTime();
		ahv_edit_article_head.setArticleDate(date/10000+"-"+(date/100)%100+"-"+date%100);
		commentDraft.setArticleTitle(tmpCommentDraft.getArticleTitle());
		commentDraft.setArticleCreateTime(tmpCommentDraft.getArticleCreateTime());
		//Todo  设置iv_comment_bgr,iv_comment_user_icon
		
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			customBackPressed();
		}else if (v == ll_edit_intro||v == ll_intro_detail) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("CommentDraft",commentDraft);
			gotoExistActivityForResult(EditArticleIntroActivity.class, bundle,Constants.COMMENT_INTRO_REQUEST);
		}else if (v == ll_edit_item||commentItemViews.contains(v)) {
			Bundle bundle = new Bundle();
			int index = commentItemViews.indexOf(v);
			commentDraft.setArticleItemIndex(index);
			Log.d(Constants.TAG, "index:"+index);
			bundle.putSerializable("CommentDraft",commentDraft);
			gotoExistActivityForResult(EditArticleItemActivity.class, bundle,Constants.COMMENT_ITEM_REQUEST);
		}else if (v == ll_edit_summarize||v == ll_summarize_detail) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("CommentDraft",commentDraft);
			gotoExistActivityForResult(EditArticleSummarizeActivity.class, bundle,Constants.COMMENT_SUMMARIZE_REQUEST);
		}else if (v == ahv_edit_article_head.getArticleEditView()) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("CommentDraft",commentDraft);
			gotoExistActivityForResult(AddArticleTitleActivity.class, bundle,Constants.COMMENT_TITLE_REQUEST);
		}else if (v == btn_save_draft) {
			saveDraft();
		}else if (v == btn_publish_draft) {
			publishDraft();
		}else if (v == btn_delete_draft) {
			deleteDraft();
			showToast(getResources().getString(R.string.toast_draft_delete_success));
		}else if (v == iv_item_edit) {
			toogleDeleteTip();
		}
	}

	/**
	 * 删除测评草稿
	 */
	private void deleteDraft() {
		
		EditArticleActivity.this.finish();
	}

	/**
	 * 发布测评草稿
	 */
	private void publishDraft() {
		ProgressDialog dialog = ProgressDialog.createDialog(this);
		dialog.setMessage("正在发布请稍等");
		dialog.show();
//		showToast(getResources().getString(R.string.toast_draft_publish_success));
		SubmitApplyTask task = new  SubmitApplyTask(dialog,this);
		task.execute(commentDraft);
		showToast(getResources().getString(R.string.toast_draft_publish_success));
	}

	 private class SubmitApplyTask extends AsyncTask<Article,Void,String> {
		 
		 ProgressDialog dialog;
		 
		 Context context;
		 
		
		public SubmitApplyTask(ProgressDialog dialog, Context context) {
			super();
			this.dialog = dialog;
			this.context = context;
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.cancel();
		}


		@Override
		protected String doInBackground(Article... params) {

			HttpClient httpClient = new DefaultHttpClient();
			for(ArticleItem item : commentDraft.getItems()){
				final List<String> newImages = new ArrayList<String>();
				if(item.getImagesList()!=null&&item.getImagesList().size()>0){
					for(String image:item.getImagesList()){
						Log.d(Constants.TAG, "=======start upload"+image);
						// 测试post图片到服务器,使用
	/*					HttpClientUtil.uploadResources(BitmapUtil.getBitmapFromUri(this, image), new AsyncHttpResponseHandler() {

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								latch.countDown();
							}

							@Override
							public void onSuccess(int arg0, Header[] arg1,
									byte[] arg2) {
								String responseStr = new String(arg2);
								com.alibaba.fastjson.JSONObject response = JSON.parseObject(responseStr);
								Log.d(Constants.TAG, "post image success:"+response.toString());
								Log.d("newImageAddress", response.getString("content"));
								newImages.add(response.getString("content"));
								latch.countDown();
							}
							});*/
						List<BasicNameValuePair> params2 = new LinkedList<BasicNameValuePair>();
						params2.add(new BasicNameValuePair("image", BitmapUtil.bitmap2Base64Str(YosneakerAppState.getInstance().getContext(), BitmapUtil.getBitmapFromUri(context, image))));
						
						try {  
						    HttpPost postMethod = new HttpPost(HttpClientUtil.getAbsoluteUrl("upload/resources/picture.json"));  
						    postMethod.setEntity(new UrlEncodedFormEntity(params2, "utf-8")); //将参数填入POST Entity中  
						                  
						    HttpResponse response = httpClient.execute(postMethod); //执行POST方法  
						    Log.i("", "resCode = " + response.getStatusLine().getStatusCode()); //获取响应码  
						    
						  //判断请求是否成功    
				            if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){  
				                Log.i("SUCCESS", "请求服务器端成功");  
				                JSONObject json = (JSONObject) JSON.parse(EntityUtils.toString(response.getEntity(), "utf-8"));
				                Log.i("SUCCESS", (String) json.get("content"));  
				                newImages.add(Constants.HTTP_IMAGE_BASE_URL+(String) json.get("content"));
				                }else {  
				                    Log.i("error", "请求服务器端失败");  
				                } 
						} catch (UnsupportedEncodingException e) {  
						    // TODO Auto-generated catch block  
						    e.printStackTrace();  
						} catch (ClientProtocolException e) {  
						    // TODO Auto-generated catch block  
						    e.printStackTrace();  
						} catch (IOException e) {  
						    // TODO Auto-generated catch block  
						    e.printStackTrace();  
						}  
						
					}
				}
				item.setImagesList(newImages);
			}
			  SharedPreferences sharedPreferences = getSharedPreferences("account", 0);
			  int accountId = sharedPreferences.getInt("accountId", 0);
			  commentDraft.setArticleAuthorId(accountId);
			  Looper.prepare();
			  HttpClientUtil.publishArticle(commentDraft, new JsonHttpResponseHandler(){

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable throwable, org.json.JSONObject errorResponse) {
					super.onFailure(statusCode, headers, throwable, errorResponse);
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						org.json.JSONObject response) {
					L.i(Constants.TAG, response);
					super.onSuccess(statusCode, headers, response);
					gotoExistActivity(HomeActivity.class, new Bundle());
				}
				  
			  });
			 Log.i("error", commentDraft.toString());  
			return commentDraft.toString();
		}
		 
	 }
	/**
	 * 保存草稿
	 */
	private void saveDraft() {
		showToast(getResources().getString(R.string.toast_draft_save_success));
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
	
	private void customBackPressed() {
		Builder builder = new Builder(EditArticleActivity.this);
        final String[] items = {getResources().getString(R.string.dialog_comment_save_draft),getResources().getString(R.string.dialog_comment_drop_draft) };
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                
            	if (which == 0) {
					saveDraft();
				} else if(which == 1){
					deleteDraft();
				}
            }

        }).show();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Article tmpCommentDraft;
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Constants.COMMENT_TITLE_REQUEST:
				tmpCommentDraft = (Article) data
				.getSerializableExtra("CommentDraft");
				ahv_edit_article_head.setArticleTitle(tmpCommentDraft.getArticleTitle());
				int date = tmpCommentDraft.getArticleCreateTime();
				ahv_edit_article_head.setArticleDate(date/10000+"-"+(date/100)%100+"-"+date%100);
				// 保存数据到内存
				commentDraft.setArticleTitle(tmpCommentDraft.getArticleTitle());
				commentDraft.setArticleCreateTime(tmpCommentDraft.getArticleCreateTime());
				break;
			case Constants.COMMENT_INTRO_REQUEST:
				tmpCommentDraft = (Article) data
						.getSerializableExtra("CommentDraft");
				String brand = String.valueOf(tmpCommentDraft.getArticleTrademarkId());
				int modelText = tmpCommentDraft.getArticleModelId();
				String introText = tmpCommentDraft.getArticleDescription();

				if (TextUtils.isEmpty(brand+"") && TextUtils.isEmpty(modelText+"")
						&& TextUtils.isEmpty(introText+"")) {
					ll_edit_intro.setVisibility(View.VISIBLE);
					ll_intro_detail.setVisibility(View.GONE);
				} else {
					ll_edit_intro.setVisibility(View.GONE);
					ll_intro_detail.setVisibility(View.VISIBLE);
					tv_intro_detail.setText(introText);
					tv_intro_brand.setText(brand);
					tv_intro_model.setText(String.valueOf(modelText));
				}
				// 保存数据到内存
				commentDraft.setArticleTrademarkId(1);
				commentDraft.setArticleModelId(modelText);
				commentDraft.setArticleDescription(introText);
				break;
			case Constants.COMMENT_ITEM_REQUEST:
				tmpCommentDraft = (Article) data
						.getSerializableExtra("CommentDraft");
				List<ArticleItem> commentItems = tmpCommentDraft.getItems();
				Log.d(Constants.TAG, "tmpCommentDraft.getComment_item_index():"+tmpCommentDraft.getArticleItemIndex());
				if (tmpCommentDraft.getArticleItemIndex() == -1) {
					for (ArticleItem commentItem : commentItems) {
						addCommentItem(commentItem);
					}
				}else {
					ArticleItem commentItem = commentItems.get(tmpCommentDraft.getArticleItemIndex());
					replaceCommentItem(commentItem,tmpCommentDraft.getArticleItemIndex());
				}
				
				isEdit = false;
				toogleEditTip();
				toogleDeleteTip();
				setDefaultBg();
				
				break;
			case Constants.COMMENT_SUMMARIZE_REQUEST:
				tmpCommentDraft = (Article) data
				.getSerializableExtra("CommentDraft");
				int sumStar = tmpCommentDraft.getArticleLevel();
				String sumText = tmpCommentDraft.getArticleComment();
				if (TextUtils.isEmpty(sumText) && sumStar==0) {
					ll_summarize_detail.setVisibility(View.GONE);
					ll_edit_summarize.setVisibility(View.VISIBLE);
				} else {
					ll_summarize_detail.setVisibility(View.VISIBLE);
					ll_edit_summarize.setVisibility(View.GONE);
					tv_summarize_detail.setText(sumText);
					asv_sum_assess.setText(sumStar+"");
				}
				// 保存数据到内存
				commentDraft.setArticleLevel(sumStar);
				commentDraft.setArticleComment(sumText);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 添加测评项
	 * @param commentItem
	 */
	public void addCommentItem(ArticleItem commentItem) {
		List<String> imageUris = commentItem.getImagesList();
		int itemStar = commentItem.getItemLevel();
		String itemTitleText = commentItem.getItemTitle();
		String itemContentText = commentItem.getItemContent();
		ArticleItemView cItemView = new ArticleItemView(this);
		cItemView.setCallbacks(EditArticleActivity.this);
		cItemView.setItemOrder(++itemsize);
		cItemView.setItemName(itemTitleText);
		cItemView.setItemContent(itemContentText);
		cItemView.setItemAssess(itemStar);
		cItemView.setDeleteVisible(isEdit?View.GONE:View.VISIBLE);	
		for (int i = 0; i < imageUris.size(); i++) {
			cItemView.addItemImage(imageUris.get(i));
			if (i==0) {
				bgBitmaps.add(BitmapUtil.getBitmapFromUri(EditArticleActivity.this, imageUris.get(i)));
			}					
		}
		if (imageUris.size() == 0) {
			bgBitmaps.add(defaultBitmap);
		}
		Log.d(Constants.TAG, "imageUris.size():"+imageUris.size());
		cItemView.setOnClickListener(this);
		commentItemViews.add(cItemView);
		// 保存数据到内存
		commentDraft.addItem(commentItem);
		ll_edit_item_detail.addView(cItemView);
	}
	
	/**
	 * 替换测评项
	 * @param commentItem
	 * @param comment_item_index
	 */
	private void replaceCommentItem(ArticleItem commentItem, int comment_item_index) {
		List<String> imageUris = commentItem.getImagesList();
		int itemStar = commentItem.getItemLevel();
		String itemTitleText = commentItem.getItemTitle();
		String itemContentText = commentItem.getItemContent();
		ArticleItemView cItemView = commentItemViews.get(comment_item_index);
		cItemView.setCallbacks(EditArticleActivity.this);
		cItemView.setItemOrder(comment_item_index+1);
		
		cItemView.setItemName(itemTitleText);
		cItemView.setItemContent(itemContentText);
		cItemView.setItemAssess(itemStar);
		cItemView.setDeleteVisible(isEdit?View.GONE:View.VISIBLE);	
		cItemView.removeAllItemImage();
		for (int i = 0; i < imageUris.size(); i++) {
			cItemView.addItemImage(imageUris.get(i));
			if (i==0) {
				bgBitmaps.remove(comment_item_index+1);
				bgBitmaps.add(comment_item_index+1,BitmapUtil.getBitmapFromUri(EditArticleActivity.this, imageUris.get(i)));
			}					
		}
		if (imageUris.size() == 0) {
			bgBitmaps.remove(comment_item_index+1);
			bgBitmaps.add(comment_item_index+1,defaultBitmap);
		}
		cItemView.setOnClickListener(this);
		commentItemViews.set(comment_item_index, cItemView);
		// 保存数据到内存
		commentDraft.replaceItem(comment_item_index, commentItem);
		ll_edit_item_detail.removeViewAt(comment_item_index);
		ll_edit_item_detail.addView(cItemView, comment_item_index);
	}
	
	@Override
	public void setItemRemove(final int item_order) {
//		showToast(""+item_order);
		Builder builder = new Builder(EditArticleActivity.this);
        final String[] items = {getResources().getString(R.string.dialog_comment_delete_item),getResources().getString(R.string.dialog_comment_back_item) };
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                
            	if (which == 0) {
            		ll_edit_item_detail.removeViewAt(item_order-1);
            		if (bgBitmaps.size()>item_order) {
            			bgBitmaps.remove(item_order);
					}
            		commentDraft.removeItem(item_order-1);     
            		commentItemViews.remove(item_order-1);
            		itemsize--;
            		resetItemOrder(item_order);
            		toogleEditTip();
            		setDefaultBg();
				} else if(which == 1){
					
				}
            }

        }).show();
		
	}
	

//	@Override
//	public void setItemImageRemove(int item_order,int item_image_order) {
//		Bitmap bitmap = BitmapUtil.getBitmapFromUri(EditCommentActivity.this,commentDraft.getComment_items().get(item_order-1).getImageUris().get(item_image_order));
//		bgBitmaps.remove(item_order);
//		bgBitmaps.add(item_order,bitmap);
//		setDefaultBg();
//		commentDraft.getComment_items().get(item_order-1).getImageUris().remove(item_image_order);
//	}
	
	/**
	 * 设置默认背景
	 */
	public void setDefaultBg() {
		Bitmap bitmap = bgBitmaps.get(0);
		if (bgBitmaps.size() > 1) {//取第一张非系统默认图片
			for (int i = 0; i < bgBitmaps.size(); i++) {
				if (!defaultBitmap.equals(bgBitmaps.get(i))) {
					bitmap = bgBitmaps.get(i);
					break;
				}
			}
		}
		int width = YosneakerAppState.getInstance().mWidth;
		try {
			bitmap = BitmapUtil.getScaleBitmap(bitmap, width,Constants.BG_SCALE);
			if (bitmap != null) {
				ahv_edit_article_head.setArticleBg(bitmap);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当删除测评项时 重置序号
	 * @param item_order
	 */
	public void resetItemOrder(int item_order) {
		int size = ll_edit_item_detail.getChildCount();
		for (int i = 0; i < size; i++) {
			if (i>=item_order-1) {
				ArticleItemView commentItemView = (ArticleItemView) ll_edit_item_detail.getChildAt(i);
				commentItemView.setItemOrder(i+1);
			}
		}
	}
	
	/**
	 * 测评项编辑图标显示开关
	 */
	public void toogleEditTip() {
		if (itemsize == 0) {
			iv_item_edit.setVisibility(View.GONE);
		}else{
			iv_item_edit.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 测评项删除图标显示开关
	 */
	public void toogleDeleteTip() {
		int visible = isEdit?View.VISIBLE:View.GONE;
		iv_item_edit.setBackgroundResource(isEdit?R.drawable.item_ok:R.drawable.item_edit);
		int size = ll_edit_item_detail.getChildCount();
		for (int i = 0; i < size; i++) {
			ArticleItemView commentItemView = (ArticleItemView) ll_edit_item_detail.getChildAt(i);
			commentItemView.setDeleteVisible(visible);
//			commentItemView.setImageDeleteVisible(visible);
		}
		isEdit = !isEdit;
	}
	
}
