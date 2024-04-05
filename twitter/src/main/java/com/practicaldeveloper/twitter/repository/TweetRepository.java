package com.practicaldeveloper.twitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practicaldeveloper.twitter.dto.TweetDto;
import com.practicaldeveloper.twitter.model.Tweet;
@Repository
public interface TweetRepository extends JpaRepository<Tweet,Integer>{
    List<Tweet> findByEmail(String email);
}
