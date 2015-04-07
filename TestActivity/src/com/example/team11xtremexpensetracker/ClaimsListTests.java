package com.example.team11xtremexpensetracker;

import model.ClaimsList;
import model.ExpenseClaim;
import junit.framework.TestCase;

public class ClaimsListTests extends TestCase {

	public void testAddClaim() {
		ExpenseClaim test = new ExpenseClaim();
		ClaimsList testList = new ClaimsList();
		testList.addClaim(test);
		assertEquals("The claim has been added.", testList.getLength(), 1);
		
	}

	public void testDeleteClaim() {
		ExpenseClaim test = new ExpenseClaim();
		ClaimsList testList = new ClaimsList();
		testList.addClaim(test);
		testList.deleteClaim(test);
		assertEquals("The claim has been deleted.", testList.getLength(), 0);
		

	}

	/*
	public void testGetClaims() {
		ExpenseClaim test = new ExpenseClaim();
		ClaimsList testList = new ClaimsList();
		testList.addClaim(test);
		assertEquals("Correct claim returned.", testList.getClaims(), test);
	}
	*/

}
