package com.yosneaker.client.view;

import com.yosneaker.client.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OptionItemView extends LinearLayout {

	private RelativeLayout container;
	private ImageView iv_icon;
	private TextView tv_left_title;
	private TextView tv_middle_title;
	
	public OptionItemView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}
	
	public OptionItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public OptionItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater.from(context).inflate(R.layout.option_item_view, this);
		container=(RelativeLayout) findViewById(R.id.container);
		iv_icon=(ImageView) findViewById(R.id.iv_icon);
		tv_left_title=(TextView) findViewById(R.id.tv_left_title);
		tv_middle_title=(TextView) findViewById(R.id.tv_middle_title);
		
		TypedArray mTypedArray=context.obtainStyledAttributes(attrs, R.styleable.OptionItemView);
		if(mTypedArray.getDrawable(R.styleable.OptionItemView_icon)!=null){
			iv_icon.setVisibility(View.VISIBLE);
			iv_icon.setImageDrawable(mTypedArray.getDrawable(R.styleable.OptionItemView_icon));
		}
		
		tv_left_title.setText(mTypedArray.getString(R.styleable.OptionItemView_left_title));
		tv_middle_title.setText(mTypedArray.getString(R.styleable.OptionItemView_middle_title));
		mTypedArray.recycle();
	}

	public void setMiddleTitle(String title){
		tv_middle_title.setText(title);
	}
	

}
