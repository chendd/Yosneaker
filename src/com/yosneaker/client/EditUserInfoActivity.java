package com.yosneaker.client;

import java.io.FileNotFoundException;
import java.util.Arrays;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yosneaker.client.util.AppConstants;
import com.yosneaker.client.util.PickerImageUtil;
import com.yosneaker.client.view.CustomTitleBar;
import com.yosneaker.client.view.OptionItemView;
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
	private OptionItemView head_sculpture, nick, gender, signature, height, weight, bounce, seat, play;
	private PickerImageUtil mPickerImageUtil;
	private Uri imageUri;
	private SharedPreferences sp;
	private String[] items=null;//WhelView项内容
	private int mPosition;//WhelView当前滑动的位置
	private boolean hasChanged;//用户资料是否被修改
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp=getSharedPreferences("user_info", Context.MODE_PRIVATE);
		mPickerImageUtil = new PickerImageUtil(this);
		initUI();
	}
	
	private void initUI(){
		setContentView(R.layout.activity_edit_user_info);
		titleBar=(CustomTitleBar) findViewById(R.id.titleBar);
		head_sculpture=(OptionItemView) findViewById(R.id.head_sculpture);
		nick=(OptionItemView) findViewById(R.id.nick);
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
		
		titleBar.setOnClickFirstRightIconListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
			}
		});
		
		head_sculpture.setOnClickListener(this);
		nick.setOnClickListener(this);
		gender.setOnClickListener(this);
		signature.setOnClickListener(this);
		height.setOnClickListener(this);
		weight.setOnClickListener(this);
		bounce.setOnClickListener(this);
		seat.setOnClickListener(this);
		play.setOnClickListener(this);
		
		nick.setMiddleTitle(sp.getString("nick", null));
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
			new AlertDialog.Builder(this).setItems(EditUserInfoActivity.this.getResources().getStringArray(R.array.edit_portrait_way), new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 0) {
						mPickerImageUtil.OpenCamera();
					}else {
						mPickerImageUtil.OpenGallery();
					}
				}
			}).show();
			break;
		case R.id.nick:
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
	
	private EditText et_content;
	
	private void showInputDialog(final int id){
		int titleId=0;
		 View view=null;
		switch(id){
		case R.id.nick:
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
				case R.id.nick:
					editor.putString("nick", content);
					nick.setMiddleTitle(content);
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
		switch (requestCode) {
		case AppConstants.USER_NICKNAME_REQUEST:
			if (resultCode == RESULT_OK) {
//				Nickname = data.getStringExtra("nickname_return");
//				tv_nickname.setText(Nickname);
			}
			break;
		case AppConstants.USER_SIGNATURE_REQUEST:
			if (resultCode == RESULT_OK) {
//				Signature = data.getStringExtra("signature_return");
//				tv_signature.setText(Signature);
			}
			break;
		case AppConstants.USER_PLAY_REQUEST:
			if (resultCode == RESULT_OK) {
//				Play = data.getStringExtra("play_return");
//				tv_play.setText(Play);
			}
			break;
		case AppConstants.PHOTO_CAREMA_REQUEST:
		case AppConstants.PHOTO_GALLERY_REQUEST:
			imageUri = data.getData();
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(imageUri, "image/*");
			intent.putExtra("scale", false);
			intent.putExtra("aspectX", 1);//裁剪框比例  
	        intent.putExtra("aspectY", 1);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, AppConstants.PHOTO_CROP_REQUEST);
			break;
		case AppConstants.PHOTO_CROP_REQUEST:

			Bitmap bmp;
			try {
				bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
				if (bmp != null) {
//					iv_portrait.setBackground(new BitmapDrawable(bmp));
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

}
