package com.yosneaker.client.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.loopj.android.http.Base64;

public class BitmapUtil {

	/**
	 * bitmap转Base64String
	 * @param context
	 * @param bitmap
	 * @return
	 */
	public static String bitmap2Base64Str(final Context context,Bitmap bitmap) {
		
		try {  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
              
            //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流  
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
            baos.close();  
            byte[] buffer = baos.toByteArray();  
            Log.d(Constants.TAG, "bitmap size:"+buffer.length);   
            //将图片的字节流数据加密成base64字符输出  
            String base64Str = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);
            return base64Str;
		} catch (Exception e) {  
            e.printStackTrace();  
        }
		return "";

	}

	/**
	 * 二进制流转为bitmap
	 * @param bit
	 * @return
	 */
	public Bitmap getBitmapFromByte(byte[] bit){  
	    if(bit != null){  
	        Bitmap bitmap = BitmapFactory.decodeByteArray(bit, 0, bit.length);  
	        return bitmap;  
	    }else{  
	        return null;  
	    }  
	}
	
	/**
	 * 从uri获取bitmap
	 * @param context
	 * @param uri
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getBitmapFromUri(Context context,String uri){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(uri)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 获取缩放后的图片
	 * @param context
	 * @param uri
	 * @param edgeLength 宽
	 * @param scale 高/宽 比例
	 * @return
	 * @throws FileNotFoundException
	 */
	public static Bitmap getScaleBitmap(Bitmap bitmap, int edgeLength,float scale) throws FileNotFoundException {

		if (null == bitmap || edgeLength <= 0 || scale == 0) {
			return null;
		}
		Bitmap result = bitmap;
		//图片原始大小
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();
		//图片需要裁减的大小
		int scaledWidth = edgeLength;
		int scaledHeight = (int) (edgeLength*scale);
		// 裁减位置
		int x;
		int y;
		if (Math.abs(((float)heightOrg/widthOrg)-scale) > 0.05) { // 图片宽高比例不等于缩放比例(浮点数计算误差0.05)
			if (widthOrg/heightOrg<scale) {
				bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth,heightOrg*scaledWidth/widthOrg, true); //先将图片宽按比例缩放到需要的尺寸
				x = 0;
				y = (heightOrg*scaledWidth/widthOrg-scaledHeight)/2;
			}else {
				bitmap = Bitmap.createScaledBitmap(bitmap, widthOrg*scaledHeight/heightOrg,scaledHeight, true); //先将图片宽按比例缩放到需要的尺寸
				x = (widthOrg*scaledHeight/heightOrg-scaledWidth)/2;
				y = 0;
			}				
				bitmap = Bitmap.createBitmap(bitmap, x, y, scaledWidth, scaledHeight); // 从中间截取需要的图片
			}			
			try {
				result = Bitmap.createScaledBitmap(bitmap, scaledWidth,scaledHeight, true);// 等比例缩放到需要尺寸
			} catch (Exception e) {
				return null;
			}
		return result;
	}
	
}
