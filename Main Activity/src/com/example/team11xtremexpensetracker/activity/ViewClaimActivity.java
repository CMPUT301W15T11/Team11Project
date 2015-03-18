package com.example.team11xtremexpensetracker.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import com.example.team11xtremexpensetracker.ClaimListController;
import com.example.team11xtremexpensetracker.ClaimsList;
import com.example.team11xtremexpensetracker.ExpenseClaim;
import com.example.team11xtremexpensetracker.Item;
import com.example.team11xtremexpensetracker.ItemlistAdapter;
import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.id;
import com.example.team11xtremexpensetracker.R.layout;
import com.example.team11xtremexpensetracker.R.menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewClaimActivity extends Activity {
	
	private ExpenseClaim currentClaim;
	private Button tagsButton;
	private Button ApproveOrSubmitButton;
	private Button menuButton;
	private Button addExpenseButton;
	
	private String claimName;
	private String dateRange;
	
	private ListView expenseView;
	private TextView nameView;
	private TextView dateRangeView;
	//===
	
	private ClaimsList datafile;
	private ItemlistAdapter itemlistAdapter;
	private ListView itemlistview;
	private int claimID; //The index of the claim in ClaimsList
	private ArrayList<Item> list;
	
	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_claim);
		
		dataList=this.loadFromFile();
		
		ClaimListController.setClaimsList(dataList);
		//Get current claim
		Intent intent = getIntent();
		claimID = intent.getIntExtra("claimID", 0);
		if (claimID >= 0){
			//new ClaimListController();
			currentClaim = ClaimListController.getClaimsList().getClaimById(claimID);
		}
		
		
	
		// TODO: load from file
		// get widgets
		tagsButton = (Button) findViewById(R.id.buttonClaimTags);
		ApproveOrSubmitButton = (Button) findViewById(R.id.buttonApproveSubmit);
		addExpenseButton = (Button) findViewById(R.id.buttonClaimAddExpense);
		nameView = (TextView) findViewById(R.id.textViewClaimName);
		dateRangeView = (TextView) findViewById(R.id.textViewClaimDateRange);
		//Expense Item list view
		itemlistview = (ListView)findViewById(R.id.expenseListView);
		
		list= currentClaim.getItemlist();
		
		itemlistAdapter = new ItemlistAdapter(this, list);

		// item list adapter
		itemlistview.setAdapter(itemlistAdapter);
		
		//populate name and dateRange fields.
		nameView.setText(currentClaim.getName());
		dateRangeView.setText(currentClaim.getDateRange());
		//Toast.makeText(this, "DateRange: " + currentClaim.getDateRange(), Toast.LENGTH_LONG).show();
		
		
		// create listeners
		tagsButton.setOnClickListener(new View.OnClickListener() {	
	
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.putExtra("claimID", claimID);
				intent.setClass(ViewClaimActivity.this,TagActivity.class);
				ViewClaimActivity.this.startActivity(intent);
			}
			
		});
		
		ApproveOrSubmitButton.setOnClickListener(new View.OnClickListener() {	

			@Override
			public void onClick(View v) {
				
			}
			
		});	
		
		addExpenseButton = (Button)findViewById(R.id.buttonClaimAddExpense);
		addExpenseButton.setOnClickListener(new View.OnClickListener() {	

			@Override
			public void onClick(View v) {
				// TODO: jump to add expense activity
				Intent intent =  new Intent(ViewClaimActivity.this,AddItemActivity.class);
				intent.putExtra("claimID", claimID);
				startActivity(intent);
			}
			
		});
			
		// show name and date range
		//nameView.setText(claimName);
				
		//set Listener for item list view
		itemlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int itemposition, long id) {
				// TODO Auto-generated method stub
				final int itemID = itemposition;
				
				Intent intent = new Intent(ViewClaimActivity.this,ViewItemActivity.class);
				intent.putExtra("claimID", claimID);
				intent.putExtra("itemID",itemID);
				startActivity(intent);
				
			}
		});
		//==========================================================================================================
		//============================long click to delete	
		itemlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			private int onLongClickPos;

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				onLongClickPos = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(ViewClaimActivity.this);
				adb.setMessage("Delete?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						list.remove(onLongClickPos);
						
						saveInFile();
						dataList=loadFromFile();
						ClaimListController.setClaimsList(dataList);
					
						itemlistAdapter.notifyDataSetChanged();
						
					}
					
				});
				adb.setNegativeButton("Cancel", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
					
				});
				adb.show();
				return true;
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_claim, menu);
		return true;
	}

	public void editClaim(MenuItem menuItem){
		//Toast.makeText(this, "Edit", Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.putExtra("claimID", claimID);
		// TODO: save claim id into intent for editing
		intent.setClass(ViewClaimActivity.this,AddClaimActivity.class);
		ViewClaimActivity.this.startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed(){
		Intent intentBackPressed = new Intent();
		intentBackPressed.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentBackPressed.setClass(ViewClaimActivity.this, ListClaimsActivity.class);
		ViewClaimActivity.this.startActivity(intentBackPressed);
	}
	
	private ClaimsList loadFromFile() {
		Gson gson = new Gson();
		dataList = new ClaimsList();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<ClaimsList>() {
			}.getType();
			dataList = gson.fromJson(in, typeOfT);
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList;
	}

	private void saveInFile() {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(clc.getClaimsList(), osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
