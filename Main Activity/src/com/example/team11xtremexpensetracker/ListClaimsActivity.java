package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListClaimsActivity extends Activity {
	private static final String FILENAME = "save.sav";
	private ClaimsList datafile;
	//private ListClaimsAdapter listClaimAdapter;
	private ListView claimsListView;
	private ClaimListController clc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_claims);
		claimsListView = (ListView) findViewById(R.id.claimsListView); 
		clc = new ClaimListController(); 
		Collection<ExpenseClaim> claims = clc.getClaimsList().getClaims(); 
		final ArrayList<ExpenseClaim> list = new ArrayList<ExpenseClaim>(claims);
		final ArrayAdapter<ExpenseClaim> claimAdapter = new ArrayAdapter<ExpenseClaim>(this, android.R.layout.simple_expandable_list_item_1, list);
		claimsListView.setAdapter(claimAdapter);
		
		clc.getClaimsList().addListener(new Listener () {
			@Override
			public void update(){
				list.clear();
				Collection<ExpenseClaim> claims = clc.getClaimsList().getClaims();
				list.addAll(claims);
				claimAdapter.notifyDataSetChanged();
			}
		});
		
		claimsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				String temp = list.get(position).toString();
				Toast.makeText(ListClaimsActivity.this, "You Clicked " + temp, Toast.LENGTH_SHORT).show();
				//Now make the same intent push as in the addClaimActivity.
				Intent intent = new Intent(ListClaimsActivity.this, ViewClaimActivity.class);
				intent.putExtra("claimID", position);
				startActivity(intent);
			}
		});
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
