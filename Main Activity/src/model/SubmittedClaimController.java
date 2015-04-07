package model;

import java.util.ArrayList;
/**
 * Controller for submitted claim
 * @author Stin
 *
 */
public class SubmittedClaimController {

	private static ArrayList<ExpenseClaim> submittedList;
	/**
	 * get submitted claim list
	 * @return submittedList
	 */
	public static ArrayList<ExpenseClaim> getSubmittedList() {
		return submittedList;
	}
/**
 * set the expense claim submitted
 * @param submittedList
 */
	public static void setSubmittedList(ArrayList<ExpenseClaim> submittedList) {
		SubmittedClaimController.submittedList = submittedList;
	}
/**
 * find submitted claim by claim ID	
 * @param claimId
 * @return submittedList.get(claimId)
 */
	public static ExpenseClaim getSubmittedClaimById(int claimId){
		return submittedList.get(claimId);
	}
}
