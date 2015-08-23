package com.yosneaker.client.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yosneaker.client.model.Account;
import com.yosneaker.client.model.ArticleDetails;
import com.yosneaker.client.model.ArticleItem;
import com.yosneaker.client.model.ArticleList;
import com.yosneaker.client.model.Brand;
import com.yosneaker.client.model.Comment;
import com.yosneaker.client.model.IntentionInfo;
import com.yosneaker.client.model.Model;
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
			contentValues.put("articleId", item.getArticleId());
			contentValues.put("articleTitle", item.getArticleTitle());
			contentValues.put("articleReadCount", item.getArticleReadCount());
			contentValues.put("articleLevel", item.getArticleLevel());
			contentValues.put("articleCreateTime", DateUtil.getLocalDate(item.getArticleCreateTime()));
			contentValues.put("articleImages", item.getArticleImages());
			contentValues.put("articleAuthorId", item.getAccount().getAccountId());
			contentValues.put("articleDescription", item.getArticleDescription());
			contentValues.put("articleComment", item.getArticleComment());
			contentValues.put("articleCommentCount", item.getArticleCommentCount());
			contentValues.put("articleCommentLikeCount", item.getArticleCommentLikeCount());
			contentValues.put("articleLastModify", DateUtil.getLocalDate(item.getArticleLastModify()));
			contentValues.put("articleTrademarkId", item.getArticleTrademarkId());
			contentValues.put("articleModelId", item.getArticleModelId());
			contentValues.put("articleBrand", item.getArticleBrand());
			contentValues.put("articleModel", item.getArticleModel());
			
			if (item.getAccount()!=null && !hasAccountById(item.getAccount().getAccountId())) {
				saveAccount(item.getAccount());
				Log.d(Constants.TAG, "saveAccount");
			}
			db.insert("ArticleList", null, contentValues);
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
		Cursor cursor = db.rawQuery("select * from ArticleList order by articleId limit "+ (page-1)*rows + "," + rows, null);
		ArticleList item = null;
		while (cursor.moveToNext()) {
			item = new ArticleList();
			item.setArticleId(cursor.getInt(cursor.getColumnIndex("articleId")));
			item.setArticleTitle(cursor.getString(cursor.getColumnIndex("articleTitle")));
			item.setArticleReadCount(cursor.getInt(cursor.getColumnIndex("articleReadCount")));
			item.setArticleLevel(cursor.getInt(cursor.getColumnIndex("articleLevel")));
			item.setArticleCreateTime(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("articleCreateTime"))));
			item.setArticleImages(cursor.getString(cursor.getColumnIndex("articleImages")));
			item.setAccount(loadAccountById(cursor.getInt(cursor.getColumnIndex("articleAuthorId"))));
			item.setArticleDescription(cursor.getString(cursor.getColumnIndex("articleDescription")));
			item.setArticleComment(cursor.getString(cursor.getColumnIndex("articleComment")));
			item.setArticleCommentCount(cursor.getInt(cursor.getColumnIndex("articleCommentCount")));
			item.setArticleCommentLikeCount(cursor.getInt(cursor.getColumnIndex("articleCommentLikeCount")));
			item.setArticleLastModify(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("articleLastModify"))));
			item.setArticleTrademarkId(cursor.getInt(cursor.getColumnIndex("articleTrademarkId")));
			item.setArticleModelId(cursor.getInt(cursor.getColumnIndex("articleModelId")));
			item.setArticleBrand(cursor.getString(cursor.getColumnIndex("articleBrand")));
			item.setArticleModel(cursor.getString(cursor.getColumnIndex("articleModel")));
			items.add(item);
		}
		cursor.close();
		return items;
	}
	
	/**
	 * 根据articleId加载ArticleList数据
	 * @param articleId
	 * @return
	 */
	public ArticleList loadArticleList(int articleId) {
		Cursor cursor = db.rawQuery("select * from ArticleList where articleId = "+ articleId, null);
		ArticleList item = null;
		while (cursor.moveToNext()) {
			item = new ArticleList();
			item.setArticleId(cursor.getInt(cursor.getColumnIndex("articleId")));
			item.setArticleTitle(cursor.getString(cursor.getColumnIndex("articleTitle")));
			item.setArticleReadCount(cursor.getInt(cursor.getColumnIndex("articleReadCount")));
			item.setArticleLevel(cursor.getInt(cursor.getColumnIndex("articleLevel")));
			item.setArticleCreateTime(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("articleCreateTime"))));
			item.setArticleImages(cursor.getString(cursor.getColumnIndex("articleImages")));
			item.setAccount(loadAccountById(cursor.getInt(cursor.getColumnIndex("articleAuthorId"))));
			item.setArticleDescription(cursor.getString(cursor.getColumnIndex("articleDescription")));
			item.setArticleComment(cursor.getString(cursor.getColumnIndex("articleComment")));
			item.setArticleCommentCount(cursor.getInt(cursor.getColumnIndex("articleCommentCount")));
			item.setArticleCommentLikeCount(cursor.getInt(cursor.getColumnIndex("articleCommentLikeCount")));
			item.setArticleLastModify(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("articleLastModify"))));
			item.setArticleTrademarkId(cursor.getInt(cursor.getColumnIndex("articleTrademarkId")));
			item.setArticleModelId(cursor.getInt(cursor.getColumnIndex("articleModelId")));
			item.setArticleBrand(cursor.getString(cursor.getColumnIndex("articleBrand")));
			item.setArticleModel(cursor.getString(cursor.getColumnIndex("articleModel")));
		}
		cursor.close();
		return item;
	}
	
	/**
	 * 指定article_id数据是否已经存在表中
	 * @param article_id
	 * @return
	 */
	private boolean hasArticleListById(int article_id) {
		Cursor cursor = db.rawQuery("select * from ArticleList where articleId = "+article_id, null);
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
			contentValues.put("accountId", item.getAccountId());
			contentValues.put("accountImages", item.getAccountImages());
			contentValues.put("accountName", item.getAccountName());
			contentValues.put("accountUsername", item.getAccountUsername());
			contentValues.put("accountRemark", item.getAccountRemark());
			contentValues.put("accountQq", item.getAccountQq());
			contentValues.put("accountTelephone", item.getAccountTelephone());
			contentValues.put("accountEmail", item.getAccountEmail());
			contentValues.put("accountSex", item.getAccountSex());
			contentValues.put("accountArea", item.getAccountArea());
			contentValues.put("accountPassword", item.getAccountPassword());
			contentValues.put("accountProvince", item.getAccountProvince());
			contentValues.put("accountCity", item.getAccountCity());
			contentValues.put("accountCityId", item.getAccountCityId());
			contentValues.put("accountProvinceId", item.getAccountProvinceId());
			contentValues.put("accountCreateTime", DateUtil.getLocalDate(item.getAccountCreateTime()));
			contentValues.put("accountStature", item.getAccountStature());
			contentValues.put("accountAge", item.getAccountAge());
			contentValues.put("accountWeight", item.getAccountWeight());
			contentValues.put("accountBounce", item.getAccountBounce());
			contentValues.put("accountPosition", item.getAccountPosition());
			contentValues.put("accountAbility", item.getAccountAbility());
			contentValues.put("accountThridPartId", item.getAccountThridPartId());
			
			db.insert("Account", null, contentValues);
			Log.d(Constants.TAG, "saveAccount "+item.getAccountId()+"=="+item.getAccountImages());
		}
	}
	
	
	/**
	 * 通过accountId取回帐号信息
	 * @param accountId
	 * @return
	 */
	public Account loadAccountById(int accountId) {
		Cursor cursor = db.rawQuery("select * from Account where accountId = "+accountId, null);
		Account item = new Account();
		while (cursor.moveToNext()) {
			item.setAccountId(accountId);
			item.setAccountImages(cursor.getString(cursor.getColumnIndex("accountImages")));
			item.setAccountName(cursor.getString(cursor.getColumnIndex("accountName")));
			item.setAccountUsername(cursor.getString(cursor.getColumnIndex("accountUsername")));
			item.setAccountRemark(cursor.getString(cursor.getColumnIndex("accountRemark")));
			item.setAccountQq(cursor.getString(cursor.getColumnIndex("accountQq")));
			item.setAccountTelephone(cursor.getString(cursor.getColumnIndex("accountTelephone")));
			item.setAccountEmail(cursor.getString(cursor.getColumnIndex("accountEmail")));
			item.setAccountSex(cursor.getInt(cursor.getColumnIndex("accountSex")));
			item.setAccountArea(cursor.getString(cursor.getColumnIndex("accountArea")));
			item.setAccountPassword(cursor.getString(cursor.getColumnIndex("accountPassword")));
			item.setAccountProvince(cursor.getString(cursor.getColumnIndex("accountProvince")));
			item.setAccountCity(cursor.getString(cursor.getColumnIndex("accountCity")));
			item.setAccountCityId(cursor.getInt(cursor.getColumnIndex("accountCityId")));
			item.setAccountProvinceId(cursor.getInt(cursor.getColumnIndex("accountProvinceId")));
			item.setAccountCreateTime(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("accountCreateTime"))));
			item.setAccountStature(cursor.getFloat(cursor.getColumnIndex("accountStature")));
			item.setAccountAge(cursor.getInt(cursor.getColumnIndex("accountAge")));
			item.setAccountWeight(cursor.getFloat(cursor.getColumnIndex("accountWeight")));
			item.setAccountBounce(cursor.getFloat(cursor.getColumnIndex("accountBounce")));
			item.setAccountPosition(cursor.getString(cursor.getColumnIndex("accountPosition")));
			item.setAccountAbility(cursor.getString(cursor.getColumnIndex("accountAbility")));
			item.setAccountThridPartId(cursor.getString(cursor.getColumnIndex("accountThridPartId")));
		}
		cursor.close();
		return item;
	}
	
	/**
	 * 指定accountId数据是否已经存在表中
	 * @param accountId
	 * @return
	 */
	private boolean hasAccountById(int accountId) {
		Cursor cursor = db.rawQuery("select * from Account where accountId = "+accountId, null);
		boolean hasAccountById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasAccountById;
	}
	
	/**
	 * 将ArticleItem实例存储到数据库中
	 * @param item
	 */
	public void saveArticleItemt(ArticleItem item) {
		if (item != null && !hasItemById(item.getItemId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("itemId", item.getItemId());
			contentValues.put("itemTitle", item.getItemTitle());
			contentValues.put("itemLevel", item.getItemLevel());
			contentValues.put("itemContent", item.getItemContent());
			contentValues.put("itemImages", item.getItemImages());
			contentValues.put("itemArticleId", item.getItemArticleId());
			
			db.insert("ArticleItem", null, contentValues);
		}
	}
	
	/**
	 * 通过article_id获取ArticleItem列表
	 * @param article_id
	 * @return
	 */
	public ArrayList<ArticleItem> loadArticleItemList(int article_id) {
		ArrayList<ArticleItem> items = new ArrayList<ArticleItem>();
		Cursor cursor = db.rawQuery("select * from ArticleItem where itemArticleId = "+article_id, null);
		ArticleItem item = new ArticleItem();
		while (cursor.moveToNext()) {
			item.setItemArticleId(article_id);
			item.setItemId(cursor.getInt(cursor.getColumnIndex("itemId")));
			item.setItemTitle(cursor.getString(cursor.getColumnIndex("itemTitle")));
			item.setItemLevel(cursor.getInt(cursor.getColumnIndex("itemLevel")));
			item.setItemContent(cursor.getString(cursor.getColumnIndex("itemContent")));
			item.setItemImages(cursor.getString(cursor.getColumnIndex("itemImages")));
			items.add(item);
		}
		cursor.close();
		return items;
	}
	
	/**
	 * 指定itemId数据是否已经存在表中
	 * @param accountId
	 * @return
	 */
	private boolean hasItemById(int itemId) {
		Cursor cursor = db.rawQuery("select * from ArticleItem where itemId = "+itemId, null);
		boolean hasItemById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasItemById;
	}
	
	/**
	 * 将Comment实例存储到数据库中
	 * @param item
	 */
	public void saveComment(Comment item) {
		if (item != null && !hasCommentById(item.getArticleCommentId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("articleCommentId", item.getArticleCommentId());
			contentValues.put("articleCommentContent", item.getArticleCommentContent());
			contentValues.put("articleCommentAccountId", item.getArticleCommentAccountId());
			contentValues.put("articleCommentPublishTime", DateUtil.getLocalDate(item.getArticleCommentPublishTime()));
			contentValues.put("articleCommentToUserId", item.getArticleCommentToUserId());
			contentValues.put("articleCommentTopNumber", item.getArticleCommentTopNumber());
			contentValues.put("articleCommentArticleId", item.getArticleCommentArticleId());
			
			db.insert("Comment", null, contentValues);
		}
	}
	
	/**
	 * 通过article_id获取Comment列表
	 * @param article_id
	 * @return
	 */
	public ArrayList<Comment> loadCommentList(int article_id) {
		ArrayList<Comment> items = new ArrayList<Comment>();
		Cursor cursor = db.rawQuery("select * from Comment where articleCommentArticleId = "+article_id, null);
		Comment item = new Comment();
		while (cursor.moveToNext()) {
			item.setArticleCommentArticleId(article_id);
			item.setArticleCommentId(cursor.getInt(cursor.getColumnIndex("articleCommentId")));
			item.setArticleCommentContent(cursor.getString(cursor.getColumnIndex("articleCommentContent")));
			item.setArticleCommentAccountId(cursor.getInt(cursor.getColumnIndex("articleCommentAccountId")));
			item.setArticleCommentPublishTime(DateUtil.getSqliteDate(cursor.getString(cursor.getColumnIndex("articleCommentPublishTime"))));
			item.setArticleCommentToUserId(cursor.getInt(cursor.getColumnIndex("articleCommentToUserId")));
			item.setArticleCommentTopNumber(cursor.getInt(cursor.getColumnIndex("articleCommentTopNumber")));
			items.add(item);
		}
		cursor.close();
		return items;
	}
	
	/**
	 * 指定Comment数据是否已经存在表中
	 * @param articleCommentId
	 * @return
	 */
	private boolean hasCommentById(int articleCommentId) {
		Cursor cursor = db.rawQuery("select * from Comment where articleCommentId = "+articleCommentId, null);
		boolean hasCommentById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasCommentById;
	}
	
	/**
	 * 将Model实例存储到数据库中
	 * @param item
	 */
	public void saveModel(Model item) {
		if (item != null && !hasModelById(item.getModelId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("modelId", item.getModelId());
			contentValues.put("modelName", item.getModelName());
			contentValues.put("modelSeries", item.getModelSeries());
			contentValues.put("brandId", item.getBrandId());
			contentValues.put("artNo", item.getArtNo());
			contentValues.put("modelStory", item.getModelStory());
			
			db.insert("Model", null, contentValues);
		}
	}
	
	
	/**
	 * 通过modelId取回Model
	 * @param modelId
	 * @return
	 */
	public Model loadModelById(int modelId) {
		Cursor cursor = db.rawQuery("select * from Model where modelId = "+modelId, null);
		Model item = new Model();
		while (cursor.moveToNext()) {
			item.setModelId(modelId);
			item.setModelName(cursor.getString(cursor.getColumnIndex("modelName")));
			item.setModelSeries(cursor.getString(cursor.getColumnIndex("modelSeries")));
			item.setBrandId(cursor.getInt(cursor.getColumnIndex("brandId")));
			item.setArtNo(cursor.getString(cursor.getColumnIndex("artNo")));
			item.setModelStory(cursor.getString(cursor.getColumnIndex("modelStory")));
		}
		cursor.close();
		return item;
	}
	
	/**
	 * 指定modelId数据是否已经存在表中
	 * @param modelId
	 * @return
	 */
	private boolean hasModelById(int modelId) {
		Cursor cursor = db.rawQuery("select * from Model where modelId = "+modelId, null);
		boolean hasModelById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasModelById;
	}
	
	/**
	 * 将Model实例存储到数据库中
	 * @param item
	 */
	public void saveBrand(Brand item) {
		if (item != null && !hasBrandById(item.getBrandId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("brandId", item.getBrandId());
			contentValues.put("brandName", item.getBrandName());
			
			db.insert("Brand", null, contentValues);
		}
	}
	
	
	/**
	 * 通过brandId取回Brand
	 * @param brandId
	 * @return
	 */
	public Brand loadBrandById(int brandId) {
		Cursor cursor = db.rawQuery("select * from Brand where brandId = "+brandId, null);
		Brand item = new Brand();
		while (cursor.moveToNext()) {
			item.setBrandId(brandId);
			item.setBrandName(cursor.getString(cursor.getColumnIndex("brandName")));
		}
		cursor.close();
		return item;
	}
	
	/**
	 * 指定Brand数据是否已经存在表中
	 * @param brandId
	 * @return
	 */
	private boolean hasBrandById(int brandId) {
		Cursor cursor = db.rawQuery("select * from Brand where brandId = "+brandId, null);
		boolean hasBrandById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasBrandById;
	}
	
	/**
	 * 将测评想入关系存储到数据库中
	 * @param item
	 */
	public void saveIntentionWantInfo(int articleId,int accountId) {
		Cursor cursor = db.rawQuery("select * from IntentionWantInfo where articleId = "+articleId+" and accountId = "+accountId, null);
		boolean tmp = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
		if (!tmp) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("articleId", articleId);
			contentValues.put("accountId", accountId);
			
			db.insert("IntentionWantInfo", null, contentValues);
		}
	}
	
	/**
	 * 将测评已入关系存储到数据库中
	 * @param item
	 */
	public void saveIntentionBuyInfo(int articleId,int accountId) {
		Cursor cursor = db.rawQuery("select * from IntentionBuyInfo where articleId = "+articleId+" and accountId = "+accountId, null);
		boolean tmp = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
		if (!tmp) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("articleId", articleId);
			contentValues.put("accountId", accountId);
			
			db.insert("IntentionBuyInfo", null, contentValues);
		}
	}
	
	/**
	 * 通过article_id获取想入用户
	 * @param article_id
	 * @return
	 */
	public ArrayList<Account> loadWantAccounts(int article_id) {
		ArrayList<Account> items = new ArrayList<Account>();
		Cursor cursor = db.rawQuery("select * from IntentionWantInfo where articleId = "+article_id, null);
		Account item = new Account();
		while (cursor.moveToNext()) {
			item = loadAccountById(cursor.getInt(cursor.getColumnIndex("accountId")));
			items.add(item);
		}
		cursor.close();
		return items;
	}
	
	/**
	 * 通过article_id获取已入用户
	 * @param article_id
	 * @return
	 */
	public ArrayList<Account> loadBuyAccounts(int article_id) {
		ArrayList<Account> items = new ArrayList<Account>();
		Cursor cursor = db.rawQuery("select * from IntentionBuyInfo where articleId = "+article_id, null);
		Account item = new Account();
		while (cursor.moveToNext()) {
			item = loadAccountById(cursor.getInt(cursor.getColumnIndex("accountId")));
			items.add(item);
		}
		cursor.close();
		return items;
	}
	
	/**
	 * 将ArticleDetails实例存储到数据库中
	 * @param item
	 */
	public void saveArticleDetails(ArticleDetails item) {
		if (item != null && !hasArticleDetailsById(item.getArticle().getArticleId())) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("articleId", item.getArticle().getArticleId());
			saveArticleList(item.getArticle());
			contentValues.put("modelId", item.getModel().getModelId());
			saveModel(item.getModel());
			contentValues.put("brandId", item.getBrand().getBrandId());
			saveBrand(item.getBrand());
			contentValues.put("articleAuthorId", item.getAuthorInfo().getAccountId());
			saveAccount(item.getAuthorInfo());
			IntentionInfo intentionInfo = item.getIntendInfo();
			contentValues.put("wantCount", intentionInfo.getWantCount());
			contentValues.put("buyCount", intentionInfo.getBuyCount());
			
			List<Account> wantAccounts = intentionInfo.getWantAccounts();
			for (Account account : wantAccounts) {
				saveIntentionWantInfo(item.getArticle().getArticleId(),account.getAccountId());
			}
			
			List<Account> buyAccounts = intentionInfo.getBuyAccounts();
			for (Account account : buyAccounts) {
				saveIntentionBuyInfo(item.getArticle().getArticleId(),account.getAccountId());
			}
			
			List<ArticleItem> items = item.getItems();
			for (ArticleItem articleItem : items) {
				saveArticleItemt(articleItem);
			}
			
			List<Comment> hotCommonts = item.getHotCommonts();
			for (Comment comment : hotCommonts) {
				saveComment(comment);
			}
			
			db.insert("ArticleDetails", null, contentValues);
		}
	}
	
	/**
	 * 通过article_id获取ArticleDetails
	 * @param article_id
	 * @return
	 */
	public ArticleDetails loadArticleDetails(int article_id) {
		Cursor cursor = db.rawQuery("select * from ArticleDetails where articleId = "+article_id, null);
		ArticleDetails item = null;
		while (cursor.moveToNext()) {
			item = new ArticleDetails();
			IntentionInfo intendInfo = new IntentionInfo();
			intendInfo.setBuyCount(cursor.getInt(cursor.getColumnIndex("buyCount")));
			intendInfo.setWantCount(cursor.getInt(cursor.getColumnIndex("wantCount")));
			List<Account> wantAccounts = loadWantAccounts(article_id);
			intendInfo.setWantAccounts(wantAccounts);
			List<Account> buyAccounts = loadBuyAccounts(article_id);
			intendInfo.setBuyAccounts(buyAccounts);
			item.setIntendInfo(intendInfo);
			
			ArticleList article = loadArticleList(article_id);
			item.setArticle(article);
			
			Model model = loadModelById(cursor.getInt(cursor.getColumnIndex("modelId")));
			item.setModel(model);
			
			Brand brand = loadBrandById(cursor.getInt(cursor.getColumnIndex("brandId")));
			item.setBrand(brand);
			
			Account account = loadAccountById(cursor.getInt(cursor.getColumnIndex("articleAuthorId")));
			item.setAuthorInfo(account);
			
			List<ArticleItem> items = loadArticleItemList(cursor.getInt(cursor.getColumnIndex("articleId")));
			item.setItems(items);
			
			List<Comment> hotCommonts = loadCommentList(cursor.getInt(cursor.getColumnIndex("articleId")));
			item.setHotCommonts(hotCommonts);
		}
		cursor.close();
		return item;
	}
	
	/**
	 * 指定ArticleDetails数据是否已经存在表中
	 * @param articleCommentId
	 * @return
	 */
	private boolean hasArticleDetailsById(int articleId) {
		Cursor cursor = db.rawQuery("select * from ArticleDetails where articleId = "+articleId, null);
		boolean hasArticleDetailsById = cursor.getCount()!=0;
        cursor.close();// 记得关闭游标
        return hasArticleDetailsById;
	}
	
}
