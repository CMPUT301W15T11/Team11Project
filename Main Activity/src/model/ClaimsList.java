package model;

/*
 * The class that holds all the claims 
 * Provides functionality for working with claims
 */
/**
 * Claim list object
 * Use to hold all the claims
 */

import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * The class that holds all the claims
 * @author Stin
 *
 */
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
	/**
	 * Used to query editablility status of claim
	 * @return isEditable
	 */
	public boolean isEditable() {
		return isEditable;
	}

	// Used to denote a claim as being editable (has to do with being submitted/approved
	/**
	 *Used to denote a claim as being editable
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	
	// Adds an expense Claim to the list
	/**
	 * Adds an expense Claim to the list
	 * @param claim
	 */
	public void addClaim(ExpenseClaim claim){
		if (isEditable){
			allClaimsList.add(claim);
			//notifyListeners();
		}
		
	}
	
	// Removes an expenseClaim from the list
	/**
	 * Removes an expenseClaim from the list
	 * @param claim
	 */
	public void deleteClaim(ExpenseClaim claim){
		if (isEditable){
			allClaimsList.remove(claim);
			notifyListeners();
		}
	}
	
	// Returns list of listeners
	/**
	 * Returns list of listeners
	 * @return listeners
	 */
	public ArrayList<Listener> getListeners() {
		return listeners;
	}

	// Sets let if listeners to a new list
	/**
	 * Sets let if listeners to a new list
	 * @param listeners
	 */
	public void setListeners(ArrayList<Listener> listeners) {
		this.listeners = listeners;
	}

	// Returns the list of expenseClaims
	/**
	 * Returns the list of expenseClaims
	 * @return allClaimsList
	 */
	public Collection<ExpenseClaim> getClaims(){
		return allClaimsList;
	}
	
	// Returns the list of expenseClaims in arrayList format
	/**
	 * Returns the list of expenseClaims in arrayList format
	 * @return allClaimsList
	 */
	public ArrayList<ExpenseClaim> getClaimsAL(){
		return allClaimsList;
	} 
	/**
	 * set Claims array list
	 * @param targetList
	 */
	public void setClaimsAL(ArrayList<ExpenseClaim> targetList){
		this.allClaimsList=targetList;
	}
	
	// Refreshes Listeners
	/**
	 * Refreshes Listeners
	 */
	public void updateSubmitted(){
		notifyListeners();
	}
	
	// Returns list of submitted claims
	/**
	 * Returns list of submitted claims
	 * @return submittedClaimsList
	 */
	public ArrayList<ExpenseClaim> getSubmittedClaims(){
		return submittedClaimsList;
	}
	
	// Notifies all listeners of a change
	/**
	 * Notifies all listeners of a change
	 */
	public void notifyListeners(){
		for (Listener listener : listeners){
			listener.update();
		}
	}
	
	// Adds a new listener  
	/**
	 * Adds a new listener  
	 * @param listener
	 */
	public void addListener(Listener listener){
		listeners.add(listener);
	}
	
	// Removes a given listener from the list
	/**
	 * Removes a given listener from the list
	 * @param listener
	 */
	public void removeListener(Listener listener){
		listeners.remove(listener);
	}
	
	// Sorts claims based on date
	/**
	 * Sorts claims based on date
	 */
	public void sort(){ // Src: http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
		Collections.sort(allClaimsList, new Comparator<ExpenseClaim>() {
			public int compare(ExpenseClaim o1, ExpenseClaim o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
			});
	}
	
	// Returns number of claims in list
	/**
	 * Returns number of claims in list
	 * @return allClaimsList.size()
	 */
	public int getLength(){
		return allClaimsList.size();
	}
	
	// Returns an expenseClaim given its index in the list
	/**
	 * Returns an expenseClaim given its index in the list
	 * @param ID
	 * @return allClaimsList.get(ID)
	 */
	public ExpenseClaim getClaimById(int ID){
		return allClaimsList.get(ID);
	}

	
}
