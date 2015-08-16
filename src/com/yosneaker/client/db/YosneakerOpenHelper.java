
package com.yosneaker.client.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.yosneaker.client.app.YosneakerAppState;
import com.yosneaker.client.model.ArticleList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 保存本地编辑的测评草稿数据库操作类
 * @author chendd
 *
 */
public class YosneakerOpenHelper extends SQLiteOpenHelper {
	 
    private static final String CREATE_ARTICLE_LIST = "create table article_list(" + 
    		"article_id INTEGER PRIMARY KEY, "+ //测评id
    		"article_title varchar(50) , " + //测评标题
    		"article_read_count INTEGER , " + //测评阅读数
    		"article_level INTEGER , "+ //测评评星
    		"article_create_time varchar(50) , "+ //测评日期
    		"article_images varchar(100) , " + //测评缩略图
    		"article_user_id INTEGER"+ //测评用户id
    		");" ;

    private static final String CREATE_ARTICLE_ACCOUNT = "create table article_account(" + 
    		"article_user_id INTEGER PRIMARY KEY, "+ //测评id
    		"account_images varchar(100)" + //头像缩略图
    		");" ;
    
    public YosneakerOpenHelper(Context context,String name,int version) {
        super(context, name, null, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE_LIST);                          
        db.execSQL(CREATE_ARTICLE_ACCOUNT);   
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 产品升级，数据库修改
 
    }
    
}
