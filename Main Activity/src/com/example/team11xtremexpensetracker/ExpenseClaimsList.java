package com.example.team11xtremexpensetracker;

import java.util.ArrayList;
import java.util.Collection;

public class ExpenseClaimsList {
	private ArrayList<ExpenseClaim> expenseClaimsList;
	private ArrayList<ExpenseClaim> submittedClaimsList;
	private ArrayList<Listener> listeners;

	public ExpenseClaimsList() {
		expenseClaimsList = new ArrayList<ExpenseClaim>();
		submittedClaimsList = new ArrayList<ExpenseClaim>();
		
	}
	
	public void addClaim(ExpenseClaim claim){
		expenseClaimsList.add(claim);
		
	}
	
	public deleteClaim(ExpenseClaim claim){
		expenseClaimsList.remove(claim);
	}
	
	public Collection<ExpenseClaim> getClaims(){
		return expenseClaimsList;
	}
	
	public void updateSubmitted(){
		
	}
	
	public ArrayList<ExpenseClaim> getSubmittedClaims(){
		return submittedClaimsList;
	}
	
	public sort(){
		
	}

}
