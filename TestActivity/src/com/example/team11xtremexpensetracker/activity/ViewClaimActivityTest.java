package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.ClaimListController;
import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.UserController;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ViewClaimActivityTest extends ActivityInstrumentationTestCase2<ViewClaimActivity> {

	
	Button buttonClaimTags;
	Button buttonApproveSubmit;
	TextView textViewClaimName;
	TextView textViewClaimDateRange;
	Button buttonClaimAddExpense;
	ListView expenseListView;
	ViewClaimActivity mActivity;
	
	//Values to check
	String claimName = "maui3";
	
	
	public ViewClaimActivityTest() {
		super(ViewClaimActivity.class);

	}
	
	protected void setUp() throws Exception {
		UserController UC = new UserController();
		UC.setUserType("Claimant");
		
		// ** Need to get index of newest claim here!!
		int claimID = (new ClaimListController().getClaimsList().getLength());
		Intent intent = new Intent();
		intent.putExtra("claimID", 2);
		setActivityIntent(intent);
		setActivityInitialTouchMode(true);
		mActivity = getActivity();
		
		super.setUp();
		
        buttonClaimTags = (Button)mActivity.findViewById(R.id.buttonClaimTags);
        buttonApproveSubmit = (Button)mActivity.findViewById(R.id.buttonApproveSubmit);
        textViewClaimName = (TextView)mActivity.findViewById(R.id.textViewClaimName);
        textViewClaimDateRange = (TextView)mActivity.findViewById(R.id.textViewClaimDateRange);
        buttonClaimAddExpense = (Button)mActivity.findViewById(R.id.buttonClaimAddExpense);
        expenseListView = (ListView)mActivity.findViewById(R.id.expenseListView);
		
	}
	
	/*
	@MediumTest
	public void testTagButton() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, buttonClaimTags);

	    final ViewGroup.LayoutParams layoutParams = buttonClaimTags.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	@MediumTest
	public void testApproveSubmitButton() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, buttonApproveSubmit);

	    final ViewGroup.LayoutParams layoutParams = buttonApproveSubmit.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	@MediumTest
	public void testName() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, textViewClaimName);

	    final ViewGroup.LayoutParams layoutParams = textViewClaimName.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	@MediumTest
	public void testDateRange() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, textViewClaimDateRange);

	    final ViewGroup.LayoutParams layoutParams = textViewClaimDateRange.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	@MediumTest
	public void testAddExpenseButton() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, buttonClaimAddExpense);

	    final ViewGroup.LayoutParams layoutParams = buttonClaimAddExpense.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	@MediumTest
	public void testEnterExpenseList() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, expenseListView);

	    final ViewGroup.LayoutParams layoutParams = expenseListView.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	} */
	
	
	@MediumTest
	public void testCorrectClaim() throws Exception{
		String display = new Integer(new ClaimListController().getClaimsList().getLength()).toString();
		assertTrue("Claim: " + textViewClaimName.getText() +" count: " + display , textViewClaimName.getText().equals(claimName));
		//tearDown();
	}
	
	/*
	@MediumTest
	public void testAddExpense() throws Exception{
		assertTrue(1==1);
		tearDown();
	} */
}
