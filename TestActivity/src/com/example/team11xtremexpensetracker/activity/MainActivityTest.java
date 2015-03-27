package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
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
		setActivityInitialTouchMode(false);
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
	
	
	//Tests to see if new activity is launched when button is pressed
	@MediumTest
	public void testbuttonGoesToNew() throws Exception{
		//setUp();
		ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ListClaimsActivity.class.getName(), null, false);
		
		mActivity.runOnUiThread(new Runnable(){
			@Override
			public void run(){
				claimantButton.performClick();
			}
		});
		ListClaimsActivity listClaimAct = (ListClaimsActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 0);
		assertNotNull("Activity did not start", listClaimAct);
		listClaimAct.finish();
		tearDown();
	}
	

}
