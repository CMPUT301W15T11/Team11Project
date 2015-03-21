/*
 * Main activity. Holds claimant and approver buttons.
 */


package com.example.team11xtremexpensetracker.activity;

import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.layout;
import com.example.team11xtremexpensetracker.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void claimantSelected(View view){
		//Toast.makeText(this, "Claimant", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(MainActivity.this, AccountActivity.class );
		startActivity(intent);
	}

}
