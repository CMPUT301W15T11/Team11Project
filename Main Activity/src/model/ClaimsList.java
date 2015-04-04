package model;

/*
 * The class that holds all the claims 
 * Provides functionality for working with claims
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ClaimsList {
	private ArrayList<ExpenseClaim> allClaimsList;
	private ArrayList<ExpenseClaim> submittedClaimsList;
	private ArrayList<Listener> listeners;
	private boolean isEditable;
	
	public ClaimsList() {
		allClaimsList = new ArrayList<ExpenseClaim>();
		submittedClaimsList = new ArrayList<ExpenseClaim>();
		listeners = new ArrayList<Listener>();
		isEditable = true;
	}
	
	// Used to query editablility status of claim
	public boolean isEditable() {
		return isEditable;
	}

	// Used to denote a claim as being editable (has to do with being submitted/approved
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	// Adds an expense Claim to the list
	public void addClaim(ExpenseClaim claim){
		if (isEditable){
			allClaimsList.add(claim);
			//notifyListeners();
		}
		
	}
	
	// Removes an expenseClaim from the list
	public void deleteClaim(ExpenseClaim claim){
		if (isEditable){
			allClaimsList.remove(claim);
			notifyListeners();
		}
	}
	
	// Returns list of listeners
	public ArrayList<Listener> getListeners() {
		return listeners;
	}

	// Sets let if listeners to a new list
	public void setListeners(ArrayList<Listener> listeners) {
		this.listeners = listeners;
	}

	// Returns the list of expenseClaims
	public Collection<ExpenseClaim> getClaims(){
		return allClaimsList;
	}
	
	// Returns the list of expenseClaims in arrayList format
	
	public ArrayList<ExpenseClaim> getClaimsAL(){
		return allClaimsList;
	} 
	
	// Refreshes Listeners
	public void updateSubmitted(){
		notifyListeners();
	}
	
	// Returns list of submitted claims
	public ArrayList<ExpenseClaim> getSubmittedClaims(){
		return submittedClaimsList;
	}
	
	// Notifies all listeners of a change
	public void notifyListeners(){
		for (Listener listener : listeners){
			listener.update();
		}
	}
	
	// Adds a new listener  
	public void addListener(Listener listener){
		listeners.add(listener);
	}
	
	// Removes a given listener from the list
	public void removeListener(Listener listener){
		listeners.remove(listener);
	}
	
	// Sorts claims based on date
	public void sort(){ // Src: http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
		Collections.sort(allClaimsList, new Comparator<ExpenseClaim>() {
			public int compare(ExpenseClaim o1, ExpenseClaim o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
			});
	}
	
	// Returns number of claims in list
	public int getLength(){
		return allClaimsList.size();
	}
	
	// Returns an expenseClaim given its index in the list
	public ExpenseClaim getClaimById(int ID){
		return allClaimsList.get(ID);
	}

	
}
