package com.example.team11xtremexpensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ViewDestinationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_destination);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_destination, menu);
		return true;
	}

	//Should populate view with information from calling dest.
	//save button should replace info in dest with new info
}
