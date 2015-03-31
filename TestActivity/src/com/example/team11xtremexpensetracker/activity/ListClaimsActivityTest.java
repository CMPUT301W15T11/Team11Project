package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListClaimsActivityTest extends ActivityInstrumentationTestCase2<ListClaimsActivity> {

	EditText tag_filter;
	ImageView tag_search;
	Button addClaimButton;
	ListView claimsListView;
	ListClaimsActivity mActivity;
	//Values To be used to create claim
	String claimName = "Maui";
	//ListClaimsActivity mActivity2;
	int origNumberClaims;
	int newNumClaims;
	
	
	public ListClaimsActivityTest() {
		super(ListClaimsActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(true);
		mActivity = getActivity();
		
		Intent intent = new Intent();
		intent.putExtra("claimID", 2);
		setActivityIntent(intent);

		
		tag_filter = (EditText)mActivity.findViewById(R.id.tag_filter);
		tag_search = (ImageView)mActivity.findViewById(R.id.tag_search);
		addClaimButton = (Button)mActivity.findViewById(R.id.addClaimButton);
		claimsListView = (ListView)mActivity.findViewById(R.id.claimsListView);
		
		
	}

	@MediumTest
	public void testTagFilter() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, tag_filter);

	    final ViewGroup.LayoutParams layoutParams = tag_filter.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	
	@MediumTest
	public void testTagSearch() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, tag_search);

	    final ViewGroup.LayoutParams layoutParams = tag_search.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	
	@MediumTest
	public void testClaimListView() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, claimsListView);

	    final ViewGroup.LayoutParams layoutParams = claimsListView.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	
	@MediumTest
	public void testAddClaimButton() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, addClaimButton);

	    final ViewGroup.LayoutParams layoutParams = addClaimButton.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	
	public void testAddClaimButtonOpensView() throws Exception {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AddClaimActivity.class.getName(), null, false);
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				addClaimButton.performClick();
			}
		});
		
		AddClaimActivity addClaimActivity = (AddClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		assertNotNull(addClaimActivity);
		addClaimActivity.finish();
		
		tearDown();
	} 
	
	public void testAddClaimInAddClaimActivity() throws Exception {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AddClaimActivity.class.getName(), null, false);
		ActivityMonitor activityMonitor2 = getInstrumentation().addMonitor(ViewClaimActivity.class.getName(), null, false);
		final EditText claimNameTextView;
		final Button doneButton;
		
		
		//Take note of number of claims in list before one is added.
		origNumberClaims = claimsListView.getAdapter().getCount();
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				addClaimButton.performClick();
			}
		});
		
		AddClaimActivity addClaimActivity = (AddClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		claimNameTextView = (EditText)addClaimActivity.findViewById(R.id.editTextEnterName);
		doneButton = (Button)addClaimActivity.findViewById(R.id.addClaimDoneButton);
		addClaimActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				claimNameTextView.setText(claimName);
				doneButton.performClick();
			}
		});
		
		
		//Close out ViewClaimActivity that has been opened
		final ViewClaimActivity vClaim = (ViewClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor2, 0);
		vClaim.finish();
		assertNotNull(mActivity);
		tearDown();
		
		/* 
		 * Re setup to get the new number of claims
		 */
		setUp();
		newNumClaims = claimsListView.getAdapter().getCount();
		assertTrue("Old: " + new Integer(origNumberClaims).toString() + " New: " + new Integer(newNumClaims).toString(), newNumClaims > origNumberClaims);
		tearDown();
		
	}
	
	/*
	 * Test clicking the last item in listView and check if the proper viewClaim opens
	 */
	public void testClickToClaimViewShowsCorrectCaim() throws Exception {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewClaimActivity.class.getName(), null, false);
		final int listCount = (claimsListView.getAdapter().getCount()) -1;
		
		//ViewClaimAcitivty UI Elemtents
		final TextView claimNameTextView;
		
		mActivity.runOnUiThread(new Runnable() {
			@Override 
			public void run(){
				claimsListView.performItemClick(claimsListView, listCount, 0);	
			}
		});
		ViewClaimActivity vActivity = (ViewClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		claimNameTextView = (TextView)vActivity.findViewById(R.id.textViewClaimName);
		assertTrue("Claim Name not what was expected" ,claimNameTextView.getText().equals(claimName));
		vActivity.finish();
		tearDown();
	}
	
	/*
	 * test adding an expense to a claims
	 */
	public void testAddExpenseToClaim() throws Exception {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewClaimActivity.class.getName(), null, false);
		ActivityMonitor activityMonitor2 = getInstrumentation().addMonitor(AddItemActivity.class.getName(), null, false);
		ViewClaimActivity vActivity;
		AddItemActivity iActivity;
		final int listCount = (claimsListView.getAdapter().getCount()) -1;
		//ViewClaimAcitivty UI Elemtents
		final Button addExpenseButton;
		//AddItemActivity UI Elements
		
		
		mActivity.runOnUiThread(new Runnable() {
			@Override 
			public void run(){
				claimsListView.performItemClick(claimsListView, listCount, 0);	
			}
		});
		vActivity = (ViewClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		addExpenseButton = (Button)vActivity.findViewById(R.id.buttonClaimAddExpense);
		
		vActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				addExpenseButton.performClick();
			}
		});
		
		iActivity = (AddItemActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor2, 0);
		
		//Add the expense stuff here.
		
		iActivity.finish();
		vActivity.finish();
		tearDown();
	}
}




