package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.TagActivity;

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
		super.setUp();
		setActivityInitialTouchMode(true);
		tActivity=getActivity();
		enterTagEditText=(EditText)tActivity.findViewById(R.id.add_tag_editText);
		addTagButton=(Button)tActivity.findViewById(R.id.add_tag_button);
	}
	
	public void testTagButton(){
		final View decorView = tActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, addTagButton);
		final ViewGroup.LayoutParams layoutParams = addTagButton.getLayoutParams();
		assertNotNull(layoutParams);
	}
	
	public void testTagEditText(){
		final View decorView = tActivity.getWindow().getDecorView();
		ViewAsserts.assertOnScreen(decorView, enterTagEditText);
		final ViewGroup.LayoutParams layoutParams = enterTagEditText.getLayoutParams();
		assertNotNull(layoutParams);
	}

}
