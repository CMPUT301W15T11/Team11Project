


import java.util.ArrayList;
import java.util.Calendar;

import com.example.team11xtremexpensetracker.ClaimsList;
import com.example.team11xtremexpensetracker.Destination;
import com.example.team11xtremexpensetracker.ExpenseClaim;

import junit.framework.TestCase;

public class ExpenseClaimModelTest extends TestCase {

	// getter and setter test
	public void testClaimantName() {
		ExpenseClaim test = new ExpenseClaim();
		test.setClaimantName("test");
		assertTrue("claimant name",test.getClaimantName()=="test");
	}

	public void testName() {
		ExpenseClaim test = new ExpenseClaim();
		test.setName("test");
		assertTrue("claimant name",test.getName()=="test");
	}

	public void testStartDate() {
		ExpenseClaim test = new ExpenseClaim();
		Calendar testDate = Calendar.getInstance();
		test.setStartDate(testDate);	
		assertTrue("claimant name",test.getStartDate()==testDate);
	}

	public void testEndDate() {
		ExpenseClaim test = new ExpenseClaim();
		Calendar testDate = Calendar.getInstance();
		test.setEndDate(testDate);	
		assertTrue("claimant name",test.getEndDate()==testDate);
	}

	public void testGetDestinations() {
		ExpenseClaim test = new ExpenseClaim();
		ArrayList<Destination> testDestinations = new ArrayList<Destination>();
		test.setDestinations(testDestinations);	
		assertTrue("claimant name",test.getDestinations()==testDestinations);
	}

	public void testGetStatus() {
		ExpenseClaim test = new ExpenseClaim();
		test.setStatus("test");
		assertTrue("claimant name",test.getStatus()=="test");
	}

	// TODO: test tags
	public void testGetTags() {
		//fail("Not yet implemented");
	}

	public void testSetTags() {
		//fail("Not yet implemented");
	}
	
	
	public void testTotalCurrency() {
		ExpenseClaim test = new ExpenseClaim();
		double testCurrency = 100;
		test.setTotalCurrency(testCurrency);
		assertTrue("claimant name",test.getTotalCurrency()==testCurrency);
	}

	public void testIsEditable() {
		ExpenseClaim test = new ExpenseClaim();
		boolean testIsEditable = true;
		test.setIsEditable(testIsEditable);
		assertTrue("claimant name",test.getIsEditable()==testIsEditable);
	}


	public void testApproverName() {
		ExpenseClaim test = new ExpenseClaim();
		test.setApproverName("test");
		assertTrue("claimant name",test.getApproverName()=="test");
	}
	
	// requirement test 1.01
	public void test1_01(){
		ExpenseClaim  claim = new ExpenseClaim();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		claim.setName("name");
		claim.setStartDate(startDate);
		claim.setEndDate(endDate);
		assertTrue(claim.getStartDate()==startDate);
		assertTrue(claim.getEndDate()==endDate);
		assertTrue(claim.getName() == "name");
	}
	// requirement test 1.02
	public void test1_02(){
		ExpenseClaim  claim = new ExpenseClaim();
		Destination dest = new Destination("string");
		dest.setReason("Reason");
		claim.getDestinations().add(dest);
		assertTrue(claim.getDestinations().get(0).getReason() == "Reason");  
	}
	// requirement test 1.03
	public void test1_03(){
		ExpenseClaim  claim = new ExpenseClaim();
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		claim.setName("name");
		claim.setStartDate(startDate);
		claim.setEndDate(endDate);
		Destination dest = new Destination("string");
		dest.setReason("Reason");
		claim.getDestinations().add(dest);
		assertTrue(claim.getDestinations().get(0)== dest);
		assertTrue(claim.getDestinations().get(0).getReason() == "Reason");  
		assertTrue(claim.getStartDate()==startDate);
		assertTrue(claim.getEndDate()==endDate);
		assertTrue(claim.getName() == "name");
	}
	// requirement test 1.04
	public void test1_04(){
		ExpenseClaim  claim = new ExpenseClaim();
		claim.setIsEditable(false);
		assertFalse(claim.getIsEditable());
		claim.setIsEditable(true);
		assertTrue(claim.getIsEditable());
		
	}
	
	// requirement test 1.05
	public void test1_05(){	
		ClaimsList claims = new ClaimsList();
		claims.setEditable(true);
		ExpenseClaim  claim = new ExpenseClaim();
		claims.addClaim(claim);
		claims.deleteClaim(claim);
		assertTrue(claims.getClaims().size() == 0);
		
		claims.addClaim(claim);
		claims.setEditable(false);
		claims.deleteClaim(claim);
		assertTrue(claims.getClaims().size() == 1);
		claims.addClaim(claim);
		assertTrue(claims.getClaims().size() == 1);
	}
	
	// requirement test 1.06
	public void test1_06(){
		ClaimsList claims = new ClaimsList();
	}
	

}
