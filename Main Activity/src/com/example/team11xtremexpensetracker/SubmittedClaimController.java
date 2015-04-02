package com.example.team11xtremexpensetracker;

import java.util.ArrayList;

public class SubmittedClaimController {

	private static ArrayList<ExpenseClaim> submittedList;

	public static ArrayList<ExpenseClaim> getSubmittedList() {
		return submittedList;
	}

	public static void setSubmittedList(ArrayList<ExpenseClaim> submittedList) {
		SubmittedClaimController.submittedList = submittedList;
	}
	
	public static ExpenseClaim getSubmittedClaimById(int claimId){
		return submittedList.get(claimId);
	}
}
