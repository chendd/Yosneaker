package com.yosneaker.client.model;


import java.io.Serializable;
import java.util.List;

public class ArticleDetails implements Serializable{
	private ArticleList article;
	
	private Model model;
	
	private	Brand brand;
	
	private Account authorInfo;
	
	private List<ArticleItem> items;
	
	private List<Comment> hotCommonts;
	
	private IntentionInfo intendInfo;
	
	public ArticleList getArticle() {
		return article;
	}

	public void setArticle(ArticleList article) {
		this.article = article;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}


	public Account getAuthorInfo() {
		return authorInfo;
	}

	public void setAuthorInfo(Account authorInfo) {
		this.authorInfo = authorInfo;
	}

	public List<ArticleItem> getItems() {
		return items;
	}

	public void setItems(List<ArticleItem> items) {
		this.items = items;
	}

	public List<Comment> getHotCommonts() {
		return hotCommonts;
	}

	public void setHotCommonts(List<Comment> hotCommonts) {
		this.hotCommonts = hotCommonts;
	}

	public IntentionInfo getIntendInfo() {
		return intendInfo;
	}

	public void setIntendInfo(IntentionInfo intendInfo) {
		this.intendInfo = intendInfo;
	}

	@Override
	public String toString() {
		return "ArticleDetails [article=" + article + ", model=" + model
				+ ", brand=" + brand + ", authorInfo=" + authorInfo
				+ ", items=" + items + ", hotCommonts=" + hotCommonts
				+ ", intendInfo=" + intendInfo + "]";
	}
	
	
}
