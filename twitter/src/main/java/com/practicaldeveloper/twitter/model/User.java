package com.practicaldeveloper.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	private String email;
	private String password;
	private String role;
	private String fullname;
	
	public User() {
		super();
	}

	public User(int userId, String email, String fullname) {
		super();
		this.userId = userId;
		this.email = email;
		this.fullname = fullname;
	}

	public User(String email, String password, String role, String fullname) {
		
		this.email = email;
		this.password = password;
		this.role = role;
		this.fullname = fullname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	
	
	
	
	
	
	
	

}
