package com.practicaldeveloper.twitter.dto;

import java.util.List;

public class UserDto {
	private int userId;
	
	private String email;
	
	private String password;
	private String role;
	private String fullname;
	private boolean isFollowed;
	

	private List<TweetDto> tweetDtoList;
	
	private List<FollowDto> followerDtoList;
	
	private  List<FollowDto> followingDtoList;
	
	
	

	public UserDto(int userId, String fullname) {
		super();
		this.userId=userId;
//		this.email = email;
		this.fullname = fullname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<TweetDto> getTweetDtoList() {
		return tweetDtoList;
	}

	public void setTweetDtoList(List<TweetDto> tweetDtoList) {
		this.tweetDtoList = tweetDtoList;
	}
	
	public UserDto() {
		
	}

	public UserDto(String email, String password, String role, String fullname, List<TweetDto> tweetDtoList) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.fullname = fullname;
		this.tweetDtoList = tweetDtoList;
	}
	
	public boolean isFollowed() {
		return isFollowed;
	}

	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
	
	
	

}
