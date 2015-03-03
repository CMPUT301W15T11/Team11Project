package com.example.team11xtremexpensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddDestionationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_destionation);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_destionation, menu);
		return true;
	}
	
	//Populate the listView with the destinations in the claim
	//If an item in the list gets hold-clicked it should be deleted
	//if an item gets clicked should bring to a destination detail view
	//Also need somewhere to add "Reason for Travel"

}
