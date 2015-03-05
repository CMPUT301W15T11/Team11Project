package com.example.team11xtremexpensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ListClaimsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_claims);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_claims, menu);
		return true;
	}
	
	public void addClaim(View v){
		Toast.makeText(this, "Adding Claim", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(ListClaimsActivity.this, AddClaimActivity.class);
		startActivity(intent);
	}

}
