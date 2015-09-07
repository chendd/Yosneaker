package com.yosneaker.client.fragment;

import com.yosneaker.client.R;
import com.yosneaker.client.view.PagerSlidingTabStrip;

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

/**
 * 
 * 消息Fragment
 * 
 * @author chendd
 *
 */
public class MessageFragment extends BaseFragment {

	private MessageCommentFragment discussMessageFragment;
	private MessageWishFragment wishMessageFragment;
	private MessageFansFragment fansMessageFragment;
	private MessageNoticeFragment informMessageFragment;
	
	private  PagerSlidingTabStrip mTabStrip1;
	
	private DisplayMetrics dm; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message, container,
				false);
		
		ViewPager pager = (ViewPager) view.findViewById(R.id.pager1);
        mTabStrip1 = (PagerSlidingTabStrip) view.findViewById(R.id.tabs1);
        dm = getResources().getDisplayMetrics(); 
        pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));  
        mTabStrip1.setViewPager(pager);  
        setTabsValue();  
		
		return view;
	}

	/** 
     * 对PagerSlidingTabStrip的各项属性进行赋值。 
     */  
    private void setTabsValue() {  
        // 设置Tab是自动填充满屏幕的  
        mTabStrip1.setShouldExpand(true);  
        // 设置Tab的分割线是透明的  
        mTabStrip1.setDividerColor(Color.TRANSPARENT);  
        // 设置Tab底部线的高度  
        mTabStrip1.setUnderlineHeight((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));  
        // 设置Tab Indicator的高度  
        mTabStrip1.setIndicatorHeight((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));  
        // 设置Tab标题文字的大小  
        mTabStrip1.setTextSize((int) TypedValue.applyDimension(  
                TypedValue.COMPLEX_UNIT_SP, 16, dm));  
        
        // 设置Tab Indicator的颜色  
        mTabStrip1.setIndicatorColor(Color.parseColor("#2CD6C0"));  
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)  
        mTabStrip1.setSelectedTextColor(Color.parseColor("#2CD6C0"));  
        // 取消点击Tab时的背景色  
        mTabStrip1.setTabBackground(0);  
    }  
  
    public class MyPagerAdapter extends FragmentPagerAdapter {  
  
        public MyPagerAdapter(FragmentManager fm) {  
            super(fm);  
        }  
  
        private final String[] titles = { "评论", "心愿", "粉丝", "通知" };  
  
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
                if (discussMessageFragment == null) {  
                	discussMessageFragment = new MessageCommentFragment();  
                }  
                return discussMessageFragment;  
            case 1:  
                if (wishMessageFragment == null) {  
                	wishMessageFragment = new MessageWishFragment();  
                }  
                return wishMessageFragment;  
            case 2:  
                if (fansMessageFragment == null) {  
                	fansMessageFragment = new MessageFansFragment();  
                }  
                return fansMessageFragment;  
            case 3:  
                if (informMessageFragment == null) {  
                	informMessageFragment = new MessageNoticeFragment();  
                }  
                return informMessageFragment;  
            default:  
                return null;  
            }  
        }  
  
    }

	
}
