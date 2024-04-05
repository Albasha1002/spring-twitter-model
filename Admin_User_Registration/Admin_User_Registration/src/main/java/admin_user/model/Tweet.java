package admin_user.model;



import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
public class Tweet {
	@Id
	@GeneratedValue
	private int tweetId;
	@Column(name="email",nullable=false)
	private String email;
	@Column(name="tweetText",nullable=false)
	private String tweetText;
	@Column(name="tweetDate",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime tweetDate;
	
	public Tweet(){
		
	}
	
	public Tweet(int tweetId, String email, String tweetText, LocalDateTime tweetDate) {
		super();
		this.tweetId = tweetId;
		this.email = email;
		this.tweetText = tweetText;
		this.tweetDate = tweetDate;
	}
	public Tweet(String email, String tweetText) {
		super();
		this.email = email;
		this.tweetText = tweetText;
	}
	public Tweet(String email, String tweetText, LocalDateTime tweetDate) {
		super();
		this.email = email;
		this.tweetText = tweetText;
		this.tweetDate = tweetDate;
	}
	public int getTweetId() {
		return tweetId;
	}
	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	public LocalDateTime getTweetDate() {
		return tweetDate;
	}
	public void setTweetDate(LocalDateTime tweetDate) {
		this.tweetDate = tweetDate;
	}
	
	
	
	
	
	
	

}
