package com.yosneaker.client.app;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import cn.sharesdk.framework.Platform;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yosneaker.client.db.YosneakerDB;
import com.yosneaker.client.util.Constants;

/**
 * 单例模式，主要在启动的时候用，他初始化了一些对象
 * @author chendd
 */
public class YosneakerAppState {

	private static YosneakerAppState INSTANCE;
	private static Context mContext;
	
	public float mScreenDensity;//屏幕密度
	public int mWidth;//屏幕宽度
	public int mHeigh;//屏幕宽度
	public static YosneakerDB db;
	public final static  Map<String,Platform> PLATFORMS = new ConcurrentHashMap<String,Platform>();
	
	public static int user_id = -1;// 当前用户id

	public static DisplayImageOptions mNormalImageOptions;
	public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
	public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator + "demo" + File.separator + "images" + File.separator;
	
	public static YosneakerAppState getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new YosneakerAppState();
		}
		return INSTANCE;
	}
	
	public static void setApplicationContext(Context context) {
		if (mContext != null) {
			Log.w(Constants.TAG,"setApplicationContext called twice!");
		}
		db = YosneakerDB.getInstance(context);
		
		mContext = context.getApplicationContext();
	}
	
	private YosneakerAppState() {
		if (mContext == null) {
			throw new IllegalStateException("YosneakerAppState inited before app context set");
		}
		mScreenDensity = mContext.getResources().getDisplayMetrics().density;
		mWidth = mContext.getResources().getDisplayMetrics().widthPixels;
		mHeigh = mContext.getResources().getDisplayMetrics().heightPixels;
		
		initImageLoader();
		
	}
	
	/**
	 * 初始化ImageLoader
	 */
	private void initImageLoader(){
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
				.resetViewBeforeLoading(true).build();

		// This
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).defaultDisplayImageOptions(mNormalImageOptions)
				.denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
				// .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(memoryCache)
				// .memoryCacheSize(memoryCacheSize)
				.tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	public Context getContext() {
        return mContext;
    }
	
	public float getmScreenDensity() {
		return mScreenDensity;
	}
	
}
