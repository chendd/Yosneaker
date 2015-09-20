package com.yosneaker.client;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.NiceSpinner;
import com.gc.materialdesign.views.Slider;
import com.gc.materialdesign.views.Slider.OnValueChangedListener;
import com.yosneaker.client.model.Article;
import com.yosneaker.client.model.ArticleItem;
import com.yosneaker.client.util.BitmapUtil;
import com.yosneaker.client.util.AppConstants;
import com.yosneaker.client.util.PickerImageUtil;

/**
 * 编辑测评项
 * 
 * @author chendd
 *
 */
public class EditArticleItemActivity extends BaseActivity{

	/** 网络，用于动态显示添加删除图片 */
	private GridView gv;
	/** 选取图片的工具 */
	private PickerImageUtil mPickerImageUtil;
	/** 适配器 */
	private AddPicGridViewAdapter adapter;
	/** 数据列表 */
	private List<Bitmap> viewList;
	/** 布局填充器 */
	private LayoutInflater inflater;
	/** 发送更多的布局，用于提供相册，照相机的操作选项。 */
	private LinearLayout sendmoreLyt;
	
	private int itemIndex;
	
	private ButtonRectangle btn_add_goon;
	private ImageButton sendPic;
	private ImageButton sendCamera;
	
	private EditText et_item_intro;
	private TextView tv_item_intro;
	private NiceSpinner et_item_title;
	private Slider rb_item_star;
	private int BigIndex;
	
	private String itemTitleText;
	private String itemIntroText;
	private int itemStar;
	
	private Uri imageUri;
	private ArrayList<String> imageUris;
	private Article commentDraft;
	
	private boolean rb_flag = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);	
		setContentView(R.layout.activity_edit_article_item);
		
		super.onCreate(savedInstanceState);
	}


	@Override
	public void initViews() {
		
		mPickerImageUtil = new PickerImageUtil(this);
		BigIndex = Integer.parseInt(getResources().getString(R.string.comment_edit_intro_maxText));
		
		et_item_intro = (EditText)findViewById(R.id.et_item_intro);
		tv_item_intro = (TextView)findViewById(R.id.tv_item_intro);
		et_item_title = (NiceSpinner)findViewById(R.id.et_item_title);
		rb_item_star = (Slider)findViewById(R.id.rb_item_star);
		btn_add_goon = (ButtonRectangle)findViewById(R.id.btn_add_goon);
		
		// 图库照相机BMP业务
		sendmoreLyt = (LinearLayout) findViewById(R.id.layout_sendmore);
		sendPic = (ImageButton) findViewById(R.id.sendPic);
		sendCamera = (ImageButton) findViewById(R.id.sendCamera);
		
		gv = (GridView) this.findViewById(R.id.gridView);
		commentDraft = new Article();
		
		adapter = new AddPicGridViewAdapter();
		gv.setAdapter(adapter);
		
		setTitleBarText(null);
		showTextViewLeft(true);
		showTextViewRight1(true);
		getTextViewRight1().setBackgroundResource(R.drawable.ic_ok);
		
	}

	@Override
	public void addListnners() {		
		getTextViewLeft().setOnClickListener(this);			
		getTextViewRight1().setOnClickListener(this);	
		sendPic.setOnClickListener(this);
		sendCamera.setOnClickListener(this);
		btn_add_goon.setOnClickListener(this);
		et_item_intro.addTextChangedListener(new EditTextWatcher());
//		rb_item_star.setOnValueChangedListener(new OnValueChangedListener() {
//			
//			@Override
//			public void onValueChanged(int value) {
//				rb_flag = true;
//			}
//		});
	}

	@Override
	public void fillDatas() {
		viewList = new ArrayList<Bitmap>();
		imageUris = new ArrayList<String>();
		viewList.add(null);
//		imageUris.add(null);
		Intent intent = getIntent();
		commentDraft = (Article) intent.getExtras().getSerializable("CommentDraft");
		itemIndex = commentDraft.getArticleItemIndex();
		Log.d(AppConstants.TAG, "itemIndex:"+itemIndex);
		if (itemIndex!=-1) {
			btn_add_goon.setVisibility(View.GONE);
			ArticleItem commentItem = commentDraft.getItems().get(itemIndex);
			et_item_title.setText(commentItem.getItemTitle());
			rb_item_star.setValue(commentItem.getItemLevel());
			et_item_intro.setText(commentItem.getItemContent());
			imageUris = commentItem.getImagesList();
			for (String uri : imageUris) {
				viewList.add(0, BitmapUtil.getBitmapFromUri(EditArticleItemActivity.this, uri));
			}
			adapter.notifyDataSetChanged();
		}else {
			commentDraft = new Article();
		}
		
		List<String> dataset = new LinkedList<String>(Arrays.asList("外观", "做工", "用料", "透气性", "支撑性"));
		et_item_title.attachDataSource(dataset);
		
	}

	public void resetDatas() {
		et_item_title.setText(null);
		rb_item_star.setValue(0);
		et_item_intro.setText(null);
		viewList = new ArrayList<Bitmap>();
		imageUris = new ArrayList<String>();
		viewList.add(null);
		
//		imageUris.add(null);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == getTextViewLeft()) {
			customBackPressed();
		}else if (v == getTextViewRight1()||v == btn_add_goon) {
			itemTitleText = et_item_title.getText().toString();
			itemIntroText = et_item_intro.getText().toString();
			itemStar = (int) (rb_item_star.getValue());
			if (TextUtils.isEmpty(itemTitleText)) {
				et_item_title.setError(getResources().getString(
						R.string.error_comment_item_title_no_null));
			}
			else if (!rb_flag) {
				showToast(getResources().getString(
						R.string.error_comment_item_sumstar_no_null));
			}
			else if (TextUtils.isEmpty(itemIntroText)) {
				et_item_intro.setError(getResources().getString(
						R.string.error_comment_item_intro_no_null));
			}else {
				if (v == getTextViewRight1()) {
					gotoEditComment(itemStar, itemTitleText, itemIntroText,imageUris);
				}else {
					ArticleItem commentItem = new ArticleItem();
					commentItem.setItemLevel(itemStar);
					commentItem.setItemTitle(itemTitleText);
					commentItem.setItemContent(itemIntroText);
					commentItem.setImagesList(imageUris);
					commentDraft.addItem(commentItem);
					resetDatas();//重置数据
				}				
			}
		}else if (v == sendCamera) {
			mPickerImageUtil.OpenCamera();
			sendmoreLyt.setVisibility(View.GONE);
		}else if (v == sendPic) {
			mPickerImageUtil.OpenGallery();
			sendmoreLyt.setVisibility(View.GONE);
		}
	}

	
	private void gotoEditComment(int itemStar, String itemTitleText,
			String itemIntroText,ArrayList<String> images) {
		Intent intent = new Intent();
		ArticleItem commentItem = new ArticleItem();
		itemTitleText = et_item_title.getText().toString();
		itemIntroText = et_item_intro.getText().toString();
		itemStar = (int) (rb_item_star.getValue());
		if (itemStar != 0||!TextUtils.isEmpty(itemTitleText)||!TextUtils.isEmpty(itemIntroText)) {
			if (rb_flag) {
				commentItem.setItemLevel(itemStar);
			}
			if (!TextUtils.isEmpty(itemTitleText)) {
				commentItem.setItemTitle(itemTitleText);
			}
			if (!TextUtils.isEmpty(itemIntroText)) {
				commentItem.setItemContent(itemIntroText);
			}
			commentItem.setImagesList(imageUris);
			if (itemIndex == -1) {
				commentDraft.addItem(commentItem);
			}else {
				commentDraft.replaceItem(itemIndex, commentItem);
			}
			
			intent.putExtra("CommentDraft",commentDraft);
			setResult(RESULT_OK, intent);
		}else {
			setResult(RESULT_CANCELED, intent);
		}
		this.finish();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case AppConstants.PHOTO_CAREMA_REQUEST:
			case AppConstants.PHOTO_GALLERY_REQUEST:
				imageUri = data.getData();
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
//				intent.putExtra("aspectX", 16);//裁剪框比例  
//		        intent.putExtra("aspectY", 9);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, AppConstants.PHOTO_CROP_REQUEST);
				break;
			case AppConstants.PHOTO_CROP_REQUEST:
//				String picPath = mPickerImageUtil.getBitmapFilePath(requestCode,
//						resultCode, data);
//				Log.d(Constants.TAG, "picPath:"+picPath);
//				Bitmap bmp = mPickerImageUtil.getBitmapByOpt(picPath);
				Bitmap bmp;
				try {
					bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
					if (bmp != null) {
						viewList.add(viewList.size()-1, bmp);
						imageUris.add(imageUri.toString());
						adapter.notifyDataSetChanged();
//						HttpClientUtil.uploadResources(bmp, new JsonHttpResponseHandler() {
//
//							@Override
//							public void onFailure(int statusCode,
//									Header[] headers, Throwable throwable,
//									JSONObject errorResponse) {
//								Log.d(Constants.TAG, "post image failure:"+errorResponse.toString());
//								showToast("post image failure:"+errorResponse.toString());
//							}
//
//							@Override
//							public void onSuccess(int statusCode,
//									Header[] headers, JSONObject response) {
//								Log.d(Constants.TAG, "post image success:"+response.toString());
//								showToast("post image success:"+response.toString());
//							}
//							
//						});
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			default:
				break;
			}
		}
		
		
		
		// 测试get json请求
//		AsyncHttpClientUtil.get("upload/resources/json", null, new AsyncHttpResponseHandler() {
//							
//				@Override
//				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//					String responseStr = new String(arg2);
//					Log.d(Constants.TAG, "get json success:"+responseStr);
//					showToast("get json success:"+responseStr);
//				}
//							
//				@Override
//				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//					String responseStr = new String(arg2);
//					Log.d(Constants.TAG, "get json failure:"+responseStr);
//					showToast("get json failure:"+responseStr);
//				}
//			});
		
		// 测试post图片到服务器
//		RequestParams params = new RequestParams();  
//        params.put("image", BitmapUtil.bitmap2Base64Str(getApplicationContext(), bmp));
//		AsyncHttpClientUtil.post("upload/resources/picture.json", params, new AsyncHttpResponseHandler() {
//			
//			@Override
//			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//				String responseStr = new String(arg2);
//				Log.d(Constants.TAG, "post image success:"+responseStr);
//				showToast("post image success:"+responseStr);
//			}
//			
//			@Override
//			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//				String responseStr = new String(arg2);
//				Log.d(Constants.TAG, "post image failure:"+responseStr);
//				showToast("post image failure:"+responseStr);
//			}
//		});
        
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public class AddPicGridViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (viewList == null) {
				return 0;
			}
			return viewList.size();
		}

		@Override
		public Object getItem(int position) {
			return viewList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			inflater = LayoutInflater.from(EditArticleItemActivity.this);
			if (position == viewList.size() - 1) {
				View addView = inflater.inflate(
						R.layout.view_edit_article_gv_item_add, null);
				addView.findViewById(R.id.add).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {								
								if (viewList.size() == 4) {
									showToast(getResources().getString(R.string.toast_picker_image_over_max));
									return;
								} else {
									if(sendmoreLyt.getVisibility() == View.VISIBLE) {
										sendmoreLyt.setVisibility(View.GONE);
									}else if(sendmoreLyt.getVisibility() == View.GONE){
										sendmoreLyt.setVisibility(View.VISIBLE);
									}
								}
							}
						});
				return addView;
			} else {
				View picView = inflater.inflate(
						R.layout.view_edit_article_gv_item_pic, null);
				final ImageButton picIBtn = (ImageButton) picView
						.findViewById(R.id.pic);
				picIBtn.setScaleType(ScaleType.CENTER_CROP);
				picIBtn.setImageBitmap(viewList.get(position));
				picIBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						AlertDialog.Builder builder = new Builder(
//								EditCommentItemActivity.this);
//						builder.setTitle(getResources().getString(R.string.picker_image_see_detail));
//						View view = inflater.inflate(
//								R.layout.view_edit_comment_showmax_dialog,
//								null);
//						((ImageView) view.findViewById(R.id.bigPic))
//								.setImageBitmap(viewList.get(position));
//						builder.setView(view);
//						builder.setNegativeButton(getResources().getString(R.string.picker_image_back), null);
//						builder.show();						
						Intent intent = new Intent(EditArticleItemActivity.this, ImageDetailActivity.class);
						intent.putExtra("images", imageUris);//非必须
						intent.putExtra("position", position);
						int[] location = new int[2];
						picIBtn.getLocationOnScreen(location);
						intent.putExtra("locationX", location[0]);//必须
						intent.putExtra("locationY", location[1]);//必须
						intent.putExtra("width", picIBtn.getWidth());//必须
						intent.putExtra("height", picIBtn.getHeight());//必须
						startActivity(intent);
						overridePendingTransition(0, 0);						
					}
				});
				picView.findViewById(R.id.delete).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								viewList.remove(position);
								imageUris.remove(position);
								adapter.notifyDataSetChanged();
							}
						});
				return picView;
			}
		}
	}
	
	public class EditTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable arg0) {
			 String edit = et_item_intro.getText().toString();
			 
			et_item_intro.setVisibility(View.VISIBLE);
			if (edit.length() <= BigIndex) {
				tv_item_intro.setText(""+(BigIndex - edit.length()));
			} else {
				 et_item_intro.setText(edit.substring(0,BigIndex));
				 et_item_intro.setSelection(edit.substring(0,BigIndex).length());
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
		itemTitleText = et_item_title.getText().toString();
		itemIntroText = et_item_intro.getText().toString();
		itemStar = (int) (rb_item_star.getValue());
		if (!TextUtils.isEmpty(itemTitleText)||!TextUtils.isEmpty(itemIntroText)) {
			Builder builder = new Builder(EditArticleItemActivity.this);
            final String[] items = {getResources().getString(R.string.dialog_comment_save_item),getResources().getString(R.string.dialog_comment_drop_item) };
            builder.setItems(items, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                	if (which == 0) {
                		gotoEditComment(itemStar, itemTitleText, itemIntroText, imageUris);
					} else if(which == 1){
						setResult(RESULT_CANCELED, new Intent());
						EditArticleItemActivity.this.finish();
					}
                }

            }).show();
		}else {
			super.onBackPressed();
		}
	}
	
}
