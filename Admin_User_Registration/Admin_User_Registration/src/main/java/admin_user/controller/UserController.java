package admin_user.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import admin_user.config.ContextPathUtils;

import admin_user.dto.FollowDto;
import admin_user.dto.FollowedUser;
import admin_user.dto.TweetDto;
import admin_user.dto.UserDto;
import admin_user.model.Follow;
import admin_user.model.Tweet;
import admin_user.model.User;
import admin_user.openfiegn.FollowClient;
import admin_user.openfiegn.TweetClient;
import admin_user.service.UserService;

@Controller("/user-app/api")
public class UserController{
	
	@Autowired
	private ContextPathUtils contextPathUtils;

//	
//	@Value("${server.servlet.context-path}")
//    private String contextPath;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private FollowClient followClient;
	
	@Autowired
	private TweetClient tweetClient;
	
	
	
	
	
	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}
	
	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
		userService.save(userDto);
		model.addAttribute("message", "Registered Successfuly!");
		return "register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user-page")
	public String userPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
//		String contextPath = contextPathUtils.getContextPath();
		return "user";
	}
	
	@GetMapping("/admin-page")
	public String adminPage (Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		  
		return "admin";
	}
	
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto>  getByUserId(@PathVariable Integer userId) {
      UserDto userDto=userService.getUserId(userId);
       return ResponseEntity.ok(userDto);
       
   }
    

    @GetMapping("/altweets")
    public String getAltweets(Model model) {
        // Get the currently authenticated user's authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the email from the authentication object
        String email = authentication.getName();
        // Assuming email is stored as the principal username
        System.out.println(email);

        // Retrieve tweets associated with the user's email
        UserDto altweets = userService.getTweetById(email);

        // Add tweets to the model
        model.addAttribute("altweets", altweets);
        
      

        // Return the name of the Thymeleaf template to render
        return "tweet";
    }
    @GetMapping("/users")
    public String getAllUser(Model model){
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         // Extract the email from the authentication object
         String email = authentication.getName();
       
        

         // Add tweets to the model
    	
    	List<UserDto> users= userService.getAllUser();
    	
    	model.addAttribute("users",users);
    	
    	return "users";
    }
    
    
    @GetMapping("/users/{userId}")
    public String followUser(@PathVariable int followingId,Model model){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the email from the authentication object
        String email = authentication.getName();
        
        int userId=userService.getuserIdByEmail(email);
        
        boolean  followed=followClient.isFollowed(userId, followingId);
        model.addAttribute("isFollowed",followed);
      
    	List<UserDto> users= userService.getAllUser();
    	
    	model.addAttribute("users",users);
    	
    	return "friends";
    }
    @GetMapping("/followpage")
    public String follwPage(Model model){
    	List<FollowDto> usersDto=followClient.getAllFollows();
    	
    	model.addAttribute("usersDto", usersDto);
    	return "follow";
    }
    
    @PostMapping("/follow/{userId}")
    public String followFriends(@PathVariable("userId") int userId,Model model){
		/*
		 * List<FollowDto> usersDto=followClient.getAllFollows();
		 * 
		 * model.addAttribute("usersDto", usersDto);
		 * 
		 */
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the email from the authentication object
        String email = authentication.getName();
      
        int loginUserId=userService.getuserIdByEmail(email);
        
        Follow follow=followClient.saveFollow(loginUserId, userId);
        
        model.addAttribute(follow);

        // Add tweets to the model
	   	
	   
	   return "redirect:/follow-friends";
    }
    
    @PostMapping("/unfollow/{userId}")
    public String unfollowFriends(@PathVariable("userId") int userId){
		/*
		 * List<FollowDto> usersDto=followClient.getAllFollows();
		 * 
		 * model.addAttribute("usersDto", usersDto);
		 * 
		 */
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the email from the authentication object
        String email = authentication.getName();
      
        int loginUserId=userService.getuserIdByEmail(email);
        
        int followId=followClient.unFollow(loginUserId, userId);
        
        if(followId==0) {
        	   return "redirect:/follow-friends";
        }
        
        
        
        System.out.println("null i think");
        
        // Add tweets to the model
        return "redirect:/follow-friends";
	
    }
    
    
    @GetMapping("/follow-friends")
    public String followPage(Model model){
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         // Extract the email from the authentication object
         String email = authentication.getName();
       
         System.out.println(email+" userDI 2");

			/*
			 * List<UserDto> users= userService.getAllUser();
			 * model.addAttribute("users",users);
			 */
    	  int userId=userService.getuserIdByEmail(email);
    	  
    	  System.out.println(userId+" userDI 3");
          
   	   
    List<FollowedUser>  usersFollowed= userService.isFollowed(userId);        
      model.addAttribute("users",usersFollowed);
      
   	
    	
    	
    	return "friends";
    }
    
    
	
	@GetMapping("/post-tweet")
	public String postTweets(@ModelAttribute("tweetForm") TweetDto userDto,Model model) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         // Extract the email from the authentication object
         String email = authentication.getName();
         model.addAttribute("email", email);
       
		return "tweetForm";
	}
	
	@PostMapping("/post-tweet")
	public String postTweet(@ModelAttribute("tweetForm") Tweet tweet, Model model) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         // Extract the email from the authentication object
         String email = authentication.getName();
       System.out.println(tweet.getEmail());
       tweet.setEmail(email);
		tweetClient.createTweet(tweet);
		 int loginUserId=userService.getuserIdByEmail(email);
		
		tweetClient.publishTweetsToFollowers(loginUserId);
//		model.addAttribute("message", "Registered Successfuly!");
		return "redirect:/altweets";
	}
	

	@GetMapping("/user-email")
	public String getUserEmail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Extract the email from the authentication object
        String email = authentication.getName();
        return email;
	}
	
	
	
	//get email authentication 
	//create method 
	//call it inside savetweet
	//try to post tweet using postman
	
	// then we should think any other idea
	
	
    
    
  
    
    
   
    
    
    
    
    
    
    

}
