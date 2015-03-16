package com.example.team11xtremexpensetracker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Calendar;

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
	private Intent intent;
	private EditText editTextEnterName;
	private ClaimListController claimListController;
	private int claimID;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_claim);


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
		
		Toast.makeText(this, "ClaimID is" + new Integer(claimID).toString(), Toast.LENGTH_LONG).show();
		
		if (oldClaim != null){
			Toast.makeText(this,"Old Claim not null", Toast.LENGTH_LONG).show();
		} else if (oldClaim == null){
			Toast.makeText(this,"Old Claim is null", Toast.LENGTH_LONG).show();
		}
		
		
		
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
				newClaim.setName(claimName);
				if (claimID==-1){
					ClaimListController.getClaimsList().addClaim(newClaim);
				}
				Intent intent = new Intent();
				intent.setClass(AddClaimActivity.this,ListClaimsActivity.class);
				AddClaimActivity.this.startActivity(intent);
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


}
