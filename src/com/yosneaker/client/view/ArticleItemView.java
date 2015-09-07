package com.yosneaker.client.view;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yosneaker.client.R;
import com.yosneaker.client.ImageDetailActivity;

/**
 * 自定义测评项视图
 * @author chendd
 *
 */
public class ArticleItemView extends LinearLayout {

	private Context context;
	private LayoutInflater inflater;
	
	private TextView tv_item_order;
	private TextView tv_item_name;
	private TextView tv_item_content;
	private ImageView iv_remove_item;
	private ImageView iv_delete;
//	private List<ImageView> iv_deletes;
//	private AssessStarView asv_item_assess;
	private TextView tv_item_assess;
	private LinearLayout fl_item_image;
	
	private Callbacks callbacks;
	
	public ArticleItemView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public ArticleItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}

	private void init() {		
//		iv_deletes = new ArrayList<ImageView>();
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_article_item_sun, this, true);
		tv_item_order = (TextView) findViewById(R.id.tv_item_order);
		tv_item_name = (TextView) findViewById(R.id.tv_item_name);
		tv_item_content = (TextView) findViewById(R.id.tv_item_content);
		iv_remove_item = (ImageView) findViewById(R.id.iv_remove_item);
		iv_remove_item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
//				CommentItemView.this.removeAllViews();
				if (callbacks != null) {
					callbacks.setItemRemove(Integer.parseInt((String) tv_item_order.getText()));
				}				
			}
		});
		tv_item_assess = (TextView) findViewById(R.id.tv_item_assess);
		fl_item_image = (LinearLayout) findViewById(R.id.fl_item_image);
	}
	
	public void setItemOrder(int item_order){
		tv_item_order.setText(item_order+"");
	}
	
	public void setItemName(String itemsize) {
		tv_item_name.setText(itemsize);
	}
	
	public void setItemContent(String item_content) {
		tv_item_content.setText(item_content);
	}
	
	public void setItemAssess(int assessStarNum) {
		tv_item_assess.setText(assessStarNum+"");
	}
	
	public void setDeleteVisible(int visible) {
		iv_remove_item.setVisibility(visible);
	}
	
//	public void setImageDeleteVisible(int visible) {
//		for (ImageView iv : iv_deletes) {
//			iv.setVisibility(visible);
//		}		
//	}
	
	public void addItemImage(final String imageUri) {
		final View picView = inflater.inflate(
				R.layout.view_edit_article_gv_item_pic, null);
		final ImageButton picIBtn = (ImageButton) picView
				.findViewById(R.id.pic);
//		final Bitmap bmp;
//		try {
//			bmp = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(imageUri)));
//			if (bmp != null) {
//				picIBtn.setImageBitmap(bmp);
				ImageLoader.getInstance().displayImage(imageUri, picIBtn);
				picIBtn.setScaleType(ScaleType.CENTER_CROP);
				picIBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(context, ImageDetailActivity.class);
						ArrayList<String> imageUris = new ArrayList<String>();
						imageUris.add(imageUri);
						intent.putExtra("images", imageUris);//非必须
						intent.putExtra("position", 0);
						int[] location = new int[2];
						picIBtn.getLocationOnScreen(location);
						intent.putExtra("locationX", location[0]);//必须
						intent.putExtra("locationY", location[1]);//必须
						intent.putExtra("width", picIBtn.getWidth());//必须
						intent.putExtra("height", picIBtn.getHeight());//必须
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(0, 0);
					}
				});
				iv_delete = (ImageView) picView.findViewById(R.id.delete);
				iv_delete.setVisibility(View.GONE);
//				iv_deletes.add(iv_delete);
//				iv_delete.setOnClickListener(
//						new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								fl_item_image.removeView(picView);
//								callbacks.setItemImageRemove(Integer.parseInt((String) tv_item_order.getText()),iv_deletes.indexOf(v));
//								iv_deletes.remove(v);
//							}
//						});
				fl_item_image.addView(picView);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
	
	}
	
	public void removeAllItemImage() {
		fl_item_image.removeAllViews();
	}
	
	public void setCallbacks(Callbacks callbacks) {
		this.callbacks = callbacks;
	}
	
	public interface Callbacks {
        public void setItemRemove(int item_order);// 点击删除测评项的回调
//        public void setItemImageRemove(int item_order,int item_image_order);// 点击删除测评项图片的回调
	}
	
	
}
