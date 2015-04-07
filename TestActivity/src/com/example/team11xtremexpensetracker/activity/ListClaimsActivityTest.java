package com.example.team11xtremexpensetracker.activity;

import model.UserController;

import com.example.team11xtremexpensetracker.R;

import activities.AddClaimActivity;
import activities.ListClaimsActivity;
import activities.ViewClaimActivity;
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

public class ListClaimsActivityTest extends ActivityInstrumentationTestCase2<ListClaimsActivity> {

	EditText tag_filter;
	ImageView tag_search;
	Button addClaimButton;
	ListView claimsListView;
	ListClaimsActivity mActivity;
	//ListClaimsActivity mActivity2;
	int origNumberClaims;
	int newNumClaims;
	
	int claimID;
	
	
	public ListClaimsActivityTest() {
		super(ListClaimsActivity.class);
	}
	
	protected void setUp() throws Exception {
		setActivityInitialTouchMode(true);
		UserController UC = new UserController();
		UC.setUserType("Claimant");
		
		claimID = 0;
		Intent intent = new Intent();
		intent.putExtra("claimID", claimID);
		intent.putExtra("aTest", 'y');
		setActivityIntent(intent);
		
		mActivity = getActivity();
			
		tag_filter = (EditText)mActivity.findViewById(R.id.tag_filter);
		tag_search = (ImageView)mActivity.findViewById(R.id.tag_search);
		addClaimButton = (Button)mActivity.findViewById(R.id.addClaimButton);
		claimsListView = (ListView)mActivity.findViewById(R.id.claimsListView);
		
		super.setUp();
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
		final EditText claimName;
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
		claimName = (EditText)addClaimActivity.findViewById(R.id.editTextEnterName);
		doneButton = (Button)addClaimActivity.findViewById(R.id.addClaimDoneButton);
		addClaimActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				claimName.setText("Maui");
				doneButton.performClick();
			}
		});
		
		
		//Close out ViewClaimActivity that has been opened
		final ViewClaimActivity vClaim = (ViewClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor2, 0);
		vClaim.finish();
		assertNotNull(mActivity);
		tearDown();
	}
	
	public void testClaimNumberWentUp() throws Exception{
		newNumClaims = claimsListView.getAdapter().getCount();
		assertTrue("Old: " + new Integer(origNumberClaims).toString() + " New: " + new Integer(newNumClaims).toString(), newNumClaims > origNumberClaims);
		tearDown();
	}
	
	/*
	 * Cant simulate listView itemLongClick with test equipment. so cant check this yet
	public void testDeleteClaim() throws Exception{
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				claimsListView.requestFocusFromTouch();
				claimsListView.setSelection(0);
				claimsListView.performItemClick(claimsListView.getAdapter().getView(0, null, null), 0, claimsListView.getAdapter().getItemId(0));
				
			}
		});
		
		
	}*/
	
	
}




