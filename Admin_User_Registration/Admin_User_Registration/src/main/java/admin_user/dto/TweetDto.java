package admin_user.dto;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class TweetDto {
	
    private int tweetId;
	
	
	private String email;

	private String tweetText;
	
	private Date tweetDate;
	
	public TweetDto() {
		
	}
	
	public TweetDto(int tweetId, String email, String tweetText, Date tweetDate) {
		super();
		this.tweetId = tweetId;
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

	public Date getTweetDate() {
		return tweetDate;
	}

	public void setTweetDate(Date tweetDate) {
		this.tweetDate = tweetDate;
	}

	
	

}
