package admin_user.openfiegn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import admin_user.dto.TweetDto;
import admin_user.model.Tweet;

@FeignClient(name = "tweet-app", url = "http://localhost:8081", path = "/tweet-app/api/tweets/")
public interface TweetClient {
	
	 @GetMapping("/{email}")
	 public ResponseEntity<List<TweetDto>> getAllTweetsByEmail(@PathVariable String email);
	
	 @PostMapping("/post-tweet")
	 public ResponseEntity<TweetDto> createTweet(@RequestBody Tweet tweet);
	 
	 @PostMapping("/publish-tweets-to-followers/{userId}")
	 public ResponseEntity<String> publishTweetsToFollowers(@PathVariable Integer userId);

}
