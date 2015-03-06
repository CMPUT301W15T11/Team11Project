package com.example.team11xtremexpensetracker;

import java.util.ArrayList;
import java.util.Collection;

public class ClaimsList {
	private ArrayList<ExpenseClaim> allClaimsList;
	private ArrayList<ExpenseClaim> submittedClaimsList;
	private ArrayList<Listener> listeners;

	public ClaimsList() {
		allClaimsList = new ArrayList<ExpenseClaim>();
		submittedClaimsList = new ArrayList<ExpenseClaim>();
		listeners = new ArrayList<Listener>();
		
	}
	
	public void addClaim(ExpenseClaim claim){
		allClaimsList.add(claim);
		notifyListeners();
		
	}
	
	public void deleteClaim(ExpenseClaim claim){
		allClaimsList.remove(claim);
		notifyListeners();
	}
	
	public Collection<ExpenseClaim> getClaims(){
		return allClaimsList;
	}
	
	public void updateSubmitted(){
		notifyListeners();
	}
	
	public ArrayList<ExpenseClaim> getSubmittedClaims(){
		return submittedClaimsList;
	}
	
	public void notifyListeners(){
		for (Listener listener : listeners){
			listener.update();
		}
	}
	
	public void addListener(Listener listener){
		listeners.add(listener);
	}
	
	public void removeListener(Listener listener){
		listeners.remove(listener);
	}
	
	public void sort(){
		
	}

}
