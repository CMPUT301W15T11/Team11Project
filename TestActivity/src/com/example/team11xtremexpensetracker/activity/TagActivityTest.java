package com.example.team11xtremexpensetracker.activity;

import model.UserController;

import com.example.team11xtremexpensetracker.R;

import activities.TagActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TagActivityTest extends ActivityInstrumentationTestCase2<TagActivity> {

	TagActivity tActivity;
	EditText enterTagEditText;
	Button addTagButton;
	
	
	public TagActivityTest(){
		super(TagActivity.class);
	}
	
	protected void setUp() throws Exception {
		setActivityInitialTouchMode(true);
		UserController UC = new UserController();
		UC.setUserType("Claimant");
		
		Intent intent = new Intent();
        intent.putExtra("claimID",0);
        setActivityIntent(intent);
		
		tActivity=getActivity();
		enterTagEditText=(EditText)tActivity.findViewById(R.id.add_tag_editText);
		addTagButton=(Button)tActivity.findViewById(R.id.add_tag_button);
		super.setUp();
	}
	
	
	public void testTagButton() throws Exception{
		final View decorView = tActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, addTagButton);
		final ViewGroup.LayoutParams layoutParams = addTagButton.getLayoutParams();
		assertNotNull(layoutParams);
		tearDown();
	} 
	
	
	public void testTagEditText() throws Exception{
		final View decorView = tActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, enterTagEditText);
		final ViewGroup.LayoutParams layoutParams = enterTagEditText.getLayoutParams();
		assertNotNull(layoutParams);
		tearDown();
	}

}
