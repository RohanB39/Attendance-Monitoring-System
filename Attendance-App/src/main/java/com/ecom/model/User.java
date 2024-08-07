package com.ecom.model;

import java.time.LocalTime;

public class User {
	private String username;
	private String password;
	private String email;
	private Long phoneNumber;
	
	private LocalTime signInTime;
    private LocalTime signOutTime;
	
	public User(String username, String password, String email, Long phoneNumber) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public LocalTime getSignInTime() {
		return signInTime;
	}


	public void setSignInTime(LocalTime signInTime) {
		this.signInTime = signInTime;
	}


	public LocalTime getSignOutTime() {
		return signOutTime;
	}


	public void setSignOutTime(LocalTime signOutTime) {
		this.signOutTime = signOutTime;
	}
	
}
