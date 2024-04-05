package admin_user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import admin_user.dto.FollowDto;
import admin_user.dto.FollowedUser;
import admin_user.dto.TweetDto;
import admin_user.dto.UserDto;
import admin_user.model.User;
import admin_user.openfiegn.FollowClient;
import admin_user.openfiegn.TweetClient;
import admin_user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired(required = true)
	private TweetClient tweetClient;
	
	@Autowired
	private FollowClient followClient;
	

	@Override
	public User save(UserDto userDto){
		User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , "USER", userDto.getFullname());
		return userRepository.save(user);
	}

	@Override
	public UserDto getTweetById(String email) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findByEmail(email);
		
	    UserDto userDto=modelMapper.map(user,UserDto.class);
		
	   ResponseEntity<List<TweetDto>> tweetResponse = tweetClient.getAllTweetsByEmail(email);
	   
	   List<TweetDto> tweetDtoList=tweetResponse.getBody();
	    
	    
	    userDto.setTweetDtoList(tweetDtoList);
		
		return userDto;
	}

	@Override
	public UserDto getUserId(Integer userId) {
		// TODO Auto-generated method stub
		User user=userRepository.getById(userId);
		
		UserDto userDto=modelMapper.map(user,UserDto.class);
		
		return userDto;
	}

	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		
		List<User> userList=userRepository.findAll();
		
		List<UserDto> userDtoList=new ArrayList<>();
		
		
		for(User user:userList) {
			UserDto userDto=new UserDto(user.getUserId(),user.getFullname());
			userDtoList.add(userDto);
			
			
		}
		
		return userDtoList;
	}

	

	@Override
	public int getuserIdByEmail(String email) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findByEmail(email);
		
		return user.getUserId();
	}
	
	
	public List<FollowedUser> isFollowed(int userId){
		
	     List<FollowDto> followedUserDtos=followClient.getAllFollows();
	     
	     List<UserDto> users=getAllUser();
	     
	    List<FollowedUser> followedUsers=new ArrayList<>();
	     
	    
	     for(UserDto userDto:users) {
	     
	    
	    	 
	    	 
	    	 FollowedUser followedUser=new FollowedUser();
	    	 
	    	 followedUser.setUserId(userDto.getUserId());
	    	 
	    	 System.out.println(followClient.isFollowed(userId, userDto.getUserId()));
	    	 followedUser.setFollowed(followClient.isFollowed(userId, userDto.getUserId()));
	    	 
	    	 followedUser.setUsername(userRepository.getById(userDto.getUserId()).getFullname());
	    	 
	    	 followedUsers.add(followedUser);
	    	 
	    
	    	 
	    	 
	     }
	     
	     
	     
		return followedUsers;
		
	}
	
	
	
	
	
	
	

}
