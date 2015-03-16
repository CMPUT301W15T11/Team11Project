package com.example.team11xtremexpensetracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ClaimsList {
	private ArrayList<ExpenseClaim> allClaimsList;
	private ArrayList<ExpenseClaim> submittedClaimsList;
	private ArrayList<Listener> listeners;
	private boolean isEditable;
	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}


	public ClaimsList() {
		allClaimsList = new ArrayList<ExpenseClaim>();
		submittedClaimsList = new ArrayList<ExpenseClaim>();
		listeners = new ArrayList<Listener>();
		isEditable = true;
	}
	
	public void addClaim(ExpenseClaim claim){
		if (isEditable){
			allClaimsList.add(claim);
			//notifyListeners();
		}
		
	}
	
	public ArrayList<Listener> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<Listener> listeners) {
		this.listeners = listeners;
	}

	public void deleteClaim(ExpenseClaim claim){
		if (isEditable){
			allClaimsList.remove(claim);
			notifyListeners();
		}
	}
	
	public Collection<ExpenseClaim> getClaims(){
		return allClaimsList;
	}
	public ArrayList<ExpenseClaim> getClaimsAL(){
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
	
	public void sort(){ // Src: http://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
		Collections.sort(allClaimsList, new Comparator<ExpenseClaim>() {
			public int compare(ExpenseClaim o1, ExpenseClaim o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
			});
	}
	
	public int getLength(){
		return allClaimsList.size();
	}
	public ExpenseClaim getClaimById(int ID){
		return allClaimsList.get(ID);
	}

	
}
