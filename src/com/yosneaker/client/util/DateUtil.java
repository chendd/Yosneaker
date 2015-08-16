package com.yosneaker.client.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 根据时间区间转化相应时间显示
	 * @param d1
	 * @return
	 */
    public static String getIntervalDate(Date d1) {
    	String interval = null;  
          
        //用现在距离1970年的时间间隔new Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔  
        long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒  
          
        if(time/1000 < 10 && time/1000 >= 0) {  
        //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒  
            interval ="刚刚";  
              
        } else if(time/1000 < 60 && time/1000 > 0) {  
        //如果时间间隔小于60秒则显示多少秒前  
            int se = (int) ((time%60000)/1000);  
            interval = se + "秒前";  
              
        } else if(time/60000 < 60 && time/60000 > 0) {  
        //如果时间间隔小于60分钟则显示多少分钟前  
            int m = (int) ((time%3600000)/60000);//得出的时间间隔的单位是分钟  
            interval = m + "分钟前";  
              
        } else if(time/3600000 < 24 && time/3600000 >= 0) {  
        //如果时间间隔小于24小时则显示多少小时前  
            int h = (int) (time/3600000);//得出的时间间隔的单位是小时  
            interval = h + "小时前";  
              
        }else if(time/86400000 < 365 && time/86400000 >= 0) { // 闰年暂时不考虑
        	//如果是今年显示 月-日
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");  
  
            interval = sdf.format(d1); 
              
        }else {  
            //去年以前显示 年-月-日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  
            interval = sdf.format(d1);  
        }  
        return interval;  
	}
	
    /**
     * 显示 yyyy-MM-dd HH:mm:ss 格式的时间
     * @param d1
     * @return
     */
    public static String getLocalDate(Date d1) {
    	//去年以前显示 年-月-日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

        return sdf.format(d1);
    }
    
    /**
     * 显示 yyyyMMddHHmmss 格式的时间转为date型
     * @param d1
     * @return
     */
    public static Date getSqliteDate(String d1) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	try {
			return sdf.parse(d1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return new Date();
    }
}
