package com.practicaldeveloper.follows.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.practicaldeveloper.follows.model.Follow;

@Repository
@EnableJpaRepositories
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    // You can define custom query methods here if needed
	
	 @Query("SELECT f FROM Follow f WHERE f.follower.userId = :followerId")
	 List<Follow> findFollowByFollowerId(Integer followerId);
//	Follow findByFollowerAndFollowing(int followerId, int followingId);
	 
	 @Query("SELECT f.followId FROM Follow f WHERE f.follower.userId = :followerId AND f.following.userId = :followingId")
	 Integer deleteFollowing(int followerId, int followingId);
	 
	 @Query("SELECT f FROM Follow f WHERE f.follower.userId = :followerUserId AND f.following.userId = :followingUserId")
	 Follow findByFollowerUserIdAndFollowingUserId(int followerUserId, int followingUserId);
	 

	
}

