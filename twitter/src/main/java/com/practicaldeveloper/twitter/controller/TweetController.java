package com.practicaldeveloper.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practicaldeveloper.twitter.dto.TweetDto;
import com.practicaldeveloper.twitter.model.Tweet;
import com.practicaldeveloper.twitter.service.TweetService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/tweet-app/api/tweets")
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    // Create operation
    @PostMapping("/post-tweet")
    public ResponseEntity<Tweet> createTweet(@RequestBody Tweet tweet) {
    	
    	System.out.print("Before");
        Tweet createdTweet = tweetService.saveTweet(tweet);
        System.out.print("save  Tweet");
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

//    // Read operation
//    @GetMapping("/{tweetId}")
//    public ResponseEntity<TweetDto> getTweetById(@PathVariable int tweetId) {
//        TweetDto tweetDTO = tweetService.getTweetById(tweetId);
//        return ResponseEntity.ok(tweetDTO);
//    }

    // Update operation
    @PutMapping("/{tweetId}")
    public ResponseEntity<TweetDto> updateTweet(@PathVariable int tweetId, @RequestBody TweetDto updatedTweetDTO) {
        TweetDto updatedTweet = tweetService.updateTweet(tweetId, updatedTweetDTO);
        return ResponseEntity.ok(updatedTweet);
    }

    // Delete operation
    @DeleteMapping("/{tweetId}")
    public ResponseEntity<Void> deleteTweet(@PathVariable int tweetId) {
        tweetService.deleteTweet(tweetId);
        return ResponseEntity.noContent().build();
    }

//    // Get all tweets
//    @GetMapping
//    public ResponseEntity<List<TweetDto>> getAllTweets() {
//        List<TweetDto> tweetDTOList = tweetService.getAllTweets();
//        return ResponseEntity.ok(tweetDTOList);
//    }
//    
    @GetMapping("/{email}")
    public ResponseEntity<List<TweetDto>> getAllTweetsByEmail(@PathVariable String email) {
        List<TweetDto> tweetDTOList = tweetService.findByEmail(email);
        return ResponseEntity.ok(tweetDTOList);
    }
    
    @PostMapping("/publish-tweets-to-followers/{userId}")
    public ResponseEntity<String> publishTweetsToFollowers(@PathVariable Integer userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must be provided.");
        }
        tweetService.publishFollowerTweets(userId);
        return ResponseEntity.status(HttpStatus.OK).body("Tweets published to followers successfully.");
    }
    
    
    
}

