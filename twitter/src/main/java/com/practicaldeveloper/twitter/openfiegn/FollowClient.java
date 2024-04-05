package com.practicaldeveloper.twitter.openfiegn;

import java.util.List;
import com.practicaldeveloper.twitter.dto.FollowDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.practicaldeveloper.twitter.model.Follow;

@FeignClient(name = "follow-app", url = "http://localhost:8084", path = "/follows/")
public interface FollowClient {
	/*
	 * @PostMapping("/follow") public ResponseEntity<FollowDto> follow(@RequestParam
	 * UserDto follower, @RequestParam UserDto following);
	 */
    @GetMapping("/all")
    public List<FollowDto> getAllFollows();
    
    @GetMapping("/{userId}/{followingId}")
    public boolean isFollowed(@RequestParam int userId,@RequestParam int followingId);
    
    @PostMapping("/{userId}/{followingId}")
    public  Follow saveFollow(@RequestParam int userId,@RequestParam int followingId);
    
    @DeleteMapping("/{userId}/{followingId}")
    public int unFollow(@RequestParam int userId,@RequestParam int followingId);
    
    @GetMapping("/{userId}")//create method in followservice (Note)
    public List<Follow> getFollowers(@PathVariable Integer userId);
    
}
