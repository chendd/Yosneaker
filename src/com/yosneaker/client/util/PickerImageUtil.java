package com.yosneaker.client.util;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.Fragment;
import android.view.WindowManager;
import android.widget.Toast;

public class PickerImageUtil {

	// ----------相机图片的业务相关
	

	private Fragment fragment;
	private Activity activity;
	private File tempFile;

	public PickerImageUtil(Fragment fragment) {
		super();
		this.fragment = fragment;
		WindowManager wm = (WindowManager) fragment.getActivity()
				.getSystemService("window");
		windowHeight = wm.getDefaultDisplay().getHeight();
		windowWidth = wm.getDefaultDisplay().getWidth();
	}

	int windowHeight;
	int windowWidth;

	public PickerImageUtil(Activity activity) {
		super();
		this.activity = activity;
		WindowManager wm = (WindowManager) activity.getSystemService("window");
		windowHeight = wm.getDefaultDisplay().getHeight();
		windowWidth = wm.getDefaultDisplay().getWidth();
	}

	/**
	 * 打开本地相簿
	 */
	public void OpenGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		if (fragment != null) {
			fragment.startActivityForResult(intent, AppConstants.PHOTO_GALLERY_REQUEST);
		} else {
			activity.startActivityForResult(intent, AppConstants.PHOTO_GALLERY_REQUEST);
		}
	}

	/**
	 * 打开拍照
	 */
	public void OpenCamera() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		if (hasSdcard()) {
			
			File dir = new File(Environment.getExternalStorageDirectory().getPath()+"/image_picker/");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			tempFile = new File(Environment.getExternalStorageDirectory().getPath()+"/image_picker/"+String.valueOf(System.currentTimeMillis()) + ".jpg");
			if (!tempFile.exists()) {
				try {
					tempFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
//			tempFile = new File(Environment.getExternalStorageDirectory(), UUID
//					.randomUUID().toString() + ".png");
			Uri uri = Uri.fromFile(tempFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		}
		if (fragment != null) {
			fragment.startActivityForResult(intent, AppConstants.PHOTO_CAREMA_REQUEST);
		} else {
			activity.startActivityForResult(intent, AppConstants.PHOTO_CAREMA_REQUEST);
		}
	}

	/**
	 * 获取图片本地地址
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public String getBitmapFilePath(int requestCode, int resultCode, Intent data) {
		if (requestCode == AppConstants.PHOTO_GALLERY_REQUEST) {
			if (data != null) {
				Uri uri = data.getData();
				String[] filePathColumn = { MediaColumns.DATA };
				Cursor cursor = null;
				if (fragment != null) {
					cursor = fragment.getActivity().getContentResolver()
							.query(uri, filePathColumn, null, null, null);
				} else {
					cursor = activity.getContentResolver().query(uri,
							filePathColumn, null, null, null);
				}
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				return picturePath;
			}
		} else if (requestCode == AppConstants.PHOTO_CAREMA_REQUEST) {
			if (hasSdcard()) {
				return tempFile.getAbsolutePath();
			} else {
				if (fragment != null) {
					Toast.makeText(fragment.getActivity(), "未找到存储卡，无法存储照片！", 0)
							.show();
				} else {
					Toast.makeText(activity, "未找到存储卡，无法存储照片！", 0).show();
				}
			}
		}
		return null;
	}

	/**
	 * 获取加工后的图片
	 * @param picturePath
	 * @return
	 */
	public Bitmap getBitmapByOpt(String picturePath) {
		Options opt = new Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(picturePath, opt);
		int imgHeight = opt.outHeight;
		int imgWidth = opt.outWidth;
		int scaleX = imgWidth / windowWidth;
		int scaleY = imgHeight / windowHeight;
		int scale = 1;
		if (scaleX > scaleY & scaleY >= 1) {
			scale = scaleX;
		}
		if (scaleY > scaleX & scaleX >= 1) {
			scale = scaleY;
		}
		opt.inJustDecodeBounds = false;
		opt.inSampleSize = scale;
		return BitmapFactory.decodeFile(picturePath, opt);
	}

	/**
	 * 本机是否有sd卡
	 */
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
