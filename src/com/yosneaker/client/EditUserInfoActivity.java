package com.yosneaker.client;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yosneaker.client.util.AppConstants;
import com.yosneaker.client.util.PickerImageUtil;
import com.yosneaker.client.view.CustomTitleBar;
import com.yosneaker.client.view.OptionItemView;
import com.yosneaker.client.view.RoundImageView;
import com.yosneaker.client.view.WheelView;
import com.yosneaker.client.view.WheelView.OnWheelViewListener;

/**
 * 编辑用户个人信息
 * 
 * @author crazymongo
 * 
 */
public class EditUserInfoActivity extends MyBaseActivity implements View.OnClickListener{

	private final String Tag=EditUserInfoActivity.class.getSimpleName();
	private CustomTitleBar titleBar;
	private RoundImageView head_sculpture;
	private View nick_container;
	private TextView tv_nick;
	private OptionItemView gender, signature, height, weight, bounce, seat, play;
	private PickerImageUtil mPickerImageUtil;
	private Uri imageUri;
	private SharedPreferences sp;
	private String[] items=null;//WhelView项内容
	private int mPosition;//WhelView当前滑动的位置
	private boolean hasChanged;//用户资料是否被修改
	private Uri mPhotoUri;//拍照图片路径
	private Uri mExpUri;//头像路径
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
		initUI();
	}
	
	private void initData(){
		sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);
		File file=new File(AppConstants.HEAD_SCULPTURE_PATH);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mExpUri=Uri.fromFile(file);
	}
	
	private void initUI(){
		setContentView(R.layout.activity_edit_user_info);
		titleBar=(CustomTitleBar) findViewById(R.id.titleBar);
		head_sculpture=(RoundImageView) findViewById(R.id.head_sculpture);
		nick_container=findViewById(R.id.nick_container);
		tv_nick=(TextView) findViewById(R.id.tv_nick);
		gender=(OptionItemView) findViewById(R.id.gender);
		signature=(OptionItemView) findViewById(R.id.signature);
		height=(OptionItemView) findViewById(R.id.height);
		weight=(OptionItemView) findViewById(R.id.weight);
		bounce=(OptionItemView) findViewById(R.id.bounce);
		seat=(OptionItemView) findViewById(R.id.seat);
		play=(OptionItemView) findViewById(R.id.play);
		
		titleBar.setOnClickBackListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		head_sculpture.setOnClickListener(this);
		nick_container.setOnClickListener(this);
		gender.setOnClickListener(this);
		signature.setOnClickListener(this);
		height.setOnClickListener(this);
		weight.setOnClickListener(this);
		bounce.setOnClickListener(this);
		seat.setOnClickListener(this);
		play.setOnClickListener(this);
		
		Bitmap bitmap=BitmapFactory.decodeFile(AppConstants.HEAD_SCULPTURE_PATH);
		if(bitmap!=null){
			head_sculpture.setImageBitmap(bitmap);
		}
		gender.setMiddleTitle(sp.getString("gender", null));
		signature.setMiddleTitle(sp.getString("signature", null));
		height.setMiddleTitle(sp.getString("height", null));
		weight.setMiddleTitle(sp.getString("weight", null));
		bounce.setMiddleTitle(sp.getString("bounce", null));
		seat.setMiddleTitle(sp.getString("seat", null));
		play.setMiddleTitle(sp.getString("play", null));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_sculpture:
			showSetHeadSculpturePopup();
			break;
		case R.id.nick_container:
		case R.id.signature:
		case R.id.play:
			showInputDialog(v.getId());
			break;
		case R.id.gender:
		case R.id.height:
		case R.id.weight:
		case R.id.bounce:
		case R.id.seat:
			showModifyInfoDialog(v.getId());
			break;
		default:
			break;
		}
	}
	
	private void showSetHeadSculpturePopup() {
		
		final PopupWindow mPopupWindow=new PopupWindow(mContext);
		View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_set_head_sculpture, null);
		OnClickListener clickListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
				Intent intent;
				switch(v.getId()){
				case R.id.btn_take_photo:
					intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File file=new File(AppConstants.TAKE_PHOTO_PATH+System.currentTimeMillis()+".jpg");
					file.getParentFile().mkdirs();
					mPhotoUri=Uri.fromFile(file);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
					startActivityForResult(intent, 1);
					break;
				case R.id.btn_album:
					intent=new Intent(Intent.ACTION_PICK);
					intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
					startActivityForResult(intent, 2);
					break;
				}
			}
		};
		
		view.findViewById(R.id.btn_take_photo).setOnClickListener(clickListener);
		view.findViewById(R.id.btn_album).setOnClickListener(clickListener);
		view.findViewById(R.id.btn_cancel).setOnClickListener(clickListener);
		
		mPopupWindow.setContentView(view);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setWindowLayoutMode(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		ColorDrawable background=new ColorDrawable(0Xa0000000);
		mPopupWindow.setBackgroundDrawable(background);
		
		mPopupWindow.showAtLocation(titleBar, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
	}
	
	private EditText et_content;
	
	private void showInputDialog(final int id){
		int titleId=0;
		 View view=null;
		switch(id){
		case R.id.nick_container:
			titleId=R.string.user_nickname;
			view=LayoutInflater.from(mContext).inflate(R.layout.dialog_input_nick, null);
			break;
		case R.id.signature:
			titleId=R.string.user_signature;
			view=LayoutInflater.from(mContext).inflate(R.layout.dialog_input_signature, null);
			break;
		case R.id.play:
			titleId=R.string.user_personal_play;
			view=LayoutInflater.from(mContext).inflate(R.layout.dialog_input_play, null);
			break;
		default:
			return;
		}
		et_content=(EditText) view.findViewById(R.id.et_content);
		AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
		mBuilder.setTitle(titleId);
		mBuilder.setView(view);
		mBuilder.setPositiveButton(getResources().getString(R.string.user_ok),  new DialogInterface.OnClickListener() {
						
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(et_content.getText()==null){
					Toast.makeText(mContext, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				String content=et_content.getText().toString().trim();
				hasChanged=true;
				Editor editor=sp.edit();
				switch(id){
				case R.id.nick_container:
					editor.putString("nick", content);
					tv_nick.setText(content);
					break;
				case R.id.signature:
					editor.putString("signature", content);
					signature.setMiddleTitle(content);
					break;
				case R.id.play:
					editor.putString("play", content);
					play.setMiddleTitle(content);
					break;
				}
				editor.commit();
			}
		});
		mBuilder.setNegativeButton(getResources().getString(R.string.user_cancle), null);
		mBuilder.create().show();
	}
	
	private void showModifyInfoDialog(final int id){
		items=null;
		mPosition=0;
		int titleId=0;
		View view = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) view.findViewById(R.id.wheel_view_wv);
        wv.setOnWheelViewListener(new OnWheelViewListener(){

			@Override
			public void onSelected(int selectedIndex, String item) {
				 Log.d(Tag, "selectedIndex: " + selectedIndex + ",  item: " + item);
				mPosition=selectedIndex-1;
			}
        });
//        wv.setOffset(1);
//        wv.setSeletion(2);
		switch(id){
		case R.id.gender:
			titleId=R.string.user_gender;
			items=getResources().getStringArray(R.array.user_info_genders);
			break;
		case R.id.height:
			titleId=R.string.personal_item_height;
			items = new String[50];
            for (int i = 0; i < items.length; i++) {
            	items[i] = 150+i+"cm";
			}
            break;
		case R.id.weight:
			titleId=R.string.personal_item_weight;
			items = new String[50];
            for (int i = 0; i < items.length; i++) {
            	items[i] = 35+i+"kg";
			}
			break;
		case R.id.bounce:
			titleId=R.string.personal_item_bounce;
			items = new String[50];
            for (int i = 0; i < items.length; i++) {
            	items[i] = 20+i+"cm";
			}
			break;
		case R.id.seat:
			titleId=R.string.user_personal_seat;
			items=getResources().getStringArray(R.array.user_info_seats);
			break;
		default:
			return;
		}
		wv.setItems(Arrays.asList(items));
		AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
		mBuilder.setTitle(titleId);
		mBuilder.setView(view);
		mBuilder.setPositiveButton(getResources().getString(R.string.user_ok),  new DialogInterface.OnClickListener() {
						
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(items==null){
					return;
				}
				hasChanged=true;
				Editor editor=sp.edit();
				switch(id){
				case R.id.gender:
					gender.setMiddleTitle(items[mPosition]);
					editor.putString("gender", items[mPosition]);
					break;
				case R.id.height:
					height.setMiddleTitle(items[mPosition]);
					editor.putString("height", items[mPosition]);
					break;
				case R.id.weight:
					weight.setMiddleTitle(items[mPosition]);
					editor.putString("weight", items[mPosition]);
					break;
				case R.id.bounce:
					bounce.setMiddleTitle(items[mPosition]);
					editor.putString("bounce", items[mPosition]);
					break;
				case R.id.seat:
					seat.setMiddleTitle(items[mPosition]);
					editor.putString("seat", items[mPosition]);
					break;
				}
				editor.commit();
			}
		});
		mBuilder.setNegativeButton(getResources().getString(R.string.user_cancle), null);
		mBuilder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(Tag, "requestCode="+requestCode+"; resultCode="+resultCode);
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 1:
				cropImage(mPhotoUri);
//				new File(mPhotoUri.getPath()).delete();
				break;
			case 2:
				cropImage(data.getData());
				break;
			case 3:
				Uri tmpUri = data.getData();
				String path;
				if (tmpUri == null) {
					path = mExpUri.getPath();
				}else{
					path = tmpUri.getPath();
				}
				Log.i(Tag, path);
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				head_sculpture.setImageBitmap(bitmap);
				break;
			}
		}
	}
	
	private void cropImage(Uri imageUri){
		
		if(imageUri==null){
			return;
		}
		Log.i(Tag, imageUri.toString());
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(imageUri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mExpUri);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, 3);
	}

}
