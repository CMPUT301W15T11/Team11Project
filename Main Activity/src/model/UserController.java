package model;
/**
 * controller for users
 * @author Stin
 *
 */
public class UserController {
	
	private static String userName;
	private static String userType;
	private static String FILENAME;
	/**
	 * set user name
	 * @param userName
	 */
	public static void setUserName(String userName){
		UserController.userName=userName;
	}
	/**
	 * get user name
	 * @return userName
	 */
	public static String getUserName(){
		return userName;
	}
	/**
	 * set user type
	 * @param userType
	 */
	public static void setUserType(String userType){
		UserController.userType=userType;
	}
	/**
	 * get uset type
	 * @return userType
	 */
	public static String getUserType(){
		return userType;
	}
	/**
	 * get file name
	 * @return FILENAME
	 */
	public static String getFILENAME() {
		return FILENAME;
	}
	/**
	 * set file name
	 * @param fILENAME
	 */
	public static void setFILENAME(String fILENAME) {
		FILENAME = fILENAME+".sav";
	}
	
	
}
