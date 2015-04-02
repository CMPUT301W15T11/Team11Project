package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.test.ViewAsserts;
import android.view.ViewGroup;
import android.test.suitebuilder.annotation.MediumTest;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Button claimantButton;
	private Button approverButton;
	
	public MainActivityTest() {
		super(MainActivity.class);
		
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(true);
		mActivity = getActivity();
		claimantButton = (Button) mActivity.findViewById(R.id.claimantButton);
		approverButton = (Button) mActivity.findViewById(R.id.approverButton);
	}
	
	
	@MediumTest
	public void testapproverButton() throws Exception{
		//setUp();
		final View decorView = mActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, approverButton);
		
		final ViewGroup.LayoutParams layoutParams = approverButton.getLayoutParams();
		assertNotNull("Approver Button does not exist", layoutParams);
		tearDown();
	}
	
	
	@MediumTest
	public void testClaimantButton() throws Exception{
		//setUp();
		final View decorView = mActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, claimantButton);
		
		final ViewGroup.LayoutParams layoutParams = claimantButton.getLayoutParams();
		assertNotNull("Claimant Button does not exist", layoutParams);
		tearDown();
	} 
	
	
	//Press Claimant and go to new screen
		@MediumTest
		public void testbuttonGoesToNew() throws Exception{
			AccountActivity accountActivity;
			ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AccountActivity.class.getName(), null, false);
			
			mActivity.runOnUiThread(new Runnable(){
				@Override
				public void run(){
					claimantButton.performClick();
				}
			});
			accountActivity = (AccountActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
			assertNotNull("Activity did not start", accountActivity);
			accountActivity.finish();
			tearDown();
		}
		
		/*
		public ListClaimsActivity navigateToListClaims(){
			AccountActivity accountActivity;
			ListClaimsActivity listClaimsActivity;
			ActivityMonitor accountActivityMonitor = getInstrumentation().addMonitor(AccountActivity.class.getName(), null, false);
			ActivityMonitor listClaimsMonitor = getInstrumentation().addMonitor(ListClaimsActivity.class.getName(), null, false);
			//Account Activity UI Elements
			final EditText claimantName;
			final Button claimantNameButton;
			
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run(){
					claimantButton.performClick();
				}
			});
			
			accountActivity = (AccountActivity)getInstrumentation().waitForMonitorWithTimeout(accountActivityMonitor, 0);
			claimantName = (EditText)accountActivity.findViewById(R.id.username_editText);
			claimantNameButton = (Button)accountActivity.findViewById(R.id.account_confirm);
		
			accountActivity.runOnUiThread(new Runnable(){
				@Override 
				public void run() {
					claimantName.setText("John");
					claimantNameButton.performClick();
				}
			});
			
			listClaimsActivity = (ListClaimsActivity)getInstrumentation().waitForMonitorWithTimeout(listClaimsMonitor,  0);
			return listClaimsActivity;
		}
		
		public void testAddClaim() throws Exception {
			final Button addClaimButton;
			AddClaimActivity addClaimActivity;
			ActivityMonitor addClaimMonitor = getInstrumentation().addMonitor(AddClaimActivity.class.getName(), null, false);
			ListClaimsActivity listClaimsActivity = navigateToListClaims();
			addClaimButton = (Button)listClaimsActivity.findViewById(R.id.addClaimButton);
			listClaimsActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					addClaimButton.performClick();
				}
			});
			
			addClaimActivity = (AddClaimActivity)getInstrumentation().waitForMonitorWithTimeout(addClaimMonitor, 0);
			assertNotNull("AddClaimActivity did not launch correctly", addClaimActivity);
			addClaimActivity.finish();
			listClaimsActivity.finish();
			tearDown();
		} */
	

}
