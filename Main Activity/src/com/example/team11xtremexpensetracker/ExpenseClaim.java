package com.example.team11xtremexpensetracker;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Expense claim object:
 * Use to store expense items.
 * 
 * @author Xindong
 *
 */
public class ExpenseClaim {
	
	private String claimantName;
	private String name;
	private Calendar startDate;
	private Calendar endDate;
	private ArrayList<Destination>  destinations;
	private String status;
	private  ArrayList<Tag> tagList;
	private Double totalCurrency;
	private Boolean isEditable;
	private String approverName;
	private ArrayList<Item>Itemlist;
	// getters and setters	
	/**
	 * Create a empty expense claim object.
	 */
	public ExpenseClaim(){
		isEditable = true;
		destinations = new ArrayList <Destination>() ;
		Itemlist = new ArrayList <Item>();
		tagList=new ArrayList<Tag>();
	}
	// item list
	/**
	 * Get item list.
	 * @return Item list
	 */
	public ArrayList<Item> getItemlist() {
		return Itemlist;
	}
	/**
	 * Set item list.
	 * @param itemlist
	 */
	public void setItemlist(ArrayList<Item> itemlist) {
		Itemlist = itemlist;
	}
	
	/**
	 * Add item to item list.
	 * @param newItem
	 */
	public void addItem(Item newItem){
		if (isEditable){
			this.Itemlist.add(newItem);
		}
	}
	/**
	 * Remove item form item list.
	 * @param id
	 */
	public void removeItem(int id){
		if (isEditable){
			this.Itemlist.remove(id);
		}
	}
	
	/**
	 * Get item by id from item list.
	 * @param ID
	 * @return
	 */
	public Item getItemById(int ID){
		return Itemlist.get(ID);
	}
	// ClaimantName
	
	/**
	 * Get claimant name.
	 * @return claimantName
	 */
	public String getClaimantName() {
		return claimantName;
	}
	/**
	 * Set claimant name.
	 * @param claimantName
	 */
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}
	// Name
	/**
	 * Get name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	// startDate
	/**
	 * Get start date.
	 * @return
	 */
	public Calendar getStartDate() {
		return startDate;
	}
	/**
	 * Set start date.
	 * @param startDate
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	// endDate
	/**
	 * Get end date
	 * @return endDate
	 */
	public Calendar getEndDate() {
		return endDate;
	}
	/**
	 * Set end date
	 * @param endDate
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	/**
	 * Get date range as a string.
	 * @return returnString
	 */
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
	/**
	 * Get destination list.
	 * @return destinations
	 */
	
	// destinations
	public ArrayList<Destination> getDestinations() {
		return destinations;
	}
	/**
	 * Set destinations list.
	 * @param destinations
	 */
	public void setDestinations(ArrayList<Destination> destinations) {
		this.destinations = destinations;
	}
	// status
	/**
	 * Get status.
	 * @return status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Set status.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Get tag list.
	 * @return tagList
	 */
	// tags
	public ArrayList<Tag> getTagList() {
		return tagList;
	}
	/**
	 * Set tag list.
	 * @param tagList
	 */
	public void setTags(ArrayList<Tag> tagList) {
		this.tagList = tagList;
	}
	// total currency
	/**
	 * Get total currency.
	 * @return totalCurrency
	 */
	public Double getTotalCurrency() {
		return totalCurrency;
	}
	/**
	 * Get total currency.
	 * @param totalCurrency
	 */
	public void setTotalCurrency(Double totalCurrency) {
		this.totalCurrency = totalCurrency;
	}
	/**
	 * Check if the claim is editable.
	 * @return totalCurrency
	 */
	// edit able
	public Boolean getIsEditable() {
		return isEditable;
	}
	/**
	 * Set if the claim is editable.
	 * @param isEditable
	 */
	public void setIsEditable(Boolean isEditable) {
		this.isEditable = isEditable;
	}
	// approver name
	/**
	 * Get approve name.
	 * @return
	 */
	public String getApproverName() {
		return approverName;
	}
	/**
	 * Set approve name.
	 * @param approverName
	 */
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	//Used to display information in list view
	/**
	 * Return the object string as its name.
	 */
	public String toString() {
		// TODO the structure is weird, it's hard to manage with only claimList, we may need to add userList as well
		return this.name;
	}
	
}
