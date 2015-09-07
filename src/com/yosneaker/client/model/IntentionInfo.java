package com.yosneaker.client.model;


import java.util.List;

public class IntentionInfo {
	 
	private int articleId;

	private int wantCount;
	 
	 private int buyCount;
	 
	 private List<Account> wantAccounts;
	 
	 private List<Account> buyAccounts;

	public int getWantCount() {
		return wantCount;
	}

	public void setWantCount(int wantCount) {
		this.wantCount = wantCount;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public List<Account> getWantAccounts() {
		return wantAccounts;
	}

	public void setWantAccounts(List<Account> wantAccounts) {
		this.wantAccounts = wantAccounts;
	}

	public List<Account> getBuyAccounts() {
		return buyAccounts;
	}

	public void setBuyAccounts(List<Account> buyAccounts) {
		this.buyAccounts = buyAccounts;
	}
		
	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	
}
