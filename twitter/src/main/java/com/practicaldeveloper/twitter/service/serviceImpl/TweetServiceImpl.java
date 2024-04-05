package com.practicaldeveloper.twitter.service.serviceImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicaldeveloper.twitter.dto.TweetDto;
import com.practicaldeveloper.twitter.dto.UserDto;
import com.practicaldeveloper.twitter.model.Follow;
import com.practicaldeveloper.twitter.model.Tweet;
import com.practicaldeveloper.twitter.model.User;
import com.practicaldeveloper.twitter.openfiegn.FollowClient;
import com.practicaldeveloper.twitter.openfiegn.UserClient;
import com.practicaldeveloper.twitter.repository.TweetRepository;
import com.practicaldeveloper.twitter.service.TweetService;
import com.practicaldeveloper.twitter.exception.NotFoundException;
@Service
public class TweetServiceImpl implements TweetService{
	
	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private UserClient userClient;
	@Autowired
	private FollowClient followClient;
	@Autowired
    private KafkaTemplate<String, Tweet> kafkaTemplate;
	
	private static final String TWEET_TOPIC="follower-tweets";
	
	

	@Override
	public Tweet saveTweet(Tweet tweetDto) {
		System.out.print("Before+ service");
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
    	tweetDto.setTweetDate(now);
		// TODO Auto-generated method stub
		Tweet tweet=new Tweet(tweetDto.getEmail(),tweetDto.getTweetText(),tweetDto.getTweetDate());
		
		
		return tweetRepository.save(tweet);
	}

	@Override
	public List<TweetDto> getAllTweets() {
		
		// TODO Auto-generated method stub
		
		List<TweetDto> tweetDtoList=new ArrayList<TweetDto>(); 
		
		List<Tweet> tweetList=tweetRepository.findAll();
		
		for(Tweet tweets:tweetList) {
			
			Stack<Tweet> s=new Stack();
			s.add(tweets);
			
			while(!s.isEmpty()) {
				Tweet tweet=s.pop();
				System.out.println(tweet);
				TweetDto tweetDto=new TweetDto(
						tweet.getTweetId(),
						tweet.getEmail(),
						tweet.getTweetText(),
						tweet.getTweetDate());
				tweetDtoList.add(tweetDto);
			}
			
			
		}
		return tweetDtoList;
	}

	@Override
	public void deleteTweet(int tweetId) {
		// TODO Auto-generated method stub
		tweetRepository.deleteById(tweetId);
			
	}

	@Override
	public TweetDto updateTweet(int tweetId,TweetDto tweetDto) {
		// TODO Auto-generated method stub
		
		Tweet updateTweet=tweetRepository.findById(tweetId)
				  .orElseThrow(() -> new NotFoundException("Tweet not found"));
		
		
		updateTweet.setTweetText(tweetDto.getTweetText());
		
		tweetRepository.save(updateTweet);
		
		
		
		return convertToTweetDto(updateTweet);
	}
	
	public TweetDto convertToTweetDto(Tweet tweet) {
		TweetDto tweetDto=new TweetDto(tweet.getTweetId(),
				                      tweet.getEmail(),
				                      tweet.getTweetText(),
				                      tweet.getTweetDate());
		
		
		return tweetDto;
	}

	@Override
	public TweetDto getTweetById(int tweetId) {
		// TODO Auto-generated method stub
		
		Tweet getTweet=tweetRepository.findById(tweetId)
				  .orElseThrow(() -> new NotFoundException("Tweet not found"));
		
		
		TweetDto tweetDto=new TweetDto(getTweet.getTweetId(),
				                       getTweet.getEmail(),
				                       getTweet.getTweetText(),
				                       getTweet.getTweetDate()
				                       );
				
		
		return tweetDto;
	}

	@Override
	public List<TweetDto> findByEmail(String email) {
		// TODO Auto-generated method stub
		List<TweetDto> tweetDtoList=new ArrayList<TweetDto>(); 
		
		List<Tweet> tweetList=tweetRepository.findByEmail(email);
				 
		Stack<Tweet> s=new Stack();
		for(Tweet twet:tweetList) {
			

			
			s.add(twet);
			
		}
		while(!s.isEmpty()) {
			Tweet tweet=s.pop();
			System.out.println(tweet.getEmail());
			tweetDtoList.add(convertToTweetDto(tweet));
		}
		
		return tweetDtoList ;
	}
	
	@Override
	public void publishFollowerTweets(int userId) {
	        // Fetch the logged-in user's followers
		 
		
	        List<Follow> followers = followClient.getFollowers(userId);

	        List<Tweet> followerTweets = new ArrayList<>();

	        // Iterate over each follower
	        for (Follow follower : followers) {
	            // Fetch tweets of the follower
	        	String followerUserId=follower.getFollowing().getEmail();
//	        	ResponseEntity<UserDto> userEntity=userClient.getByUserId(userId);
//	        	UserDto user=userEntity.getBody();
//	        	String email=user.getEmail();
	        	
	        	String em=userClient.getUserEmail();
	        	List<Tweet> userTweets=tweetRepository.findByEmail(em);
	        	List<Tweet> tweets= tweetRepository.findByEmail(followerUserId);
	            followerTweets.addAll(tweets);
	            followerTweets.addAll(userTweets);
	        }

	        // Sort tweets by timestamp
	        followerTweets.sort(Comparator.comparing(Tweet::getTweetDate).reversed());

	        // Publish each tweet to Kafka topic
	        for (Tweet tweet : followerTweets){
	        	String tweetJson = serializeTweetToJson(tweet);
	           System.out.print("KAfka sending");
	           			kafkaTemplate.send(TWEET_TOPIC, tweetJson, tweet);
	        }
	        
	    }
	
	private String serializeTweetToJson(Tweet tweet) {
       // Serialize tweet object to JSON string using a JSON library like Jackson
       // Example:
       ObjectMapper objectMapper = new ObjectMapper();
       try {
           return objectMapper.writeValueAsString(tweet);
       } catch (JsonProcessingException e) {
           // Handle serialization error
           e.printStackTrace();
           return null;
       }
   }

}
