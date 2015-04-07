/*
 * Used to record a travel destination and reason for travel
 * Constructor takes 1 arg, the destination name
 * in it's current form, destination name cannot be changed after creation.
 */

package model;

public class Destination {
	protected String destinationName;
	protected String reasonForTravel;
	protected String locationStr;

	public String getLocationStr() {
		return locationStr;
	}

	public void setLocationStr(String locationStr) {
		this.locationStr = locationStr;
	}

	// Constructor. Sets destination name
	public Destination(String name){
		this.destinationName = name;
	}
	
	// Set travel resons
	public void setReason(String reason){
		this.reasonForTravel = reason;
	}
	
	// Get travel reason
	public String getReason(){
		if (reasonForTravel != null)
			return reasonForTravel;
		else 
			return "";
	}
	
	//returns destination name
	public String getDest(){
		return destinationName;
	}
}
