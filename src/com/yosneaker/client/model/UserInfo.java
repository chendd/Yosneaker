package com.yosneaker.client.model;

/**
 * 用户基本信息
 * @author chendd
 *
 */
public class UserInfo {
	private String Portrait;
	private String NickName;
	private String Gender;
	private String Signature;
	private int Height;
	private int Weight;
	private int Bounce;
	private String Seat;
	private String Play;
	
	public String getPortrait() {
		return Portrait;
	}
	public void setPortrait(String portrait) {
		Portrait = portrait;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getSignature() {
		return Signature;
	}
	public void setSignature(String signature) {
		Signature = signature;
	}
	public int getHeight() {
		return Height;
	}
	public void setHeight(int height) {
		Height = height;
	}
	public int getWeight() {
		return Weight;
	}
	public void setWeight(int weight) {
		Weight = weight;
	}
	public int getBounce() {
		return Bounce;
	}
	public void setBounce(int bounce) {
		Bounce = bounce;
	}
	public String getSeat() {
		return Seat;
	}
	public void setSeat(String seat) {
		Seat = seat;
	}
	public String getPlay() {
		return Play;
	}
	public void setPlay(String play) {
		Play = play;
	}
	
}
