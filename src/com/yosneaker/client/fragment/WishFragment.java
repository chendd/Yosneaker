package com.yosneaker.client.fragment;



import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yosneaker.client.R;
import com.yosneaker.client.view.PagerSlidingTabStrip;

/**
 * 
 * 心愿单Fragment
 * 
 * @author chendd
 *
 */
public class WishFragment extends BaseFragment {
	
	/** 
     * 想入界面的Fragment 
     */  
    private WishWantFragment squareFragment;  
  
    /** 
     * 已入界面的Fragment 
     */  
    private WishYetFragment friendFragment;  
	
	private  PagerSlidingTabStrip mTabStrip;
	
	private DisplayMetrics dm; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wish,
				container, false);

        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        mTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        dm = getResources().getDisplayMetrics(); 
        pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));  
        mTabStrip.setViewPager(pager);  
        setTabsValue();  
      
		return view;
	}
	
	/** 
     * 对PagerSlidingTabStrip的各项属性进行赋值。 
     */  
    private void setTabsValue() {  
        // 设置Tab是自动填充满屏幕的  
        mTabStrip.setShouldExpand(true);  
        // 设置Tab的分割线是透明的  
        mTabStrip.setDividerColor(Color.TRANSPARENT);  
        // 设置Tab底部线的高度  
        mTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));  
        // 设置Tab Indicator的高度  
        mTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));  
        // 设置Tab标题文字的大小  
        mTabStrip.setTextSize((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_SP, 15, dm));  
        
        // 设置Tab Indicator的颜色  
        mTabStrip.setIndicatorColor(Color.parseColor("#2CD6C0"));  
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)  
        mTabStrip.setSelectedTextColor(Color.parseColor("#2CD6C0"));  
        // 取消点击Tab时的背景色  
        mTabStrip.setTabBackground(0);  
    }  
  
    public class MyPagerAdapter extends FragmentPagerAdapter {  
  
        public MyPagerAdapter(FragmentManager fm) {  
            super(fm);  
        }  
  
        private final String[] titles = { "想入", "已入" };  
  
        @Override  
        public CharSequence getPageTitle(int position) {  
            return titles[position];  
        }  
  
        @Override  
        public int getCount() {  
            return titles.length;  
        }  
  
        @Override  
        public Fragment getItem(int position) {  
            switch (position) {  
            case 0:  
                if (squareFragment == null) {  
                	squareFragment = new WishWantFragment();  
                }  
                return squareFragment;  
            case 1:  
                if (friendFragment == null) {  
                	friendFragment = new WishYetFragment();  
                }  
                return friendFragment;  
            default:  
                return null;  
            }  
        }  
  
    }

}
