package admin_user.dto;

public class FollowedUser {

	
	int userId;
	
	String username;
	
	boolean isFollowed;
	
	public FollowedUser() {
		
	}

	public FollowedUser(int userId, String username, boolean isFollowed) {
		super();
		this.userId = userId;
		this.username = username;
		this.isFollowed = isFollowed;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isFollowed() {
		return isFollowed;
	}

	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}

	
}
