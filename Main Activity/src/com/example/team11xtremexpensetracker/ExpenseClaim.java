package com.example.team11xtremexpensetracker;

import java.util.ArrayList;
import java.util.Calendar;

public class ExpenseClaim {
	private String claimantName;
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private ArrayList<Destination>  destinations;
	private String status;
	// TODO: set tag class
	//private  ArrayList<Tag> tags;
	private Double totalCurrency;
	private Boolean isEditable;
	private String approverName;
	private ArrayList<Item>Itemlist;
	// getters and setters
	public ExpenseClaim(){
		ArrayList<Destination> destinations = new ArrayList <Destination>() ;
		ArrayList<Item> Itemlist = new ArrayList <Item>();
	}
	// item list
	public ArrayList<Item> getItemlist() {
		return Itemlist;
	}
	public void setItemlist(ArrayList<Item> itemlist) {
		Itemlist = itemlist;
	}
	
	public void additem(Item newItem){
		this.Itemlist.add(newItem);
	}
	public Item getItemById(int ID){
		return Itemlist.get(ID);
	}
	// ClaimantName
	
	public String getClaimantName() {
		return claimantName;
	}
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}
	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// startDate
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	// endDate
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public String getDateRange(){
		//should return a string 
		//of format "startDate - EndDate
		int month1 = this.startDate.get(Calendar.MONTH);
		int day1 = this.startDate.get(Calendar.DAY_OF_MONTH);
		int year1 = this.startDate.get(Calendar.YEAR);
		int month2 = this.endDate.get(Calendar.MONTH);
		int day2 = this.endDate.get(Calendar.DAY_OF_MONTH);
		int year2 = this.endDate.get(Calendar.YEAR);
		
		String returnString = String.format("%d/%d/%d - %d/%d/%d", month1, day1, year1, month2, day2, year2);
		return returnString;
		//return this.startDate.set(startYear, startMonthOfYear+1, startDayOfMonth).toString() + " - " + this.endDate.set(startYear, startMonthOfYear+1, startDayOfMonth).toString();
	}
	
	// destinations
	public ArrayList<Destination> getDestinations() {
		return destinations;
	}
	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
	}
	// status
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// tags
	//public ArrayList<Tag> getTags() {
		//return tags;
	//}
	//public void setTags(ArrayList<Tag> tags) {
		//this.tags = tags;
	//}
	// total currency
	public Double getTotalCurrency() {
		return totalCurrency;
	}
	public void setTotalCurrency(Double totalCurrency) {
		this.totalCurrency = totalCurrency;
	}
	// edit able
	public Boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	// approver name
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	//Used to display information in list view
	public String toString() {
		return this.name;
	}
	
}
