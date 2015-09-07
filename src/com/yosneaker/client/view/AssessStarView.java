package com.yosneaker.client.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yosneaker.client.R;

/**
 * 自定义显示 评价星数
 * @author chendd
 *
 */
public class AssessStarView extends LinearLayout {

	private Context context;
	private AttributeSet attrs;
	
	private ImageView iv1;
	private ImageView iv2;
	private ImageView iv3;
	private ImageView iv4;
	private ImageView iv5;
	
	public AssessStarView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public AssessStarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.attrs = attrs;
		init();
	}

	private void init() {
		LayoutInflater.from(context).inflate(R.layout.assess_star_view, this, true);
		iv1 = (ImageView) findViewById(R.id.ImageView01);
		iv2 = (ImageView) findViewById(R.id.ImageView02);
		iv3 = (ImageView) findViewById(R.id.ImageView03);
		iv4 = (ImageView) findViewById(R.id.ImageView04);
		iv5 = (ImageView) findViewById(R.id.ImageView05);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AssessStarView);
		int num = a.getInt(R.styleable.AssessStarView_starNumber, 5);
		setStarNumber(num);
	}
	
	/**
	 * 设置星星显示数目
	 * @param num 1,2,3,4,5
	 */
	public void setStarNumber(int num) {
		if (num < 1 || num > 5) {
			Log.e("Yosneaker", "setStarNumber error");
		} 
		setDefault();
		switch (num) {
		case 5:
			break;
		case 4:
			iv5.setVisibility(View.GONE);
			break;
		case 3:
			iv4.setVisibility(View.GONE);
			iv5.setVisibility(View.GONE);
			break;
		case 2:
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.GONE);
			iv5.setVisibility(View.GONE);
			break;
		case 1:
			iv2.setVisibility(View.GONE);
			iv3.setVisibility(View.GONE);
			iv4.setVisibility(View.GONE);
			iv5.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 恢复默认设置
	 */
	private void setDefault() {
		iv1.setVisibility(View.VISIBLE);
		iv2.setVisibility(View.VISIBLE);
		iv3.setVisibility(View.VISIBLE);
		iv4.setVisibility(View.VISIBLE);
		iv5.setVisibility(View.VISIBLE);
	}
	
}
