package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;

import activities.AddClaimActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddClaimActivityTest extends ActivityInstrumentationTestCase2<AddClaimActivity> {
	
	TextView textViewEnterName;
	EditText editTextEnterName;
	Button startDatePickerButton;
	Button endDatePickerButton;
	Button addClaimDoneButton;
	AddClaimActivity mActivity;
	
	public AddClaimActivityTest() {
		super(AddClaimActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(true);
		mActivity = getActivity();
		textViewEnterName = (TextView)mActivity.findViewById(R.id.textViewEnterName);
		editTextEnterName = (EditText)mActivity.findViewById(R.id.editTextEnterName);
		startDatePickerButton = (Button)mActivity.findViewById(R.id.startDatePickerButton);
		endDatePickerButton = (Button)mActivity.findViewById(R.id.endDatePickerButton);
		addClaimDoneButton = (Button)mActivity.findViewById(R.id.addClaimDoneButton);
		
		
		Intent intent = new Intent();
        intent.putExtra("claimID",2);
        setActivityIntent(intent);
	}
	
	
	@MediumTest
	public void testEnterNameText() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, textViewEnterName);

	    final ViewGroup.LayoutParams layoutParams = textViewEnterName.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	
	@MediumTest
	public void testEnterNameEdit() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, editTextEnterName);

	    final ViewGroup.LayoutParams layoutParams = editTextEnterName.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	@MediumTest
	public void testStartDate() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, startDatePickerButton);

	    final ViewGroup.LayoutParams layoutParams = startDatePickerButton.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	@MediumTest
	public void testEndDate() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, endDatePickerButton);

	    final ViewGroup.LayoutParams layoutParams = endDatePickerButton.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
	
	@MediumTest
	public void testDone() throws Exception {
	    final View decorView = mActivity.getWindow().getDecorView();

	    ViewAsserts.assertOnScreen(decorView, addClaimDoneButton);

	    final ViewGroup.LayoutParams layoutParams = addClaimDoneButton.getLayoutParams();
	    assertNotNull(layoutParams);

	    tearDown();
	}
}







