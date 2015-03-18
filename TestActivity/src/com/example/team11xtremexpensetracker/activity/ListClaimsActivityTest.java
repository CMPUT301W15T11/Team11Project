package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;

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
	
	
}




