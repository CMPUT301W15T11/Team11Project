package com.example.team11xtremexpensetracker;

import java.util.Calendar;
/**
 * Item structure
 * use to store items
 * @author Stin
 *
 */

public class Item {
	private String item;
	private Calendar date;
	private String category;
	private String amount;
	private String unit;
	private String description;
	private Boolean indicator = false;
	private Boolean hasPhoto = false;
	private Byte[] photo;
	/**
	 * get item name
	 * @return
	 */
	public String getItem() {
		return item;
	}
	/**
	 * set item name
	 * @param item
	 */
	public void setItem(String item) {
		this.item = item;
	}
	/**
	 * get item date
	 * @return
	 */
	public Calendar getDate() {
		return date;
	}
	/**
	 * set item date
	 * @param date
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}
	/**
	 * get item category
	 * @return
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * set item category
	 * @param category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * get item amount spent
	 * @return
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * set item amount spent
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * get item unit of currency
	 * @return
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * set item unit of currency
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * get item description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * set item description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * get item indicator
	 * @return
	 */
	public Boolean getIndecator(){
		return indicator;
	}
	/**
	 * item set indicator
	 * @param indicator
	 */
	public void setIndecator(Boolean indicator){
		this.indicator = indicator;
	}

}
