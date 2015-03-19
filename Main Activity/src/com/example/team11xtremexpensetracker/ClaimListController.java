package com.example.team11xtremexpensetracker;



/*
 *  Singleton Class
 *  Holds list of all claims in system.
 */


public class ClaimListController {

	private static ClaimsList claimsList = null;
	
	static public ClaimsList getClaimsList(){
		if (claimsList == null){
			claimsList = new ClaimsList();
		}
		claimsList.sort();
		return claimsList;
	}
	
	// Adds a claim to the list
	public static void addClaim(ExpenseClaim claim){
		getClaimsList().addClaim(claim);
	}
	// Removes a claim given it's ID in the list
	public static void removeClaim(int ID){
		getClaimsList().getClaimsAL().remove(ID);
		//getClaimsList().getClaims().remove(ID);
	}
	// Removes Listener from claimsList with given ID
	public static void removeListener(int ID){
		getClaimsList().getListeners().remove(ID);
	}

	// Sets the inital claims list
	public static void setClaimsList(ClaimsList claimsList) {
		ClaimListController.claimsList = claimsList;
	}
	
}
