package com.practicaldeveloper.follows.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.practicaldeveloper.follows.dto.FollowDto;
import com.practicaldeveloper.follows.model.Follow;
import com.practicaldeveloper.follows.model.User;
import com.practicaldeveloper.follows.repository.FollowRepository;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowService {
	@Autowired
    private FollowRepository followRepository;
    

    public List<FollowDto> getAllFollows() {
    	
    	List<Follow> follows= followRepository.findAll();
    	
    	List<FollowDto> followdetails=new ArrayList<>();
    	
    	
    	for(Follow follow:follows) {
    		System.out.println(follow);
    		System.out.println(follow.getFollower().getUserId());
    		FollowDto followdto =new FollowDto();
    		followdto.setFollowId(follow.getFollowId());
    		followdto.setFollower(follow.getFollower());
    		followdto.setFollowing(follow.getFollowing());
    		followdto.setFollowDate(follow.getFollowDate());
    		
    		followdetails.add(followdto);
    		
    }
    	
        return followdetails;
    }

    public Follow addFollow(Follow follow) {
        return followRepository.save(follow);
    }
    
    public int unfollow(int userId,int following) {
    	
    	Integer followId=followRepository.deleteFollowing(userId, following);
    	System.out.println(followId+" followId");
    	if(followId!=null) {
    		followRepository.deleteById(followId);
    		return 0;
    	}
    	return -1;
    	
    }
    
    public int unfollowByFollowId(int followId) {
    	followRepository.deleteById(followId);
        return followId;
    }

    public boolean isFollowed(int userId,int following) {
    	
    	List<Follow> follows=followRepository.findFollowByFollowerId(userId);
    	if(follows==null) {
    		return false;
    	}
    	
    	 Map<Integer, Integer> map = new HashMap<>();
    	 
    	 for(Follow follow:follows) {
    		 map.put(follow.getFollowing().getUserId(), follow.getFollower().getUserId());
    		 System.out.println(following==follow.getFollowing().getUserId());
    	 }
    	 
    	
    	if(map.containsKey(following)) {
    		return true;
    	}
    	return false;
    }
   
    
    public Follow follow(int userId,int followingId) {
    	Follow follow=new Follow();
    	User user1=new User();
    	user1.setUserId(userId);
    	User user2=new User();
    	user2.setUserId(followingId);
    	follow.setFollower(user1);
    	follow.setFollowing(user2);
    	
    	return followRepository.save(follow);
    }
    // Add other methods as needed
    
    public List<Follow> getFollowers(Integer userId){
    	List<Follow> follows=followRepository.findFollowByFollowerId(userId);
    	
    	if(follows==null) {
    		return follows;
    	}
    	return follows;
    }
}

