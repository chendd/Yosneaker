
package com.yosneaker.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 测评草稿
 * @author chendd
 *
 */
public class Article implements Serializable{

	private String articleTitle;// 测评标题
	private String articleImages;// 测评缩略图
	private int articleAuthorId;// 测评作者id
	private int articleTrademarkId;// 测评简介品牌
	private int articleModelId;// 测评简介型号
	private String articleDescription;// 测评简介评价
	private int articleLevel;// 测评总评星数（取值0~5）
	private String articleComment;// 测评总评内容
	private int articleCreateTime;// 测评日期 yyyymmdd	
	private List<ArticleItem> items;// 测评项
	
	private int articleStatus;// 测评状态 0-未保存草稿，1-已保存草稿，2-已发布
	private int articleItemIndex;// 当前编辑哪个测评项(-1表示要新增)

	public Article() {
		super();
		this.articleTitle = "";
		this.articleImages = "";
		this.articleAuthorId = -1;
		this.articleTrademarkId = 0;
		this.articleModelId = 0;
		this.articleDescription = "";
		this.articleLevel = 0;
		this.articleComment = "";
		this.articleCreateTime = 0;
		this.items = new ArrayList<ArticleItem>();
		this.articleStatus = 0;
		this.articleItemIndex = -1;
	}
	
	public String getArticleImages() {
		return articleImages;
	}

	public void setArticleImages(String articleImages) {
		this.articleImages = articleImages;
	}

	public int getArticleAuthorId() {
		return articleAuthorId;
	}

	public void setArticleAuthorId(int articleAuthorId) {
		this.articleAuthorId = articleAuthorId;
	}
	
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public int getArticleTrademarkId() {
		return articleTrademarkId;
	}
	public void setArticleTrademarkId(int articleTrademarkId) {
		this.articleTrademarkId = articleTrademarkId;
	}
	public int getArticleModelId() {
		return articleModelId;
	}
	public void setArticleModelId(int articleModelId) {
		this.articleModelId = articleModelId;
	}
	public String getArticleDescription() {
		return articleDescription;
	}
	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}
	public int getArticleLevel() {
		return articleLevel;
	}
	public void setArticleLevel(int articleLevel) {
		this.articleLevel = articleLevel;
	}
	@Override
	public String toString() {
		return "Article [articleTitle=" + articleTitle + ", articleImages="
				+ articleImages + ", articleAuthorId=" + articleAuthorId
				+ ", articleTrademarkId=" + articleTrademarkId
				+ ", articleModelId=" + articleModelId
				+ ", articleDescription=" + articleDescription
				+ ", articleLevel=" + articleLevel + ", articleComment="
				+ articleComment + ", articleCreateTime=" + articleCreateTime
				+ ", items=" + items + ", articleStatus=" + articleStatus
				+ ", articleItemIndex=" + articleItemIndex + "]";
	}

	public String getArticleComment() {
		return articleComment;
	}
	public void setArticleComment(String articleComment) {
		this.articleComment = articleComment;
	}
	public int getArticleStatus() {
		return articleStatus;
	}
	public void setArticleStatus(int articleStatus) {
		this.articleStatus = articleStatus;
	}
	public int getArticleCreateTime() {
		return articleCreateTime;
	}
	public void setArticleCreateTime(int articleCreateTime) {
		this.articleCreateTime = articleCreateTime;
	}
		
	public List<ArticleItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<ArticleItem> items) {
		this.items = items;
	}

	public int getArticleItemIndex() {
		return articleItemIndex;
	}

	public void setArticleItemIndex(int articleItemIndex) {
		this.articleItemIndex = articleItemIndex;
	}
	
	public void addItem(ArticleItem item) {
		this.items.add(item);
	}
	
	public void removeItem(int i) {
		this.items.remove(i);
	}
	
	public void replaceItem(int i,ArticleItem item) {
		this.items.remove(i);
		this.items.add(i, item);
	}
	
}
