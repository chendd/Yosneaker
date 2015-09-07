package com.yosneaker.client.util;

import com.yosneaker.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;

public class DialogUtil {

	/**
	 * 没网络，本地又没缓存数据时显示设置网络提示
	 * @param activity
	 */
	public static void showSetNetworkDialog(final Activity activity) {
		AlertDialog.Builder builder = new Builder(activity);
		builder.setTitle(R.string.dialog_network_error_title);
		builder.setMessage(R.string.dialog_network_error_content);
		builder.setPositiveButton(R.string.dialog_network_error_positive, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				activity.startActivity(intent);
				activity.finish();
			}
		});
		builder.setNegativeButton(R.string.dialog_network_error_negative, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
			}
		});
		builder.create().show();
		
	}
}
