package com.practicaldeveloper.twitter.dto;

import java.sql.Timestamp;

import com.practicaldeveloper.twitter.model.User;



public class FollowDto {

	private int followId;

   
    private User follower;


    private User following;
    
    private Timestamp followDate;

    // getters and setters
    
    public Timestamp getFollowDate() {
		return followDate;
	}



	public void setFollowDate(Timestamp followDate) {
		this.followDate = followDate;
	}



	public FollowDto() {
    	
    	
    }
    


	public FollowDto(int followId, User follower, User following) {
		super();
		this.followId = followId;
		this.follower = follower;
		this.following = following;
	}




	public int getFollowId() {
		return followId;
	}


	public void setFollowId(int followId) {
		this.followId = followId;
	}


	public User getFollower() {
		return follower;
	}


	public void setFollower(User follower) {
		this.follower = follower;
	}


	public User getFollowing() {
		return following;
	}


	public void setFollowing(User following) {
		this.following = following;
	}
    
}
