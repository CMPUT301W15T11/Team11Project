package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.UserController;

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
	String expenseItemName = "Margaritas";
	String expenseItemAmountSpent = "120";
	//ListClaimsActivity mActivity2;
	int origNumClaims;
	int newNumClaims;
	
	int origNumItems;
	int newNumItems;
	
	
	public ListClaimsActivityTest() {
		super(ListClaimsActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(true);
		UserController UC = new UserController();
		UC.setUserType("Claimant");
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
	
	@MediumTest
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
	
	//Check to see if new claim shows up in claims list
	@MediumTest
	public void testAddNewClaimAddedToClaimList() throws Exception {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AddClaimActivity.class.getName(), null, false);
		ActivityMonitor activityMonitor2 = getInstrumentation().addMonitor(ViewClaimActivity.class.getName(), null, false);
		final EditText claimNameTextView;
		final Button doneButton;
		
		
		//Take note of number of claims in list before one is added.
		origNumClaims = claimsListView.getAdapter().getCount();
		
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
		assertTrue("Old: " + new Integer(origNumClaims).toString() + " New: " + new Integer(newNumClaims).toString(), newNumClaims > origNumClaims);
		tearDown();
		
	}
	
	/*
	 * Test clicking the last item in listView and check if the proper viewClaim opens
	 */
	@MediumTest
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
	
	/*
	@MediumTest
	public void testAddExpenseToClaim() throws Exception {
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ViewClaimActivity.class.getName(), null, false);
		ActivityMonitor activityMonitor2 = getInstrumentation().addMonitor(AddItemActivity.class.getName(), null, false);
		ActivityMonitor activityMonitor3 = getInstrumentation().addMonitor(ViewItemActivity.class.getName(), null, false);
		ViewClaimActivity vActivity;
		AddItemActivity iActivity;
		ViewItemActivity viewItemActivity;
		final int listCount = (claimsListView.getAdapter().getCount()) -1;
		//ViewClaimAcitivty UI Elemtents
		final Button addExpenseButton;
		final ListView expenseItemListView;
		//AddItemActivity UI Elements
		EditText itemName;
		final Button expenseItemDoneButton;
		EditText amountSpent;
		//ViewItemActivity UI Elements
		TextView itemNameView;
		TextView amountSpentView;
		
		mActivity.runOnUiThread(new Runnable() {
			@Override 
			public void run(){
				claimsListView.performItemClick(claimsListView, listCount, 0);	
			}
		});
		vActivity = (ViewClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		addExpenseButton = (Button)vActivity.findViewById(R.id.buttonClaimAddExpense);
		expenseItemListView = (ListView)vActivity.findViewById(R.id.expenseListView);
		origNumItems = expenseItemListView.getAdapter().getCount();
		vActivity.runOnUiThread(new Runnable() {
			@Override
			public void run(){
				addExpenseButton.performClick();
			}
		});
		
		
		
		 //Grab add item activity, 
		 //add a new item and click "done" button 
		iActivity = (AddItemActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor2, 0);
		itemName = (EditText)iActivity.findViewById(R.id.additemname);
		expenseItemDoneButton = (Button)iActivity.findViewById(R.id.confirmadd);
		amountSpent = (EditText)iActivity.findViewById(R.id.addamountspent);
		
		itemName.setText(expenseItemName);
		amountSpent.setText(expenseItemAmountSpent);
		
		iActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				expenseItemDoneButton.performClick();
			}
		});
		
		
		 //grab view item activity
		 //Make sure data matches what was added
		viewItemActivity = (ViewItemActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor3, 0);
		itemNameView = (TextView)viewItemActivity.findViewById(R.id.itemnameView);
		amountSpentView = (TextView)viewItemActivity.findViewById(R.id.amountspentView);
		
		//assertTrue("Item name is not correct", itemNameView.getText().equals(expenseItemName) );
		assertTrue("Item name is not correct", itemNameView.getText().equals("NOPE") );
		assertTrue("Amount spent is not correct", amountSpentView.getText().equals(expenseItemAmountSpent) );
		
		viewItemActivity.finish();
		iActivity.finish();
		vActivity.finish();
		
		tearDown();
		/*
		setUp();
		mActivity.runOnUiThread(new Runnable() {
			@Override 
			public void run(){
				claimsListView.performItemClick(claimsListView, listCount, 0);	
			}
		});
		vActivity = (ViewClaimActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		ListView expenseItemListView2 = (ListView)vActivity.findViewById(R.id.expenseListView);
		newNumItems = expenseItemListView2.getAdapter().getCount();
		assertTrue("Expense Item was not added to list view", origNumItems+1 == newNumItems);
		vActivity.finish();
		tearDown(); */
	//} 
}




