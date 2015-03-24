package com.example.team11xtremexpensetracker;

public class UserController {
	
	private User currentUser;
	
	public UserController(User currentUser){
		this.currentUser=currentUser;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
}
