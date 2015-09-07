
package com.yosneaker.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 保存本地编辑的测评草稿数据库操作类
 * @author chendd
 *
 */
public class YosneakerOpenHelper extends SQLiteOpenHelper {
	 
    private static final String CREATE_ARTICLE_LIST = "create table ArticleList(" + 
    		"articleId INTEGER PRIMARY KEY, "+ //测评id
    		"articleTitle varchar(50) , " + //测评标题
    		"articleDescription varchar(1000) , " + 
    		"articleComment varchar(500) , " + 
    		"articleLevel INTEGER , "+ //测评评星
    		"articleImages varchar(100) , " + //测评缩略图
    		"articleAuthorId INTEGER ,"+ //测评用户id
    		"articleCreateTime varchar(50) , "+ //测评日期
    		"articleReadCount INTEGER , " + //测评阅读数
    		"articleCommentCount INTEGER , " + 
    		"articleCommentLikeCount INTEGER , " + 
    		"articleLastModify varchar(50) , "+
			"articleTrademarkId INTEGER , " + 
			"articleModelId INTEGER , " + 
			"articleBrand varchar(100) , " + 
			"articleModel varchar(100)" + 
    		");" ;

    private static final String CREATE_ARTICLE_ACCOUNT = "create table Account(" + 
    		"accountId INTEGER PRIMARY KEY, "+ //测评id
    		"accountName varchar(100)," + 
    		"accountUsername varchar(100)," + 
    		"accountRemark varchar(100)," + 
    		"accountQq varchar(100)," + 
    		"accountTelephone varchar(100)," + 
    		"accountEmail varchar(100)," + 
    		"accountSex INTEGER," + 
    		"accountImages varchar(100)," + //用户头像
    		"accountArea varchar(100)," + 
    		"accountPassword varchar(100)," + 
    		"accountProvince varchar(100)," + 
    		"accountCity varchar(100)," + 
    		"accountCityId INTEGER," + 
    		"accountProvinceId INTEGER," + 
    		"accountCreateTime varchar(50)," + 
    		"accountStature REAL," + 
    		"accountAge INTEGER," + 
    		"accountWeight REAL," + 
    		"accountBounce REAL," + 
    		"accountPosition varchar(100)," + 
    		"accountAbility varchar(100)," + 
    		"accountThridPartId varchar(100)" + 
    		");" ;
    
    private static final String CREATE_ARTICLE_DETAIL = "create table ArticleDetails(" + 
    		"articleId INTEGER PRIMARY KEY, "+ //测评id
    		"modelId INTEGER , " + 
    		"brandId INTEGER , " + 
    		"articleAuthorId INTEGER ,"+ //测评用户id
    		"wantCount INTEGER , " + 
    		"buyCount INTEGER" + 
    		");" ;
   
    private static final String CREATE_ARTICLE_DETAIL_ITEM = "create table ArticleItem(" + 
    		"itemId INTEGER PRIMARY KEY, "+ //测评项id
    		"itemTitle varchar(100) , " + 
    		"itemLevel INTEGER , " + 
    		"itemContent varchar(500) ,"+ 
    		"itemImages varchar(100) , " + 
    		"itemArticleId INTEGER" + //测评id
    		");" ;
   
    private static final String CREATE_ARTICLE_DETAIL_COMMENT = "create table Comment(" + 
    		"articleCommentId INTEGER PRIMARY KEY, "+ //评论id
    		"articleCommentContent varchar(500) , " + 
    		"articleCommentAccountId INTEGER , " + 
    		"articleCommentPublishTime varchar(100) , " + 
    		"articleCommentToUserId INTEGER , " + 
    		"articleCommentTopNumber INTEGER , " + 
    		"articleCommentArticleId INTEGER" + //测评id
    		");" ;
    
    private static final String CREATE_ARTICLE_MODEL = "create table Model(" + 
    		"modelId INTEGER PRIMARY KEY, "+ 
    		"modelName varchar(100) , " + 
    		"modelSeries varchar(100) , " + 
    		"brandId INTEGER ,"+ 
    		"artNo varchar(100) , " + 
    		"modelStory varchar(100)" + 
    		");" ;
    
    private static final String CREATE_ARTICLE_BRAND = "create table Brand(" + 
    		"brandId INTEGER PRIMARY KEY, "+ 
    		"brandName varchar(100) " + 
    		");" ;
    
    private static final String CREATE_ARTICLE_INTENTION_WANT_ACCOUNT = "create table IntentionWantInfo(" + 
    		"articleId INTEGER PRIMARY KEY, "+ //测评id
    		"accountId INTEGER " + //想入用户id
    		");" ;
    
    private static final String CREATE_ARTICLE_INTENTION_BUY_ACCOUNT = "create table IntentionBuyInfo(" + 
    		"articleId INTEGER PRIMARY KEY, "+ //测评id
    		"accountId INTEGER " + //已入用户id
    		");" ;
    
    public YosneakerOpenHelper(Context context,String name,int version) {
        super(context, name, null, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE_LIST);                          
        db.execSQL(CREATE_ARTICLE_ACCOUNT);   
        db.execSQL(CREATE_ARTICLE_DETAIL);                          
        db.execSQL(CREATE_ARTICLE_DETAIL_ITEM); 
        db.execSQL(CREATE_ARTICLE_DETAIL_COMMENT); 
        db.execSQL(CREATE_ARTICLE_MODEL); 
        db.execSQL(CREATE_ARTICLE_BRAND);
        
        db.execSQL(CREATE_ARTICLE_INTENTION_WANT_ACCOUNT); 
        db.execSQL(CREATE_ARTICLE_INTENTION_BUY_ACCOUNT);
        
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 产品升级，数据库修改
 
    }
    
}
