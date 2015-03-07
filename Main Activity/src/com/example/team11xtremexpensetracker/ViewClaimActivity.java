package com.example.team11xtremexpensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
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
		
		
		addExpenseButton.setOnClickListener(new View.OnClickListener() {	

			@Override
			public void onClick(View v) {
				// TODO: jump to add expense activity
			}
			
		});
		
		// show name and date range
		nameView.setText(claimName);
		
		
		// create adapter for expense list view
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_claim, menu);
		return true;
	}


}
