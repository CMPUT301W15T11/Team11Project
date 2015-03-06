package com.example.team11xtremexpensetracker;

/*
 *  Controller for the list of all current claims in the system.
 */
public class ClaimListController {

	private static ClaimsList claimsList = null;
	
	static public ClaimsList getClaimsList(){
		if (claimsList == null){
			claimsList = new ClaimsList();
		}
		return claimsList;
	}
	
	public void addClaim(ExpenseClaim claim){
		getClaimsList().addClaim(claim);
	}
}
