package model;

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
	private String location;
	private String amount;
	private String unit;
	private String description;
	private Boolean indicator=false;
	private Boolean hasPhoto=false;
	private String photo;

	/**
	 * get item name
	 * @return item
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
	 * @return date
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
	 * @return category
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
	 * @return amount
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
	 * @return unit
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
	 * @return description
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
	 * @return indicator
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
	/**
	 * get indicator if has photo or not
	 * @return hasPhoto
	 */
	public Boolean getHasPhoto() {
		return hasPhoto;
	}
	/**
	 * @param hasPhoto the hasPhoto to set
	 */
	public void setHasPhoto(Boolean hasPhoto) {
		this.hasPhoto = hasPhoto;
	}
	/**
	 * get the photo
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}
	/**
	 * set photo
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	/**
	 * get location of the item
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * set location of the item
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
}
