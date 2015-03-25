package com.example.team11xtremexpensetracker.activity;

/*
 * Activity that provdies the UI to add new Expense Claims
 * Claims must contain a name, and a start date before their end date.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Calendar;

import com.example.team11xtremexpensetracker.ClaimListController;
import com.example.team11xtremexpensetracker.ClaimsList;
import com.example.team11xtremexpensetracker.ExpenseClaim;
import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.SingleDatePickerDialog;
import com.example.team11xtremexpensetracker.R.id;
import com.example.team11xtremexpensetracker.R.layout;
import com.example.team11xtremexpensetracker.R.menu;
import com.example.team11xtremexpensetracker.SingleDatePickerDialog.OnDateSetListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddClaimActivity extends Activity {
	
	private ExpenseClaim oldClaim;
	private ExpenseClaim newClaim;
	private String claimName;
	private Calendar startDate;
	private Calendar endDate;
	private Button startDatePickerButton;
	private Button endDatePickerButton;
	private Button doneButton;
	private EditText editTextEnterName;
	private ClaimListController claimListController;
	private int claimID;
	
	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_claim);
		
		dataList=this.loadFromFile();
		clc = new ClaimListController(); 
		ClaimListController.setClaimsList(dataList);


		// get widgets
		startDatePickerButton = (Button) findViewById(R.id.startDatePickerButton);
		endDatePickerButton = (Button) findViewById(R.id.endDatePickerButton);
		doneButton = (Button) findViewById(R.id.addClaimDoneButton);
		
		editTextEnterName = (EditText)findViewById(R.id.editTextEnterName);
		startDate=Calendar.getInstance();
		endDate=Calendar.getInstance();
		// set listener
		
		
		 // Ok, now if you get some extra, set Buttons to start values of claim
		Intent intent =  getIntent();
		claimID = intent.getIntExtra("claimID",-1);
		if (claimID >= 0){
			new ClaimListController();
			oldClaim = ClaimListController.getClaimsList().getClaimById(claimID);
			editTextEnterName.setText(oldClaim.getName());
			newClaim = oldClaim;
		}
		
		
		//Sets date picker button to open a date picker when clicked
		startDatePickerButton.setOnClickListener(new View.OnClickListener() {	
			Calendar c = Calendar.getInstance();
			
			@Override
			public void onClick(View v) {
				new SingleDatePickerDialog(AddClaimActivity.this, 0, new SingleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth) {
						String textString_start = String.format("%d/%d/%d", startMonthOfYear + 1,
								startDayOfMonth, startYear);
						startDatePickerButton.setText(textString_start);
						startDate.set(startYear, startMonthOfYear+1, startDayOfMonth);
						
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
			}
			
		});  
		
		// sets date picker button to open a date picker when clicked
		endDatePickerButton.setOnClickListener(new View.OnClickListener() {	
			Calendar c = Calendar.getInstance();
			@Override
			public void onClick(View v) {
				new SingleDatePickerDialog(AddClaimActivity.this, 0, new SingleDatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
							int startDayOfMonth) {
						String textString_start = String.format("%d/%d/%d", startMonthOfYear + 1,
								startDayOfMonth, startYear);
						endDatePickerButton.setText(textString_start);
						endDate.set(startYear, startMonthOfYear+1, startDayOfMonth);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
			}
		});
		
		
		// Defines what is executed when done button is pressed
		// If name is entered, and dates are ok it creates a new claim,
		// Adds it to the list, and brings user to view the claim and add expense. 
		// Else it prompts user to enter more info
		doneButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				if(startDate.after(endDate)){
					Toast.makeText(AddClaimActivity.this,"Start date should be earlier than end date\n"+"        Submit Denied",Toast.LENGTH_SHORT).show();
					startDatePickerButton.setText("Choose start Date");
					return;
				}
		
				if (claimID==-1){
					newClaim = new ExpenseClaim();
				}
				
				newClaim.setStartDate(startDate);
				newClaim.setEndDate(endDate);
				
				String claimName = editTextEnterName.getText().toString();
				if(claimName.equals("")){
					Toast.makeText(AddClaimActivity.this, "Enter a Claim Name", Toast.LENGTH_SHORT).show();
					return;
				}
				newClaim.setName(claimName);
				if (claimID==-1){
					claimID = ClaimListController.getClaimsList().getLength();
					ClaimListController.getClaimsList().addClaim(newClaim);
				}
				saveInFile();
				new ClaimListController();
				Intent intent = new Intent(AddClaimActivity.this, ViewClaimActivity.class);
				intent.putExtra("claimID", claimID);
				startActivity(intent);
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true; 
	}
	
	// Loads claimsList from file
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

	// Saves claimsList to file
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
