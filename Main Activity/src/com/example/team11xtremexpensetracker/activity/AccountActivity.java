package com.example.team11xtremexpensetracker.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.team11xtremexpensetracker.ClaimListController;
import com.example.team11xtremexpensetracker.ClaimsList;
import com.example.team11xtremexpensetracker.R;
import com.example.team11xtremexpensetracker.R.id;
import com.example.team11xtremexpensetracker.R.layout;
import com.example.team11xtremexpensetracker.R.menu;
import com.example.team11xtremexpensetracker.RegisterActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountActivity extends Activity {

	private ClaimsList dataList;
	private static final String FILENAME = "datafile.sav";
	private ClaimListController clc;

	private EditText accountIdEdit;
	private EditText accountPasswordEdit;
	private String accountId;
	private String password;
	private Button backButton;
	private Button confirmButton;
	private Button registerButton;

	private void init() {

		accountIdEdit = (EditText) findViewById(R.id.account_id_view);
		accountPasswordEdit = (EditText) findViewById(R.id.account_password_view);
		backButton = (Button) findViewById(R.id.account_back);
		confirmButton = (Button) findViewById(R.id.account_confirm);
		registerButton=(Button) findViewById(R.id.register_new_button);

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				accountId = accountIdEdit.getText().toString();
				password = accountPasswordEdit.getText().toString();
				if (checkId(accountId) && checkPassword(password)) {
					// TODO check userID exists or not,if so go next step
					// TODO check every claim's userID and add them into next activity
					
					//
					Intent intent=new Intent(AccountActivity.this,ListClaimsActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(AccountActivity.this, "Invalid ID or password!", Toast.LENGTH_SHORT).show();
					accountPasswordEdit.setText("");
				}
			}
		});
		
		registerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(AccountActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	private boolean checkId(String accountId) {
		String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(accountId);
		return m.matches();
	}

	private boolean checkPassword(String accountPassword) {
		if (accountPassword.length() < 8) {
			return false;
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
