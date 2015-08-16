package com.yosneaker.client.db;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yosneaker.client.model.Account;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.util.Constants;
import com.yosneaker.client.util.DateUtil;

public class YosneakerDB {
	private static final String DB_NAME = "yosneaker.db"; //数据库名称
	private static final int VERSSION = 1;// 数据库版本
	
	private static YosneakerDB yosneakerDB;
	
	private SQLiteDatabase db;
	
	private YosneakerDB(Context context) {
		YosneakerOpenHelper dbHelper = new YosneakerOpenHelper(context, DB_NAME, VERSSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static YosneakerDB getInstance(Context context) {
		if (yosneakerDB == null) {
			yosneakerDB = new YosneakerDB(context);
		}
		return yosneakerDB;
	} 

	/**
	 * 将ArticleList实例存储到数据库中
	 * @param item
	 */
	public void saveArticleList(ArticleList item) {
		if (item != null && !hasArticleListById(item.getArticleId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("article_id", item.getArticleId());
			contentValues.put("article_title", item.getArticleTitle());
			contentValues.put("article_read_count", item.getArticleReadCount());
			contentValues.put("article_level", item.getArticleLevel());
			contentValues.put("article_create_time", DateUtil.getLocalDate(item.getArticleCreateTime()));
			contentValues.put("article_images", item.getArticleImages());
			contentValues.put("article_user_id", item.getAccount().getAccountId());
			if (item.getAccount()!=null && !hasAccountById(item.getAccount().getAccountId())) {
				saveAccount(item.getAccount());
				Log.d(Constants.TAG, "saveAccount");
			}
			db.insert("article_list", null, contentValues);
		}
	}
	
	/**
	 * 分页加载ArticleList数据
	 * @param page
	 * @param rows
	 * @return
	 */
	public ArrayList<ArticleList> loadArticleList(int page,int rows) {
		ArrayList<ArticleList> items = new ArrayList<ArticleList>();
		Cursor cursor = db.rawQuery("select * from article_list order by article_id limit "+ (page-1)*rows + "," + rows, null);
		ArticleList item = null;
		while (cursor.moveToNext()) {
			item = new ArticleList();
			item.setArticleId(cursor.getInt(cursor.getColumnIndex("article_id")));
			item.setArticleTitle(cursor.getString(cursor.getColumnIndex("article_title")));
			item.setArticleReadCount(cursor.getInt(cursor.getColumnIndex("article_read_count")));
			item.setArticleLevel(cursor.getInt(cursor.getColumnIndex("article_level")));
			item.setArticleCreateTime(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("article_create_time"))));
			item.setArticleImages(cursor.getString(cursor.getColumnIndex("article_images")));
			item.setAccount(loadAccountById(cursor.getInt(cursor.getColumnIndex("article_user_id"))));
			items.add(item);
		}
		cursor.close();
		return items;
	}
	
	/**
	 * 指定article_id数据是否已经存在表中
	 * @param article_id
	 * @return
	 */
	private boolean hasArticleListById(int article_id) {
		Cursor cursor = db.rawQuery("select * from article_list where article_id = "+article_id, null);
        boolean hasArticleListById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasArticleListById;
	}

	
	/**
	 * 将Account实例存储到数据库中
	 * @param item
	 */
	public void saveAccount(Account item) {
		if (item != null && !hasAccountById(item.getAccountId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("article_user_id", item.getAccountId());
			contentValues.put("account_images", item.getAccountImages());
			db.insert("article_account", null, contentValues);
			Log.d(Constants.TAG, "saveAccount "+item.getAccountId()+"=="+item.getAccountImages());
		}
	}
	
	
	/**
	 * 通过article_user_id取回帐号信息
	 * @param article_user_id
	 * @return
	 */
	public Account loadAccountById(int article_user_id) {
		Cursor cursor = db.rawQuery("select * from article_account where article_user_id = "+article_user_id, null);
		Account item = new Account();
		while (cursor.moveToNext()) {
			item.setAccountId(article_user_id);
			item.setAccountImages(cursor.getString(cursor.getColumnIndex("account_images")));
		}
		cursor.close();
		return item;
	}
	
	/**
	 * 指定article_user_id数据是否已经存在表中
	 * @param article_user_id
	 * @return
	 */
	private boolean hasAccountById(int article_user_id) {
		Cursor cursor = db.rawQuery("select * from article_account where article_user_id = "+article_user_id, null);
		boolean hasAccountById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasAccountById;
	}
	
	 /**
	  * 利用反射机制给对象赋值
	  * @param obj
	  * @param cursor void
	  */
	  public static void setClassValueBycursor(Object obj, Cursor cursor) {
	    int ColCount = cursor.getColumnCount();
	    int i = 0;
	    for (i = 0; i < ColCount; i++) {
	      String ColName = cursor.getColumnName(i);

	      try {
	        Field f = obj.getClass().getField(ColName);
	        String ret = cursor.getString(i);
	        if (f == null)
	          continue;
	        if (ret == null)
	          ret = "";
	        f.set(obj, ret);
	      } catch (SecurityException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      } catch (NoSuchFieldException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      } catch (IllegalArgumentException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      } catch (IllegalAccessException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	    }
	  }
	
}
