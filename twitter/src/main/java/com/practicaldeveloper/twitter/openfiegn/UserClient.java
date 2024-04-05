package com.practicaldeveloper.twitter.openfiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.practicaldeveloper.twitter.dto.UserDto;



@FeignClient(name = "user-app", url = "http://localhost:8082", path = "/user-app/api")
public interface UserClient {
	
	@GetMapping("/{userId}")
    public ResponseEntity<UserDto>  getByUserId(@PathVariable Integer userId);

	@GetMapping("/user-email")
	public String getUserEmail();

}
