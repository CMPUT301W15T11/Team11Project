package model;

/*
 *  Singleton Class
 *  Holds list of all claims in system.
 */
/**
 * Singleton Class,Holds list of all claims in system.
 * @author Stin
 *
 */
public class ClaimListController {

	private static ClaimsList claimsList = null;
	/**
	 * get claim list
	 * @return claimsList
	 */
	static public ClaimsList getClaimsList(){
		if (claimsList == null){
			claimsList = new ClaimsList();
		}
		return claimsList;
	}
	
	// Adds a claim to the list
	/**
	 * Adds a claim to the list
	 * @param claim
	 */
	public static void addClaim(ExpenseClaim claim){
		getClaimsList().addClaim(claim);
	}
	// Removes a claim given it's ID in the list
	/**
	 * Removes a claim given it's ID in the list
	 * @param ID
	 */
	public static void removeClaim(int ID){
		getClaimsList().getClaimsAL().remove(ID);
		//getClaimsList().getClaims().remove(ID);
	}
	// Removes Listener from claimsList with given ID
	/**
	 * Removes Listener from claimsList with given ID
	 * @param ID
	 */
	public static void removeListener(int ID){
		getClaimsList().getListeners().remove(ID);
	}

	// Sets the inital claims list
	/**
	 * Sets the inital claims list
	 * @param claimsList
	 */
	public static void setClaimsList(ClaimsList claimsList) {
		ClaimListController.claimsList = claimsList;
	}
	/**
	 * Sort the claim in claim list
	 */
	public static void sort(){
		claimsList.sort();
	}
}
