package com.yosneaker.client.model;

/**
 * 鉴定列表
 * 
 * @author chendd
 *
 */
public class IdentifyList {
	
	/** 鉴定标题 */
	private String title;

	/** 鉴定内容 */
	private String content;
	
	/** 鉴定日期 */
	private String date;
	
	/** 鉴定用户名 */
	private String username;
	
	/** 评论数 */
	private int count;
	
	public IdentifyList(String title, String content, String date, String username,int count) {
		super();
		this.title = title;
		this.content = content;
		this.date = date;
		this.username = username;
		this.count = count;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
}
