/*
 * Used to record a travel destination and reason for travel
 * Constructor takes 1 arg, the destination name
 * in it's current form, destination name cannot be changed after creation.
 */

package model;
/**
 * Used to record a travel destination and reason for travel
 * @author Stin
 *
 */
public class Destination {
	protected String destinationName;
	protected String reasonForTravel;
	protected String locationStr;
	//get destination location
	/**
	 * get destination location
	 * @return locationStr
	 */
	public String getLocationStr() {
		return locationStr;
	}
//set destination location
	/**
	 * set destination location
	 * @param locationStr
	 */
	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}

	// Constructor. Sets destination name
	/**
	 * Constructor. Sets destination name
	 * @param name
	 */
	public Destination(String name){
		this.destinationName = name;
	}
	
	// Set travel resons
	/**
	 * Set travel resons
	 * @param reason
	 */
	public void setReason(String reason){
		this.reasonForTravel = reason;
	}
	
	// Get travel reason
	/**
	 * Get travel reason
	 * @return reasonForTravel
	 */
	public String getReason(){
		if (reasonForTravel != null)
			return reasonForTravel;
		else 
			return "";
	}
	
	//returns destination name
	/**
	 * returns destination name
	 * @return destinationName
	 */
	public String getDest(){
		return destinationName;
	}
}
