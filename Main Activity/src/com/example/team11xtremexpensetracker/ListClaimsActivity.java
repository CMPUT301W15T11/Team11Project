package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ListClaimsActivity extends Activity {
	private static final String FILENAME = "save.sav";
	private ClaimsList datafile;
	private ListClaimsAdapter listClaimAdapter;
	private ListView claimsListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_claims);
		claimsListView = (ListView) findViewById(R.id.claimsListView); 
		
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
