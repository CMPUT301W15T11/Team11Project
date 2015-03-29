package com.example.team11xtremexpensetracker;

public class UserController {
	
	private static String userName;
	private static String userType;
	
	public static void setUserName(String userName){
		UserController.userName=userName;
	}
	
	public static String getUserName(){
		return userName;
	}
	
	public static void setUserType(String userType){
		UserController.userType=userType;
	}
	
	public static String getUserType(){
		return userType;
	}
}
