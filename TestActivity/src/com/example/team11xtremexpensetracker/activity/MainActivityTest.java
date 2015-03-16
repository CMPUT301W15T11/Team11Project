package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.MainActivity;
import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.id;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.test.ViewAsserts;
import android.view.ViewGroup;
import android.test.suitebuilder.annotation.MediumTest;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	MainActivity mActivity;
	Button claimantButton;
	Button approverButton;
	
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
	public void testClaimantButton(){
		final View decorView = mActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, claimantButton);
		
		final ViewGroup.LayoutParams layoutParams = claimantButton.getLayoutParams();
		assertNotNull(layoutParams);
	}
	
	@MediumTest
	public void approverButton(){
		final View decorView = mActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, approverButton);
		
		final ViewGroup.LayoutParams layoutParams = approverButton.getLayoutParams();
		assertNotNull(layoutParams);
	}
	


}
