package com.yosneaker.client.model;

public class ThridPlatFormInfo {
	private String id;
	private String token;
	private long expiresTime;
	private String platformNname;
	private String tokenSecret;
	private long expiresIn;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getExpiresTime() {
		return expiresTime;
	}
	public void setExpiresTime(long expiresTime) {
		this.expiresTime = expiresTime;
	}
	public String getPlatformNname() {
		return platformNname;
	}
	public void setPlatformNname(String platformNname) {
		this.platformNname = platformNname;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	public long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
