package com.practicaldeveloper.follows.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.practicaldeveloper.follows.dto.FollowDto;
import com.practicaldeveloper.follows.model.Follow;
import com.practicaldeveloper.follows.model.User;
import com.practicaldeveloper.follows.repository.FollowRepository;

import com.practicaldeveloper.follows.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/follows")
public class FollowController {
	@Autowired
    private FollowService followService;
    
    @Autowired
    private FollowRepository followRepository;

   
    
    @GetMapping("/all")
    public List<FollowDto> getAllFollows() {
        return followService.getAllFollows();
    }

    @PostMapping("/add")
    public Follow addFollow(@RequestBody Follow follow) {
        return followService.addFollow(follow);
    }
    
	/*
	 * @DeleteMapping("/unfollow") public String unfollow(@RequestBody Follow
	 * follow) { followService.unfollowByFollowId(follow.getFollowId()); return
	 * "Unfollowed successfully."; }
	 */
    
    @GetMapping("/{userId}/{followingId}")
    public boolean isFollowed(@RequestParam int userId,@RequestParam int followingId) {
    	return followService.isFollowed(userId, followingId);
    }
    
    @PostMapping("/{userId}/{followingId}")
    public Follow saveFollow(@RequestParam int userId,@RequestParam int followingId) {
    	return followService.follow(userId, followingId);
    }
    @DeleteMapping("/{userId}/{followingId}")
    public int unFollow(@RequestParam int userId,@RequestParam int followingId) {
    	int followId=followService.unfollow(userId, followingId);
    	System.out.println(followId+"controller");
    	if(followId==0) {
    		return followId;
    	}
    	return -1;
    }
    
    
    @GetMapping("/{userId}")//create method in followservice (Note)
    public List<Follow> getFollowers(@PathVariable Integer userId){
    	return followService.getFollowers(userId);
    }


//    // Display the follow page
//    @GetMapping
//    public String showFollowPage(@RequestParam("followerId") int  followerId,
//                                 @RequestParam("followingId") int followingId,
//                                 Model model) {
//        model.addAttribute("followerId", followerId);
//        model.addAttribute("followingId", followingId);
//
//        // Check if the users are already followed
//        boolean followed = isFollowed(followerId, followingId);
//        model.addAttribute("followed", followed);
//
//        return "follow";
//    }
//
//    // Handle follow/unfollow action
//    @PostMapping
//    public String followOrUnfollow(@RequestParam("followerId") int followerId,
//                                   @RequestParam("followingId") int  followingId) {
//        if (isFollowed(followerId, followingId)) {
//            // Unfollow
//            unfollow(followerId, followingId);
//        } else {
//            // Follow
//            follow(followerId, followingId);
//        }
//        return "redirect:/follow?followerId=" + followerId + "&followingId=" + followingId;
//    }

    // Check if the follow relationship exists
//    private boolean isFollowed(int followerId, int followingId) {
//        
//       
//
//        Follow followRelationship = followRepository.findByFollowerAndFollowing(followerId, followingId);
//        return followRelationship != null;
//    }
////    @GetMapping(/followed)
////    private boolean isFollowed(int followerId, int followingId) {
////        
////        
////
////        Follow followRelationship = followRepository.findByFollowerAndFollowing(followerId, followingId);
////        return followRelationship != null;
////    }
//
//    // Create a new follow relationship
//	
//	  private void follow(int followerId, int followingId) {
//	  
//	  
//	  Follow follow= new Follow();
//	  User user1=new User();
//	  user1.setUserId(followerId);
//	  User user2=new User();
//	  user2.setUserId(followingId);
//	  follow.setFollower(user1);
//	  follow.setFollowing(user2);
//	  followRepository.save(follow); } 
//	 
//    // Delete the follow relationship
//    private void unfollow(int followerId, int followingId) {
////        User follower = userRepository.findById(followerId).orElse(null);
////        User following = userRepository.findById(followingId).orElse(null);
//
//        
//            Follow followRelationship = followRepository.findByFollowerAndFollowing(followerId, followingId);
//            if (followRelationship != null) {
//                followRepository.delete(followRelationship);
//            }
//        
//    }
//    @GetMapping("/{followerId}/{followingId}")
//    private Follow getFollowDetail(@RequestParam int followerId, @RequestParam  int followingId) {
//
//        Follow follow= followRepository.findByFollowerAndFollowing(followerId, followingId);
//        return follow;
//    }
//    
//    
//
//    // Add other methods as needed
}
