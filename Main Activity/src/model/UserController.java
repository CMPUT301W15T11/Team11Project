package model;

public class UserController {
	
	private static String userName;
	private static String userType;
	private static String homeLocation;
	
	public static String getHomeLocation() {
		return homeLocation;
	}

	public static void setHomeLocation(String homeLocation) {
		UserController.homeLocation = homeLocation;
	}

	private static String FILENAME;
	
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

	public static String getFILENAME() {
		return FILENAME;
	}

	public static void setFILENAME(String fILENAME) {
		FILENAME = fILENAME+".sav";
	}
	
	
}
