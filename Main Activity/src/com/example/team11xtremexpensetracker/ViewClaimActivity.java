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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
	private static final String FILENAME = "save.sav";
	private Itemlist datafile;
	private ItemlistAdapter itemlistAdapter;
	private ListView itemlistview;
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//==listView
		datafile = this.loadFromFile();
		
		itemlistAdapter = new ItemlistAdapter(this,datafile.getItemlist());
		itemlistview.setAdapter(itemlistAdapter);
		
	}

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_claim);
		
		// TODO: load from file
		// get widgets
		tagsButton = (Button) findViewById(R.id.buttonClaimTags);
		ApproveOrSubmitButton = (Button) findViewById(R.id.buttonApproveSubmit);
		menuButton = (Button) findViewById(R.id.ViewClaimMenuButton);
		addExpenseButton = (Button) findViewById(R.id.buttonClaimAddExpense);
		expenseView = (ListView) findViewById(R.id.expenseListView);
		nameView = (TextView) findViewById(R.id.textViewClaimName);
		dateRangeView = (TextView) findViewById(R.id.textViewClaimDateRange);
		//Expense Item list view
		itemlistview = (ListView)findViewById(R.id.expenseListView);

		
		// create listeners
		tagsButton.setOnClickListener(new View.OnClickListener() {	
	
			@Override
			public void onClick(View v) {
				// TODO: save claim id into intent
				// TODO: jump to tags activity
				Intent intent = new Intent();
				intent.setClass(ViewClaimActivity.this,TagActivity.class);
				ViewClaimActivity.this.startActivity(intent);
			}
			
		});
		
		ApproveOrSubmitButton.setOnClickListener(new View.OnClickListener() {	

			@Override
			public void onClick(View v) {
				
			}
			
		});
		
		menuButton.setOnClickListener(new View.OnClickListener() {	
			// TODO: NOT FINISHED. more function will be added, for now it can 
			// only edit current claim
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				// TODO: save claim id into intent for editing
				intent.setClass(ViewClaimActivity.this,AddClaimActivity.class);
				ViewClaimActivity.this.startActivity(intent);
			}
			
		});
		
		addExpenseButton = (Button)findViewById(R.id.buttonClaimAddExpense);
		addExpenseButton.setOnClickListener(new View.OnClickListener() {	

			@Override
			public void onClick(View v) {
				// TODO: jump to add expense activity
				Intent intent =  new Intent(ViewClaimActivity.this,AddItemActivity.class);
				startActivity(intent);
			}
			
		});
		
		
		// show name and date range
		nameView.setText(claimName);
		
		
		//set Listener for itemlist view
		itemlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int itemposition, long id) {
				// TODO Auto-generated method stub
				final int itemID = itemposition;
				
				Intent intent = new Intent(ViewClaimActivity.this,ViewItemActivity.class);
				intent.putExtra("itemID",itemID);
				startActivity(intent);
				
			}
		});
		//==========================================================================================================
		//============================long click to delete	
		itemlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int itemID = position;
				AlertDialog.Builder adb = new AlertDialog.Builder(ViewClaimActivity.this);
				adb.setMessage("Delete?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Item item = datafile.getItemlist().get(itemID);
						datafile.getItemlist().remove(item);
						saveInFile();
						itemlistAdapter.clear();
						datafile = loadFromFile();
						itemlistAdapter.addAll(datafile.getItemlist());
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
				return false;
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_claim, menu);
		return true;
	}
	//==========================================================================================================
	//============================Gson
	private Itemlist loadFromFile(){
		Gson gson = new Gson();
		datafile = new Itemlist();
		try{
			FileInputStream fis = openFileInput(FILENAME);
			InputStreamReader in = new InputStreamReader(fis);
			Type typeOfT = new TypeToken<Itemlist>(){}.getType();
			datafile = gson.fromJson(in, typeOfT);
			fis.close();
		} catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		return datafile;
	}
	private void saveInFile(){
		Gson gson = new Gson();
		try{
			FileOutputStream fos = openFileOutput(FILENAME,0);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(datafile,osw);
			osw.flush();
			fos.close();
			
			
		} catch(FileNotFoundException e){
			e.printStackTrace();
			
		}catch (IOException e){
			e.printStackTrace();
		}
		
		
		
	}


}
