package com.practicaldeveloper.twitter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practicaldeveloper.twitter.dto.TweetDto;
import com.practicaldeveloper.twitter.model.Tweet;

public interface TweetService {
	
	public Tweet saveTweet(Tweet tweet);
	
	public TweetDto getTweetById(int tweetId);
	
	public List<TweetDto> getAllTweets();
	
	public void deleteTweet(int tweetId);
	
	public TweetDto updateTweet(int tweetId,TweetDto tweetDto);
	 
	public List<TweetDto> findByEmail(String email);
	public void publishFollowerTweets(int userId);
		
		
	
}
