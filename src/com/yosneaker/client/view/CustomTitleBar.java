package com.yosneaker.client.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yosneaker.client.R;

public class CustomTitleBar extends LinearLayout {

	private Button btn_back;
	private ImageView iv_left_icon, iv_first_right_icon, iv_second_right_icon;
	private TextView tv_center_title;
	
	public CustomTitleBar(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public CustomTitleBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public CustomTitleBar(final Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this);
		btn_back=(Button) findViewById(R.id.btn_back);
		iv_left_icon=(ImageView) findViewById(R.id.iv_left_icon);
		iv_first_right_icon=(ImageView) findViewById(R.id.iv_first_right_icon);
		iv_second_right_icon=(ImageView) findViewById(R.id.iv_second_right_icon);
		tv_center_title=(TextView) findViewById(R.id.tv_middle_title);
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)context).finish();
			}
		});
		
		TypedArray mTypedArray=context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
		
		if(mTypedArray.getBoolean(R.styleable.CustomTitleBar_show_back_view, false)){
			btn_back.setVisibility(View.VISIBLE);
		}
		
		if(mTypedArray.getDrawable(R.styleable.CustomTitleBar_left_icon)!=null){
			iv_left_icon.setImageDrawable(mTypedArray.getDrawable(R.styleable.CustomTitleBar_left_icon));
			iv_left_icon.setVisibility(View.VISIBLE);
		}
		
		if(mTypedArray.getDrawable(R.styleable.CustomTitleBar_first_right_icon)!=null){
			iv_first_right_icon.setImageDrawable(mTypedArray.getDrawable(R.styleable.CustomTitleBar_first_right_icon));
			iv_first_right_icon.setVisibility(View.VISIBLE);
		}
		
		if(mTypedArray.getDrawable(R.styleable.CustomTitleBar_second_right_icon)!=null){
			iv_second_right_icon.setImageDrawable(mTypedArray.getDrawable(R.styleable.CustomTitleBar_second_right_icon));
			iv_second_right_icon.setVisibility(View.VISIBLE);
		}
		
		tv_center_title.setText(mTypedArray.getString(R.styleable.CustomTitleBar_center_title));
		
		mTypedArray.recycle();
	}
	
	public void setOnClickBackListener(View.OnClickListener listener){
		btn_back.setOnClickListener(listener);
	}
	
	public void setOnClickLeftIconListener(View.OnClickListener listener){
		iv_left_icon.setOnClickListener(listener);
	}
	
	public void setOnClickFirstRightIconListener(View.OnClickListener listener){
		iv_first_right_icon.setOnClickListener(listener);
	}
	
	public void setCenterTitle(String title){
		tv_center_title.setText(title);	
	}
	
}
