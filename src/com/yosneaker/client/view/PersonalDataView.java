package com.yosneaker.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yosneaker.client.R;

/**
 * 自定义测评顶部视图
 * @author chendd
 *
 */
public class PersonalDataView extends LinearLayout {

	private Context context;
	private LayoutInflater inflater;
	
	private TextView tv_personal_height;
	private TextView tv_personal_weight;
	private TextView tv_personal_bounce;
	
	public PersonalDataView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public PersonalDataView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
		
	}

	/**
	 * 初始化
	 */
	private void init() {		
		inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.view_personal_data, this, true);
		tv_personal_height = (TextView) findViewById(R.id.tv_personal_height);
		tv_personal_weight = (TextView) findViewById(R.id.tv_personal_weight);
		tv_personal_bounce = (TextView) findViewById(R.id.tv_personal_bounce);
	}

	public void setPersonalHeight(String personalHeight) {
		tv_personal_height.setText(personalHeight);
	}
	
	public void setPersonalWeight(String personalWeight) {
		tv_personal_weight.setText(personalWeight);
	}
	
	public void setPersonalBounce(String personalBounce) {
		tv_personal_bounce.setText(personalBounce);
	}
}
