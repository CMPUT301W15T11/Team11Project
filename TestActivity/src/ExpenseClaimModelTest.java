


import java.util.ArrayList;
import java.util.Calendar;

import com.example.team11xtremexpensetracker.Destination;
import com.example.team11xtremexpensetracker.ExpenseClaimModel;

import junit.framework.TestCase;

public class ExpenseClaimModelTest extends TestCase {


	public void testClaimantName() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		test.setClaimantName("test");
		assertTrue("claimant name",test.getClaimantName()=="test");
	}

	public void testName() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		test.setName("test");
		assertTrue("claimant name",test.getName()=="test");
	}

	public void testStartDate() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		Calendar testDate = Calendar.getInstance();
		test.setStartDate(testDate);	
		assertTrue("claimant name",test.getStartDate()==testDate);
	}

	public void testEndDate() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		Calendar testDate = Calendar.getInstance();
		test.setEndDate(testDate);	
		assertTrue("claimant name",test.getEndDate()==testDate);
	}

	public void testGetDestinations() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		ArrayList<Destination> testDestinations = new ArrayList<Destination>();
		test.setDestinations(testDestinations);	
		assertTrue("claimant name",test.getDestinations()==testDestinations);
	}

	public void testGetStatus() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		test.setStatus("test");
		assertTrue("claimant name",test.getStatus()=="test");
	}

	// TODO: test tags
	public void testGetTags() {
		fail("Not yet implemented");
	}

	public void testSetTags() {
		fail("Not yet implemented");
	}
	
	
	public void testTotalCurrency() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		double testCurrency = 100;
		test.setTotalCurrency(testCurrency);
		assertTrue("claimant name",test.getTotalCurrency()==testCurrency);
	}

	public void testIsEditable() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		boolean testIsEditable = true;
		test.setIsEditable(testIsEditable);
		assertTrue("claimant name",test.getIsEditable()==testIsEditable);
	}


	public void testApproverName() {
		ExpenseClaimModel test = new ExpenseClaimModel();
		test.setApproverName("test");
		assertTrue("claimant name",test.getStatus()=="test");
	}

}
